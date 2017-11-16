package kap.com.smarthome.android.communication.bean.extend.HTTP;

import java.util.List;

import kap.com.smarthome.android.communication.bean.base.DATABean.DeviceData;
import kap.com.smarthome.android.data.bean.RelayBox;

/**
 * Created by Administrator on 2017/10/17 0017.
 */

public class HTTPResponseQueryDevicesBody {

    protected String INSTP ;

    protected String RESULT;

    protected List<DeviceData> DATA;

    public HTTPResponseQueryDevicesBody() {
    }

    public HTTPResponseQueryDevicesBody(String INSTP, String RESULT, List<DeviceData> DATA) {
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

    public List<DeviceData> getDATA() {
        return DATA;
    }

    public void setDATA(List<DeviceData> DATA) {
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
