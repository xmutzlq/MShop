package com.king.android.details.util;

import google.architecture.coremodel.data.xlj.goodsdetail.List;

/**
 * @author lq.zeng
 * @date 2018/12/27
 */

public class SpecParams {

    public java.util.List<SpecChild> specs;
    public String img;
    public String price;
    public String totalSave;
    public String shopSave;
    public String chooseStr;

    public static class SpecChild {
        public String name;
        public java.util.List<List> detailSpecInfos;
    }
}
