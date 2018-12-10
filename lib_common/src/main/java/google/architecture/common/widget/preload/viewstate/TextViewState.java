package google.architecture.common.widget.preload.viewstate;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.TextView;

/**
 * @author lq.zeng
 * @date 2018/7/9
 */

public class TextViewState extends ViewState<TextView> {
    ColorStateList textColor;
    CharSequence charSequence;
    String replaceStr;

    public TextViewState(TextView textView) {
        super(textView);
    }

    @Override
    public void beforeStart() {
        super.beforeStart();
        this.textColor = view.getTextColors();
        this.charSequence = view.getText();
        this.darker = view.getTypeface() != null && view.getTypeface().isBold();
        for (int i = 0; i < view.getText().toString().length(); i ++) {
            this.replaceStr += "-";
        }
    }

    @Override
    protected void restore() {
        this.view.setTextColor(textColor);
        this.view.setText(charSequence);
    }

    @Override
    public void start(boolean fadein) {
        super.start(fadein);
        view.setText(this.replaceStr);
        view.setTextColor(Color.TRANSPARENT);
    }
}
