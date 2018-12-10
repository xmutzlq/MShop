package google.architecture.common.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.BaseViewHolder;

import google.architecture.common.base.BaseApplication;
import google.architecture.common.util.DimensionsUtil;
import google.architecture.common.widget.decoration.RvDecoration;

/**
 * @author lq.zeng
 * @date 2018/9/30
 */

public class ImageUploadDecoration extends RvDecoration {
    @Override
    protected void getExRvItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        BaseViewHolder viewHolder = (BaseViewHolder) parent.getChildViewHolder(view);
        LogUtils.tag("zlq").e("position = " + viewHolder.getAdapterPosition());
        if(viewHolder.getAdapterPosition() % 4 != 3) {
            outRect.set(0, 0, DimensionsUtil.dip2px(BaseApplication.getIns(), 20), 0);
        }
    }
}
