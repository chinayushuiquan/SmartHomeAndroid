package kap.com.smarthome.android.communication.bean.base.DATABean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2017/11/9 0009.
 */

public class UDPBoxIdData {

    @JSONField(name = "BOXID", ordinal = 1)
    private String BOXID ;

    public UDPBoxIdData() {
    }

    public UDPBoxIdData(String BOXID) {
        this.BOXID = BOXID;
    }

    public String getBOXID() {
        return BOXID;
    }

    public void setBOXID(String BOXID) {
        this.BOXID = BOXID;
    }
}
