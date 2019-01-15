package com.king.android.details.cache;

import java.util.List;

import google.architecture.coremodel.data.DetailSpecListInfo;
/**
 * @author lq.zeng
 * @date 2018/7/12
 */

public class SpecData {

    public static final int TYPE_COLOR = 1; //颜色
    public static final int TYPE_COUNT = 2; //数量
    public static final int TEXT_SERVICE = 3; //服务
    public static final int TEXT_CONFIRM = 4; //确定

    public int type; //类型
    public String name; //名称
    public int count; //购买数量

    public java.util.List<google.architecture.coremodel.data.xlj.goodsdetail.List> item1;

    public List<DetailSpecListInfo> colors;
    public List<SpecService> services;

    public static class SpecService {
        public String sIcoUrl;
        public String sTitle;
        public String sDetailUrl;
        public List<String> sServiceName;
    }
}
