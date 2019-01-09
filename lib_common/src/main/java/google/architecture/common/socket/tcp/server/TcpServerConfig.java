package google.architecture.common.socket.tcp.server;


import google.architecture.common.socket.tcp.client.TcpConnConfig;

/**
 * server配置
 */
public class TcpServerConfig {
    private int maxClientSize = Integer.MAX_VALUE;
    private TcpConnConfig mTcpConnConfig;

    private TcpServerConfig() {
        mTcpConnConfig = new TcpConnConfig.Builder().create();
    }

    public int getMaxClientSize() {
        return maxClientSize;
    }

    public TcpConnConfig getTcpConnConfig() {
        return mTcpConnConfig;
    }

    public static class Builder {
        private TcpServerConfig tcpServerConfig;

        public Builder() {
            tcpServerConfig = new TcpServerConfig();
        }

        public TcpServerConfig create() {
            return tcpServerConfig;
        }

        public Builder setMaxClientSize(int maxSize) {
            tcpServerConfig.maxClientSize = maxSize;
            return this;
        }

        public Builder setTcpConnConfig(TcpConnConfig tcpConnConfig) {
            tcpServerConfig.mTcpConnConfig = tcpConnConfig;
            return this;
        }
    }
}
