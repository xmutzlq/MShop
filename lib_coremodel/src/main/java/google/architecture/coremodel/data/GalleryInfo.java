package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/5/3
 */

public class GalleryInfo {

    /** 轮播id **/
    @SerializedName("id")
    private String id;

    /** 标题 **/
    @SerializedName("title")
    private String title;

    @SerializedName("name")
    private String name;

    /** 图片地址 **/
    @SerializedName("img")
    private String img;

    /** 跳转HTML **/
    @SerializedName("url")
    private String url;

    @SerializedName("image")
    private String image;

    @SerializedName("type")
    private String type;

    @SerializedName("h5_url")
    private String h5_url;

    @SerializedName("target")
    private String target;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getH5_url() {
        return h5_url;
    }

    public void setH5_url(String h5_url) {
        this.h5_url = h5_url;
    }
}
