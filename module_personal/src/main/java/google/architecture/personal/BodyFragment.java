package google.architecture.personal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

import google.architecture.common.base.BaseFragment;
import google.architecture.coremodel.data.xlj.FullBodyDate;
import google.architecture.coremodel.data.xlj.ScanBody;

public class BodyFragment extends BaseFragment {

    public static BodyFragment getNewInstance(){
        BodyFragment fragment = new BodyFragment();
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_body;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void refreshUI(){
        FullBodyDate data = (FullBodyDate)getArguments().getSerializable("fullBodyDate");
        ScanBody item = data.getBody().getDate().get(0);
        DecimalFormat df = new DecimalFormat("#.0");
        ((TextView)getView().findViewById(R.id.head_cirlen_tv)).setText("头围:"+df.format(item.getHead_cirlen()/10)+"cm");
        ((TextView)getView().findViewById(R.id.neck_cirlen_tv)).setText("颈围:"+df.format(item.getNeck_cirlen()/10)+"cm");
        ((TextView)getView().findViewById(R.id.neck_croth_len_tv)).setText("颈裆长:"+df.format(item.getNeck_croth_len()/10)+"cm");
        ((TextView)getView().findViewById(R.id.chest_cirlen_tv)).setText("胸围:"+df.format(item.getChest_cirlen()/10)+"cm");
        ((TextView)getView().findViewById(R.id.waist_cirlen_tv)).setText("腰围:"+df.format(item.getWaist_cirlen()/10)+"cm");
        ((TextView)getView().findViewById(R.id.pelvis_cirlen_tv)).setText("臀围:"+df.format(item.getPelvis_cirlen()/10)+"cm");
        ((TextView)getView().findViewById(R.id.wrist_left_cirlen_tv)).setText("手腕围(左):"+df.format(item.getWrist_left_cirlen()/10)+"cm");
        ((TextView)getView().findViewById(R.id.wrist_right_cirlen_tv)).setText("手腕围(右):"+df.format(item.getWrist_right_cirlen()/10)+"cm");
        ((TextView)getView().findViewById(R.id.uparm_left_cirlen_tv)).setText("上臂围(左):"+df.format(item.getUparm_left_cirlen()/10)+"cm");
        ((TextView)getView().findViewById(R.id.uparm_right_cirlen_tv)).setText("上臂围(右):"+df.format(item.getUparm_right_cirlen()/10)+"cm");
        ((TextView)getView().findViewById(R.id.forearm_left_cirlen_tv)).setText("前臂围(左):"+df.format(item.getForearm_left_cirlen()/10)+"cm");
        ((TextView)getView().findViewById(R.id.forearm_right_cirlen_tv)).setText("前臂围(右):"+df.format(item.getForearm_right_cirlen()/10)+"cm");
        ((TextView)getView().findViewById(R.id.arm_left_len_tv)).setText("臂长(左):"+df.format(item.getArm_left_len()/10)+"cm");
        ((TextView)getView().findViewById(R.id.arm_right_len_tv)).setText("臂长(右):"+df.format(item.getArm_right_len()/10)+"cm");
        ((TextView)getView().findViewById(R.id.inside_leg_left_len_tv)).setText("内腿长(左):"+df.format(item.getInside_leg_left_len()/10)+"cm");
        ((TextView)getView().findViewById(R.id.inside_leg_right_len_tv)).setText("内腿长(右):"+df.format(item.getInside_leg_right_len()/10)+"cm");
        ((TextView)getView().findViewById(R.id.thigh_left_cirlen_tv)).setText("大腿围(左):"+df.format(item.getThigh_left_cirlen()/10)+"cm");
        ((TextView)getView().findViewById(R.id.thigh_right_cirlen_tv)).setText("大腿围(右):"+df.format(item.getThigh_right_cirlen()/10)+"cm");
        ((TextView)getView().findViewById(R.id.calf_left_cirlen_tv)).setText("小腿围(左):"+df.format(item.getCalf_left_cirlen()/10)+"cm");
        ((TextView)getView().findViewById(R.id.calf_right_cirlen_tv)).setText("小腿围(右):"+df.format(item.getCalf_right_cirlen()/10)+"cm");
        ((TextView)getView().findViewById(R.id.ankle_left_cirlen_tv)).setText("脚踝围(左):"+df.format(item.getAnkle_left_cirlen()/10)+"cm");
        ((TextView)getView().findViewById(R.id.ankle_right_cirlen_tv)).setText("脚踝围(右):"+df.format(item.getAnkle_right_cirlen()/10)+"cm");
        ((TextView)getView().findViewById(R.id.body_height_tv)).setText("身高:"+df.format(item.getBody_height()/10)+"cm");
        ((TextView)getView().findViewById(R.id.shoulder_breadth_tv)).setText("肩宽:"+df.format(item.getShoulder_breadth()/10)+"cm");
        ((TextView)getView().findViewById(R.id.back_len_tv)).setText("后背长:"+df.format(item.getBack_len()/10)+"cm");
        ((TextView)getView().findViewById(R.id.front_back_len_tv)).setText("前背长:"+df.format(item.getFront_back_len()/10)+"cm");
        ((TextView)getView().findViewById(R.id.leg_len_tv)).setText("腿长:"+df.format(item.getLeg_len()/10)+"cm");
    }
}
