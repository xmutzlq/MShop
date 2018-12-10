package google.architecture.personal.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import google.architecture.common.widget.adapter.CommFlowLayoutAdapter;
import google.architecture.coremodel.data.MyOrderOper;
import google.architecture.personal.R;

/**
 * @author lq.zeng
 * @date 2018/10/16
 */

public class OrderOperateFlowLayoutAdapter extends CommFlowLayoutAdapter<MyOrderOper> {

    public OrderOperateFlowLayoutAdapter(Context context, List<MyOrderOper> datas) {
        super(context, datas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_my_order_operate_item;
    }

    @Override
    protected void setView(View view, MyOrderOper myOrderOper) {
        TextView textView = view.findViewById(R.id.btn_my_order_operate);
        textView.setTag(String.valueOf(myOrderOper.getBt_flag()));
        textView.setText(myOrderOper.getBt_name());
    }
}
