package google.architecture.common.widget.adapter;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import google.architecture.common.widget.CommFlowLayout;


/**
 * @author lq.zeng
 * @date 2018/5/25
 */

public abstract class TagAdapter<T> {
    private List<T> mTagDatas;
    private OnDataChangedListener mOnDataChangedListener;
    @Deprecated
    private HashSet<Integer> mCheckedPosList = new HashSet<Integer>();

    public TagAdapter(List<T> datas) {
        mTagDatas = datas;
    }

    public TagAdapter(T[] datas) {
        mTagDatas = new ArrayList<T>(Arrays.asList(datas));
    }

    public interface OnDataChangedListener {
        void onChanged();
    }

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    public void setSelectedList(int... poses) {
        Set<Integer> set = new HashSet<>();
        for (int pos : poses) {
            set.add(pos);
        }
        setSelectedList(set);
    }

    public void setSelectedList(Set<Integer> set) {
        mCheckedPosList.clear();
        if (set != null && set.size() > 0) {
            mCheckedPosList.addAll(set);
        }
        notifyDataChanged();
    }

    @Deprecated
    public HashSet<Integer> getPreCheckedList() {
        return mCheckedPosList;
    }

    public List<T> getData() { return mTagDatas; }

    public int getCount() {
        return mTagDatas == null ? 0 : mTagDatas.size();
    }

    public boolean isEnabled(int position) {
        return true;
    }

    public void notifyDataChanged() {
        if (mOnDataChangedListener != null)
            mOnDataChangedListener.onChanged();
    }

    public T getItem(int position) {
        return mTagDatas.get(position);
    }

    public abstract View getView(CommFlowLayout parent, int position, T t);


    public void onSelected(int position, View view){

    }

    public void unSelected(int position, View view){

    }

    public boolean setSelected(int position, T t) {
        return false;
    }
}
