package base;

import org.apache.mina.core.service.IoHandlerAdapter;
/**
 * @author KING
 * @create time 2018.11.13
 */
public abstract class BaseIoHandlerAdapter<V,T extends BasePresenter<V>> extends IoHandlerAdapter {
    protected T mPresenter;

    public BaseIoHandlerAdapter() {
        this.mPresenter = createPresenter();
    }

    protected abstract T createPresenter();
}
