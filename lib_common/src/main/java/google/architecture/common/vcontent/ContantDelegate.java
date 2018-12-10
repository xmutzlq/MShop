package google.architecture.common.vcontent;

import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;

import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/5/2
 */

public interface ContantDelegate {
    DelegateAdapter initRecyclerView(RecyclerView recyclerView);
    List<BaseDelegateAdapter> initItemDelegates();
}
