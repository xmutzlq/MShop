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

import org.w3c.dom.Text;

import google.architecture.category.adapter.CategoryLeftItemDecoration;
import google.architecture.category.adapter.CategoryTitleAdapter;
import google.architecture.common.base.BaseFragment;
import google.architecture.common.util.FragmentUtils;
import google.architecture.common.util.ScreenUtils;
import google.architecture.common.util.Utils;
import google.architecture.common.viewmodel.CategoryViewModel;
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

    private RecyclerView mCatesTiltsRV;
    private ProgressBar mCategoryPb;
    private CategoryViewModel categoryViewModel;
    private int currentPosition;

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
            leftFlagView.setVisibility(View.VISIBLE);
            rightFlagView.setVisibility(View.INVISIBLE);
            leftTabTv.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
            rightTabTv.setTextColor(ContextCompat.getColor(mContext, R.color.color_9b9b9b));
        });
        rightBtn.setOnClickListener(view1 -> {
            leftFlagView.setVisibility(View.INVISIBLE);
            rightFlagView.setVisibility(View.VISIBLE);
            leftTabTv.setTextColor(ContextCompat.getColor(mContext, R.color.color_9b9b9b));
            rightTabTv.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        });
    }

    @Override
    public void onFragmentFirstVisible() {
        loadCategoryData();
        //loadData();
    }

    private void loadCategoryData() {
        LogUtils.tag("zlq").e("loadCategoryData");
        categoryViewModel = new CategoryViewModel();
        addRunStatusChangeCallBack(categoryViewModel);
        categoryViewModel.getShoppingCategory();
    }

    @Override
    protected void onDataResult(Object o) {
        OpDiscoverIndexResult result = (OpDiscoverIndexResult)o;
        if(result != null) {
            CategoryTitleAdapter mAdapter = new CategoryTitleAdapter(R.layout.item_category_title, result.getDiscoverList());
            mAdapter.setOnItemClickListener((adapter1, view1, position) -> {
                if(currentPosition == position) return;
                currentPosition = position;
                mAdapter.setSelectPos(position);
                FragmentCategoryRight fragmentCategoryRight = (FragmentCategoryRight)FragmentUtils.findFragment(getChildFragmentManager(), FragmentCategoryRight.class);
                fragmentCategoryRight.refreshData(position);
            });

            //左边列表
            LinearLayoutManager catesTiltsLM = new LinearLayoutManager(Utils.getContext());
            mCatesTiltsRV.setLayoutManager(catesTiltsLM);
            mCatesTiltsRV.setAdapter(mAdapter);

            ViewGroup.LayoutParams vglp = mCatesTiltsRV.getLayoutParams();
            vglp.width = (int) (ScreenUtils.getScreenWidth() * (0.25));
            mAdapter.setSelectPos(0);

            Looper.myQueue().addIdleHandler(() -> {
                //右边列表
                FragmentUtils.addFragment(getChildFragmentManager(), FragmentCategoryRight.newInstance(result.getDiscoverList()),
                        R.id.category_right_fl, false, true);
                return false;
            });
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
                    fragmentCategoryRight.refreshData(position);
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
