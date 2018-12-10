package google.architecture.common.base;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import google.architecture.common.base.listener.LifeCycleListener;
import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends BaseObservable implements LifeCycleListener {
    public ObservableField<Throwable> isError = new ObservableField<>();
    public ObservableBoolean isEmpty = new ObservableBoolean(false);
    public ObservableBoolean isRunning = new ObservableBoolean(false);
    public ObservableField data = new ObservableField();

    protected CompositeDisposable disposable = new CompositeDisposable();
    protected Map<String, CompositeDisposable> disposableMap = new HashMap<>();

    public BaseViewModel() {
        super();
    }

    protected void checkEmpty(Object object) {
        checkEmpty(isEmpty, object);
    }

    protected void checkEmpty(ObservableBoolean isEmpty, Object object) {
        boolean emptyFlag = object == null;
        if (emptyFlag != isEmpty.get()) {
            isEmpty.set(emptyFlag);
        }
    }

    protected void checkEmpty(List list) {
        checkEmpty(isEmpty, list);
    }

    protected void checkEmpty(ObservableBoolean isEmpty, List list) {
        boolean emptyFlag = list == null || list.isEmpty();
        if (emptyFlag != isEmpty.get()) {
            isEmpty.set(emptyFlag);
        }
    }

    protected <T> void setDataObject(T object, ObservableField<T> field) {
        setDataObject(isEmpty, object, field);
    }

    protected <T> void setDataObject(ObservableBoolean isEmpty, T object,
                                     ObservableField<T> field) {
        if (field == null || isEmpty == null) {
            return;
        }
        checkEmpty(isEmpty, object);
        if (!isEmpty.get()) {
            field.set(object);
        }
    }

    protected <T> void setDataList(List<T> list,
                                   ObservableList<T> observableList) {
        setDataList(isEmpty, list, observableList);
    }

    protected <T> void setDataList(ObservableBoolean isEmpty, List<T> list,
                                   ObservableList<T> observableList) {
        if (observableList == null || isEmpty == null) {
            return;
        }
        checkEmpty(isEmpty, list);
        if (!isEmpty.get()) {
            observableList.clear();
            observableList.addAll(list);
        }
    }

    public void clear() {
        if(disposable != null) disposable.clear();
    }

    public void clearAll() {
        if(disposableMap != null && disposableMap.size() > 0) {
            for (Map.Entry<String, CompositeDisposable> entry : disposableMap.entrySet()) {
                entry.getValue().clear();
            }
            disposableMap.clear();
        }
    }

    @Override
    public void onActivityDestroy() {
        clear();
        clearAll();
    }
}
