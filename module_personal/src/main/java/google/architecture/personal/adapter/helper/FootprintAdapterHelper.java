package google.architecture.personal.adapter.helper;

import android.support.v7.util.DiffUtil;

import com.crazysunj.multitypeadapter.helper.AsynAdapterHelper;

import java.util.List;

import google.architecture.coremodel.data.FootprintInfo;
import google.architecture.coremodel.data.MultiHeaderEntity;
import google.architecture.personal.R;
import google.architecture.personal.adapter.FootprintAdapter;


/**
 * @author lq.zeng
 * @date 2018/10/18
 */

public class FootprintAdapterHelper extends AsynAdapterHelper<MultiHeaderEntity> {

    public static final int LEVEL_FOOT_PRINT = 0;

    public FootprintAdapterHelper() {
        super(null);
    }

    @Override
    protected void registerModule() {
        registerModule(LEVEL_FOOT_PRINT)
                .type(FootprintInfo.TYPE_USER_FOOT_PRINT)
                .layoutResId(R.layout.item_foot_print)
                .register();
    }

    @Override
    protected int getPreDataCount() {
        final FootprintAdapter adapter = getBindAdapter();
        return adapter.getHeaderLayoutCount();
    }

    @Override
    protected DiffUtil.Callback getDiffCallBack(List<MultiHeaderEntity> oldData, List<MultiHeaderEntity> newData) {
        return new TitleDiffCallBack(oldData, newData);
    }

    private static class TitleDiffCallBack extends DiffUtil.Callback {

        private List<MultiHeaderEntity> mOldDatas;
        private List<MultiHeaderEntity> mNewDatas;

        TitleDiffCallBack(List<MultiHeaderEntity> mOldDatas, List<MultiHeaderEntity> mNewDatas) {
            this.mOldDatas = mOldDatas;
            this.mNewDatas = mNewDatas;
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
            return oldItem.getId() == newItem.getId() && oldItem.getItemType() == newItem.getItemType();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            MultiHeaderEntity oldItem = mOldDatas.get(oldItemPosition);
            MultiHeaderEntity newItem = mNewDatas.get(newItemPosition);
            return oldItem.getId() == newItem.getId() && oldItem.getItemType() == newItem.getItemType();
        }
    }

    public long getHeaderId(int position) {
        int preDataCount = getPreDataCount();
        if (position < preDataCount
                || mData.size() == 0 || mData.size() == position - preDataCount) {
            return -1;
        }
        MultiHeaderEntity data = mData.get(position - preDataCount);
        return data.getHeaderId();
    }
}
