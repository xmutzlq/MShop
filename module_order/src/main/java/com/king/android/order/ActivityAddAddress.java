package com.king.android.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.king.android.order.databinding.ActivityAddAddressBinding;
import com.king.android.res.config.ARouterPath;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.ViewManager;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.util.ToastUtils;
import google.architecture.common.viewmodel.AddressViewModel;
import google.architecture.coremodel.data.AddressData;
import google.architecture.coremodel.data.AdressListData;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import io.reactivex.Observable;

/**
 * @author lq.zeng
 * @date 2018/9/10
 */
@Route(path = ARouterPath.OrderAddressAddActy)
public class ActivityAddAddress extends BaseActivity<ActivityAddAddressBinding> {

    private AddressViewModel addressViewModel;
    private ArrayList<AddressData.AddressItem> mAddressItems;

    private boolean isEdit;
    private AdressListData.AddressListItem addressListItem;

    @Override
    protected int getLayout() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanBack(true);
        setTitleName(getString(R.string.add_address));
        setRightText(getResources().getString(R.string.str_save), v -> {
            save();
        });

        Intent intent = getIntent();
        if(intent != null) {
            addressListItem = intent.getParcelableExtra(CommKeyUtil.EXTRA_KEY);
            if(addressListItem != null) {
                isEdit = true;
                updateUI(addressListItem);
            }
        }

        mAddressItems = new ArrayList<>();
        addressViewModel = new AddressViewModel();
        addRunStatusChangeCallBack(addressViewModel);

        binding.addressPhoneEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        binding.addressZipcodeEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

        binding.addressAreaLay.setOnClickListener(v -> {
            new AreaBottomSheetFragment().show(getSupportFragmentManager(), "Dialog");
        });
    }

    private void updateUI(AdressListData.AddressListItem addressListItem) {
        if(addressListItem == null) return;
        binding.addressConsigneeEt.setText(addressListItem.getConsignee());
        binding.addressPhoneEt.setText(addressListItem.getMobile());
        StringBuilder sb = new StringBuilder();
        sb.append(addressListItem.getProvince_name()).append(addressListItem.getCity_name()).append(addressListItem.getDistrict_name());
        binding.addressAreaEt.setText(sb.toString());
        binding.addressZipcodeEt.setText(addressListItem.getZipcode());
        binding.addressDetailEt.setText(addressListItem.getAddress());
    }

    @Override
    public void onMessageEvent(CommEvent event) {
        if(CommEvent.MSG_TYPE_CLOSE_AREA.equals(event.msgType)) {
            Bundle bundle = event.bundle;
            ArrayList<AddressData.AddressItem> addressItems = bundle.getParcelableArrayList(CommKeyUtil.EXTRA_KEY);
            if(addressItems != null && addressItems.size() > 0) {
                mAddressItems.clear();
                mAddressItems.addAll(addressItems);
                Observable.fromIterable(addressItems)
                        .filter(addressItem -> addressItem != null)
                        .flatMap(addressItem -> Observable.just(addressItem.getName()))
                        .scan((s, s2) -> s + " " + s2).subscribe(s -> binding.addressAreaEt.setText(s));
            }
        }
    }

    private void save() {
        if(TextUtils.isEmpty(binding.addressConsigneeEt.getText().toString())
                || binding.addressConsigneeEt.getText().length() < 2) {
            ToastUtils.showShortToast("姓名最短两个字");
            return;
        }
        if(TextUtils.isEmpty(binding.addressPhoneEt.getText().toString())
                || binding.addressPhoneEt.getText().length() < 11){
            ToastUtils.showShortToast("请输入11位手机号");
            return;
        }
        if(TextUtils.isEmpty(binding.addressAreaEt.getText().toString())){
            ToastUtils.showShortToast("未填写区域");
            return;
        }
        if(TextUtils.isEmpty(binding.addressZipcodeEt.getText().toString())
                || binding.addressZipcodeEt.getText().length() < 6){
            ToastUtils.showShortToast("请输入6位邮编");
            return;
        }
        if(TextUtils.isEmpty(binding.addressDetailEt.getText().toString())
                || binding.addressDetailEt.getText().length() < 5){
            ToastUtils.showShortToast("详细地址最短5个字");
            return;
        }

        String provinceId = mAddressItems.size() > 0 ? String.valueOf(mAddressItems.get(0).getId()) : "";
        String cityId = mAddressItems.size() > 1 ? String.valueOf(mAddressItems.get(1).getId()) : "";
        String districtId = mAddressItems.size() > 2 ? String.valueOf(mAddressItems.get(2).getId()) : "";
        String townId = mAddressItems.size() > 3 ? String.valueOf(mAddressItems.get(3).getId()) : "";

        String provinceName = mAddressItems.size() > 0 ? mAddressItems.get(0).getName() : "";
        String cityName = mAddressItems.size() > 1 ? mAddressItems.get(1).getName() : "";
        String districtName = mAddressItems.size() > 2 ? mAddressItems.get(2).getName() : "";
        String townName = mAddressItems.size() > 3 ? mAddressItems.get(3).getName() : "";

        if(isEdit) {
            addressViewModel.modifyAddress(provinceName, cityName, districtName, townName, binding.addressDetailEt.getText().toString(),
                    binding.addressZipcodeEt.getText().toString(), String.valueOf(addressListItem.getIs_default()), binding.addressConsigneeEt.getText().toString(), binding.addressPhoneEt.getText().toString(),
                    addressListItem.getAddress_id(), provinceId, cityId, districtId, townId, (code, msg) -> {
                        if(code == 0) {
                            EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_NOTIFY_UPDATE));
                            ViewManager.getInstance().finishActivity();
                        }
                    });
        } else {
            addressViewModel.addAddress(provinceName, cityName, districtName, townName, binding.addressDetailEt.getText().toString(),
                    binding.addressZipcodeEt.getText().toString(), binding.addressConsigneeEt.getText().toString(), binding.addressPhoneEt.getText().toString(),
                    provinceId, cityId, districtId, townId, (code, msg) -> {
                        if(code == 0) {
                            EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_NOTIFY_UPDATE));
                            ViewManager.getInstance().finishActivity();
                        }
                    });
        }
    }
}
