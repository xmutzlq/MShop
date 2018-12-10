package com.king.android.details.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.king.android.details.R;

import java.util.List;

import google.architecture.common.util.DimensionsUtil;
import google.architecture.coremodel.data.DetailRecommendInfo;

/**
 * @author lq.zeng
 * @date 2018/6/29
 */

public class DetailRecommendParentAdapter extends BaseQuickAdapter<List<DetailRecommendInfo>, BaseViewHolder> {

    public DetailRecommendParentAdapter(int layoutResId, @Nullable List<List<DetailRecommendInfo>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, List<DetailRecommendInfo> item) {
        RecyclerView recyclerView = helper.getView(R.id.comm_recyclerView_auto_size);
        recyclerView.addItemDecoration(new DetailDividerItemMarginDecoration(mContext, DimensionsUtil.DIPToPX(8)));
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(new DetailRecommendAdapter(R.layout.recommend_item, item));
    }
}
