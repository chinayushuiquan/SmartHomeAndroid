package kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponse.Devices;

import kap.com.smarthome.android.communication.bean.base.JsonHeadBase;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponse.AllData.HTTPResponseQueryAllDataBody;

/**
 * Created by ChrisYu on 2017/11/18.
 */

public class HTTPResponseQueryDevicesMsg {

    private JsonHeadBase HEAD;

    private HTTPResponseQueryDevicesBody BODY;

    public HTTPResponseQueryDevicesMsg() {
    }

    public HTTPResponseQueryDevicesMsg(JsonHeadBase HEAD, HTTPResponseQueryDevicesBody BODY) {
        this.HEAD = HEAD;
        this.BODY = BODY;
    }

    public JsonHeadBase getHEAD() {
        return HEAD;
    }

    public void setHEAD(JsonHeadBase HEAD) {
        this.HEAD = HEAD;
    }

    public HTTPResponseQueryDevicesBody getBODY() {
        return BODY;
    }

    public void setBODY(HTTPResponseQueryDevicesBody BODY) {
        this.BODY = BODY;
    }

    @Override
    public String toString() {
        return "HTTPResponseQueryDevicesMsg{" +
                "HEAD=" + HEAD +
                ", BODY=" + BODY +
                '}';
    }

}
