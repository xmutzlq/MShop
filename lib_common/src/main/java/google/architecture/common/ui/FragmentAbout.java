package google.architecture.common.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.apkfuns.logutils.LogUtils;
import com.king.android.res.config.ARouterPath;

import java.util.ArrayList;
import java.util.List;

import google.architecture.common.R;
import google.architecture.common.base.BaseFragment;
import google.architecture.common.databinding.FragmentAboutBinding;

/**
 * 关于页面
 */
@Route(path = ARouterPath.AboutFgt)
public class FragmentAbout extends BaseFragment<FragmentAboutBinding> {

    private UserData userData1,userData2;
    private UserDataOF userDataOF;

    @Override
    protected int getLayout() {
        return R.layout.fragment_about;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<UserData> userList = new ArrayList<>();

        userData1 = new UserData();
        userData1.setUserName("dawish");
        userData1.setUserId("1212");

        userData2 = new UserData();
        userData2.setUserName("dawish_233");
        userData2.setUserId("2331");

        userList.add(userData1);
        userList.add(userData2);

        binding.setUserList(userList);

        userDataOF = new UserDataOF();
        userDataOF.userName.set("dxxx_5");
        userDataOF.userId.set("xxxxxx");

        binding.setUserDataOF(userDataOF);

        /**执行executePendingBindings方法开始数据绑定*/
        binding.executePendingBindings();

        LogUtils.d("fragmentAboutBinding--->");

        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeName();
            }
        });
    }

    public void changeName(){
        userData1.setUserName("Dawish_大D");
        userData2.setUserName("Dawish_大D_233");
        userDataOF.userName.set("Dawish_ofof");
    }
}
