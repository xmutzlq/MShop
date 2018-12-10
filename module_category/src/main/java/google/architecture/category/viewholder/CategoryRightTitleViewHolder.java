package google.architecture.category.viewholder;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author lq.zeng
 * @date 2018/6/6
 */

public class CategoryRightTitleViewHolder extends BaseViewHolder {
    private int type;

    public CategoryRightTitleViewHolder(View view) {
        super(view);
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
