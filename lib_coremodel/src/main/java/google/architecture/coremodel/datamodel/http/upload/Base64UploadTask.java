package google.architecture.coremodel.datamodel.http.upload;

import android.content.Intent;

import com.apkfuns.logutils.LogUtils;

import net.gotev.uploadservice.HttpUploadTask;
import net.gotev.uploadservice.UploadService;
import net.gotev.uploadservice.http.BodyWriter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author lq.zeng
 * @date 2018/9/18
 */

public class Base64UploadTask extends HttpUploadTask {

    @Override
    protected void init(UploadService service, Intent intent) throws IOException {
        super.init(service, intent);
        httpParams.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpParams.customUserAgent = getUserAgent();
    }

    @Override
    protected long getBodyLength() throws UnsupportedEncodingException {
        return 0;
    }

    @Override
    public void onBodyReady(BodyWriter bodyWriter) throws IOException {
    }

    /**
     * 返回正确的UserAgent
     * @return
     */
    private  static String getUserAgent(){
        String userAgent = "";
        StringBuffer sb = new StringBuffer();
        userAgent = System.getProperty("http.agent");//Dalvik/2.1.0 (Linux; U; Android 6.0.1; vivo X9L Build/MMB29M)
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        LogUtils.tag("zlq").e("User-Agent","User-Agent: "+ sb.toString());
        return sb.toString();
    }
}
