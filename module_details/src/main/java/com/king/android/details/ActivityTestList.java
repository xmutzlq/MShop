package com.king.android.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.king.android.details.adapter.DetailListTestAdapter;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseActivity;
import google.architecture.coremodel.datamodel.http.event.CommEvent;

/**
 * @author lq.zeng
 * @date 2018/7/9
 */

public class ActivityTestList extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.layout_comm_recyclerview;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);

        List<String> imgs = new ArrayList<>();
        imgs.add("https://img14.360buyimg.com/n1/s546x546_jfs/t17083/327/2335382799/285989/d34a93fa/5af50e5fNa34f717e.jpg");
        imgs.add("https://img14.360buyimg.com/n1/s546x546_jfs/t17083/327/2335382799/285989/d34a93fa/5af50e5fNa34f717e.jpg");
        imgs.add("https://img14.360buyimg.com/n1/s546x546_jfs/t17083/327/2335382799/285989/d34a93fa/5af50e5fNa34f717e.jpg");
        imgs.add("https://img14.360buyimg.com/n1/s546x546_jfs/t17083/327/2335382799/285989/d34a93fa/5af50e5fNa34f717e.jpg");
        imgs.add("https://img14.360buyimg.com/n1/s546x546_jfs/t17083/327/2335382799/285989/d34a93fa/5af50e5fNa34f717e.jpg");
        imgs.add("https://img14.360buyimg.com/n1/s546x546_jfs/t17083/327/2335382799/285989/d34a93fa/5af50e5fNa34f717e.jpg");
        imgs.add("https://img14.360buyimg.com/n1/s546x546_jfs/t17083/327/2335382799/285989/d34a93fa/5af50e5fNa34f717e.jpg");
        imgs.add("https://img14.360buyimg.com/n1/s546x546_jfs/t17083/327/2335382799/285989/d34a93fa/5af50e5fNa34f717e.jpg");
        imgs.add("https://img14.360buyimg.com/n1/s546x546_jfs/t17083/327/2335382799/285989/d34a93fa/5af50e5fNa34f717e.jpg");
        DetailListTestAdapter adapter = new DetailListTestAdapter(R.layout.test_list_img, imgs);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, ViewCompat.getTransitionName(view));
            Intent intent = new Intent(this, ActivityDetails.class);
            intent.putExtra(CommEvent.KEY_EXTRA_VALUE, "53963");
            ActivityCompat.startActivity(this, intent, options.toBundle());
        });
        RecyclerView recyclerView = findViewById(R.id.comm_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
