package google.architecture.common.widget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

import google.architecture.common.widget.CommFlowLayout;

/**
 * @author lq.zeng
 * @date 2018/10/16
 */

public abstract class CommFlowLayoutAdapter<T> extends TagAdapter<T> {

    protected Context mContext;
    protected List<T> mDatas;

    protected abstract int getLayoutId();
    protected abstract void setView(View view, T t);

    public CommFlowLayoutAdapter(Context context, List<T> datas) {
        super(datas);
        mDatas = datas;
        mContext = context;
    }

    @Override
    public View getView(CommFlowLayout parent, int position, T t) {
        View view = LayoutInflater.from(mContext).inflate(getLayoutId(), parent, false);
        setView(view, t);
        return view;
    }

    public void refreshData(List<T> datas) {
        mDatas.addAll(datas);
        notifyDataChanged();
    }
}
