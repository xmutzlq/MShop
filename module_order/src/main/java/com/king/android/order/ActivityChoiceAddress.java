package com.king.android.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.king.android.order.adapter.AdressChoiceAdapter;
import com.king.android.order.databinding.ActivityChoiceAddressBinding;
import com.king.android.res.config.ARouterPath;
import com.kongzue.dialog.v2.SelectDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.ViewManager;
import google.architecture.common.util.AppCompat;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.viewmodel.AddressViewModel;
import google.architecture.coremodel.data.AdressListData;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.coremodel.util.TextUtil;

/**
 * @author lq.zeng
 * @date 2018/9/7
 */
@Route(path = ARouterPath.OrderAddressChoiceActy)
public class ActivityChoiceAddress extends BaseActivity<ActivityChoiceAddressBinding>{

    private AddressViewModel addressViewModel;
    private AdressChoiceAdapter adressChoiceAdapter;
    private List<AdressListData.AddressListItem> addressListItems;
    private String addressId;

    @Override
    protected int getLayout() {
        return R.layout.activity_choice_address;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanBack(true);
        setTitleName(getString(R.string.add_choice));

        setRightText(getResources().getString(R.string.str_manage),
                v -> ARouter.getInstance().build(ARouterPath.OrderAddressManageActy).navigation());

        AppCompat.setBackgroundPressed(this_, binding.btnAddAddress, R.color.themePrimaryDark);
        binding.btnAddAddress.setOnClickListener(
                v -> ARouter.getInstance().build(ARouterPath.OrderAddressAddActy).navigation());

        if(getIntent() != null) {
            Intent intent = getIntent();
            addressId = intent.getStringExtra(CommKeyUtil.EXTRA_KEY);
        }

        addressListItems = new ArrayList<>();
        adressChoiceAdapter = new AdressChoiceAdapter(R.layout.layout_address_item_info, addressListItems);
        adressChoiceAdapter.setOnItemClickListener((adapter, view, position) -> {
            adressChoiceAdapter.setChecked(position);
            Looper.myQueue().addIdleHandler(() -> {
                Bundle bundle = new Bundle();
                bundle.putParcelable(CommKeyUtil.EXTRA_KEY, addressListItems.get(position));
                EventBus.getDefault().unregister(this);
                EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_NOTIFY_UPDATE, bundle));
                ViewManager.getInstance().finishActivity();
                return false;
            });
        });
        adressChoiceAdapter.bindToRecyclerView(binding.addressRv);
        binding.addressRv.setAdapter(adressChoiceAdapter);
        binding.addressRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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
            SelectDialog.show(this_, getResources().getString(R.string.str_friendly_reminder),
                    getResources().getString(R.string.address_first_tip), getResources().getString(R.string.str_add), (dialog, which) -> {
                        //添加收货地址
                        ARouter.getInstance().build(ARouterPath.OrderAddressAddActy).navigation();
                    }, getResources().getString(R.string.str_cancel), (dialog, which) -> {
                        ViewManager.getInstance().finishActivity();
                    });
        } else {
            addressListItems.clear();
            if(!TextUtil.isEmpty(addressId)) {
                for (AdressListData.AddressListItem addressListItem : adressListData.getList()) {
                    if(addressId.equals(addressListItem.getAddress_id())) {
                        addressListItem.isSelected = true;
                        break;
                    }
                }
            }
            addressListItems.addAll(adressListData.getList());
            adressChoiceAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onEmptyDisplaying() {
        addressListItems.clear();
        adressChoiceAdapter.notifyDataSetChanged();
        adressChoiceAdapter.setEmptyView(R.layout.layout_empty_address, (ViewGroup) binding.addressRv.getParent());
    }

    @Override
    public void onMessageEvent(CommEvent event) {
        if(CommEvent.MSG_TYPE_NOTIFY_UPDATE.equals(event.msgType)) {
            loadData();
        }
    }

    private void loadData() {
        if(addressViewModel != null) {
            addressViewModel.addressList();
        }
    }
}
