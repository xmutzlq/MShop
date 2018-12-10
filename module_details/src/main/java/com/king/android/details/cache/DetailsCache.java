package com.king.android.details.cache;

import java.util.ArrayList;
import java.util.List;

import google.architecture.coremodel.data.DetailRecommendInfo;

/**
 * @author lq.zeng
 * @date 2018/6/8
 */

public final class DetailsCache {
    public static final String imgs[] = {"https://img14.360buyimg.com/n1/s546x546_jfs/t17083/327/2335382799/285989/d34a93fa/5af50e5fNa34f717e.jpg",
                                            "https://img14.360buyimg.com/n1/s546x546_jfs/t11434/292/2658672827/40823/c061b116/5a1ce2f9N031f65f7.jpg",
                                            "https://img14.360buyimg.com/n1/s546x546_jfs/t7414/211/2874300655/95051/cb42815/59b62cb6Na9a675f7.jpg",
                                            "https://img14.360buyimg.com/n1/s546x546_jfs/t7738/139/2868938547/260302/5c295b95/59b62cbfN84dbca23.jpg",
                                            "https://img14.360buyimg.com/n1/s546x546_jfs/t11434/292/2658672827/40823/c061b116/5a1ce2f9N031f65f7.jpg"};
    public static String[] getImgs() {
        return imgs;
    }

    public static List<DetailRecommendInfo> getDetailRecommendInfos() {
        List<DetailRecommendInfo> detailRecommendInfos = new ArrayList<>();
        //一页
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/mobilecms/s456x456_jfs/t22582/293/534124721/467849/bb4b3ea/5b349918N7f5ff002.jpg!q70.dpg.webp",
                "APPLE苹果 MacBook air苹果电脑笔记本13.3英寸2017年款轻薄本 官方标配（下单送鼠标+键盘膜） i5+8GB内存+128GB闪存【D32】", "5868.00"));
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/mobilecms/s456x456_jfs/t6700/155/2098998076/156185/6cf95035/595dd5a5Nc3a7dab5.jpg!q70.dpg.webp",
                "小米(MI)Air 13.3英寸金属超轻薄笔记本电脑(i5-7200U 8G 256G PCleSSD MX150 2G独显 FHD 指纹识别 Win10)银", "4999.00"));
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/mobilecms/s456x456_jfs/t20026/165/1231284227/286068/e5b9f16e/5b179c83N4051d200.jpg!q70.dpg.webp",
                "联想(Lenovo)小新潮7000 13.3英寸超轻薄窄边框笔记本电脑(i5-8250U 8G 256G MX150 office2016)花火银", "5299.00"));
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/mobilecms/s456x456_jfs/t15601/39/1909654599/97649/2a2bbc75/5a694948N5cc36e24.jpg!q70.dpg.webp",
                "Apple MacBook Pro 15.4英寸笔记本电脑 银色(Core i7 处理器/16GB内存/256GB SSD闪存/Retina屏 MJLQ2CH/A)", "13288.00"));
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/mobilecms/s456x456_jfs/t17083/327/2335382799/285989/d34a93fa/5af50e5fNa34f717e.jpg!q70.dpg.webp",
                "小米(MI)Pro 15.6英寸金属轻薄笔记本(i5-8250U 8G 256GSSD MX150 2G独显 FHD 指纹识别 预装office)深空灰", "5599.00"));
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/mobilecms/s456x456_jfs/t23116/306/196414644/231993/442bdae5/5b288709Nd796fc55.jpg!q70.dpg.webp",
                "华硕顽石(ASUS) 畅玩版A541UV 15.6英寸娱乐办公笔记本电脑(i3-6006U 4G 500G 920MX 2G独显)黑色", "3199.00"));
        //2页
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/mobilecms/s456x456_jfs/t21145/176/746020245/205456/3be426fc/5b179d15Nb921e4f8.jpg!q70.dpg.webp",
                "联想(Lenovo)小新潮5000 15.6英寸笔记本电脑(i5-7200U 4G 1T+128G 2G独显 FHD Office2016)银", "4199.00"));
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/mobilecms/s456x456_jfs/t14920/22/1777379314/198740/95d0cc64/5a582c82N56109a34.jpg!q70.dpg.webp",
                "联想ThinkPad 翼480（0VCD）英特尔8代酷睿14英寸轻薄窄边框笔记本电脑（i5-8250U 8G 128GSSD+500G 2G独显）", "5499.00"));
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/mobilecms/s456x456_jfs/t7474/343/3338107076/398153/5e8849fc/59f156f1N704c1c7a.jpg!q70.dpg.webp",
                "戴尔DELL XPS13.3英寸超轻薄窄边框笔记本电脑(i5-8250U 8G 256GSSD IPS Win10)无忌银", "7699.00"));
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/mobilecms/s456x456_jfs/t17017/365/1748791174/206845/b5605014/5ad4736aN9c1de980.jpg!q70.dpg.webp",
                "ThinkPad 联想e570 15.6英寸笔记本电脑轻薄手提本 旗舰版|1XCD 8G 256G i5-7200U 2G独显 WIN10", "5499.00"));
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/mobilecms/s456x456_jfs/t17083/327/2335382799/285989/d34a93fa/5af50e5fNa34f717e.jpg!q70.dpg.webp",
                "联想(Lenovo)小新Air 2018款 15.6英寸超轻薄笔记本电脑(i5-8250U 8G 256G MX150 Office2016)灰", "5599.00"));
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/mobilecms/s456x456_jfs/t22243/204/1514283227/245416/7dcbf688/5b2b62d1Nb52bd261.jpg!q70.dpg.webp",
                "华硕（ASUS） 金属超极本RX310/410轻薄便携商务办公超薄学生游戏手提笔记本电脑 旗舰店品质 玫瑰金【金属超极本】 13.3英寸/I3-7100U/4G/128G", "3899.00"));
        //3页
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/mobilecms/s456x456_jfs/t9493/10/1937658089/202465/bc24f9ed/59cdf918N85c402af.jpg!q70.dpg.webp",
                "戴尔（DELL） 旗舰店 灵越燃7000 7472 14英寸八代四核轻薄固态微边框女生办公笔记本电脑 1725粉 预订：i7/8G/双硬盘/2G独显", "6199.00"));
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/mobilecms/s456x456_jfs/t22771/259/515463506/215112/96586ace/5b3359c8N83aafd34.jpg!q70.dpg.webp",
                "华硕（ASUS） U4000 14英寸轻薄笔记本电脑 i5-7200U/8G/512 SSD-钛灰", "5499.00"));
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/mobilecms/s456x456_jfs/t16507/44/1982014378/315151/34304ffd/5a94ccebNdfcace72.jpg!q70.dpg.webp",
                "华硕（ASUS） 超薄笔记本电脑轻薄便携A580固态独显手提办公 【热卖升级】4G/256G固态/2G独显 灰", "3099.00"));
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/mobilecms/s456x456_jfs/t15601/39/1909654599/97649/2a2bbc75/5a694948N5cc36e24.jpg!q70.dpg.webp",
                "Apple 苹果（APPLE） MacBook 2017新款12英寸 超轻薄笔记本电脑 玫瑰金色(YM2/YN2) M3(1.2G) 8G 256闪存 HD615", "8899.00"));
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/mobilecms/s456x456_jfs/t18583/218/1618821733/589894/d347dbe/5ad1d304N866da74d.png!q70.jpg.webp",
                "Apple 苹果 全新正品 MacBook Air 13.3英寸笔记本电脑 2017新款 D32 8GB内存128GB闪存版", "5888.00"));
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/mobilecms/s456x456_jfs/t19813/350/2142796953/110923/fdfef9ce/5b31aa51N54817739.jpg!q70.dpg.webp",
                "华硕（ASUS） 笔记本x555 15.6英寸商务家用上网轻薄便携学生手提超薄超极本游戏笔记本电脑 白色 E2-9010/2G独显/4G/500G", "2699.00"));
        return detailRecommendInfos;
    }

    public static List<DetailRecommendInfo> getDetailRecommendRankInfos() {
        List<DetailRecommendInfo> detailRecommendInfos = new ArrayList<>();
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/n1/s330x330_jfs/t21937/248/436134587/354741/59f6381c/5b0cefeaNe8ccb366.jpg!q70.jpg.webp",
                "华为(HUAWEI) MateBook D(2018版) 15.6英寸轻薄微边框笔记本(i5-8250U 8G 256G MX150 2G独显 FHD office)银", "5188.00"));
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/n1/s330x330_jfs/t14848/365/2076510540/93902/e5883831/5a6947e5N39e16ed8.jpg!q70.jpg.webp",
                "Apple MacBook Air 13.3英寸笔记本电脑 银色(2017款Core i5 处理器/8GB内存/128GB闪存 MQD32CH/A)", "5998.00"));
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/n1/s330x330_jfs/t12937/145/1336861903/193806/872ef179/5a434bffNc7d0c8c2.jpg!q70.jpg.webp",
                "联想ThinkPad E470（1NCD）14英寸笔记本电脑（i5-7200U 4G 500G 2G独显 Win10）黑色", "4299.00"));
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/n1/s330x330_jfs/t15601/39/1909654599/97649/2a2bbc75/5a694948N5cc36e24.jpg!q70.jpg.webp",
                "Apple MacBook Pro 15.4英寸笔记本电脑 银色(Core i7 处理器/16GB内存/256GB SSD闪存/Retina屏 MJLQ2CH/A)", "13288.00"));
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/n1/s330x330_jfs/t9817/357/2388454191/261171/731320b5/59f6ad1dN4003b382.jpg!q70.jpg.webp",
                "联想（Lenovo） YOGA920(YOGA6 PRO)13.9英寸i7超轻薄触控笔记本电脑 棕i7-8550U 16G 1T固态4K屏 蓝牙笔", "16899.00"));
        detailRecommendInfos.add(new DetailRecommendInfo("https://m.360buyimg.com/n1/s330x330_jfs/t11542/236/465469886/260610/bc7201c5/59f15b73N0b92bec5.jpg!q70.jpg.webp",
                "戴尔DELL灵越燃7000 II 14.0英寸轻薄窄边框笔记本电脑(i5-8250U 8G 128GSSD+1T MX150 2G独显 IPS)银", "5299.00"));
        return detailRecommendInfos;
    }

    public static List<SpecData> getColorSpecData() {
        List<SpecData> specDatas = new ArrayList<>();
        List<String> colors = new ArrayList<>();
        colors.add("【爆款】四核i5 8G 256G");
        colors.add("【爆款】四核i7 16G 256G");
        colors.add("【爆款】i7 8G 256G");
        colors.add("【爆款】i7 16G GTX1060");
        colors.add("【爆款】i5 8G GTX1050TI");
        colors.add("【爆款】i5 8G GTX1060");
        colors.add("【爆款】i7 8G GTX1060");
        colors.add("极客双肩包 黑色");
        SpecData colorSpec = new SpecData();
        colorSpec.type = SpecData.TYPE_COLOR;
//        colorSpec.colors = colors;
        specDatas.add(colorSpec);

        SpecData countSpec = new SpecData();
        countSpec.type = SpecData.TYPE_COUNT;
        specDatas.add(countSpec);
        return specDatas;
    }
}
