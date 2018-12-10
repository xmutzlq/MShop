package google.architecture.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.king.android.res.config.ARouterPath;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.BaseFragment;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.personal.adapter.PersonalFragmentAdapter;
import google.architecture.personal.adapter.order.MyOrderHeadAdapter;
import google.architecture.personal.databinding.ActivityMyOrderBinding;

/**
 * @author lq.zeng
 * @date 2018/9/13
 */
@Route(path = ARouterPath.PersonalShoppingOrderAty)
public class ActivityMyOrder extends BaseActivity<ActivityMyOrderBinding> {

    private List<BaseFragment> mFragments = new ArrayList<>();

    private int index;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCanBack(true);
        setTitleName(getString(R.string.personal_my_order_title));

        Intent intent = getIntent();
        if(intent != null) index = intent.getIntExtra(CommKeyUtil.EXTRA_KEY, - 1);

        CommonNavigator navigator = new CommonNavigator(getContext());
        navigator.setAdjustMode(true);
        MyOrderHeadAdapter adapter = new MyOrderHeadAdapter(getContext(), getResources().getStringArray(R.array.personal_my_order_titles));
        adapter.setHeadTitleClickListener((view1, index) -> binding.personalOrderVp.setCurrentItem(index));
        navigator.setAdapter(adapter);
        binding.personalOrderIndicator.setNavigator(navigator);

        mFragments.add((BaseFragment) ARouter.getInstance().build(ARouterPath.PersonalAllOrderListFgt).navigation());
        mFragments.add((BaseFragment) ARouter.getInstance().build(ARouterPath.PersonalWaitingPayListFgt).navigation());
        mFragments.add((BaseFragment) ARouter.getInstance().build(ARouterPath.PersonalWaitingReceiptListFgt).navigation());
        mFragments.add((BaseFragment) ARouter.getInstance().build(ARouterPath.PersonalFinishListFgt).navigation());
        mFragments.add((BaseFragment) ARouter.getInstance().build(ARouterPath.PersonalCancelListFgt).navigation());

        PersonalFragmentAdapter adapter1 = new PersonalFragmentAdapter(getSupportFragmentManager(), mFragments);
        binding.personalOrderVp.setOffscreenPageLimit(adapter.getCount());
        ViewPagerHelper.bind(binding.personalOrderIndicator, binding.personalOrderVp);

        binding.personalOrderVp.setAdapter(adapter1);
        binding.personalOrderVp.setCurrentItem(index);
    }
}
