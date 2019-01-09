package google.architecture.common.socket.tcp.client.bean;


import android.text.TextUtils;

import google.architecture.common.socket.utils.ExceptionUtils;
import google.architecture.common.socket.utils.StringValidationUtils;

/**
 * @author zlq
 * @date 2017年5月10日 下午6:10:17
 */
public class TargetInfo {
    private String ip;
    private int port;

    public TargetInfo(String ip, int port) {
    	if(!TextUtils.isEmpty(ip) && ip.contains("http://")) {
    		this.ip = ip.substring("http://".length(), ip.length());
    	} else {
    		this.ip = ip;
    	}
        this.port = port;
        check();
    }

    private void check() {
        if (!StringValidationUtils.validateRegex(port + "", StringValidationUtils.RegexPort)) {
            ExceptionUtils.throwException("port 格式不合法");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TargetInfo that = (TargetInfo) o;

        if (port != that.port) return false;
        return ip != null ? ip.equals(that.ip) : that.ip == null;

    }

    @Override
    public int hashCode() {
        int result = ip != null ? ip.hashCode() : 0;
        result = 31 * result + port;
        return result;
    }

    @Override
    public String toString() {
        return "TargetInfo{" +
                "ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                '}';
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
