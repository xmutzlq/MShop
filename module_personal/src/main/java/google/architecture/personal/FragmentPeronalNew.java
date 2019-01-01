package google.architecture.personal;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.king.android.res.config.ARouterPath;
import com.qmuiteam.qmui.layout.QMUIFrameLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseFragment;
import google.architecture.common.viewmodel.PersonalViewNewModel;
import google.architecture.coremodel.data.xlj.personal.LikeGoods;
import google.architecture.coremodel.data.xlj.personal.UserInfos;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.personal.adapter.HeaderFooterAdapterWrapper;
import google.architecture.personal.adapter.PersonalNewAdapter;
import google.architecture.personal.databinding.FragmentPersonalNewBinding;

@Route(path = ARouterPath.PersonalShoppingFgt)
public class FragmentPeronalNew extends BaseFragment<FragmentPersonalNewBinding> {
    private PersonalNewAdapter mAdapter;
    private List<LikeGoods> mList;
    private PersonalViewNewModel viewModel;

    @Override
    protected int getLayout() {
        return R.layout.fragment_personal_new;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mList = new ArrayList();
        mAdapter = new PersonalNewAdapter(mList, new PersonalNewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                openGoodsDetail(mList.get(position).getGoodsId()+"");
            }
        });
        HeaderFooterAdapterWrapper adapterWrapper = new HeaderFooterAdapterWrapper(mAdapter);
        binding.personalNew.setAdapter(adapterWrapper);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position == 0){
                    return 2;
                }else{
                    return 1;
                }
            }
        });
        binding.personalNew.setLayoutManager(gridLayoutManager);
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.personal_new_header,null);
        QMUIFrameLayout qmuiFrameALayout = headerView.findViewById(R.id.shadow_a_layout);
        qmuiFrameALayout.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, 6),
                QMUIDisplayHelper.dp2px(mContext, 8), 0.5f);
        adapterWrapper.addHeaderView(headerView);

        binding.personalRefreshLayout.setEnableOverScrollBounce(false);
        binding.personalRefreshLayout.setOnMultiPurposeListener(listener);


        headerView.findViewById(R.id.btn_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterPath.PersonalSettingAty).navigation(mContext);
            }
        });

        viewModel = new PersonalViewNewModel();
        viewModel.userInfosObservableField.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                refreshData(viewModel.userInfosObservableField.get());
            }
        });
        addRunStatusChangeCallBack(viewModel);//为了现实loading界面
        viewModel.loadData();
    }

    SimpleMultiPurposeListener listener = new SimpleMultiPurposeListener() {
        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishLoadMore(0);
        }
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(0);
            //loadData();
            viewModel.loadData();
        }
        @Override
        public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {

        }
    };

    private void openGoodsDetail(String goodsIs){
        ARouter.getInstance().build(ARouterPath.DetailAty).withString(CommEvent.KEY_EXTRA_VALUE,goodsIs).navigation(mContext);
    }

    private void refreshData(UserInfos infos){
        mList.clear();
        mList.addAll(infos.getLike());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();

    }
}
