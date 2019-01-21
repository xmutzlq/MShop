package google.architecture.personal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import google.architecture.common.base.BaseFragment;

public class FullBodyScanFragmentAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> mFragments;

    public FullBodyScanFragmentAdapter(FragmentManager fm, List<BaseFragment>mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
