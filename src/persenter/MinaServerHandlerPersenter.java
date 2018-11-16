package persenter;

import base.BasePresenter;
import contract.MinaServerHandlerContract;
import model.MinaServerHandlerModel;
import org.apache.mina.core.session.IoSession;
/**
 * @author KING
 * @create time 2018.11.13
 */
public class MinaServerHandlerPersenter extends BasePresenter<MinaServerHandlerContract.View> {
    private MinaServerHandlerContract.Model model;
    private MinaServerHandlerContract.View view;

    public MinaServerHandlerPersenter(MinaServerHandlerContract.View view) {
        this.view = view;
        this.model = new MinaServerHandlerModel();
    }
    //上线
    public void onLineUser(IoSession session){
        model.resultAddIoSession(view,session);
    }
    //下线
    public void ofLineUser(IoSession session){
        model.resultDeleteIoSession(view,session);
    }
    //发送消息
    public void onSendMsg(IoSession session, Object message){
        model.receiveMessage(view,session,message);
    }

    //发送所有消息
    public void onSendAllMsg(IoSession session, Object message){
        model.resultSendAllMessage(getView(),session,message);
    }



}
