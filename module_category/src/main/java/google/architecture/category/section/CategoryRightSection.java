package google.architecture.category.section;

import com.chad.library.adapter.base.entity.SectionEntity;

import google.architecture.coremodel.data.OpDiscoverCates;

/**
 * @author lq.zeng
 * @date 2018/6/6
 */

public class CategoryRightSection extends SectionEntity<OpDiscoverCates>{

    public static final int SECTION_TYPE_BANNER = 0;
    public static final int SECTION_TYPE_TITLE = 1;
    public static final int SECTION_TYPE_ITEM = 2;

    private boolean hasBind;
    private boolean isMore;
    private int type;

    public CategoryRightSection(boolean isHeader, OpDiscoverCates cates) {
        super(isHeader, cates.getTitle());
        t = cates;
    }

    public CategoryRightSection(OpDiscoverCates cates) {
        super(cates);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setHasBind(boolean hasBind) {
        this.hasBind = hasBind;
    }

    public boolean isHasBind() {
        return hasBind;
    }
}
