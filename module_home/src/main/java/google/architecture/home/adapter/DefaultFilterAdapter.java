package google.architecture.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import google.architecture.home.R;

/**
 * @author lq.zeng
 * @date 2018/12/24
 */

public class DefaultFilterAdapter extends BaseAdapter {
    private List<String> filterDefaultDataList = new ArrayList<>();
    private Context mContext;
    private int selectedPosition = 0;

    public DefaultFilterAdapter(Context context) {
        mContext = context;
        filterDefaultDataList.add("默认");
        filterDefaultDataList.add("价格降序");
        filterDefaultDataList.add("价格升序");
    }

    public void selectItem(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return filterDefaultDataList.size();
    }

    @Override
    public String getItem(int position) {
        return filterDefaultDataList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.filter_item_default, null);
            holder.nameTv = convertView.findViewById(R.id.filter_name_tv);
            holder.nameIv = convertView.findViewById(R.id.filter_name_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String filterName = filterDefaultDataList.get(position);
        holder.nameTv.setText(filterName);
        if(selectedPosition == position) {
            holder.nameTv.setSelected(true);
            holder.nameIv.setVisibility(View.VISIBLE);
        } else {
            holder.nameTv.setSelected(false);
            holder.nameIv.setVisibility(View.GONE);
        }
        return convertView;
    }

    public static class ViewHolder {
        TextView nameTv;
        ImageView nameIv;
    }
}
