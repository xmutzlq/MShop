package google.architecture.home.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.util.DimensionsUtil;
import google.architecture.common.widget.BackgroundDarkPopupWindow;
import google.architecture.home.R;
import google.architecture.home.adapter.DefaultFilterAdapter;

/**
 * @author lq.zeng
 * @date 2018/5/23
 */

public class CommFilterTabView extends FrameLayout implements View.OnClickListener {

    public static final String TAB_COLLIGATION = "tab_colligation";
    public static final String TAB_SALES_VOLUME = "tab_sales_volume";
    public static final String TAB_PRICE = "tab_price";

    public static final String TAB_DEFAULT = "tab_default"; //默认
    public static final String TAB_NEWEST = "tab_newest"; //新品
    public static final String TAB_HOT = "tab_hot"; //人气
    public static final String TAB_EXCHANGE = "tab_exchange"; //L&G交换
    public static final String TAB_FILTER = "tab_filter"; //过滤

    private BackgroundDarkPopupWindow popWindow;

    private View rootView;
    private ViewGroup tvColligation, tvSalesVolume, tvPrice, tvExchange, tvFilter;
    private TextView defaultFilterFlag;

    private boolean isPriceToggle;
    private boolean isExchangeToggle;
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
        makeDefaultPopupWindow();
    }

    private void initUI() {
        tvColligation = rootView.findViewById(R.id.btn_tab_colligation);
        tvSalesVolume = rootView.findViewById(R.id.btn_tab_sales_volume);
        tvPrice = rootView.findViewById(R.id.btn_tab_price);
        tvExchange = rootView.findViewById(R.id.btn_tab_exchange);
        tvFilter = rootView.findViewById(R.id.btn_tab_filter);

        defaultFilterFlag = rootView.findViewById(R.id.filter_default_flag);

        tvColligation.setTag(TAB_DEFAULT);
        tvSalesVolume.setTag(TAB_NEWEST);
        tvPrice.setTag(TAB_HOT);
        tvExchange.setTag(TAB_EXCHANGE);
        tvFilter.setTag(TAB_FILTER);

        tvColligation.setOnClickListener(this);
        tvSalesVolume.setOnClickListener(this);
        tvPrice.setOnClickListener(this);
        tvExchange.setOnClickListener(this);
        tvFilter.setOnClickListener(this);

        changeDefaultFilterFlag(true, true);
        pressedTabColligation(true);
        pressedSalesVolume(false);
        pressedPrice(false, false);
        pressedTabFilter(false);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        switch (tag) {
            case TAB_DEFAULT:
                currentTag = tag;
                isPriceToggle = true;
                pressedTabColligation(true);
                pressedSalesVolume(false);
                pressedPrice(false, false);
                changeDefaultFilterFlag(false, false);
                pressedTabFilter(false);
                showPopWindow();
                break;
            case TAB_NEWEST:
                currentTag = tag;
                isPriceToggle = true;
                pressedTabColligation(false);
                pressedSalesVolume(true);
                pressedPrice(false, false);
                pressedTabFilter(false);
                changeDefaultFilterFlag(true, false);
                if(mTabClickListener != null) {
                    mTabClickListener.onTabClick(true, currentTag);
                }
                break;
            case TAB_HOT:
                currentTag = tag;
                pressedTabColligation(false);
                pressedSalesVolume(false);
                isPriceToggle = !isPriceToggle;
                pressedPrice(true, isPriceToggle);
                pressedTabFilter(false);
                changeDefaultFilterFlag(true, false);
                if(mTabClickListener != null) {
                    mTabClickListener.onTabClick(true, currentTag);
                }
                break;
            case TAB_EXCHANGE:
                currentTag = tag;
//                isPriceToggle = true;
                isExchangeToggle = !isExchangeToggle;
//                pressedTabColligation(false);
//                pressedSalesVolume(false);
//                pressedPrice(false, false);
//                pressedTabFilter(false);
                selectedTabExchange();
                if(mTabClickListener != null) {
                    mTabClickListener.onTabClick(true, currentTag);
                }
                break;
            case TAB_FILTER:
                isDrawerOpen = true;
//                pressedTabFilter(true);
//                changeDefaultFilterFlag(true, false);
                if(mFilterOpenListener != null) mFilterOpenListener.onFilterOpenListener();
                break;
        }
    }

    private void pressedTabColligation(boolean isPressed) {
        ((TextView)tvColligation.getChildAt(0)).setSelected(isPressed);
//        if(isPressed) {
//            ((TextView)tvColligation.getChildAt(0)).setCompoundDrawablesWithIntrinsicBounds(0,0, R.mipmap.ic_filter_selected_down, 0);
//        } else {
//            ((TextView)tvColligation.getChildAt(0)).setCompoundDrawablesWithIntrinsicBounds(0,0, R.mipmap.ic_filter_unselect_down, 0);
//        }
    }

    private void pressedTabFilter(boolean isPressed) {
        tvFilter.getChildAt(0).setSelected(isPressed);
        if(isPressed) {
            ((TextView)tvFilter.getChildAt(0)).setCompoundDrawablesWithIntrinsicBounds(0,0, R.mipmap.ic_filter_selected, 0);
        } else {
            ((TextView)tvFilter.getChildAt(0)).setCompoundDrawablesWithIntrinsicBounds(0,0, R.mipmap.ic_filter, 0);
        }
    }

    private void selectedTabExchange() {
        if(isExchangeToggle) {
            ((ImageView)tvExchange.getChildAt(0)).setImageResource(R.mipmap.ic_list);
        } else {
            ((ImageView)tvExchange.getChildAt(0)).setImageResource(R.mipmap.ic_grid);
        }
    }

    private void pressedSalesVolume(boolean isPressed) {
        ((TextView)tvSalesVolume.getChildAt(0)).setSelected(isPressed);
    }

    private void pressedPrice(boolean isPressed, boolean isPriceToggle) {
        ((TextView)tvPrice.getChildAt(0)).setSelected(isPressed);
//        if(isPressed) {
//            if(isPriceToggle) {
//                ((TextView)tvPrice.getChildAt(0)).setCompoundDrawablesWithIntrinsicBounds(0,0, R.mipmap.ic_filter_full_down, 0);
//            } else {
//                ((TextView)tvPrice.getChildAt(0)).setCompoundDrawablesWithIntrinsicBounds(0,0, R.mipmap.ic_filter_full_up, 0);
//            }
//        } else {
//            ((TextView)tvPrice.getChildAt(0)).setCompoundDrawablesWithIntrinsicBounds(0,0, R.mipmap.ic_filter_full_unselect, 0);
//        }
    }

    private void makeDefaultPopupWindow() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.layout_pop_default_filter,null);
        DefaultFilterAdapter adapter = new DefaultFilterAdapter(getContext());
        ListView listView = contentView.findViewById(R.id.default_filter_lv);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            adapter.selectItem(position);
            if(popWindow != null) popWindow.dismiss();
        });
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        popWindow = new BackgroundDarkPopupWindow(contentView, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        popWindow.setOnDismissListener(() -> changeDefaultFilterFlag(true, true));
        popWindow.setFocusable(true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setAnimationStyle(android.R.style.Animation_Dialog);
    }

    private void showPopWindow() {
        popWindow.setDarkStyle(-1);
        popWindow.setDarkColor(Color.parseColor("#a0000000"));
        popWindow.resetDarkPosition();
        popWindow.darkBelow(rootView);
        popWindow.showAsDropDown(rootView, rootView.getRight() / 2, 0);
    }

    private void changeDefaultFilterFlag(boolean isDownState, boolean isNormal) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)defaultFilterFlag.getLayoutParams();
        if(isNormal) {
            layoutParams.setMargins(DimensionsUtil.dip2px(getContext(), 2), DimensionsUtil.dip2px(getContext(), 3), 0, 0);
            defaultFilterFlag.setBackgroundResource(R.drawable.ic_triangle_down);
        } else {
            if(isDownState) {
                layoutParams.setMargins(DimensionsUtil.dip2px(getContext(), 2), DimensionsUtil.dip2px(getContext(), 3), 0, 0);
                defaultFilterFlag.setBackgroundResource(R.drawable.ic_triangle_down_default);
            } else {
                layoutParams.setMargins(DimensionsUtil.dip2px(getContext(), 2), 0, 0, DimensionsUtil.dip2px(getContext(), 3));
                defaultFilterFlag.setBackgroundResource(R.drawable.ic_triangle_up);
            }
        }
        defaultFilterFlag.setLayoutParams(layoutParams);
    }

    public interface FilterOpenListener {
        void onFilterOpenListener();
    }

    public interface TabClickListener {
        void onTabClick(boolean fromBtn, String type);
    }
}
