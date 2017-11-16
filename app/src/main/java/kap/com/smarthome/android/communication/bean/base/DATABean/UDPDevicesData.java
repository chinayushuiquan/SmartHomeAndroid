package kap.com.smarthome.android.communication.bean.base.DATABean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by yushq on 2017/11/2 0002.
 * “DEVICELIST”:[{ “BOXID” : “0000000001100001”, “DEVID” : “0000000001100001”, “PARA” : “0000000001100001”},
 * { “BOXID” : “0000000001100001”, “DEVID” : “0000000001100001”, “PARA” : “0000000001100001”}]
 */

public class UDPDevicesData {

    @JSONField(name = "BOXID", ordinal = 1)
    private String BOXID ;

    @JSONField(name = "DEVID", ordinal = 2)
    private String DEVID ;

    @JSONField(name = "VALUE", ordinal = 3)
    private String  VALUE ;

    public UDPDevicesData() {
    }

    public UDPDevicesData(String BOXID, String DEVID, String VALUE) {
        this.BOXID = BOXID;
        this.DEVID = DEVID;
        this.VALUE = VALUE;
    }

    public String getBOXID() {
        return BOXID;
    }

    public void setBOXID(String BOXID) {
        this.BOXID = BOXID;
    }

    public String getDEVID() {
        return DEVID;
    }

    public void setDEVID(String DEVID) {
        this.DEVID = DEVID;
    }

    public String getVALUE() {
        return VALUE;
    }

    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }
}
