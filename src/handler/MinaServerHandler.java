package handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import filter.MsgCodeModel;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class MinaServerHandler extends IoHandlerAdapter {

//	HashMap<IoSession,String>hashMap = new HashMap<>();
//	List<HashMap>sessions = new ArrayList<>();

	// 从端口接受消息，会响应此方法来对消息进行处理
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		super.messageReceived(session, message);
		System.out.println(String.valueOf(message));
		MsgCodeModel model = (MsgCodeModel) message;
		String msg = message.toString();
		if ("exit".equals(msg)) {
			// 如果客户端发来exit，则关闭该连接
			session.close(true);
		}
		// 向客户端发送消息
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		session.write(sdf.format(date));
		System.out.println("服务器接受消息成功-0..." + new String(model.getHeader()));
		System.out.println("服务器接受消息成功-1..." + new String(model.getBody()));

		session.write(message);
	}

	// 向客服端发送消息后会调用此方法
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
		MsgCodeModel model = (MsgCodeModel) message;
//		session.close(true);//加上这句话实现短连接的效果，向客户端成功发送数据后断开连接
	}

	// 关闭与客户端的连接时会调用此方法
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
		System.out.println("服务器与客户端断开连接...");
	}

	// 服务器与客户端创建连接
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
		System.out.println("服务器与客户端创建连接0..."+session.getId());
		System.out.println("服务器与客户端创建连接1..."+session);
	}

	// 服务器与客户端连接打开
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
		System.out.println("服务器与客户端连接打开...");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		super.sessionIdle(session, status);
		System.out.println("服务器进入空闲状态...");
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		super.exceptionCaught(session, cause);
		System.out.println("服务器发送异常..."+ cause.getMessage());
	}

}
