package base;

import org.apache.mina.core.service.IoHandlerAdapter;

public abstract class BaseIoHandlerAdapter<V,T extends BasePresenter<V>> extends IoHandlerAdapter {
    protected T mPresenter;

    public BaseIoHandlerAdapter() {
        this.mPresenter = createPresenter();
    }

    protected abstract T createPresenter();
}
