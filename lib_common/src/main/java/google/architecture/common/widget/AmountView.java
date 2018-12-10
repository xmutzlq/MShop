package google.architecture.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import google.architecture.common.R;

/**
 * 自定义组件：购买数量，带减少增加按钮
 */
public class AmountView extends LinearLayout implements View.OnClickListener {

    private static final String cart_decrease = "cart_decrease";
    private static final String cart_increase = "cart_increase";
    public static final String STATE_PLUS = "plus";
    public static final String STATE_MINUS = "minus";

    private int amount = 1; //购买数量
    private int goods_storage = 200; //商品库存
    private String state; //加或减

    private TextView etAmount;
    private Button btnDecrease;
    private Button btnIncrease;

    private IAmountChange amountChangeListener;
    private OnClickListener mClickListener;

    public interface IAmountChange {
        void onAmountChange(String state, int count);
    }

    public AmountView(Context context) {
        this(context, null);
    }

    public AmountView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_amount, this);
        etAmount = (TextView) findViewById(R.id.etAmount);
        btnDecrease = (Button) findViewById(R.id.btnDecrease);
        btnDecrease.setTag(cart_decrease);
        btnIncrease = (Button) findViewById(R.id.btnIncrease);
        btnIncrease.setTag(cart_increase);
        btnDecrease.setOnClickListener(this);
        btnIncrease.setOnClickListener(this);

        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.AmountView);
        int btnWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_cart_btnWidth, LayoutParams.WRAP_CONTENT);
        int tvWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_cart_tvWidth, 80);
        int tvTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_cart_tvTextSize, 0);
        int btnTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_cart_btnTextSize, 0);
        obtainStyledAttributes.recycle();

        LayoutParams btnParams = new LayoutParams(btnWidth, LayoutParams.MATCH_PARENT);
        btnDecrease.setLayoutParams(btnParams);
        btnIncrease.setLayoutParams(btnParams);
        if (btnTextSize != 0) {
            btnDecrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
            btnIncrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
        }

        LayoutParams textParams = new LayoutParams(tvWidth, LayoutParams.MATCH_PARENT);
        etAmount.setLayoutParams(textParams);
        if (tvTextSize != 0) {
            etAmount.setTextSize(tvTextSize);
        }

        etAmount.setOnClickListener(v->{
            if(mClickListener != null) {
                mClickListener.onClick(v);
            }
        });

    }

    public void setGoods_storage(int goods_storage) {
        this.goods_storage = goods_storage;
    }

    public void setAmount(int count) {
        etAmount.setText(count + "");
        amount = count;
        if(count > 1) {
            btnDecrease.setEnabled(true);
            btnDecrease.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_404040));
        } else {
            btnDecrease.setEnabled(false);
            btnDecrease.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_cc));
        }

        if(count < goods_storage) {
            btnIncrease.setEnabled(true);
            btnIncrease.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_404040));
        } else {
            btnIncrease.setEnabled(false);
            btnIncrease.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_cc));
        }
    }

    public void setAmountChangeListener(IAmountChange amountChangeListener) {
        this.amountChangeListener = amountChangeListener;
    }

    public void setClickListener(OnClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        String tag = (String)v.getTag();
        if (cart_decrease.equals(tag)) {
            btnIncrease.setEnabled(true);
            btnIncrease.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_404040));
            if (amount > 1) {
                btnDecrease.setEnabled(true);
                btnDecrease.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_404040));
                amount--;
                etAmount.setText(amount + "");
                state = STATE_MINUS;
                if(amount <= 1) {
                    btnDecrease.setEnabled(false);
                    btnDecrease.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_cc));
                }
            } else {
                btnDecrease.setEnabled(false);
                btnDecrease.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_cc));
            }
        } else if (cart_increase.equals(tag)) {
            btnDecrease.setEnabled(true);
            btnDecrease.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_404040));
            if (amount < goods_storage) {
                btnIncrease.setEnabled(true);
                btnIncrease.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_404040));
                amount++;
                etAmount.setText(amount + "");
                state = STATE_PLUS;
                if (amount >= goods_storage) {
                    btnIncrease.setEnabled(false);
                    btnIncrease.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_cc));
                }
            } else {
                btnIncrease.setEnabled(false);
                btnIncrease.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_cc));
            }
        }

        if(amountChangeListener != null) {
            amountChangeListener.onAmountChange(state, amount);
        }
    }
}
