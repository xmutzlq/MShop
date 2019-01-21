package google.architecture.coremodel.data.xlj;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BodyData implements Serializable {

    @SerializedName("date")
    private List<ScanBody> date;
    @SerializedName("sessionId")
    private int sessionId;
    @SerializedName("family")
    private List family;

    public List<ScanBody> getDate() {
        return date;
    }

    public void setDate(List<ScanBody> date) {
        this.date = date;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public List getFamily() {
        return family;
    }

    public void setFamily(List family) {
        this.family = family;
    }
}
