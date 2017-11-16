package kap.com.smarthome.android.communication.bean.base.DATABean;

/**
 * Created by Administrator on 2017/10/16 0016.
 */

public class RoomData {

    private String ID;

    private String NAME;

    private String ROOMORDER;

    private String TYPE;

    public RoomData() {
    }

    public RoomData(String ID, String NAME, String ROOMORDER, String TYPE) {
        this.ID = ID;
        this.NAME = NAME;
        this.ROOMORDER = ROOMORDER;
        this.TYPE = TYPE;
    }

    public void setID(String ID){
        this.ID = ID;
    }
    public String getID(){
        return this.ID;
    }
    public void setNAME(String NAME){
        this.NAME = NAME;
    }
    public String getNAME(){
        return this.NAME;
    }
    public void setROOMORDER(String ROOMORDER){
        this.ROOMORDER = ROOMORDER;
    }
    public String getROOMORDER(){
        return this.ROOMORDER;
    }
    public void setTYPE(String TYPE){
        this.TYPE = TYPE;
    }
    public String getTYPE(){
        return this.TYPE;
    }

    @Override
    public String toString() {
        return "RoomData{" +
                "ID='" + ID + '\'' +
                ", NAME='" + NAME + '\'' +
                ", ROOMORDER='" + ROOMORDER + '\'' +
                ", TYPE='" + TYPE + '\'' +
                '}';
    }
}
