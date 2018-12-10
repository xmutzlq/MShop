package google.architecture.coremodel.data;

/**
 * @author lq.zeng
 * @date 2018/5/28
 */

public class MoreFilterTagData {
    public String tagId = "";
    public String tagName = "";
    public boolean isShown;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MoreFilterTagData) {
            return ((MoreFilterTagData)obj).tagName.equals(tagName)
                    && ((MoreFilterTagData)obj).tagId.equals(tagId);
        }
        return false;
    }
}
