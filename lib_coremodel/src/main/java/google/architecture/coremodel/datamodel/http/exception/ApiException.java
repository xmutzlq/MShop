package google.architecture.coremodel.datamodel.http.exception;

/**
 * 回调统一请求异常
 * @author lq.zeng
 * @date 2018/4/10
 */

public class ApiException extends Throwable {
    /*错误码*/
    private int code;
    /*显示的信息*/
    private String displayMessage;

    public ApiException(Throwable e) {
        super(e);
    }

    public ApiException(Throwable cause,@CodeException.CodeEp int code, String showMsg) {
        super(showMsg, cause);
        setCode(code);
        setDisplayMessage(showMsg);
    }

    @CodeException.CodeEp
    public int getCode() {
        return code;
    }

    public void setCode(@CodeException.CodeEp int code) {
        this.code = code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
