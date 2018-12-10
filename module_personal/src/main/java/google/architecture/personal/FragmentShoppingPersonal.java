package google.architecture.personal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils;
import com.king.android.res.config.ARouterPath;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseFragment;
import google.architecture.common.statusbar.StatusbarUtils;
import google.architecture.common.viewmodel.PersonalViewModel;
import google.architecture.personal.adapter.PersonalFragmentAdapter;
import google.architecture.personal.adapter.PersonalHeadAdapter;
import google.architecture.personal.databinding.FragmentShoppingPersonalBinding;

/**
 * @author lq.zeng
 * @date 2018/6/11
 */
public class FragmentShoppingPersonal extends BaseFragment<FragmentShoppingPersonalBinding> implements AppBarLayout.OnOffsetChangedListener {

    private int height = 0;// 滑动开始变色的高,真实项目中此高度是由头部view高度决定

    public static FragmentShoppingPersonal newInstance() {
        return new FragmentShoppingPersonal();
    }

    private List<BaseFragment> mFragments = new ArrayList<>();

    private PersonalViewModel personalViewModel;

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        getToolbarHelper().getAppBarLayout().setVisibility(View.GONE);
    }

    @Override
    protected boolean isStatusBarTransparent() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_shopping_personal;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        height = getResources().getDimensionPixelSize(com.king.android.res.R.dimen.size_160dp) / 2;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.height = StatusbarUtils.getStatusBarHeight(mContext);
        binding.personalStatusBar.setLayoutParams(lp);

        ViewGroup.LayoutParams lp2 = binding.personalToolBar.getLayoutParams();
        lp2.height = lp2.height + StatusbarUtils.getStatusBarHeight(mContext);
        binding.personalToolBar.setLayoutParams(lp2);

        binding.personalRefreshLayout.setEnableOverScrollBounce(false);
        binding.personalRefreshLayout.setOnMultiPurposeListener(listener);

        CommonNavigator navigator = new CommonNavigator(getContext());
        PersonalHeadAdapter adapter = new PersonalHeadAdapter(getContext(), getResources().getStringArray(R.array.personal_head_titles));
        adapter.setHeadTitleClickListener((view1, index) -> {binding.personalViewPager.setCurrentItem(index);});
        navigator.setAdapter(adapter);
        binding.personalHeadIndicator.setNavigator(navigator);

        for (int i = 0; i < adapter.getCount(); i ++) {
            mFragments.add( (BaseFragment) ARouter.getInstance().build(ARouterPath.PersonalLikeFgt).navigation());
        }

        PersonalFragmentAdapter adapter1 = new PersonalFragmentAdapter(getChildFragmentManager(), mFragments);
        binding.personalViewPager.setOffscreenPageLimit(adapter.getCount());
        binding.personalViewPager.setAdapter(adapter1);

        ViewPagerHelper.bind(binding.personalHeadIndicator, binding.personalViewPager);

//        binding.personalNestedScrollView.setFillViewport(true);
//        binding.personalNestedScrollView.setOnScrollChangeListener((MyNestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
//            if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())
//                    || scrollY == 0) { //滚动到底或顶
//                binding.personalNestedScrollView.setOutSizeInterrupt(true);
//            }
//        });

        personalViewModel = new PersonalViewModel();
    }

    SimpleMultiPurposeListener listener = new SimpleMultiPurposeListener() {
        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishLoadMore(0);
        }
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(0);
            LogUtils.tag("zlq").e("getShoppingCategory");
            if(personalViewModel != null) {
                personalViewModel.getShoppingCategory();
            }
        }
        @Override
        public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        binding.personalAppBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.personalAppBarLayout.removeOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int deltaY = Math.abs(verticalOffset);
        if (deltaY <= 0) {   //设置标题的背景颜色
            binding.userHeadHeadIv.setVisibility(View.GONE);
            binding.userHeadHeadTv.setVisibility(View.GONE);
            binding.personalHeadRl.setBackgroundColor(Color.argb(0, 0, 0, 0));
        } else if (deltaY > 0 && deltaY <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) deltaY / height;
            float alpha = (255 * scale);
            binding.userHeadHeadIv.setVisibility(View.GONE);
            binding.userHeadHeadTv.setVisibility(View.GONE);
            binding.personalHeadRl.setBackgroundColor(Color.argb((int) alpha, 255, 200, 55));
        } else {
            binding.userHeadHeadIv.setVisibility(View.VISIBLE);
            binding.userHeadHeadTv.setVisibility(View.VISIBLE);
            binding.personalHeadRl.setBackgroundColor(Color.argb(255, 255, 200, 55));
        }
    }
}
