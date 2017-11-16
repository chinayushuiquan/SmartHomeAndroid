package kap.com.smarthome.android.communication.bean.base.HTTP;


import java.util.List;

/**
 * Created by Administrator on 2017/9/29 0029.
 */

public class HTTPRequestBodyBase<T> {

    protected String INSTP ;

    protected String APPKEY = "54eb0828c7e841e2bf8ad1496d0e7c07";

    protected String CHECKSUM = "514352" ;

    private List<T>  DATA;

    public HTTPRequestBodyBase() {

    }

    public HTTPRequestBodyBase(String INSTP, String APPKEY, String CHECKSUM, String RESULT , List<T> DATA) {
        this.INSTP = INSTP;
        this.APPKEY = APPKEY;
        this.CHECKSUM = CHECKSUM;
        this.DATA = DATA;
    }


    public String getINSTP() {
        return INSTP;
    }

    public void setINSTP(String INSTP) {
        this.INSTP = INSTP;
    }

    public String getAPPKEY() {
        return APPKEY;
    }

    public void setAPPKEY(String APPKEY) {
        this.APPKEY = APPKEY;
    }

    public String getCHECKSUM() {
        return CHECKSUM;
    }

    public void setCHECKSUM(String CHECKSUM) {
        this.CHECKSUM = CHECKSUM;
    }


    public List<T> getDATA() {
        return DATA;
    }

    public void setDATA(List<T> DATA) {
        this.DATA = DATA;
    }


    @Override
    public String toString() {
        return "HTTPRequestBodyBase{" +
                "INSTP='" + INSTP + '\'' +
                ", APPKEY='" + APPKEY + '\'' +
                ", CHECKSUM='" + CHECKSUM + '\'' +
                ", DATA=" + DATA +
                '}';
    }
}

