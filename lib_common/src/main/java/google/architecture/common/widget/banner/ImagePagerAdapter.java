/*
 * Copyright 2014 trinea.cn All right reserved. This software is the
 * confidential and proprietary information of trinea.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with trinea.cn.
 */
package google.architecture.common.widget.banner;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import java.util.List;

import google.architecture.common.imgloader.ImageLoader;

/**
 * 
 * @author YFY
 * 
 * @describe 生成一个ImageView显示在ViewPager上
 */
public class ImagePagerAdapter extends PagerAdapter {
	public final static int BannerClick = 0x10010;
	private Fragment context;
	private List<String> imageUrlList;
	private boolean isClick = false;

	/**
	 * 
	 * @param context
	 *            传入的context
	 * @param imageUrlList
	 *            传入的图片List
	 * @param isClick
	 *            每个图片是否可以点击
	 */
	public ImagePagerAdapter(Fragment context, List<String> imageUrlList, boolean isClick) {
		this.context = context;
		this.imageUrlList = imageUrlList;
		this.isClick = isClick;
	}

	@Override
	public int getCount() {
		return imageUrlList == null ? 0 : imageUrlList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return (view == object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView = new ImageView(context.getActivity());
		imageView.setScaleType(ScaleType.FIT_XY);
		ImageLoader.get().load(imageView, imageUrlList.get(position));
		if (isClick) {
			// 点击回调，将点击图片的位置范围给context
			imageView.setOnClickListener(new BannerClick((View.OnClickListener) context, BannerClick, position));
		}
		((ViewPager) container).addView(imageView, 0);
		return imageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((ImageView) object);
	}
}
