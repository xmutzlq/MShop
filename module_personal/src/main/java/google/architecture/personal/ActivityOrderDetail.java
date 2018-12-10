package google.architecture.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.king.android.res.config.ARouterPath;
import com.kongzue.dialog.v2.MessageDialog;
import com.kongzue.dialog.v2.SelectDialog;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.params.OrderGoodsData;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.util.ToastUtils;
import google.architecture.common.viewmodel.MyOrderOperateViewModel;
import google.architecture.coremodel.MultiItemTypeHelper;
import google.architecture.coremodel.data.MyOrderOper;
import google.architecture.coremodel.data.OrderDetailData;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.personal.adapter.order.OrderDetailAdapter;
import google.architecture.personal.adapter.order.OrderDetailDataResultHelper;
import google.architecture.personal.databinding.ActivityOrderDetailBinding;

/**
 * @author lq.zeng
 * @date 2018/9/28
 */
@Route(path = ARouterPath.PersonalShoppingOrderDetailAty)
public class ActivityOrderDetail extends BaseActivity<ActivityOrderDetailBinding> {

    private OrderDetailAdapter adapter;
    private OrderDetailDataResultHelper orderDetailDataResultHelper;

    private MyOrderOperateViewModel myOrderViewModel;
    private OrderDetailData orderDetailData;
    private String orderId;

    @Override
    protected int getLayout() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanBack(true);
        setTitleName(getString(R.string.personal_order_detail_title));

        if(getIntent() != null) {
            orderId = getIntent().getStringExtra(CommKeyUtil.EXTRA_KEY);
        }
        orderDetailDataResultHelper = new OrderDetailDataResultHelper();
        adapter = new OrderDetailAdapter(orderDetailDataResultHelper.getMultiItemEntityList());
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            if(MultiItemTypeHelper.TYPE_ORDER_BUSINESSES_GOODS == adapter1.getItemViewType(position)) {
                OrderGoodsData orderGoodsData = (OrderGoodsData)adapter.getData();
                ARouter.getInstance().build(ARouterPath.DetailAty).withString(CommEvent.KEY_EXTRA_VALUE, orderGoodsData.getGoods_id()).navigation();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this_);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        binding.orderDetailRv.setLayoutManager(linearLayoutManager);
        binding.orderDetailRv.setHasFixedSize(true);

        myOrderViewModel = new MyOrderOperateViewModel();
        addRunStatusChangeCallBack(myOrderViewModel);
        myOrderViewModel.getMyOrderDetail(orderId);
    }

    @Override
    protected void onDataResult(Object o) {
        orderDetailData = (OrderDetailData) o;

        for (MyOrderOper myOrderOper : orderDetailData.getButton_list()) {
            TextView textView = (TextView) LayoutInflater.from(this_).inflate(R.layout.layout_my_order_operate_item, null);
            textView.setTag(myOrderOper.getBt_flag());
            textView.setText(myOrderOper.getBt_name());
            textView.setOnClickListener(v -> handleOrder((Integer) v.getTag(), orderDetailData));
            binding.orderOperateFlowLayout.addView(textView);
        }

        orderDetailDataResultHelper.getOrderAdapterData(orderDetailData, multiItemEntities -> {
            orderDetailDataResultHelper.getMultiItemEntityList().clear();
            orderDetailDataResultHelper.getMultiItemEntityList().addAll(multiItemEntities);
            binding.orderDetailRv.setAdapter(adapter);
            adapter.expandAll();
        });
    }

    private void operateOrder(String type) {
        switch (Integer.valueOf(type)) {
            case MyOrderOper.FLAG_CANCEL:
                ListView listView = new ListView(this_);
                ArrayAdapter<String> adapter = new ArrayAdapter(this_, R.layout.layout_dialog_textview_dark, orderDetailData.getCancel_reason_list());
                listView.setAdapter(adapter);
                MessageDialog messageDialog = MessageDialog.show(this_, this_.getString(R.string.order_cancel_dialog_title), "", "取消", (dialog, which) -> {})
                        .setCustomView(listView);
                listView.setOnItemClickListener((parent1, view1, position, id) -> {
                    messageDialog.doDismiss();
                    if(myOrderViewModel != null) myOrderViewModel.cancelOrder(orderId, orderDetailData.getCancel_reason_list().get(position), (code, msg) -> {
                        if(code == 0) { //取消成功

                        }
                    });
                });
                break;
            case MyOrderOper.FLAG_DEL:
                myOrderViewModel.deleteOrder(orderId, (code, msg) -> {
                    if(code == 0) { //删除成功

                    }
                });
                break;
            case MyOrderOper.FLAG_CONFIRM:
                myOrderViewModel.confirmReceipt(orderId, (code, msg) -> {
                    if(code == 0) { //收货成功

                    }
                });
                break;
            case MyOrderOper.FLAG_SEND:
                myOrderViewModel.remindingSend(orderId, null);
                break;
        }
    }

    private void handleOrder(int type, OrderDetailData data) {
        switch (type) {
            case MyOrderOper.FLAG_CANCEL:
                if(data.getCancel_reason_list() != null && data.getCancel_reason_list().size() != 0) { //无原因
                    ListView listView = new ListView(this_);
                    ArrayAdapter<String> adapter = new ArrayAdapter(this_, R.layout.layout_dialog_textview_dark, data.getCancel_reason_list());
                    listView.setAdapter(adapter);
                    MessageDialog messageDialog = MessageDialog.show(this_, this_.getString(R.string.order_cancel_dialog_title), "", "取消", (dialog, which) -> {})
                            .setCustomView(listView);
                    listView.setOnItemClickListener((parent, view, position, id) -> {
                        messageDialog.doDismiss();
                        if(myOrderViewModel != null) myOrderViewModel.cancelOrder(orderId, orderDetailData.getCancel_reason_list().get(position), (code, msg) -> {
                            if(code == 0) { //取消成功

                            }
                        });
                    });
                } else {
                    SelectDialog.show(this_, null, this_.getString(R.string.order_cancel_dialog_title), (dialog, which) -> {
                        if(myOrderViewModel != null) myOrderViewModel.cancelOrder(orderId, "", (code, msg) -> {
                            if(code == 0) { //取消成功

                            }
                        });
                    });
                }
                break;
            case MyOrderOper.FLAG_COMMENT:
                ARouter.getInstance().build(ARouterPath.PersonalShoppingCommentDetailAty).withString(CommKeyUtil.EXTRA_KEY, data.getOrder_id()).navigation(this_);
                break;
            case MyOrderOper.FLAG_DEL:
                SelectDialog.show(this_, this_.getString(R.string.order_delete_dialog_title), null, (dialog, which) -> {
                    if(myOrderViewModel != null) myOrderViewModel.deleteOrder(orderId, (code, msg) -> {
                        if(code == 0) { //删除成功

                        }
                    });
                });
                break;
            case MyOrderOper.FLAG_LOGISTICAL:
                ToastUtils.showShortToast("查看物流");
                break;
            case MyOrderOper.FLAG_PAY:
                ARouter.getInstance().build(ARouterPath.OrderPayActy)
                        .withString(CommKeyUtil.EXTRA_KEY, data.getOrder_no())
                        .withString(CommKeyUtil.EXTRA_KEY_2, data.getOrder_id()).navigation();
                break;
            case MyOrderOper.FLAG_SEND:
                if(myOrderViewModel != null) myOrderViewModel.remindingSend(orderId, null);
                break;
            case MyOrderOper.FLAG_CONFIRM:
                SelectDialog.show(this_, this_.getString(R.string.order_confirm_receipt_title), null, (dialog, which) -> {
                    if(myOrderViewModel != null) myOrderViewModel.confirmReceipt(orderId, (code, msg) -> {
                        if(code == 0) { //收货成功

                        }
                    });
                });
                break;
        }
    }
}
