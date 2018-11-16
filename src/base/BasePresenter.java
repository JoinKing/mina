package base;

import java.lang.ref.WeakReference;

/**
 * Created by king on 2017/12/7.
 */

public abstract class BasePresenter<T> {
    //当内存不足时，释放内存
    protected WeakReference<T> mViewRef;
    public void attachView(T view) {
        mViewRef = new WeakReference<T>(view);
    }

    public void detachView() {
        if(mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
    protected T getView() {
        return mViewRef.get();
    }
}
