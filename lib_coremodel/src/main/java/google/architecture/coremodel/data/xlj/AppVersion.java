package google.architecture.coremodel.data.xlj;

import com.google.gson.annotations.SerializedName;

public class AppVersion {

    @SerializedName("platform")
    private String platform;
    @SerializedName("update_log")
    private String updateLog;
    @SerializedName("version_code")
    private String versionCode;
    @SerializedName("version_name")
    private String versionName;
    @SerializedName("download_url")
    private String downloadUrl;
    @SerializedName("is_force")
    private String isForce;
    @SerializedName("create_time")
    private String createTime;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getUpdateLog() {
        return updateLog;
    }

    public void setUpdateLog(String updateLog) {
        this.updateLog = updateLog;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getIsForce() {
        return isForce;
    }

    public void setIsForce(String isForce) {
        this.isForce = isForce;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
