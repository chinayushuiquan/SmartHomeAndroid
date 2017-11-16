package kap.com.smarthome.android.communication.bean.extend.UDP;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

import kap.com.smarthome.android.communication.bean.base.DATABean.UDPBoxIdData;
import kap.com.smarthome.android.communication.bean.base.DATABean.UDPDevicesData;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPRequestBodyBase;

/**
 * Created by yushuq 2017/11/1 0001.
 */

public class UDPRelayBoxEnterAddDevicesRequestBody extends UDPRequestBodyBase {

    @JSONField(name = "BOXNUM", ordinal = 8)
    private int   BOXNUM;

    @JSONField(name = "BOXIDLIST", ordinal = 9)
    private List<UDPBoxIdData> BOXIDLIST;


    public UDPRelayBoxEnterAddDevicesRequestBody() {

    }

    public UDPRelayBoxEnterAddDevicesRequestBody(int BOXNUM, List<UDPBoxIdData> BOXIDLIST) {
        this.BOXNUM = BOXNUM;
        this.BOXIDLIST = BOXIDLIST;
    }

    public int getBOXNUM() {
        return BOXNUM;
    }

    public void setBOXNUM(int BOXNUM) {
        this.BOXNUM = BOXNUM;
    }

    public List<UDPBoxIdData> getBOXIDLIST() {
        return BOXIDLIST;
    }

    public void setBOXIDLIST(List<UDPBoxIdData> BOXIDLIST) {
        this.BOXIDLIST = BOXIDLIST;
    }
}
