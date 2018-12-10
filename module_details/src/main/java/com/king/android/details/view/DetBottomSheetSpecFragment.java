package com.king.android.details.view;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.king.android.details.R;
import com.king.android.details.adapter.spec.SpecAdapter;
import com.king.android.details.cache.SpecData;
import com.king.android.res.config.ARouterPath;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.params.ConfirmOrderParams;
import google.architecture.common.util.AppCompat;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.util.ToastUtils;
import google.architecture.common.viewmodel.DetailCommdityViewModel;
import google.architecture.common.widget.BaseBottomSheetFrag;
import google.architecture.common.widget.span.Spans;
import google.architecture.coremodel.data.DetailSpecInfo;
import google.architecture.coremodel.data.DetailSpecListInfo;
import google.architecture.coremodel.data.DetailSpecSelectedInfo;
import google.architecture.coremodel.data.GoodsSpecData;
import google.architecture.coremodel.datamodel.http.event.CommEvent;

/**
 * @author lq.zeng
 * @date 2018/7/12
 */

public class DetBottomSheetSpecFragment extends BaseBottomSheetFrag {

    public static final int TYPE_NONE = -1;
    public static final int TYPE_BUY = 0;
    public static final int TYPE_ADD_CART = 1;

    private String goodsId;
    private List<DetailSpecInfo> detailSpecInfos;
    private DetailSpecSelectedInfo detailSpecSelectedInfo;
    private GoodsSpecData goodsSpecDataRemote;
    private boolean isNormal;
    private int operateType = TYPE_NONE;

    private SpecAdapter adapter;
    private DetailCommdityViewModel detailCommdityViewModel;

    public void setData(String goodsId, List<DetailSpecInfo> detailSpecInfos, DetailSpecSelectedInfo detailSpecSelectedInfo) {
        setData(true, goodsId, detailSpecInfos, detailSpecSelectedInfo);
    }

    public void setData(boolean isNormal, String goodsId, List<DetailSpecInfo> detailSpecInfos, DetailSpecSelectedInfo detailSpecSelectedInfo) {
        this.isNormal = isNormal;
        this.goodsId = goodsId;
        this.detailSpecInfos = detailSpecInfos;
        this.detailSpecSelectedInfo = detailSpecSelectedInfo;
    }

    public void setOperateType(int operateType) {
        this.operateType = operateType;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.layout_det_bottom_sheet_goods_spec;
    }

    @Override
    public void initView() {

        detailCommdityViewModel = new DetailCommdityViewModel();

        ImageView iv = rootView.findViewById(R.id.detail_bottom_sheet_iv);
        TextView price = rootView.findViewById(R.id.detail_bottom_sheet_price);
        TextView no = rootView.findViewById(R.id.detail_bottom_sheet_no);

        price.setText(Spans.builder().text("¥", 12, ContextCompat.getColor(getContext(), R.color.color_ff593e))
                .text(detailSpecSelectedInfo.getPrice(), 23, ContextCompat.getColor(getContext(), R.color.color_ff593e)).typeface(Typeface.DEFAULT_BOLD)
                .build());
        no.setText("库存：" + detailSpecSelectedInfo.getStore_count());

        if(!isNormal) {
            rootView.findViewById(R.id.layout_material_one_btn).setVisibility(View.GONE);
            rootView.findViewById(R.id.layout_common_two_btn).setVisibility(View.VISIBLE);
        } else {
            rootView.findViewById(R.id.layout_material_one_btn).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.layout_common_two_btn).setVisibility(View.GONE);
        }

        ImageLoader.get().load(iv, detailSpecSelectedInfo.getImage());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = rootView.findViewById(R.id.detail_bottom_sheet_rv);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        adapter = new SpecAdapter(prepareData(detailSpecInfos), selectPosSet -> {
            detailCommdityViewModel.choiceGoodsSpec(goodsId, getGoodsIds(adapter.getData()), t -> {
                goodsSpecDataRemote = (GoodsSpecData) t;
                ImageLoader.get().load(iv, goodsSpecDataRemote.getSpec_select_info().getImage());
                price.setText(Spans.builder().text("¥", 14, ContextCompat.getColor(getContext(), R.color.color_ff593e))
                        .text(goodsSpecDataRemote.getSpec_select_info().getPrice(), 23, ContextCompat.getColor(getContext(), R.color.color_ff593e)).typeface(Typeface.DEFAULT_BOLD)
                        .build());
                Bundle bundle = new Bundle();
                bundle.putParcelable(CommKeyUtil.EXTRA_KEY, goodsSpecDataRemote.getSpec_select_info());
                CommEvent event = new CommEvent(CommEvent.MSG_TYPE_GOODS_SPC_UPDATE);
                event.bundle = bundle;
                EventBus.getDefault().post(event);
            });
        });
        recyclerView.setAdapter(adapter);

        if(rootView.findViewById(R.id.common_left_btn) != null) {
            AppCompat.setBackgroundPressed(mContext, rootView.findViewById(R.id.common_left_btn), R.color.common_title_bg);
            rootView.findViewById(R.id.common_left_btn).setOnClickListener(v -> { //立即购买
                close(false);
                String[] params = getSpecParamsWithRemote(adapter.getData());
                ConfirmOrderParams confirmOrderParams = new ConfirmOrderParams();
                confirmOrderParams.goodsId = goodsId;
                if(params != null && params.length > 1) {
                    confirmOrderParams.itemsId = params[0];
                    confirmOrderParams.goodsNum = params[1];
                }
                ARouter.getInstance().build(ARouterPath.OrderMainActy).withParcelable(CommKeyUtil.EXTRA_KEY, confirmOrderParams).navigation();
            });
        }

        if(rootView.findViewById(R.id.common_right_btn) != null) {
            AppCompat.setBackgroundPressed(mContext, rootView.findViewById(R.id.common_right_btn), R.color.themePrimaryDark);
            rootView.findViewById(R.id.common_right_btn).setOnClickListener(v -> { //加入购物车
                close(false);
                Bundle bundle = new Bundle();
                bundle.putStringArray(CommKeyUtil.EXTRA_KEY, getSpecParamsWithRemote(adapter.getData()));
                CommEvent event = new CommEvent(CommEvent.MSG_TYPE_UPDATE_CART);
                event.bundle = bundle;
                EventBus.getDefault().post(event);
            });
        }

        if(rootView.findViewById(R.id.common_btn_material) != null) {
            AppCompat.setBackgroundPressed(mContext, rootView.findViewById(R.id.common_btn_material), R.color.themePrimaryDark);
            rootView.findViewById(R.id.common_btn_material).setOnClickListener(v -> { //确定
                close(false);
                switch (operateType) {
                    case TYPE_NONE:
                        ToastUtils.showShortToast("请选择操作类型");
                        break;
                    case TYPE_BUY:
                        String[] params = getSpecParamsWithRemote(adapter.getData());
                        ConfirmOrderParams confirmOrderParams = new ConfirmOrderParams();
                        confirmOrderParams.goodsId = goodsId;
                        if(params != null && params.length > 1) {
                            confirmOrderParams.itemsId = params[0];
                            confirmOrderParams.goodsNum = params[1];
                        }
                        ARouter.getInstance().build(ARouterPath.OrderMainActy).withParcelable(CommKeyUtil.EXTRA_KEY, confirmOrderParams).navigation();
                        break;
                    case TYPE_ADD_CART:
                        Bundle bundle = new Bundle();
                        bundle.putStringArray(CommKeyUtil.EXTRA_KEY, getSpecParamsWithRemote(adapter.getData()));
                        CommEvent event = new CommEvent(CommEvent.MSG_TYPE_UPDATE_CART);
                        event.bundle = bundle;
                        EventBus.getDefault().post(event);
                        break;
                }
            });
        }
    }

    private List<SpecData> prepareData(List<DetailSpecInfo> detailSpecInfos) {
        List<SpecData> specDatas = new ArrayList<>();
        for (DetailSpecInfo info : detailSpecInfos) {
            SpecData colorSpec = new SpecData();
            colorSpec.name = info.getSpec_name();
            colorSpec.type = SpecData.TYPE_COLOR;
            colorSpec.colors = info.getSpec_list();
            specDatas.add(colorSpec);
        }
        SpecData countSpec = new SpecData();
        countSpec.type = SpecData.TYPE_COUNT;
        countSpec.count = 1;
        specDatas.add(countSpec);
        return specDatas;
    }

    private String[] getSpecParamsWithRemote(List<SpecData> specData) {
        String params[] = new String[2];
        if(goodsSpecDataRemote != null) {
            params[0] = goodsSpecDataRemote.getSpec_select_info().getItem_ids();
            params[1] = getGoodsNum(specData);
        } else {
            params = getSpecParams(specData);
        }
        return params;
    }

    private String[] getSpecParams(List<SpecData> specData) {
        String[] params = new String[2];
        StringBuilder itemIds = new StringBuilder();
        for (SpecData info : specData) {
            switch (info.type) {
                case SpecData.TYPE_COLOR:
                    for (DetailSpecListInfo info1 : info.colors) {
                        if(info1.getSelected() == 1) {
                            itemIds.append(info1.getItem_id()).append(",");
                        }
                    }
                    params[0] = itemIds.toString();
                    break;
                case SpecData.TYPE_COUNT:
                    params[1] = String.valueOf(info.count);
                    break;
            }
        }
        return params;
    }

    private String getGoodsIds(List<SpecData> specData) {
        StringBuilder itemIds = new StringBuilder();
        for (SpecData info : specData) {
            if (SpecData.TYPE_COLOR == info.type) {
                for (DetailSpecListInfo info1 : info.colors) {
                    if(info1.getSelected() == 1) {
                        itemIds.append(info1.getItem_id()).append(",");
                    }
                }
            }
        }
        return itemIds.toString();
    }

    private String getGoodsNum(List<SpecData> specData) {
        for (SpecData info : specData) {
            if (SpecData.TYPE_COUNT == info.type) {
                return String.valueOf(info.count);
            }
        }
        return "0";
    }
}
