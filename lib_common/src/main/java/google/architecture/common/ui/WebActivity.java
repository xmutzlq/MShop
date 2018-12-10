package google.architecture.common.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.king.android.res.config.ARouterPath;
import com.tencent.smtt.sdk.ValueCallback;

import google.architecture.common.R;
import google.architecture.common.base.BaseActivity;
import google.architecture.common.databinding.ActivityWebPageBinding;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.web.JavaScriptObject;
import google.architecture.common.widget.CommWebView;

/**
 * @author lq.zeng
 * @date 2018/10/25
 */
@Route(path = ARouterPath.WebPage)
public class WebActivity extends BaseActivity<ActivityWebPageBinding> implements CommWebView.WebViewCallback {

    private ValueCallback uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;
    private final static int FILE_CHOOSER_RESULT_CODE = 10000;

    private CommWebView commWebView;

    @Override
    protected int getLayout() {
        return R.layout.activity_web_page;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCanBack(true);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        commWebView = new CommWebView(this_);
        binding.commWebContainer.addView(commWebView, params);

        commWebView.setWebViewCallback(this);
        commWebView.addJavascriptInterface(new JavaScriptObject(this_), "android");
        removeJavascriptInterfaces(commWebView);

        Intent intent = getIntent();
        String loadContent = intent.getStringExtra(CommKeyUtil.EXTRA_KEY);
        commWebView.loadUrl(loadContent);
    }

    @TargetApi(11)
    private static final void removeJavascriptInterfaces(CommWebView webView) {
        try {
            if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT < 17) {
                webView.removeJavascriptInterface("searchBoxJavaBridge_");
                webView.removeJavascriptInterface("accessibility");
                webView.removeJavascriptInterface("accessibilityTraversal");
            }
        } catch (Throwable tr) {
            tr.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if(commWebView != null) commWebView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onReceivedTitle(String title) {
        setTitleName(title);
    }

    @Override
    public void onReceivedShowFileChooser(ValueCallback<Uri[]> valueCallback) {
        uploadMessageAboveL = valueCallback;
        openImageChooserActivity();
    }

    @Override
    public void onReceivedFileChooser(ValueCallback valueCallback) {
        uploadMessage = valueCallback;
        openImageChooserActivity();
    }

    private void openImageChooserActivity() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (null == uploadMessage && null == uploadMessageAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (uploadMessageAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (uploadMessage != null) {
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
    }
}
