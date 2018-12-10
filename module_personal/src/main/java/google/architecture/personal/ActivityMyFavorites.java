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
import com.king.android.res.config.ARouterPath;
import com.kongzue.dialog.v2.SelectDialog;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BasePagingActivity;
import google.architecture.common.util.AppCompat;
import google.architecture.common.viewmodel.MyFavoriteViewModel;
import google.architecture.common.viewmodel.PersonalViewModel;
import google.architecture.coremodel.data.FavoriteData;
import google.architecture.coremodel.data.MultiHeaderEntity;
import google.architecture.coremodel.datamodel.RefreshListModel;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.personal.adapter.MyFavoritesAdapter;
import google.architecture.personal.adapter.helper.MyFavoritesAdapterHelper;
import google.architecture.personal.adapter.helper.MyFavoritesItem;
import google.architecture.personal.databinding.ActivityMyFavoritesBinding;

/**
 * @author lq.zeng
 * @date 2018/9/19
 */
@Route(path = ARouterPath.PersonalFavoritesAty)
public class ActivityMyFavorites extends BasePagingActivity<ActivityMyFavoritesBinding> {

    private MyFavoriteViewModel myFavoriteViewModel;
    private MyFavoritesAdapter adapter;
    private PersonalViewModel personalViewModel;

    private boolean toggleFootprintManager;
    private boolean toggleSelectedAll;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_favorites;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        adapter = new MyFavoritesAdapter(new MyFavoritesAdapterHelper());
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            MyFavoritesItem favoriteItem = (MyFavoritesItem) adapter1.getData().get(position);
            ARouter.getInstance().build(ARouterPath.DetailAty).withString(CommEvent.KEY_EXTRA_VALUE, String.valueOf(favoriteItem.getId())).navigation();
        });
        adapter.setASelectListener(isSelected -> {
            toggleSelectedAll = isSelected;
            binding.cbFootprintAllSelect.setChecked(isSelected);
            binding.btnFootprintCommit.setText(getResources().getString(R.string.str_del_count, adapter.getSelectedCount()));
        });
        return adapter;
    }

    @Override
    protected void prePareRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this_);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void afterRecycleView() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCanBack(true);
        setTitleName(getString(R.string.personal_my_favorites_title));

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
            SelectDialog.show(this_, getResources().getString(R.string.warming_tip), "是否删除收藏？", (dialog, which) -> {
                personalViewModel.delMyFavorites(adapter.getSelectedIds(), (code, msg) -> {
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
        myFavoriteViewModel = new MyFavoriteViewModel();
        setListViewModel(myFavoriteViewModel);
        if(swipeRefresh != null) swipeRefresh.setRefreshing(true);
        if(adapter != null) adapter.setLoadMoreShown(false);
        if(pagingHelper != null) pagingHelper.onRefresh();
    }

    private void exitEditState() {
        Looper.myQueue().addIdleHandler(() -> {
            toggleFootprintManager = !toggleFootprintManager;
            toggleSelectedAll = !toggleFootprintManager;
            binding.footprintSelectLayout.setVisibility(toggleFootprintManager ? View.VISIBLE : View.GONE);
            adapter.showItemCheckSate(toggleFootprintManager); //退出编辑模式
            if(!toggleSelectedAll) { //退出全选模式
                adapter.selectedAll(false);
                binding.cbFootprintAllSelect.setChecked(false);
            }
            return false;
        });
    }

    @Override
    protected void onRefreshLoadingShown() {
        if(adapter != null) adapter.loadMoreComplete();
    }

    @Override
    protected void onDataResult(Object o) {
        if(swipeRefresh != null) swipeRefresh.setRefreshing(false);
        if(o instanceof RefreshListModel) {
            RefreshListModel<FavoriteData.FavoriteItem> refreshListModel = (RefreshListModel) o;
            List<MultiHeaderEntity> footprintItems = new ArrayList<>();
            for (FavoriteData.FavoriteItem footprintInfo : refreshListModel.list) {
                footprintItems.add(new MyFavoritesItem(Long.valueOf(footprintInfo.getGoodsId()),
                        footprintInfo.getGoodsName(), footprintInfo.getShopPrice(), footprintInfo.getOriginalImg()));
            }
            if (refreshListModel != null) {
                if(adapter != null) adapter.setLoadMoreShown(refreshListModel.list.size() > 0); //展示加载更多(崩溃关键点)
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
        adapter.setEmptyView(R.layout.layout_empty_my_favorite, (ViewGroup) recyclerView.getParent());
    }
}
