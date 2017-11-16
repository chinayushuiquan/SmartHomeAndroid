package kap.com.smarthome.android.communication.bean.extend.UDP;


import kap.com.smarthome.android.communication.bean.base.UDP.UDPResponseBodyBase;

/**
 * Created by Administrator on 2017/10/13 0013.
 */

public class UDPSearchRelayBoxRequestBody extends UDPResponseBodyBase {

    private String  HARDWAREVERSION;

    private String  SOFTWAREVERSION;

    private String  MACHINECODE;

    public UDPSearchRelayBoxRequestBody() {
    }

    public UDPSearchRelayBoxRequestBody(String HARDWAREVERSION, String SOFTWAREVERSION, String MACHINECODE) {
        this.HARDWAREVERSION = HARDWAREVERSION;
        this.SOFTWAREVERSION = SOFTWAREVERSION;
        this.MACHINECODE = MACHINECODE;
    }

    public String getHARDWAREVERSION() {
        return HARDWAREVERSION;
    }

    public void setHARDWAREVERSION(String HARDWAREVERSION) {
        this.HARDWAREVERSION = HARDWAREVERSION;
    }

    public String getSOFTWAREVERSION() {
        return SOFTWAREVERSION;
    }

    public void setSOFTWAREVERSION(String SOFTWAREVERSION) {
        this.SOFTWAREVERSION = SOFTWAREVERSION;
    }

    public String getMACHINECODE() {
        return MACHINECODE;
    }

    public void setMACHINECODE(String MACHINECODE) {
        this.MACHINECODE = MACHINECODE;
    }


}
