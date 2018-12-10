package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/8/28
 */

public class DetailCommentInfo {
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
}
