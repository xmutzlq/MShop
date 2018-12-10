package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/8/29
 */

public class DetailCommentsInfo {

    @SerializedName("comment_id")
    private String comment_id; //评论ID
    @SerializedName("username")
    private String username; //评论用户
    @SerializedName("head_pic")
    private String head_pic; //用户头像
    @SerializedName("spec_depict")
    private String spec_depict; //购买的规格参数
    @SerializedName("content")
    private String content; //评论内容
    @SerializedName("create_time")
    private String create_time; //时间
    @SerializedName("image")
    private List<String> image;

    @SerializedName("is_reply")
    private String is_reply; //店家是否回复，1=是，-1否
    @SerializedName("reply_content")
    private String reply_content; //店家回复内容
    @SerializedName("is_append")
    private String is_append;  //是否有追加评论 1=有，-1=无

    @SerializedName("append_list")
    private List<DetailCommentsInfo> append_list;//有追加的评论列表

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getSpec_depict() {
        return spec_depict;
    }

    public void setSpec_depict(String spec_depict) {
        this.spec_depict = spec_depict;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public String getIs_reply() {
        return is_reply;
    }

    public void setIs_reply(String is_reply) {
        this.is_reply = is_reply;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public String getIs_append() {
        return is_append;
    }

    public void setIs_append(String is_append) {
        this.is_append = is_append;
    }

    public List<DetailCommentsInfo> getAppend_list() {
        return append_list;
    }

    public void setAppend_list(List<DetailCommentsInfo> append_list) {
        this.append_list = append_list;
    }

    @Override
    public String toString() {
        return "DetailCommentsInfo{" +
                "comment_id='" + comment_id + '\'' +
                ", username='" + username + '\'' +
                ", head_pic='" + head_pic + '\'' +
                ", spec_depict='" + spec_depict + '\'' +
                ", content='" + content + '\'' +
                ", create_time='" + create_time + '\'' +
                ", image=" + image +
                ", is_reply='" + is_reply + '\'' +
                ", reply_content='" + reply_content + '\'' +
                ", is_append='" + is_append + '\'' +
                ", append_list=" + append_list +
                '}';
    }
}
