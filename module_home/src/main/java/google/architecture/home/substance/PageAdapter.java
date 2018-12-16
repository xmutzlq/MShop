package google.architecture.home.substance;

import android.view.View;

import google.architecture.common.vcontent.BaseDelegateAdapter;
import google.architecture.coremodel.data.HomeData;

/**
 * @author lq.zeng
 * @date 2018/5/2
 */

public interface PageAdapter {
    BaseDelegateAdapter initTop();
    BaseDelegateAdapter initTitle(String title, String titleCenter, String rightTip, int drawableRightRes, int titleBgRes, boolean isNeedBottomLine,
                                  int clickType, IActionTitleRightLabelClick listener);
    BaseDelegateAdapter initBanner(HomeData.HomeInfo banner);
    BaseDelegateAdapter initBrand();
    BaseDelegateAdapter initSecondBrand();
    BaseDelegateAdapter initRowBrands();
    BaseDelegateAdapter initLongPic();
//    BaseDelegateAdapter initHomeAd(HomeData.HomeInfo adImages);
//    BaseDelegateAdapter initRecommend(HomeData.HomeInfo recommends);
//    BaseDelegateAdapter initNewestGoods(HomeData.HomeInfo newestGoods);
//    BaseDelegateAdapter initYouLikeList(HomeData.HomeInfo likeGoods);

    public void reSetBinded();

    public interface IActionTitleRightLabelClick{
        public void onActionTitleRightLabelClick(View v, int type);
    };
}
