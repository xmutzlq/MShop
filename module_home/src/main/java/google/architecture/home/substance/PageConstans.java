package google.architecture.home.substance;

/**
 * @author lq.zeng
 * @date 2018/5/2
 */

public final class PageConstans {
    public interface viewType{
        int typeTop = 0;            //顶部
        int typeBanner = 1;         //轮播图
        int typeGv = 2;             //九宫格
        int typeTitle = 3;          //标题
        int typeList = 4;           //list
        int typeNews = 5;           //新闻
        int typeMarquee = 6;        //跑马灯
        int typePlus = 7 ;          //不规则视图
        int typeSticky = 8;         //指示器
        int typeFooter = 9;         //底部
        int typeGvSecond = 10;      //九宫格
        int typeRecommend = 11;     //推荐
        int typeNewestGoods = 12;   //最新商品
        int typeYouLikeList = 13;   //猜你喜欢
        int typeBrand = 14;         //商品列表
        int typeHorizontal = 15;
        int typeThreeSome = 16;
        int typeCommonView = 17;
        int typeCenterView = 18;
        int typeColumnTwo = 19;
        int typeClassify = 20;
    }

    public interface titleRightClickType {
        int clickTypeRecommnedExchange = 20;
    }
}
