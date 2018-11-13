package handler;

import base.BaseIoHandlerAdapter;
import contract.MinaServerHandlerContract;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import persenter.MinaServerHandlerPersenter;
import utils.log;

public class MinaServerHandler extends BaseIoHandlerAdapter<MinaServerHandlerContract.View, MinaServerHandlerPersenter>implements MinaServerHandlerContract.View {
	// 从端口接受消息，会响应此方法来对消息进行处理
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		super.messageReceived(session, message);
		mPresenter.onSendMsg(session,message);
	}

	// 向客服端发送消息后会调用此方法
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
//		session.close(true);//加上这句话实现短连接的效果，向客户端成功发送数据后断开连接
	}

	// 关闭与客户端的连接时会调用此方法
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
		mPresenter.ofLineUser(session);
	}

	// 服务器与客户端创建连接
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
	}

	// 服务器与客户端连接打开
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
		mPresenter.onLineUser(session);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		super.sessionIdle(session, status);
		System.out.println("服务器进入空闲状态...");
	}

	/**
	 * 出现异常的时候调用该方法
	 * @param session
	 * @param cause
	 * @throws Exception
	 */

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		super.exceptionCaught(session, cause);
	}

	/**
	 * 操作的结果
	 * @param result
	 */
	@Override
	public void requestAddIoSession(String result) {

	}

	@Override
	public void requestDeleteIoSession(String result) {

	}

	@Override
	public void requestSendMessage(String result) {

	}

	@Override
	public void requestUpdataIoSession(String result) {

	}

	@Override
	public void requestAllIoSession() {

	}

	@Override
	protected MinaServerHandlerPersenter createPresenter() {
		return  new MinaServerHandlerPersenter(this);
	}
}
