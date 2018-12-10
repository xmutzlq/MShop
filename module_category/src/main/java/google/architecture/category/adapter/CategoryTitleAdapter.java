package google.architecture.category.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import google.architecture.category.R;
import google.architecture.category.viewholder.CategoryLeftTitleViewHolder;
import google.architecture.common.util.Utils;
import google.architecture.coremodel.data.OpDiscoverCates;
import google.architecture.coremodel.util.CollectionUtil;

/**
 * @author lq.zeng
 * @date 2018/6/5
 */

public class CategoryTitleAdapter extends BaseQuickAdapter<OpDiscoverCates, CategoryLeftTitleViewHolder>{

    // 选中
    public static final String STATUS_SELECT = "SELECT";
    // 默认
    public static final String STATUS_NOMAL = "NOMAL";
    // 当前索引
    private int mSelectPosition;

    public CategoryTitleAdapter(int layoutResId, @Nullable List<OpDiscoverCates> data) {
        super(layoutResId, data);
    }

    public void setSelectPos(int position) {
        if (position >= 0 && position < getItemCount() && mSelectPosition != position) {
            notifyItemRangeChanged(mSelectPosition, 1, STATUS_NOMAL);
            notifyItemRangeChanged(position, 1, STATUS_SELECT);
            mSelectPosition = position;
        }
    }

    @Override
    public void onBindViewHolder(CategoryLeftTitleViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        boolean isSelected = position == mSelectPosition;
        refreshSelectState(holder, isSelected);
    }

    @Override
    public void onBindViewHolder(CategoryLeftTitleViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if (CollectionUtil.isEmpty(payloads)) return;
        String payload = (String) payloads.get(0);
        boolean isSelected = STATUS_SELECT.equalsIgnoreCase(payload);
        refreshSelectState(holder, isSelected);
    }

    @Override
    protected void convert(CategoryLeftTitleViewHolder helper, OpDiscoverCates item) {
        helper.setText(R.id.category_title_tv, item.getTitle());
    }

    private void refreshSelectState(BaseViewHolder holder, boolean isSelected) {
        if (isSelected) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(Utils.getContext(), R.color.white));
            holder.setGone(R.id.category_title_iv, true);
            holder.setTextColor(R.id.category_title_tv, ContextCompat.getColor(Utils.getContext(), R.color.color_f44336));
            ((TextView)holder.getView(R.id.category_title_tv)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(Utils.getContext(), R.color.color_f6f6f6));
            holder.setGone(R.id.category_title_iv, false);
            holder.setTextColor(R.id.category_title_tv, ContextCompat.getColor(Utils.getContext(), R.color.color_999999));
            ((TextView)holder.getView(R.id.category_title_tv)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
    }
}
