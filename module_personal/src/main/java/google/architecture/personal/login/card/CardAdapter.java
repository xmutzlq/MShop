package google.architecture.personal.login.card;

import android.support.v7.widget.CardView;

/**
 * @author lq.zeng
 * @date 2018/10/17
 */

public interface CardAdapter {

    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    CardItem getCardItemAt(int position);

    int getCount();
}
