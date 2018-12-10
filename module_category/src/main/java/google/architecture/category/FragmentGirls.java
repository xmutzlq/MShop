package google.architecture.category;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;

import google.architecture.category.databinding.FragmentGirlsBinding;
import google.architecture.common.base.BaseFragment;
import google.architecture.common.viewmodel.GirlsViewModel;
import google.architecture.coremodel.data.GirlsData;


/**
 * @Desc FragmentGirls
 */
public class FragmentGirls extends BaseFragment<FragmentGirlsBinding> {

    GirlsAdapter girlsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_girls;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ARouter.getInstance().inject(FragmentGirls.this);
        girlsAdapter = new GirlsAdapter(girlItemClickCallback);
        binding.setRecyclerAdapter(girlsAdapter);
        final GirlsViewModel girlsViewModel = ViewModelProviders.of(getActivity()).get(GirlsViewModel.class);
//        subscribeToModel(girlsViewModel);
    }

    GirlItemClickCallback   girlItemClickCallback = new GirlItemClickCallback() {
        @Override
        public void onClick(GirlsData.ResultsBean fuliItem) {
            Toast.makeText(getContext(), fuliItem.getDesc(), Toast.LENGTH_SHORT).show();
        }
    };
    /**
     * 订阅数据变化来刷新UI
     * @param model
     */
    private void subscribeToModel(final GirlsViewModel model){
        //观察数据变化来刷新UI
        model.getLiveObservableData().observe(FragmentGirls.this, new Observer<GirlsData>() {
            @Override
            public void onChanged(@Nullable GirlsData girlsData) {
                model.setUiObservableData(girlsData);
                girlsAdapter.setGirlsList(girlsData.getResults());
            }
        });
    }

}
