package google.architecture.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import google.architecture.common.base.BaseFragment;
import google.architecture.common.widget.address.ISelectAble;
import google.architecture.common.widget.address.Selector;
import google.architecture.coremodel.datamodel.http.event.CommEvent;
import google.architecture.home.area.db.DBRxManage;

/**
 * @author lq.zeng
 * @date 2018/5/29
 */

public class FragmentFilterAreaSelect extends BaseFragment {

    private static final int DEFAULT_DEEP = 4;

    private Selector selector;
    private DBRxManage helper;

    public static FragmentFilterAreaSelect newInstance() {
        return new FragmentFilterAreaSelect();
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter_area, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((View)findViewById(view, R.id.filter_area_close)).setOnClickListener(v -> {
            EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_CLOSE_AREA));
        });
        selector = new Selector(mContext, DEFAULT_DEEP);
        selector.setDeepInterrupter(() -> DEFAULT_DEEP);
        selector.setSelectedListener(selectAbles -> {
            String result = "";
            for (ISelectAble selectAble : selectAbles) {
                result += selectAble == null || TextUtils.isEmpty(selectAble.getName()) ? "" : selectAble.getName() + " ";
            }
            Bundle bundle = new Bundle();
            bundle.putString(CommEvent.KEY_EXTRA_VALUE, result);
            EventBus.getDefault().post(new CommEvent(CommEvent.MSG_TYPE_CLOSE_AREA, bundle));
        });
        ((ViewGroup)findViewById(view, R.id.filter_area_layout)).addView(selector.getView());
        loadAreaData();
    }

    private void loadAreaData() {
        helper = new DBRxManage(mContext);
        helper.createDataBaseToSdCard(new DBRxManage.IDBResult() {
            @Override
            public void onPreDBResult() {
            }

            @Override
            public void onDBResult() {
                helper.getProvinces().subscribe(iSelectAbles -> {
                    notifyResult();
                });
            }
        });
    }

    private void notifyResult() {
        selector.setDataProvider((currentDeep, preId, receiver) -> {
            //根据tab的深度和前一项选择的id，获取下一级菜单项
            switch (currentDeep) {
                case 0:
                    helper.getProvinces().subscribe(iSelectAbles -> {
                        receiver.send(iSelectAbles);
                    });
                    break;
                case 1:
                    helper.getCitys(String.valueOf(preId)).subscribe(iSelectAbles -> {
                        receiver.send(iSelectAbles);
                    });
                    break;
                case 2:
                    helper.getCountrys(String.valueOf(preId)).subscribe(iSelectAbles -> {
                        receiver.send(iSelectAbles);
                    });
                    break;
                case 3:
                    helper.getStreets(String.valueOf(preId)).subscribe(iSelectAbles -> {
                        receiver.send(iSelectAbles);
                    });
                    break;
            }
        });
    }
}
