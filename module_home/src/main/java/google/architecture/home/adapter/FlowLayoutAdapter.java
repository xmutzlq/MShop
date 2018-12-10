package google.architecture.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import google.architecture.common.util.AppCompat;
import google.architecture.common.widget.adapter.CommFlowLayoutAdapter;
import google.architecture.home.R;

/**
 * @author lq.zeng
 * @date 2018/5/23
 *
 * //预先设置选中
mAdapter.setSelectedList(1,3,5,7,8,9);
//获得所有选中的pos集合
flowLayout.getSelectedList();
 */

public class FlowLayoutAdapter extends CommFlowLayoutAdapter<String> {

    public FlowLayoutAdapter(Context context, List<String> datas) {
        super(context, datas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_search_hot_item;
    }

    @Override
    protected void setView(View view, String string) {
        final TextView tv = view.findViewById(R.id.home_search_hot_key_tv);
        tv.setText(string);
        AppCompat.setBackgroundPressed(mContext, tv, R.color.gray_f6, 0.9f);
    }
}
