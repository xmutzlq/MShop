package google.architecture.coremodel.datamodel.http;

import com.apkfuns.logutils.LogUtils;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import google.architecture.coremodel.Account;
import google.architecture.coremodel.BuildConfig;
import google.architecture.coremodel.util.MD5Util;

/**
 *  一个排序的Map用于组装网络请求参数
 * @author lq.zeng
 * @date 2018/4/8
 */

public class PostValues {
    private Map<String, String> mPostValues; //组装器

    public PostValues() {
        mPostValues = new TreeMap<>((lhs, rhs) -> lhs.compareTo(rhs));
    }

    public Map<String, String> postValues() {
        return mPostValues;
    }

    public void put(String key, String value) {
        mPostValues.put(key, value);
    }

    public int size() { return mPostValues.size(); }

    /**
     * 公共部分参数
     */
    public void makeCommParams() {
        mPostValues.put("app_key", BuildConfig.APP_KEY);
        mPostValues.put("access_token", Account.get().getAcessToken());
        mPostValues.put("version_code", BuildConfig.APP_VERSION_CODE);
        mPostValues.put("platform", "android");
        mPostValues.put("device_code", "1");
    }

    /**
     * Post参数组装
     */
    public PostValues preparePost() {
        // 时间戳
        Long mTimestamp = System.currentTimeMillis() / 1000;
        mPostValues.put("timestamp", mTimestamp + "");
        //构建MD5加密
        Set<Map.Entry<String, String>> entrySet = mPostValues.entrySet();
        // Mac 参数构建macStr
        StringBuilder macStr = new StringBuilder();
        for (Map.Entry<String, String> entry : entrySet) {
            String key = entry.getKey();
            String value = entry.getValue();
            macStr.append(key);
            macStr.append("=");
            macStr.append(value);
            macStr.append("&");
        }
        macStr.deleteCharAt(macStr.length() - 1);
        String token_key = BuildConfig.APP_TOKEN_KEY;
        macStr.append("&key=" + token_key);
        LogUtils.tag("zlq").e("getMD5String = " + macStr.toString());
        String md5Str = MD5Util.getMD5String(macStr.toString());
        mPostValues.put("signature", md5Str);
        return this;
    }

    public void printLog() {
        StringBuilder sb = new StringBuilder("parameters:{\n");
        for(Map.Entry<String, String> entry : mPostValues.entrySet()){
            sb.append("[key:"+entry.getKey()+ ", value:" + entry.getValue()+"]\n");
        }
        sb.append("}");
        LogUtils.tag("zlq").e(sb.toString());
    }
}
