package kap.com.smarthome.android.communication.bean.base.DATABean;

/**
 * Created by yushq on 2017/11/3 0003.
 *
 *
 *  "SCENEDEVICE":[{"DEVICEID":"20a2061907d2476b","SCENEDEVICEGUID":"20a2061907d2476b",
 *  "DEVICEINFO":"9","DEVICETYPE":"2","DEVICEVALUE":"设备值","RELAYBOXID":"1001116016400001"}],
 *
 */

public class ScenesDevicesData {

    private String DEVICEID;

    private String SCENEDEVICEGUID;

    private String DEVICETYPE;

    private String DEVICEINFO;

    private String DEVICEVALUE;

    private String RELAYBOXID;

    public ScenesDevicesData() {
    }


    public ScenesDevicesData(String DEVICEID, String SCENEDEVICEGUID, String DEVICETYPE, String DEVICEINFO, String DEVICEVALUE, String RELAYBOXID) {
        this.DEVICEID = DEVICEID;
        this.SCENEDEVICEGUID = SCENEDEVICEGUID;
        this.DEVICETYPE = DEVICETYPE;
        this.DEVICEINFO = DEVICEINFO;
        this.DEVICEVALUE = DEVICEVALUE;
        this.RELAYBOXID = RELAYBOXID;
    }

    public String getDEVICEID() {
        return DEVICEID;
    }

    public void setDEVICEID(String DEVICEID) {
        this.DEVICEID = DEVICEID;
    }

    public String getSCENEDEVICEGUID() {
        return SCENEDEVICEGUID;
    }

    public void setSCENEDEVICEGUID(String SCENEDEVICEGUID) {
        this.SCENEDEVICEGUID = SCENEDEVICEGUID;
    }

    public String getDEVICEINFO() {
        return DEVICEINFO;
    }

    public void setDEVICEINFO(String DEVICEINFO) {
        this.DEVICEINFO = DEVICEINFO;
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

    public String getDEVICETYPE() {
        return DEVICETYPE;
    }

    public void setDEVICETYPE(String DEVICETYPE) {
        this.DEVICETYPE = DEVICETYPE;
    }
}
