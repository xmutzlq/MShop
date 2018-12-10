package google.architecture.personal.holder;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.king.android.res.application.BaseApp;

import google.architecture.common.base.BaseApplication;
import google.architecture.common.imgloader.util.DisplayUtil;
import google.architecture.common.widget.decoration.RvDecoration;
import google.architecture.personal.R;

/**
 * @author lq.zeng
 * @date 2018/9/4
 */

public class PersonalContentItemDecoration extends RvDecoration {

    private static final int SPAN_COUNT = 4; //根据布局锁定
    private Paint mHorPaint;

    public PersonalContentItemDecoration() {
        init();
    }

    void init() {
        mHorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHorPaint.setStyle(Paint.Style.FILL);
        mHorPaint.setColor(ContextCompat.getColor(BaseApp.getIns(), R.color.grayLine));
    }

    @Override
    protected void onExRvDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c, parent);
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        int itemCount = 0; //item个数
        int titleCount = 0; //title个数
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            BaseViewHolder viewHolder = (BaseViewHolder) parent.getChildViewHolder(child);
            if (viewHolder instanceof PersonalContentTitleViewHolder) {
                int type = ((PersonalContentTitleViewHolder) viewHolder).getType();
                if(type != PersonalContentSection.SECTION_TYPE_ITEM) {
                    titleCount += 1;
                    continue;
                }
                itemCount += 1;
                //if(itemCount > SPAN_COUNT && itemCount != childCount - titleCount) {
                    int leftMargin = 0;
                    int rightMargin = 0;
                    if(itemCount % SPAN_COUNT == 1) {
                        leftMargin = DisplayUtil.dip2px(BaseApplication.getIns(),20);
                    }
                    if(itemCount % SPAN_COUNT == 0) {
                        rightMargin = DisplayUtil.dip2px(BaseApplication.getIns(),20);
                    }
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    final int left = child.getLeft() + leftMargin;
                    final int right = child.getRight() - rightMargin;
                    final int top = child.getBottom() + params.bottomMargin;
                    final int bottom = top + 2;
                    c.drawRect(left, top, right, bottom, mHorPaint);
                //}
            }
        }
    }

    @Override
    protected void getExRvItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        BaseViewHolder viewHolder = (BaseViewHolder) parent.getChildViewHolder(view);
        if (viewHolder instanceof PersonalContentTitleViewHolder) {
            int type = ((PersonalContentTitleViewHolder) viewHolder).getType();
            switch (type) {
                case PersonalContentSection.SECTION_TYPE_TITLE:
//                    outRect.top = DisplayUtil.dip2px(BaseApplication.getIns(),5);
//                    outRect.bottom = DisplayUtil.dip2px(BaseApplication.getIns(),5);
                    outRect.left = DisplayUtil.dip2px(BaseApplication.getIns(),5);
                    outRect.right = outRect.left;
                    break;
                case PersonalContentSection.SECTION_TYPE_ITEM:
//                    outRect.left = DisplayUtil.dip2px(BaseApplication.getIns(),15);
//                    outRect.right = outRect.left;
                    break;
            }
        }
    }
}
