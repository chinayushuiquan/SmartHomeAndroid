package kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponse.AllData;
import kap.com.smarthome.android.communication.bean.base.JsonHeadBase;

/**
 * Created by Administrator on 2017/11/13 0013.
 */

public class HTTPResponseQueryAllDataMsg {

    private JsonHeadBase HEAD;

    private HTTPResponseQueryAllDataBody BODY;

    public HTTPResponseQueryAllDataMsg() {
    }

    public HTTPResponseQueryAllDataMsg(JsonHeadBase HEAD, HTTPResponseQueryAllDataBody BODY) {
        this.HEAD = HEAD;
        this.BODY = BODY;
    }

    public JsonHeadBase getHEAD() {
        return HEAD;
    }

    public void setHEAD(JsonHeadBase HEAD) {
        this.HEAD = HEAD;
    }

    public HTTPResponseQueryAllDataBody getBODY() {
        return BODY;
    }

    public void setBODY(HTTPResponseQueryAllDataBody BODY) {
        this.BODY = BODY;
    }

    @Override
    public String toString() {
        return "HTTPResponseQueryAllDataMsg{" +
                "HEAD=" + HEAD +
                ", BODY=" + BODY +
                '}';
    }
}
