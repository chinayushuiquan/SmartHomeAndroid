package kap.com.smarthome.android.communication.bean.extend.UDP;

import com.alibaba.fastjson.annotation.JSONField;

import kap.com.smarthome.android.communication.bean.base.UDP.UDPRequestBodyBase;

/**
 * Created by yushuq 2017/11/1 0001.
 */

public class UDPGetRfDeviceIdRequestBody  extends UDPRequestBodyBase {

    @JSONField(name = "SECURITYSUBTYPE", ordinal = 8)
    private String  SECURITYSUBTYPE;

    @JSONField(name = "SECURITYCODE", ordinal = 9)
    private String SECURITYCODE;


    public UDPGetRfDeviceIdRequestBody() {
    }

    public UDPGetRfDeviceIdRequestBody(String SECURITYSUBTYPE, String SECURITYCODE) {
        this.SECURITYSUBTYPE = SECURITYSUBTYPE;
        this.SECURITYCODE = SECURITYCODE;
    }

    public String getSECURITYSUBTYPE() {
        return SECURITYSUBTYPE;
    }

    public void setSECURITYSUBTYPE(String SECURITYSUBTYPE) {
        this.SECURITYSUBTYPE = SECURITYSUBTYPE;
    }

    public String getSECURITYCODE() {
        return SECURITYCODE;
    }

    public void setSECURITYCODE(String SECURITYCODE) {
        this.SECURITYCODE = SECURITYCODE;
    }
}
