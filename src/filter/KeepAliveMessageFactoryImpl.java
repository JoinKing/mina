package filter;


import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import utils.log;

/**
 * Created by king on 2017/11/17.
 */

public class KeepAliveMessageFactoryImpl implements KeepAliveMessageFactory{
    private String TAG = "心跳";
    @Override
    public boolean isRequest(IoSession ioSession, Object o) {
//        Log.e(TAG, "isRequest: " );
        return false;
    }

    @Override
    public boolean isResponse(IoSession ioSession, Object o) {
//        Log.e(TAG, "isResponse: " );
        log.e("心跳");
        return false;
    }

    @Override
    public Object getRequest(IoSession ioSession) {

        return 10;
    }

    @Override
    public Object getResponse(IoSession ioSession, Object o) {
//        Log.e(TAG, "getResponse: " );
        return 10;
    }
}
