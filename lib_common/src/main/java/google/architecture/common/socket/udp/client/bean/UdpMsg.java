package google.architecture.common.socket.udp.client.bean;


import google.architecture.common.socket.tcp.client.bean.TargetInfo;
import google.architecture.common.socket.tcp.client.bean.TcpMsg;

/**
 */
public class UdpMsg extends TcpMsg {

    public UdpMsg(byte[] data, TargetInfo target, MsgType type) {
        super(data, target, type);
    }

    public UdpMsg(String data, TargetInfo target, MsgType type) {
        super(data, target, type);
    }

    public UdpMsg(int id) {
        super(id);
    }
}
