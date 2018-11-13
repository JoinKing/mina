package bean;

import org.apache.mina.core.session.IoSession;

public class MinaServerHandlerBean {
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
