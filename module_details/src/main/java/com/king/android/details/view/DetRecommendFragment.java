package com.king.android.details.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.king.android.details.ActivityDetails;
import com.king.android.details.R;
import com.king.android.details.adapter.DetailRecommendParentAdapter;
import com.king.android.res.config.ARouterPath;

import java.util.ArrayList;

import google.architecture.common.base.BaseFragment;
import google.architecture.coremodel.data.xlj.goodsdetail.Like;
import google.architecture.coremodel.util.ConvertUtils;

/**
 * @author lq.zeng
 * @date 2018/6/8
 */
@Route(path = ARouterPath.DetailRecommendFgt)
public class DetRecommendFragment extends BaseFragment{

    private RecyclerView detailCommodityRecommendRec;

    private ActivityDetails mActivityDetails;
    private FrameLayout emptyView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context != null && context instanceof ActivityDetails) {
            mActivityDetails = (ActivityDetails)context;
        }
    }

    @Override
    protected boolean isStatusBarTransparent() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_det_recommend;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_det_recommend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emptyView = (FrameLayout) findViewById(view, R.id.detail_empty_recommend);
        detailCommodityRecommendRec = (RecyclerView)findViewById(view, R.id.detail_commodity_recommend_rec);
    }

    @Override
    public void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        if(mActivityDetails == null) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            if(mActivityDetails.getLikes() == null || mActivityDetails.getLikes().size() == 0) {
                emptyView.setVisibility(View.VISIBLE);
            } else {
                emptyView.setVisibility(View.GONE);
                LinearLayoutManager detailLinearLayoutManager = new LinearLayoutManager(mContext,
                        LinearLayoutManager.HORIZONTAL, false);

                detailCommodityRecommendRec.setLayoutManager(detailLinearLayoutManager);
                detailCommodityRecommendRec.setNestedScrollingEnabled(false);
                detailCommodityRecommendRec.setHasFixedSize(true);
                java.util.List<Like> pageDetailRecommendInfos = new ArrayList<>(mActivityDetails.getLikes());
                //总记录数
                int rows = pageDetailRecommendInfos.size();
                //每页显示的记录数
                int pageCount = 6;
                //页数
                int pageSize = (rows - 1) / pageCount + 1;
                if(pageSize == 0) pageSize = 1;
                java.util.List<java.util.List<Like>> pages = ConvertUtils.averageAssign(pageDetailRecommendInfos, pageSize);
                DetailRecommendParentAdapter detailRecommendAdapter = new DetailRecommendParentAdapter(R.layout.layout_comm_recyclerview_auto_size, pages);
                detailCommodityRecommendRec.setAdapter(detailRecommendAdapter);
            }
        }
    }
}
