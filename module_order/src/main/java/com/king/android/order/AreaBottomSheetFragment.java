package com.king.android.order;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.FrameLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.viewmodel.AddressViewModel;
import google.architecture.common.widget.BaseBottomSheetFrag;
import google.architecture.common.widget.address.ISelectAble;
import google.architecture.common.widget.address.Selector;
import google.architecture.coremodel.data.AddressData;
import google.architecture.coremodel.datamodel.http.event.CommEvent;

/**
 * @author lq.zeng
 * @date 2018/9/10
 */

public class AreaBottomSheetFragment extends BaseBottomSheetFrag {

    private static final int DEFAULT_DEEP = 4;
    private Selector selector;
    private AddressViewModel addressViewModel;
    private ArrayList<AddressData.AddressItem> addressData;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_address_select;
    }

    @Override
    public void initView() {
        addressData = new ArrayList<>();
        addressViewModel = new AddressViewModel();
        selector = new Selector(mContext, DEFAULT_DEEP);
        selector.setDeepInterrupter(() -> DEFAULT_DEEP);
        selector.setSelectedListener(selectAbles -> {
            for (ISelectAble selectAble : selectAbles) {
                AddressData.AddressItem addressItem = new AddressData.AddressItem();
                addressItem.setId(selectAble.getId());
                addressItem.setName(selectAble.getName());
                addressData.add(addressItem);
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(CommKeyUtil.EXTRA_KEY, addressData);
            EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_CLOSE_AREA, bundle));
            close(false);
        });
        rootView.findViewById(R.id.address_select_close_iv).setOnClickListener(v -> close(false));
        ((FrameLayout)rootView.findViewById(R.id.address_layout)).addView(selector.getView());
        loadAreaData();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(addressViewModel != null) addressViewModel.clear();
    }

    private void loadAreaData() {
        addressViewModel.getProvinces(t -> {
            AddressData addressData = (AddressData) t;
            List<ISelectAble> provinces = new ArrayList<>();
            for (AddressData.AddressItem data : addressData.getList()) {
                ISelectAble able = new SelectAble(data.getId(), data.getName(), String.valueOf(data.getId()));
                provinces.add(able);
            }
            notifyResult();
        });
    }

    private void notifyResult() {
        selector.setDataProvider((currentDeep, preId, receiver) -> {
            //根据tab的深度和前一项选择的id，获取下一级菜单项
            switch (currentDeep) {
                case 0:
                    addressViewModel.getProvinces(t -> {
                        AddressData addressData = (AddressData) t;
                        List<ISelectAble> provinces = new ArrayList<>();
                        for (AddressData.AddressItem data : addressData.getList()) {
                            ISelectAble able = new SelectAble(data.getId(), data.getName(), String.valueOf(data.getId()));
                            provinces.add(able);
                        }
                        receiver.send(provinces);
                    });
                    break;
                case 1:
                    addressViewModel.getCites(String.valueOf(preId), t -> {
                        AddressData addressData = (AddressData) t;
                        List<ISelectAble> cites = new ArrayList<>();
                        for (AddressData.AddressItem data : addressData.getList()) {
                            ISelectAble able = new SelectAble(data.getId(), data.getName(), String.valueOf(data.getId()));
                            cites.add(able);
                        }
                        receiver.send(cites);
                    });
                    break;
                case 2:
                    addressViewModel.getDistricts(String.valueOf(preId), t -> {
                        AddressData addressData = (AddressData) t;
                        List<ISelectAble> districts = new ArrayList<>();
                        for (AddressData.AddressItem data : addressData.getList()) {
                            ISelectAble able = new SelectAble(data.getId(), data.getName(), String.valueOf(data.getId()));
                            districts.add(able);
                        }
                        receiver.send(districts);
                    });
                    break;
                case 3:
                    addressViewModel.getTowns(String.valueOf(preId), t -> {
                        AddressData addressData = (AddressData) t;
                        List<ISelectAble> towns = new ArrayList<>();
                        for (AddressData.AddressItem data : addressData.getList()) {
                            ISelectAble able = new SelectAble(data.getId(), data.getName(), String.valueOf(data.getId()));
                            towns.add(able);
                        }
                        receiver.send(towns);
                    });
                    break;
            }
        });
    }

    static class SelectAble implements ISelectAble {

        int id;
        String name;
        String referenceId;

        public SelectAble(int id, String name, String referenceId) {
            this.id = id;
            this.name = name;
            this.referenceId = referenceId;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public Object getArg() {
            return referenceId;
        }
    }
}
