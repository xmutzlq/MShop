package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/9/13
 */

public class MyOrderData {
    @SerializedName("order_list")
    private List<MyOrderShop> order_list;
    @SerializedName("cancel_reason_list")
    private List<String> cancel_reason_list;

    @SerializedName("order_total")
    private int order_total;
    @SerializedName("order_pages")
    private int order_pages;

    public List<MyOrderShop> getOrder_list() {
        return order_list;
    }

    public void setOrder_list(List<MyOrderShop> order_list) {
        this.order_list = order_list;
    }

    public List<String> getCancel_reason_list() {
        return cancel_reason_list;
    }

    public void setCancel_reason_list(List<String> cancel_reason_list) {
        this.cancel_reason_list = cancel_reason_list;
    }

    public int getOrder_total() {
        return order_total;
    }

    public void setOrder_total(int order_total) {
        this.order_total = order_total;
    }

    public int getOrder_pages() {
        return order_pages;
    }

    public void setOrder_pages(int order_pages) {
        this.order_pages = order_pages;
    }
}
