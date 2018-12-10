package google.architecture.personal.holder;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import google.architecture.common.widget.decoration.Divider;
import google.architecture.common.widget.decoration.DividerBuilder;
import google.architecture.common.widget.decoration.DividerItemDecoration;
import google.architecture.personal.R;

/**
 * @author lq.zeng
 * @date 2018/9/19
 */

public class MyFavoritesDecoration extends DividerItemDecoration {

    private Context context;

    public MyFavoritesDecoration(Context context) {
        super(context);
        this.context = context;
    }

    @Nullable
    @Override
    public Divider getDivider(int itemPosition) {
        return new DividerBuilder()
                .setBottomSideLine(true, ContextCompat.getColor(context, R.color.grayLine), 1, 0, 0).create();
    }
}
