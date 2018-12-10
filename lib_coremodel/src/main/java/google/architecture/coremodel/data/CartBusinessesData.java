package google.architecture.coremodel.data;

import google.architecture.coremodel.MultiItemTypeHelper;

/**
 * @author lq.zeng
 * @date 2018/5/10
 */


public class CartBusinessesData extends CartExpandableItem {
    public String businessesId;
    public String businessesName;
    public CartBusinessesData(){}

    public CartBusinessesData(String businessesId, String businessesName) {
        this.businessesId = businessesId;
        this.businessesId = businessesName;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return MultiItemTypeHelper.TYPE_BUSINESSES;
    }
}
