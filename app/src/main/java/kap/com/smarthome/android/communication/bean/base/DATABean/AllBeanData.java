package kap.com.smarthome.android.communication.bean.base.DATABean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/13 0013.
 */

public class AllBeanData {

    List<RoomData> ROOMDATA ;

    List<RelayBoxData> BOXDATA;

    List<DeviceData>  DEVICEDATA;

    List<IrkeysData> REDCODEDATA;

    List<ScenesData> SCENEDATA;


    public AllBeanData() {
    }

    public AllBeanData(List<RoomData> ROOMDATA, List<RelayBoxData> BOXDATA, List<DeviceData> DEVICEDATA, List<IrkeysData> REDCODEDATA, List<ScenesData> SCENEDATA) {
        this.ROOMDATA = ROOMDATA;
        this.BOXDATA = BOXDATA;
        this.DEVICEDATA = DEVICEDATA;
        this.REDCODEDATA = REDCODEDATA;
        this.SCENEDATA = SCENEDATA;
    }

    public List<RoomData> getROOMDATA() {
        return ROOMDATA;
    }

    public void setROOMDATA(List<RoomData> ROOMDATA) {
        this.ROOMDATA = ROOMDATA;
    }

    public List<RelayBoxData> getBOXDATA() {
        return BOXDATA;
    }

    public void setBOXDATA(List<RelayBoxData> BOXDATA) {
        this.BOXDATA = BOXDATA;
    }

    public List<DeviceData> getDEVICEDATA() {
        return DEVICEDATA;
    }

    public void setDEVICEDATA(List<DeviceData> DEVICEDATA) {
        this.DEVICEDATA = DEVICEDATA;
    }

    public List<IrkeysData> getREDCODEDATA() {
        return REDCODEDATA;
    }

    public void setREDCODEDATA(List<IrkeysData> REDCODEDATA) {
        this.REDCODEDATA = REDCODEDATA;
    }

    public List<ScenesData> getSCENEDATA() {
        return SCENEDATA;
    }

    public void setSCENEDATA(List<ScenesData> SCENEDATA) {
        this.SCENEDATA = SCENEDATA;
    }
}
