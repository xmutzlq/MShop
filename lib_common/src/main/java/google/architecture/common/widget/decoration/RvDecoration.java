package google.architecture.common.widget.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author lq.zeng
 * @date 2018/9/4
 */

public class RvDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        onExRvDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        onExRvDrawOver(c, parent, state);
    }

    protected void onExRvDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        //nothing
    }

    protected void onExRvDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        //nothing
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        getExRvItemOffsets(outRect, view, parent, state);
    }

    protected void getExRvItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        BaseViewHolder viewHolder = (BaseViewHolder) parent.getChildViewHolder(view);
        getExRvDataItemOffsets(outRect, viewHolder, parent, state);
    }
    protected void getExRvDataItemOffsets(Rect outRect, BaseViewHolder viewHolder, RecyclerView parent, RecyclerView.State state) {

        //nothing
    }

}
