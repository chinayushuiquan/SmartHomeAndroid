package kap.com.smarthome.android.data.bean;

import android.support.annotation.IntDef;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/19 0019.
 * 房间表的greenDao实体
 */

@Entity
public class Room  implements Serializable{

    private static final long serialVersionUID=1L;

    @Id (autoincrement =  true)
    private Long ID;

    @Index(unique  = true)
    private String  GUID ;

    private String  NAME;

    private int   ROOM_ORDER;

    private int   TYPE;

    @Generated(hash = 1384300547)
    public Room(Long ID, String GUID, String NAME, int ROOM_ORDER, int TYPE) {
        this.ID = ID;
        this.GUID = GUID;
        this.NAME = NAME;
        this.ROOM_ORDER = ROOM_ORDER;
        this.TYPE = TYPE;
    }

    @Generated(hash = 703125385)
    public Room() {
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

    public String getNAME() {
        return this.NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public int getROOM_ORDER() {
        return this.ROOM_ORDER;
    }

    public void setROOM_ORDER(int ROOM_ORDER) {
        this.ROOM_ORDER = ROOM_ORDER;
    }

    public int getTYPE() {
        return this.TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }

    @Override
    public String toString() {
        return "Room{" +
                "ID=" + ID +
                ", GUID='" + GUID + '\'' +
                ", NAME='" + NAME + '\'' +
                ", ROOM_ORDER=" + ROOM_ORDER +
                ", TYPE=" + TYPE +
                '}';
    }
}
