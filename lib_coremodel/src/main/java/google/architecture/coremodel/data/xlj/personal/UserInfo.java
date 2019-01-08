package google.architecture.coremodel.data.xlj.personal;

import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("userName")
    private String userName;
    @SerializedName("userPhone")
    private String userPhone;
    @SerializedName("userPhoto")
    private String userPhoto;
    @SerializedName("userScore")
    private String userScore;//积分
    @SerializedName("userSex")
    private String userSex;//性别

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserScore() {
        return userScore;
    }

    public void setUserScore(String userScore) {
        this.userScore = userScore;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }
}
