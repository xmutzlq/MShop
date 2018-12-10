package google.architecture.coremodel.datamodel.http.exception;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author lq.zeng
 * @date 2018/4/10
 */

public class CodeException {
    /*网络错误*/
    public static final int NETWORD_ERROR = 0x1;
    /*http_错误*/
    public static final int HTTP_ERROR = 0x2;
    /*fastjson错误*/
    public static final int JSON_ERROR = 0x3;
    /*未知错误*/
    public static final int UNKNOWN_ERROR = 0x4;
    /*运行时异常-包含自定义异常*/
    public static final int RUNTIME_ERROR = 0x5;
    /*无法解析该域名*/
    public static final int UNKOWNHOST_ERROR = 0x6;

    @IntDef({NETWORD_ERROR, HTTP_ERROR, RUNTIME_ERROR, UNKNOWN_ERROR, JSON_ERROR, UNKOWNHOST_ERROR,
            IServerCode.ERROR_TOKEN_EXPIRED, IServerCode.ERROR_HOST_CLOSE})
    @Retention(RetentionPolicy.SOURCE)

    public @interface CodeEp {
    }

    /*后端返回的异常*/
    public static interface IServerCode {
        public static final int ERROR_TOKEN_EXPIRED = -66;
        public static final int ERROR_HOST_CLOSE = -88;
    }
}
