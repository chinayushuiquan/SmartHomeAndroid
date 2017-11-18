package kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponse.RelayBox;

import kap.com.smarthome.android.communication.bean.base.JsonHeadBase;

/**
 * Created by ChrisYu on 2017/11/18.
 */

public class HTTPResponseQueryRelayBoxMsg {

    private JsonHeadBase HEAD;

    private HTTPResponseQueryRelayBoxBody BODY;

    public HTTPResponseQueryRelayBoxMsg() {}

    public HTTPResponseQueryRelayBoxMsg(JsonHeadBase HEAD, HTTPResponseQueryRelayBoxBody BODY) {
        this.HEAD = HEAD;
        this.BODY = BODY;
    }

    public JsonHeadBase getHEAD() {
        return HEAD;
    }

    public void setHEAD(JsonHeadBase HEAD) {
        this.HEAD = HEAD;
    }

    public HTTPResponseQueryRelayBoxBody getBODY() {
        return BODY;
    }

    public void setBODY(HTTPResponseQueryRelayBoxBody BODY) {
        this.BODY = BODY;
    }


    @Override
    public String toString() {
        return "HTTPResponseQueryRelayBoxMsg{" +
                "HEAD=" + HEAD +
                ", BODY=" + BODY +
                '}';
    }
}
