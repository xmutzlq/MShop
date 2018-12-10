/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package google.architecture.common.widget.banner.recycle;

import android.content.Context;

import java.util.ArrayList;

import google.architecture.common.imgloader.ImageLoader;
import google.architecture.coremodel.data.HomeItemsInfo;

/**
 * AdvertImagePagerAdapter
 */
public class AdvertImagePagerAdapter extends CommRecyclingPagerAdapter<HomeItemsInfo> {

	public AdvertImagePagerAdapter(Context context, ArrayList<HomeItemsInfo> imageIdList) {
		super(context, imageIdList);
	}

	@Override
	protected void loadImage(CommRecyclingPagerAdapter.ViewHolder holder, HomeItemsInfo imgId, int position) {
		ImageLoader.get().load(holder.imageView, imgId.getImgUrl());
	}
}
