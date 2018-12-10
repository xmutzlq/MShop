package google.architecture.common.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.kongzue.dialog.v2.SelectDialog;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.File;

import google.architecture.common.base.AppContext;
import google.architecture.common.util.ToastUtils;

/**
 * @author lq.zeng
 * @date 2018/10/17
 */

public class CommWebView extends MyWebView {

    private static final String HTTP = "http:";
    private static final String HTTPS = "https:";
    /**
     * 应用市场
     */
    private static final String MARKET = "market";

    private WebViewCallback mWebViewCallback;
    private DownloadCallback mDownloadCallback;
    private WebViewSonicCallback mWebViewSonicCallback;

    public CommWebView(Context context) {
        this(context, null);
        init(context);
    }

    public CommWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public CommWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressWarnings("all")
    private void init(Context context) {
        WebSettings setttings = getSettings();
        setttings.setJavaScriptEnabled(true);//打开js
        setttings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//设置布局
        setttings.setDomStorageEnabled(true);//打开Dom Storage
        setttings.setDatabaseEnabled(true);//打开Database
        setttings.setAppCacheEnabled(true);//打开App Cache
        setttings.setAppCacheMaxSize(Long.MAX_VALUE);
        File cacheDir = new File(context.getExternalCacheDir(), AppContext.CACHE_DIR_WEB);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        setttings.setAppCachePath(cacheDir.getAbsolutePath());//设置App Cache缓存目录
        setttings.setSupportMultipleWindows(false);//不支持多窗口
        setttings.setJavaScriptCanOpenWindowsAutomatically(true);//支持js打开新窗口
        setttings.setAllowContentAccess(true);
        setttings.setAllowFileAccess(true);//启用WebView访问文件数据
        setttings.setSupportZoom(true);//支持缩放
        setttings.setDisplayZoomControls(false);//隐藏webview缩放按钮
        setttings.setBuiltInZoomControls(true);//支持手势缩放
        setttings.setLoadWithOverviewMode(true);//缩放至屏幕大小
        setttings.setUseWideViewPort(true);//调整屏幕自适应
        setttings.setDefaultTextEncodingName("utf-8");//设置编码格式为utf-8
        setttings.setLoadsImagesAutomatically(true);//支持自动加载图片
        setttings.setSavePassword(false);//禁止密码保存在本地
        setWebViewClient(new CommWebViewClient());
        setWebChromeClient(new CommWebChromeClient());
        setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                if (mDownloadCallback != null) {
                    mDownloadCallback.onDownload(url, contentLength);
                }
            }
        });
    }

    /**
     * 防止内存泄露
     */
    public void onDestroy() {
        loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        clearHistory();
        ((ViewGroup) getParent()).removeView(this);
        setWebViewClient(null);
        setWebChromeClient(null);
        destroy();
    }

    public class CommWebViewClient extends WebViewClient {

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
            if (mWebViewSonicCallback != null) {
                WebResourceResponse webResourceResponse;
                try {
                    webResourceResponse = (WebResourceResponse) mWebViewSonicCallback.requestResource(s);
                } catch (Exception e) {
                    e.printStackTrace();
                    webResourceResponse = super.shouldInterceptRequest(webView, s);
                }
                return webResourceResponse;
            }
            return super.shouldInterceptRequest(webView, s);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
            if (mWebViewSonicCallback != null) {
                String s = webResourceRequest.getUrl().toString();
                WebResourceResponse webResourceResponse;
                try {
                    webResourceResponse = (WebResourceResponse) mWebViewSonicCallback.requestResource(s);
                } catch (Exception e) {
                    e.printStackTrace();
                    webResourceResponse = super.shouldInterceptRequest(webView, webResourceRequest);
                }
                return webResourceResponse;
            }
            return super.shouldInterceptRequest(webView, webResourceRequest);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest, Bundle bundle) {
            if (mWebViewSonicCallback != null) {
                String s = webResourceRequest.getUrl().toString();
                WebResourceResponse webResourceResponse;
                try {
                    webResourceResponse = (WebResourceResponse) mWebViewSonicCallback.requestResource(s);
                } catch (Exception e) {
                    e.printStackTrace();
                    webResourceResponse = super.shouldInterceptRequest(webView, webResourceRequest, bundle);
                }
                return webResourceResponse;
            }
            return super.shouldInterceptRequest(webView, webResourceRequest, bundle);
        }

        private boolean isLoaded = false;

        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            if (!isLoaded) {
                isLoaded = true;
                webView.loadUrl(s);
            }
            super.onPageStarted(webView, s, bitmap);
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
            if (mWebViewSonicCallback != null) {
                mWebViewSonicCallback.pageFinish(s);
            }
        }

        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler handler, SslError sslError) {
            handler.proceed();//处理证书
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            if (url == null) {
                return false;
            }
            if (url.startsWith(HTTP) || url.startsWith(HTTPS)) {
                return false;
            }
            Uri uri = Uri.parse(url);
            // 这里只列了应用市场
            if (uri.getScheme().contains(MARKET)) {
                showEnterDialog(uri);
            }
            return true;
        }

        private void showEnterDialog(Uri uri) {
            final Context context = getContext();
            SelectDialog.show(context, "温馨提示", "要不要跳转到商店", "无限流量", (dialog, which) -> {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShortToast("跳转商店出了点差错");
                }
            }, "浪费流量", (dialog, which) -> {

            });
        }
    }

    @Override
    public void loadUrl(String s) {
        try {
            Uri uri = Uri.parse(s);
            if (TextUtils.isEmpty(uri.getScheme())) {
                loadData(s, "text/html; charset=UTF-8", null);
            } else {
                super.loadUrl(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
            loadData(s, "text/html; charset=UTF-8", null);
        }
    }

    public class CommWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (mWebViewCallback != null) {
                mWebViewCallback.onReceivedTitle(title);
            }
        }

        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> valueCallback) {
            if (mWebViewCallback != null) {
                mWebViewCallback.onReceivedFileChooser(valueCallback);
            }
        }

        // For Android  >= 3.0
        public void openFileChooser(ValueCallback valueCallback, String acceptType) {
            if (mWebViewCallback != null) {
                mWebViewCallback.onReceivedFileChooser(valueCallback);
            }
        }

        //For Android  >= 4.1
        public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
            if (mWebViewCallback != null) {
                mWebViewCallback.onReceivedFileChooser(valueCallback);
            }
        }

        // For Android >= 5.0
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            if (mWebViewCallback != null) {
                mWebViewCallback.onReceivedShowFileChooser(filePathCallback);
            }
            return true;
        }
    }

    public void setWebViewSonicCallback(WebViewSonicCallback callback) {
        mWebViewSonicCallback = callback;
    }

    public interface WebViewSonicCallback {
        void pageFinish(String url);

        Object requestResource(String url);
    }

    public void setWebViewCallback(WebViewCallback callback) {
        mWebViewCallback = callback;
    }

    public interface WebViewCallback {
        /**
         * 回调title
         */
        void onReceivedTitle(String title);
        void onReceivedFileChooser(ValueCallback<Uri> valueCallback);
        void onReceivedShowFileChooser(ValueCallback<Uri[]> valueCallback);
    }

    public void setDownloadCallback(DownloadCallback callback) {
        mDownloadCallback = callback;
    }

    public interface DownloadCallback {
        void onDownload(String url, long contentLength);
    }
}
