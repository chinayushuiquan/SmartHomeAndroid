package kap.com.smarthome.android.communication.bean.extend.UDP;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

import kap.com.smarthome.android.communication.bean.base.DATABean.UDPDevicesData;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPRequestBodyBase;

/**
 * Created by yushuq 2017/11/1 0001.
 */

public class UDPAddRfDevicesRequestBody extends UDPRequestBodyBase {

    @JSONField(name = "DEVNUM", ordinal = 8)
    private int   DEVNUM;

    @JSONField(name = "DEVICELIST", ordinal = 9)
    private List<UDPDevicesData> DEVICELIST;


    public UDPAddRfDevicesRequestBody() {

    }

    public UDPAddRfDevicesRequestBody(int DEVNUM, List<UDPDevicesData> DEVICELIST) {
        this.DEVNUM = DEVNUM;
        this.DEVICELIST = DEVICELIST;
    }

    public int getDEVNUM() {
        return DEVNUM;
    }

    public void setDEVNUM(int DEVNUM) {
        this.DEVNUM = DEVNUM;
    }

    public List<UDPDevicesData> getDEVICELIST() {
        return DEVICELIST;
    }

    public void setDEVICELIST(List<UDPDevicesData> DEVICELIST) {
        this.DEVICELIST = DEVICELIST;
    }
}
