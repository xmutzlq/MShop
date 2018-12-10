package google.architecture.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apkfuns.logutils.LogUtils;

import solid.ren.skinlibrary.base.SkinBaseFragment;

/**
 * 打印Fragment生命周期
 * @author lq.zeng
 * @date 2018/4/28
 */

public class BaseLifeCircleFragment extends SkinBaseFragment {

    public static final String TAG =  "BaseLifeCircleFragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.i(TAG,getClass().getSimpleName() + "  onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.i(TAG,getClass().getSimpleName() + "  setUserVisibleHint " + isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtils.i(TAG,getClass().getSimpleName() + "  onHiddenChanged " + hidden);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.i(TAG,getClass().getSimpleName() + "  onActivityCreated ");
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i(TAG,getClass().getSimpleName() + " onResume  ");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.i(TAG,getClass().getSimpleName() + "  onPause ");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.i(TAG,getClass().getSimpleName() + "  onStop ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.i(TAG,getClass().getSimpleName() + "  onDestroyView ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG,getClass().getSimpleName() + "  onDestroy ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.i(TAG,getClass().getSimpleName() + "  onDetach ");
    }
}
