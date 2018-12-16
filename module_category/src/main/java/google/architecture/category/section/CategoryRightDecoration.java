package google.architecture.category.section;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

import google.architecture.category.viewholder.CategoryRightTitleViewHolder;
import google.architecture.common.base.BaseApplication;
import google.architecture.common.imgloader.util.DisplayUtil;

/**
 * @author lq.zeng
 * @date 2018/6/6
 */

public class CategoryRightDecoration extends ExRvDecoration {

    private int flagPosition = 0;

    @Override
    protected void getExRvItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        BaseViewHolder viewHolder = (BaseViewHolder) parent.getChildViewHolder(view);
        if (viewHolder instanceof CategoryRightTitleViewHolder) {
            CategoryRightTitleViewHolder categoryRightTitleViewHolder = (CategoryRightTitleViewHolder)viewHolder;
            int type = categoryRightTitleViewHolder.getType();
            int position = categoryRightTitleViewHolder.getAdapterPosition();
            switch (type) {
                /*case CategoryRightSection.SECTION_TYPE_BANNER:
                    outRect.top = DisplayUtil.dip2px(BaseApplication.getIns(), 10);
                    outRect.left = DisplayUtil.dip2px(BaseApplication.getIns(),10);
                    outRect.right = DisplayUtil.dip2px(BaseApplication.getIns(),5);
                    break;*/
                case CategoryRightSection.SECTION_TYPE_TITLE:
                    flagPosition = position;
                    if(position == 0) {
                        outRect.top = DisplayUtil.dip2px(BaseApplication.getIns(),8);
                    } else {
                        outRect.top = DisplayUtil.dip2px(BaseApplication.getIns(),20);
                    }
                    outRect.bottom = DisplayUtil.dip2px(BaseApplication.getIns(),5);
                    outRect.left = DisplayUtil.dip2px(BaseApplication.getIns(),15);
                    outRect.right = outRect.left;
                    break;
                case CategoryRightSection.SECTION_TYPE_ITEM:
                    int currentPosition = position - flagPosition - 1;
                    Log.d("zlq", "currentPosition: " + currentPosition);
                    if(currentPosition % 3 == 0) {
                        outRect.left = DisplayUtil.dip2px(BaseApplication.getIns(),10);
                        outRect.right = DisplayUtil.dip2px(BaseApplication.getIns(),5);
                    } else if(currentPosition % 3 == 2) {
                        outRect.left = DisplayUtil.dip2px(BaseApplication.getIns(),5);
                        outRect.right = DisplayUtil.dip2px(BaseApplication.getIns(),10);
                    } else {
                        outRect.left = DisplayUtil.dip2px(BaseApplication.getIns(),5);
                        outRect.right = DisplayUtil.dip2px(BaseApplication.getIns(),5);
                    }
                    outRect.bottom = DisplayUtil.dip2px(BaseApplication.getIns(),10);
                    break;
            }
        }
    }
}
