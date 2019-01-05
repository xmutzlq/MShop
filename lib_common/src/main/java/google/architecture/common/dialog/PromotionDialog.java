package google.architecture.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import google.architecture.common.R;
import google.architecture.common.util.ScreenUtils;

public class PromotionDialog extends Dialog {

    public PromotionDialog(@NonNull Context context) {
        super(context,R.style.DialogStyle);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        params.width = ScreenUtils.getScreenWidth();
        params.height = ScreenUtils.getScreenHeight()-(ScreenUtils.getTitleHight(context)+ScreenUtils.getBackButtonHight(context));
        getWindow().setAttributes(params);
        setContentView(R.layout.dialog_promotion);
        View container = findViewById(R.id.container_layout);
        container.setLayoutParams(new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(),(ScreenUtils.getScreenHeight()-(ScreenUtils.getTitleHight(context)/*+ScreenUtils.getBackButtonHight(context)*/))));
        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PromotionDialog.this.dismiss();
            }
        });
    }
}
