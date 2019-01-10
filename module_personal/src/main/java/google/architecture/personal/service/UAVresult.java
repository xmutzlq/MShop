package google.architecture.personal.service;

import com.google.gson.annotations.SerializedName;

public class UAVresult {

    @SerializedName("code")
    private int code;
    @SerializedName("ts")
    private long ts;
    @SerializedName("msg")
    private String msg;
    @SerializedName("xjName")
    private String xjName;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getXjName() {
        return xjName;
    }

    public void setXjName(String xjName) {
        this.xjName = xjName;
    }
}
