package google.architecture.home.substance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.vcontent.BaseDelegateAdapter;
import google.architecture.common.vcontent.ContantDelegate;
import google.architecture.coremodel.data.xlj.shopdata.ShopData;

public class HomePageNew implements ContantDelegate, PageAdapter.IActionTitleRightLabelClick {

    private Context mContext;
    private PageAdapterNew mPageAdapter;
    private DelegateAdapter delegateAdapter;
    private ShopData mData;

    public HomePageNew(Context activity, ShopData data){
        mContext = activity;
        mPageAdapter = new PageAdapterNew(mContext);
        mData = data;
    }

    @Override
    public DelegateAdapter initRecyclerView(RecyclerView recyclerView) {
        //初始化
        //创建VirtualLayoutManager对象
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        //设置适配器
        delegateAdapter = new DelegateAdapter(layoutManager, false);
        recyclerView.setAdapter(delegateAdapter);
        return delegateAdapter;
    }

    @Override
    public List<BaseDelegateAdapter> initItemDelegates() {
        List<BaseDelegateAdapter> itemDelegates = new ArrayList<>();
        itemDelegates.add(mPageAdapter.initBanner(mData.getSlideImg()));
        return itemDelegates;
    }

    @Override
    public void onActionTitleRightLabelClick(View v, int type) {

    }
}
