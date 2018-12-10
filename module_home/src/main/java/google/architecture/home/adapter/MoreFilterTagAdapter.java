package google.architecture.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import google.architecture.common.widget.CommFlowLayout;
import google.architecture.common.widget.adapter.TagAdapter;
import google.architecture.coremodel.data.MoreFilterTagData;
import google.architecture.home.R;

/**
 * @author lq.zeng
 * @date 2018/5/28
 */

public class MoreFilterTagAdapter extends TagAdapter<MoreFilterTagData> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public MoreFilterTagAdapter(Context context, List<MoreFilterTagData> datas) {
        super(datas);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(CommFlowLayout parent, int position, MoreFilterTagData moreFilterTagData) {
        final TextView tv = (TextView) mLayoutInflater.inflate(R.layout.filter_item_tag, parent, false);
        tv.setText(moreFilterTagData.tagName);
        tv.setVisibility(moreFilterTagData.isShown ? View.VISIBLE : View.GONE);
        return tv;
    }
}
