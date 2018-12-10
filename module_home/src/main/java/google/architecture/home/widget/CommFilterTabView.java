package google.architecture.home.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import google.architecture.home.R;

/**
 * @author lq.zeng
 * @date 2018/5/23
 */

public class CommFilterTabView extends FrameLayout implements View.OnClickListener {

    public static final String TAB_COLLIGATION = "tab_colligation";
    public static final String TAB_SALES_VOLUME = "tab_sales_volume";
    public static final String TAB_PRICE = "tab_price";
    public static final String TAB_FILTER = "tab_filter";

    private View rootView;
    private ViewGroup tvColligation, tvSalesVolume, tvPrice, tvFilter;
    private boolean isPriceToggle;
    private boolean isDrawerOpen;

    private String currentTag = TAB_COLLIGATION;

    private TabClickListener mTabClickListener;
    private FilterOpenListener mFilterOpenListener;

    public CommFilterTabView(Context context) {
        super(context);
        init();
        initUI();
    }

    public CommFilterTabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initUI();
    }

    public String getCurrentTag() {
        return currentTag;
    }

    public boolean isDrawerOpen() {
        return isDrawerOpen;
    }

    public void setFilterOpenListener(FilterOpenListener listener) {
        mFilterOpenListener = listener;
    }

    public void setTabClickListener(TabClickListener listener) {
        mTabClickListener = listener;
    }

    private void init() {
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.tab_goods_filter, this, true);
    }

    private void initUI() {
        tvColligation = rootView.findViewById(R.id.btn_tab_colligation);
        tvSalesVolume = rootView.findViewById(R.id.btn_tab_sales_volume);
        tvPrice = rootView.findViewById(R.id.btn_tab_price);
        tvFilter = rootView.findViewById(R.id.btn_tab_filter);

        tvColligation.setTag(TAB_COLLIGATION);
        tvSalesVolume.setTag(TAB_SALES_VOLUME);
        tvPrice.setTag(TAB_PRICE);
        tvFilter.setTag(TAB_FILTER);

        tvColligation.setOnClickListener(this);
        tvSalesVolume.setOnClickListener(this);
        tvPrice.setOnClickListener(this);
        tvFilter.setOnClickListener(this);

        pressedTabColligation(true);
        pressedSalesVolume(false);
        pressedPrice(false, false);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        switch (tag) {
            case TAB_COLLIGATION:
                currentTag = tag;
                isPriceToggle = true;
                pressedTabColligation(true);
                pressedSalesVolume(false);
                pressedPrice(false, false);
                if(mTabClickListener != null) {
                    mTabClickListener.onTabClick(true, currentTag);
                }
                break;
            case TAB_SALES_VOLUME:
                currentTag = tag;
                isPriceToggle = true;
                pressedTabColligation(false);
                pressedSalesVolume(true);
                pressedPrice(false, false);
                if(mTabClickListener != null) {
                    mTabClickListener.onTabClick(true, currentTag);
                }
                break;
            case TAB_PRICE:
                currentTag = tag;
                pressedTabColligation(false);
                pressedSalesVolume(false);
                isPriceToggle = !isPriceToggle;
                pressedPrice(true, isPriceToggle);
                if(mTabClickListener != null) {
                    mTabClickListener.onTabClick(true, currentTag);
                }
                break;
            case TAB_FILTER:
                isDrawerOpen = true;
                if(mFilterOpenListener != null) mFilterOpenListener.onFilterOpenListener();
                break;
        }
    }

    private void pressedTabColligation(boolean isPressed) {
        ((TextView)tvColligation.getChildAt(0)).setSelected(isPressed);
        if(isPressed) {
            ((TextView)tvColligation.getChildAt(0)).setCompoundDrawablesWithIntrinsicBounds(0,0, R.mipmap.ic_filter_selected_down, 0);
        } else {
            ((TextView)tvColligation.getChildAt(0)).setCompoundDrawablesWithIntrinsicBounds(0,0, R.mipmap.ic_filter_unselect_down, 0);
        }
    }

    private void pressedSalesVolume(boolean isPressed) {
        ((TextView)tvSalesVolume.getChildAt(0)).setSelected(isPressed);
    }

    private void pressedPrice(boolean isPressed, boolean isPriceToggle) {
        ((TextView)tvPrice.getChildAt(0)).setSelected(isPressed);
        if(isPressed) {
            if(isPriceToggle) {
                ((TextView)tvPrice.getChildAt(0)).setCompoundDrawablesWithIntrinsicBounds(0,0, R.mipmap.ic_filter_full_down, 0);
            } else {
                ((TextView)tvPrice.getChildAt(0)).setCompoundDrawablesWithIntrinsicBounds(0,0, R.mipmap.ic_filter_full_up, 0);
            }
        } else {
            ((TextView)tvPrice.getChildAt(0)).setCompoundDrawablesWithIntrinsicBounds(0,0, R.mipmap.ic_filter_full_unselect, 0);
        }
    }

    public interface FilterOpenListener {
        void onFilterOpenListener();
    }

    public interface TabClickListener {
        public void onTabClick(boolean fromBtn, String type);
    }
}
