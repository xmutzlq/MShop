package google.architecture.personal.orders;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.king.android.res.config.ARouterPath;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BasePagingFragment;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.viewmodel.MyOrderOperateViewModel;
import google.architecture.common.viewmodel.MyOrderViewModel;
import google.architecture.coremodel.data.MyOrderOper;
import google.architecture.coremodel.data.MyOrderShop;
import google.architecture.coremodel.datamodel.RefreshListModel;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.personal.R;
import google.architecture.personal.adapter.order.MyOrderAdapter;
import google.architecture.personal.adapter.order.MyOrderDataHelper;
import google.architecture.personal.adapter.order.MyOrderSpc;
import google.architecture.personal.adapter.order.PayProvider;
import google.architecture.personal.databinding.FragmentMyOrderItemBinding;

/**
 * @author lq.zeng
 * @date 2018/10/16
 */

public abstract class FragmentMyOrderList extends BasePagingFragment<FragmentMyOrderItemBinding> implements PayProvider.IOperateOrder {

    private MyOrderViewModel myOrderViewModel;
    private MyOrderOperateViewModel myOrderOperateViewModel;
    private MyOrderAdapter adapter;

    protected abstract String getOrderState();

    @Override
    protected int getLayout() {
        return R.layout.fragment_my_order_item;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        adapter = new MyOrderAdapter(new ArrayList<>(), this);
        adapter.setOnItemClickListener((adapter1, view1, position) -> {
            if(adapter1.getItemViewType(position) == MyOrderAdapter.TYPE_GOODS) {
                String orderId = ((MyOrderSpc)adapter1.getData().get(position)).orderId;
                ARouter.getInstance().build(ARouterPath.PersonalShoppingOrderDetailAty).withString(CommKeyUtil.EXTRA_KEY, orderId).navigation();
            }
        });
        return adapter;
    }

    @Override
    protected void prePareRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void afterRecycleView() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myOrderOperateViewModel = new MyOrderOperateViewModel();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        myOrderOperateViewModel.clear();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onFragmentFirstVisible() {
        myOrderViewModel = new MyOrderViewModel();
        myOrderViewModel.setOrderState(getOrderState());
        setListViewModel(myOrderViewModel);
        super.onFragmentFirstVisible();
    }

    @Override
    protected void onDataResult(Object o) {
        RefreshListModel<MyOrderShop> refreshListModel = (RefreshListModel<MyOrderShop>) o;
        List<MyOrderSpc> source = MyOrderDataHelper.prePareMyOrderListData(myOrderViewModel.getMyOrderData());
        RefreshListModel<MyOrderSpc> listModel = new RefreshListModel<>();
        listModel.refreshType = refreshListModel.refreshType;
        listModel.list = source;
        super.onDataResult(listModel);
    }

    @Override
    protected void onEmptyDisplaying() {
        adapter.setEmptyView(R.layout.layout_empty_my_order, (ViewGroup) recyclerView.getParent());
    }

    @Override
    public void onMessageEvent(CommEvent event) {
        if(CommEvent.MSG_TYPE_UPDATE_USER_ALL_ORDER.equals(event.msgType)) {
            loadData();
        }
    }

    private void loadData() {
        if(myOrderViewModel != null) myOrderViewModel.getAllOrderList();
    }

    @Override
    public void onOperateOrder(int type, int position, String orderId, String reason) {
        switch (type) {
            case MyOrderOper.FLAG_CANCEL:
                myOrderOperateViewModel.cancelOrder(orderId, reason, (code, msg) -> {
                    if(code == 0) adapter.itemRangeMoved(adapter.getPositionStart(position), position);
                });
                break;
            case MyOrderOper.FLAG_DEL:
                myOrderOperateViewModel.deleteOrder(orderId, (code, msg) -> {
                    if(code == 0) adapter.itemRangeMoved(adapter.getPositionStart(position), position);
                });
                break;
            case MyOrderOper.FLAG_CONFIRM:
                myOrderOperateViewModel.confirmReceipt(orderId, (code, msg) -> {
                    if(code == 0) adapter.itemRangeMoved(adapter.getPositionStart(position), position);
                });
                break;
            case MyOrderOper.FLAG_SEND:
                myOrderOperateViewModel.remindingSend(orderId, null);
                break;
        }
    }
}
