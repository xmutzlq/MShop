package google.architecture.common.imgpicker.filter;

import android.support.v4.app.FragmentActivity;

import google.architecture.common.imgpicker.filter.callback.FileLoaderCallbacks;
import google.architecture.common.imgpicker.filter.callback.FilterResultCallback;
import google.architecture.coremodel.data.ImageFile;

import static google.architecture.common.imgpicker.filter.callback.FileLoaderCallbacks.TYPE_IMAGE;

/**
 * @author lq.zeng
 * @date 2018/9/17
 */

public class FileFilter {
    public static void getImages(FragmentActivity activity, FilterResultCallback<ImageFile> callback){
        activity.getSupportLoaderManager().initLoader(0, null,
                new FileLoaderCallbacks(activity, callback, TYPE_IMAGE));
    }
}
