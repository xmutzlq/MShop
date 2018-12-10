package google.architecture.home.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import google.architecture.common.util.AppCompat;
import google.architecture.common.util.KeyboardUtils;
import google.architecture.home.R;

/**
 * @author lq.zeng
 * @date 2018/5/23
 */

public class CommSearchView extends SearchView implements KeyboardUtils.SoftKeyboardToggleListener {

    private boolean isKeyBoardShown;
    private SearchView.SearchAutoComplete mEdit;

    public CommSearchView(Context context) {
        super(context);
        init();
    }

    public CommSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setText(String inputValue) {
        mEdit.setText(inputValue);
        if(!TextUtils.isEmpty(inputValue)) mEdit.setSelection(inputValue.length());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        KeyboardUtils.addKeyboardToggleListener((Activity) getContext(), this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        KeyboardUtils.removeKeyboardToggleListener(this);
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK && !isKeyBoardShown) {
            ((Activity)getContext()).finish();
            return true;
        }
        return super.dispatchKeyEventPreIme(event);
    }

    private void init() {
        mEdit = findViewById(R.id.search_src_text);

        deleteUnderline();
        changeSearchIcon();
        changeSearchText();
        changeSearchDelIcon();

        setIconifiedByDefault(false);
        setIconified(true);
        onActionViewExpanded();

        setOnQueryTextFocusChangeListener((view, queryTextFocused)->{
            if(!queryTextFocused) setQuery("", false);
        });
    }

    /**改变searchview清理图标*/
    private void changeSearchDelIcon() {
        ImageView closeViewIcon = (ImageView)findViewById(R.id.search_close_btn);
        closeViewIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.icon_search_clear));
    }

    /**改变searchview文本*/
    private void changeSearchText() {
        final EditText editText = (EditText)findViewById(R.id.search_src_text);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.text_size_15sp));
        editText.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_88));
        editText.setHintTextColor(ContextCompat.getColor(getContext(), R.color.gray_D5));
    }

    /**改变searchview搜索图标*/
    private void changeSearchIcon() {
        ImageView searchViewIcon = (ImageView)findViewById(R.id.search_mag_icon);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 10;
        lp.gravity = Gravity.CENTER_VERTICAL;
        searchViewIcon.setLayoutParams(lp);
        searchViewIcon.setImageResource(R.mipmap.icon_search_gray);
    }

    /**去掉searchview下划线*/
    private void deleteUnderline() {
        AppCompat.setBackground(findViewById(android.support.v7.appcompat.R.id.search_plate));
        AppCompat.setBackground(findViewById(android.support.v7.appcompat.R.id.submit_area));
    }

    @Override
    public void onToggleSoftKeyboard(boolean isVisible, int heightDifference) {
        isKeyBoardShown = isVisible;
    }
}
