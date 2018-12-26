package google.architecture.category;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.apkfuns.logutils.LogUtils;
import com.king.android.res.config.ARouterPath;
import com.king.android.res.view.HomeSearchScrollController;

import java.util.List;

import google.architecture.category.adapter.CategoryLeftItemDecoration;
import google.architecture.category.adapter.CategoryTitleAdapter;
import google.architecture.common.base.BaseFragment;
import google.architecture.common.util.FragmentUtils;
import google.architecture.common.util.ScreenUtils;
import google.architecture.common.util.Utils;
import google.architecture.common.viewmodel.CategoryViewModel;
import google.architecture.coremodel.data.OpDiscoverCates;
import google.architecture.coremodel.data.OpDiscoverIndexResult;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lq.zeng
 * @date 2018/6/5
 */
@Route(path = ARouterPath.CategoryFgt)
public class FragmentCategory extends BaseFragment{

    private String sex = "1";

    private RecyclerView mCatesTiltsRV;
    private ProgressBar mCategoryPb;
    private CategoryViewModel categoryViewModel;
    private int currentPosition;

    private FragmentCategoryRight fragmentCategoryRight;

    public static FragmentCategory newInstance() {
        return new FragmentCategory();
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        getToolbarHelper().getAppBarLayout().setVisibility(View.GONE);
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCategoryPb = (ProgressBar) findViewById(view, R.id.category_progress);

        //搜索栏
        HomeSearchScrollController searchScrollAdapter = new HomeSearchScrollController(Utils.getContext());
        searchScrollAdapter.bindRollView((View) findViewById(view, R.id.category_top));
        searchScrollAdapter.onScrolled(null, 0, searchScrollAdapter.getMaxHeight());

        //左边列表
        mCatesTiltsRV = (RecyclerView) findViewById(view, R.id.category_left_rv);
        mCatesTiltsRV.addItemDecoration(new CategoryLeftItemDecoration());

        ViewGroup leftBtn = (ViewGroup) findViewById(view, R.id.category_left_tab_left_content);
        ViewGroup rightBtn = (ViewGroup) findViewById(view, R.id.category_left_tab_right_content);
        View leftFlagView = (View)findViewById(view, R.id.category_left_tab_left_flag);
        View rightFlagView = (View)findViewById(view, R.id.category_left_tab_right_flag);
        TextView leftTabTv = (TextView) findViewById(view, R.id.category_left_tab_left_tv);
        TextView rightTabTv = (TextView) findViewById(view, R.id.category_left_tab_right_tv);
        leftBtn.setOnClickListener(view1 -> {
            sex = "1";
            leftFlagView.setVisibility(View.VISIBLE);
            rightFlagView.setVisibility(View.INVISIBLE);
            leftTabTv.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
            rightTabTv.setTextColor(ContextCompat.getColor(mContext, R.color.color_9b9b9b));
            loadCategoryData(sex);
        });
        rightBtn.setOnClickListener(view1 -> {
            sex = "2";
            leftFlagView.setVisibility(View.INVISIBLE);
            rightFlagView.setVisibility(View.VISIBLE);
            leftTabTv.setTextColor(ContextCompat.getColor(mContext, R.color.color_9b9b9b));
            rightTabTv.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
            loadCategoryData(sex);
        });
    }

    @Override
    public void onFragmentFirstVisible() {
        loadCategoryData(sex);
        //loadData();
    }

    private void loadCategoryData(String sex) {
        LogUtils.tag("zlq").e("loadCategoryData");
        categoryViewModel = new CategoryViewModel();
        addRunStatusChangeCallBack(categoryViewModel);
        categoryViewModel.getCategoryLeft(sex);
    }

    @Override
    protected void onDataResult(Object o) {
        List<OpDiscoverCates> result = (List<OpDiscoverCates>)o;
        if(result != null) {
            CategoryTitleAdapter mAdapter = new CategoryTitleAdapter(R.layout.item_category_title, result);
            mAdapter.setOnItemClickListener((adapter1, view1, position) -> {
                if(currentPosition == position) return;
                currentPosition = position;
                mAdapter.setSelectPos(position);
                fragmentCategoryRight.refreshData(position, sex);
            });

            //左边列表
            LinearLayoutManager catesTiltsLM = new LinearLayoutManager(Utils.getContext());
            mCatesTiltsRV.setLayoutManager(catesTiltsLM);
            mCatesTiltsRV.setAdapter(mAdapter);

            ViewGroup.LayoutParams vglp = mCatesTiltsRV.getLayoutParams();
            vglp.width = (int) (ScreenUtils.getScreenWidth() * (0.25));
            mAdapter.setSelectPos(currentPosition);

            if(fragmentCategoryRight == null) {
                Looper.myQueue().addIdleHandler(() -> {
                    //右边列表
                    fragmentCategoryRight = FragmentCategoryRight.newInstance(result);
                    FragmentUtils.replaceFragment(getChildFragmentManager(), R.id.category_right_fl, fragmentCategoryRight, true);
                    return false;
                });
            } else {
                fragmentCategoryRight.refreshData(currentPosition, sex);
            }
        }
    }

    //假数据用
    private void loadData() {
        Observable.create((ObservableEmitter<OpDiscoverIndexResult> e) -> {
            OpDiscoverIndexResult result = CatesCache.getCatesData(Utils.getContext());
            e.onNext(result);
            e.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<OpDiscoverIndexResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCategoryPb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(OpDiscoverIndexResult opDiscoverIndexResult) {
                CategoryTitleAdapter mAdapter = new CategoryTitleAdapter(R.layout.item_category_title, opDiscoverIndexResult.getDiscoverList());
                mAdapter.setOnItemClickListener((adapter1, view1, position) -> {
                    mAdapter.setSelectPos(position);
                    FragmentCategoryRight fragmentCategoryRight = (FragmentCategoryRight)FragmentUtils.findFragment(getChildFragmentManager(), FragmentCategoryRight.class);
                    fragmentCategoryRight.refreshData(position, sex);
                });
                LinearLayoutManager catesTiltsLM = new LinearLayoutManager(Utils.getContext());
                mCatesTiltsRV.setLayoutManager(catesTiltsLM);
                mCatesTiltsRV.setAdapter(mAdapter);
                ViewGroup.LayoutParams vglp = mCatesTiltsRV.getLayoutParams();
                vglp.width = (int) (ScreenUtils.getScreenWidth() * (0.25));
                mAdapter.setSelectPos(0);

                FragmentUtils.addFragment(getChildFragmentManager(), FragmentCategoryRight.newInstance(opDiscoverIndexResult.getDiscoverList()),
                        R.id.category_right_fl, false, true);
            }

            @Override
            public void onError(Throwable e) {
                mCategoryPb.setVisibility(View.GONE);
            }

            @Override
            public void onComplete() {
                mCategoryPb.setVisibility(View.GONE);
            }
        });
    }
}
