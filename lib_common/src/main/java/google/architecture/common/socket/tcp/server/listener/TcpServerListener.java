package google.architecture.common.socket.tcp.server.listener;


import google.architecture.common.socket.tcp.client.DeHongTcpClient;
import google.architecture.common.socket.tcp.client.bean.TcpMsg;
import google.architecture.common.socket.tcp.server.DeHongTcpServer;

/**
 * tcpserver
 */
public interface TcpServerListener {
    void onCreated(DeHongTcpServer server);

    void onListened(DeHongTcpServer server);

    void onAccept(DeHongTcpServer server, DeHongTcpClient tcpClient);

    void onSended(DeHongTcpServer server, DeHongTcpClient tcpClient, TcpMsg tcpMsg);

    void onReceive(DeHongTcpServer server, DeHongTcpClient tcpClient, TcpMsg tcpMsg);

    void onValidationFail(DeHongTcpServer server, DeHongTcpClient client, TcpMsg tcpMsg);

    void onClientClosed(DeHongTcpServer server, DeHongTcpClient tcpClient, String msg, Exception e);

    void onServerClosed(DeHongTcpServer server, String msg, Exception e);
}
