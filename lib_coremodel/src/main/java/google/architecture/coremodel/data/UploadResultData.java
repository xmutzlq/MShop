package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author lq.zeng
 * @date 2018/9/18
 */

public class UploadResultData {
    @SerializedName("pic")
    private String pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
