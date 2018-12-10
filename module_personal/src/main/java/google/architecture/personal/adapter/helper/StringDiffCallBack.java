package google.architecture.personal.adapter.helper;

import android.support.v7.util.DiffUtil;

import java.util.List;

import google.architecture.coremodel.data.MultiHeaderEntity;


/**
 * @author lq.zeng
 * @date 2018/10/18
 */

public class StringDiffCallBack extends DiffUtil.Callback {

    private List<MultiHeaderEntity> mOldDatas;
    private List<MultiHeaderEntity> mNewDatas;

    public StringDiffCallBack(List<MultiHeaderEntity> oldDatas, List<MultiHeaderEntity> newDatas) {
        this.mOldDatas = oldDatas;
        this.mNewDatas = newDatas;
    }

    @Override
    public int getOldListSize() {
        return mOldDatas == null ? 0 : mOldDatas.size();
    }

    @Override
    public int getNewListSize() {
        return mNewDatas == null ? 0 : mNewDatas.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        MultiHeaderEntity oldItem = mOldDatas.get(oldItemPosition);
        MultiHeaderEntity newItem = mNewDatas.get(newItemPosition);
        return !(oldItem == null || newItem == null) && oldItem.getItemType() == newItem.getItemType();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        long oldId = mOldDatas.get(oldItemPosition).getId();
        long newId = mNewDatas.get(newItemPosition).getId();
        return oldId != newId;
    }
}
