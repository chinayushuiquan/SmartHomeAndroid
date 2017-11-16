package kap.com.smarthome.android.communication.bean.base.HTTP;

/**
 * Created by Administrator on 2017/10/10 0010.
 */

public class HTTPResponseBodyBase {

    protected String INSTP ;

    protected String RESULT;

    public HTTPResponseBodyBase() {
    }

    public HTTPResponseBodyBase(String INSTP, String RESULT) {
        this.INSTP = INSTP;
        this.RESULT = RESULT;
    }

    public String getINSTP() {
        return INSTP;
    }

    public void setINSTP(String INSTP) {
        this.INSTP = INSTP;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    @Override
    public String toString() {
        return "HTTPResponseBodyBase{" +
                "INSTP='" + INSTP + '\'' +
                ", RESULT='" + RESULT + '\'' +
                '}';
    }
}
