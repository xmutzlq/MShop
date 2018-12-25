package google.architecture.personal.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * recycleView增加addHeaderView和addFooterView所用的Adapter
 * 这是一个装饰模式包装其他的Adapter
 */
public class HeaderFooterAdapterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    enum ITEM_TYPE{
        HEADER,
        FOOTER,
        NORMAL
    }

    private RecyclerView.Adapter mAdapter;
    private View mHeaderView;
    private View mFooterView;

    public HeaderFooterAdapterWrapper(RecyclerView.Adapter adapter){
        mAdapter = adapter;
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderView != null && position == 0){
            return ITEM_TYPE.HEADER.ordinal();
        }else if(mFooterView != null && position == mAdapter.getItemCount()+1){
            return ITEM_TYPE.FOOTER.ordinal();
        }else{
            return ITEM_TYPE.NORMAL.ordinal();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_TYPE.HEADER.ordinal()){
            return new RecyclerView.ViewHolder(mHeaderView) {};
        }else if(viewType == ITEM_TYPE.FOOTER.ordinal()){
            return  new RecyclerView.ViewHolder(mFooterView) {};
        }else{
            return mAdapter.onCreateViewHolder(parent,viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(mHeaderView != null && position == 0){
            return;
        }else if(mFooterView != null && position == mAdapter.getItemCount()+1){
            return;
        }else{
            mAdapter.onBindViewHolder(holder,mHeaderView != null  ? position -1: position);
        }
    }

    @Override
    public int getItemCount() {
        int extraCount = 0;
        if(mHeaderView != null){
            extraCount++;
        }
        if(mFooterView != null){
            extraCount++;
        }
        return mAdapter.getItemCount()+extraCount;
    }

    public void addHeaderView(View view){
        this.mHeaderView = view;
    }

    public void addFooterView(View view){
        this.mFooterView = view;
    }
}
