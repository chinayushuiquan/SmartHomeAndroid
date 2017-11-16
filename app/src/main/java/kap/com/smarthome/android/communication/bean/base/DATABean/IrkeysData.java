package kap.com.smarthome.android.communication.bean.base.DATABean;

/**
 * Created by yushq on 2017/11/6 0006.
 *
 * "BODY":{"INSTP":"NEWREDCODEREQ",
 * "DATA":[{"ID":"24343",
 * "IFCODE":"fssss4hh",
 * "DEVICEID":"fffffggf",
 * "KEY1":"254",
 * "KEY2":"252",
 * "INDEX":"1",
 * "BUTTONNAME":"dd"},
 */

public class IrkeysData {

    private String ID ;


    private String IFCODE ;


    private String DEVICEID ;


    private String KEY1 ;


    private String KEY2 ;

    private String INDEX ;

    private String BUTTONNAME ;

    public IrkeysData() {
    }

    public IrkeysData(String ID, String IFCODE, String DEVICEID, String KEY1, String KEY2, String INDEX, String BUTTONNAME) {
        this.ID = ID;
        this.IFCODE = IFCODE;
        this.DEVICEID = DEVICEID;
        this.KEY1 = KEY1;
        this.KEY2 = KEY2;
        this.INDEX = INDEX;
        this.BUTTONNAME = BUTTONNAME;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIFCODE() {
        return IFCODE;
    }

    public void setIFCODE(String IFCODE) {
        this.IFCODE = IFCODE;
    }

    public String getDEVICEID() {
        return DEVICEID;
    }

    public void setDEVICEID(String DEVICEID) {
        this.DEVICEID = DEVICEID;
    }

    public String getKEY1() {
        return KEY1;
    }

    public void setKEY1(String KEY1) {
        this.KEY1 = KEY1;
    }

    public String getKEY2() {
        return KEY2;
    }

    public void setKEY2(String KEY2) {
        this.KEY2 = KEY2;
    }

    public String getINDEX() {
        return INDEX;
    }

    public void setINDEX(String INDEX) {
        this.INDEX = INDEX;
    }

    public String getBUTTONNAME() {
        return BUTTONNAME;
    }

    public void setBUTTONNAME(String BUTTONNAME) {
        this.BUTTONNAME = BUTTONNAME;
    }

    @Override
    public String toString() {
        return "IrkeysData{" +
                "ID='" + ID + '\'' +
                ", IFCODE='" + IFCODE + '\'' +
                ", DEVICEID='" + DEVICEID + '\'' +
                ", KEY1='" + KEY1 + '\'' +
                ", KEY2='" + KEY2 + '\'' +
                ", INDEX='" + INDEX + '\'' +
                ", BUTTONNAME='" + BUTTONNAME + '\'' +
                '}';
    }
}
