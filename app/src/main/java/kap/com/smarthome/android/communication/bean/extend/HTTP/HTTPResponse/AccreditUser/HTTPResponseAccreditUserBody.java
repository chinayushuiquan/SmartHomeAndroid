package kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponse.AccreditUser;


/**
 * Created by Administrator on 2017/10/17 0017.
 */

public class HTTPResponseAccreditUserBody {

    protected String INSTP ;

    protected String RESULT;

    protected String OLDUSERID;

    public HTTPResponseAccreditUserBody() {
    }

    public HTTPResponseAccreditUserBody(String INSTP, String RESULT, String OLDUSERID) {
        this.INSTP = INSTP;
        this.RESULT = RESULT;
        this.OLDUSERID = OLDUSERID;
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

    public String getOLDUSERID() {
        return OLDUSERID;
    }

    public void setOLDUSERID(String OLDUSERID) {
        this.OLDUSERID = OLDUSERID;
    }

    @Override
    public String toString() {
        return "HTTPResponseQueryRoomBody{" +
                "INSTP='" + INSTP + '\'' +
                ", RESULT='" + RESULT + '\'' +
                ", OLDUSERID='" + OLDUSERID + '\'' +
                '}';
    }
}
