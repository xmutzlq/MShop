package google.architecture.common.widget.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author lq.zeng
 * @date 2018/10/18
 */

public class BaseViewHolder  extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;

    public BaseViewHolder(View view) {
        super(view);
        if (mViews == null) {
            mViews = new SparseArray<>();
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId, Class<T> clazz) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public void setText(int viewId, CharSequence text) {
        getTextView(viewId).setText(text);
    }

    public void setImageResource(int viewId, int resId) {
        getImageView(viewId).setImageResource(resId);
    }

    public void setVisible(int viewId, boolean visible) {
        getView(viewId).setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public TextView getTextView(int viewId) {
        return getView(viewId, TextView.class);
    }

    public ImageView getImageView(int viewId) {
        return getView(viewId, ImageView.class);
    }

}
