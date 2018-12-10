package google.architecture.personal.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;

import java.util.List;

import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.selector.SelectorFactory;
import google.architecture.common.selector.SelectorShape;
import google.architecture.common.util.AppCompat;
import google.architecture.personal.R;
import google.architecture.personal.holder.PersonalContentSection;
import google.architecture.personal.holder.PersonalContentTitleViewHolder;

/**
 * @author lq.zeng
 * @date 2018/9/4
 */

public class PersonalContentAdapter extends BaseSectionQuickAdapter<PersonalContentSection, PersonalContentTitleViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public PersonalContentAdapter(int layoutResId, int sectionHeadResId, List<PersonalContentSection> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(PersonalContentTitleViewHolder helper, PersonalContentSection item) {
        helper.setType(item.getType());
        switch (item.getType()) {
            case PersonalContentSection.SECTION_TYPE_TITLE:
                helper.setText(R.id.personal_content_title, item.header);
                break;
        }
    }

    @Override
    protected void convert(PersonalContentTitleViewHolder helper, PersonalContentSection item) {
        helper.setType(item.getType());
        Drawable drawable = SelectorFactory.create(new SelectorShape.SelectorBuilder()
                .normalColor(ContextCompat.getColor(mContext, R.color.transparent))
                .pressColor(ContextCompat.getColor(mContext, R.color.common_pressed)).build());
        AppCompat.setBackground(helper.itemView, drawable);
        ImageView iv = helper.getView(R.id.personal_content_item_iv);
        ImageLoader.get().load(helper.getView(R.id.personal_content_item_iv), Integer.valueOf(item.t.getImg()), new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                iv.setImageDrawable(resource);
                return false;
            }
        });
        helper.setText(R.id.personal_content_item_tv, item.t.getName());
    }
}
