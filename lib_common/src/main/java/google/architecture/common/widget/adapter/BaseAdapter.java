package google.architecture.common.widget.adapter;

import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazysunj.multitypeadapter.helper.RecyclerViewAdapterHelper;

import google.architecture.coremodel.data.MultiHeaderEntity;

/**
 * @author lq.zeng
 * @date 2018/10/19
 */

public abstract class BaseAdapter<T extends MultiHeaderEntity, K extends com.chad.library.adapter.base.BaseViewHolder, H extends RecyclerViewAdapterHelper<T>> extends BaseQuickAdapter<T, K> {

    protected H mHelper;

    public BaseAdapter(H helper) {
        super(helper.getData());
        mHelper = helper;
        mHelper.bindAdapter(this);
    }

    public BaseAdapter(int layoutId, H helper) {
        super(layoutId, helper.getData());
        mHelper = helper;
        mHelper.bindAdapter(this);
    }

    @Override
    protected K onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, mHelper.getLayoutId(viewType));
    }

    @Override
    protected int getDefItemViewType(int position) {
        if(mHelper.getData() != null && mHelper.getData().size() > 0) {
            return mHelper.getItemViewType(position);
        }
        return RecyclerViewAdapterHelper.DEFAULT_VIEW_TYPE;
    }

    public H getHelper() {
        return mHelper;
    }
}
