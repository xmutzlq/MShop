package google.architecture.home.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.widget.ImageView;

import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import google.architecture.common.widget.TagFlowLayout;
import google.architecture.coremodel.data.MoreFilterData;
import google.architecture.coremodel.data.MoreFilterTagData;
import google.architecture.home.R;

/**
 * @author lq.zeng
 * @date 2018/5/28
 */

public class MoreFilterAdapter extends BaseQuickAdapter<MoreFilterData, BaseViewHolder> {

    public static final int DEFAULT_SHOWN_TAG_NUM = 9;

    private ArrayMap<Integer, Set<Integer>> selectedMap = new ArrayMap<>();
    private ArrayMap<Integer, List<MoreFilterTagData>> allKinds = new ArrayMap<>();
    private ArrayMap<String, List<String>> params = new ArrayMap<>();
    private ArrayMap<Integer, Integer> prePosition = new ArrayMap<>(); //单选时使用
    private int mTagsRowCount = DEFAULT_SHOWN_TAG_NUM;

    public MoreFilterAdapter(int layoutResId, @Nullable List<MoreFilterData> data) {
        super(layoutResId, data);
        initAllKinds(data);
    }

    public String getParams() {
        StringBuilder stringBuilder = new StringBuilder();
        int a = 0;
        for (Map.Entry<String, List<String>> entry : params.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i ++) {
                stringBuilder.append(entry.getValue().get(i));
                if(i != entry.getValue().size() - 1) {
                    stringBuilder.append("_");
                }
            }
            if(a != params.size() - 1) {
                stringBuilder.append("-");
            }
            a += 1;
        }
        return stringBuilder.toString();
    }
    public ArrayMap<Integer, List<MoreFilterTagData>> getAllKindsTagData() {
        return allKinds;
    }

    private void initAllKinds(List<MoreFilterData> data) {
        for (int i = 0; i < data.size(); i++) {
            List<MoreFilterTagData> tagData = new ArrayList<>();
            for (int j = 0; j < data.get(i).tagData.size(); j++){
                tagData.add(new MoreFilterTagData());
            }
            allKinds.put(i, tagData);
        }
    }

    /**重置**/
    public void reSet() {
        params.clear();
        selectedMap.clear();
        allKinds.clear();
        initAllKinds(getData());
        for (int i = 0; i < getData().size(); i++) {
            MoreFilterData data = getData().get(i);
            data.isAllShown = false;
            data.itemTitleRightName = "";
            for (int j = 0; j < data.tagData.size(); j++) {
                MoreFilterTagData data1 = data.tagData.get(j);
                data1.isShown = (j >= mTagsRowCount) ? data.isAllShown : true;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, MoreFilterData item) {
        helper.setText(R.id.filter_more_title_tv, item.itemTilteName);
        MoreFilterTagAdapter moreFilterTagAdapter = new MoreFilterTagAdapter(mContext, item.tagData);
        TagFlowLayout tagFlowLayout = helper.getView(R.id.filter_more_layout);
//        mTagsRowCount = tagFlowLayout.getRowItemCount(); //一行显示多少数量
        if(item.maxChooseNum > 0) tagFlowLayout.setMaxSelectCount(item.maxChooseNum); //设置最大可选数
        tagFlowLayout.setOnTagClickListener((view, position, parent) -> {
            if(!params.containsKey(item.searchParamKey))params.put(item.searchParamKey, new ArrayList<>());
            boolean haveTag = allKinds.get(helper.getAdapterPosition()).contains(item.tagData.get(position));
            if(item.maxChooseNum == 1) { //单选
                params.get(item.searchParamKey).clear();
                if(prePosition.size() - 1 >= helper.getAdapterPosition()) {
                    allKinds.get(helper.getAdapterPosition()).remove((int)prePosition.get(helper.getAdapterPosition()));
                }
                prePosition.put(helper.getAdapterPosition(), position);
            } else { //多选
                allKinds.get(helper.getAdapterPosition()).remove(position);
            }
            if(haveTag) {
                allKinds.get(helper.getAdapterPosition()).add(position, new MoreFilterTagData());
                params.get(item.searchParamKey).remove(item.tagData.get(position).tagUrlId);
            } else {
                allKinds.get(helper.getAdapterPosition()).add(position, item.tagData.get(position));
                params.get(item.searchParamKey).add(item.tagData.get(position).tagUrlId);
            }
            List<MoreFilterTagData> tempTags = allKinds.get(helper.getAdapterPosition());
            StringBuilder sb = new StringBuilder();
            if(tempTags.size() > 0) {
                boolean isFirst = false;
                for (int i = 0; i < tempTags.size(); i++) {
                    if(!TextUtils.isEmpty(tempTags.get(i).tagName)) {
                        if(!isFirst) {
                            isFirst = true;
                            sb.append(tempTags.get(i).tagName);
                        } else {
                            sb.append("," + tempTags.get(i).tagName);
                        }
                    }
                }
            }
            item.itemTitleRightName = TextUtils.isEmpty(sb.toString()) ? mContext.getResources().getString(R.string.filter_title_all_kinds_) : sb.toString();
            helper.setText(R.id.filter_more_title_righ_tv, item.itemTitleRightName);
            return true;
        });
        tagFlowLayout.setOnSelectListener(selectPosSet -> {
            selectedMap.put(helper.getAdapterPosition(), selectPosSet);
        });
        tagFlowLayout.setAdapter(moreFilterTagAdapter);
        //重置状态
        moreFilterTagAdapter.setSelectedList(selectedMap.get(helper.getAdapterPosition()));
        tagFlowLayout.relayoutToAlign();

        helper.setGone(R.id.filter_just_a_space, helper.getAdapterPosition() == getItemCount() - 1);

        if(getData ().get(helper.getAdapterPosition()).tagData.size() > mTagsRowCount) {
            helper.setGone(R.id.filter_arrow_iv, true);
            helper.getView(R.id.filter_more_title_layout).setOnClickListener(v -> {
                item.isAllShown = !item.isAllShown;
                for (int i = 0; i < getData().get(helper.getAdapterPosition()).tagData.size(); i++) {
                    MoreFilterTagData tagData = getData().get(helper.getAdapterPosition()).tagData.get(i);
                    tagData.isShown = (i >= mTagsRowCount) ? item.isAllShown : true; //前3个Tag不做显示隐藏操作
                }
//                moreFilterTagAdapter.notifyDataChanged();
                notifyItemChanged(helper.getAdapterPosition());
            });
        } else {
            helper.setGone(R.id.filter_arrow_iv, false);
        }

        helper.setText(R.id.filter_more_title_righ_tv, TextUtils.isEmpty(item.itemTitleRightName) ?
                mContext.getResources().getString(R.string.filter_title_all_kinds_) : item.itemTitleRightName);

        if(item.isAllShown) {
            ((ImageView)helper.getView(R.id.filter_arrow_iv)).setImageResource(R.mipmap.ic_filter_arrow_up_normal);
        } else {
            ((ImageView)helper.getView(R.id.filter_arrow_iv)).setImageResource(R.mipmap.ic_filter_arrow_down_normal);
        }
    }
}
