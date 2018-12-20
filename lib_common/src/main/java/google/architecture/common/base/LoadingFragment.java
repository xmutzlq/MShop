package google.architecture.common.base;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import google.architecture.common.R;

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
