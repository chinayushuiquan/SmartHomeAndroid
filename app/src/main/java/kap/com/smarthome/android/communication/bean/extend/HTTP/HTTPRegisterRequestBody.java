package kap.com.smarthome.android.communication.bean.extend.HTTP;


import java.util.List;

import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPRequestBodyBase;

/**
 * Created by yushq on 2017/9/29 0029.
 * 注册新用户的 实体类
 */

public class HTTPRegisterRequestBody extends HTTPRequestBodyBase {

    //注册类型
    private String REGTYPE;

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


    public HTTPRegisterRequestBody() {

    }

    public HTTPRegisterRequestBody(String INSTP, String APPKEY, String CHECKSUM, String RESULT, List DATA, String REGTYPE, String LOGINNAME, String PASSWORD, String TELEPHONE, String VALIDCODE, String WECHAT, String QQ, String EMAIL) {
        super(INSTP, APPKEY, CHECKSUM, RESULT, DATA);
        this.REGTYPE = REGTYPE;
        this.LOGINNAME = LOGINNAME;
        this.PASSWORD = PASSWORD;
        this.TELEPHONE = TELEPHONE;
        this.VALIDCODE = VALIDCODE;
        this.WECHAT = WECHAT;
        this.QQ = QQ;
        this.EMAIL = EMAIL;
    }

    public String getREGTYPE() {
        return REGTYPE;
    }

    public void setREGTYPE(String REGTYPE) {
        this.REGTYPE = REGTYPE;
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

    @Override
    public String toString() {
        return "HTTPRegisterBody{" +
                "REGTYPE='" + REGTYPE + '\'' +
                ", LOGINNAME='" + LOGINNAME + '\'' +
                ", PASSWORD='" + PASSWORD + '\'' +
                ", TELEPHONE='" + TELEPHONE + '\'' +
                ", VALIDCODE='" + VALIDCODE + '\'' +
                ", WECHAT='" + WECHAT + '\'' +
                ", QQ='" + QQ + '\'' +
                ", EMAIL='" + EMAIL + '\'' +
                '}';
    }
}
