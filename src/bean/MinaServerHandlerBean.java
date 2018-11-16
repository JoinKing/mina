package bean;

import org.apache.mina.core.session.IoSession;

import java.io.Serializable;

/**
 * @author KING
 * @create time 2018.11.13
 */
public class MinaServerHandlerBean implements Serializable {
    private String uId;
    private IoSession session;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public IoSession getSession() {
        return session;
    }

    public void setSession(IoSession session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "MinaServerHandlerBean{" +
                "uId='" + uId + '\'' +
                ", session=" + session +
                '}';
    }
}
