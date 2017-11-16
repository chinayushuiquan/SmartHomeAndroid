package kap.com.smarthome.android.communication.bean.extend.HTTP;


import java.util.List;

import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPRequestBodyBase;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class HTTPModifyPassWordRequestBody   extends HTTPRequestBodyBase {

    private String USERID;

    private String PASSWORD;

    private String OLDPASSWORD;

    public HTTPModifyPassWordRequestBody() {
    }


    public HTTPModifyPassWordRequestBody(String INSTP, String APPKEY, String CHECKSUM, String RESULT, List DATA, String USERID, String PASSWORD, String OLDPASSWORD) {
        super(INSTP, APPKEY, CHECKSUM, RESULT, DATA);
        this.USERID = USERID;
        this.PASSWORD = PASSWORD;
        this.OLDPASSWORD = OLDPASSWORD;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getOLDPASSWORD() {
        return OLDPASSWORD;
    }

    public void setOLDPASSWORD(String OLDPASSWORD) {
        this.OLDPASSWORD = OLDPASSWORD;
    }

    @Override
    public String toString() {
        return "HTTPModifyPassWordRequestBody{" +
                "USERID='" + USERID + '\'' +
                ", PASSWORD='" + PASSWORD + '\'' +
                ", OLDPASSWORD='" + OLDPASSWORD + '\'' +
                '}';
    }
}
