package kap.com.smarthome.android.communication.bean.base.DATABean;

import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by Administrator on 2017/10/18 0018.
 *
 * 服务器保存字段
 *
 *  id	String	是	记录的guid
 ROOMID	String	是	房间guid主键
 DEVICEID	String	是	设备uid
 RELAYBOXID	String	是	盒子uid
 TYPE	Int	是	设备类型
 SUBTYPE	Int	是	父类型
 VALUE	String	是	发送值保留字段
 NAME	String	是	界面显示文本/设备名称
 DEVICEORDER	Int	否	界面显示序号
 USEFREQUENCY	Int	是	使用频率
 *
 */

public class DeviceData {

    private String  ID ;

    private String ROOMID;

    private String DEVICEID;

    private String RELAYBOXID;

    private String TYPE;

    private String SUBTYPE;

    private String VALUE;

    private String NAME;

    private String DEVICEORDER;

    private String USEFREQUENCY;

    public DeviceData() {

    }

    public DeviceData(String ID, String ROOMID, String DEVICEID, String RELAYBOXID, String TYPE, String SUBTYPE, String VALUE, String NAME, String DEVICEORDER, String USEFREQUENCY) {
        this.ID = ID;
        this.ROOMID = ROOMID;
        this.DEVICEID = DEVICEID;
        this.RELAYBOXID = RELAYBOXID;
        this.TYPE = TYPE;
        this.SUBTYPE = SUBTYPE;
        this.VALUE = VALUE;
        this.NAME = NAME;
        this.DEVICEORDER = DEVICEORDER;
        this.USEFREQUENCY = USEFREQUENCY;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getROOMID() {
        return ROOMID;
    }

    public void setROOMID(String ROOMID) {
        this.ROOMID = ROOMID;
    }

    public String getDEVICEID() {
        return DEVICEID;
    }

    public void setDEVICEID(String DEVICEID) {
        this.DEVICEID = DEVICEID;
    }

    public String getRELAYBOXID() {
        return RELAYBOXID;
    }

    public void setRELAYBOXID(String RELAYBOXID) {
        this.RELAYBOXID = RELAYBOXID;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getSUBTYPE() {
        return SUBTYPE;
    }

    public void setSUBTYPE(String SUBTYPE) {
        this.SUBTYPE = SUBTYPE;
    }

    public String getVALUE() {
        return VALUE;
    }

    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getDEVICEORDER() {
        return DEVICEORDER;
    }

    public void setDEVICEORDER(String DEVICEORDER) {
        this.DEVICEORDER = DEVICEORDER;
    }

    public String getUSEFREQUENCY() {
        return USEFREQUENCY;
    }

    public void setUSEFREQUENCY(String USEFREQUENCY) {
        this.USEFREQUENCY = USEFREQUENCY;
    }

    @Override
    public String toString() {
        return "DeviceData{" +
                "ID='" + ID + '\'' +
                ", ROOMID='" + ROOMID + '\'' +
                ", DEVICEID='" + DEVICEID + '\'' +
                ", RELAYBOXID='" + RELAYBOXID + '\'' +
                ", TYPE=" + TYPE +
                ", SUBTYPE=" + SUBTYPE +
                ", VALUE='" + VALUE + '\'' +
                ", NAME='" + NAME + '\'' +
                ", DEVICEORDER=" + DEVICEORDER +
                ", USEFREQUENCY=" + USEFREQUENCY +
                '}';
    }
}
