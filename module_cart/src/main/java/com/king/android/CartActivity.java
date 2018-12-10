package com.king.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.king.android.res.config.ARouterPath;

import java.util.ArrayList;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.BaseFragment;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

@Route(path = ARouterPath.CartAty)
public class CartActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_cart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        loadData(this_, null);
        setCanBack(true);
        setTitleName(getString(R.string.moudle_name));

        BaseFragment fragment = (BaseFragment)ARouter.getInstance()
                .build(ARouterPath.CartFgt).navigation();

        if(fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_cart, fragment)
                    .commit();
        }
    }

    public static void loadData(AppCompatActivity appCompatActivity, Runnable runnable) {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                CartDataCache.cacheRecommendData(appCompatActivity);
                CartDataCache.getCartData();
                if(CartDataCache.allCartData == null) {
                    CartDataCache.allCartData = new ArrayList<>();
                }
                CartDataCache.allCartData.clear();
                CartDataCache.allCartData = CartDataCache.generateData();
                e.onComplete();
            }
        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        if(runnable != null) {
                            runnable.run();
                        } else {
                            appCompatActivity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.container_cart, FragmentCart.newInstance(),
                                            FragmentCart.class.getSimpleName())
                                    .commit();
                        }
                    }
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

}
