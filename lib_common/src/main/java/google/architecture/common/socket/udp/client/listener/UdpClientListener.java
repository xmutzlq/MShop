package google.architecture.common.socket.udp.client.listener;


import google.architecture.common.socket.udp.client.DeHongUdp;
import google.architecture.common.socket.udp.client.bean.UdpMsg;

/**
 */
public interface UdpClientListener {
    void onStarted(DeHongUdp XUdp);

    void onStoped(DeHongUdp XUdp);

    void onSended(DeHongUdp XUdp, UdpMsg udpMsg);

    void onReceive(DeHongUdp client, UdpMsg udpMsg);

    void onError(DeHongUdp client, String msg, Exception e);

    class SimpleUdpClientListener implements UdpClientListener {

        @Override
        public void onStarted(DeHongUdp XUdp) {

        }

        @Override
        public void onStoped(DeHongUdp XUdp) {

        }

        @Override
        public void onSended(DeHongUdp XUdp, UdpMsg udpMsg) {

        }

        @Override
        public void onReceive(DeHongUdp client, UdpMsg msg) {

        }

        @Override
        public void onError(DeHongUdp client, String msg, Exception e) {

        }
    }

}
