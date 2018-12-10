package google.architecture.common.viewmodel;

import com.apkfuns.logutils.LogUtils;

import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author lq.zeng
 * @date 2018/5/3
 */

public class SplashViewModel {
    final CompositeDisposable disposable = new CompositeDisposable();

    public void bind() {
        disposable.add(DeHongDataRepository.get().getStartInfo()
                .doOnNext(startInfoHttpResult -> {
                    disposable.dispose();
                    LogUtils.tag("zlq").e("startInfoHttpResult = " + startInfoHttpResult.getData().toString());
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    public void unbind() {
        dispose();
    }

    private void dispose() {
        if(disposable != null) {
            disposable.dispose();
        }
    }
}
