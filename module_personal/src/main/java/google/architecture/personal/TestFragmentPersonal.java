package google.architecture.personal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.king.android.res.config.ARouterPath;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseFragment;
import google.architecture.common.widget.nested.NRStickyLayout2;
import google.architecture.personal.adapter.PersonalFragmentAdapter;

/**
 * @author lq.zeng
 * @date 2018/6/14
 */

public class TestFragmentPersonal extends BaseFragment {

    public static TestFragmentPersonal newInstance() {
        return new TestFragmentPersonal();
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    private NRStickyLayout2 nrStickyLayout;
    private ViewPager mViewPager;
    private PersonalFragmentAdapter adapter1;
    private List<BaseFragment> mFragments = new ArrayList<>();

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
    public void onResume() {
        super.onResume();
        int currentItem = mViewPager.getCurrentItem();
        Fragment item = adapter1.getItem(currentItem);
        if (item instanceof PersonalLikeFragment) {
            View scrollView = ((PersonalLikeFragment) item).getScrollView();
            nrStickyLayout.setCurrentScrollableView(scrollView);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_fragment_personal_3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nrStickyLayout = (NRStickyLayout2)findViewById(view, R.id.sticky_view_layout);

        for (int i = 0; i < 3; i ++) {
            mFragments.add( (BaseFragment) ARouter.getInstance().build(ARouterPath.PersonalLikeFgt).navigation());
        }

        mViewPager = (ViewPager) findViewById(view,R.id.id_nr_stickylayout_viewpage);
        adapter1 = new PersonalFragmentAdapter(getChildFragmentManager(), mFragments);
        adapter1.setPrimaryItemListener(new PersonalFragmentAdapter.ISetPrimaryItem() {
            @Override
            public void onSetPrimaryItem() {
                setScrollView();
            }
        });
        mViewPager.setAdapter(adapter1);

        if (mFragments.get(0) instanceof PersonalLikeFragment) {
            View scrollView = ((PersonalLikeFragment) mFragments.get(0)).getScrollView();
            nrStickyLayout.setCurrentScrollableView(scrollView);
        }
    }

    private void setScrollView() {
        View currentScrollableView = nrStickyLayout.getCurrentScrollableView();
        int currentItem = mViewPager.getCurrentItem();
        Fragment item = adapter1.getItem(currentItem);
        if (item instanceof PersonalLikeFragment) {
            View scrollView = ((PersonalLikeFragment) item).getScrollView();
            if (currentScrollableView != scrollView) {
                nrStickyLayout.setCurrentScrollableView(scrollView);
            }
        }
    }
}
