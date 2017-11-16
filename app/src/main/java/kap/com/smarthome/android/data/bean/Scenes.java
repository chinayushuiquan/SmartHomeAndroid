package kap.com.smarthome.android.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yushq on 2017/10/26 0026.
 *
 * guid
 scene_id
 scene_name
 scene_icon
 user_id
 device_number
 trigger_number
 trigger_status
 type
 *
 */

@Entity
public class Scenes implements Serializable {

    private static final long serialVersionUID=1L;

    @Id(autoincrement =  true)
    private Long ID;

    @Index(unique  = true)
    private String  GUID ;

    private String SCENE_ID;

    private String SCENE_NAME;

    private int SCENE_ICON;

    private String USER_ID;

    private String DEVICE_NUMBER;

    private String TRIGGER_NUMBER;

    private int TRIGGER_STATUS;

    private int type;

    @Generated(hash = 526083234)
    public Scenes(Long ID, String GUID, String SCENE_ID, String SCENE_NAME,
            int SCENE_ICON, String USER_ID, String DEVICE_NUMBER,
            String TRIGGER_NUMBER, int TRIGGER_STATUS, int type) {
        this.ID = ID;
        this.GUID = GUID;
        this.SCENE_ID = SCENE_ID;
        this.SCENE_NAME = SCENE_NAME;
        this.SCENE_ICON = SCENE_ICON;
        this.USER_ID = USER_ID;
        this.DEVICE_NUMBER = DEVICE_NUMBER;
        this.TRIGGER_NUMBER = TRIGGER_NUMBER;
        this.TRIGGER_STATUS = TRIGGER_STATUS;
        this.type = type;
    }

    @Generated(hash = 1003349671)
    public Scenes() {
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

    public String getSCENE_ID() {
        return this.SCENE_ID;
    }

    public void setSCENE_ID(String SCENE_ID) {
        this.SCENE_ID = SCENE_ID;
    }

    public String getSCENE_NAME() {
        return this.SCENE_NAME;
    }

    public void setSCENE_NAME(String SCENE_NAME) {
        this.SCENE_NAME = SCENE_NAME;
    }

    public int getSCENE_ICON() {
        return this.SCENE_ICON;
    }

    public void setSCENE_ICON(int SCENE_ICON) {
        this.SCENE_ICON = SCENE_ICON;
    }

    public String getUSER_ID() {
        return this.USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getDEVICE_NUMBER() {
        return this.DEVICE_NUMBER;
    }

    public void setDEVICE_NUMBER(String DEVICE_NUMBER) {
        this.DEVICE_NUMBER = DEVICE_NUMBER;
    }

    public String getTRIGGER_NUMBER() {
        return this.TRIGGER_NUMBER;
    }

    public void setTRIGGER_NUMBER(String TRIGGER_NUMBER) {
        this.TRIGGER_NUMBER = TRIGGER_NUMBER;
    }

    public int getTRIGGER_STATUS() {
        return this.TRIGGER_STATUS;
    }

    public void setTRIGGER_STATUS(int TRIGGER_STATUS) {
        this.TRIGGER_STATUS = TRIGGER_STATUS;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

   
}
