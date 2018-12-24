package google.architecture.coremodel.datamodel.http;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/4/8
 */

public class HttpResult <T> {

    @SerializedName("error")
    private String code;
    @SerializedName("message")
    private String msg;
    @SerializedName("data")
    private T data;

    public boolean isOK() {
        return !TextUtils.isEmpty(code) && code.equals("0") && data != null;
    }

    public String getInfo() {
        return msg;
    }

    public void setInfo(String info) {
        this.msg = info;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
