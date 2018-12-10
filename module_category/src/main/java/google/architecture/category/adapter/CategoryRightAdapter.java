package google.architecture.category.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.List;

import google.architecture.category.R;
import google.architecture.category.section.CategoryRightSection;
import google.architecture.category.viewholder.CategoryRightTitleViewHolder;
import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.util.GlideImageLoader;
import google.architecture.common.widget.banner.MyBanner;

/**
 * @author lq.zeng
 * @date 2018/6/6
 */

public class CategoryRightAdapter extends BaseSectionQuickAdapter<CategoryRightSection, CategoryRightTitleViewHolder>{

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public CategoryRightAdapter(int layoutResId, int sectionHeadResId, List<CategoryRightSection> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(CategoryRightTitleViewHolder helper, CategoryRightSection item) {
        helper.setType(item.getType());
        switch (item.getType()) {
            case CategoryRightSection.SECTION_TYPE_TITLE:
                helper.setGone(R.id.category_section_title, true);
                helper.setGone(R.id.category_section_banner, false);
                helper.setText(R.id.category_section_tvTitle, item.header);
                break;
            case CategoryRightSection.SECTION_TYPE_BANNER:
                helper.setGone(R.id.category_section_title, false);
                helper.setGone(R.id.category_section_banner, true);

                MyBanner banner = helper.getView(R.id.category_section_banner);
                banner.setImageLoader(new GlideImageLoader());
                banner.setImages(item.t.getChildren().getBanner());
                banner.setDelayTime(3500);
                banner.setIndicatorGravity(BannerConfig.CENTER);
                banner.setBannerAnimation(Transformer.DepthPage);

                if(item.isHasBind()) { //过滤不需要返回结果
                    banner.setIsSkip(true);
                }

                banner.start();

                if(!item.isHasBind()) {
                    item.setHasBind(true);
                } else {
                    banner.turnPage();
                }

                break;
        }
    }

    @Override
    protected void convert(CategoryRightTitleViewHolder helper, CategoryRightSection item) {
        helper.setType(item.getType());
        ImageLoader.get().load(helper.getView(R.id.category_section_fivCover), item.t.getPic());
        helper.setText(R.id.category_section_tvName, item.t.getTitle());
    }

}
