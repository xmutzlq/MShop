package com.king.android.details.util;

import google.architecture.coremodel.data.xlj.goodsdetail.List;

/**
 * @author lq.zeng
 * @date 2018/12/27
 */

public class SpecParams {

    //颜色
    public SpecChild specsColor;
    //号码
    public SpecChild specsSize;

    //默认选中的号码ID
    public String defaultSizeId;
    //默认选中的颜色ID
    public String defaultColorId;

    public String img;
    public String price;

    public static class SpecChild {
        public String name;
        public java.util.List<List> detailSpecInfos;
    }
}
