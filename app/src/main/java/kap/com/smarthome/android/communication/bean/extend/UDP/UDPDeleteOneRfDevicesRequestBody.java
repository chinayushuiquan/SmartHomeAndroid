package kap.com.smarthome.android.communication.bean.extend.UDP;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

import kap.com.smarthome.android.communication.bean.base.DATABean.UDPDevicesData;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPRequestBodyBase;

/**
 * Created by yushuq 2017/11/1 0001.
 */

public class UDPDeleteOneRfDevicesRequestBody extends UDPRequestBodyBase {

    @JSONField(name = "TERMINALDEVICEID", ordinal = 8)
    private String  TERMINALDEVICEID;

    @JSONField(name = "TERMINALDEVICEVALUE", ordinal = 9)
    private String TERMINALDEVICEVALUE;


    public UDPDeleteOneRfDevicesRequestBody() {

    }

    public UDPDeleteOneRfDevicesRequestBody(String TERMINALDEVICEID, String TERMINALDEVICEVALUE) {
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
