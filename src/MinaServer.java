import java.net.InetSocketAddress;

import filter.CodingProtocol;
import handler.MinaServerHandler;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * mina 服务端程序入口
 */
public class MinaServer {
	// 端口号，要求客户端与服务器端一致
	private static int PORT = 5588;

	public static void main(String[] args) {
		IoAcceptor acceptor = null;
		try {
			// 创建一个非阻塞的server端的Socket
			acceptor = new NioSocketAcceptor();
			// 设置过滤器（使用mina提供的文本换行符编解码器）

//			acceptor.getFilterChain().addLast("codec",
//					new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),
//							LineDelimiter.WINDOWS.getValue(), LineDelimiter.WINDOWS.getValue())));
			acceptor.getFilterChain().addLast("mdc",new MdcInjectionFilter());
			acceptor.getFilterChain().addLast("logger", new LoggingFilter());
			acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CodingProtocol()));
			// 自定义的编解码器
			// acceptor.getFilterChain().addLast("codec", new
			// ProtocolCodecFilter(new CharsetCodecFactory()));
			// 设置读取数据的换从区大小
			acceptor.getSessionConfig().setReadBufferSize(2048);
			// 读写通道10秒内无操作进入空闲状态
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
			// 为接收器设置管理服务
			acceptor.setHandler(new MinaServerHandler());
			// 绑定端口
			acceptor.bind(new InetSocketAddress(PORT));
			System.out.println("服务器启动成功... 端口号未：" + PORT);

		} catch (Exception e) {
			System.out.println("服务器启动异常..."+e.getMessage());
			e.printStackTrace();
		}

	}

}
