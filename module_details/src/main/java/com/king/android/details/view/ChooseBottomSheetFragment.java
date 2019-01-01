package com.king.android.details.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.king.android.details.R;
import com.king.android.details.adapter.spec.SpecAdapter;
import com.king.android.details.cache.SpecData;
import com.king.android.details.util.DetailUtil;
import com.king.android.details.util.SpecParams;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.widget.BaseBottomSheetFrag;
import google.architecture.coremodel.util.TextUtil;

/**
 * @author lq.zeng
 * @date 2018/12/27
 */

public class ChooseBottomSheetFragment extends BaseBottomSheetFrag {

    private SpecAdapter adapter;

    private SpecParams specParams;

    private TextView goodsChooseTv, goodsSaveTv;
    private String defaultColor, defaultSize;

    private INotifySpecChange notifySpecChange;

    public void setData(SpecParams specParams) {
        this.specParams = specParams;
    }

    public void setNotifySpecChange(INotifySpecChange notifySpecChange) {
        this.notifySpecChange = notifySpecChange;
    }

    @Override
    public boolean isNeedHeight() {
        return true;
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
        initTop(rootView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = rootView.findViewById(R.id.detail_bottom_sheet_rv);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        List<SpecData> specData = prepareData(specParams);
        adapter = new SpecAdapter(specData, (type, catId, selectPosSet) -> {
            if(selectPosSet == null || selectPosSet.size() == 0) return;
            String color = defaultColor;
            String size = defaultSize;
            StringBuilder stringBuilder = new StringBuilder();
            int selectedPosition = selectPosSet.iterator().next();
            switch (type) {
                case SpecAdapter.TYPE_SERVICE: //配送
                    break;
                case SpecAdapter.TYPE_COLOR: //颜色&尺码
                    if(!TextUtil.isEmpty(catId)) {
                        if(catId.equals("1")) { //颜色
                            if(selectedPosition <= specParams.specsColor.detailSpecInfos.size()) {
                                color = specParams.specsColor.detailSpecInfos.get(selectedPosition).getItemName();
                                updateSelected(color, defaultSize);
                            }
                        } else if(catId.equals("5")) { //尺码
                            if(selectedPosition <= specParams.specsSize.detailSpecInfos.size()) {
                                size = specParams.specsSize.detailSpecInfos.get(selectedPosition).getItemName();
                                updateSelected(defaultColor, size);
                            }
                        }
                    }
                    break;
            }
            stringBuilder.append(color).append("；").append(size);
            //更新界面中的规格
            if(notifySpecChange != null) {
                notifySpecChange.onSpecChange(stringBuilder.toString());
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void initTop(View rootView) {
        //关闭
        ImageView closeIv = rootView.findViewById(R.id.server_close_iv);
        closeIv.setOnClickListener(v -> {dismiss();});
        //图片
        SimpleDraweeView draweeView = rootView.findViewById(R.id.detail_bottom_sheet_iv);
        draweeView.setImageURI(specParams.img);
        //价格
        TextView priceTv = rootView.findViewById(R.id.goods_price_tv);
        priceTv.setText("¥" + specParams.price);
        //库存
        goodsSaveTv = rootView.findViewById(R.id.goods_save_tv);
        DetailUtil.GoodsSizeParam goodsSizeParam = DetailUtil.getDefaultSizeAndSaveCount(
                specParams.specsSize.detailSpecInfos, specParams.defaultSizeId);
        updateSaveCount(String.valueOf(goodsSizeParam.pAllSave), String.valueOf(goodsSizeParam.pShopSave));
        //已选
        goodsChooseTv = rootView.findViewById(R.id.goods_choose_shown_tv);
        String defaultColor = DetailUtil.getDefaultColor(specParams.specsColor.detailSpecInfos, specParams.defaultColorId);
        updateSelected(defaultColor, goodsSizeParam.pSize);
    }

    private void updateSaveCount(String allSave, String shopSave) {
        goodsSaveTv.setText(getResources().getString(R.string.detail_save_get_value, allSave, shopSave));
    }

    private void updateSelected(String colorStr, String sizeStr) {
        defaultColor = colorStr;
        defaultSize = sizeStr;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\"").append(colorStr).append("；").append(sizeStr).append("\"");
        goodsChooseTv.setText(getResources().getString(R.string.detail_choose_flag_value, stringBuilder.toString()));
    }

    private List<SpecData> prepareData(SpecParams specParams) {
        List<SpecData> specDatas = new ArrayList<>();
        //颜色
        SpecParams.SpecChild specsColor = specParams.specsColor;
        SpecData colorSpec = new SpecData();
        colorSpec.name = specsColor.name;
        colorSpec.type = SpecData.TYPE_COLOR;
        colorSpec.item1 = specsColor.detailSpecInfos;
        specDatas.add(colorSpec);
        //尺码
        SpecParams.SpecChild specsSize = specParams.specsSize;
        SpecData sizeSpec = new SpecData();
        sizeSpec.name = specsSize.name;
        sizeSpec.type = SpecData.TYPE_COLOR;
        sizeSpec.item1 = specsSize.detailSpecInfos;
        specDatas.add(sizeSpec);
        //件数
        SpecData countSpec = new SpecData();
        countSpec.type = SpecData.TYPE_COUNT;
        countSpec.count = 1;
        specDatas.add(countSpec);

        google.architecture.coremodel.data.xlj.goodsdetail.List list1 =
                new google.architecture.coremodel.data.xlj.goodsdetail.List();
        list1.setItemName("快递配送");
        list1.setIsDefault(1);
        google.architecture.coremodel.data.xlj.goodsdetail.List list2 =
                new google.architecture.coremodel.data.xlj.goodsdetail.List();
        list2.setItemName("到店自取");

        List<google.architecture.coremodel.data.xlj.goodsdetail.List> items =
                new ArrayList<>();
        items.add(list1);
        items.add(list2);
        //配送
        SpecData gettingSpec = new SpecData();
        gettingSpec.name = "取件方式";
        gettingSpec.type = SpecData.TEXT_SERVICE;
        gettingSpec.item1 = items;
        specDatas.add(gettingSpec);
        return specDatas;
    }

    public interface INotifySpecChange {
        void onSpecChange(String newSpec);
    }
}
