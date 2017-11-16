package kap.com.smarthome.android.communication.bean.extend.HTTP;


import java.util.List;

import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPRequestBodyBase;

/**
 * Created by Administrator on 2017/10/10 0010.
 */

public class HTTPModifyUserInfoRequestBody  extends HTTPRequestBodyBase {

    //修改用户信息动作枚举
    private String ACTIONTYPE;

    private String USERID;

    //昵称
    private String NICKNAME;

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


    public HTTPModifyUserInfoRequestBody() {

    }

    public HTTPModifyUserInfoRequestBody(String INSTP, String APPKEY, String CHECKSUM, String RESULT, List DATA, String ACTIONTYPE, String USERID, String NICKNAME, String TELEPHONE, String VALIDCODE, String WECHAT, String QQ, String EMAIL) {
        super(INSTP, APPKEY, CHECKSUM, RESULT, DATA);
        this.ACTIONTYPE = ACTIONTYPE;
        this.USERID = USERID;
        this.NICKNAME = NICKNAME;
        this.TELEPHONE = TELEPHONE;
        this.VALIDCODE = VALIDCODE;
        this.WECHAT = WECHAT;
        this.QQ = QQ;
        this.EMAIL = EMAIL;
    }

    public String getACTIONTYPE() {
        return ACTIONTYPE;
    }

    public void setACTIONTYPE(String ACTIONTYPE) {
        this.ACTIONTYPE = ACTIONTYPE;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getNICKNAME() {
        return NICKNAME;
    }

    public void setNICKNAME(String NICKNAME) {
        this.NICKNAME = NICKNAME;
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

    public String toString() {
        return "HTTPModifyUserInfoRequestBody{" +
                "ACTIONTYPE='" + ACTIONTYPE + '\'' +
                ", USERID='" + USERID + '\'' +
                ", NICKNAME='" + NICKNAME + '\'' +
                ", TELEPHONE='" + TELEPHONE + '\'' +
                ", VALIDCODE='" + VALIDCODE + '\'' +
                ", WECHAT='" + WECHAT + '\'' +
                ", QQ='" + QQ + '\'' +
                ", EMAIL='" + EMAIL + '\'' +
                '}';
    }
}
