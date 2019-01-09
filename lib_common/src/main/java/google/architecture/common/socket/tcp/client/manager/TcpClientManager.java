package google.architecture.common.socket.tcp.client.manager;

import java.util.HashSet;
import java.util.Set;

import google.architecture.common.socket.tcp.client.DeHongTcpClient;
import google.architecture.common.socket.tcp.client.bean.TargetInfo;


/**
 * tcpclient的管理者
 */
public class TcpClientManager {
    private static Set<DeHongTcpClient> sMXTcpClients = new HashSet<DeHongTcpClient>();

    public static void putTcpClient(DeHongTcpClient XTcpClient) {
        sMXTcpClients.add(XTcpClient);
    }

    public static DeHongTcpClient getTcpClient(TargetInfo targetInfo) {
        for (DeHongTcpClient tc : sMXTcpClients) {
            if (tc.getTargetInfo().equals(targetInfo)) {
                return tc;
            }
        }
        return null;
    }
    
    public static void clearClients() {
    	if(sMXTcpClients != null) {
    		sMXTcpClients.clear();
    	}
    }
}
