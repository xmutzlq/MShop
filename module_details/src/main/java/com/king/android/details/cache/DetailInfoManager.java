package com.king.android.details.cache;

import google.architecture.coremodel.data.DetailMainInfo;
import google.architecture.coremodel.data.DetailSpecSelectedInfo;
import google.architecture.coremodel.util.TextUtil;

/**
 * @author lq.zeng
 * @date 2018/9/3
 */

public class DetailInfoManager {

    private DetailMainInfo info;

    public void bindData(DetailMainInfo info) {
        this.info = info;
    }

    public DetailMainInfo getDetailInfo() {
        return info;
    }

    public String getGoodsId() {
        if(info != null && info.getItem() != null) {
            return info.getItem().getGoods_id();
        }
        return "";
    }

    public String getItemsId() {
        if(info != null && info.getSpec_select_info() != null) {
            return info.getSpec_select_info().getItem_ids();
        }
        return "";
    }

    public boolean hasFavorites() {
        if(info != null && info.getItem() != null
                && !TextUtil.isEmpty(info.getItem().getGoods_user_collect())) {
            return Integer.valueOf(info.getItem().getGoods_user_collect()) > 0;
        }
        return false;
    }

    public void setHasFavorites(int favorites) {
        if(info != null && info.getItem() != null) {
            info.getItem().setGoods_user_collect(String.valueOf(favorites));
        }
    }

    public void updateSpecSelected(DetailSpecSelectedInfo spec_select_info) {
        if(info != null) {
            info.setSpec_select_info(spec_select_info);
        }
    }
}
