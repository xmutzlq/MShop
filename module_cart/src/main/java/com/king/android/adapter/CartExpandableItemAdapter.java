package com.king.android.adapter;

import android.databinding.ObservableField;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.king.android.CartDataCache;
import com.king.android.R;
import com.king.android.dialog.DialogCartGoodsModify;
import com.oushangfeng.pinnedsectionitemdecoration.utils.FullSpanUtil;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.layout.QMUIRelativeLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.util.ToastUtils;
import google.architecture.common.viewmodel.CartViewModel;
import google.architecture.common.widget.AmountView;
import google.architecture.common.widget.span.Spans;
import google.architecture.coremodel.MultiItemTypeHelper;
import google.architecture.coremodel.data.CartBusinessesData;
import google.architecture.coremodel.data.CartGoodsData;
import google.architecture.coremodel.data.DetailRecommendInfo;
import google.architecture.coremodel.datamodel.http.event.CommEvent;

/**
 * @author lq.zeng
 * @date 2018/5/10
 */

public class CartExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public ObservableField<Boolean> allCheckState = new ObservableField<>();
    public ObservableField<CharSequence> allMoney = new ObservableField<>();
    public ObservableField<CharSequence> allCount = new ObservableField<>();
    public ObservableField<Boolean> topManageShown = new ObservableField<>();
    public ObservableField<Boolean> bottomManageShown = new ObservableField<>();

    private boolean isEmptyState; //购物车空
    private INotifyDataReload mNotifyDataReload; //通知购物车网络数据更新
    private IEmptyStateChangeListener mEmptyStateChangeListener; //空转态回调
    private ICartItemStateListener cartItemStateListener; //购物item状态(是否可选择)
    private CartResultHelper cartResultHelper; //计算帮助类

    private CartViewModel cartViewModel;

    public CartExpandableItemAdapter(List<MultiItemEntity> data, INotifyDataReload notifyDataReload) {
        super(data);
        mNotifyDataReload = notifyDataReload;
        initCartResultData();
        addItemType(MultiItemTypeHelper.TYPE_BUSINESSES, R.layout.cart_business);
        addItemType(MultiItemTypeHelper.TYPE_BUSINESSES_GOODS, R.layout.cart_goods);
        addItemType(MultiItemTypeHelper.TYPE_EMPTY, R.layout.cart_empty_view);
        addItemType(MultiItemTypeHelper.TYPE_GUESS_YOU_LIKE_TITLE, R.layout.cart_guess_you_like_title);
        addItemType(MultiItemTypeHelper.TYPE_GUESS_YOU_LIKE, R.layout.layout_comm_guess_you_like);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        FullSpanUtil.onAttachedToRecyclerView(recyclerView, this, MultiItemTypeHelper.TYPE_BUSINESSES);
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        FullSpanUtil.onViewAttachedToWindow(holder, this, MultiItemTypeHelper.TYPE_BUSINESSES);
    }

    public void setEmptyStateChangeListener(IEmptyStateChangeListener listener) {
        mEmptyStateChangeListener = listener;
    }

    public void setEmptyState(boolean isEmptyState) {
        this.isEmptyState = isEmptyState;
        bottomManageShown.set(!isEmptyState);
    }

    public void setControllSate(int state) {
        state = View.VISIBLE == state ? CartResultHelper.STATE_DELETE : CartResultHelper.STATE_NONE;
        if(cartResultHelper != null) cartResultHelper.setControllState(state);
        if(CartResultHelper.STATE_DELETE == state) { //【管理-删除】的状态下去除已选状态，让用户选择删除
            notifyAllChecked(false);
            checkAllState();
        } else {
            if(mNotifyDataReload != null) mNotifyDataReload.notifyDataReload();
        }
    }

    public boolean isEmptyState() {
        return isEmptyState;
    }

    private void initCartResultData() {
        cartResultHelper = new CartResultHelper();
        cartItemStateListener = new CartItemStateImp(cartResultHelper);
        cartViewModel = new CartViewModel();
        topManageShown.set(false);
        updateCartResultData("0.00", 0);
    }

    public void updateCartResultData(String totalPrice, int count) {
        updateMoneyResult(totalPrice);
        updateCountResult(count);
    }

    private void computeResult() {
        if(cartResultHelper != null) {
            cartResultHelper.getCountResult(getData(), count -> updateCountResult(count));
            cartResultHelper.getMoneyResult(getData(), money -> updateMoneyResult(String.valueOf(money)));
        }
    }

    private void updateCountResult(int count) {
        allCount.set("结算(" + count + ")");
    }

    private void updateMoneyResult(String money) {
        allMoney.set(Spans.builder()
                .text("合计：",15, Color.BLACK)
                .text("¥", 15, Color.RED)
                .text(money, 15, Color.RED).build());
    }

    private void notifyChildState(int parentPositon, MultiItemEntity item) {
        int start = parentPositon;
        int itemCount = ((CartBusinessesData) item).getSubItems().size();
        //需要加上parent的个数
        notifyItemRangeChanged(start, itemCount + 1, ((CartBusinessesData) item).getSubItems());
    }

    /**通过双向DataBinding改变全选按钮状态**/
    public boolean checkAllState() {
        if(cartResultHelper != null) cartResultHelper.checkAllState(getData(), cartItemStateListener, canCheckAll -> {
            allCheckState.set(canCheckAll);
        });
        return allCheckState.get();
    }

    /**
     * 全选按钮通知列表改变选择状态
     * @param isCheckedAll
     */
    public void notifyAllCheckedRemote(boolean isCheckedAll) {
        if(cartViewModel != null) {
            if(cartResultHelper != null) {
                if(cartResultHelper.checkOnNetWork()) {
                    cartResultHelper.getCartIds(getData(), new StringBuilder(), cartItemStateListener, ids -> {
                        cartViewModel.updateCart(ids,"", isCheckedAll ? "1" : "-1", t -> notifyAllChecked(isCheckedAll), (code, msg) -> {});
                    });
                } else {
                    notifyAllChecked(isCheckedAll);
                }
            }
        }
    }

    private void notifyAllChecked(boolean isCheckedAll) {
        if(cartResultHelper != null) {
            cartResultHelper.notifyAllChecked(getData(), isCheckedAll, cartItemStateListener, () -> {
                notifyItemRangeChanged(0, getData().size(), getData());
                computeResult();
            });
        }
    }

    /**
     * 删除指定购物车
     * @param position
     */
    public void notifyDeleteItemRemote(final int position) {
        if(cartViewModel != null) {
            cartViewModel.deleteCart(((CartGoodsData)getData().get(position)).cartId, (code, msg) -> {
                if(code == 0) {
                    notifyDeleteItem(position);
                }
            });
        }
    }

    private void notifyDeleteItem(final int position) {
        remove(position);
    }

    public void notifyDeleteItemsRemote() {
        if(cartViewModel != null) {
            if(cartResultHelper != null) {
                if(cartResultHelper.checkOnNetWork()) {
                    cartResultHelper.getCartIds(getData(), new StringBuilder(), cartItemStateListener, ids -> {
                        cartViewModel.deleteCart(ids, (code, msg) -> {
                            if(code == 0) {
                                notifyDeleteItems();
                            }
                        });
                    });
                } else {
                    notifyDeleteItems();
                }
            }
        }
    }

    /**删除操作应先从子节点开始删除，所以是倒序的，否则会出现错乱**/
    private void notifyDeleteItems() {
        boolean haveChecked = false;
        for (MultiItemEntity entity : getData()) {
            if(entity != null) {
                if(entity instanceof CartBusinessesData && ((CartBusinessesData) entity).isCehcked) { //检查父节点
                    haveChecked = true;
                    break;
                }
                if(entity instanceof CartGoodsData && ((CartGoodsData) entity).isChecked) {
                    haveChecked = true;
                    break;
                }
            }
        }
        if(!haveChecked) {
            ToastUtils.showShortToast(R.string.cart_unselect_value);
            return;
        }
        for (int i = getData().size() - 1; i >= 0; i--) {
            MultiItemEntity entity = getData().get(i);
            if(entity != null) {
                if(entity instanceof CartGoodsData && ((CartGoodsData) entity).isChecked) {
                    remove(i);
                }
                if(entity instanceof CartBusinessesData && ((CartBusinessesData) entity).isCehcked) {
                    ((CartBusinessesData) entity).isDeleted = true;
                    remove(i);
                }
            }
        }
        boolean isAllRemoveState = checkAllState();
        computeResult();
        //如果是所有状态，那么必然是清空数据
        if(isAllRemoveState) {
            topManageShown.set(false);
            bottomManageShown.set(false);
            isEmptyState = true;
            if(mEmptyStateChangeListener != null) mEmptyStateChangeListener.onEmptyStateChangeListener(isEmptyState);
        } else {
            if(mEmptyStateChangeListener != null) mEmptyStateChangeListener.onDeleteStateChangeListener();
        }
        notifyDataSetChanged();
    }

    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case MultiItemTypeHelper.TYPE_BUSINESSES: //商家
                final CartBusinessesData lv0 = (CartBusinessesData) item;
                holder.itemView.setTag(lv0.isDeleted ? "" : "sticky-nonconstant");
                holder.setText(R.id.tv_cart_business_title, lv0.businessesName);
                //选择
                holder.getView(R.id.btn_cart_business_title).setOnClickListener(v -> {
                    //商品缺货
                    if(cartItemStateListener.enableShopChecked(lv0.isEnable)) {
                        lv0.isCehcked = !lv0.isCehcked;
                        if(cartResultHelper.checkOnNetWork()) {
                            cartResultHelper.getCartIds(((CartBusinessesData) item).getSubItems(), new StringBuilder(), cartItemStateListener, ids -> {
                                if (!TextUtils.isEmpty(ids)) {
                                    cartViewModel.updateCart(ids, "", lv0.isCehcked ? "1" : "-1", t -> {
                                        ((CartBusinessesData) item).isCehcked = lv0.isCehcked;
                                        for (CartGoodsData data : ((CartBusinessesData) item).getSubItems()) {
                                            data.isChecked = lv0.isCehcked && cartItemStateListener.enableGoodsChecked(data.goodsState);
                                        }
                                        notifyChildState(holder.getAdapterPosition(), item);
                                        checkAllState();
                                        computeResult();
                                    }, (code, msg) -> lv0.isCehcked = !lv0.isCehcked);
                                } else {
                                    lv0.isCehcked = !lv0.isCehcked;
                                }
                            });
                        } else {
                            ((CartBusinessesData) item).isCehcked = lv0.isCehcked;
                            for (CartGoodsData data : ((CartBusinessesData) item).getSubItems()) {
                                data.isChecked = lv0.isCehcked && cartItemStateListener.enableGoodsChecked(data.goodsState);
                            }
                            notifyChildState(holder.getAdapterPosition(), item);
                            checkAllState();
                            computeResult();
                        }
                    }
                });

                //领券
                holder.getView(R.id.tv_cart_business_title_edit).setOnClickListener(v->{
                    ToastUtils.showShortToast("领券, item = " + holder.getAdapterPosition());
                });

                if(lv0.isCehcked && cartItemStateListener.enableShopChecked(lv0.isEnable)) {
                    ((CartBusinessesData) item).isCehcked = true;
                    holder.setChecked(R.id.cb_cart_business_title, true);
                } else {
                    ((CartBusinessesData) item).isCehcked = false;
                    holder.setChecked(R.id.cb_cart_business_title, false);
                }

                break;
            case MultiItemTypeHelper.TYPE_BUSINESSES_GOODS: //货品
                final CartGoodsData lv1 = (CartGoodsData) item;

                QMUIRelativeLayout qmuiRelativeLayout = holder.getView(R.id.shadow_layout);
                qmuiRelativeLayout.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, 6),
                        QMUIDisplayHelper.dp2px(mContext, 4), 0.15f);

                ImageView iv = holder.getView(R.id.iv_cart_goods_title);
                ImageLoader.get().load(iv, lv1.goodsPic, new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        //商品失效图片
                        iv.setImageDrawable(resource);
                        return false;
                    }
                });
                holder.setText(R.id.tv_cart_goods_title, lv1.goodsName);
                holder.setText(R.id.tv_cart_goods_price, lv1.goodsDes);
                holder.setText(R.id.tv_cart_goods_price_value, Spans.builder()
                                .text("￥",12, Color.RED)
                                .text(lv1.goodsPrice, 15, Color.RED).build());
                AmountView amountView = holder.getView(R.id.cart_amount_view);
                amountView.setAmount(lv1.count);
                amountView.setGoods_storage(lv1.goodsBuyLimit == 0 ? Integer.MAX_VALUE : lv1.goodsBuyLimit);
                amountView.setAmountChangeListener((state, count) -> {
                    cartViewModel.updateCart(lv1.cartId, String.valueOf(count), lv1.isChecked ? "1" : "-1", t -> {
                        lv1.count = count;
                        if(lv1.isChecked) computeResult();
                    }, (code, msg) -> {
                        if(code == 1) { //更新失败
                            if(AmountView.STATE_MINUS.equals(state)) {
                                amountView.setAmount(count + 1);
                            } else if(AmountView.STATE_PLUS.equals(state)) {
                                amountView.setAmount(count - 1);
                            }
                        }
                    });
                });
                amountView.setClickListener(v -> {
                    DialogCartGoodsModify.show(mContext, "修改购买数量", (dialog, inputText) ->{
                        final int buyCount = TextUtils.isEmpty(inputText) ? 1 : Integer.valueOf(inputText);
                        cartViewModel.updateCart(lv1.cartId, String.valueOf(buyCount), lv1.isChecked ? "1" : "-1", t -> {
                            lv1.count = buyCount;
                            amountView.setAmount(lv1.count);
                            if(lv1.isChecked)  computeResult();
                        }, null);
                    }).setDefaultInputText(lv1.count + "");
                });
                //选择
                holder.getView(R.id.btn_cart_goods_title).setOnClickListener(v -> {
                    //商品缺货
                    if(cartItemStateListener.enableGoodsChecked(lv1.goodsState)) {
                        lv1.isChecked = !lv1.isChecked;
                        if(cartResultHelper.checkOnNetWork()) {
                            cartViewModel.updateCart(lv1.cartId, String.valueOf(lv1.count), lv1.isChecked ? "1" : "-1", t -> {
                                int parentPosition = getParentPosition(item);
                                boolean isParentNeedChecked = true;
                                CartBusinessesData cartBusinessesData = (CartBusinessesData) getData().get(parentPosition);
                                for (CartGoodsData data : cartBusinessesData.getSubItems()) {
                                    if (!data.isChecked && cartItemStateListener.enableGoodsChecked(data.goodsState)) isParentNeedChecked = false;
                                }
                                boolean isParentChecked = cartBusinessesData.isCehcked;
                                cartBusinessesData.isCehcked = isParentNeedChecked;
                                if (isParentChecked && !isParentNeedChecked) { //父级已经是选中状态并且子集无需刷新父级时(刷新)
                                    notifyChildState(parentPosition, cartBusinessesData);
                                } else { //刷新
                                    if (isParentNeedChecked) {
                                        notifyChildState(parentPosition, cartBusinessesData);
                                    } else {
                                        notifyItemChanged(holder.getAdapterPosition(), lv1);
                                    }
                                }
                                checkAllState();
                                computeResult();
                            }, (code, msg) -> lv1.isChecked = !lv1.isChecked);
                        } else {
                            int parentPosition = getParentPosition(item);
                            boolean isParentNeedChecked = true;
                            CartBusinessesData cartBusinessesData = (CartBusinessesData) getData().get(parentPosition);
                            for (CartGoodsData data : cartBusinessesData.getSubItems()) {
                                if (!data.isChecked && cartItemStateListener.enableGoodsChecked(data.goodsState)) isParentNeedChecked = false;
                            }
                            boolean isParentChecked = cartBusinessesData.isCehcked;
                            cartBusinessesData.isCehcked = isParentNeedChecked;
                            if (isParentChecked && !isParentNeedChecked) { //父级已经是选中状态并且子集无需刷新父级时(刷新)
                                notifyChildState(parentPosition, cartBusinessesData);
                            } else { //刷新
                                if (isParentNeedChecked) {
                                    notifyChildState(parentPosition, cartBusinessesData);
                                } else {
                                    notifyItemChanged(holder.getAdapterPosition(), lv1);
                                }
                            }
                            checkAllState();
                            computeResult();
                        }
                    }
                });

                //删除孩子节点
                holder.getView(R.id.tv_cart_goods_delete).setOnClickListener(v -> {
                    cartViewModel.deleteCart(((CartGoodsData) item).cartId, (code, msg) -> {
                        int parentPosition = getParentPosition(item);
                        int pos = holder.getAdapterPosition();
                        remove(pos); //删除孩子节点
                        CartBusinessesData cartBusinessesData = (CartBusinessesData)getData().get(parentPosition);
                        cartBusinessesData.getSubItems().remove(item);
                        int childSize = cartBusinessesData.getSubItems().size();
                        if (childSize == 0) { //无孩子节点，删除父节点
                            remove(parentPosition);
                        }
                        checkAllState();
                    });
                });
                if(lv1.isChecked && cartItemStateListener.enableGoodsChecked(lv1.goodsState)) {
                    ((CartGoodsData) item).isChecked = true;
                    holder.setChecked(R.id.cb_cart_goods_title, true);
                } else {
                    ((CartGoodsData) item).isChecked = false;
                    holder.setChecked(R.id.cb_cart_goods_title, false);
                }

                if(lv1.isDeleteState) {
                    holder.setGone(R.id.tv_cart_goods_delete, true);
                } else {
                    holder.setGone(R.id.tv_cart_goods_delete, false);
                }

                break;
            case MultiItemTypeHelper.TYPE_GUESS_YOU_LIKE:
                final DetailRecommendInfo lv2 = (DetailRecommendInfo) item;

                QMUILinearLayout qmuiLinearLayout = holder.getView(R.id.ll_new_seed_item);
                qmuiLinearLayout.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, 6),
                        QMUIDisplayHelper.dp2px(mContext, 0), 0);

                ImageLoader.get().load(holder.getView(R.id.iv_you_like), lv2.rec_url);
                holder.setText(R.id.tv_you_like, lv2.rec_title);
                holder.setText(R.id.tv_you_like_money, lv2.rec_price);
                break;
            case MultiItemTypeHelper.TYPE_GUESS_YOU_LIKE_TITLE:
                final CartDataCache.GuessTitleEntity lv3 = (CartDataCache.GuessTitleEntity) item;
                holder.setText(R.id.cart_guess_you_like_title_tv, lv3.title);

                break;
            case MultiItemTypeHelper.TYPE_EMPTY:
                if(isEmptyState){
                    holder.setGone(R.id.cart_empty_layout, true);
                } else {
                    holder.setGone(R.id.cart_empty_layout, false);
                }
                holder.getView(R.id.btn_cart_empty_go).setOnClickListener(v -> {
                    LogUtils.tag("zlq").e("MSG_TYPE_HOME_GO");
                    Bundle bundle = new Bundle();
                    bundle.putInt(CommKeyUtil.EXTRA_KEY, 0);
                    EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_HOME_GO, bundle));
                });
                break;
        }
    }

    public interface IEmptyStateChangeListener {
        void onDeleteStateChangeListener();
        void onEmptyStateChangeListener(boolean isEmpty);
    }

    public interface INotifyDataReload {
        void notifyDataReload();
    }
}
