package kap.com.smarthome.android.communication.bean.base.DATABean;

import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.io.Serializable;
import java.util.List;

import kap.com.smarthome.android.data.bean.Scenes;
import kap.com.smarthome.android.data.bean.ScenesTrigger;

/**
 * Created by yushq on 2017/10/27 0027.
 *
 * 通过该实例把场景数据转换成服务器需要的格式
 *
 *
 * "DATA":[{"ID":"122","SCENENAME":"场景名称","DEVICENUMBER":"1","SCENEICON":"1","TRIGGERNUMBER":"1","TYPE":"1","TRIGGERSTATUS":"1",
 * "SCENEDEVICE":[{"DEVICEID":"20a2061907d2476b","SCENEDEVICEGUID":"20a2061907d2476b","DEVICEINFO":"9","DEVICETYPE":"2","DEVICEVALUE":"设备值","RELAYBOXID":"1001116016400001"}],
 * "SCENETRIGGER":[{"DEVICEID":"20a2061907d2476b","SCENETRIGGERGUID":"20a2061907d2476b","SCENETRIGGERTYPE":"2","TRIGGERINFO":"2","TRIGGERVALUE":"2","RELAYBOXID":"1001116016400001"}]}]}
 *
 */

public class ScenesData implements Serializable {

    private static final long serialVersionUID=1L;

    private String ID;

    private String SCENENAME;

    //控制设备个数
    private String DEVICENUMBER;

    //条件个数
    private String TRIGGERNUMBER;

    private String TRIGGERSTATUS;

    private String  TYPE;

    private String  SCENEICON;


    private List<ScenesDevicesData> SCENEDEVICE;

    private List<ScenesTriggerData> SCENETRIGGER;

    public ScenesData() {
    }


    public ScenesData(String ID, String SCENENAME, String DEVICENUMBER, String TRIGGERNUMBER, String TRIGGERSTATUS,
                      String TYPE, String SCENEICON, List<ScenesDevicesData> SCENEDEVICE, List<ScenesTriggerData> SCENETRIGGER) {
        this.ID = ID;
        this.SCENENAME = SCENENAME;
        this.DEVICENUMBER = DEVICENUMBER;
        this.TRIGGERNUMBER = TRIGGERNUMBER;
        this.TRIGGERSTATUS = TRIGGERSTATUS;
        this.TYPE = TYPE;
        this.SCENEICON = SCENEICON;
        this.SCENEDEVICE = SCENEDEVICE;
        this.SCENETRIGGER = SCENETRIGGER;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSCENENAME() {
        return SCENENAME;
    }

    public void setSCENENAME(String SCENENAME) {
        this.SCENENAME = SCENENAME;
    }

    public String getDEVICENUMBER() {
        return DEVICENUMBER;
    }

    public void setDEVICENUMBER(String DEVICENUMBER) {
        this.DEVICENUMBER = DEVICENUMBER;
    }

    public String getTRIGGERNUMBER() {
        return TRIGGERNUMBER;
    }

    public void setTRIGGERNUMBER(String TRIGGERNUMBER) {
        this.TRIGGERNUMBER = TRIGGERNUMBER;
    }

    public String getTRIGGERSTATUS() {
        return TRIGGERSTATUS;
    }

    public void setTRIGGERSTATUS(String TRIGGERSTATUS) {
        this.TRIGGERSTATUS = TRIGGERSTATUS;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getSCENEICON() {
        return SCENEICON;
    }

    public void setSCENEICON(String SCENEICON) {
        this.SCENEICON = SCENEICON;
    }

    public List<ScenesDevicesData> getSCENEDEVICE() {
        return SCENEDEVICE;
    }

    public void setSCENEDEVICE(List<ScenesDevicesData> SCENEDEVICE) {
        this.SCENEDEVICE = SCENEDEVICE;
    }

    public List<ScenesTriggerData> getSCENETRIGGER() {
        return SCENETRIGGER;
    }

    public void setSCENETRIGGER(List<ScenesTriggerData> SCENETRIGGER) {
        this.SCENETRIGGER = SCENETRIGGER;
    }


}
