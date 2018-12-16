package google.architecture.coremodel.datamodel.http;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

public class XLJ_HttpResult<T> {

    @SerializedName("code")
    private String error;
    @SerializedName("msg")
    private String message;
    @SerializedName("data")
    private T data;

    public boolean isOK() {
        return !TextUtils.isEmpty(error) && error.equals("1") && data != null;
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
