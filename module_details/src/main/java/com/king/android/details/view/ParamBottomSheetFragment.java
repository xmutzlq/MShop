package com.king.android.details.view;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.king.android.details.R;
import com.king.android.details.adapter.DetailParamListAdapter;

import java.util.ArrayList;
import java.util.List;

import google.architecture.coremodel.data.xlj.goodsdetail.Attrs;

/**
 * @author lq.zeng
 * @date 2018/12/25
 */

public class ParamBottomSheetFragment extends ServerBottomSheetFragment {

    private List<Attrs> attrs;

    public void setData(List<Attrs> params) {
        attrs = new ArrayList<>();
        attrs.clear();
        attrs.addAll(params);
    }

    @Override
    public void initView() {
        ImageView closeIv = rootView.findViewById(R.id.server_close_iv);
        closeIv.setOnClickListener(v -> {dismiss();});
        TextView title = rootView.findViewById(R.id.server_title_tv);
        title.setText(getTitle());
        ListView listView = rootView.findViewById(R.id.server_lv);
        DetailParamListAdapter adapter = new DetailParamListAdapter(getContext(), attrs);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected String getTitle() {
        return "商品参数";
    }
}
