package kap.com.smarthome.android.communication.bean.base.UDP;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by yushq on 2017/6/1 0001.
 */

public class UDPResponseBodyBase implements Serializable {

    @JSONField(name = "INSTP", ordinal = 1)
    protected String INSTP;

    @JSONField(name = "IP", ordinal = 2)
    protected String IP;

    @JSONField(name = "TIMESTAMP", ordinal = 3)
    protected long TIMESTAMP;

    @JSONField(name = "DEVICEID", ordinal = 4)
    protected String DEVICEID;

    @JSONField(name = "DEVICEID", ordinal = 5)
    protected String BOXID;

    @JSONField(name = "DEVICEID", ordinal = 6)
    protected String PROTOCOLVER;

    @JSONField(name = "DEVICEID", ordinal = 7)
    protected int  RESULT;

    @JSONField(name = "IRCODE", ordinal = 8)
    protected String  IRCODE;

    @JSONField(name = "SECURITYDEVICEID", ordinal = 9)
    protected String  SECURITYDEVICEID;

    @JSONField(name = "VALUE", ordinal = 10)
    protected String  VALUE;


    public UDPResponseBodyBase() {
    }

    public UDPResponseBodyBase(String INSTP, String IP, long TIMESTAMP, String DEVICEID, String BOXID, String PROTOCOLVER, int RESULT, String IRCODE, String SECURITYDEVICEID, String VALUE) {
        this.INSTP = INSTP;
        this.IP = IP;
        this.TIMESTAMP = TIMESTAMP;
        this.DEVICEID = DEVICEID;
        this.BOXID = BOXID;
        this.PROTOCOLVER = PROTOCOLVER;
        this.RESULT = RESULT;
        this.IRCODE = IRCODE;
        this.SECURITYDEVICEID = SECURITYDEVICEID;
        this.VALUE = VALUE;
    }

    public void setINSTP(String INSTP){
        this.INSTP = INSTP;
    }

    public String getINSTP(){
        return this.INSTP;
    }

    public void setIP(String IP){
        this.IP = IP;
    }

    public String getIP(){
        return this.IP;
    }

    public long getTIMESTAMP() {
        return TIMESTAMP;
    }

    public void setTIMESTAMP(long TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }
    public String getDEVICEID() {
        return DEVICEID;
    }

    public void setDEVICEID(String DEVICEID) {
        this.DEVICEID = DEVICEID;
    }

    public String getBOXID() {
        return BOXID;
    }

    public void setBOXID(String BOXID) {
        this.BOXID = BOXID;
    }

    public String getPROTOCOLVER() {
        return PROTOCOLVER;
    }

    public void setPROTOCOLVER(String PROTOCOLVER) {
        this.PROTOCOLVER = PROTOCOLVER;
    }

    public int getRESULT() {
        return RESULT;
    }

    public void setRESULT(int RESULT) {
        this.RESULT = RESULT;
    }

    public String getIRCODE() {
        return IRCODE;
    }

    public void setIRCODE(String IRCODE) {
        this.IRCODE = IRCODE;
    }

    public String getSECURITYDEVICEID() {
        return SECURITYDEVICEID;
    }

    public void setSECURITYDEVICEID(String SECURITYDEVICEID) {
        this.SECURITYDEVICEID = SECURITYDEVICEID;
    }

    public String getVALUE() {
        return VALUE;
    }

    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }

    @Override
    public String toString() {
        return "UDPResponseBodyBase{" +
                "INSTP='" + INSTP + '\'' +
                ", IP='" + IP + '\'' +
                ", TIMESTAMP=" + TIMESTAMP +
                ", DEVICEID='" + DEVICEID + '\'' +
                ", BOXID='" + BOXID + '\'' +
                ", PROTOCOLVER='" + PROTOCOLVER + '\'' +
                ", RESULT=" + RESULT +
                ", IRCODE='" + IRCODE + '\'' +
                ", SECURITYDEVICEID='" + SECURITYDEVICEID + '\'' +
                ", VALUE='" + VALUE + '\'' +
                '}';
    }
}

