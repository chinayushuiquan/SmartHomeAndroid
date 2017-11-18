package kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPRequest;


import java.util.List;

import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPRequestBodyBase;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class HTTPExitLoginRequestBody extends HTTPRequestBodyBase {

    private String SESSIONID;

    public HTTPExitLoginRequestBody() {
    }

    public HTTPExitLoginRequestBody(String INSTP, String APPKEY, String CHECKSUM, String RESULT, List DATA, String SESSIONID) {
        super(INSTP, APPKEY, CHECKSUM, RESULT, DATA);
        this.SESSIONID = SESSIONID;
    }

    public String getSESSIONID() {
        return SESSIONID;
    }

    public void setSESSIONID(String SESSIONID) {
        this.SESSIONID = SESSIONID;
    }

    @Override
    public String toString() {
        return "HTTPExitLoginRequestBody{" +
                "SESSIONID='" + SESSIONID + '\'' +
                '}';
    }
}
