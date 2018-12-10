package com.king.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.king.android.databinding.FragmentBidsBinding;
import com.king.android.res.config.ARouterPath;

import google.architecture.common.base.BaseFragment;
import google.architecture.common.viewmodel.FinancingViewModel;
import google.architecture.coremodel.data.BidsData;

/**
 * @Desc FragmentBids
 */
@Route(path = ARouterPath.ItelligentListFgt)
public class FragmentBids extends BaseFragment<FragmentBidsBinding> {

    private FinancingViewModel mFinancingViewModel;
    private BidsAdapter bidsAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_bids;
    }

    @Override
    protected void onCreateBindView() {
        ARouter.getInstance().inject(FragmentBids.this);
        bidsAdapter = new BidsAdapter(bidItemClickCallback);
        binding.setRecyclerAdapter(bidsAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFinancingViewModel = new FinancingViewModel();
//        subscribeToModel(mFinancingViewModel);
    }

    BidItemClickCallback bidItemClickCallback = new BidItemClickCallback() {
        @Override
        public void onClick(BidsData.IntelligentBidInfo fuliItem) {
            Toast.makeText(getContext(), fuliItem.id, Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 订阅数据变化来刷新UI
     * @param model
     */
    private void subscribeToModel(final FinancingViewModel model){
        //观察数据变化来刷新UI
        model.getBannerLiveData().observe(FragmentBids.this, bidsAdapter::setBidsList);
        model.initLocalBanners();
    }

    @Override
    public void onDestroy() {
        mFinancingViewModel.clear();
        super.onDestroy();
    }
}
