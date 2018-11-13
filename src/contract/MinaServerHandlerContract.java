package contract;

import base.BaseView;
import org.apache.mina.core.session.IoSession;

public class MinaServerHandlerContract {

    public interface View extends BaseView{
        void requestAddIoSession(String result);
        void requestDeleteIoSession(String result);
        void requestSendMessage(String result);
        void requestUpdataIoSession(String result);
        void requestAllIoSession();
    }

    public interface Model{
        String LOGIN = "1";//文本消息
        String EXIT = "-1";//文本消息
        String TEXT = "TEXT";//文本消息
        String IMAGE = "IMAGE";//图片消息
        String VOICE = "VOICE";//语音消息

        void resultAddIoSession(View view,IoSession session);
        void resultDeleteIoSession(View view,IoSession session);
        void resultSendAllMessage(View view,IoSession session,Object message);
        void receiveMessage(View view,IoSession session,Object message);
    }
}
