package model;

import bean.MinaServerHandlerBean;
import contract.MinaServerHandlerContract;
import filter.MsgCodeModel;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.json.JSONException;
import org.json.JSONObject;
import utils.EditDataModel;
import utils.log;

import java.util.LinkedList;
import java.util.List;

/**
 * @author KING
 * @create time 2018.11.13
 */

public class MinaServerHandlerModel implements MinaServerHandlerContract.Model {

    private LinkedList<MinaServerHandlerBean> list = new LinkedList<>();
    private MinaServerHandlerBean minaServerHandlerBean;

    /**
     * 用户上线缓存用户session
     *
     * @param session 用户session
     */
    @Override
    public void resultAddIoSession(MinaServerHandlerContract.View view, IoSession session) {
        log.e("用户" + session.getId() + ",已经上线.");
        minaServerHandlerBean = new MinaServerHandlerBean();
        minaServerHandlerBean.setSession(session);
        minaServerHandlerBean.setuId("-1");
        list.add(minaServerHandlerBean);
        view.setResult(session.getId() + "已经上线!");
    }

    /**
     * 用户离线删除session
     *
     * @param view
     * @param session
     */
    @Override
    public void resultDeleteIoSession(MinaServerHandlerContract.View view, IoSession session) {
        view.setResult("用户" + session.getId() + ",已经下线.");
        if (list.size() > 0) {
            list.remove(session);
        } else {
            System.out.println("暂无需要删除的用户");
        }

    }

    /**
     * 向所有用户发送消息
     *
     * @param view
     * @param session
     * @param message
     */
    @Override
    public void resultSendAllMessage(MinaServerHandlerContract.View view, IoSession session, Object message) {
        view.setResult("正在给所有用户发送消息");

    }

    /**
     * 当服务器接收到消息的时候
     * 处理消息的转发以及存储session,清除历史存在的session
     *
     * @param view
     * @param session 用户
     * @param message 消息体
     */
    @Override
    public void receiveMessage(MinaServerHandlerContract.View view, IoSession session, Object message) {
        /**
         * 业务需求
         * (1).用户第一次登陆，需要缓存session，以及用户id 存储对象为- -MsgCodeModel- -
         * (2).非第一次登陆，需要转发消息
         *
         */
        MsgCodeModel model = (MsgCodeModel) message;
        view.setResult("服务器收到" + session.getId() + ",发来的消息，消息内容为：" + new String(model.getBody()));
        view.setResult("服务器收到" + session.getId() + ",发来的消息，消息体：" + model.getHeader());

        for (int i = 0; i <list.size() ; i++) {
            log.e(list.get(i).hashCode());
        }
        //1.解析消息体（{"fileName":"","msgType":-1,"receiver":"","sender":"0001","bodyLength":0}）
        // 注意：msgType 1登陆 -1退出登陆 TEXT文本消息 IMAGE图片消息 VOICE语音消息
        try {
            JSONObject header = new JSONObject(model.getHeader());
            String sender = header.optString("sender");
            String msgType = header.optString("msgType");
            String receiver = header.optString("receiver");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    onSendMessage(msgType, model,receiver,sender,session);
                }
            }).start();



            view.setResult("当前在线用户数:" + list.size() + "人。");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param msgType 消息类型 1 登陆 -1 退出登陆 TEXT文本消息 IMAGE图片消息 VOICE语音消息
     * @param model 当前转发的消息体
     * @param receiver 接收者id
     */
    private void onSendMessage(String msgType, MsgCodeModel model, String receiver,String sender,IoSession session) {
        switch (msgType) {
            case LOGIN:
                onCacheUser(sender, list, session);//缓存用户session
                break;
            case EXIT:
                session.closeNow();
                break;
            case TEXT:
                IoBuffer TEXT = EditDataModel.init().sendData(model.getHeader(), model.getBody());
                getReceiver(receiver).write(TEXT);
                break;
            case IMAGE:
                //1.缓存文件操作（暂时未做缓存）
                //2.转发消息
                IoBuffer IMAGE = EditDataModel.init().sendData(model.getHeader(), model.getBody());
                getReceiver(receiver).write(IMAGE);
                break;
            case VOICE:
                //1.缓存文件操作（暂时未做缓存）
                //2.转发消息
                IoBuffer VOICE = EditDataModel.init().sendData(model.getHeader(), model.getBody());
                getReceiver(receiver).write(VOICE);
                break;
        }
    }


    /**
     * 缓存session
     *
     * @param sender
     * @param list
     */
    public void onCacheUser(String sender, List<MinaServerHandlerBean> list, IoSession session) {
        for (int i = 0; i < list.size(); i++) {//处理在线的session，设置id
            if (list.get(i).getSession().hashCode() == session.hashCode()) {
                list.get(i).setuId(sender);
            } else if (list.get(i).getuId().equals(sender)) {
                for (int j = 0; j < list.size(); j++) {//处理离线重连的session，并清除没用的session
                    if (list.get(j).getSession().hashCode() == session.hashCode()) {
                        list.remove(j);
                        break;
                    }
                }
                list.get(i).setSession(session);
            }
        }
    }

    public IoSession getReceiver(String receiver){
        IoSession user = null;

        for (int i = 0; i <list.size() ; i++) {
            if (list.get(i).getuId().equals(receiver)){
                user = list.get(i).getSession();
                break;
            }
        }
        return user;
    }
}
