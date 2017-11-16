package kap.com.smarthome.android.communication.bean.base;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by yushq on 2017/6/1 0001.
 * JosnHead 头
 */

public class JsonHeadBase implements Serializable {

    //系统当前时间戳
    @JSONField(name = "TIMESTAMP", ordinal = 1)
    private long TIMESTAMP;

    //未知字段
    //系统当前时间戳
    @JSONField(name = "SERVICEID", ordinal = 2)
    private String SERVICEID;

    //协议版本号
    @JSONField(name = "VERSION", ordinal = 3)
    private String VERSION;

    //流水号
    @JSONField(name = "SERIALNUM", ordinal = 4)
    private long SERIALNUM;

    //该消息重复次数
    @JSONField(name = "REPEATCOUNT", ordinal =5)
    private int REPEATCOUNT ;

    //用户ID
    @JSONField(name = "USERID", ordinal =6)
    private String USERID;

    //设备ID
    @JSONField(name = "DEVICEID", ordinal =7)
    private String DEVICEID;

    public JsonHeadBase() {

    }

    public JsonHeadBase(long TIMESTAMP, String SERVICEID, String VERSION, long SERIALNUM, int REPEATCOUNT, String USERID, String DEVICEID) {
        this.TIMESTAMP = TIMESTAMP;
        this.SERVICEID = SERVICEID;
        this.VERSION = VERSION;
        this.SERIALNUM = SERIALNUM;
        this.REPEATCOUNT = REPEATCOUNT;
        this.USERID = USERID;
        this.DEVICEID = DEVICEID;
    }

    public long getTIMESTAMP() {
        return TIMESTAMP;
    }

    public void setTIMESTAMP(long TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }

    public String getSERVICEID() {
        return SERVICEID;
    }

    public void setSERVICEID(String SERVICEID) {
        this.SERVICEID = SERVICEID;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public long getSERIALNUM() {
        return SERIALNUM;
    }

    public void setSERIALNUM(long SERIALNUM) {
        this.SERIALNUM = SERIALNUM;
    }

    public int getREPEATCOUNT() {
        return REPEATCOUNT;
    }

    public void setREPEATCOUNT(int REPEATCOUNT) {
        this.REPEATCOUNT = REPEATCOUNT;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getDEVICEID() {
        return DEVICEID;
    }

    public void setDEVICEID(String DEVICEID) {
        this.DEVICEID = DEVICEID;
    }


    @Override
    public String toString() {
        return "JsonHeadBase{" +
                "TIMESTAMP=" + TIMESTAMP +
                ", SERVICEID='" + SERVICEID + '\'' +
                ", VERSION='" + VERSION + '\'' +
                ", SERIALNUM='" + SERIALNUM + '\'' +
                ", REPEATCOUNT=" + REPEATCOUNT +
                ", USERID='" + USERID + '\'' +
                ", DEVICEID='" + DEVICEID + '\'' +
                '}';
    }

    private static int SERIAL_NUM = 0;

    /**
     * 流水号加一：主动发送msg的时候使用,中控器的流水号（0x80000000到0xFFFFFFFF）
     */
    public void updateSERIALNUM(){
        //setSERIALNUM(Integer.toHexString(SERIAL_NUM));
        setSERIALNUM(SERIAL_NUM);
        SERIAL_NUM += 1;
        /*if(SERIAL_NUM>0xFFFFFFFF){
            SERIAL_NUM=0x80000000;
        }*/

    }

}
