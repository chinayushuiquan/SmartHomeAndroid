package kap.com.smarthome.android.communication.bean.extend.UDP;

import com.alibaba.fastjson.annotation.JSONField;

import kap.com.smarthome.android.communication.bean.base.UDP.UDPRequestBodyBase;


/**
 * Created by Administrator on 2017/10/13 0013.
 */

public class UDPAddRelayBoxRequestBody  extends UDPRequestBodyBase {

       @JSONField(name = "USERID", ordinal = 5)
       private String USERID;

        @JSONField(name = "BOXID", ordinal = 6)
       private String BOXID;


    public UDPAddRelayBoxRequestBody() {
    }

    public UDPAddRelayBoxRequestBody(String USERID, String BOXID) {
        this.USERID = USERID;
        this.BOXID = BOXID;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getBOXID() {
        return BOXID;
    }

    public void setBOXID(String BOXID) {
        this.BOXID = BOXID;
    }


}
