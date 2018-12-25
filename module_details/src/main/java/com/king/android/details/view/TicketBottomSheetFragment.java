package com.king.android.details.view;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.king.android.details.R;
import com.king.android.details.adapter.DetailParamListAdapter;
import com.king.android.details.adapter.DetailServerListAdapter;
import com.king.android.details.adapter.DetailTicketListAdapter;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.widget.BaseBottomSheetFrag;
import google.architecture.coremodel.data.xlj.goodsdetail.Attrs;

/**
 * @author lq.zeng
 * @date 2018/12/25
 */

public class TicketBottomSheetFragment extends BaseBottomSheetFrag {

    public TicketBottomSheetFragment() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.xlj_layout_ticket_list;
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
        ListView listView = rootView.findViewById(R.id.ticket_lv);
        DetailTicketListAdapter adapter = new DetailTicketListAdapter(getContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    protected String getTitle() {
        return "促销";
    }
}

