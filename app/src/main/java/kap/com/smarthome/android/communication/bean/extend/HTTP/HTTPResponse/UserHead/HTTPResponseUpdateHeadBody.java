package kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponse.UserHead;


import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPResponseBodyBase;

/**
 * Created by Administrator on 2017/10/10 0010.
 */

public class HTTPResponseUpdateHeadBody extends HTTPResponseBodyBase {

    private String HEADLOGOURL;

    public HTTPResponseUpdateHeadBody() {
    }

    public HTTPResponseUpdateHeadBody(String INSTP, String RESULT, String HEADLOGOURL) {
        super(INSTP, RESULT);
        this.HEADLOGOURL = HEADLOGOURL;
    }

    public String getHEADLOGOURL() {
        return HEADLOGOURL;
    }

    public void setHEADLOGOURL(String HEADLOGOURL) {
        this.HEADLOGOURL = HEADLOGOURL;
    }

    @Override
    public String toString() {
        return "HTTPResponseUpdateHeadBody{" +
                "HEADLOGOURL='" + HEADLOGOURL + '\'' +
                '}';
    }
}
