package kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPRequest;


import java.util.List;

import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPRequestBodyBase;

/**
 * Created by Administrator on 2017/9/30 0030.
 * 验证码的请求实体 通过实体转换成 请求服务器的 Json
 */

public class HTTPVerificodeRequestBody extends HTTPRequestBodyBase {

    //手机号
    private String TELEPHONE;

    //请求验证码的类型
    private String ACTIONTYPE;

    //地区区号
    private String LOCATION;

    public HTTPVerificodeRequestBody(String INSTP, String APPKEY, String CHECKSUM, String RESULT, List DATA, String TELEPHONE, String ACTIONTYPE) {
        super(INSTP, APPKEY, CHECKSUM, RESULT, DATA);
        this.TELEPHONE = TELEPHONE;
        this.ACTIONTYPE = ACTIONTYPE;
    }

    public HTTPVerificodeRequestBody() {

    }

    public String getTELEPHONE() {
        return TELEPHONE;
    }

    public void setTELEPHONE(String TELEPHONE) {
        this.TELEPHONE = TELEPHONE;
    }

    public String getACTIONTYPE() {
        return ACTIONTYPE;
    }

    public void setACTIONTYPE(String ACTIONTYPE) {
        this.ACTIONTYPE = ACTIONTYPE;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }


}
