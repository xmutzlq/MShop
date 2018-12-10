package google.architecture.common.widget.hmore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import google.architecture.common.R;

/**
 * @author lq.zeng
 * @date 2018/6/27
 */

public class LoadingRefreshHeader implements RefreshHeader {
    private final Context context;
    private ProgressBar progressBar;
    private ImageView staticLoading;

    public LoadingRefreshHeader(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(ViewGroup container) {
        View view = LayoutInflater.from(context).inflate(R.layout.common_loading_refresh_header, container, false);
        progressBar = view.findViewById(R.id.progressbar);
        staticLoading = view.findViewById(R.id.static_loading);
        progressBar.setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public void onStart(int dragPosition, View refreshHead) {
        staticLoading.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDragging(float distance, float percent, View refreshHead) {
        staticLoading.setRotation(percent * 360);
    }

    @Override
    public void onReadyToRelease(View refreshHead) {

    }

    @Override
    public void onRefreshing(View refreshHead) {
        staticLoading.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }
}
