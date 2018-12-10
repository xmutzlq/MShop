package google.architecture.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.king.android.res.config.ARouterPath;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseFragment;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.coremodel.data.MoreFilterData;
import google.architecture.coremodel.data.MoreFilterTagData;
import google.architecture.coremodel.data.SearchFilterList;
import google.architecture.coremodel.data.SearchResult;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.coremodel.util.PreferencesUtils;
import google.architecture.home.adapter.MoreFilterAdapter;
import google.architecture.home.databinding.FragmentFilterMainBinding;
import google.architecture.home.search.ScrollLinearLayoutManager;

/**
 * @author lq.zeng
 * @date 2018/5/25
 */
@Route(path = ARouterPath.FilterMainFgt)
public class FragmentFilterMain extends BaseFragment<FragmentFilterMainBinding>{

    public static final String TYPE_FILTER_RESET = "filter_reset";

    private LayoutInflater mLayoutInflater;
    private MoreFilterAdapter moreFilterAdapter;

    private ArrayList<SearchResult.FilterContainer> brands;
//    private String[] mVals = new String[]
//            {"沃物流", "自营520", "货到付款", "仅看有货", "促销", "全球购", "PLUS专享价", "新品", "配送全球"};

    public static FragmentFilterMain newInstance(ArrayList<SearchResult.FilterContainer> filterContainers, String choiceCat) {
        Bundle bundle = new Bundle();
        bundle.putString(CommEvent.KEY_EXTRA_VALUE_2, choiceCat);
        bundle.putParcelableArrayList(CommEvent.KEY_EXTRA_VALUE, filterContainers);
        FragmentFilterMain filterMain = new FragmentFilterMain();
        filterMain.setArguments(bundle);
        return filterMain;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_filter_main;
    }

    public void refreshLocation(String text) {
        binding.tvFilterLocation.setText(text);
    }

    public void refreshCates(String text) {
        String allKinds = TextUtils.isEmpty(text) ? mContext.getString(R.string.filter_title_all_kinds_) : text;
        int color = TextUtils.isEmpty(text) ? ContextCompat.getColor(mContext, R.color.color_999999) : ContextCompat.getColor(mContext, R.color.color_ff593e);
        binding.filterAllKindsTv.setText(allKinds);
        binding.filterAllKindsTv.setTextColor(color);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLayoutInflater = LayoutInflater.from(getContext());
        //配送地址
        binding.tvFilterLocation.setOnClickListener(v -> {
            EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_OPEN_AREA));
        });

        //全部分类
        binding.filterAllKindsLl.setVisibility(View.GONE);
        binding.filterAllKindsLl.setOnClickListener(v -> {
            EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_OPEN_ALL_CATE));
        });

        //重置
        binding.btnReset.setOnClickListener(v -> {
            moreFilterAdapter.reSet();
            PreferencesUtils.putInt(mContext, PreferencesUtils.PREFERENCE_KEY_FILTER_ALL_CATE_POSITION, -1);
            refreshCates("");
            binding.filterPriceRight.setText("");
            binding.filterPriceLeft.setText("");

            Bundle bundle = new Bundle();
            bundle.putParcelable(CommKeyUtil.EXTRA_KEY, null);
            bundle.putString(CommKeyUtil.EXTRA_KEY_2, TYPE_FILTER_RESET);
            CommEvent event = new CommEvent(CommEvent.MSG_TYPE_FILTER_RESULT);
            event.bundle = bundle;
            EventBus.getDefault().post(event);
//            binding.filterTagLayout.getAdapter().setSelectedList(new HashSet<>());
//            binding.filterTagLayout.relayoutToAlign();
        });

        //确定
        binding.btnConfirm.setOnClickListener(v -> {
            CommEvent eventClose = new CommEvent(CommEvent.MSG_TYPE_CLOSE_ALL_FILTER);
            EventBus.getDefault().post(eventClose);
            //发送过滤数据
            String higherPrice = binding.filterPriceRight.getText().toString();
            String lowPrice = binding.filterPriceLeft.getText().toString();
            SearchResult.FilterResultData filterResultData = new SearchResult.FilterResultData();
            filterResultData.setLowPrice(lowPrice);
            filterResultData.setHeightPrice(higherPrice);
            filterResultData.setParams(moreFilterAdapter.getParams());
            Bundle bundle = new Bundle();
            bundle.putParcelable(CommKeyUtil.EXTRA_KEY, filterResultData);
            CommEvent event = new CommEvent(CommEvent.MSG_TYPE_FILTER_RESULT);
            event.bundle = bundle;
            EventBus.getDefault().post(event);
        });

        //服务
//        binding.filterTagLayout.setAdapter(new TagAdapter<String>(mVals){
//            @Override
//            public View getView(CommFlowLayout parent, int position, String s){
//                TextView tv = (TextView) mLayoutInflater.inflate(R.layout.filter_item_tag,
//                        binding.filterTagLayout, false);
//                tv.setText(s);
//                return tv;
//            }
//        });
//        binding.filterTagLayout.relayoutToAlign();
        String choiceCat = getArguments().getString(CommEvent.KEY_EXTRA_VALUE_2);
        brands = getArguments().getParcelableArrayList(CommEvent.KEY_EXTRA_VALUE);
        refreshCates(choiceCat);
        //更多选择
        moreFilterAdapter = new MoreFilterAdapter(R.layout.filter_item_more, makeData(brands));
        binding.filterMoreRecyclerView.setNestedScrollingEnabled(false);
        binding.filterMoreRecyclerView.setAdapter(moreFilterAdapter);
        ScrollLinearLayoutManager scrollLinearLayoutManager = new ScrollLinearLayoutManager(getContext());
        scrollLinearLayoutManager.setScrollEnabled(false);
        binding.filterMoreRecyclerView.setLayoutManager(scrollLinearLayoutManager);
    }

    private List<MoreFilterData> makeData(ArrayList<SearchResult.FilterContainer> brands) {
        List<MoreFilterData> datas = new ArrayList<>();
        for (int i = 0; i < brands.size(); i++) {
            List<MoreFilterTagData> tagDatas = new ArrayList<>();
            SearchResult.FilterContainer filterContainer = brands.get(i);
            String title = filterContainer.getTitle();
            for (int j = 0; j < filterContainer.getList().size(); j++) {
                SearchFilterList searchFilterList = filterContainer.getList().get(j);
                MoreFilterTagParams moreFilterTagParams = new MoreFilterTagParams();
                moreFilterTagParams.setTagId(searchFilterList.getId());
                moreFilterTagParams.setTagName(searchFilterList.getName());
                if(j <= 2) {
                    moreFilterTagParams.setShown(true);
                    tagData(tagDatas, moreFilterTagParams);
                } else {
                    moreFilterTagParams.setShown(false);
                    tagData(tagDatas, moreFilterTagParams);
                }
            }
            MoreFilterParams moreFilterParams = new MoreFilterParams();
            moreFilterParams.setSearchParamKey(filterContainer.getSearch_key());
            moreFilterParams.setTitleName(title);
            moreFilterParams.setChooseMaxNum(filterContainer.getChoose_num());
            moreData(datas, tagDatas, moreFilterParams);
        }
//        tagData(tagDatas, "耐克", true);
//        tagData(tagDatas, "阿迪达斯", true);
//        tagData(tagDatas, "新百伦", true);
//        tagData(tagDatas, "亚瑟士", false);
//        tagData(tagDatas, "美津浓", false);
//        tagData(tagDatas, "李宁", false);
//        tagData(tagDatas, "安踏", false);
//        tagData(tagDatas, "特步", false);
//        tagData(tagDatas, "全部品牌", false);

//        int count = 0;
//        List<MoreFilterTagData> tagDatas2 = new ArrayList<>();
//        for (int i = 17; i < 50; i++) {
//            String value = i % 2 == 0 ? (i + 0.5) + "" : i + "";
//            count++;
//            if(count < 4) {
//                tagData(tagDatas2, value, true);
//            } else {
//                tagData(tagDatas2, value, false);
//            }
//        }
//        moreData(datas, tagDatas2, "尺码");
//
//        List<MoreFilterTagData> tagDatas3 = new ArrayList<>();
//        tagData(tagDatas3, "系带", true);
//        tagData(tagDatas3, "套脚", true);
//        tagData(tagDatas3, "魔术贴", true);
//        tagData(tagDatas3, "松紧带", false);
//        tagData(tagDatas3, "一字式扣带", false);
//        tagData(tagDatas3, "搭扣", false);
//        tagData(tagDatas3, "不系带", false);
//        tagData(tagDatas3, "拉链", false);
//        tagData(tagDatas3, "套筒", false);
//        tagData(tagDatas3, "前系带", false);
//        tagData(tagDatas3, "丁字式扣带", false);
//        tagData(tagDatas3, "侧拉链", false);
//        tagData(tagDatas3, "其他", false);
//        tagData(tagDatas3, "其他", false);
//        moreData(datas, tagDatas3, "闭合方式");
//
//        List<MoreFilterTagData> tagDatas4 = new ArrayList<>();
//        tagData(tagDatas4, "透气", true);
//        tagData(tagDatas4, "耐磨", true);
//        tagData(tagDatas4, "防滑", true);
//        tagData(tagDatas4, "轻质", false);
//        tagData(tagDatas4, "保暖", false);
//        tagData(tagDatas4, "增高", false);
//        tagData(tagDatas4, "减震", false);
//        tagData(tagDatas4, "防水", false);
//        tagData(tagDatas4, "平衡", false);
//        tagData(tagDatas4, "吸汗", false);
//        tagData(tagDatas4, "避震", false);
//        tagData(tagDatas4, "缓冲", false);
//        tagData(tagDatas4, "支撑", false);
//        tagData(tagDatas4, "其他", false);
//        moreData(datas, tagDatas4, "功能");
//
//        List<MoreFilterTagData> tagDatas5 = new ArrayList<>();
//        tagData(tagDatas5, "软面皮", true);
//        tagData(tagDatas5, "磨砂皮", true);
//        tagData(tagDatas5, "漆皮", true);
//        tagData(tagDatas5, "压花皮", false);
//        tagData(tagDatas5, "油蜡皮", false);
//        tagData(tagDatas5, "印花皮", false);
//        tagData(tagDatas5, "皮绒皮", false);
//        tagData(tagDatas5, "打蜡皮", false);
//        tagData(tagDatas5, "贴膜皮", false);
//        tagData(tagDatas5, "摔纹皮", false);
//        tagData(tagDatas5, "其他", false);
//        moreData(datas, tagDatas5, "皮质特征");

        return datas;
    }

    private void moreData(List<MoreFilterData> data, List<MoreFilterTagData> tagData, MoreFilterParams moreFilterParams) {
        MoreFilterData moreFilterData = new MoreFilterData();
        moreFilterData.searchParamKey = moreFilterParams.searchParamKey;
        moreFilterData.itemTilteName = moreFilterParams.titleName;
        moreFilterData.maxChooseNum = moreFilterParams.chooseMaxNum;
        moreFilterData.tagData = tagData;
        data.add(moreFilterData);
    }

    private void tagData(List<MoreFilterTagData> tagData, MoreFilterTagParams moreFilterTagParams) {
        MoreFilterTagData moreFilterTagData = new MoreFilterTagData();
        moreFilterTagData.tagId = moreFilterTagParams.tagId;
        moreFilterTagData.tagName = moreFilterTagParams.tagName;
        moreFilterTagData.isShown = moreFilterTagParams.isShown;
        tagData.add(moreFilterTagData);
    }

    private static class MoreFilterTagParams {
        private String tagId;
        private String tagName;
        private boolean isShown;

        public String getTagId() {
            return tagId;
        }

        public void setTagId(String tagId) {
            this.tagId = tagId;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public boolean isShown() {
            return isShown;
        }

        public void setShown(boolean shown) {
            isShown = shown;
        }
    }

    private static class MoreFilterParams {
        private String searchParamKey;
        private String titleName;
        private int chooseMaxNum;

        public String getTitleName() {
            return titleName;
        }

        public void setTitleName(String titleName) {
            this.titleName = titleName;
        }

        public int getChooseMaxNum() {
            return chooseMaxNum;
        }

        public void setChooseMaxNum(int chooseMaxNum) {
            this.chooseMaxNum = chooseMaxNum;
        }

        public String getSearchParamKey() {
            return searchParamKey;
        }

        public void setSearchParamKey(String searchParamKey) {
            this.searchParamKey = searchParamKey;
        }
    }
}
