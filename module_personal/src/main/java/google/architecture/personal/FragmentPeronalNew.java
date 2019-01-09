package google.architecture.personal;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.king.android.res.config.ARouterPath;
import com.qmuiteam.qmui.layout.QMUIFrameLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import google.architecture.common.base.BaseApplication;
import google.architecture.common.base.BaseFragment;
import google.architecture.common.imgloader.ImageLoader;
import google.architecture.common.viewmodel.PersonalViewNewModel;
import google.architecture.coremodel.data.xlj.personal.LikeGoods;
import google.architecture.coremodel.data.xlj.personal.UserInfos;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.personal.adapter.HeaderFooterAdapterWrapper;
import google.architecture.personal.adapter.PersonalNewAdapter;
import google.architecture.personal.databinding.FragmentPersonalNewBinding;

@Route(path = ARouterPath.PersonalShoppingFgt)
public class FragmentPeronalNew extends BaseFragment<FragmentPersonalNewBinding> {
    HeaderFooterAdapterWrapper adapterWrapper;
    private List<LikeGoods> mList;
    private PersonalViewNewModel viewModel;

    private CircleImageView mAvatorIv;
    private TextView mUserNameTv;
    private TextView mUserScoreTv;//积分

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        getToolbarHelper().getAppBarLayout().setVisibility(View.GONE);
    }

    @Override
    protected boolean isStatusBarTransparent() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_personal_new;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mList = new ArrayList();
        PersonalNewAdapter mAdapter = new PersonalNewAdapter(mList, new PersonalNewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                openGoodsDetail(mList.get(position).getGoodsId()+"");
            }
        });
        adapterWrapper = new HeaderFooterAdapterWrapper(mAdapter);
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

        QMUIFrameLayout qmuiFrameBLayout = headerView.findViewById(R.id.shadow_b_layout);
        qmuiFrameBLayout.setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, 6),
                QMUIDisplayHelper.dp2px(mContext, 8), 0.5f);

        adapterWrapper.addHeaderView(headerView);

        binding.personalRefreshLayout.setEnableOverScrollBounce(false);
        binding.personalRefreshLayout.setOnMultiPurposeListener(listener);

        initHeaderViewUI(headerView);
        initHeaderViewBtn(headerView);

        viewModel = new PersonalViewNewModel();
        viewModel.userInfosObservableField.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                refreshData(viewModel.userInfosObservableField.get());
            }
        });
        addRunStatusChangeCallBack(viewModel);//为了现实loading界面
    }

    private void initHeaderViewUI(View headerView){
        mAvatorIv = headerView.findViewById(R.id.avator_iv);//头像
        mAvatorIv.setOnClickListener(view -> {
            checkLogin(null);
        });
        mUserNameTv = headerView.findViewById(R.id.user_name_tv);//用户名
        mUserScoreTv = headerView.findViewById(R.id.user_score_tv);
    }

    private void initHeaderViewBtn(View headerView){
        //设置
        headerView.findViewById(R.id.btn_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                checkLogin(()->{
                    ARouter.getInstance().build(ARouterPath.PersonalSettingAty).navigation(mContext);
                    //ARouter.getInstance().build(ARouterPath.WeixinLoginAty).navigation(mContext);
                    //ARouter.getInstance().build(ARouterPath.PromotionAty).navigation(mContext);
//                });
            }
        });

        //上
        headerView.findViewById(R.id.btn_way_for_pay).setOnClickListener(view -> {
            checkLogin(null);
        });

        headerView.findViewById(R.id.btn_way_for_deliver).setOnClickListener(view -> {
            checkLogin(null);
        });

        headerView.findViewById(R.id.btn_way_for_receiver).setOnClickListener(view -> {
            checkLogin(null);
        });

        headerView.findViewById(R.id.btn_comment).setOnClickListener(view -> {
            checkLogin(null);
        });


        headerView.findViewById(R.id.btn_return_saled).setOnClickListener(view -> {
            checkLogin(null);
        });

        //下1

        headerView.findViewById(R.id.btn_coupons).setOnClickListener(view -> {
            checkLogin(null);
        });

        headerView.findViewById(R.id.btn_coins).setOnClickListener(view -> {
            checkLogin(null);
        });

        headerView.findViewById(R.id.btn_drink_ticket).setOnClickListener(view -> {
            checkLogin(null);
        });

        headerView.findViewById(R.id.btn_my_data).setOnClickListener(view -> {
            checkLogin(null);
        });

        //下2

        headerView.findViewById(R.id.btn_my_favorite).setOnClickListener(view -> {
            checkLogin(null);
        });

        headerView.findViewById(R.id.btn_my_history).setOnClickListener(view -> {
            checkLogin(null);
        });

        headerView.findViewById(R.id.btn_my_ques).setOnClickListener(view -> {
            checkLogin(null);
        });

        headerView.findViewById(R.id.btn_my_addr).setOnClickListener(view -> {
            checkLogin(null);
        });

    }

    SimpleMultiPurposeListener listener = new SimpleMultiPurposeListener() {
        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishLoadMore(0);
        }
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(0);
            checkLogin();
        }
        @Override
        public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {

        }
    };

    private void openGoodsDetail(String goodsIs){
        ARouter.getInstance().build(ARouterPath.DetailAty).withString(CommEvent.KEY_EXTRA_VALUE,goodsIs).navigation(mContext);
    }

    private void refreshData(UserInfos infos){
        if(!TextUtils.isEmpty(infos.getUserInfo().getUserPhoto())){
            ImageLoader.get().load(mAvatorIv, infos.getUserInfo().getUserPhoto());
        }

        if(!TextUtils.isEmpty(infos.getUserInfo().getUserName())){
            mUserNameTv.setText(infos.getUserInfo().getUserName());
        }

        mUserScoreTv.setText(infos.getUserInfo().getUserScore());

        mList.clear();
        mList.addAll(infos.getLike());
        adapterWrapper.notifyDataSetChanged();
    }

    @Override
    public void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        checkLogin();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onUserLoginStateChange(boolean isLogin) {
        if(isLogin) {
            loadUserData();
        } else {
            ImageLoader.get().load(mAvatorIv, R.drawable.default_avator, null);
            mUserNameTv.setText("---");
            mUserScoreTv.setText(0 + "");
        }
    }

    @Override
    public void onMessageEvent(CommEvent event) {
        super.onMessageEvent(event);
        if(CommEvent.MSG_TYPE_UPDATE_USER_INFO.equals(event.msgType)) {
            loadUserData();
        }
    }

    @Override
    public void onReLoad() {
        loadUserData();
    }

    private void checkLogin() {
        if(!BaseApplication.getIns().isUserLogin()) {
            ARouter.getInstance().build(ARouterPath.WeixinLoginAty).navigation(mContext);
        } else {
            loadUserData();
        }
    }

    private void checkLogin(Runnable runnable) {
        if(!BaseApplication.getIns().isUserLogin()) {
            ARouter.getInstance().build(ARouterPath.WeixinLoginAty).navigation(mContext);
        } else {
            if(runnable != null) runnable.run();
        }
    }

    private void loadUserData() {
        if(viewModel != null) viewModel.loadData();
    }
}
