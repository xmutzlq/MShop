package com.king.android.details.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.king.android.details.R;
import com.king.android.details.adapter.spec.SpecAdapter;
import com.king.android.details.cache.SpecData;
import com.king.android.details.util.SpecParams;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import google.architecture.common.widget.BaseBottomSheetFrag;

/**
 * @author lq.zeng
 * @date 2018/12/27
 */

public class ChooseBottomSheetFragment extends BaseBottomSheetFrag {

    private SpecAdapter adapter;

    private SpecParams specParams;

    public void setData(SpecParams specParams) {
        this.specParams = specParams;
    }

    @Override
    public boolean isNeedHeight() {
        return false;
    }

    @Override
    public boolean isNeedHideBg() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.xlj_layout_choose;
    }

    @Override
    public void initView() {
        ImageView closeIv = rootView.findViewById(R.id.server_close_iv);
        closeIv.setOnClickListener(v -> {dismiss();});
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = rootView.findViewById(R.id.detail_bottom_sheet_rv);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        List<SpecData> specData = prepareData(specParams);
        adapter = new SpecAdapter(specData, selectPosSet -> {});
        recyclerView.setAdapter(adapter);
    }

    private List<SpecData> prepareData(SpecParams specParams) {
        List<SpecData> specDatas = new ArrayList<>();
        for (SpecParams.SpecChild specChild : specParams.specs) {
            SpecData colorSpec = new SpecData();
            colorSpec.name = specChild.name;
            colorSpec.type = SpecData.TYPE_COLOR;
            colorSpec.item1 = specChild.detailSpecInfos;
            specDatas.add(colorSpec);
        }
        SpecData countSpec = new SpecData();
        countSpec.type = SpecData.TYPE_COUNT;
        countSpec.count = 1;
        specDatas.add(countSpec);

        google.architecture.coremodel.data.xlj.goodsdetail.List list1 =
                new google.architecture.coremodel.data.xlj.goodsdetail.List();
        list1.setItemName("快递");

        google.architecture.coremodel.data.xlj.goodsdetail.List list2 =
                new google.architecture.coremodel.data.xlj.goodsdetail.List();
        list2.setItemName("到店自取");

        List<google.architecture.coremodel.data.xlj.goodsdetail.List> items =
                new ArrayList<>();
        items.add(list1);
        items.add(list2);

        SpecData gettingSpec = new SpecData();
        gettingSpec.name = "取件方式";
        gettingSpec.type = SpecData.TEXT_SERVICE;
        gettingSpec.item1 = items;
        specDatas.add(gettingSpec);
        return specDatas;
    }

}
