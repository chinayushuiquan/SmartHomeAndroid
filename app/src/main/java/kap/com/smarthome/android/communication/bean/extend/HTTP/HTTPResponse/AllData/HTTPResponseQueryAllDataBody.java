package kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponse.AllData;

import java.util.List;

import kap.com.smarthome.android.communication.bean.base.DATABean.AllBeanData;
import kap.com.smarthome.android.communication.bean.base.DATABean.DeviceData;

/**
 * Created by Administrator on 2017/10/17 0017.
 */

public class HTTPResponseQueryAllDataBody {

    protected String INSTP ;

    protected String RESULT;

    protected AllBeanData DATA;

    public HTTPResponseQueryAllDataBody() {
    }

    public HTTPResponseQueryAllDataBody(String INSTP, String RESULT, AllBeanData DATA) {
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

    public AllBeanData getDATA() {
        return DATA;
    }

    public void setDATA(AllBeanData DATA) {
        this.DATA = DATA;
    }

    @Override
    public String toString() {
        return "HTTPResponseQureyRelayBoxBody{" +
                "INSTP='" + INSTP + '\'' +
                ", RESULT='" + RESULT + '\'' +
                ", DATA=" + DATA +
                '}';
    }
}
