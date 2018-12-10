package com.king.android.order;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.king.android.order.adapter.OrderAdapter;
import com.king.android.order.adapter.OrderItemDecoration;
import com.king.android.order.databinding.ActivityOrderBinding;
import com.king.android.res.config.ARouterPath;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.ViewManager;
import google.architecture.common.params.ConfirmOrderParams;
import google.architecture.common.util.AppCompat;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.util.NetworkUtils;
import google.architecture.common.viewmodel.OrderViewModel;
import google.architecture.common.widget.span.Spans;
import google.architecture.coremodel.MultiItemTypeHelper;
import google.architecture.coremodel.data.AdressListData;
import google.architecture.coremodel.data.OrderData;
import google.architecture.coremodel.data.OrderResultData;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.coremodel.util.TextUtil;

@Route(path = ARouterPath.OrderMainActy)
public class ActivityOrder extends BaseActivity<ActivityOrderBinding> {

    private OrderViewModel orderViewModel;
    private OrderAdapter orderAdapter;
    private OrderDataResultHelper orderDataResultHelper;
    private GridLayoutManager gridLayoutManager = new GridLayoutManager(this_, 1);

    private ConfirmOrderParams confirmOrderParams;
    private String addressId;
    private View headView;

    @Override
    protected int getLayout() {
        return R.layout.activity_order;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setCanBack(true);
        setTitleName(getString(R.string.order_confirm));

        AppCompat.setBackgroundPressed(this_, binding.btnOrderCommit, R.color.themePrimaryDark);

        //提交订单
        binding.btnOrderCommit.setOnClickListener(v -> {
            if(TextUtil.isEmpty(addressId)) {
                ARouter.getInstance().build(ARouterPath.OrderAddressChoiceActy).navigation();
            } else {
                String leftStr = "";
                List<OrderDataResultHelper.LeftParams> leftList = new ArrayList<>();
                for(MultiItemEntity multiItemEntity : orderAdapter.getData()) {
                    if(MultiItemTypeHelper.TYPE_ORDER_LEFT == multiItemEntity.getItemType()) {
                        OrderDataResultHelper.LeftParams leftParams = new OrderDataResultHelper.LeftParams();
                        OrderLeftData orderLeftData = (OrderLeftData)multiItemEntity;
                        leftParams.supplier_id = orderLeftData.supplierId;
                        leftParams.user_note = leftStr = NetworkUtils.getEncodeParam(orderLeftData.leftStr);
                        leftList.add(leftParams);
                    }
                }

                OrderDataResultHelper.OrderParams orderParams = new OrderDataResultHelper.OrderParams();
                orderParams.addressId = addressId;

                if(confirmOrderParams != null) {
                    orderParams.noteJson = leftStr;
                    orderViewModel.submitOrder("goods", confirmOrderParams.goodsId, confirmOrderParams.itemsId, confirmOrderParams.goodsNum,
                            orderParams.addressId, orderParams.noteJson, t -> {
                        OrderResultData orderResultData = (OrderResultData) t;
                        ARouter.getInstance().build(ARouterPath.OrderPayActy)
                                .withString(CommKeyUtil.EXTRA_KEY, orderResultData.getOrder_no()).navigation();
                    });
                } else {
                    Gson gson = new Gson();
                    String leftJson = gson.toJson(leftList);
                    LogUtils.tag("zlq").e("leftJson = " + leftJson);
                    orderParams.noteJson = leftJson;
                    orderViewModel.submitOrderByCart(orderParams.addressId, orderParams.noteJson, t -> {
                        OrderResultData orderResultData = (OrderResultData) t;
                        ARouter.getInstance().build(ARouterPath.OrderPayActy)
                                .withString(CommKeyUtil.EXTRA_KEY, orderResultData.getOrder_no()).navigation();
                        ViewManager.getInstance().finishActivity();
                    });
                }
            }
        });

        orderDataResultHelper = new OrderDataResultHelper();
        orderAdapter = new OrderAdapter(orderDataResultHelper.getMultiItemEntityList());
        headView = LayoutInflater.from(this_).inflate(R.layout.layout_order_item_consignee, null);
        headView.findViewById(R.id.order_consignee_edit).setOnClickListener(v -> {
            ARouter.getInstance().build(ARouterPath.OrderAddressChoiceActy)
                    .withString(CommKeyUtil.EXTRA_KEY, addressId).navigation();
        });
        orderAdapter.addHeaderView(headView);
        orderAdapter.bindToRecyclerView(binding.orderRv);

        //reLoad
        orderViewModel = new OrderViewModel();
        addRunStatusChangeCallBack(orderViewModel);

        //Load
        confirmOrderParams = getIntent().getParcelableExtra(CommKeyUtil.EXTRA_KEY);
        if(confirmOrderParams != null) {
            orderViewModel.directOrder(confirmOrderParams.goodsId, confirmOrderParams.itemsId, confirmOrderParams.goodsNum);
        } else {
            orderViewModel.getCartSettlement("cart");
        }
    }

    @Override
    protected void onDataResult(Object o) {
        OrderData orderData = (OrderData)o;
        orderDataResultHelper.getOrderAdapterData(orderData.getOrder_list(), multiItemEntities -> {
            if(multiItemEntities != null && multiItemEntities.size() > 0) {
                orderDataResultHelper.getMultiItemEntityList().clear();
                orderDataResultHelper.getMultiItemEntityList().addAll(multiItemEntities);
                binding.orderRv.setAdapter(orderAdapter);
                gridLayoutManager.setSmoothScrollbarEnabled(true);
                gridLayoutManager.setAutoMeasureEnabled(true);
                binding.orderRv.addItemDecoration(new OrderItemDecoration());
                binding.orderRv.setLayoutManager(gridLayoutManager);
                binding.orderRv.setHasFixedSize(true);
                binding.orderRv.setNestedScrollingEnabled(false);
                orderAdapter.expandAll();
            }
        });

        if(orderData != null && orderData.getAddress_item() != null) {
            addressId = orderData.getAddress_item().getAddress_id();
            updateHeadData(orderData.getAddress_item().getConsignee(),
                    orderData.getAddress_item().getMobile(),
                    orderData.getAddress_item().getAddress());
        }

        String allNum = getResources().getString(R.string.order_str_all_goods_num, orderData.getAll_goods_num());
        binding.orderAllGoodsNumTv.setText(Spans.builder().text(allNum)
                .newSpanPart(1, 1 + String.valueOf(orderData.getAll_goods_num()).length(),
                        new ForegroundColorSpan(ContextCompat.getColor(this_, R.color.color_FD644B))).build());
        String allMoney = getResources().getString(R.string.order_str_all_goods_money) + "¥" + orderData.getAll_goods_price();
        binding.orderAllGoodsPriceTv.setText(Spans.builder().text(allMoney)
                .newSpanPart(3, allMoney.length(), new ForegroundColorSpan(ContextCompat.getColor(this_, R.color.color_FD644B))).build());
    }

    private void updateHeadData(String consignee, String mobile, String address) {
        View emptyView = headView.findViewById(R.id.order_consignee_empty_tv);
        View contentView = headView.findViewById(R.id.order_consignee_info_lay);
        TextView consigneeTv = headView.findViewById(R.id.order_consignee_tv);
        TextView consigneePhoneTv = headView.findViewById(R.id.order_consignee_phone_tv);
        TextView consigneeAddressTv = headView.findViewById(R.id.order_consignee_address_tv);
        if(!TextUtil.isEmpty(consignee) && !TextUtil.isEmpty(mobile) && !TextUtil.isEmpty(address)) {
            consigneeTv.setText(consignee);
            consigneePhoneTv.setText(mobile);
            consigneeAddressTv.setText(address);
            emptyView.setVisibility(View.GONE);
            contentView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.VISIBLE);
            contentView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onMessageEvent(CommEvent event) {
        if(CommEvent.MSG_TYPE_NOTIFY_UPDATE.equals(event.msgType)) {
            Bundle bundle = event.bundle;
            AdressListData.AddressListItem addressListItem = bundle.getParcelable(CommKeyUtil.EXTRA_KEY);
            if(addressListItem != null) {
                addressId = addressListItem.getAddress_id();
                updateHeadData(addressListItem.getConsignee(), addressListItem.getMobile(), addressListItem.getAddress());
            }
        } else if(CommEvent.MSG_TYPE_CLOSE_PAGE.equals(event.msgType)) {
            ViewManager.getInstance().finishActivity();
        }
    }
}
