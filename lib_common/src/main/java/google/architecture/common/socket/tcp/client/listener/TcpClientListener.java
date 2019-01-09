package google.architecture.common.socket.tcp.client.listener;


import google.architecture.common.socket.tcp.client.DeHongTcpClient;
import google.architecture.common.socket.tcp.client.bean.TcpMsg;

/**
 */
public interface TcpClientListener {
    void onConnected(DeHongTcpClient client);

    void onSended(DeHongTcpClient client, TcpMsg tcpMsg);

    void onDisconnected(DeHongTcpClient client, String msg, Exception e);

    void onReceive(DeHongTcpClient client, TcpMsg tcpMsg);

    void onValidationFail(DeHongTcpClient client, TcpMsg tcpMsg);

    class SimpleTcpClientListener implements TcpClientListener {

        @Override
        public void onConnected(DeHongTcpClient client) {

        }

        @Override
        public void onSended(DeHongTcpClient client, TcpMsg tcpMsg) {

        }

        @Override
        public void onDisconnected(DeHongTcpClient client, String msg, Exception e) {

        }

        @Override
        public void onReceive(DeHongTcpClient client, TcpMsg tcpMsg) {

        }

        @Override
        public void onValidationFail(DeHongTcpClient client, TcpMsg tcpMsg) {

        }

    }
}
