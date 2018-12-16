package google.architecture.category;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.king.android.res.config.ARouterPath;

import java.util.ArrayList;
import java.util.List;

import google.architecture.category.adapter.CategoryRightAdapter;
import google.architecture.category.section.CategoryRightDecoration;
import google.architecture.category.section.CategoryRightSection;
import google.architecture.common.base.BaseFragment;
import google.architecture.common.util.CommKeyUtil;
import google.architecture.common.util.ScreenUtils;
import google.architecture.common.viewmodel.CategoryViewModel;
import google.architecture.coremodel.data.OpChildrenCates;
import google.architecture.coremodel.data.OpDiscoverCates;
import google.architecture.coremodel.data.OpDiscoverIndexResult;

/**
 * @author lq.zeng
 * @date 2018/6/6
 */

public class FragmentCategoryRight extends BaseFragment {

    public static final String EXTRA_DATA = "extra_data";

    private RecyclerView recyclerView;
    private CategoryRightAdapter sectionAdapter;

    private List<OpDiscoverCates> opDiscoverCates;
    private List<CategoryRightSection> categoryRightSections;

    private CategoryViewModel categoryViewModel;

    public static FragmentCategoryRight newInstance(List<OpDiscoverCates> cates) {
        FragmentCategoryRight fragmentCategoryRight = new FragmentCategoryRight();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(EXTRA_DATA, new ArrayList<>(cates));
        fragmentCategoryRight.setArguments(bundle);
        return fragmentCategoryRight;
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_right, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //传值
        opDiscoverCates = getArguments().getParcelableArrayList(EXTRA_DATA);
        //Section数据
        categoryRightSections = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(view, R.id.category_right_rv);
        sectionAdapter = new CategoryRightAdapter(R.layout.item_category_section_conent, R.layout.item_category_section_head, categoryRightSections);
        sectionAdapter.setOnItemClickListener((adapter, view1, position) -> {
            if(CategoryRightSection.SECTION_TYPE_ITEM == sectionAdapter.getData().get(position).getType()) {
                //CategoryRightSection categoryRightSection = sectionAdapter.getData().get(position);
                //ARouter.getInstance().build(ARouterPath.Search2Aty).withString(CommKeyUtil.EXTRA_KEY, String.valueOf(categoryRightSection.t.getElement_id())).navigation();
            }
        });
        sectionAdapter.bindToRecyclerView(recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int dataPos) {
                int type = sectionAdapter.getData().get(dataPos).getType();
                switch (type) {
                    case CategoryRightSection.SECTION_TYPE_ITEM:
                        return 3;
                    default:
                    case CategoryRightSection.SECTION_TYPE_TITLE:
                    //case CategoryRightSection.SECTION_TYPE_BANNER:
                        return 1;
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new CategoryRightDecoration());
        recyclerView.setAdapter(sectionAdapter);
        ViewGroup.LayoutParams vglp = recyclerView.getLayoutParams();
        vglp.width = (int) (ScreenUtils.getScreenWidth() * (0.75));

        //组装数据
        categoryViewModel = new CategoryViewModel();
        addRunStatusChangeCallBack(categoryViewModel);
        refreshData(0);
    }

    @Override
    protected void onDataResult(Object o) {
        OpDiscoverIndexResult result = (OpDiscoverIndexResult)o;
        if(result == null) return;
        if(categoryViewModel.isEmpty.get()) {
            sectionAdapter.setEmptyView(R.layout.layout_view_empty);
            return;
        }
        categoryRightSections.clear();
        //头部-banner
        /*if(result.getBanners() != null
                && result.getBanners().size() > 0) {
            result.getDiscoverList().get(0).setChildren(new OpChildrenCates());
            result.getDiscoverList().get(0).getChildren().setBanner(result.getBanners());

            CategoryRightSection sectionBanner = new CategoryRightSection(true, result.getDiscoverList().get(0));
            sectionBanner.setType(CategoryRightSection.SECTION_TYPE_BANNER);
            categoryRightSections.add(sectionBanner);
        }*/

        for (OpDiscoverCates cate : result.getDiscoverList()) {


            //孩子
            if(cate.getChild() != null && cate.getChild().size() > 0) {

                //头部-标题
                CategoryRightSection sectionTitle = new CategoryRightSection(true, cate);
                sectionTitle.setType(CategoryRightSection.SECTION_TYPE_TITLE);
                categoryRightSections.add(sectionTitle);

                for (OpDiscoverCates cates : cate.getChild()) {
                    CategoryRightSection sChild = new CategoryRightSection(cates);
                    sChild.setType(CategoryRightSection.SECTION_TYPE_ITEM);
                    categoryRightSections.add(sChild);
                }


            }
        }

        if(sectionAdapter != null) sectionAdapter.notifyDataSetChanged();
        recyclerView.post(()->{
            recyclerView.scrollToPosition(0);
        });

    }

    public void refreshData(int position) {
        categoryViewModel.getShoppingCategory(String.valueOf(opDiscoverCates.get(position).getElement_id()));
    }

    private boolean noData(int position) {
        return opDiscoverCates == null || position > opDiscoverCates.size() - 1;
    }
}
