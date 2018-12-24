package google.architecture.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.king.android.res.config.ARouterPath;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.BaseFragment;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Route(path = ARouterPath.HomeAty)
public class ActivityHome extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        loadData(this_,null);
        BaseFragment fragment = (BaseFragment) ARouter.getInstance()
                .build(ARouterPath.HomeFgt).navigation();

        if(fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_home, fragment)
                    .commit();
        }
    }

    public static void loadData(final AppCompatActivity activity, final Runnable post) {
        Flowable.create(e -> {
                DataCache.cacheHomeRecommendData(activity);
                DataCache.cacheHomeNewestGoodsData(activity);
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
                                .add(R.id.container_home, FragmentHome.newInstance(),
                                        FragmentHome.class.getSimpleName())
                                .commit();
                    }
                }).subscribe(new EmptyConsumer(), new ErrorConsumer());
    }
}
