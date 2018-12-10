package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/8/28
 */

public class DetailConsultInfo {
    @SerializedName("content")
    private String content; //问题描述
    @SerializedName("reply_count")
    private String reply_count; //回复数

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReply_count() {
        return reply_count;
    }

    public void setReply_count(String reply_count) {
        this.reply_count = reply_count;
    }
}
