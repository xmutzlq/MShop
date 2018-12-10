package google.architecture.common.widget.expand;

import android.content.Context;
import android.widget.Checkable;

/**
 * @author lq.zeng
 * @date 2018/5/31
 */

public class CheckableWrapperView extends WrapperView implements Checkable {

    public CheckableWrapperView(final Context context) {
        super(context);
    }

    @Override
    public boolean isChecked() {
        return ((Checkable) mItem).isChecked();
    }

    @Override
    public void setChecked(final boolean checked) {
        ((Checkable) mItem).setChecked(checked);
    }

    @Override
    public void toggle() {
        setChecked(!isChecked());
    }
}
