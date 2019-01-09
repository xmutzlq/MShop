package google.architecture.common.socket;

import com.apkfuns.logutils.LogUtils;

import google.architecture.common.socket.tcp.client.bean.TargetInfo;
import google.architecture.common.socket.tcp.client.bean.TcpMsg;
import google.architecture.common.socket.udp.client.DeHongUdp;
import google.architecture.common.socket.udp.client.UdpClientConfig;
import google.architecture.common.socket.udp.client.bean.UdpMsg;
import google.architecture.common.socket.udp.client.listener.UdpClientListener;
import google.architecture.common.util.NetworkUtils;

/**
 * @author lq.zeng
 * @date 2019/1/9
 */

public class XljUpdManager implements UdpClientListener {

    private static XljUpdManager instance;
    private DeHongUdp mUdp;
    private boolean isUseServer;

    public static XljUpdManager getInstance() {
        if (instance == null) {
            instance = new XljUpdManager();
        }
        return instance;
    }

    /**建立客户端通信**/
    public void clientContribute() {
        isUseServer = false;
        String ipAddress = NetworkUtils.getIPAddress(true);
        int port = 594666;
        TargetInfo targetInfo;
        targetInfo = new TargetInfo(ipAddress, port);
        if (mUdp == null) {
            mUdp = DeHongUdp.getUdpClient();
            mUdp.addUdpClientListener(this);
        }
        mUdp.config(new UdpClientConfig.Builder()
                .setLocalPort(8989).create());

        String firstMsg = "";//这边需要处理
        mUdp.sendMsg(new UdpMsg(firstMsg, targetInfo, TcpMsg.MsgType.Send), true);
    }

    /**建立客户端通信**/
    public void serverContribute() {
        isUseServer = false;
        if (mUdp != null && mUdp.isUdpServerRuning()) {
            mUdp.stopUdpServer();
        } else {
            if (mUdp == null) {
                int port = 594666;
                mUdp = mUdp.getUdpClient();
                mUdp.config(new UdpClientConfig.Builder().setLocalPort(port).create());
                mUdp.addUdpClientListener(this);
            }
            mUdp.startUdpServer();
        }
    }

    public void stopUdpServer(){
        if (mUdp != null) {
            mUdp.removeUdpClientListener(this);
            mUdp.stopUdpServer();
        }
    };

    @Override
    public void onStarted(DeHongUdp XUdp) {
        LogUtils.tag("zlq").e("udp服务开启");
    }

    @Override
    public void onStoped(DeHongUdp XUdp) {
        LogUtils.tag("zlq").e("udp服务关闭");
    }

    @Override
    public void onSended(DeHongUdp XUdp, UdpMsg udpMsg) {
        LogUtils.tag("zlq").e("我：" + udpMsg.getSourceDataString());
    }

    @Override
    public void onReceive(DeHongUdp client, UdpMsg udpMsg) {
        LogUtils.tag("zlq").e("收到消息：" + udpMsg.getSourceDataString());
        if(isUseServer) {
            client.sendMsg(new UdpMsg("", udpMsg.getTarget(), TcpMsg.MsgType.Send)); //这边需要处理
        }
    }

    @Override
    public void onError(DeHongUdp client, String msg, Exception e) {
        LogUtils.tag("zlq").e("onError：" + client + msg + e);
    }
}
