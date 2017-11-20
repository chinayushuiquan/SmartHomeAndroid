package kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponse.Login;


import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPResponseBodyBase;

/**
 * Created by Administrator on 2017/10/10 0010.
 */

public class HTTPLoginResponseBody extends HTTPResponseBodyBase {

    private String USERID;

    private String SESSIONID;

    public HTTPLoginResponseBody(String INSTP, String RESULT, String USERID, String SESSIONID) {
        super(INSTP, RESULT);
        this.USERID = USERID;
        this.SESSIONID = SESSIONID;
    }

    public HTTPLoginResponseBody() {

    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getSESSIONID() {
        return SESSIONID;
    }

    public void setSESSIONID(String SESSIONID) {
        this.SESSIONID = SESSIONID;
    }


    @Override
    public String toString() {
        return "HTTPLoginResponseBody{" +
                "USERID='" + USERID + '\'' +
                ", SESSIONID='" + SESSIONID + '\'' +
                 "INSTP:" + INSTP +","+
                 "RESULT : "+ RESULT+ ","+
                '}';
    }
}
