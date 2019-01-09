package google.architecture.common.socket.tcp.client.helper.decode;


import google.architecture.common.socket.tcp.client.TcpConnConfig;
import google.architecture.common.socket.tcp.client.bean.TargetInfo;

public class BaseDecodeHelper implements AbsDecodeHelper {
    @Override
    public byte[][] execute(byte[] data, TargetInfo targetInfo, TcpConnConfig tcpConnConfig) {
        return new byte[][]{data};
    }
}
