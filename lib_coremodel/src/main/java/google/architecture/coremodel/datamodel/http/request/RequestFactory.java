package google.architecture.coremodel.datamodel.http.request;

public class RequestFactory {

    public static final String TYPE_FORM = "form";//表单请求
    public static final String TYPE_PAYLOAD = "payload";//数据流

    public static IRequestType makeRequest(String type) {
        switch (type) {
            case TYPE_FORM:
                return new FormRequest();
            case TYPE_PAYLOAD:
                return new PayloadRequest();
            default:
                return new FormRequest();
        }
    }

}
