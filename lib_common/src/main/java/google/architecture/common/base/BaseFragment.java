package google.architecture.common.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.king.android.res.util.DebouncingOnClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import google.architecture.common.R;
import google.architecture.common.statusbar.StatusbarUtils;
import google.architecture.coremodel.datamodel.http.event.CommEvent;

public abstract class BaseFragment<VB extends ViewDataBinding> extends BaseFragmentFrame {

    protected ProgressDialog progressDialog;

    protected VB binding;
    protected BaseViewModel viewModel;
    private RunCallBack runCallBack;
    private DataCallBack dataCallBack;
    private EmptyCallBack emptyCallBack;
    private LoadingFragment loadingFragment;

    protected abstract int getLayout();
    protected void responseEvent(CommEvent event){}

    protected void onDataResult(Object o){};
    /**空数据回调**/
    protected void onEmptyDisplaying() {};

    protected void onCreateBindView() {
        tryAutoCreate();
    }

    public void showProgressDialog(int message) {
       /* if(loadingDialog == null){
            loadingDialog = new LoadingDialog(getActivity());
        }
        if(!loadingDialog.isShowing()) {
            loadingDialog.show();
        }*/
        if(loadingFragment == null){
            loadingFragment = LoadingFragment.newInstance(false);

        }
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(loadingFragment, "loading");
        loadingFragment.setAlert(getString(message));
        transaction.commitAllowingStateLoss();
    }

    public void dismissProgressDialog() {
//        loadingDialog.dismiss();
        loadingFragment.dismissAllowingStateLoss();
    }

    @SuppressWarnings("unchecked")
    protected <T> T findViewById(View view, int id) {
        return (T) view.findViewById(id);
    }

    protected void replaceStatusBar(View view) {
        View statusBarView = findViewById(view, R.id.action_bar_space);
        if(statusBarView != null) {
            ViewGroup.LayoutParams layoutParams = statusBarView.getLayoutParams();
            if(layoutParams != null) {
                layoutParams.height = StatusbarUtils.getStatusBarHeight(mContext);
                statusBarView.setLayoutParams(layoutParams);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        onCreateBindView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(isStatusBarTransparent()) {
            replaceStatusBar(view);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CommEvent event) {
        responseEvent(event);
    }

    @Override
    public void onDestroy() {
        if (viewModel != null) {
            viewModel.isRunning.removeOnPropertyChangedCallback(runCallBack);
        }
        super.onDestroy();
    }

    protected void setToolbar(Toolbar toolbar) {
        if (toolbar == null) {
            return;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                getActivity().onBackPressed();
            }
        });

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void addRunStatusChangeCallBack(BaseViewModel viewModel) {
        if (viewModel == null) {
            return;
        }
        this.viewModel = viewModel;
        if (runCallBack == null) {
            runCallBack = new RunCallBack();
        }
        this.viewModel.isRunning.addOnPropertyChangedCallback(runCallBack);
        if(dataCallBack == null) {
            dataCallBack = new DataCallBack();
        }
        this.viewModel.data.addOnPropertyChangedCallback(dataCallBack);
        if(emptyCallBack == null) {
            emptyCallBack = new EmptyCallBack();
        }
        this.viewModel.isEmpty.addOnPropertyChangedCallback(emptyCallBack);
    }

    protected void runStatusChange(boolean isRunning) {
        if (isRunning) {
            showProgressDialog(R.string.wait);
        } else {
            dismissProgressDialog();
        }
    }

    private class RunCallBack extends Observable.OnPropertyChangedCallback {

        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            runStatusChange(viewModel.isRunning.get());
        }
    }

    private class DataCallBack extends Observable.OnPropertyChangedCallback {

        @Override
        public void onPropertyChanged(android.databinding.Observable sender, int propertyId) {
            onDataResult(viewModel.data.get());
        }
    }

    private class EmptyCallBack extends Observable.OnPropertyChangedCallback {

        @Override
        public void onPropertyChanged(android.databinding.Observable sender, int propertyId) {
            dismissProgressDialog();
            onEmptyDisplaying();
        }
    }
}
