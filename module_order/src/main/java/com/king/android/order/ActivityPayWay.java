package com.king.android.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.king.android.order.adapter.OrderPayWayAdapter;
import com.king.android.order.databinding.ActivityPayWayBinding;
import com.king.android.res.config.ARouterPath;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.pay.ali.PayResult;
import google.architecture.common.util.AppCompat;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.util.ToastUtils;
import google.architecture.common.viewmodel.PayViewModel;
import google.architecture.coremodel.data.PayOrderData;
import google.architecture.coremodel.data.PayWayData;
import google.architecture.coremodel.data.PayWayItemData;
import google.architecture.coremodel.datamodel.http.event.CommEvent;

/**
 * @author lq.zeng
 * @date 2018/9/11
 */

@Route(path = ARouterPath.OrderPayActy)
public class ActivityPayWay extends BaseActivity<ActivityPayWayBinding> {

    private PayViewModel payViewModel;
    private PayWayData payWayData;
    private OrderPayWayAdapter orderPayWayAdapter;
    private List<PayWayItemData> payWayItemDataList;

    private String orderId = "", orderNo = "";

    @Override
    protected int getLayout() {
        return R.layout.activity_pay_way;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanBack(true);
        setTitleName(getString(R.string.order_pay));

        payViewModel = new PayViewModel();
        AppCompat.setBackgroundPressed(this_, binding.btnOrderCommit, R.color.themePrimaryDark);
        binding.btnOrderCommit.setOnClickListener(v -> {
            if(payWayData == null) return;
            PayWayItemData payWayItemData = payWayData.getPay_way().get(orderPayWayAdapter.getPosition());
            if(payWayItemData != null) {
                payViewModel.payOrder(payWayData.getOrder_no(), payWayData.getOrder_id(), payWayItemData.getCode(), t -> {
                    PayOrderData payOrderData = (PayOrderData) t;
                    payViewModel.aliPay(this_, payOrderData.getOrder_info());
                });
            }
        });

        if(getIntent() != null) {
            Intent intent = getIntent();
            orderNo = intent.getStringExtra(CommKeyUtil.EXTRA_KEY);
            orderId = intent.getStringExtra(CommKeyUtil.EXTRA_KEY_2);
        }

        payWayItemDataList = new ArrayList<>();
        orderPayWayAdapter = new OrderPayWayAdapter(R.layout.layout_pay_way_item, payWayItemDataList);
        orderPayWayAdapter.setOnItemClickListener((adapter, view, position) -> orderPayWayAdapter.setChecked(position));
        orderPayWayAdapter.bindToRecyclerView(binding.payWayRv);
        binding.payWayRv.setAdapter(orderPayWayAdapter);
        binding.payWayRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.payWayRv.setLayoutManager(new LinearLayoutManager(this_));
        binding.payWayRv.setHasFixedSize(true);

        Looper.myQueue().addIdleHandler(() -> {
            if(payWayData != null
                    && payWayData.getPay_way() != null && payWayData.getPay_way().size() > 0) {
                refreshUI();
            } else {
                addRunStatusChangeCallBack(payViewModel);
                payViewModel.getPayWay(orderNo, orderId, t1 -> {
                    payWayData = (PayWayData) t1;
                    refreshUI();
                });
            }
            return false;
        });
    }

    private void refreshUI() {
        binding.btnOrderCommit.setText(binding.btnOrderCommit.getText().toString() + " ¥" + payWayData.getSum_order_money());
        if(payWayData.getPay_way() != null && payWayData.getPay_way().size() > 0) {
            payWayData.getPay_way().get(0).isChecked = true;
            payWayItemDataList.addAll(payWayData.getPay_way());
            orderPayWayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDataResult(Object o) {
        PayResult payResult = (PayResult) o;
        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
        String resultStatus = payResult.getResultStatus();
        // 判断resultStatus 为9000则代表支付成功
        if (TextUtils.equals(resultStatus, "9000")) {
            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
            ToastUtils.showShortToast("支付成功");
//            EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_CLOSE_PAGE));
        } else {
            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
            ToastUtils.showShortToast("支付失败");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_UPDATE_CART_MAIN));
    }
}
