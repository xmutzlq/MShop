package google.architecture.common.socket.tcp.client.helper.validation;

/**
 */
public class BaseValidationHelper implements AbsValidationHelper {
    @Override
    public boolean execute(byte[] msg) {
        return true;
    }
}
