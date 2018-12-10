package google.architecture.common.base.listener;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lq.zeng
 * @date 2018/4/16
 */

public class AbsObservable<Observer>{
    private LinkedList<Observer> mObservers = new LinkedList<Observer>();

    public void registerObserver(Observer observer) {
        if(!mObservers.contains(observer)){
            mObservers.add(observer);
        }
    }

    public void unregisterObserver(Observer observer) {
        if(mObservers.contains(observer)){
            mObservers.remove(observer);
        }
    }

    public void release() {
        mObservers.clear();
    }

    public List<Observer> getObservers(){
        return mObservers;
    }
}
