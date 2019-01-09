package google.architecture.common.socket.utils;

/**
 * 异常
 */
public class ExceptionUtils {
    private static final String TAG = "DeHongAndroidSocket";

    public static void throwException(String message) {
        throw new IllegalStateException(TAG + " : " + message);
    }
}
