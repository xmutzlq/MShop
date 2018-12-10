package com.king.android;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.king.android.adapter.CartExpandableItemAdapter;
import com.king.android.databinding.FragmentCartBinding;
import com.king.android.res.config.ARouterPath;
import com.kongzue.dialog.v2.SelectDialog;
import com.oushangfeng.pinnedsectionitemdecoration.PinnedHeaderItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseFragment;
import google.architecture.common.util.AppCompat;
import google.architecture.common.viewmodel.CartViewModel;
import google.architecture.coremodel.MultiItemTypeHelper;
import google.architecture.coremodel.data.CartBusinessesData;
import google.architecture.coremodel.data.CartData;
import google.architecture.coremodel.data.CartGoodsData;
import google.architecture.coremodel.data.CartRemoteBusinessesData;
import google.architecture.coremodel.data.CartRemoteGoodsData;
import google.architecture.coremodel.data.DetailRecommendInfo;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lq.zeng
 * @date 2018/5/10
 */
@Route(path = ARouterPath.CartFgt)
public class FragmentCart extends BaseFragment<FragmentCartBinding> implements View.OnClickListener {
    SmartRefreshLayout refreshLayout;
    SwipeMenuRecyclerView mRecyclerView;
    CheckBox mCartAllCheckCB;
    ImageView mQuickGoTopIV;
    CartExpandableItemAdapter adapter;
    PinnedHeaderItemDecoration pinnedHeaderItemDecoration;
    ArrayList<MultiItemEntity> list;

    public static FragmentCart newInstance() {
        return new FragmentCart();
    }

    @Override
    protected boolean isStatusBarTransparent() {
        return false;
    }

    @Override
    protected int getLayout() { return R.layout.fragment_cart; }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        getToolbarHelper().getAppBarLayout().setVisibility(View.VISIBLE);
        setTitleName(R.string.moudle_name);
        setRightText(R.string.cart_manage, this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mQuickGoTopIV = binding.cartGoTop;
        mQuickGoTopIV.setOnClickListener(v -> { mRecyclerView.scrollToPosition(0); });
        mCartAllCheckCB = binding.cbCartAllSelect;
        //全选
        binding.btnCartAllSelect.setOnClickListener(v->{
            mCartAllCheckCB.setChecked(!mCartAllCheckCB.isChecked());
            if(adapter != null) adapter.notifyAllCheckedRemote(mCartAllCheckCB.isChecked());
        });
        //删除多项
        binding.cartTvDel.setOnClickListener(v -> {
            if(adapter != null) adapter.notifyDeleteItemsRemote();
        });
        //下单
        AppCompat.setBackgroundPressed(mContext, binding.btnCartCommit, R.color.themePrimaryDark);
        binding.btnCartCommit.setOnClickListener(v -> {
            ARouter.getInstance().build(ARouterPath.OrderMainActy).navigation();
        });
        refreshLayout = binding.cartRefreshLayout;
        refreshLayout.setEnableAutoLoadMore(false);//使上拉加载具有弹性效果
        refreshLayout.setEnableOverScrollDrag(false);//禁止越界拖动（1.0.4以上版本）
        refreshLayout.setEnableOverScrollBounce(false);//关闭越界回弹功能
        refreshLayout.setOnRefreshListener(refreshLayout1 -> {
            loadData();
//           if(adapter.isEmptyState()) {
//               loadData();
//           } else {
//               refreshLayout1.finishRefresh();
//           }
        });
        list = new ArrayList<>();
        adapter = new CartExpandableItemAdapter(list, () -> loadData());
        mRecyclerView = binding.layoutCommRecyclerview;
        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.addItemDecoration(new CartItemDecoration());
        pinnedHeaderItemDecoration = new PinnedHeaderItemDecoration.Builder(MultiItemTypeHelper.TYPE_BUSINESSES).create();
        mRecyclerView.addItemDecoration(pinnedHeaderItemDecoration);
        mRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        mRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
        mRecyclerView.addOnScrollListener(cartScroll);
        adapter.setOnItemLongClickListener((adapter1, view1, position) -> {
            if(MultiItemTypeHelper.TYPE_BUSINESSES_GOODS == adapter1.getItemViewType(position)) {
                SelectDialog.show(mContext, "删除提示", "确认要从购物车删除所选商品？", "确定", (dialog, which) -> {
                    if (adapter != null) adapter.notifyDeleteItemRemote(position);
                }, "取消", (dialog, which) -> {});
            }
            return true;
        });
        adapter.setOnItemClickListener((adapter1, view1, position) -> {
            if(MultiItemTypeHelper.TYPE_GUESS_YOU_LIKE == adapter1.getItemViewType(position)) {
                DetailRecommendInfo detailRecommendInfo = (DetailRecommendInfo)adapter1.getData().get(position);
                ARouter.getInstance().build(ARouterPath.DetailAty).withString(CommEvent.KEY_EXTRA_VALUE, detailRecommendInfo.rec_id).navigation();
            }
        });
        adapter.setEmptyStateChangeListener(new CartExpandableItemAdapter.IEmptyStateChangeListener() {
            @Override
            public void onDeleteStateChangeListener() { }

            @Override
            public void onEmptyStateChangeListener(boolean isEmpty) {
                setRightText("", null);
            }
        });

        binding.setCheckAll(adapter);//绑定结算数据
    }

    @Override
    public void onFragmentFirstVisible() {
//        loadData(true);
        loadData();
    }

    @Override
    public void onMessageEvent(CommEvent event) {
        if(CommEvent.MSG_TYPE_UPDATE_CART_MAIN.equals(event.msgType)) {
            loadData();
        }
    }

    private void loadData() {
        CartViewModel cartViewModel = new CartViewModel();
        addRunStatusChangeCallBack(cartViewModel);
        cartViewModel.getCarts();
    }

    @Override
    protected void onDataResult(Object o) {
        CartData data = (CartData)o;
        Flowable.just(data).flatMap(cartData -> {
            List<MultiItemEntity> res = new ArrayList<>();
            List<CartRemoteBusinessesData> cartList = cartData.getCart_list();
            for (CartRemoteBusinessesData businessesData: cartList) {
                CartBusinessesData lv0 = new CartBusinessesData();
                lv0.businessesId = businessesData.getSupplier_id();
                lv0.businessesName = businessesData.getShop_name();
                boolean isInterrupt = false;
                boolean isCartBusinessesEnable = false;
                for (CartRemoteGoodsData goodsData : businessesData.getShop_list()) {
                    CartGoodsData lv1 = new CartGoodsData();
                    lv1.cartId = goodsData.getId(); //购物车ID
                    lv1.goodsId = goodsData.getGoods_id(); //商品ID
                    lv1.goodsPic = goodsData.getImage();
                    lv1.goodsPrice = goodsData.getGoods_price();
                    lv1.goodsName = goodsData.getGoods_name();
                    lv1.goodsStoreCount = goodsData.getStore_count(); //库存
                    lv1.goodsBuyLimit = goodsData.getBuy_limit();
                    lv1.goodsState = goodsData.getState();
                    lv1.isChecked = goodsData.getSelected() == 1;
                    lv1.count = goodsData.getGoods_num();
                    lv1.goodsDes = goodsData.getChange_depict();
                    lv0.addSubItem(lv1);
                    //【物品不是选择状态】并且【物品不是失效状态】
                    if((goodsData.getSelected() != 1 && goodsData.getState() != -1) && !isInterrupt) {
                        isInterrupt = true;
                    }
                    //【只要有一件物品可使用，店铺就可以使用】
                    if(goodsData.getState() != -1) {
                        isCartBusinessesEnable = true;
                    }
                }
                lv0.isCehcked = !isInterrupt; //商品状态由数据判断
                lv0.isEnable = isCartBusinessesEnable; //商品状态由数据判断
                res.add(lv0);
            }
            adapter.setEmptyState(res.size() == 0);
            return Flowable.just(res);
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(multiItemEntities -> {
            multiItemEntities.add(new CartDataCache.EmptyStateEntity());
            multiItemEntities.add(new CartDataCache.GuessTitleEntity("为·你·推·荐"));
            multiItemEntities.addAll(data.getGoods_recommend_list());
            list.clear();
            list.addAll(multiItemEntities);
            //显示需要的组件
            changeTitleRight(false);
//            adapter.setEmptyState(false);
            //刷新adapter
            mRecyclerView.setAdapter(adapter);
            //Gride分割
            gridLayoutManager.setSpanSizeLookup(spanSizeLookup);
            gridLayoutManager.setSmoothScrollbarEnabled(true);
            gridLayoutManager.setAutoMeasureEnabled(true);
            mRecyclerView.setLayoutManager(gridLayoutManager);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setNestedScrollingEnabled(false);
            adapter.expandAll();
            refreshLayout.finishRefresh();
            adapter.updateCartResultData(data.getSelect_goods_price(), data.getSelect_goods_num());
            adapter.checkAllState();
        });
    }

    private void loadData(boolean isAll) {
        CartActivity.loadData((AppCompatActivity) mContext, ()->{
            if(isAll) {
                list.clear();
                list.addAll(CartDataCache.allCartData);
            } else {
                list.addAll(0, CartDataCache.cartData);
            }
            //显示需要的组件
            changeTitleRight(false);
//            adapter.setEmptyState(false);
            //刷新adapter
            mRecyclerView.setAdapter(adapter);
            //Gride分割
            gridLayoutManager.setSpanSizeLookup(spanSizeLookup);
            gridLayoutManager.setSmoothScrollbarEnabled(true);
            gridLayoutManager.setAutoMeasureEnabled(true);
            mRecyclerView.setLayoutManager(gridLayoutManager);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setNestedScrollingEnabled(false);
            adapter.expandAll();
            refreshLayout.finishRefresh();
        });
    }



    @Override
    public void onClick(View v) {
        int shown = binding.cartManageLayout.isShown() ? View.GONE : View.VISIBLE;
        changeTitleRight(shown == View.VISIBLE);
        adapter.setControllSate(shown);
        adapter.topManageShown.set(shown == View.VISIBLE);
        binding.cartTvDel.setEnabled(adapter.getData().size() > 0);
    }

    private void changeTitleRight(boolean isManageShown) {
        setRightText(isManageShown ? R.string.cart_manage_close : R.string.cart_manage, this);
    }

    GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
    GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {

        @Override
        public int getSpanSize(int position) {
            return adapter.getItemViewType(position) == MultiItemTypeHelper.TYPE_GUESS_YOU_LIKE ? 1 : gridLayoutManager.getSpanCount();
        }
    };

    SwipeMenuCreator mSwipeMenuCreator = (swipeLeftMenu, swipeRightMenu, viewType) -> {
        if(viewType == MultiItemTypeHelper.TYPE_BUSINESSES_GOODS) { //商品才可侧滑删除
            int width = getResources().getDimensionPixelSize(R.dimen.size_70dp);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                    .setBackground(R.drawable.selector_red)
                    .setImage(R.drawable.ic_delete_black_24dp)
                    .setText(mContext.getResources().getString(R.string.cart_item_delete))
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);
        }
    };

    SwipeMenuItemClickListener mMenuItemClickListener = menuBridge -> {
        menuBridge.closeMenu();
        int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
        if(adapter != null) adapter.notifyDeleteItemRemote(adapterPosition);
    };

    RecyclerView.OnScrollListener cartScroll =  new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //获得recyclerView的布局管理器
            GridLayoutManager gridLayoutManager = (GridLayoutManager)recyclerView.getLayoutManager();
            //获取到第一个item的显示的下标  不等于0表示第一个item处于不可见状态 说明列表没有滑动到顶部 显示回到顶部按钮
            int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
            mQuickGoTopIV.setVisibility(firstVisibleItemPosition <= 4 ? View.GONE : View.VISIBLE);

            int itemViewType = adapter.getItemViewType(firstVisibleItemPosition);
            boolean isNeedShowSticky = MultiItemTypeHelper.TYPE_BUSINESSES_GOODS == itemViewType || MultiItemTypeHelper.TYPE_BUSINESSES == itemViewType;
            pinnedHeaderItemDecoration.disableDrawHeader(!isNeedShowSticky);
        }
    };
}
