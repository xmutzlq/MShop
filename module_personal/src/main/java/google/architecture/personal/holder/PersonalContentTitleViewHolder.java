package google.architecture.personal.holder;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author lq.zeng
 * @date 2018/9/4
 */

public class PersonalContentTitleViewHolder extends BaseViewHolder {
    private int type;

    public PersonalContentTitleViewHolder(View view) {
        super(view);
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
