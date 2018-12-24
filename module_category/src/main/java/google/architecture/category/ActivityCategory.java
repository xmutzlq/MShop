package google.architecture.category;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
//        loadData(this_,null);

//        XLJ_GoodsDetailViewModel xljGoodsDetailViewModel = new XLJ_GoodsDetailViewModel();
//        String requestJson = GoodsDetailRequestEntity.getRequestJson();
//        xljGoodsDetailViewModel.getGoodsDetail(requestJson, t -> {
//            GoodsDetailData goodsDetailData = (GoodsDetailData) t;
//            LogUtils.tag("zlq").e(goodsDetailData.toString());
//        });

        BaseFragment fragment = (BaseFragment)ARouter.getInstance()
                .build(ARouterPath.CategoryFgt).navigation();

        if(fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_category, fragment)
                    .commit();
        }
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
