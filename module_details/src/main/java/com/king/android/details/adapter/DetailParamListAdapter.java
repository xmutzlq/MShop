package com.king.android.details.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.king.android.details.R;
import com.king.android.details.util.Params;

import java.util.ArrayList;
import java.util.List;

import google.architecture.coremodel.data.xlj.goodsdetail.Attrs;

/**
 * @author lq.zeng
 * @date 2018/12/25
 */

public class DetailParamListAdapter extends BaseAdapter {

    private Context mContext;

    private List<Attrs> paramList = new ArrayList<>();

    public DetailParamListAdapter(Context context, List<Attrs> params) {
        mContext = context;
        paramList.clear();
        paramList.addAll(params);
    }

    @Override
    public int getCount() {
        return paramList.size();
    }

    @Override
    public Object getItem(int position) {
        return paramList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.xlj_layout_param_list_item, null);
            holder.nameTv = convertView.findViewById(R.id.param_list_item_title);
            holder.desTv = convertView.findViewById(R.id.param_list_item_des);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Attrs filterName = paramList.get(position);
        holder.nameTv.setText(filterName.getAttrName());
        holder.desTv.setText(filterName.getAttrVal());
        return convertView;
    }

    public static class ViewHolder {
        TextView nameTv;
        TextView desTv;
    }
}
