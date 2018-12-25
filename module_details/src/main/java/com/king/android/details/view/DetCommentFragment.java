package com.king.android.details.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.king.android.details.R;
import com.king.android.details.adapter.DetailCommentsAdapter;
import com.king.android.details.databinding.FragmentDetCommentBinding;
import com.king.android.res.config.ARouterPath;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseFragment;
import google.architecture.common.viewmodel.DetailCommentViewModel;
import google.architecture.coremodel.data.DetailCommentPageInfo;
import google.architecture.coremodel.data.DetailCommentsInfo;
import google.architecture.coremodel.datamodel.http.event.CommEvent;

/**
 * @author lq.zeng
 * @date 2018/6/8
 */

@Route(path = ARouterPath.DetailCommentFgt)
public class DetCommentFragment extends BaseFragment<FragmentDetCommentBinding> {

    private DetailCommentViewModel detailCommentViewModel;
    private DetailCommentsAdapter detailCommentsAdapter;

    private RecyclerView recyclerView;
    private SmartRefreshLayout commentRefreshLayout;

    private List<DetailCommentsInfo> detailCommentsInfos;
    private String goodsId;

    @Override
    protected boolean isStatusBarTransparent() {
        return false;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_det_comment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = findViewById(view, R.id.detail_comment_rec);
        commentRefreshLayout = findViewById(view, R.id.detail_comment_refreshLayout);
        commentRefreshLayout.setEnableOverScrollBounce(false);
        commentRefreshLayout.setOnMultiPurposeListener(listener);

        detailCommentsInfos = new ArrayList<>();
        detailCommentsAdapter = new DetailCommentsAdapter(R.layout.detail_item_comment, detailCommentsInfos);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(detailCommentsAdapter);
    }

    @Override
    public void onFragmentFirstVisible() {
        if(null != getActivity() && getActivity().getIntent() != null) {
            goodsId = getActivity().getIntent().getStringExtra(CommEvent.KEY_EXTRA_VALUE);
            loadCommentData(goodsId, "1");
        }
    }

    @Override
    protected void onEmptyDisplaying() {
        detailCommentsInfos.clear();
        detailCommentsAdapter.notifyDataSetChanged();
        detailCommentsAdapter.setEmptyView(R.layout.layout_empty_comment, (ViewGroup) recyclerView.getParent());
    }

    @Override
    protected void onDataResult(Object o) {
        DetailCommentPageInfo info = (DetailCommentPageInfo)o;
        detailCommentsInfos.clear();
        detailCommentsInfos.addAll(info.getComment_list());
        detailCommentsAdapter.notifyDataSetChanged();
    }

    private void loadCommentData(String goodsId, String page) {
        detailCommentViewModel = new DetailCommentViewModel();
        addRunStatusChangeCallBack(detailCommentViewModel);
        detailCommentViewModel.getDetailCommentInfo(goodsId, page, "10");
    }

    SimpleMultiPurposeListener listener = new SimpleMultiPurposeListener() {
        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishLoadMore(0);
        }
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(0);
            loadCommentData(goodsId, "1");
        }
        @Override
        public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {

        }
    };
}
