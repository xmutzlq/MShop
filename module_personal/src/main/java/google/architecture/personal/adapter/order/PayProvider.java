package google.architecture.personal.adapter.order;

import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.king.android.res.config.ARouterPath;
import com.kongzue.dialog.v2.MessageDialog;
import com.kongzue.dialog.v2.SelectDialog;

import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.util.ToastUtils;
import google.architecture.common.widget.FlowLayout;
import google.architecture.common.widget.adapter.BaseItemProvider;
import google.architecture.coremodel.data.MyOrderOper;
import google.architecture.personal.R;

/**
 * @author lq.zeng
 * @date 2018/9/13
 */

public class PayProvider extends BaseItemProvider<MyOrderSpc, BaseViewHolder> {

    private IOperateOrder operateOrder;

    public PayProvider(IOperateOrder operateOrder) {
        this.operateOrder = operateOrder;
    }

    @Override
    public int viewType() {
        return MyOrderAdapter.TEXT_PAY;
    }

    @Override
    public int layout() {
        return R.layout.layout_my_order_pay_provider;
    }

    @Override
    public void convert(BaseViewHolder helper, MyOrderSpc data, int position) {
        FlowLayout flowLayout = helper.getView(R.id.order_operate_flow_layout);
        flowLayout.removeAllViews();
        for (MyOrderOper myOrderOper : data.MyOrderOpers) {
            TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.layout_my_order_operate_item, null);
            textView.setTag(myOrderOper.getBt_flag());
            textView.setText(myOrderOper.getBt_name());
            textView.setOnClickListener(v -> {
                handleOrder((Integer) v.getTag(), position, data);
            });
            flowLayout.addView(textView);
        }
    }

    @Override
    public void onClick(BaseViewHolder helper, MyOrderSpc data, int position) {
    }

    @Override
    public boolean onLongClick(BaseViewHolder helper, MyOrderSpc data, int position) {
        return true;
    }

    private void handleOrder(int type, int holderPosition, MyOrderSpc data) {
        switch (type) {
            case MyOrderOper.FLAG_CANCEL:
                if(data.cancelReasonList != null && data.cancelReasonList.size() != 0) { //无原因
                    ListView listView = new ListView(mContext);
                    ArrayAdapter<String> adapter = new ArrayAdapter(mContext, R.layout.layout_dialog_textview_dark, data.cancelReasonList);
                    listView.setAdapter(adapter);
                    MessageDialog messageDialog = MessageDialog.show(mContext, mContext.getString(R.string.order_cancel_dialog_title), "", "取消", (dialog, which) -> {})
                    .setCustomView(listView);
                    listView.setOnItemClickListener((parent, view, position, id) -> {
                        messageDialog.doDismiss();
                        if(operateOrder != null) operateOrder.onOperateOrder(type, holderPosition, data.orderId, data.cancelReasonList.get(position));
                    });
                } else {
                    SelectDialog.show(mContext, null, mContext.getString(R.string.order_cancel_dialog_title), (dialog, which) -> {
                        if(operateOrder != null) operateOrder.onOperateOrder(type, holderPosition, data.orderId, null);
                    });
                }
                break;
            case MyOrderOper.FLAG_COMMENT:
                ARouter.getInstance().build(ARouterPath.PersonalShoppingCommentDetailAty).withString(CommKeyUtil.EXTRA_KEY, data.orderId).navigation(mContext);
                break;
            case MyOrderOper.FLAG_DEL:
                SelectDialog.show(mContext, mContext.getString(R.string.order_delete_dialog_title), null, (dialog, which) -> {
                    if(operateOrder != null) operateOrder.onOperateOrder(type, holderPosition, data.orderId, null);
                });
                break;
            case MyOrderOper.FLAG_LOGISTICAL:
                ToastUtils.showShortToast("查看物流");
                break;
            case MyOrderOper.FLAG_PAY:
                ARouter.getInstance().build(ARouterPath.OrderPayActy)
                        .withString(CommKeyUtil.EXTRA_KEY, data.orderNo)
                        .withString(CommKeyUtil.EXTRA_KEY_2, data.orderId).navigation();
                break;
            case MyOrderOper.FLAG_SEND:
                if(operateOrder != null) operateOrder.onOperateOrder(type, holderPosition, data.orderId, null);
                break;
            case MyOrderOper.FLAG_CONFIRM:
                SelectDialog.show(mContext, mContext.getString(R.string.order_confirm_receipt_title), null, (dialog, which) -> {
                    if(operateOrder != null) operateOrder.onOperateOrder(type, holderPosition, data.orderId, null);
                });
                break;
        }
    }

    public interface IOperateOrder {
        void onOperateOrder(int type, int position, String orderId, String reason);
    }
}
