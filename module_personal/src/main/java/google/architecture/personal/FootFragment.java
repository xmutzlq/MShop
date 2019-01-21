package google.architecture.personal;

import android.widget.TextView;

import java.text.DecimalFormat;

import google.architecture.common.base.BaseFragment;
import google.architecture.coremodel.data.xlj.FullBodyDate;
import google.architecture.coremodel.data.xlj.ScanFoot;

public class FootFragment extends BaseFragment {

    public static FootFragment getNewInstance(){
        FootFragment fragment = new FootFragment();
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_foot;
    }

    public void refreshUI(){
        FullBodyDate data = (FullBodyDate)getArguments().getSerializable("fullBodyDate");
        ScanFoot item = data.getFoot().getDate().get(0);
        DecimalFormat df = new DecimalFormat("#.0");
        ((TextView)getView().findViewById(R.id.leftLength_tv)).setText("长(左):"+df.format(item.getLeftLength()/100)+"cm");
        ((TextView)getView().findViewById(R.id.rightLength_tv)).setText("长(右):"+df.format(item.getRightLength()/100)+"cm");
        ((TextView)getView().findViewById(R.id.leftBreadth_tv)).setText("宽(左):"+df.format(item.getLeftBreadth()/100)+"cm");
        ((TextView)getView().findViewById(R.id.rightBreadth_tv)).setText("宽(右):"+df.format(item.getRightBreadth()/100)+"cm");
        ((TextView)getView().findViewById(R.id.leftHigh_tv)).setText("高(左):"+df.format(item.getLeftHigh()/100)+"cm");
        ((TextView)getView().findViewById(R.id.rightHigh_tv)).setText("高(右):"+df.format(item.getRightHigh()/100)+"cm");
        ((TextView)getView().findViewById(R.id.leftLowHigh_tv)).setText("浅口脚背高度(左):"+df.format(item.getLeftLowHigh()/100)+"cm");
        ((TextView)getView().findViewById(R.id.rightLowHigh_tv)).setText("浅口脚背高度(右):"+df.format(item.getRightLowHigh()/100)+"cm");
        ((TextView)getView().findViewById(R.id.leftHeelDist_tv)).setText("足弓脚后跟距离(左):"+df.format(item.getLeftHeelDist()/100)+"cm");
        ((TextView)getView().findViewById(R.id.rightHeelDist_tv)).setText("足弓脚后跟距离(右):"+df.format(item.getRightHeelDist()/100)+"cm");
        ((TextView)getView().findViewById(R.id.leftFootCirlen_tv)).setText("脚围(左):"+df.format(item.getLeftFootCirlen()/100)+"cm");
        ((TextView)getView().findViewById(R.id.rightFootCirlen_tv)).setText("脚围(右):"+df.format(item.getRightFootCirlen()/100)+"cm");
        ((TextView)getView().findViewById(R.id.leftInstepCirlen_tv)).setText("脚背围(左):"+df.format(item.getLeftInstepCirlen()/100)+"cm");
        ((TextView)getView().findViewById(R.id.rightInstepCirlen_tv)).setText("脚背围(右):"+df.format(item.getRightInstepCirlen()/100)+"cm");
    }

}
