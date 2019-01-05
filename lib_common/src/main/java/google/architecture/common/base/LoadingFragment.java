package google.architecture.common.base;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import google.architecture.common.R;
import google.architecture.common.util.ScreenUtils;

public class LoadingFragment extends DialogFragment {
    private boolean cancel;
    private TextView tvAlert;
    private String alert;

    public static LoadingFragment newInstance(boolean cancel) {
        LoadingFragment instance = new LoadingFragment();
        Bundle args = new Bundle();
        args.putBoolean("cancel", cancel);
        instance.setArguments(args);
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_loading, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ScreenUtils.getScreenWidth(),ScreenUtils.getScreenHeight()));
        tvAlert = (TextView) view.findViewById(R.id.loading_txt);
        if (!TextUtils.isEmpty(alert)) {
            tvAlert.setText(alert);
            tvAlert.setVisibility(View.VISIBLE);
        } else {
            tvAlert.setVisibility(View.GONE);
        }
        cancel = getArguments().getBoolean("cancel");
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (cancel) {
                    dismiss();
                }
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_FRAME|STYLE_NO_INPUT|STYLE_NO_TITLE, 0);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window dialogWindow = getDialog().getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
    }

    public void setAlert(String alert) {
        this.alert = alert;
        if (tvAlert != null) {
            if (!TextUtils.isEmpty(alert)) {
                tvAlert.setText(alert);
                tvAlert.setVisibility(View.VISIBLE);
            } else {
                tvAlert.setVisibility(View.GONE);
            }
        }

    }

}
