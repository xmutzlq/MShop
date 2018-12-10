package google.architecture.home.adapter;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.support.v7.util.DiffUtil;

import java.util.List;

import google.architecture.common.widget.adapter.BaseBindingAdapter;
import google.architecture.coremodel.datamodel.db.entity.SearchInfoEntity;
import google.architecture.home.R;
import google.architecture.home.databinding.HomeSearchHistoryItemBinding;

/**
 * @author lq.zeng
 * @date 2018/5/21
 */

public class HomeSearchHistoryAdapter extends BaseBindingAdapter<SearchInfoEntity, HomeSearchHistoryItemBinding> {

    public HomeSearchHistoryAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.home_search_history_item;
    }

    @Override
    protected void onBindItem(HomeSearchHistoryItemBinding binding, SearchInfoEntity item) {
        binding.setSearchHistoryData(item);
        binding.executePendingBindings();
    }

    public void setRefreshData(List<SearchInfoEntity> list) {
        if (list == null) {
            return;
        }
        items.clear();
        items.addAll(list);
        notifyDataSetChanged();
    }

    public void setSearchHistoryList(final List<SearchInfoEntity> list){
        ObservableArrayList<SearchInfoEntity> entities = new ObservableArrayList<>();
        entities.addAll(list);
        if(items == null){
            items = entities;
            notifyItemRangeInserted(0, items.size());
        }else {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return items.size();
                }

                @Override
                public int getNewListSize() {
                    return list.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    SearchInfoEntity oldData = items.get(oldItemPosition);
                    SearchInfoEntity newData = list.get(newItemPosition);
                    return oldData.keyWord.equalsIgnoreCase(newData.keyWord);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    SearchInfoEntity oldData = items.get(oldItemPosition);
                    SearchInfoEntity newData = list.get(newItemPosition);
                    return oldData.keyWord.equalsIgnoreCase(newData.keyWord);
                }
            });
            items = entities;
            diffResult.dispatchUpdatesTo(this);
        }
    }
}
