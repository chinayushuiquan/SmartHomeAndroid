package kap.com.smarthome.android.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by  on 2017/10/25 0025.
 */

@Entity
public class IRKey implements Serializable {

    private static final long serialVersionUID=1L;

    @Id(autoincrement =  true)
    private Long ID;

    @Index(unique  = true)
    private String  GUID ;

    private String  DEVICE_ID;

    private int  INDEX;

    private String KEY1;

    private String KEY2;

    private String BUTTON_NAME;

    private String USER_ID;

    @Generated(hash = 914282208)
    public IRKey(Long ID, String GUID, String DEVICE_ID, int INDEX, String KEY1,
            String KEY2, String BUTTON_NAME, String USER_ID) {
        this.ID = ID;
        this.GUID = GUID;
        this.DEVICE_ID = DEVICE_ID;
        this.INDEX = INDEX;
        this.KEY1 = KEY1;
        this.KEY2 = KEY2;
        this.BUTTON_NAME = BUTTON_NAME;
        this.USER_ID = USER_ID;
    }

    @Generated(hash = 2101497125)
    public IRKey() {
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

    public String getDEVICE_ID() {
        return this.DEVICE_ID;
    }

    public void setDEVICE_ID(String DEVICE_ID) {
        this.DEVICE_ID = DEVICE_ID;
    }

    public int getINDEX() {
        return this.INDEX;
    }

    public void setINDEX(int INDEX) {
        this.INDEX = INDEX;
    }

    public String getKEY1() {
        return this.KEY1;
    }

    public void setKEY1(String KEY1) {
        this.KEY1 = KEY1;
    }

    public String getKEY2() {
        return this.KEY2;
    }

    public void setKEY2(String KEY2) {
        this.KEY2 = KEY2;
    }

    public String getBUTTON_NAME() {
        return this.BUTTON_NAME;
    }

    public void setBUTTON_NAME(String BUTTON_NAME) {
        this.BUTTON_NAME = BUTTON_NAME;
    }

    public String getUSER_ID() {
        return this.USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }


    @Override
    public String toString() {
        return "IRKey{" +
                "ID=" + ID +
                ", GUID='" + GUID + '\'' +
                ", DEVICE_ID='" + DEVICE_ID + '\'' +
                ", INDEX=" + INDEX +
                ", KEY1='" + KEY1 + '\'' +
                ", KEY2='" + KEY2 + '\'' +
                ", BUTTON_NAME='" + BUTTON_NAME + '\'' +
                ", USER_ID='" + USER_ID + '\'' +
                '}';
    }
}
