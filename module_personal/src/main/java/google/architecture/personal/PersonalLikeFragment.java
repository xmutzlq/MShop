package google.architecture.personal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.king.android.res.config.ARouterPath;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseFragment;
import google.architecture.personal.adapter.PersonalFragmentLikeAdapter;

/**
 * @author lq.zeng
 * @date 2018/6/12
 */

@Route(path = ARouterPath.PersonalLikeFgt)
public class PersonalLikeFragment extends BaseFragment{

    private RecyclerView mRecyclerView;
    private PersonalFragmentLikeAdapter mAdapter;
    private List<String> mDatas;

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected boolean isStatusBarTransparent() {
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_like, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDatas = new ArrayList<>();
        for (int i = 0; i < 50; i ++) {
            mDatas.add(i + "");
        }
        mRecyclerView = (RecyclerView) findViewById(view, R.id.personal_like_recycler_view);
        mAdapter = new PersonalFragmentLikeAdapter(R.layout.item_personal_like, mDatas);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    public View getScrollView() {
        return mRecyclerView;
    }
}
