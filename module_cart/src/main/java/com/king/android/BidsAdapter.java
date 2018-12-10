package com.king.android;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.king.android.databinding.BidItemBinding;

import java.util.List;

import google.architecture.coremodel.data.BidsData;

/**
 * Created by dxx on 2017/11/10.
 */

public class BidsAdapter extends RecyclerView.Adapter<BidsAdapter.BidsViewHolder> {

    List<BidsData.IntelligentBidInfo> bidsList;
    BidItemClickCallback bidItemClickCallback;

    /**
     * 构造方法传入点击监听器
     * @param itemClickCallback
     */
    public BidsAdapter(@Nullable BidItemClickCallback itemClickCallback) {
        bidItemClickCallback = itemClickCallback;
    }

    public void setBidsList(final List<BidsData.IntelligentBidInfo> list){
        if(bidsList == null){
            bidsList = list;
            notifyItemRangeInserted(0, bidsList != null ? bidsList.size() : 0);
        } else {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return bidsList.size();
                }

                @Override
                public int getNewListSize() {
                    return list.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    BidsData.IntelligentBidInfo oldData = bidsList.get(oldItemPosition);
                    BidsData.IntelligentBidInfo newData = list.get(newItemPosition);
                    return oldData.id == newData.id;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    BidsData.IntelligentBidInfo oldData = bidsList.get(oldItemPosition);
                    BidsData.IntelligentBidInfo newData = list.get(newItemPosition);
                    return oldData.id == newData.id
                            && oldData.title == newData.title;
                }
            });
            bidsList = list;
            diffResult.dispatchUpdatesTo(this);
        }
    }

    @Override
    public BidsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BidItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.bid_item,
                        parent, false);
        binding.setCallback(bidItemClickCallback);
        return new BidsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BidsViewHolder holder, int position) {
        holder.binding.setBidsItem(bidsList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return bidsList == null ? 0 : bidsList.size();
    }

    static class BidsViewHolder extends RecyclerView.ViewHolder {
        BidItemBinding binding;
        public BidsViewHolder(BidItemBinding binding ) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
