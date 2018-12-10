package google.architecture.personal.login.card;

/**
 * @author lq.zeng
 * @date 2018/10/17
 */

public class CardItem {
    private String mTitleResource;
    private String mInputValue;
    private String mType;

    public CardItem(String type, String title) {
        mType = type;
        mTitleResource = title;
    }

    public String getTitle() {
        return mTitleResource;
    }

    public String getInputValue() {
        return mInputValue;
    }

    public void setInputValue(String inputValue) {
        mInputValue = inputValue;
    }

    public String getType() {
        return mType;
    }

    public interface IRegisterType {
        String REGISTER_TYPE_PHONE = "cellphone";
        String REGISTER_TYPE_EMAIL = "email";
    }
}
