package google.architecture.coremodel.data.xlj;

import com.google.gson.annotations.SerializedName;

public class TecentTicket {

    @SerializedName("errcode")
    private int errcode;

    @SerializedName("errmsg")
    private String errmsg;

    @SerializedName("ticket")
    private String ticket;

    @SerializedName("expires_in")
    private int expiresIn;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
