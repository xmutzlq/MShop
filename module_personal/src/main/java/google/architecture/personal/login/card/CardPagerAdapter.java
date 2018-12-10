package google.architecture.personal.login.card;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import google.architecture.personal.R;

/**
 * @author lq.zeng
 * @date 2018/10/17
 */

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;
    private ICardItemChildClick mCardItemChildClick;

    public CardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(CardItem item) {
        mViews.add(null);
        mData.add(item);
    }

    public void setCardItemChildClick(ICardItemChildClick cardItemChildClick) {
        mCardItemChildClick = cardItemChildClick;
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public CardItem getCardItemAt(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.card_adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) mBaseElevation = cardView.getCardElevation();

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(CardItem item, View view) {
        TextInputLayout titleTextInputLayout = view.findViewById(R.id.username_et_layout);
        titleTextInputLayout.setHint(item.getTitle());
        TextInputEditText titleTextView = view.findViewById(R.id.username_et);
        if(CardItem.IRegisterType.REGISTER_TYPE_PHONE.equals(item.getType())) {
            titleTextView.setInputType(InputType.TYPE_CLASS_NUMBER);
            InputFilter[] filters = {new InputFilter.LengthFilter(11)};
            titleTextView.setFilters(filters);
        } else {
            titleTextView.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }
        titleTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                item.setInputValue(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        view.findViewById(R.id.send_btn).setOnClickListener(v -> {
            if(mCardItemChildClick != null) {
                mCardItemChildClick.onSend(item.getType());
            }
        });
    }

    public interface ICardItemChildClick {
        void onSend(String type);
    }
}
