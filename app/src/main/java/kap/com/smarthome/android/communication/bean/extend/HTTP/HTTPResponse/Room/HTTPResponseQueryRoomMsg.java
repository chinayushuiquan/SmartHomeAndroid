package kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponse.Room;
import kap.com.smarthome.android.communication.bean.base.JsonHeadBase;

/**
 * Created by Administrator on 2017/11/13 0013.
 */

public class HTTPResponseQueryRoomMsg {

    private JsonHeadBase HEAD;

    private HTTPResponseQueryRoomBody BODY;


    public HTTPResponseQueryRoomMsg() {}

    public HTTPResponseQueryRoomMsg(JsonHeadBase HEAD, HTTPResponseQueryRoomBody BODY) {
        this.HEAD = HEAD;
        this.BODY = BODY;
    }

    public JsonHeadBase getHEAD() {
        return HEAD;
    }

    public void setHEAD(JsonHeadBase HEAD) {
        this.HEAD = HEAD;
    }

    public HTTPResponseQueryRoomBody getBODY() {
        return BODY;
    }

    public void setBODY(HTTPResponseQueryRoomBody BODY) {
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
