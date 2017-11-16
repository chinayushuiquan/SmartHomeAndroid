package kap.com.smarthome.android.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/10/26 0026.
 * guid
 scene_guid
 device_id
 trigger_stauts
 trigger_value
 relaybox_id
 type

 */

@Entity
public class ScenesTrigger implements Serializable {

    private static final long serialVersionUID=1L;

    @Id(autoincrement =  true)
    private Long ID;

    private String  GUID;

    private String  SCENES_ID ;

    private String DEVICE_ID;

    private String TRIGGER_INFO;

    private String DEVICE_NAME;

    private String TRIGGER_VALUE;

    private String RELAYBOX_ID;

    private int TYPE;

    @Generated(hash = 1617360928)
    public ScenesTrigger(Long ID, String GUID, String SCENES_ID, String DEVICE_ID,
            String TRIGGER_INFO, String DEVICE_NAME, String TRIGGER_VALUE,
            String RELAYBOX_ID, int TYPE) {
        this.ID = ID;
        this.GUID = GUID;
        this.SCENES_ID = SCENES_ID;
        this.DEVICE_ID = DEVICE_ID;
        this.TRIGGER_INFO = TRIGGER_INFO;
        this.DEVICE_NAME = DEVICE_NAME;
        this.TRIGGER_VALUE = TRIGGER_VALUE;
        this.RELAYBOX_ID = RELAYBOX_ID;
        this.TYPE = TYPE;
    }

    @Generated(hash = 834360800)
    public ScenesTrigger() {
    }

    public Long getID() {
        return this.ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getGUID() {
        return this.GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getSCENES_ID() {
        return this.SCENES_ID;
    }

    public void setSCENES_ID(String SCENES_ID) {
        this.SCENES_ID = SCENES_ID;
    }

    public String getDEVICE_ID() {
        return this.DEVICE_ID;
    }

    public void setDEVICE_ID(String DEVICE_ID) {
        this.DEVICE_ID = DEVICE_ID;
    }

    public String getTRIGGER_INFO() {
        return this.TRIGGER_INFO;
    }

    public void setTRIGGER_INFO(String TRIGGER_INFO) {
        this.TRIGGER_INFO = TRIGGER_INFO;
    }

    public String getDEVICE_NAME() {
        return this.DEVICE_NAME;
    }

    public void setDEVICE_NAME(String DEVICE_NAME) {
        this.DEVICE_NAME = DEVICE_NAME;
    }

    public String getTRIGGER_VALUE() {
        return this.TRIGGER_VALUE;
    }

    public void setTRIGGER_VALUE(String TRIGGER_VALUE) {
        this.TRIGGER_VALUE = TRIGGER_VALUE;
    }

    public String getRELAYBOX_ID() {
        return this.RELAYBOX_ID;
    }

    public void setRELAYBOX_ID(String RELAYBOX_ID) {
        this.RELAYBOX_ID = RELAYBOX_ID;
    }

    public int getTYPE() {
        return this.TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }



}
