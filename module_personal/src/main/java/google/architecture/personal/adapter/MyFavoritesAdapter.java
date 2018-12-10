package google.architecture.personal.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseViewHolder;
import com.crazysunj.multitypeadapter.adapter.LoadingEntityAdapter;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.params.LoadingEntity;
import google.architecture.common.params.SimpleEmptyEntity;
import google.architecture.common.params.SimpleErrorEntity;
import google.architecture.common.util.DimensionsUtil;
import google.architecture.common.widget.adapter.BaseAdapter;
import google.architecture.coremodel.data.FavoriteData;
import google.architecture.coremodel.data.MultiHeaderEntity;
import google.architecture.personal.R;
import google.architecture.personal.adapter.helper.MyFavoritesAdapterHelper;
import google.architecture.personal.adapter.helper.MyFavoritesItem;
import io.reactivex.Observable;

/**
 * @author lq.zeng
 * @date 2018/10/24
 */

public class MyFavoritesAdapter extends BaseAdapter<MultiHeaderEntity, BaseViewHolder, MyFavoritesAdapterHelper> {

    private boolean isShowCheck;
    private boolean isAllSelected;

    private MyFavoritesAdapter.IASelectListener aSelectListener;

    public MyFavoritesAdapter(MyFavoritesAdapterHelper helper) {
        super(helper);
        helper.setEmptyAdapter((type, level) -> new SimpleEmptyEntity(type));
        helper.setErrorAdapter((type, level) -> new SimpleErrorEntity(type));
        helper.setLoadingAdapter(new LoadingEntityAdapter<MultiHeaderEntity>() {
            @Override
            public MultiHeaderEntity createLoadingEntity(int type, int level) {
                return new LoadingEntity(type);
            }

            @Override
            public MultiHeaderEntity createLoadingHeaderEntity(int type, int level) {
                return new LoadingEntity(type);
            }

            @Override
            public void bindLoadingEntity(MultiHeaderEntity loadingEntity, int position) {

            }
        });
    }

    @Override
    protected void convert(BaseViewHolder holder, MultiHeaderEntity item) {
        if(holder.getItemViewType() == FavoriteData.FavoriteItem.TYPE_USER_MY_FAVORITE) {
            if(item instanceof MyFavoritesItem) {
                MyFavoritesItem myFavoritesItem = (MyFavoritesItem) item;
                renderFootprint(holder, myFavoritesItem);
            }
        }
    }

    private void renderFootprint(BaseViewHolder holder, MyFavoritesItem myFavoritesItem) {
        ImageView iv = holder.getView(R.id.personal_footprint_goods_iv);
        ImageLoader.get().load(iv, myFavoritesItem.getImageUrl(), new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                refreshText(holder, myFavoritesItem);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                iv.setImageDrawable(resource);
                refreshText(holder, myFavoritesItem);
                return false;
            }
        });
        refreshText(holder, myFavoritesItem);
    }

    private boolean isAllSelected() {
        for (MultiHeaderEntity multiHeaderEntity : getData()) {
            if(multiHeaderEntity instanceof MyFavoritesItem) {
                MyFavoritesItem MyFavoritesItem = (MyFavoritesItem)multiHeaderEntity;
                if(!MyFavoritesItem.isChecked()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void refreshText(BaseViewHolder holder, MyFavoritesItem myFavoritesItem) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.getView(R.id.personal_footprint_goods_rf).getLayoutParams();
        layoutParams.width = DimensionsUtil.dip2px(mContext, 80);
        layoutParams.height = layoutParams.width;
        holder.getView(R.id.personal_footprint_goods_rf).setLayoutParams(layoutParams);

        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) holder.getView(R.id.cb_goods).getLayoutParams();
        layoutParams2.width = DimensionsUtil.dip2px(mContext, 20);
        layoutParams2.height = layoutParams2.width;
        holder.getView(R.id.cb_goods).setLayoutParams(layoutParams2);

        TextView goodTitle = holder.getView(R.id.personal_footprint_goods_name);
        goodTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        goodTitle.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
        holder.setText(R.id.personal_footprint_goods_name, myFavoritesItem.getTitle());

        TextView goodPrice = holder.getView(R.id.personal_footprint_goods_price);
        goodPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        goodPrice.setTextColor(ContextCompat.getColor(mContext, R.color.color_f44336));
        holder.setText(R.id.personal_footprint_goods_price, myFavoritesItem.getPrice());

        if(!isShowCheck) {
            myFavoritesItem.setChecked(false);
            holder.setGone(R.id.btn_cb_goods, false);
            holder.setChecked(R.id.cb_goods, false);
        } else {
            boolean checked = isAllSelected ? true : myFavoritesItem.isChecked();
            myFavoritesItem.setChecked(checked);
            holder.setGone(R.id.btn_cb_goods, true);
            holder.setChecked(R.id.cb_goods, checked);
        }

        holder.getView(R.id.btn_cb_goods).setOnClickListener(v -> { //单选
            myFavoritesItem.setChecked(!myFavoritesItem.isChecked());
            isAllSelected = isAllSelected();
            notifyItemChanged(holder.getAdapterPosition(), myFavoritesItem);
            if(aSelectListener != null) aSelectListener.onASelectListener(isAllSelected);
        });

        QMUILinearLayout qmuiLinearLayout = holder.getView(R.id.shadow_layout);
        qmuiLinearLayout.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, 8),
                QMUIDisplayHelper.dp2px(mContext, 3), 0.14f);
    }

    public void notifyRefresh(List<MultiHeaderEntity> MyFavoritesItems) {
        final int level = MyFavoritesAdapterHelper.LEVEL_FOOT_PRINT;
        mHelper.notifyModuleDataChanged(MyFavoritesItems, level);
    }

    public void notifyUpdate(List<MultiHeaderEntity> MyFavoritesItems) {
        if(MyFavoritesItems == null || MyFavoritesItems.size() == 0) return;
        final int level = MyFavoritesAdapterHelper.LEVEL_FOOT_PRINT;
        List<MultiHeaderEntity> multiHeaderEntities = new ArrayList<>(getData());
        multiHeaderEntities.addAll(MyFavoritesItems);
        mHelper.notifyModuleDataChanged(multiHeaderEntities, level);
    }

    public void onEmptyStateShow() {
        mHelper.notifyModuleEmptyChanged(MyFavoritesAdapterHelper.LEVEL_FOOT_PRINT);
    }

    public void onLoadingShow() {
        mHelper.notifyLoadingDataChanged(MyFavoritesAdapterHelper.LEVEL_FOOT_PRINT, 10);
    }

    /**显示|隐藏选择**/
    public void showItemCheckSate(boolean isShown) {
        isShowCheck = isShown;
        notifyItemRangeChanged(0, getData().size(), getData());
    }

    /**全选|反选**/
    public void selectedAll(boolean isSelectedAll) {
        isAllSelected = isSelectedAll;
        for (MultiHeaderEntity multiHeaderEntity : getData()) {
            if(multiHeaderEntity instanceof MyFavoritesItem) {
                ((MyFavoritesItem) multiHeaderEntity).setChecked(isSelectedAll);
            }
        }
        notifyItemRangeChanged(0, getData().size(), getData());
    }

    public int getSelectedCount() {
        if(isAllSelected) return getData().size();
        int count = 0;
        for (MultiHeaderEntity multiHeaderEntity : getData()) {
            if(multiHeaderEntity instanceof MyFavoritesItem) {
                MyFavoritesItem MyFavoritesItem = ((MyFavoritesItem) multiHeaderEntity);
                if(MyFavoritesItem != null && MyFavoritesItem.isChecked()) {
                    count++;
                }
            }
        }
        return count;
    }

    public String getSelectedIds() {
        StringBuilder stringBuilder = new StringBuilder();
        for (MultiHeaderEntity multiHeaderEntity : getData()) {
            if(multiHeaderEntity instanceof MyFavoritesItem) {
                MyFavoritesItem MyFavoritesItem = ((MyFavoritesItem) multiHeaderEntity);
                if(MyFavoritesItem != null && MyFavoritesItem.isChecked()) {
                    stringBuilder.append(MyFavoritesItem.getId()).append(",");
                }
            }
        }
        String ids = stringBuilder.toString();
        int start = ids.length() - 1;
        int end = ids.length();
        if(ids.length() > 0 && ids.substring(start, end).equals(",")) ids = ids.substring(0, start);
        return ids;
    }

    public List<MultiHeaderEntity> getUnSelectedList() {
        List<MultiHeaderEntity> multiHeaderEntities = new ArrayList<>();
        Observable.fromIterable(getData())
                .filter(multiHeaderEntity -> !((MyFavoritesItem)multiHeaderEntity).isChecked())
                .subscribe(multiHeaderEntity -> multiHeaderEntities.add(multiHeaderEntity));
        return multiHeaderEntities;
    }

    public void setASelectListener(MyFavoritesAdapter.IASelectListener aSelectListener) {
        this.aSelectListener = aSelectListener;
    }

    private int isLoadMoreShown;

    public boolean isLoadMoreShown() {
        return isLoadMoreShown == 1;
    }

    public void setLoadMoreShown(boolean isLoadMoreShown) {
        this.isLoadMoreShown = isLoadMoreShown ? 1 : 0;
    }

    @Override
    public int getLoadMoreViewCount() {
        return isLoadMoreShown;
    }

    public interface IASelectListener {
        void onASelectListener(boolean isSelected);
    }
}
