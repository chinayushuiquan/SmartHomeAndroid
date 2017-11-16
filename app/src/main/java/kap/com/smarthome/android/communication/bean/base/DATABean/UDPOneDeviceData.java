package kap.com.smarthome.android.communication.bean.base.DATABean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by yushq on 2017/11/2 0002.
 * “BODY”:{
 “INSTP”:”DELTERMINALDEVICEREQ” ,
 “TERMINALDEVICEID” :” 0000000002000001” // 16字节
 “TERMINALDEVICEVALUE” : ” 000000”
 }

 */

public class UDPOneDeviceData {

    @JSONField(name = "TERMINALDEVICEID", ordinal = 1)
    private String TERMINALDEVICEID ;


    @JSONField(name = "TERMINALDEVICEVALUE", ordinal = 2)
    private String  TERMINALDEVICEVALUE ;

    public UDPOneDeviceData() {
    }

    public UDPOneDeviceData(String TERMINALDEVICEID, String TERMINALDEVICEVALUE) {
        this.TERMINALDEVICEID = TERMINALDEVICEID;
        this.TERMINALDEVICEVALUE = TERMINALDEVICEVALUE;
    }

    public String getTERMINALDEVICEID() {
        return TERMINALDEVICEID;
    }

    public void setTERMINALDEVICEID(String TERMINALDEVICEID) {
        this.TERMINALDEVICEID = TERMINALDEVICEID;
    }

    public String getTERMINALDEVICEVALUE() {
        return TERMINALDEVICEVALUE;
    }

    public void setTERMINALDEVICEVALUE(String TERMINALDEVICEVALUE) {
        this.TERMINALDEVICEVALUE = TERMINALDEVICEVALUE;
    }
}
