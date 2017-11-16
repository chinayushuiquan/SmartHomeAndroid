package kap.com.smarthome.android.communication.bean.base.DATABean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2017/10/27 0027.
 *
 * “EXCUTEDEVICE”:[ {“DEVICEID”:”1234”, “DEVICEVALUE” :”1234”, “RELAYBOXID”:”1234”},
 {“DEVICEID”:”1234”, “DEVICEVALUE” :”1234”, “RELAYBOXID”:”1234”}],
 *
 */

public class UDPScenesDeviceData {

    @JSONField(name = "DEVICEID", ordinal = 1)
    private String DEVICEID ;

    @JSONField(name = "DEVICEVALUE", ordinal = 2)
    private String DEVICEVALUE ;

    @JSONField(name = "RELAYBOXID", ordinal = 3)
    private String RELAYBOXID ;

    public String getDEVICEID() {
        return DEVICEID;
    }

    public void setDEVICEID(String DEVICEID) {
        this.DEVICEID = DEVICEID;
    }

    public String getDEVICEVALUE() {
        return DEVICEVALUE;
    }

    public void setDEVICEVALUE(String DEVICEVALUE) {
        this.DEVICEVALUE = DEVICEVALUE;
    }

    public String getRELAYBOXID() {
        return RELAYBOXID;
    }

    public void setRELAYBOXID(String RELAYBOXID) {
        this.RELAYBOXID = RELAYBOXID;
    }
}
