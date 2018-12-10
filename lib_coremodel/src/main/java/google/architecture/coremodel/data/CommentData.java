package google.architecture.coremodel.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/6/28
 */

public class CommentData {
    public String userNickName;
    public String userComments;
    public String userHeadUrl;
    public String userCommentStarts;
    public String userBuyGoodsName;
    public List<String> pubImgs;

    public CommentData(String userNickName, String userComments, String userHeadUrl, String userCommentStarts, String userBuyGoodsName) {
        this.userNickName = userNickName;
        this.userComments = userComments;
        this.userHeadUrl = userHeadUrl;
        this.userCommentStarts = userCommentStarts;
        this.userBuyGoodsName = userBuyGoodsName;
        if(pubImgs == null) {
            pubImgs = new ArrayList<>();
        }
        pubImgs.add("http://img30.360buyimg.com/shaidan/s354x354_jfs/t10642/318/1677297843/674341/d81d2c2d/59e47fefN1adb6f46.jpg.dpg");
        pubImgs.add("http://img30.360buyimg.com/shaidan/s354x354_jfs/t10795/231/1675348652/618190/d557104/59e47febN8cd74f49.jpg.dpg");
        pubImgs.add("http://img30.360buyimg.com/shaidan/s354x354_jfs/t10177/243/1683788380/129023/c47153cc/59e47fdfN0c42f534.jpg.dpg");
        pubImgs.add("http://img30.360buyimg.com/shaidan/s354x354_jfs/t10045/2/1653935856/146738/2c2569d0/59e47fe0N2b3299e9.jpg.dpg");
    }
}
