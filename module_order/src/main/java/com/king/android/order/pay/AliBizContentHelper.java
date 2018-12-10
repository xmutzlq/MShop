package com.king.android.order.pay;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import google.architecture.common.pay.ali.OrderInfoUtil2_0;

/**
 * @author lq.zeng
 * @date 2018/9/12
 */

public class AliBizContentHelper {

    public static String getOrderInfo(String appId, String privateKey, String notifyUrl, BizContentParams bizContentParams, boolean rsa2) {
        String bizContent = null;
        try {
            bizContent = makeBizContent(bizContentParams);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(appId, bizContent, notifyUrl, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String key = rsa2 ? privateKey : privateKey;
        String sign = OrderInfoUtil2_0.getSign(params, key, rsa2);
        return orderParam + "&" + sign;
    }

    private static String makeBizContent(BizContentParams params) throws UnsupportedEncodingException {
        OrderInfoUtil2_0.BizContent bizContent = new OrderInfoUtil2_0.BizContent();
        bizContent.timeout_express = "30m";
        bizContent.product_code = "QUICK_MSECURITY_PAY";
        bizContent.total_amount = params.totalAmount;
        bizContent.subject = params.goodsDes;
        bizContent.body = params.goodsDes;
        bizContent.out_trade_no = params.orderNo;
        bizContent.passback_params = URLEncoder.encode(params.passbackParams, "UTF-8");
        Gson gson = new Gson();
        return gson.toJson(bizContent);
    }

    public static class BizContentParams {
        public String totalAmount;
        public String goodsDes;
        public String orderNo;
        public String passbackParams;
    }
}
