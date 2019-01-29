package com.bop.android.shopping;

import android.content.Context;

import java.io.IOException;

import google.architecture.common.util.DeviceUtils;
import google.architecture.coremodel.datamodel.http.ApiClient;
import google.architecture.coremodel.util.MD5Util;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestMessageDeliver {

    public static final String webContext = "http://172.18.1.244/";
//    public static final String webContext = "http://rssi.vaiwan.com:8081/";

    public static void receiveMessageCallback(String msgId){
        String sign = MD5Util.getMD5String(msgId+".xlj20190128");
        String url = webContext+"api/ReceiveMessageCallback?msgId="+msgId+"&sign="+sign;
        OkHttpClient okHttpClient = ApiClient.getOkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"),""))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("======szq======:receiveMessageCallback fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("======szq======:receiveMessageCallback success");
            }
        });
    }

    public static void updateDeviceToken(Context context, String deviceToken){
        String sign = MD5Util.getMD5String(DeviceUtils.getUniqueId(context)+"."+deviceToken+".xlj20190128");
        String url = webContext+"api/UpdateDeviceToken?machineCode="+DeviceUtils.getUniqueId(context)+"&deviceToken="+deviceToken+"&sign="+sign;
        OkHttpClient okHttpClient = ApiClient.getOkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"),""))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("======szq======:updateDeviceToken fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("======szq======:updateDeviceToken success");
            }
        });
    }

}
