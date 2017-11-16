package kap.com.smarthome.android.communication.bean.extend.UDP;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

import kap.com.smarthome.android.communication.bean.base.DATABean.UDPScenesDeviceData;
import kap.com.smarthome.android.communication.bean.base.DATABean.UDPScenesTriggerData;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPRequestBodyBase;

/**
 * Created by Administrator on 2017/10/27 0027.
 *
 *
 * “INSTP”:”SENDSCENEDATAREQ” ,
 “SCENEID”: “1234”, //场景ID号
 “SCENENAME”:”scene1”,
 “USERID”:”1234”,
 “DEVICENUMBER”: 2,
 “TRIGGERNUMBER”: 2,
 “TRIGGERSTATUS”: 0,
 “EXCUTEDEVICE”:[ {“DEVICEID”:”1234”, “DEVICEVALUE” :”1234”, “RELAYBOXID”:”1234”},
 {“DEVICEID”:”1234”, “DEVICEVALUE” :”1234”, “RELAYBOXID”:”1234”}],
 “TRIGGERDEVICE”:[ {“DEVICEID”:”1234”, “TRIGGERVALUE” :”1234”, “RELAYBOXID”:”1234”},
 {“DEVICEID”:”1234”, “TRIGGERVALUE” :”1234”, “RELAYBOXID”:”1234”}]


 @JSONField(name = "INSTP", ordinal = 1)
 protected String INSTP;

 @JSONField(name = "IP", ordinal = 2)
 protected String IP;

 @JSONField(name = "TIMESTAMP", ordinal = 3)
 protected long TIMESTAMP;

 @JSONField(name = "DEVICEID", ordinal = 4)
 protected String DEVICEID;

 @JSONField(name = "BOXID", ordinal = 5)
 protected String BOXID;

 @JSONField(name = "PROTOCOLVER", ordinal = 6)
 protected String PROTOCOLVER;

 @JSONField(name = "RESULT", ordinal = 7)
 protected int  RESULT;
 *
 */

public class UDPAddScenesRequestBody  extends UDPRequestBodyBase {

    @JSONField(name = "SCENEID", ordinal = 8)
    private String SCENEID ;

    @JSONField(name = "DEVICENUMBER", ordinal = 11)
    private int  DEVICENUMBER ;

    @JSONField(name = "TRIGGERNUMBER", ordinal = 12)
    private int  TRIGGERNUMBER ;

    @JSONField(name = "TRIGGERSTATUS", ordinal = 13)
    private int  TRIGGERSTATUS ;

    @JSONField(name = "EXCUTEDEVICE", ordinal = 14)
    private List<UDPScenesDeviceData> EXCUTEDEVICE;

    @JSONField(name = "TRIGGERDEVICE", ordinal = 15)
    private List<UDPScenesTriggerData> TRIGGERDEVICE;


    public String getSCENEID() {
        return SCENEID;
    }

    public void setSCENEID(String SCENEID) {
        this.SCENEID = SCENEID;
    }

    public int getDEVICENUMBER() {
        return DEVICENUMBER;
    }

    public void setDEVICENUMBER(int DEVICENUMBER) {
        this.DEVICENUMBER = DEVICENUMBER;
    }

    public int getTRIGGERNUMBER() {
        return TRIGGERNUMBER;
    }

    public void setTRIGGERNUMBER(int TRIGGERNUMBER) {
        this.TRIGGERNUMBER = TRIGGERNUMBER;
    }

    public int getTRIGGERSTATUS() {
        return TRIGGERSTATUS;
    }

    public void setTRIGGERSTATUS(int TRIGGERSTATUS) {
        this.TRIGGERSTATUS = TRIGGERSTATUS;
    }

    public List<UDPScenesDeviceData> getEXCUTEDEVICE() {
        return EXCUTEDEVICE;
    }

    public void setEXCUTEDEVICE(List<UDPScenesDeviceData> EXCUTEDEVICE) {
        this.EXCUTEDEVICE = EXCUTEDEVICE;
    }

    public List<UDPScenesTriggerData> getTRIGGERDEVICE() {
        return TRIGGERDEVICE;
    }

    public void setTRIGGERDEVICE(List<UDPScenesTriggerData> TRIGGERDEVICE) {
        this.TRIGGERDEVICE = TRIGGERDEVICE;
    }
}
