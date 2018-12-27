package com.king.android.details.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.king.android.details.R;
import com.king.android.details.adapter.spec.SpecAdapter;
import com.king.android.details.cache.SpecData;
import com.king.android.details.util.SpecParams;

import java.util.ArrayList;
import java.util.List;

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
        adapter = new SpecAdapter(prepareData(specParams), selectPosSet -> {});
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
        return specDatas;
    }

}
