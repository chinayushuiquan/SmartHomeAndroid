package kap.com.smarthome.android.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;

/**
 * Created by yushq on 2017/9/25 0025.
 */

@Entity
public class RelayBox implements Serializable{

    private static final long serialVersionUID=1L;

    @Id(autoincrement =  true)
    private Long ID;

    @Index(unique  = true)
    private String  GUID ;

    @Unique
    private String  BOX_ID;

    private String NAME;

    private String IP;

    private int PORT;

    private String PLATFORM_ADDR;

    private int  PLATFORM_PORT;

    private String MASK;

    private int RELAY_ORDER;

    private String HARDWARE_VERSION;

    private String SOFTWARE_VERSION;

    private String MACHINE_CODE;

    @Generated(hash = 1990086116)
    public RelayBox(Long ID, String GUID, String BOX_ID, String NAME, String IP,
            int PORT, String PLATFORM_ADDR, int PLATFORM_PORT, String MASK,
            int RELAY_ORDER, String HARDWARE_VERSION, String SOFTWARE_VERSION,
            String MACHINE_CODE) {
        this.ID = ID;
        this.GUID = GUID;
        this.BOX_ID = BOX_ID;
        this.NAME = NAME;
        this.IP = IP;
        this.PORT = PORT;
        this.PLATFORM_ADDR = PLATFORM_ADDR;
        this.PLATFORM_PORT = PLATFORM_PORT;
        this.MASK = MASK;
        this.RELAY_ORDER = RELAY_ORDER;
        this.HARDWARE_VERSION = HARDWARE_VERSION;
        this.SOFTWARE_VERSION = SOFTWARE_VERSION;
        this.MACHINE_CODE = MACHINE_CODE;
    }

    @Generated(hash = 396113921)
    public RelayBox() {
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

    public String getBOX_ID() {
        return this.BOX_ID;
    }

    public void setBOX_ID(String BOX_ID) {
        this.BOX_ID = BOX_ID;
    }

    public String getNAME() {
        return this.NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getIP() {
        return this.IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public int getPORT() {
        return this.PORT;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    public String getPLATFORM_ADDR() {
        return this.PLATFORM_ADDR;
    }

    public void setPLATFORM_ADDR(String PLATFORM_ADDR) {
        this.PLATFORM_ADDR = PLATFORM_ADDR;
    }

    public int getPLATFORM_PORT() {
        return this.PLATFORM_PORT;
    }

    public void setPLATFORM_PORT(int PLATFORM_PORT) {
        this.PLATFORM_PORT = PLATFORM_PORT;
    }

    public String getMASK() {
        return this.MASK;
    }

    public void setMASK(String MASK) {
        this.MASK = MASK;
    }

    public int getRELAY_ORDER() {
        return this.RELAY_ORDER;
    }

    public void setRELAY_ORDER(int RELAY_ORDER) {
        this.RELAY_ORDER = RELAY_ORDER;
    }

    public String getHARDWARE_VERSION() {
        return this.HARDWARE_VERSION;
    }

    public void setHARDWARE_VERSION(String HARDWARE_VERSION) {
        this.HARDWARE_VERSION = HARDWARE_VERSION;
    }

    public String getSOFTWARE_VERSION() {
        return this.SOFTWARE_VERSION;
    }

    public void setSOFTWARE_VERSION(String SOFTWARE_VERSION) {
        this.SOFTWARE_VERSION = SOFTWARE_VERSION;
    }

    public String getMACHINE_CODE() {
        return this.MACHINE_CODE;
    }

    public void setMACHINE_CODE(String MACHINE_CODE) {
        this.MACHINE_CODE = MACHINE_CODE;
    }



   
}
