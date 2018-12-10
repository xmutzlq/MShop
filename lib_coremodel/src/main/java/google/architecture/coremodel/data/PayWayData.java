package google.architecture.coremodel.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/9/11
 */

public class PayWayData implements Parcelable{
    @SerializedName("sum_order_money")
    private String sum_order_money; //应支付总金额
    @SerializedName("order_no")
    private String order_no; //平台订单号
    @SerializedName("order_id")
    private String order_id;
    @SerializedName("goods_desc")
    private String goods_desc; //商品名称描述
    @SerializedName("pay_way")
    private List<PayWayItemData> pay_way;

    public PayWayData() {}

    protected PayWayData(Parcel in) {
        sum_order_money = in.readString();
        order_no = in.readString();
        order_id = in.readString();
        goods_desc = in.readString();
        pay_way = in.createTypedArrayList(PayWayItemData.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sum_order_money);
        dest.writeString(order_no);
        dest.writeString(order_id);
        dest.writeString(goods_desc);
        dest.writeTypedList(pay_way);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PayWayData> CREATOR = new Creator<PayWayData>() {
        @Override
        public PayWayData createFromParcel(Parcel in) {
            return new PayWayData(in);
        }

        @Override
        public PayWayData[] newArray(int size) {
            return new PayWayData[size];
        }
    };

    public List<PayWayItemData> getPay_way() {
        return pay_way;
    }

    public void setPay_way(List<PayWayItemData> pay_way) {
        this.pay_way = pay_way;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getSum_order_money() {
        return sum_order_money;
    }

    public void setSum_order_money(String sum_order_money) {
        this.sum_order_money = sum_order_money;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getGoods_desc() {
        return goods_desc;
    }

    public void setGoods_desc(String goods_desc) {
        this.goods_desc = goods_desc;
    }
}
