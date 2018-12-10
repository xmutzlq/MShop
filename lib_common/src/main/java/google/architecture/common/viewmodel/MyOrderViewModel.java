package google.architecture.common.viewmodel;

import google.architecture.common.base.BaseListViewModel;
import google.architecture.coremodel.Account;
import google.architecture.coremodel.data.MyOrderData;
import google.architecture.coremodel.data.MyOrderShop;
import google.architecture.coremodel.datamodel.RefreshListModel;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

/**
 * @author lq.zeng
 * @date 2018/9/14
 */

public class MyOrderViewModel extends BaseListViewModel {
    private String orderState;

    private MyOrderData myOrderData;

    public MyOrderData getMyOrderData() {
        return myOrderData;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public void getAllOrderList() {
        getMyOrderList("0");
    }

    public void getWaitingPayOrderList() {
        getMyOrderList("1");
    }

    public void getWaitingReceiptOrderList() {
        getMyOrderList("3");
    }

    public void getWaitingCommentOrderList() {getMyOrderList("4");}

    public void getCancelOrderList() {getMyOrderList("6");}

    public void getFinishOrderList() {
        getMyOrderList("5");
    }

    public void getMyOrderList(String orderState) {
        if (isRunning.get()) return;
        disposable.add(DeHongDataRepository.get().myOrderList(Account.get().getUserId(), orderState , String.valueOf(page), String.valueOf(PAGE_SIZE))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    checkEmpty(result.getData().getOrder_list());
                    setDataObject(result.getData(), data);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    @Override
    public void refreshData(boolean refresh) {
        disposable.add(DeHongDataRepository.get().myOrderList(Account.get().getUserId(), orderState, String.valueOf(page), String.valueOf(PAGE_SIZE))
                .doOnSubscribe(disposable -> isRunning.set(false))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    myOrderData = result.getData();
                    pageTotal = myOrderData.getOrder_pages();
                    RefreshListModel<MyOrderShop> refreshListModel = new RefreshListModel<>();
                    if (refresh) {
                        refreshListModel.setRefreshType(myOrderData.getOrder_list());
                    } else {
                        refreshListModel.setUpdateType(myOrderData.getOrder_list());
                    }
                    if(refreshListModel.isRefreshType()) checkEmpty(myOrderData.getOrder_list());
                    setDataObject(refreshListModel, data);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }
}
