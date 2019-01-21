package google.architecture.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.king.android.res.config.ARouterPath;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.BaseFragment;
import google.architecture.common.viewmodel.xlj.FootScanViewModel;
import google.architecture.coremodel.data.xlj.FullBodyDate;
import google.architecture.personal.databinding.ActivityFullBodyScanBinding;

@Route(path = ARouterPath.FootBodyScanAty)
public class ActivityFullBodyScan extends BaseActivity<ActivityFullBodyScanBinding> {

    private List<BaseFragment> mFragments = new ArrayList<>();
    private FootScanViewModel mFootScanViewModel;

    private BodyFragment mBodyFragment;
    private FootFragment mFootFragment;

    @Override
    protected boolean isStatusBarTransparent() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_full_body_scan;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCanBack(true);

        setTitleName("我的数据");

        CommonNavigator navigator = new CommonNavigator(getContext());
        navigator.setAdjustMode(true);
        FullBodyScanNavAdapter navAdapter = new FullBodyScanNavAdapter();
        navAdapter.setHeadTitleClickListener(new FullBodyScanNavAdapter.IHeadTitleClickListener() {
            @Override
            public void onHeadTitleClickListener(View view, int index) {
                binding.personalOrderVp.setCurrentItem(index);
            }
        });
        navigator.setAdapter(navAdapter);
        binding.personalOrderIndicator.setNavigator(navigator);

        mBodyFragment = BodyFragment.getNewInstance();
        mFootFragment = FootFragment.getNewInstance();
        mFragments.add(mBodyFragment);
        mFragments.add(mFootFragment);

        FullBodyScanFragmentAdapter fragmentAdapter = new FullBodyScanFragmentAdapter(getSupportFragmentManager(), mFragments);
        binding.personalOrderVp.setOffscreenPageLimit(fragmentAdapter.getCount());
        ViewPagerHelper.bind(binding.personalOrderIndicator, binding.personalOrderVp);

        binding.personalOrderVp.setAdapter(fragmentAdapter);
        binding.personalOrderVp.setCurrentItem(0);

        mFootScanViewModel = new FootScanViewModel();
        addRunStatusChangeCallBack(mFootScanViewModel);
        mFootScanViewModel.getTryData();
    }

    @Override
    protected void onDataResult(Object o) {
        super.onDataResult(o);
        FullBodyDate data = (FullBodyDate)o;
        Bundle bundle = new Bundle();
        bundle.putSerializable("fullBodyDate",data);
        mBodyFragment.setArguments(bundle);
        mFootFragment.setArguments(bundle);
        mBodyFragment.refreshUI();
        mFootFragment.refreshUI();
    }
}
