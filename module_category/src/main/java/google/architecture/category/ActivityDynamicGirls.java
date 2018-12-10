package google.architecture.category;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;

import google.architecture.category.databinding.ActivityGirlsBinding;
import google.architecture.common.base.BaseActivity;
import google.architecture.common.base.BaseDynamicViewModel;
import google.architecture.common.viewmodel.DynamicGirlsViewModel;
import google.architecture.coremodel.data.GirlsData;

/**
 * Created by dxx on 2017/11/20.
 */
public class ActivityDynamicGirls extends BaseActivity<ActivityGirlsBinding> {
    @Autowired(name = "fullUrl")
    public String fullUrl;
    GirlsAdapter            girlsAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_girls;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Module_ActivityDynamicGirls");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //inject需要注入后才可以读取到携带过来的参数
        ARouter.getInstance().inject(this);

        if(TextUtils.isEmpty(fullUrl)){
            return;
        }

        DynamicGirlsViewModel dynamicGirlsViewModel = new DynamicGirlsViewModel(ActivityDynamicGirls.this.getApplication(), fullUrl);

        girlsAdapter = new GirlsAdapter(girlItemClickCallback);
//        activityGirlsBinding.girlsList.setAdapter(girlsAdapter);
        binding.setRecyclerAdapter(girlsAdapter);
        subscribeToModel(dynamicGirlsViewModel);

    }

    GirlItemClickCallback   girlItemClickCallback = new GirlItemClickCallback() {
        @Override
        public void onClick(GirlsData.ResultsBean fuliItem) {
            Toast.makeText(ActivityDynamicGirls.this, fuliItem.getDesc(), Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 订阅数据变化来刷新UI
     * @param model
     */
    private void subscribeToModel(final BaseDynamicViewModel model){
        //观察数据变化来刷新UI
        model.getLiveObservableData().observe(this, new Observer<GirlsData>() {
            @Override
            public void onChanged(@Nullable GirlsData girlsData) {
                model.setUiObservableData(girlsData);
                girlsAdapter.setGirlsList(girlsData.getResults());
            }
        });
    }

}
