package google.architecture.common.widget.banner;

import android.view.View;
import android.view.View.OnClickListener;

public class BannerClick implements OnClickListener {
	private final OnClickListener mListener;
	int id;
	int position;

	public BannerClick(OnClickListener mListener, int id, int position) {
		this.id = id;
		this.mListener = mListener;
		this.position = position;
	}

	@Override
	public void onClick(View v) {
		v.setId(id);
		v.setTag(position);
		mListener.onClick(v);
	}

}
