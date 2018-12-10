package com.king.android.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.king.android.order.adapter.AdressItemDecoration;
import com.king.android.order.adapter.AdressManageAdapter;
import com.king.android.order.databinding.ActivityAddressManageBinding;
import com.king.android.res.config.ARouterPath;
import com.kongzue.dialog.v2.SelectDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.util.AppCompat;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.viewmodel.AddressViewModel;
import google.architecture.coremodel.data.AdressListData;
import google.architecture.coremodel.datamodel.http.event.CommEvent;

/**
 * @author lq.zeng
 * @date 2018/9/10
 */
@Route(path = ARouterPath.OrderAddressManageActy)
public class ActivityAddressManage extends BaseActivity<ActivityAddressManageBinding> {
    private AddressViewModel addressViewModel;
    private AdressManageAdapter adressManageAdapter;
    private List<AdressListData.AddressListItem> addressListItems;
    private boolean isNeedDispatchNotify;
    @Override
    protected int getLayout() {
        return R.layout.activity_address_manage;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanBack(true);
        setTitleName(getString(R.string.add_manage));

        AppCompat.setBackgroundPressed(this_, binding.btnAddAddress, R.color.themePrimaryDark);
        binding.btnAddAddress.setOnClickListener(v -> {
            ARouter.getInstance().build(ARouterPath.OrderAddressAddActy).navigation();
        });

        addressListItems = new ArrayList<>();
        adressManageAdapter = new AdressManageAdapter(R.layout.layout_address_item_info_edit, addressListItems);
        adressManageAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            AdressListData.AddressListItem addressListItem = adressManageAdapter.getData().get(position);
            switch (String.valueOf(view.getTag())) {
                case AdressManageAdapter.TAG_EDIT:
                    ARouter.getInstance().build(ARouterPath.OrderAddressAddActy).withParcelable(CommKeyUtil.EXTRA_KEY, addressListItem).navigation();
                    break;
                case AdressManageAdapter.TAG_DEL:
                    SelectDialog.show(this_, getResources().getString(R.string.str_friendly_reminder),
                            getResources().getString(R.string.address_del_tip), getResources().getString(R.string.str_sure), (dialog, which) -> {
                                //删除
                                addressViewModel.deleteAddress(addressListItem.getAddress_id(), (code, msg) -> {
                                    if(code == 0) {isNeedDispatchNotify = true; loadData();}
                                });
                            }, getResources().getString(R.string.str_cancel), (dialog, which) -> {});
                    break;
                case AdressManageAdapter.TAG_CHECK:
                    //设置默认地址
                    if(adressManageAdapter.getData().get(position).getIs_default() == 1) return; //已经是默认地址，不需要更新
                    addressViewModel.setDefaultAddress(addressListItem.getAddress_id(), (code, msg) -> {
                        if(code == 0) {isNeedDispatchNotify = true; loadData();}
                    });
                    break;
            }
        });
        adressManageAdapter.bindToRecyclerView(binding.addressRv);
        binding.addressRv.setAdapter(adressManageAdapter);
        binding.addressRv.addItemDecoration(new AdressItemDecoration(this));
        binding.addressRv.setLayoutManager(new LinearLayoutManager(this_));
        binding.addressRv.setHasFixedSize(true);
        addressViewModel = new AddressViewModel();
        addRunStatusChangeCallBack(addressViewModel);
        loadData();
    }

    @Override
    protected void onDataResult(Object o) {
        AdressListData adressListData = (AdressListData) o;
        if(adressListData.getList() != null && adressListData.getList().size() == 0) {
            onEmptyDisplaying();
        } else {
            addressListItems.clear();
            addressListItems.addAll(adressListData.getList());
            adressManageAdapter.notifyDataSetChanged();
        }
    }

    private void loadData() {
        if(addressViewModel != null) {
            addressViewModel.addressList();
        }
    }

    @Override
    protected void onEmptyDisplaying() {
        addressListItems.clear();
        adressManageAdapter.notifyDataSetChanged();
        adressManageAdapter.setEmptyView(R.layout.layout_empty_address, (ViewGroup) binding.addressRv.getParent());
    }

    @Override
    public void onMessageEvent(CommEvent event) {
        if(CommEvent.MSG_TYPE_NOTIFY_UPDATE.equals(event.msgType)) {
            loadData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isNeedDispatchNotify) {
            EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_NOTIFY_UPDATE));
        }
    }
}
