package kap.com.smarthome.android.communication.bean.extend.UDP;

import com.alibaba.fastjson.annotation.JSONField;

import kap.com.smarthome.android.communication.bean.base.UDP.UDPRequestBodyBase;


/**
 * Created by yushq on 2017/10/13 0013.
 *
 * CONTROLTYPE = 0;
 DEVICEID = 01101E0A;
 INSTP = DEVICECONTROLREQ;
 VALUE = 00ff;

 *
 */

public class UDPControlDevicesRequestBody extends UDPRequestBodyBase {

       @JSONField(name = "CONTROLTYPE", ordinal = 8)
       private int CONTROLTYPE;

       @JSONField(name = "VALUE", ordinal = 9)
       private String VALUE;

    public UDPControlDevicesRequestBody() {

    }

    public UDPControlDevicesRequestBody(int CONTROLTYPE , String VALUE) {
        this.CONTROLTYPE = CONTROLTYPE;
        this.VALUE = VALUE;
    }

    public int getCONTROLTYPE() {
        return CONTROLTYPE;
    }


    public void setCONTROLTYPE(int  CONTROLTYPE) {
        this.CONTROLTYPE = CONTROLTYPE;
    }


    public String getVALUE() {
        return VALUE;
    }

    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }
}
