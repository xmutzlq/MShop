package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/6/6
 */

public class OpDiscoverIndexResult implements Serializable{
    @SerializedName("category_list")
    private List<OpDiscoverCates> discoverList;
    private List<OpDiscoverCates> localLevel2List;

    @SerializedName("slide_list")
    private List<OpDiscoverCates> banners;

    public List<OpDiscoverCates> getLocalLevel2List() {
        return localLevel2List;
    }

    public void setLocalLevel2List(List<OpDiscoverCates> localLevel2List) {
        this.localLevel2List = localLevel2List;
    }

    public List<OpDiscoverCates> getDiscoverList() {
        return discoverList;
    }

    public void setDiscoverList(List<OpDiscoverCates> discoverList) {
        this.discoverList = discoverList;
    }

    public List<OpDiscoverCates> getBanners() {
        return banners;
    }

    public void setBanners(List<OpDiscoverCates> banners) {
        this.banners = banners;
    }

    @Override
    public String toString() {
        return "OpDiscoverIndexResult{" +
                "discoverList=" + discoverList +
                '}';
    }
}
