package google.architecture.coremodel.datamodel.http.exception;

/**
 * @author lq.zeng
 * @date 2018/4/12
 */

public class ServerException extends Error{

    public String code;
    public String message;

    public ServerException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
