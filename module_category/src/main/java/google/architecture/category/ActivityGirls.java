package google.architecture.category;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import google.architecture.category.databinding.ActivityGirlsBinding;
import google.architecture.common.base.BaseActivity;
import google.architecture.common.viewmodel.GirlsViewModel;
import google.architecture.coremodel.data.GirlsData;

public class ActivityGirls extends BaseActivity<ActivityGirlsBinding> {

    GirlsAdapter girlsAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_girls;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Module_ActivityGirls");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GirlsViewModel girlsViewModel = new GirlsViewModel(ActivityGirls.this.getApplication());
        girlsAdapter = new GirlsAdapter(girlItemClickCallback);
        binding.setRecyclerAdapter(girlsAdapter);
        subscribeToModel(girlsViewModel);

    }

    GirlItemClickCallback   girlItemClickCallback = new GirlItemClickCallback() {
        @Override
        public void onClick(GirlsData.ResultsBean fuliItem) {
            Toast.makeText(ActivityGirls.this, fuliItem.getDesc(), Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 订阅数据变化来刷新UI
     * @param model
     */
    private void subscribeToModel(final GirlsViewModel model){
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
