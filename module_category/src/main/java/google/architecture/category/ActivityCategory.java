package google.architecture.category;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import google.architecture.common.base.BaseActivity;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lq.zeng
 * @date 2018/6/5
 */

public class ActivityCategory extends BaseActivity {
    @Override
    protected int getLayout() {
        return R.layout.activity_category;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData(this_,null);
    }

    public static void loadData(final AppCompatActivity activity, final Runnable post) {
        Flowable.create(e -> {
            CatesCache.cacheCatesData(activity);
            e.onComplete();
        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(()->{
                    if(post != null) {
                        post.run();
                    } else {
                        activity.getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.container_category, FragmentCategory.newInstance(),
                                        FragmentCategory.class.getSimpleName()).commit();
                    }
                }).subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

}
