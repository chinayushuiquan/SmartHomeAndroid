package kap.com.smarthome.android.communication.bean.base.DATABean;

/**
 * Created by Administrator on 2017/10/17 0017.
 */

public class RelayBoxData {


/* :[{"ID":"f00ggggffgf","BOXID":"12p00ghhgjffjj",
            "RELAYBOXNAME":"123","HARDWAREVERSION":"86-02304239",
            "SOFTWAREVERSION":"123456","MACHINECODE":"34343434g",
            "IP":"192.167.3.123","PORT":"8082",
            "MASK":"255.255.1.1",
            "PLATFORMADDR":"125.258.3.255",
            "PLATFORMPORT":"5632",
            "RELAYORDER":"3"}*/

    private String ID;

    private String BOXID;

    private String RELAYBOXNAME;

    private String HARDWAREVERSION;

    private String SOFTWAREVERSION;

    private String MACHINECODE;

    private String IP;

    private String PORT;

    private String MASK;

    private String PLATFORMADDR;

    private String PLATFORMPORT;

    private String RELAYORDER;

    public RelayBoxData() {

    }

    public RelayBoxData(String ID, String BOXID, String RELAYBOXNAME, String HARDWAREVERSION, String SOFTWAREVERSION, String MACHINECODE, String IP, String PORT, String MASK, String PLATFORMADDR, String PLATFORMPORT, String RELAYORDER) {
        this.ID = ID;
        this.BOXID = BOXID;
        this.RELAYBOXNAME = RELAYBOXNAME;
        this.HARDWAREVERSION = HARDWAREVERSION;
        this.SOFTWAREVERSION = SOFTWAREVERSION;
        this.MACHINECODE = MACHINECODE;
        this.IP = IP;
        this.PORT = PORT;
        this.MASK = MASK;
        this.PLATFORMADDR = PLATFORMADDR;
        this.PLATFORMPORT = PLATFORMPORT;
        this.RELAYORDER = RELAYORDER;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getBOXID() {
        return BOXID;
    }

    public void setBOXID(String BOXID) {
        this.BOXID = BOXID;
    }

    public String getRELAYBOXNAME() {
        return RELAYBOXNAME;
    }

    public void setRELAYBOXNAME(String RELAYBOXNAME) {
        this.RELAYBOXNAME = RELAYBOXNAME;
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

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getPORT() {
        return PORT;
    }

    public void setPORT(String PORT) {
        this.PORT = PORT;
    }

    public String getMASK() {
        return MASK;
    }

    public void setMASK(String MASK) {
        this.MASK = MASK;
    }

    public String getPLATFORMADDR() {
        return PLATFORMADDR;
    }

    public void setPLATFORMADDR(String PLATFORMADDR) {
        this.PLATFORMADDR = PLATFORMADDR;
    }

    public String getRELAYORDER() {
        return RELAYORDER;
    }

    public void setRELAYORDER(String RELAYORDER) {
        this.RELAYORDER = RELAYORDER;
    }

    public String getPLATFORMPORT() {
        return PLATFORMPORT;
    }

    public void setPLATFORMPORT(String PLATFORMPORT) {
        this.PLATFORMPORT = PLATFORMPORT;
    }

    @Override
    public String toString() {
        return "RelayBoxData{" +
                "ID='" + ID + '\'' +
                ", BOXID='" + BOXID + '\'' +
                ", RELAYBOXNAME='" + RELAYBOXNAME + '\'' +
                ", HARDWAREVERSION='" + HARDWAREVERSION + '\'' +
                ", SOFTWAREVERSION='" + SOFTWAREVERSION + '\'' +
                ", MACHINECODE='" + MACHINECODE + '\'' +
                ", IP='" + IP + '\'' +
                ", PORT='" + PORT + '\'' +
                ", MASK='" + MASK + '\'' +
                ", PLATFORMADDR='" + PLATFORMADDR + '\'' +
                ", PLATFORMPORT='" + PLATFORMPORT + '\'' +
                ", RELAYORDER='" + RELAYORDER + '\'' +
                '}';
    }
}
