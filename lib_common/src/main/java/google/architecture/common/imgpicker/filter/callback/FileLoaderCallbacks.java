package google.architecture.common.imgpicker.filter.callback;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import google.architecture.common.imgpicker.Util;
import google.architecture.common.imgpicker.filter.entity.Directory;
import google.architecture.common.imgpicker.filter.loader.ImageLoader;
import google.architecture.coremodel.data.ImageFile;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME;
import static android.provider.MediaStore.Images.ImageColumns.BUCKET_ID;
import static android.provider.MediaStore.Images.ImageColumns.ORIENTATION;
import static android.provider.MediaStore.MediaColumns.DATA;
import static android.provider.MediaStore.MediaColumns.DATE_ADDED;
import static android.provider.MediaStore.MediaColumns.SIZE;
import static android.provider.MediaStore.MediaColumns.TITLE;

/**
 * @author lq.zeng
 * @date 2018/9/17
 */

public class FileLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final int TYPE_IMAGE = 0;
    public static final int TYPE_VIDEO = 1;
    public static final int TYPE_AUDIO = 2;
    public static final int TYPE_FILE = 3;

    private WeakReference<Context> context;
    private FilterResultCallback resultCallback;

    private int mType = TYPE_IMAGE;
    private String[] mSuffixArgs;
    private CursorLoader mLoader;
    private String mSuffixRegex;

    public FileLoaderCallbacks(Context context, FilterResultCallback resultCallback, int type) {
        this(context, resultCallback, type, null);
    }

    public FileLoaderCallbacks(Context context, FilterResultCallback resultCallback, int type, String[] suffixArgs) {
        this.context = new WeakReference<>(context);
        this.resultCallback = resultCallback;
        this.mType = type;
        this.mSuffixArgs = suffixArgs;
        if (suffixArgs != null && suffixArgs.length > 0) {
            mSuffixRegex = obtainSuffixRegex(suffixArgs);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (mType) {
            case TYPE_IMAGE:
                mLoader = new ImageLoader(context.get());
                break;
            case TYPE_VIDEO:
                break;
            case TYPE_AUDIO:
                break;
            case TYPE_FILE:
                break;
        }

        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null) return;
        switch (mType) {
            case TYPE_IMAGE:
                onImageResult(data);
                break;
            case TYPE_VIDEO:
                break;
            case TYPE_AUDIO:
                break;
            case TYPE_FILE:
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @SuppressWarnings("unchecked")
    private void onImageResult(Cursor data) {
        List<Directory<ImageFile>> directories = new ArrayList<>();

        if (data.getPosition() != -1) {
            data.moveToPosition(-1);
        }

        while (data.moveToNext()) {
            //Create a File instance
            ImageFile img = new ImageFile();
            img.setId(data.getLong(data.getColumnIndexOrThrow(_ID)));
            img.setName(data.getString(data.getColumnIndexOrThrow(TITLE)));
            img.setPath(data.getString(data.getColumnIndexOrThrow(DATA)));
            img.setSize(data.getLong(data.getColumnIndexOrThrow(SIZE)));
            img.setBucketId(data.getString(data.getColumnIndexOrThrow(BUCKET_ID)));
            img.setBucketName(data.getString(data.getColumnIndexOrThrow(BUCKET_DISPLAY_NAME)));
            img.setDate(data.getLong(data.getColumnIndexOrThrow(DATE_ADDED)));

            img.setOrientation(data.getInt(data.getColumnIndexOrThrow(ORIENTATION)));

            //Create a Directory
            Directory<ImageFile> directory = new Directory<>();
            directory.setId(img.getBucketId());
            directory.setName(img.getBucketName());
            directory.setPath(Util.extractPathWithoutSeparator(img.getPath()));

            if (!directories.contains(directory)) {
                directory.addFile(img);
                directories.add(directory);
            } else {
                directories.get(directories.indexOf(directory)).addFile(img);
            }
        }

        if (resultCallback != null) {
            resultCallback.onResult(directories);
        }
    }

    private String obtainSuffixRegex(String[] suffixes) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < suffixes.length ; i++) {
            if (i ==0) {
                builder.append(suffixes[i].replace(".", ""));
            } else {
                builder.append("|\\.");
                builder.append(suffixes[i].replace(".", ""));
            }
        }
        return ".+(\\." + builder.toString() + ")$";
    }
}
