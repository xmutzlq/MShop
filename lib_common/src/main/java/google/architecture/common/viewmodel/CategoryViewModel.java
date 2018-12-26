package google.architecture.common.viewmodel;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import google.architecture.common.base.BaseViewModel;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

/**
 * @author lq.zeng
 * @date 2018/7/24
 */

public class CategoryViewModel extends BaseViewModel {

    public void getShoppingCategory() {
        if (isRunning.get()) return;
        disposable.add(DeHongDataRepository.get().getShoppingCategory().doOnSubscribe(disposable -> isRunning.set(false))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    if (result == null || result.getData() == null) {
                        return;
                    }
                    setDataObject(result.getData(), data);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    public void getShoppingCategory(String parentId) {
        if (isRunning.get()) return;
        disposable.add(DeHongDataRepository.get().getShoppingCategoryRight(parentId).doOnSubscribe(disposable -> isRunning.set(false))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    if (result == null || result.getData() == null) {
                        return;
                    }
                    setDataObject(result.getData(), data);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    public void getCategoryLeft(String sex) {
        if (isRunning.get()) return;
        String requestJson = CateParam.getJsonRequest(sex);
        disposable.add(DeHongDataRepository.get().xlj_getTopCat(requestJson).doOnSubscribe(disposable -> isRunning.set(false))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    if (result == null || result.getData() == null) {
                        return;
                    }
                    setDataObject(result.getData(), data);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    public void getCategoryRight(String sex, String catId) {
        if (isRunning.get()) return;
        String requestJson = CateParam2.getJsonRequest(sex, catId);
        disposable.add(DeHongDataRepository.get().xlj_getChildCat(requestJson).doOnSubscribe(disposable -> isRunning.set(false))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    if (result == null || result.getData() == null) {
                        return;
                    }
                    setDataObject(result.getData(), data);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private static class CateParam {
        @SerializedName("appType")
        public String appType;
        @SerializedName("appToken")
        public String appToken;
        @SerializedName("method")
        public String method;
        @SerializedName("sex")
        public String sex;

        public static String getJsonRequest(String sex) {
            CateParam cateParam = new CateParam();
            cateParam.appType = "erp";
            cateParam.appToken = "rXwIxQo8dEFWTBpa";
            cateParam.method = "GoodsCats/getTopCat";
            cateParam.sex = sex;
            return new Gson().toJson(cateParam);
        }
    }

    private static class CateParam2 {
        @SerializedName("appType")
        public String appType;
        @SerializedName("appToken")
        public String appToken;
        @SerializedName("method")
        public String method;
        @SerializedName("sex")
        public String sex;
        @SerializedName("catId")
        public String catId;

        public static String getJsonRequest(String sex, String catId) {
            CateParam2 cateParam = new CateParam2();
            cateParam.appType = "erp";
            cateParam.appToken = "rXwIxQo8dEFWTBpa";
            cateParam.method = "GoodsCats/getTopCat";
            cateParam.sex = sex;
            cateParam.catId = catId;
            return new Gson().toJson(cateParam);
        }
    }
}
