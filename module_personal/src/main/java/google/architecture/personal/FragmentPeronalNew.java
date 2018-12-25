package google.architecture.personal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.king.android.res.config.ARouterPath;
import com.qmuiteam.qmui.layout.QMUIFrameLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.base.BaseFragment;
import google.architecture.personal.adapter.HeaderFooterAdapterWrapper;
import google.architecture.personal.adapter.PersonalNewAdapter;
import google.architecture.personal.databinding.FragmentPersonalNewBinding;

@Route(path = ARouterPath.PersonalShoppingFgt)
public class FragmentPeronalNew extends BaseFragment<FragmentPersonalNewBinding> {
    @Override
    protected int getLayout() {
        return R.layout.fragment_personal_new;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List list = new ArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        PersonalNewAdapter adapter = new PersonalNewAdapter(list);
        HeaderFooterAdapterWrapper adapterWrapper = new HeaderFooterAdapterWrapper(adapter);
        binding.personalNew.setAdapter(adapterWrapper);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position == 0){
                    return 2;
                }else{
                    return 1;
                }
            }
        });
        binding.personalNew.setLayoutManager(gridLayoutManager);
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.personal_new_header,null);
        QMUIFrameLayout qmuiFrameALayout = headerView.findViewById(R.id.shadow_a_layout);
        qmuiFrameALayout.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, 6),
                QMUIDisplayHelper.dp2px(mContext, 8), 0.5f);
        adapterWrapper.addHeaderView(headerView);
    }
}
