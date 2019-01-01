package google.architecture.coremodel.data.xlj.personal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserInfos {

    @SerializedName("userInfo")
    private UserInfo userInfo;
    @SerializedName("userCount")
    private UserCount userCount;
    @SerializedName("guideInfo")
    private GuideInfo guideInfo;
    @SerializedName("like")
    private List<LikeGoods> like;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserCount getUserCount() {
        return userCount;
    }

    public void setUserCount(UserCount userCount) {
        this.userCount = userCount;
    }

    public GuideInfo getGuideInfo() {
        return guideInfo;
    }

    public void setGuideInfo(GuideInfo guideInfo) {
        this.guideInfo = guideInfo;
    }

    public List<LikeGoods> getLike() {
        return like;
    }

    public void setLike(List<LikeGoods> like) {
        this.like = like;
    }
}
