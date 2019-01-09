package google.architecture.common.socket.tcp.server.manager;

import java.util.HashSet;
import java.util.Set;

import google.architecture.common.socket.tcp.server.DeHongTcpServer;


/**
 * tcpserver
 */
public class TcpServerManager {
    private static Set<DeHongTcpServer> sMXTcpServers = new HashSet<DeHongTcpServer>();

    public static void putTcpServer(DeHongTcpServer XTcpServer) {
        sMXTcpServers.add(XTcpServer);
    }

    public static DeHongTcpServer getTcpServer(int port) {
        for (DeHongTcpServer ts : sMXTcpServers) {
            if (ts.getPort() == port) {
                return ts;
            }
        }
        return null;
    }
}
