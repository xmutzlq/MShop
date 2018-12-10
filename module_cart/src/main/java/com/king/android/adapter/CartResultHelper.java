package com.king.android.adapter;

import android.text.TextUtils;

import java.util.List;

import google.architecture.coremodel.data.CartBusinessesData;
import google.architecture.coremodel.data.CartGoodsData;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * @author lq.zeng
 * @date 2018/9/6
 */

public class CartResultHelper<T> implements CartResultHelperApi<T> {

    private int resultCount = 0;
    private double resultMoney = 0;
    private int controllState = STATE_NONE;

    public void setControllState(int controllState) {
        this.controllState = controllState;
    }

    public int getControllState() {
        return controllState;
    }

    @Override
    public boolean checkOnNetWork() {
        return controllState == STATE_NONE;
    }

    @Override
    public void getCountResult(List<T> cartData, CartResultHelperApi.ICountResult countResult) {
        if(cartData == null || cartData.size() == 0) return;
        CompositeDisposable disposable = new CompositeDisposable();
        DisposableObserver observer = new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer aDouble) {
                resultCount = aDouble;
            }

            @Override
            public void onError(Throwable e) {
                if(disposable != null) disposable.clear();
            }

            @Override
            public void onComplete() {
                if(disposable != null) disposable.clear();
                if(countResult != null) countResult.onCountResult(resultCount);
                resultCount = 0;
            }
        };
        Observable.fromIterable(cartData)
            .filter(multiItemEntity -> multiItemEntity instanceof CartGoodsData
                    && ((CartGoodsData) multiItemEntity).isChecked
                    && !TextUtils.isEmpty(((CartGoodsData) multiItemEntity).goodsPrice))
            .flatMap(multiItemEntity -> {
                int count = ((CartGoodsData) multiItemEntity).count;
                return Observable.just(count);
            })
            .scan((integer, integer2) -> integer + integer2)
            .subscribe(observer);
        disposable.add(observer);
    }

    @Override
    public void getMoneyResult(List<T> cartData, CartResultHelperApi.IMoneyResult moneyResult) {
        if(cartData == null || cartData.size() == 0) return;
        CompositeDisposable disposable = new CompositeDisposable();
        DisposableObserver observer = new DisposableObserver<Double>() {
            @Override
            public void onNext(Double aDouble) {
                resultMoney = aDouble;
            }

            @Override
            public void onError(Throwable e) {
                if(disposable != null) disposable.clear();
            }

            @Override
            public void onComplete() {
                if(disposable != null) disposable.clear();
                if(moneyResult != null) moneyResult.onMoneyResult(resultMoney);
                resultMoney = 0;
            }
        };
        Observable.fromIterable(cartData)
                .filter(multiItemEntity -> multiItemEntity instanceof CartGoodsData
                        && ((CartGoodsData) multiItemEntity).isChecked
                        && !TextUtils.isEmpty(((CartGoodsData) multiItemEntity).goodsPrice))
                .flatMap(multiItemEntity -> {
                    int count = ((CartGoodsData) multiItemEntity).count;
                    double price = Double.valueOf(((CartGoodsData) multiItemEntity).goodsPrice);
                    double result = count * price;
                    return Observable.just(result);
                })
                .scan((aDouble, aDouble2) -> aDouble + aDouble2)
                .subscribe(observer);
        disposable.add(observer);
    }

    @Override
    public void checkAllState(List<T> cartData, ICartItemStateListener cartItemStateListener, CartResultHelperApi.ICheckAllState checkAllState) {
        if(cartData == null || cartData.size() == 0) return;
        CompositeDisposable disposable = new CompositeDisposable();
        DisposableObserver observer = new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aDouble) {
            }

            @Override
            public void onError(Throwable e) {
                if(disposable != null) disposable.clear();
                if(checkAllState != null) checkAllState.onCheckAllState(Boolean.valueOf(e.getMessage()));
            }

            @Override
            public void onComplete() {
                if(disposable != null) disposable.clear();
                if(checkAllState != null) checkAllState.onCheckAllState(true);
            }
        };
        Observable.fromIterable(cartData)
            .flatMap(multiItemEntity -> {
                if(multiItemEntity instanceof CartBusinessesData) {
                    CartBusinessesData cartBusinessesData = (CartBusinessesData) multiItemEntity;
                    if (!cartBusinessesData.isCehcked
                            && cartItemStateListener != null
                            && cartItemStateListener.enableShopChecked(cartBusinessesData.isEnable)) {
                        return Observable.error(new Throwable("false"));
                    }
                }
                if(multiItemEntity instanceof CartGoodsData) {
                    CartGoodsData cartGoodsData = (CartGoodsData) multiItemEntity;
                    if(!cartGoodsData.isChecked
                            && cartItemStateListener != null
                            && cartItemStateListener.enableGoodsChecked(cartGoodsData.goodsState)){
                        return Observable.error(new Throwable("false"));
                    }
                }
                return Observable.just(true);
            }).subscribe(observer);
        disposable.add(disposable);
    }

    @Override
    public void notifyAllChecked(List<T> cartData, boolean isCheckedAll, ICartItemStateListener cartItemStateListener, CartResultHelperApi.INotifyAllChecked notifyAllChecked) {
        if(cartData == null || cartData.size() == 0) return;
        CompositeDisposable disposable = new CompositeDisposable();
        DisposableObserver observer = new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aDouble) {
            }

            @Override
            public void onError(Throwable e) {
                if(disposable != null) disposable.clear();
            }

            @Override
            public void onComplete() {
                if(disposable != null) disposable.clear();
                if(notifyAllChecked != null) notifyAllChecked.onNotifyAllChecked();
            }
        };
        Observable.fromIterable(cartData)
            .flatMap(multiItemEntity -> {
                boolean isChecked = false;
                if(multiItemEntity instanceof CartBusinessesData) {
                    CartBusinessesData cartBusinessesData = (CartBusinessesData) multiItemEntity;
                    cartBusinessesData.isCehcked = isChecked = isCheckedAll && cartItemStateListener.enableShopChecked(cartBusinessesData.isEnable);
                }
                if(multiItemEntity instanceof CartGoodsData) {
                    CartGoodsData cartGoodsData = (CartGoodsData) multiItemEntity;
                    cartGoodsData.isChecked = isChecked = isCheckedAll && cartItemStateListener.enableGoodsChecked(cartGoodsData.goodsState);
                }
                return Observable.just(isChecked);
            }).subscribe(observer);
        disposable.add(disposable);
    }

    @Override
    public void getCartIds(List cartData, StringBuilder ids, ICartItemStateListener cartItemStateListener, CartResultHelperApi.IPostIdsResult postIdsResult) {
        if(cartData == null || cartData.size() == 0) return;
        CompositeDisposable disposable = new CompositeDisposable();
        DisposableObserver observer = new DisposableObserver<String>() {
            @Override
            public void onNext(String aDouble) {
                if(!TextUtils.isEmpty(aDouble)) {
                    ids.append(aDouble).append(",");
                }
            }

            @Override
            public void onError(Throwable e) {
                if(disposable != null) disposable.clear();
            }

            @Override
            public void onComplete() {
                if(disposable != null) disposable.clear();
                if(postIdsResult != null) postIdsResult.onIdsResult(ids.toString());
            }
        };
        Observable.fromIterable(cartData)
            .filter(multiItemEntity -> multiItemEntity instanceof CartGoodsData)
            .flatMap(multiItemEntity -> {
                CartGoodsData cartGoodsData = (CartGoodsData)multiItemEntity;
                if(cartItemStateListener.enableGoodsChecked(cartGoodsData.goodsState)) {
                    return Observable.just(cartGoodsData.cartId);
                } else {
                    return Observable.just("");
                }
            })
            .subscribe(observer);
        disposable.add(disposable);
    }
}
