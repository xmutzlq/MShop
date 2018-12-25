package com.king.android.details.view;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.king.android.details.R;
import com.king.android.details.adapter.DetailServerListAdapter;

import google.architecture.common.widget.BaseBottomSheetFrag;

/**
 * @author lq.zeng
 * @date 2018/12/25
 */

public class ServerBottomSheetFragment extends BaseBottomSheetFrag {

    public ServerBottomSheetFragment() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.xlj_layout_server;
    }

    @Override
    public boolean isNeedHeight() {
        return false;
    }

    @Override
    public void initView() {
        ImageView closeIv = rootView.findViewById(R.id.server_close_iv);
        closeIv.setOnClickListener(v -> {dismiss();});
        TextView title = rootView.findViewById(R.id.server_title_tv);
        title.setText(getTitle());
        ListView listView = rootView.findViewById(R.id.server_lv);
        DetailServerListAdapter adapter = new DetailServerListAdapter(getContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    protected String getTitle() {
        return "基础保障";
    }
}
