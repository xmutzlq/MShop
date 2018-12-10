package google.architecture.personal.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import google.architecture.personal.R;

/**
 * @author lq.zeng
 * @date 2018/6/12
 */

public class PersonalFragmentLikeAdapter extends BaseQuickAdapter<String, BaseViewHolder>{

    public PersonalFragmentLikeAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.personal_like_title, item);
    }
}
