package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/8/29
 */

public class DetailCommentPageInfo {
    @SerializedName("comment_list")
    private List<DetailCommentsInfo> comment_list;

    @SerializedName("comment_total")
    private int comment_total;

    @SerializedName("comment_pages")
    private int comment_pages;

    public List<DetailCommentsInfo> getComment_list() {
        return comment_list;
    }

    public void setComment_list(List<DetailCommentsInfo> comment_list) {
        this.comment_list = comment_list;
    }

    public int getComment_total() {
        return comment_total;
    }

    public void setComment_total(int comment_total) {
        this.comment_total = comment_total;
    }

    public int getComment_pages() {
        return comment_pages;
    }

    public void setComment_pages(int comment_pages) {
        this.comment_pages = comment_pages;
    }
}
