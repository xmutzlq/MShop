package google.architecture.home.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import google.architecture.common.widget.expand.StickyListHeadersAdapter;
import google.architecture.coremodel.data.MoreFilterCatesData;
import google.architecture.coremodel.util.PreferencesUtils;
import google.architecture.home.R;

/**
 * @author lq.zeng
 * @date 2018/5/31
 */

public class MoreFilterAllCateListAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private Context mContext;
    private List<MoreFilterCatesData> mInfos;
    private String[] sectionHeaders;
    private int[] sectionIndices;
    private Map<String, String> letterMap;

    public MoreFilterAllCateListAdapter(Context context, List<MoreFilterCatesData> infos) {
        mContext = context;
        mInfos = infos;
        letterMap = new HashMap<>();
    }

    public int getLetterPosition(String letter){
        int position = letterMap != null && !TextUtils.isEmpty(letterMap.get(letter)) ?
                Integer.valueOf(letterMap.get(letter)) : -1;
        return position;
    }

    private int[] getSectionIndices() {
        List<String> hasCompare = new ArrayList<String>();
        List<Integer> sectionIndices = new ArrayList<Integer>();
        String lastCreateDate = mInfos.get(0).id;
        sectionIndices.add(0);
        hasCompare.add(lastCreateDate);
        for (int i = 1; i < mInfos.size(); i++) {
            String createDate = mInfos.get(i).id;
            if (hasCompare.size() > 0 && !hasCompare.contains(createDate)) {
                hasCompare.add(createDate);
                sectionIndices.add(i);
            }
        }
        int[] sections = new int[sectionIndices.size()];
        for (int i = 0; i < sectionIndices.size(); i++) {
            sections[i] = sectionIndices.get(i);
        }
        return sections;
    }

    private String[] getSectionHeaders() {
        String[] sectionHeaders = new String[sectionIndices.length];
        for (int i = 0; i < sectionIndices.length; i++) {
            sectionHeaders[i] = mInfos.get(sectionIndices[i]).cateName;
            letterMap.put(sectionHeaders[i], String.valueOf(sectionIndices[i]));
        }
        return sectionHeaders;
    }

    public void appendToTopList(List<MoreFilterCatesData> list) {
        if(mInfos == null || list == null || list.size() == 0) {
            return;
        }
        this.mInfos.addAll(list);
        sectionIndices = getSectionIndices();
        sectionHeaders = getSectionHeaders();
        notifyDataSetChanged();
    }

    public void clear() {
        mInfos.clear();
        sectionIndices = null;
        sectionHeaders = null;
    }

    @Override
    public int getCount() {
        return mInfos.size();
    }

    @Override
    public MoreFilterCatesData getItem(int position) {
        return mInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.filter_item_all_cate_child, null);
            holder.contactName =  convertView.findViewById(R.id.filter_all_cate_item_child_label);
            holder.contactPhoneNum =  convertView.findViewById(R.id.filter_all_cate_item_child_arrow_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MoreFilterCatesData info = mInfos.get(position);
        holder.contactName.setText(info.cateChildName);

        if(info.isChecked) {
            holder.contactName.setTextColor(ContextCompat.getColor(mContext, R.color.color_ff593e));
            holder.contactPhoneNum.setImageResource(R.mipmap.ic_filter_cate_checked);
            holder.contactPhoneNum.setVisibility(View.VISIBLE);
        } else {
            holder.contactName.setTextColor(ContextCompat.getColor(mContext, R.color.color_666666));
            holder.contactPhoneNum.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        return new View(mContext);
    }

    public void refreshDataSet(int position, long headerId, boolean isExpand) {
        if(position != - 1 && mInfos.size() > position) mInfos.get(position).isChecked = true;
        for (MoreFilterCatesData data : mInfos) {
            if(data.id.equals(String.valueOf(headerId))) {
                data.isExpand = isExpand;
            }
        }
    }

    public void onHandleHeader(View headerView, boolean isExpand) {
        ImageView rightArrowIv = headerView.findViewById(R.id.filter_all_cate_item_arrow_iv);
        rightArrowIv.setImageResource(isExpand ? R.mipmap.ic_filter_arrow_up_normal : R.mipmap.ic_filter_arrow_down_normal);
    }

    public void onHandleItem(View itemView, int position, boolean isChecked) {
        ImageView rightChecked = itemView.findViewById(R.id.filter_all_cate_item_child_arrow_iv);
        TextView leftLabel = itemView.findViewById(R.id.filter_all_cate_item_child_label);
        leftLabel.setTextColor(isChecked ?
                ContextCompat.getColor(mContext, R.color.color_ff593e) : ContextCompat.getColor(mContext, R.color.color_666666));
        rightChecked.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        //save
        int oldPositin =  PreferencesUtils.getInt(mContext, PreferencesUtils.PREFERENCE_KEY_FILTER_ALL_CATE_POSITION);
        if(mInfos != null && mInfos.size() > position) {
            if(oldPositin != - 1) mInfos.get(oldPositin).isChecked = false;
            mInfos.get(position).isChecked = true;
        }
        PreferencesUtils.putInt(mContext, PreferencesUtils.PREFERENCE_KEY_FILTER_ALL_CATE_POSITION, position);
    }

    @Override
    public long getHeaderId(int position) {
        return Long.valueOf(mInfos.get(position).id);
    }

    private class ViewHolder {
        private TextView contactName;
        private ImageView contactPhoneNum;
    }

    private class HeaderViewHolder {
        private ViewGroup headerLL;
        private TextView headerName;
        private ImageView headerRightIv;
    }

}
