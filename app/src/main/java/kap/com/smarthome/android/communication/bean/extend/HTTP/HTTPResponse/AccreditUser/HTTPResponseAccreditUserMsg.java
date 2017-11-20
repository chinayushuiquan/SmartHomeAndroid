package kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponse.AccreditUser;
import kap.com.smarthome.android.communication.bean.base.JsonHeadBase;

/**
 * Created by Administrator on 2017/11/13 0013.
 */

public class HTTPResponseAccreditUserMsg {

    private JsonHeadBase HEAD;

    private HTTPResponseAccreditUserBody BODY;


    public HTTPResponseAccreditUserMsg() {}

    public HTTPResponseAccreditUserMsg(JsonHeadBase HEAD, HTTPResponseAccreditUserBody BODY) {
        this.HEAD = HEAD;
        this.BODY = BODY;
    }

    public JsonHeadBase getHEAD() {
        return HEAD;
    }

    public void setHEAD(JsonHeadBase HEAD) {
        this.HEAD = HEAD;
    }

    public HTTPResponseAccreditUserBody getBODY() {
        return BODY;
    }

    public void setBODY(HTTPResponseAccreditUserBody BODY) {
        this.BODY = BODY;
    }

    @Override
    public String toString() {
        return "HTTPResponseQueryRoomMsg{" +
                "HEAD=" + HEAD +
                ", BODY=" + BODY +
                '}';
    }
}
