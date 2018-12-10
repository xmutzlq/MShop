package google.architecture.coremodel.datamodel.http;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/4/8
 */

public class HttpResult <T> {

    @SerializedName("error")
    private String error;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private T data;

    public boolean isOK() {
        return !TextUtils.isEmpty(error) && error.equals("0") && data != null;
    }

    public String getInfo() {
        return message;
    }

    public void setInfo(String info) {
        this.message = info;
    }

    public String getCode() {
        return error;
    }

    public void setCode(String code) {
        this.error = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
