package kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponse.RelayBox;

import java.util.List;

import kap.com.smarthome.android.communication.bean.base.DATABean.RelayBoxData;

/**
 * Created by Administrator on 2017/10/17 0017.
 */

public class HTTPResponseQueryRelayBoxBody {

    protected String INSTP ;

    protected String RESULT;

    protected List<RelayBoxData> DATA;

    public HTTPResponseQueryRelayBoxBody() {
    }

    public HTTPResponseQueryRelayBoxBody(String INSTP, String RESULT, List<RelayBoxData> DATA) {
        this.INSTP = INSTP;
        this.RESULT = RESULT;
        this.DATA = DATA;
    }

    public String getINSTP() {
        return INSTP;
    }

    public void setINSTP(String INSTP) {
        this.INSTP = INSTP;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public List<RelayBoxData> getDATA() {
        return DATA;
    }

    public void setDATA(List<RelayBoxData> DATA) {
        this.DATA = DATA;
    }

    @Override
    public String toString() {
        return "HTTPResponseQueryRelayBoxBody{" +
                "INSTP='" + INSTP + '\'' +
                ", RESULT='" + RESULT + '\'' +
                ", DATA=" + DATA +
                '}';
    }
}
