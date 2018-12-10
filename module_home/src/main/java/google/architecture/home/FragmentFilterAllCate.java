package google.architecture.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseFragment;
import google.architecture.common.widget.expand.ExpandableStickyListHeadersListView;
import google.architecture.coremodel.data.MoreFilterCatesData;
import google.architecture.coremodel.data.SearchResult;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.coremodel.util.PreferencesUtils;
import google.architecture.home.adapter.MoreFilterAllCateListAdapter;
import google.architecture.home.cache.AllCatesData;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lq.zeng
 * @date 2018/5/31
 */

public class FragmentFilterAllCate extends BaseFragment {

    private ExpandableStickyListHeadersListView allCates;
    private MoreFilterAllCateListAdapter moreFilterAllCateAdapter;
    private View headerViewRoot;
    private ProgressBar mProgressBar;

    private List<MoreFilterCatesData> moreFilterCatesData;
    private int position;

    public static FragmentFilterAllCate newInstance(ArrayList<SearchResult.FilterCat> cats) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(CommEvent.KEY_EXTRA_VALUE, cats);
        FragmentFilterAllCate filterAllCate = new FragmentFilterAllCate();
        filterAllCate.setArguments(bundle);
        return filterAllCate;
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter_all_cate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<SearchResult.FilterCat> catsData = getArguments().getParcelableArrayList(CommEvent.KEY_EXTRA_VALUE);
        moreFilterCatesData = AllCatesData.getAllFilterCates(catsData);

        position = PreferencesUtils.getInt(mContext, PreferencesUtils.PREFERENCE_KEY_FILTER_ALL_CATE_POSITION, -1);
        mProgressBar = (ProgressBar) findViewById(view, R.id.progressBar);
        ((View)findViewById(view, R.id.filter_cate_close)).setOnClickListener(v -> {
            EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_CLOSE_ALL_CATE));
        });
        makeHeaderView();
        //粘性头部用MoreFilterAllCateAdapter
        moreFilterAllCateAdapter = new MoreFilterAllCateListAdapter(mContext, new ArrayList<>());
        allCates = (ExpandableStickyListHeadersListView) findViewById(view, R.id.filter_all_cate_lv);
        allCates.setAreHeadersSticky(true);//是否支持header
        allCates.addHeaderView(headerViewRoot);
        allCates.setAdapter(moreFilterAllCateAdapter);
        allCates.setOnHeaderClickListener((l, header, itemPosition, headerId, currentlySticky) -> {
            if (allCates.isHeaderCollapsed(headerId)) {
                refreshHeaderRightArrowState(headerId, true);
                moreFilterAllCateAdapter.onHandleHeader(header, true);
            } else {
                refreshHeaderRightArrowState(headerId, false);
                moreFilterAllCateAdapter.onHandleHeader(header, false);
            }
        });
        allCates.setOnItemClickListener((parent, view1, position, id) -> {
            Bundle bundle = new Bundle();
            if(position == 0) {
                PreferencesUtils.putInt(mContext, PreferencesUtils.PREFERENCE_KEY_FILTER_ALL_CATE_POSITION, -1);
            } else {
                int realPosition = position - allCates.getHeaderViewsCount();
                moreFilterAllCateAdapter.onHandleItem(view1, realPosition, true);
                bundle.putString(CommEvent.KEY_EXTRA_VALUE, moreFilterAllCateAdapter.getItem(realPosition).cateChildName);
            }
            EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_CLOSE_ALL_CATE, bundle));
        });
        loadAllCatsData();
//        loadData();
    }

    private void refreshHeaderRightArrowState(long headerId, boolean isExpand) {
        moreFilterAllCateAdapter.refreshDataSet(position, headerId, isExpand);
        if(isExpand) {
            allCates.expand(headerId);
        } else {
            allCates.collapse(headerId);
        }
    }

    private void makeHeaderView() {
        headerViewRoot = getLayoutInflater().inflate(R.layout.filter_item_all_cate_header, null);
        headerViewRoot.setVisibility(View.INVISIBLE);
        ((TextView) headerViewRoot.findViewById(R.id.filter_all_cate_item_label)).setText(R.string.filter_title_all_kinds);
        if(position != - 1) {
            headerViewRoot.findViewById(R.id.filter_all_cate_item_arrow_iv).setVisibility(View.GONE);
        } else {
            headerViewRoot.findViewById(R.id.filter_all_cate_item_arrow_iv).setVisibility(View.VISIBLE);
            ((ImageView) headerViewRoot.findViewById(R.id.filter_all_cate_item_arrow_iv)).setImageResource(R.mipmap.ic_filter_cate_checked);
        }
        headerViewRoot.findViewById(R.id.filter_all_cate_item_line).setVisibility(View.GONE);
    }

    private void loadAllCatsData() {
        Observable.create((ObservableEmitter<List<MoreFilterCatesData>> e) -> {
            for (MoreFilterCatesData data : moreFilterCatesData) {
                data.isChecked = false;
                data.isExpand = false;
            }
            e.onNext(moreFilterCatesData);
            e.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<MoreFilterCatesData>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(List<MoreFilterCatesData> moreFilterCatesData) {
                List<MoreFilterCatesData> temp = moreFilterCatesData;
                moreFilterAllCateAdapter.appendToTopList(temp);
                headerViewRoot.setVisibility(View.VISIBLE);
                //闭合所有Item
                allCates.expand(0);
                //打开对应选中的Item
                if(position != - 1 && moreFilterCatesData.size() > position) {
                    refreshHeaderRightArrowState(moreFilterAllCateAdapter.getHeaderId(position), true);
                    //滚动到指定位置
                    allCates.setSelection(position);
                }
            }

            @Override
            public void onError(Throwable e) {
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onComplete() {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void loadData() {
        Observable.create((ObservableEmitter<List<MoreFilterCatesData>> e) -> {
            if(AllCatesData.mDatas == null || AllCatesData.mDatas.size() == 0) {
                AllCatesData.makeCatesData();
            }
            for (MoreFilterCatesData data : AllCatesData.mDatas) {
                data.isChecked = false;
                data.isExpand = false;
            }
            e.onNext(AllCatesData.mDatas);
            e.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<MoreFilterCatesData>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(List<MoreFilterCatesData> moreFilterCatesData) {
                List<MoreFilterCatesData> temp = moreFilterCatesData;
                moreFilterAllCateAdapter.appendToTopList(temp);
                headerViewRoot.setVisibility(View.VISIBLE);
                //闭合所有Item
                allCates.collapseAll();
                //打开对应选中的Item
                if(position != - 1 && moreFilterCatesData.size() > position) {
                    refreshHeaderRightArrowState(moreFilterAllCateAdapter.getHeaderId(position), true);
                    //滚动到指定位置
                    allCates.setSelection(position);
                }
            }

            @Override
            public void onError(Throwable e) {
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onComplete() {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }
}
