package google.architecture.common.widget.expand;

import android.content.Context;
import android.widget.SectionIndexer;

/**
 * @author lq.zeng
 * @date 2018/5/31
 */

public class SectionIndexerAdapterWrapper extends AdapterWrapper implements SectionIndexer {

    SectionIndexer mSectionIndexerDelegate;

    SectionIndexerAdapterWrapper(Context context,
                                 StickyListHeadersAdapter delegate) {
        super(context, delegate);
        mSectionIndexerDelegate = (SectionIndexer) delegate;
    }

    @Override
    public int getPositionForSection(int section) {
        return mSectionIndexerDelegate.getPositionForSection(section);
    }

    @Override
    public int getSectionForPosition(int position) {
        return mSectionIndexerDelegate.getSectionForPosition(position);
    }

    @Override
    public Object[] getSections() {
        return mSectionIndexerDelegate.getSections();
    }

}
