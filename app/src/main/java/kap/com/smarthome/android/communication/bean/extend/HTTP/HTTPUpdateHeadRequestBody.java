package kap.com.smarthome.android.communication.bean.extend.HTTP;


import java.util.List;

import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPRequestBodyBase;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class HTTPUpdateHeadRequestBody extends HTTPRequestBodyBase {

    private String HEADLOGO ;

    private String USERID;

    public HTTPUpdateHeadRequestBody() {
    }

    public HTTPUpdateHeadRequestBody(String INSTP, String APPKEY, String CHECKSUM, String RESULT, List DATA, String HEADLOGO, String USERID) {
        super(INSTP, APPKEY, CHECKSUM, RESULT, DATA);
        this.HEADLOGO = HEADLOGO;
        this.USERID = USERID;
    }

    public String getHEADLOGO() {
        return HEADLOGO;
    }

    public void setHEADLOGO(String HEADLOGO) {
        this.HEADLOGO = HEADLOGO;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    @Override
    public String toString() {
        return "HTTPUpdateHeadRequestBody{" +
                "HEADLOGO='" + HEADLOGO + '\'' +
                ", USERID='" + USERID + '\'' +
                '}';
    }
}
