package kap.com.smarthome.android.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by  on 2017/9/21 0021.
 */
@Entity
public class Devices  implements Serializable{

    private static final long serialVersionUID=1L;

    @Id (autoincrement =  true)
    private Long Id;

    @Index(unique  = true)
    @NotNull
    private String  GUID ;

    private String ROOM_GUID;

    @NotNull
    private String DEVICE_ID;

    @NotNull
    private String RELAY_ID;

    private int TYPE;

    private int SUB_TYPE;

    private String VALUE;

    @NotNull
    private String NAME;

    private int DEVICE_ORDER;

    private int USE_FREQUENCY;


    @Generated(hash = 383384470)
    public Devices(Long Id, @NotNull String GUID, String ROOM_GUID,
            @NotNull String DEVICE_ID, @NotNull String RELAY_ID, int TYPE,
            int SUB_TYPE, String VALUE, @NotNull String NAME, int DEVICE_ORDER,
            int USE_FREQUENCY) {
        this.Id = Id;
        this.GUID = GUID;
        this.ROOM_GUID = ROOM_GUID;
        this.DEVICE_ID = DEVICE_ID;
        this.RELAY_ID = RELAY_ID;
        this.TYPE = TYPE;
        this.SUB_TYPE = SUB_TYPE;
        this.VALUE = VALUE;
        this.NAME = NAME;
        this.DEVICE_ORDER = DEVICE_ORDER;
        this.USE_FREQUENCY = USE_FREQUENCY;
    }

    @Generated(hash = 597445211)
    public Devices() {
    }

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getGUID() {
        return this.GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getROOM_GUID() {
        return this.ROOM_GUID;
    }

    public void setROOM_GUID(String ROOM_GUID) {
        this.ROOM_GUID = ROOM_GUID;
    }

    public String getDEVICE_ID() {
        return this.DEVICE_ID;
    }

    public void setDEVICE_ID(String DEVICE_ID) {
        this.DEVICE_ID = DEVICE_ID;
    }

    public String getRELAY_ID() {
        return this.RELAY_ID;
    }

    public void setRELAY_ID(String RELAY_ID) {
        this.RELAY_ID = RELAY_ID;
    }

    public int getTYPE() {
        return this.TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }

    public int getSUB_TYPE() {
        return this.SUB_TYPE;
    }

    public void setSUB_TYPE(int SUB_TYPE) {
        this.SUB_TYPE = SUB_TYPE;
    }

    public String getVALUE() {
        return this.VALUE;
    }

    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }

    public String getNAME() {
        return this.NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public int getDEVICE_ORDER() {
        return this.DEVICE_ORDER;
    }

    public void setDEVICE_ORDER(int DEVICE_ORDER) {
        this.DEVICE_ORDER = DEVICE_ORDER;
    }

    public int getUSE_FREQUENCY() {
        return this.USE_FREQUENCY;
    }

    public void setUSE_FREQUENCY(int USE_FREQUENCY) {
        this.USE_FREQUENCY = USE_FREQUENCY;
    }


    @Override
    public String toString() {
        return "Devices{" +
                "Id=" + Id +
                ", GUID='" + GUID + '\'' +
                ", ROOM_GUID='" + ROOM_GUID + '\'' +
                ", DEVICE_ID='" + DEVICE_ID + '\'' +
                ", RELAY_ID='" + RELAY_ID + '\'' +
                ", TYPE=" + TYPE +
                ", SUB_TYPE=" + SUB_TYPE +
                ", VALUE='" + VALUE + '\'' +
                ", NAME='" + NAME + '\'' +
                ", DEVICE_ORDER=" + DEVICE_ORDER +
                ", USE_FREQUENCY=" + USE_FREQUENCY +
                '}';
    }
}
