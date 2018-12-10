package google.architecture.personal.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

import google.architecture.common.base.BaseFragment;

/**
 * @author lq.zeng
 * @date 2018/6/12
 */

public class PersonalFragmentAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> mFragments;
    private List<String> mTitles;
    private ISetPrimaryItem mSetPrimaryItem;

    public PersonalFragmentAdapter(FragmentManager fm, List<BaseFragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    public void setPrimaryItemListener(ISetPrimaryItem setPrimaryItem) {
        mSetPrimaryItem = setPrimaryItem;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.size() : 0;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if(mSetPrimaryItem != null) { mSetPrimaryItem.onSetPrimaryItem(); }
    }

    public interface ISetPrimaryItem {
        void onSetPrimaryItem();
    }
}
