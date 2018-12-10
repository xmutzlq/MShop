package google.architecture.common.base.listener;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/4/16
 */

public class ActivityObservable extends AbsObservable<IActivityObserver> implements IActivityObservable{

    @Override
    public void registerActivityObserver(IActivityObserver observer) {
        registerObserver(observer);
    }

    @Override
    public void unregisterActivityObserver(IActivityObserver observer) {
        unregisterObserver(observer);
    }

    @Override
    public boolean dispatchMenuOpened(int featureId, Menu menu) {
        boolean isHandler = false;
        List<IActivityObserver> list = new ArrayList<IActivityObserver>(getObservers());
        for(IActivityObserver observer : list){
            isHandler = observer.onMenuOpened(featureId, menu);
            if(isHandler){
                break;
            }
        }
        return isHandler;
    }

    @Override
    public boolean dispatchOptionsItemSelected(MenuItem item) {
        boolean isHandler = false;
        List<IActivityObserver> list = new ArrayList<IActivityObserver>(getObservers());
        for(IActivityObserver observer : list){
            isHandler = observer.onOptionsItemSelected(item);
            if(isHandler){
                break;
            }
        }
        return isHandler;
    }

    @Override
    public void dispatchActivityResume(boolean isFirst) {
        List<IActivityObserver> list = new ArrayList<IActivityObserver>(getObservers());
        for(IActivityObserver observer : list){
            observer.onActivityResume(isFirst);
        }
    }

    @Override
    public void dispatchActivityPause() {
        List<IActivityObserver> list = new ArrayList<IActivityObserver>(getObservers());
        for(IActivityObserver observer : list){
            observer.onActivityPause();
        }
    }

    @Override
    public boolean dispatchBackPressed() {
        boolean isHandler = false;
        List<IActivityObserver> list = new ArrayList<IActivityObserver>(getObservers());
        for(IActivityObserver observer : list){
            isHandler = observer.onBackPressed();
            if(isHandler){
                break;
            }
        }
        return isHandler;
    }

    @Override
    public boolean dispatchActivityResult(int requestCode, int resultCode, Intent data) {
        boolean isHandler = false;
        List<IActivityObserver> list = new ArrayList<IActivityObserver>(getObservers());
        for(IActivityObserver observer : list){
            isHandler = observer.onActivityResultPatch(requestCode, resultCode, data);
            if(isHandler){
                break;
            }
        }
        return isHandler;
    }
}
