package kap.com.smarthome.android.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/10/26 0026.
 *
 1.	guid是数据库用户表存储的唯一索引，该值为不重复的32位的16进制字符串,不为空。
 2.	scene_guid 绑定场景的guid
 3.	device_id 该设备的id号(绑定的场景的设备都是已经添加到设备表中的设备， 该id好即是该设备的设备id号)
 4.	device_stauts设备的控制状态描述（例如”开” ”关”）
 5.	device_value设备的状态值（例如”00FF” 、红外码值）
 6.	relaybox_id设备关联的中继盒子id(如果该设备有一对一关联中继盒子)
 7.	type设备的类型（标志出是什么设备类型，例如窗帘、可调灯等）,更据类型才能在界面上显示相应的图标
 */


@Entity
public class ScenesDevice implements Serializable{

    private static final long serialVersionUID=1L;

    @Id(autoincrement =  true)
    private Long ID;

    private String GUID;

    private String SCENES_ID ;

    private String DEVICE_ID;

    private String DEVICE_NAME;

    private String DEVICE_INFO;

    private String DEVICE_VALUE;

    private String RELAYBOX_ID;

    private int TYPE;

    @Generated(hash = 1156214952)
    public ScenesDevice(Long ID, String GUID, String SCENES_ID, String DEVICE_ID,
            String DEVICE_NAME, String DEVICE_INFO, String DEVICE_VALUE,
            String RELAYBOX_ID, int TYPE) {
        this.ID = ID;
        this.GUID = GUID;
        this.SCENES_ID = SCENES_ID;
        this.DEVICE_ID = DEVICE_ID;
        this.DEVICE_NAME = DEVICE_NAME;
        this.DEVICE_INFO = DEVICE_INFO;
        this.DEVICE_VALUE = DEVICE_VALUE;
        this.RELAYBOX_ID = RELAYBOX_ID;
        this.TYPE = TYPE;
    }

    @Generated(hash = 327292061)
    public ScenesDevice() {
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

    public String getDEVICE_NAME() {
        return this.DEVICE_NAME;
    }

    public void setDEVICE_NAME(String DEVICE_NAME) {
        this.DEVICE_NAME = DEVICE_NAME;
    }

    public String getDEVICE_INFO() {
        return this.DEVICE_INFO;
    }

    public void setDEVICE_INFO(String DEVICE_INFO) {
        this.DEVICE_INFO = DEVICE_INFO;
    }

    public String getDEVICE_VALUE() {
        return this.DEVICE_VALUE;
    }

    public void setDEVICE_VALUE(String DEVICE_VALUE) {
        this.DEVICE_VALUE = DEVICE_VALUE;
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
