package google.architecture.coremodel.datamodel.http.upload;

import android.text.TextUtils;

import net.gotev.uploadservice.http.BodyWriter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import google.architecture.coremodel.datamodel.http.PostValues;
import google.architecture.coremodel.util.DESSecretUtils;
import okio.BufferedSink;

/**
 * @author lq.zeng
 * @date 2018/9/18
 */

public class OkHttpBodyWriter extends BodyWriter {

    private BufferedSink mSink;

    protected OkHttpBodyWriter(BufferedSink sink) {
        mSink = sink;
    }

    public byte[] makeParams() {
        PostValues postValues = new PostValues();
        // 公共参数
        postValues.makeCommParams();
        postValues.preparePost();
        postValues.printLog();
        // 生成params
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : postValues.postValues().entrySet()) {
                if(!TextUtils.isEmpty(entry.getKey())) {
                    encodedParams.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                }
                encodedParams.append('=');
                if(!TextUtils.isEmpty(entry.getValue())) {
                    encodedParams.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                }
                encodedParams.append('&');
            }
            return ("params=" + DESSecretUtils.AES_cbc_encrypt(encodedParams.substring(0, encodedParams.length() - 1).toString())).getBytes("UTF-8");
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + "UTF-8", uee);
        }
    }

    @Override
    public void write(byte[] bytes) throws IOException {
        mSink.write(bytes);
    }

    @Override
    public void write(byte[] bytes, int lengthToWriteFromStart) throws IOException {
        mSink.write(bytes, 0, lengthToWriteFromStart);
    }

    @Override
    public void flush() throws IOException {
        mSink.flush();
    }
}
