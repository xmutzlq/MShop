package com.king.android.details.util;

import google.architecture.coremodel.data.xlj.goodsdetail.List;

/**
 * @author lq.zeng
 * @date 2018/12/25
 */

public class DetailUtil {
    public static String getDefaultColor(java.util.List<List> colors, String colorId) {
        if(colors == null || colors.size() == 0) return "白色";
        for (List color : colors) {
            if(colorId.equals(color.getItemId())) {
                return color.getItemName();
            }
        }
        return "白色";
    }

    public static String getDefaultSize(java.util.List<List> sizes, String sizeId) {
        if(sizes == null || sizes.size() == 0) return "0";
        for (List size : sizes) {
            if(sizeId.equals(size.getItemId())) {
                return size.getItemName();
            }
        }
        return "0";
    }
}
