package google.architecture.coremodel.data;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/4/10
 */

public class StartInfo {
    /**手机短信注册，1-开启**/
    public String sys_sms_open;
    /**邮箱注册，1-开启**/
    public String sys_emailset_open;
    /**qq快捷登录，1-开启**/
    public String sys_quick_login_qq_open;
    /**微信快捷登录，1-开启**/
    public String sys_quick_login_wx_open;
    /**新浪微博快捷登录，1-开启**/
    public String sys_quick_login_xlwb_open;

    /**主题包地址**/
    public String sys_theme;

    public SplashInfo flashing_page;

    public static class SplashInfo {
        public List<String> img;
        public String url;
        public String countdown;
        public String skip;

        @Override
        public String toString() {
            return "SplashInfo{" +
                    "img=" + img +
                    ", url='" + url + '\'' +
                    ", countdown='" + countdown + '\'' +
                    ", skip='" + skip + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "StartInfo{" +
                "sys_sms_open='" + sys_sms_open + '\'' +
                ", sys_emailset_open='" + sys_emailset_open + '\'' +
                ", sys_quick_login_qq_open='" + sys_quick_login_qq_open + '\'' +
                ", sys_quick_login_wx_open='" + sys_quick_login_wx_open + '\'' +
                ", sys_quick_login_xlwb_open='" + sys_quick_login_xlwb_open + '\'' +
                ", sys_theme='" + sys_theme + '\'' +
                ", flashing_page=" + flashing_page.toString() +
                '}';
    }
}
