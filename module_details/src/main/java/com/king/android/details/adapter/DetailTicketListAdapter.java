package com.king.android.details.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.king.android.details.R;
import com.king.android.details.util.ServerParams;
import com.king.android.details.util.TicketParams;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/12/25
 */

public class DetailTicketListAdapter extends BaseAdapter {

    private Context mContext;

    private List<TicketParams> ticketParams = new ArrayList<>();

    public DetailTicketListAdapter(Context context) {
        mContext = context;
        for (int i = 0; i < 1; i++) {
            TicketParams ticketParam = new TicketParams();
            ticketParam.usePrice = "10";
            ticketParam.conditionPrice = "100";
            ticketParam.startDate = "2018-12-01";
            ticketParam.endDate = "2019-12-01";
            ticketParams.add(ticketParam);
        }
    }

    @Override
    public int getCount() {
        return ticketParams.size();
    }

    @Override
    public Object getItem(int position) {
        return ticketParams.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.xlj_layout_ticket_list_item, null);
            holder.nameTv = convertView.findViewById(R.id.ticket_list_item_use);
            holder.desTv = convertView.findViewById(R.id.ticket_list_item_condition);
            holder.dateTv = convertView.findViewById(R.id.ticket_list_item_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TicketParams filterName = ticketParams.get(position);
        holder.nameTv.setText("¥" + filterName.usePrice + "店铺优惠券");
        holder.desTv.setText("满" + filterName.conditionPrice + "使用");
        holder.dateTv.setText("有效期" + filterName.startDate + "-" + filterName.endDate);
        return convertView;
    }

    public static class ViewHolder {
        TextView nameTv;
        TextView desTv;
        TextView dateTv;
    }
}
