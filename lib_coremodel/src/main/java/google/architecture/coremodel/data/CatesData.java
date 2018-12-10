package google.architecture.coremodel.data;

import org.json.JSONObject;

/**
 * @author lq.zeng
 * @date 2018/6/5
 */

public class CatesData {
    private String filmId;
    private String filmName;
    private String brief;
    private String short_brief;
    private String dimensional;
    private String imax;
    private String releaseDate;
    private String wekDate;
    private String starCode;
    private String grade;
    private String duration;
    private int status;
    private int showCinemasCount;
    private int showSchedulesCount;
    private int have_schedule;
    private String media;
    private String imageUrl;
    private String posterUrl;

    public CatesData(JSONObject jsonObject) {
        this.filmId = jsonObject.optString("filmId");
        this.filmName = jsonObject.optString("filmName");
        this.short_brief = jsonObject.optString("short_brief");
        this.wekDate = jsonObject.optString("wekDate");
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getShort_brief() {
        return short_brief;
    }

    public void setShort_brief(String short_brief) {
        this.short_brief = short_brief;
    }

    public String getDimensional() {
        return dimensional;
    }

    public void setDimensional(String dimensional) {
        this.dimensional = dimensional;
    }

    public String getImax() {
        return imax;
    }

    public void setImax(String imax) {
        this.imax = imax;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getWekDate() {
        return wekDate;
    }

    public void setWekDate(String wekDate) {
        this.wekDate = wekDate;
    }

    public String getStarCode() {
        return starCode;
    }

    public void setStarCode(String starCode) {
        this.starCode = starCode;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getShowCinemasCount() {
        return showCinemasCount;
    }

    public void setShowCinemasCount(int showCinemasCount) {
        this.showCinemasCount = showCinemasCount;
    }

    public int getShowSchedulesCount() {
        return showSchedulesCount;
    }

    public void setShowSchedulesCount(int showSchedulesCount) {
        this.showSchedulesCount = showSchedulesCount;
    }

    public int getHave_schedule() {
        return have_schedule;
    }

    public void setHave_schedule(int have_schedule) {
        this.have_schedule = have_schedule;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
