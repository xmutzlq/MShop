package com.king.android.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.king.android.R;
import com.kongzue.dialog.listener.InputDialogOkButtonClickListener;
import com.kongzue.dialog.util.ModalBaseDialog;

import google.architecture.common.base.listener.LifeCycleListener;
import google.architecture.common.widget.AmountViewDivide;

import static android.content.DialogInterface.BUTTON_NEGATIVE;

/**
 * @author lq.zeng
 * @date 2018/5/15
 */

public class DialogCartGoodsModify extends ModalBaseDialog implements LifeCycleListener {

    private AlertDialog alertDialog;
    private static DialogCartGoodsModify inputDialog;
    private boolean isCanCancel = false;

    private Context context;
    private String title;
    private String defaultInputText = "";
    private String okButtonCaption = "确定";
    private String cancelButtonCaption = "取消";
    private InputDialogOkButtonClickListener onOkButtonClickListener;
    private DialogInterface.OnClickListener onCancelButtonClickListener;

    private DialogCartGoodsModify() {
    }

    //Fast Function
    public static DialogCartGoodsModify show(Context context, String title, InputDialogOkButtonClickListener onOkButtonClickListener) {
        return show(context, title, "确定", onOkButtonClickListener, "取消", null);
    }

    public static DialogCartGoodsModify show(Context context, String title, String okButtonCaption, InputDialogOkButtonClickListener onOkButtonClickListener,
                                   String cancelButtonCaption, DialogInterface.OnClickListener onCancelButtonClickListener) {
        synchronized (DialogCartGoodsModify.class) {
            if (inputDialog == null) inputDialog = new DialogCartGoodsModify();
            if(inputDialog.isDialogShown) return inputDialog;
            inputDialog.context = context;
            inputDialog.title = title;
            inputDialog.okButtonCaption = okButtonCaption;
            inputDialog.cancelButtonCaption = cancelButtonCaption;
            inputDialog.onOkButtonClickListener = onOkButtonClickListener;
            inputDialog.onCancelButtonClickListener = onCancelButtonClickListener;
            modalDialogList.add(inputDialog);
            showNextModalDialog();
            return inputDialog;
        }
    }

    private ViewGroup bkg;
    private AmountViewDivide amountViewDivide;
    private TextView txtDialogTitle;
    private TextView btnSelectNegative;
    private TextView btnSelectPositive;

    @Override
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(isCanCancel);
        alertDialog = builder.create();
        alertDialog.setView(new AmountViewDivide(context));
        if (getDialogLifeCycleListener() != null) getDialogLifeCycleListener().onCreate(alertDialog);
        if (isCanCancel) alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setOnDismissListener(dialog -> {
            if (onCancelButtonClickListener != null)
                onCancelButtonClickListener.onClick(alertDialog, BUTTON_NEGATIVE);
            if (getDialogLifeCycleListener() != null) getDialogLifeCycleListener().onDismiss();
            isDialogShown = false;
            modalDialogList.remove(0);
            if(modalDialogList.size() > 0) {
                showNextModalDialog();
            }
        });

        Window window = alertDialog.getWindow();
        alertDialog.show();
        window.setContentView(R.layout.cart_goods_modify);

        bkg = (LinearLayout) window.findViewById(R.id.bkg);
        amountViewDivide = window.findViewById(R.id.cart_amount_view_divide);
        txtDialogTitle = window.findViewById(R.id.txt_dialog_title);
        btnSelectNegative = window.findViewById(R.id.btn_selectNegative);
        btnSelectPositive = window.findViewById(R.id.btn_selectPositive);

        txtDialogTitle.setText(title);

        btnSelectNegative.setVisibility(View.VISIBLE);
        btnSelectPositive.setText(okButtonCaption);
        btnSelectPositive.setOnClickListener(v -> {
            alertDialog.dismiss();
            if (onOkButtonClickListener != null) {
                onOkButtonClickListener.onClick(alertDialog, amountViewDivide.getAmount() + "");
            }
        });
        btnSelectNegative.setText(cancelButtonCaption);
        btnSelectNegative.setOnClickListener(v -> {
            alertDialog.dismiss();
            if (onCancelButtonClickListener != null) {
                onCancelButtonClickListener.onClick(alertDialog, BUTTON_NEGATIVE);
            }
        });

        isDialogShown = true;
        if (getDialogLifeCycleListener() != null) getDialogLifeCycleListener().onShow(alertDialog);
    }

    @Override
    public void doDismiss() {

    }

    @Override
    public void onActivityDestroy() {
        if(alertDialog != null && alertDialog.isShowing()){
            alertDialog.dismiss();
        }
    }

    public DialogCartGoodsModify setCanCancel(boolean canCancel) {
        isCanCancel = canCancel;
        if (alertDialog != null) alertDialog.setCancelable(canCancel);
        return this;
    }

    public DialogCartGoodsModify setDefaultInputText(String defaultInputText) {
        this.defaultInputText = defaultInputText;
        if (alertDialog != null) {
            amountViewDivide.setAmount(Integer.valueOf(defaultInputText));
        }
        return this;
    }

}
