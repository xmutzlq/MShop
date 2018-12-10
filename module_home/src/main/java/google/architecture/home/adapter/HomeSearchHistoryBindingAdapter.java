package google.architecture.home.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.util.DiffUtil;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import google.architecture.coremodel.datamodel.db.entity.SearchInfoEntity;
import google.architecture.home.BR;
import google.architecture.home.R;

/**
 * @author lq.zeng
 * @date 2018/5/22
 */

public class HomeSearchHistoryBindingAdapter extends BaseQuickAdapter<SearchInfoEntity, HomeSearchHistoryBindingAdapter.SearchViewHolder> {

    public HomeSearchHistoryBindingAdapter(int layoutResId, List<SearchInfoEntity> data) {
        super(layoutResId, data);
    }

    public void setSearchHistoryList(List<SearchInfoEntity> list){
        List<SearchInfoEntity> tempSearchs = new ArrayList<>();
        tempSearchs.addAll(list);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(mData, tempSearchs), true);
        diffResult.dispatchUpdatesTo(this);
        this.mData = tempSearchs;
    }

    @Override
    protected void convert(SearchViewHolder helper, SearchInfoEntity item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.searchHistoryData, item);
        binding.executePendingBindings();
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        if (binding == null) {
            return super.getItemView(layoutResId, parent);
        }
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return view;
    }

    public static class DiffCallBack extends DiffUtil.Callback {
        private List<SearchInfoEntity> mOldDatas, mNewDatas;

        public DiffCallBack(List<SearchInfoEntity> mOldDatas, List<SearchInfoEntity> mNewDatas) {
            this.mOldDatas = mOldDatas;
            this.mNewDatas = mNewDatas;
        }

        @Override
        public int getOldListSize() {
            return mOldDatas.size();
        }

        @Override
        public int getNewListSize() {
            return mNewDatas.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            SearchInfoEntity oldData = mOldDatas.get(oldItemPosition);
            SearchInfoEntity newData = mNewDatas.get(newItemPosition);
            return oldData.keyWord.equalsIgnoreCase(newData.keyWord);
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            SearchInfoEntity oldData = mOldDatas.get(oldItemPosition);
            SearchInfoEntity newData = mNewDatas.get(newItemPosition);
            return oldData.keyWord.equalsIgnoreCase(newData.keyWord);
        }
    }

    public static class SearchViewHolder extends BaseViewHolder {

        public SearchViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) getConvertView().getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }
}

