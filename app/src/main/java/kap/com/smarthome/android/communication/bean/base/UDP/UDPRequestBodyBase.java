package kap.com.smarthome.android.communication.bean.base.UDP;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by yushq on 2017/6/1 0001.
 */

public class UDPRequestBodyBase implements Serializable {

    @JSONField(name = "INSTP", ordinal = 1)
    protected String INSTP;

    @JSONField(name = "IP", ordinal = 2)
    protected String IP;

    @JSONField(name = "TIMESTAMP", ordinal = 3)
    protected long TIMESTAMP;

    @JSONField(name = "DEVICEID", ordinal = 4)
    protected String DEVICEID;

    @JSONField(name = "BOXID", ordinal = 5)
    protected String BOXID;

    @JSONField(name = "PROTOCOLVER", ordinal = 6)
    protected String PROTOCOLVER;

    @JSONField(name = "RESULT", ordinal = 7)
    protected int  RESULT;

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

    @Override
    public String toString() {
        return "UDPResponseBodyBase{" +
                "INSTP='" + INSTP + '\'' +
                ", IP='" + IP + '\'' +
                ", TIMESTAMP=" + TIMESTAMP +
                ", DEVICEID='" + DEVICEID + '\'' +
                ", BOXID='" + BOXID + '\'' +
                ", PROTOCOLVER='" + PROTOCOLVER + '\'' +
                ", RESULT='" + RESULT + '\'' +
                '}';
    }
}

