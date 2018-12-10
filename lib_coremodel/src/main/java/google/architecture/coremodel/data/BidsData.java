package google.architecture.coremodel.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BidsData {

    public class IntelligentBidInfo {
        /**预认购**/
        public static final int INTELLIGENT_READY_TO_BUY = 1;
        /**开启**/
        public static final int INTELLIGENT_OPEN_TO_BUY = 2;
        /**认购结束**/
        public static final int INTELLIGENT_CLOSE_TO_BUY = 3;

        /**标ID**/
        @SerializedName("id")
        public String id;
        /**标名称**/
        @SerializedName("title")
        public String title;
        /**产品类型 1鸿福月 2鸿福季 3双季鸿 4鸿利新手 5鸿福年**/
        @SerializedName("autotype")
        public int autotype;
        @SerializedName("money")
        public String money;
        @SerializedName("cycle")
        public int cycle;
        @SerializedName("days")
        public String days;
        @SerializedName("is_out")
        public int is_out;
        /**优惠券是否可用 	1:可用 0：不可用**/
        @SerializedName("is_user_voucher")
        public int is_user_voucher;
        /**标年化率**/
        @SerializedName("rates")
        public String rates;
        /**新手标说明**/
        @SerializedName("rates_msg")
        public String rates_msg;
        /**加成率**/
        @SerializedName("add_rate")
        public String add_rate;
        /**鸿利宝加成**/
        @SerializedName("added_rates")
        public String added_rates;
        /**转让**/
        @SerializedName("cre_msg")
        public String cre_msg;
        /**起投**/
        @SerializedName("min_msg")
        public String min_msg;
        @SerializedName("min")
        public String min;
        @SerializedName("max")
        public String max;
        @SerializedName("surplus")
        public String surplus;
        @SerializedName("content")
        public String content;
        @SerializedName("saletime")
        public String saletime;
        @SerializedName("sourcechannel")
        public String sourcechannel;
        @SerializedName("productchannel")
        public String productchannel;
        /**鸿利宝状态 0待审核 1预认购 2开启 3认购结束 4审核失败**/
        @SerializedName("status")
        public int status;
        @SerializedName("number")
        public int number;
        @SerializedName("useramount")
        public String useramount;
        @SerializedName("addtime")
        public String addtime;
        @SerializedName("edittime")
        public String edittime;

        @SerializedName("end_day")
        public String end_day;

        /**含2.40%质保服务专款**/
        @SerializedName("pay_msg")
        public String pay_msg;

        @SerializedName("way_msg")
        public String way_msg;

        @SerializedName("add_rate_msg")
        public String add_rate_msg;

        @SerializedName("process")
        public ArrayList<String> process;

        /**已投过**/
        @SerializedName("newuser")
        public int newuser;

//	  0--默认0--无竞猜,其他有竞猜
//    1--有竞猜，可参与
//    2--有竞猜，不可参与（已参与）,
//    3--有竞猜，不可参与（其他原因）

        @SerializedName("guess_exits")
        public int guessExits;

        @SerializedName("guess_url")
        public String guessUrl;

        @SerializedName("is_turntable")
        public int isTurntable;
        @SerializedName("turntable_url")
        public String turntableUrl;
        @SerializedName("activity_exits")
        public int activityExits;
        @SerializedName("activity_url")
        public String activityUrl;
        @SerializedName("activity_msg")
        public String activityMsg;
        @SerializedName("activity_img_2x")
        public String activity_img_2x;
        @SerializedName("activity_img_3x")
        public String activity_img_3x;
        @SerializedName("surplus_msg")
        public String surplusMsg;

        @SerializedName("allmoney")
        public String allmoney;
        @SerializedName("brief_msg")
        public String briefMsg;
    }
}
