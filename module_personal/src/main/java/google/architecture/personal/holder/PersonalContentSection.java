package google.architecture.personal.holder;

import com.chad.library.adapter.base.entity.SectionEntity;

import google.architecture.coremodel.data.PersonalContentData;

/**
 * @author lq.zeng
 * @date 2018/9/4
 */

public class PersonalContentSection extends SectionEntity<PersonalContentData> {

    public static final int SECTION_TYPE_TITLE = 1;
    public static final int SECTION_TYPE_ITEM = 2;

    private boolean isMore;
    private int type;

    public PersonalContentSection(boolean isHeader, PersonalContentData contentData) {
        super(isHeader, contentData.getName());
        t = contentData;
    }

    public PersonalContentSection(PersonalContentData contentData) {
        super(contentData);
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
}
