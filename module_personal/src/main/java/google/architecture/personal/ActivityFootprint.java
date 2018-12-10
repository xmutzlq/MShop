package google.architecture.personal;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazysunj.multitypeadapter.sticky.StickyHeaderDecoration;
import com.king.android.res.config.ARouterPath;
import com.kongzue.dialog.v2.SelectDialog;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BasePagingActivity;
import google.architecture.common.util.AppCompat;
import google.architecture.common.viewmodel.FootprintViewModel;
import google.architecture.common.viewmodel.PersonalViewModel;
import google.architecture.coremodel.data.FootprintInfo;
import google.architecture.coremodel.data.MultiHeaderEntity;
import google.architecture.coremodel.datamodel.RefreshListModel;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.personal.adapter.FootprintAdapter;
import google.architecture.personal.adapter.helper.FootprintAdapterHelper;
import google.architecture.personal.adapter.helper.FootprintItem;
import google.architecture.personal.databinding.ActivityFootPrintBinding;

/**
 * @author lq.zeng
 * @date 2018/10/18
 */

@Route(path = ARouterPath.PersonalFootprintsAty)
public class ActivityFootprint extends BasePagingActivity<ActivityFootPrintBinding> {

    private FootprintAdapter adapter;
    private FootprintViewModel footprintViewModel;
    private PersonalViewModel personalViewModel;
    private boolean toggleFootprintManager;
    private boolean toggleSelectedAll;

    @Override
    protected BaseQuickAdapter getAdapter() {
        adapter = new FootprintAdapter(new FootprintAdapterHelper());
        adapter.setASelectListener(isSelected -> {
            toggleSelectedAll = isSelected;
            binding.cbFootprintAllSelect.setChecked(isSelected);
            binding.btnFootprintCommit.setText(getResources().getString(R.string.str_del_count, adapter.getSelectedCount()));
        });
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            String goodsId = String.valueOf(adapter.getData().get(position).getId());
            ARouter.getInstance().build(ARouterPath.DetailAty).withString(CommEvent.KEY_EXTRA_VALUE, goodsId).navigation();
        });
        return adapter;
    }

    @Override
    protected void prePareRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this_);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new StickyHeaderDecoration(adapter));
    }

    @Override
    protected void afterRecycleView() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_foot_print;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCanBack(true);
        setTitleName(getString(R.string.personal_my_footprint_title));
        setRightText(getResources().getString(R.string.str_edit), v -> {
            if(adapter != null && adapter.getData().size() > 0) {
                exitEditState();
            }
        });

        binding.btnFootprintAllSelect.setOnClickListener(v -> {
            toggleSelectedAll = !toggleSelectedAll;
            binding.cbFootprintAllSelect.setChecked(toggleSelectedAll);
            adapter.selectedAll(toggleSelectedAll);
            binding.btnFootprintCommit.setText(getResources().getString(R.string.str_del_count, adapter.getSelectedCount()));
        });

        AppCompat.setBackgroundPressed(this_, binding.btnFootprintCommit, R.color.themePrimaryDark);
        binding.btnFootprintCommit.setOnClickListener(v -> {
            SelectDialog.show(this_, getResources().getString(R.string.warming_tip), "是否删除浏览记录？", (dialog, which) -> {
                personalViewModel.delFootprints(adapter.getSelectedIds(), (code, msg) -> {
                    if(code == 0) {
                        List<MultiHeaderEntity> multiHeaderEntities = adapter.getUnSelectedList();
                        adapter.notifyRefresh(multiHeaderEntities);
                        if(multiHeaderEntities == null || multiHeaderEntities.size() == 0) {
                            if(pagingHelper != null) pagingHelper.onRefresh();
                        }
                        Looper.myQueue().addIdleHandler(() -> {
                            exitEditState();
                            return false;
                        });
                    }
                });
            });
        });

        personalViewModel = new PersonalViewModel();
        addRunStatusChangeCallBack(personalViewModel);
        footprintViewModel = new FootprintViewModel();
        setListViewModel(footprintViewModel);
        if(swipeRefresh != null) swipeRefresh.setRefreshing(true);
        if(adapter != null) adapter.setLoadMoreShown(false);
        if(pagingHelper != null) pagingHelper.onRefresh();
    }

    private void exitEditState() {
        toggleFootprintManager = !toggleFootprintManager;
        toggleSelectedAll = !toggleFootprintManager;
        binding.footprintSelectLayout.setVisibility(toggleFootprintManager ? View.VISIBLE : View.GONE);
        adapter.showItemCheckSate(toggleFootprintManager); //退出编辑模式
        if(!toggleSelectedAll) { //退出全选模式
            adapter.selectedAll(false);
            binding.cbFootprintAllSelect.setChecked(false);
        }
    }

    @Override
    protected void onRefreshLoadingShown() {
        if(adapter != null) adapter.loadMoreComplete();
    }

    @Override
    protected void onDataResult(Object o) {
        if(swipeRefresh != null) swipeRefresh.setRefreshing(false);
        if(o instanceof RefreshListModel) {
            RefreshListModel<FootprintInfo> refreshListModel = (RefreshListModel) o;
            if(adapter != null) adapter.setLoadMoreShown(refreshListModel != null && refreshListModel.list.size() > 0);
            List<MultiHeaderEntity> footprintItems = new ArrayList<>();
            for (FootprintInfo footprintInfo : refreshListModel.list) {
                footprintItems.add(new FootprintItem(Long.valueOf(footprintInfo.getGoods_id()),
                        footprintInfo.getGoods_name(), footprintInfo.getShop_price(), footprintInfo.getOriginal_img(), footprintInfo.getDate()));
            }
            if (refreshListModel != null) {
                if (refreshListModel.isRefreshType()) {
                    if(adapter != null) adapter.notifyRefresh(footprintItems);
                    Looper.myQueue().addIdleHandler(() -> {
                        if(recyclerView != null) recyclerView.smoothScrollToPosition(0);
                        if(pagingHelper.isFull()) {
                            if(adapter != null) adapter.loadMoreEnd();
                        }
                        return false;
                    });
                } else if (refreshListModel.isUpdateType()) {
                    if(refreshListModel.list == null || refreshListModel.list.size() == 0) {
                        if(adapter != null) adapter.setLoadMoreShown(true);
                        if(adapter != null) adapter.loadMoreEnd();
                    } else {
                        if(adapter != null) adapter.notifyUpdate(footprintItems);
                    }
                }
            }
        }
    }

    @Override
    protected void onEmptyDisplaying() {
        adapter.setEmptyView(R.layout.layout_empty_foot_print, (ViewGroup) recyclerView.getParent());
    }
}
