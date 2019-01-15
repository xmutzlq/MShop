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

import java.util.ArrayList;
import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/12/25
 */

public class DetailServerListAdapter extends BaseAdapter {

    private Context mContext;

    private List<ServerParams> serverList = new ArrayList<>();

    private static final int imgIds[] = {R.mipmap.zheng, R.mipmap.serviceqitiantuihuan, R.mipmap.you, R.mipmap.ti/*, R.mipmap.qian*/};
    private static final String titles[] = {"正品保障", "七天无理由退换", "全场满300免邮", "支持线下店铺自提"/*, "支持货到付款"*/};
    private static final String des[] = {"专业精选，正品护航",
            "消费者在满足7天无理由退换货申请条件的前提下，可以提出“7天无理由退换货”的申请",
            "全场订单满300元即可免邮费",
            "同城有线下店铺的，可支持到店提货"//,
            /*"货到付款，安心便捷"*/
    };

    public DetailServerListAdapter(Context context) {
        mContext = context;
        for (int i = 0; i < imgIds.length; i++) {
            ServerParams serverParams = new ServerParams();
            serverParams.imgId = imgIds[i];
            serverParams.title = titles[i];
            serverParams.des = des[i];
            serverList.add(serverParams);
        }
    }

    @Override
    public int getCount() {
        return serverList.size();
    }

    @Override
    public Object getItem(int position) {
        return serverList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.xlj_layout_server_list_item, null);
            holder.nameTv = convertView.findViewById(R.id.server_list_item_title);
            holder.desTv = convertView.findViewById(R.id.server_list_item_des);
            holder.nameIv = convertView.findViewById(R.id.server_list_item_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ServerParams filterName = serverList.get(position);
        holder.nameTv.setText(filterName.title);
        holder.desTv.setText(filterName.des);
        holder.nameIv.setImageResource(filterName.imgId);
        return convertView;
    }

    public static class ViewHolder {
        TextView nameTv;
        TextView desTv;
        ImageView nameIv;
    }
}
