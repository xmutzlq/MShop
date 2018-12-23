package google.architecture.coremodel.data.xlj.searchfilter;

import com.google.gson.annotations.SerializedName;

public class SelectedTags {
    @SerializedName("0a1")
    private SelectedTagsChild sa1;
    @SerializedName("0b1")
    private SelectedTagsChild sb1;
    @SerializedName("0c1")
    private SelectedTagsChild sc1;
    @SerializedName("0c2")
    private SelectedTagsChild sc2;

    public SelectedTagsChild getSa1() {
        return sa1;
    }

    public void setSa1(SelectedTagsChild sa1) {
        this.sa1 = sa1;
    }

    public SelectedTagsChild getSb1() {
        return sb1;
    }

    public void setSb1(SelectedTagsChild sb1) {
        this.sb1 = sb1;
    }

    public SelectedTagsChild getSc1() {
        return sc1;
    }

    public void setSc1(SelectedTagsChild sc1) {
        this.sc1 = sc1;
    }

    public SelectedTagsChild getSc2() {
        return sc2;
    }

    public void setSc2(SelectedTagsChild sc2) {
        this.sc2 = sc2;
    }
}
