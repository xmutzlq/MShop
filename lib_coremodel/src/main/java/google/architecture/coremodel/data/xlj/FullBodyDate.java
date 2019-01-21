package google.architecture.coremodel.data.xlj;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FullBodyDate implements Serializable {

    @SerializedName("foot")
    private FootData foot;
    @SerializedName("body")
    private BodyData body;

    public FootData getFoot() {
        return foot;
    }

    public void setFoot(FootData foot) {
        this.foot = foot;
    }

    public BodyData getBody() {
        return body;
    }

    public void setBody(BodyData body) {
        this.body = body;
    }
}
