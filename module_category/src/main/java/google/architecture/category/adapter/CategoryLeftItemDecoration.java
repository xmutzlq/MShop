package google.architecture.category.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

import google.architecture.common.widget.decoration.RvDecoration;
import google.architecture.coremodel.MultiItemTypeHelper;

public class CategoryLeftItemDecoration extends RvDecoration {
    @Override
    protected void getExRvItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        BaseViewHolder viewHolder = (BaseViewHolder) parent.getChildViewHolder(view);
        outRect.set(0, 0, 0, 5);
    }
}
