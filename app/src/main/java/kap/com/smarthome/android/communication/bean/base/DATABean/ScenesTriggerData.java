package kap.com.smarthome.android.communication.bean.base.DATABean;

/**
 * Created by yushq on 2017/11/3 0003.
 *
 * SCENETRIGGER":[{"DEVICEID":"20a2061907d2476b","SCENETRIGGERGUID":"20a2061907d2476b",
 * "SCENETRIGGERTYPE":"2","TRIGGERINFO":"2","TRIGGERVALUE":"2","RELAYBOXID":"1001116016400001"}
 *
 */

public class ScenesTriggerData {

    private String DEVICEID;

    private String SCENETRIGGERGUID;

    private String SCENETRIGGERTYPE;

    private String TRIGGERINFO;

    private String TRIGGERVALUE;

    private String RELAYBOXID;


    public ScenesTriggerData() {
    }

    public ScenesTriggerData(String DEVICEID, String SCENETRIGGERGUID, String SCENETRIGGERTYPE, String TRIGGERINFO, String TRIGGERVALUE, String RELAYBOXID) {
        this.DEVICEID = DEVICEID;
        this.SCENETRIGGERGUID = SCENETRIGGERGUID;
        this.SCENETRIGGERTYPE = SCENETRIGGERTYPE;
        this.TRIGGERINFO = TRIGGERINFO;
        this.TRIGGERVALUE = TRIGGERVALUE;
        this.RELAYBOXID = RELAYBOXID;
    }

    public String getDEVICEID() {
        return DEVICEID;
    }

    public void setDEVICEID(String DEVICEID) {
        this.DEVICEID = DEVICEID;
    }

    public String getSCENETRIGGERGUID() {
        return SCENETRIGGERGUID;
    }

    public void setSCENETRIGGERGUID(String SCENETRIGGERGUID) {
        this.SCENETRIGGERGUID = SCENETRIGGERGUID;
    }

    public String getSCENETRIGGERTYPE() {
        return SCENETRIGGERTYPE;
    }

    public void setSCENETRIGGERTYPE(String SCENETRIGGERTYPE) {
        this.SCENETRIGGERTYPE = SCENETRIGGERTYPE;
    }

    public String getTRIGGERINFO() {
        return TRIGGERINFO;
    }

    public void setTRIGGERINFO(String TRIGGERINFO) {
        this.TRIGGERINFO = TRIGGERINFO;
    }

    public String getTRIGGERVALUE() {
        return TRIGGERVALUE;
    }

    public void setTRIGGERVALUE(String TRIGGERVALUE) {
        this.TRIGGERVALUE = TRIGGERVALUE;
    }

    public String getRELAYBOXID() {
        return RELAYBOXID;
    }

    public void setRELAYBOXID(String RELAYBOXID) {
        this.RELAYBOXID = RELAYBOXID;
    }
}
