package kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPRequest;


import java.util.List;

import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPRequestBodyBase;

/**
 * Created by Administrator on 2017/10/9 0009.
 */

public class HTTPLoginRequestBody extends HTTPRequestBodyBase {

    //注册类型
    private String LOGINTYPE;

    //远程登录账号
    private String LOGINNAME;

    //用户密码
    private String PASSWORD;

    //用户的电话号码，需符合国际手机号码格式如“86-13112345678
    private String TELEPHONE;

    //VALIDCODE
    private String VALIDCODE;

    //WECHAT
    private String WECHAT;

    //QQ
    private String QQ;

    //EMAIL
    private String EMAIL;

    private String USERID ;


    public HTTPLoginRequestBody() {
    }

    public HTTPLoginRequestBody(String INSTP, String APPKEY, String CHECKSUM, String RESULT, List DATA, String LOGINTYPE, String LOGINNAME, String PASSWORD, String TELEPHONE, String VALIDCODE, String WECHAT, String QQ, String EMAIL, String USERID) {
        super(INSTP, APPKEY, CHECKSUM, RESULT, DATA);
        this.LOGINTYPE = LOGINTYPE;
        this.LOGINNAME = LOGINNAME;
        this.PASSWORD = PASSWORD;
        this.TELEPHONE = TELEPHONE;
        this.VALIDCODE = VALIDCODE;
        this.WECHAT = WECHAT;
        this.QQ = QQ;
        this.EMAIL = EMAIL;
        this.USERID = USERID;
    }

    public String getLOGINTYPE() {
        return LOGINTYPE;
    }

    public void setLOGINTYPE(String LOGINTYPE) {
        this.LOGINTYPE = LOGINTYPE;
    }

    public String getLOGINNAME() {
        return LOGINNAME;
    }

    public void setLOGINNAME(String LOGINNAME) {
        this.LOGINNAME = LOGINNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getTELEPHONE() {
        return TELEPHONE;
    }

    public void setTELEPHONE(String TELEPHONE) {
        this.TELEPHONE = TELEPHONE;
    }

    public String getVALIDCODE() {
        return VALIDCODE;
    }

    public void setVALIDCODE(String VALIDCODE) {
        this.VALIDCODE = VALIDCODE;
    }

    public String getWECHAT() {
        return WECHAT;
    }

    public void setWECHAT(String WECHAT) {
        this.WECHAT = WECHAT;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }


    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }
}
