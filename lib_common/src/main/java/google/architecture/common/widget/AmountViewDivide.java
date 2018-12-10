package google.architecture.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import google.architecture.common.R;

/**
 * @author lq.zeng
 * @date 2018/5/15
 */

public class AmountViewDivide extends LinearLayout implements View.OnClickListener, TextWatcher {

    private static final String cart_decrease = "cart_decrease";
    private static final String cart_increase = "cart_increase";
    public static final String STATE_PLUS = "plus";
    public static final String STATE_MINUS = "minus";
    public static final String STATE_NONE = "none";

    private int amount = 1; //购买数量
    private int goods_storage = 200; //商品库存
    private String state; //加或减

    private EditText etAmount;
    private Button btnDecrease;
    private Button btnIncrease;

    private AmountView.IAmountChange amountChangeListener;

    public interface IAmountChange {
        void onAmountChange(int count);
    }

    public AmountViewDivide(Context context) {
        this(context, null);
    }

    public AmountViewDivide(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_amount_divide, this);
        etAmount = findViewById(R.id.etAmount_);
        btnDecrease = findViewById(R.id.btnDecrease_);
        btnDecrease.setTag(cart_decrease);
        btnIncrease = findViewById(R.id.btnIncrease_);
        btnIncrease.setTag(cart_increase);
        btnDecrease.setOnClickListener(this);
        btnIncrease.setOnClickListener(this);

        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.AmountView);
        int btnWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_cart_btnWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
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

        etAmount.addTextChangedListener(this);
        etAmount.setOnClickListener(v->{ });
    }

    public void setGoods_storage(int goods_storage) {
        this.goods_storage = goods_storage;
    }

    public void setAmount(int count) {
        if(count < 1) return;
        etAmount.setText(count + "");
        amount = count;
        if(count > 1) {
            btnDecrease.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_404040));
        } else {
            btnDecrease.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_cc));
        }

        if(count < goods_storage) {
            btnIncrease.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_404040));
        } else {
            btnIncrease.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_cc));
        }
    }

    public int getAmount() {
        return amount;
    }

    public void setAmountChangeListener(AmountView.IAmountChange amountChangeListener) {
        this.amountChangeListener = amountChangeListener;
    }

    @Override
    public void onClick(View v) {
        String tag = (String)v.getTag();
        if (cart_decrease.equals(tag)) {
            btnIncrease.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_404040));
            if (amount > 1) {
                btnDecrease.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_404040));
                amount--;
                etAmount.setText(amount + "");
                state = STATE_MINUS;
                if(amount <= 1) {
                    btnDecrease.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_cc));
                }
            } else {
                btnDecrease.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_cc));
            }
        } else if (cart_increase.equals(tag)) {
            btnDecrease.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_404040));
            if (amount < goods_storage) {
                btnIncrease.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_404040));
                amount++;
                etAmount.setText(amount + "");
                state = STATE_PLUS;
                if (amount >= goods_storage) {
                    btnIncrease.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_cc));
                }
            } else {
                btnIncrease.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_cc));
            }
        }

        if(amountChangeListener != null) {
            amountChangeListener.onAmountChange(state, amount);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().isEmpty()) {
            btnIncrease.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_404040));
            btnDecrease.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_cc));
            return;
        }
        int value = Integer.valueOf(s.toString());
        if (value >= goods_storage) {
            btnIncrease.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_cc));
            btnDecrease.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_404040));
        } else if(value <= 1) {
            btnIncrease.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_404040));
            btnDecrease.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_cc));
        } else {
            btnIncrease.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_404040));
            btnDecrease.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_404040));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        state = STATE_NONE;
        if (s.toString().isEmpty()) return;
        if (s.length() == 1 && s.toString().equals("0")) {
            etAmount.setText("");
            return;
        }

        amount = Integer.valueOf(s.toString());
        if (amount > goods_storage) {
            etAmount.setText(goods_storage + "");
            etAmount.setSelection(etAmount.getText().length());
            return;
        }

        if(String.valueOf(s.toString().charAt(0)).equals("0")) {
            etAmount.setText(s.toString().substring(1, s.toString().length()));
            return;
        }

        if(amountChangeListener != null) {
            amountChangeListener.onAmountChange(state, amount);
        }
    }
}
