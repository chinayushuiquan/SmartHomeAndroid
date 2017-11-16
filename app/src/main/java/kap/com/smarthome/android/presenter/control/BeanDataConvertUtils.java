package kap.com.smarthome.android.presenter.control;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.communication.bean.base.DATABean.DeviceData;
import kap.com.smarthome.android.communication.bean.base.DATABean.IrkeysData;
import kap.com.smarthome.android.communication.bean.base.DATABean.RelayBoxData;
import kap.com.smarthome.android.communication.bean.base.DATABean.RoomData;
import kap.com.smarthome.android.communication.bean.base.DATABean.ScenesData;
import kap.com.smarthome.android.communication.bean.base.DATABean.ScenesDevicesData;
import kap.com.smarthome.android.communication.bean.base.DATABean.ScenesTriggerData;
import kap.com.smarthome.android.communication.bean.base.DATABean.UDPDevicesData;
import kap.com.smarthome.android.communication.bean.base.DATABean.UDPScenesDeviceData;
import kap.com.smarthome.android.communication.bean.base.DATABean.UDPScenesTriggerData;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.IRKey;
import kap.com.smarthome.android.data.bean.RelayBox;
import kap.com.smarthome.android.data.bean.Room;
import kap.com.smarthome.android.data.bean.Scenes;
import kap.com.smarthome.android.data.bean.ScenesDevice;
import kap.com.smarthome.android.data.bean.ScenesTrigger;
import kap.com.smarthome.android.presenter.utils.UUIDUtils;

/**
 * Created by yushq on 2017/10/25 0025.
 *
 * 由于沟通和时间问题， app本地数据库和云端数据库的字段未能协调一致， 数据通过GSON不能直接装换，需要进行适配转换
 *
 * 也许后期时间容许 可以通过协调一致进行修改
 *
 * 例如： data 包下的 RelayBox 和 communication - DATABean 包下的 RelayBoxData  需要进行转换
 *
 * 本地存储的RelayBox  字段
 *
 @Id(autoincrement =  true)
 private Long ID;

 @Index(unique  = true)
 private String  GUID ;

 @Unique
 private String  BOX_ID;

 private String NAME;

 private String IP;

 private int PORT;

 private String PLATFORM_ADDR;

 private int  PLATFORM_PORT;

 private String MASK;

 private int RELAY_ORDER;

 private String HARDWARE_VERSION;

 private String SOFTWARE_VERSION;

 private String MACHINE_CODE;


 *
 * 服务器数据字段  RelayBoxData
  *
 private String ID;（对应本地的GUID）

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

 *
 *
 */

public class BeanDataConvertUtils {


    /**
     * RoomData convert to  Room
     *
     * 服务器返回的房间数据 转换成本地数据存储
     */

    public  static List<Room> convertToRoom(List<RoomData> roomDataList){

        //由于 本地的实体类 字段不一致，需要进行转换
        List<Room> roomList  = new ArrayList<>();

        for (int i = 0; i < roomDataList.size() ; i++){

            RoomData roomData = roomDataList.get(i);

            Room room = new Room();
            room.setGUID(roomData.getID());
            room.setNAME(roomData.getNAME());
            room.setROOM_ORDER(Integer.parseInt(roomData.getROOMORDER()));
            room.setTYPE(Integer.parseInt(roomData.getTYPE()));

            roomList.add(room);
        }
        return roomList;


    }

    /**
     * RelayBox  convert to  RelayBoxData
     */
    public static List<RelayBoxData> convertToRelayBoxData(List<RelayBox>  relayBoxs) {

        //由于 本地的实体类 字段不一致，需要进行转换
        List<RelayBoxData> relayBoxDataList  = new ArrayList<>();

        for (int i = 0; i < relayBoxs.size() ; i++){

            RelayBox relayBox = relayBoxs.get(i);

            RelayBoxData relayBoxData = new RelayBoxData(
                    relayBox.getGUID()+"",
                    relayBox.getBOX_ID()+"",
                    relayBox.getNAME()+"",
                    relayBox.getHARDWARE_VERSION()+"",
                    relayBox.getSOFTWARE_VERSION()+"",
                    relayBox.getMACHINE_CODE()+"",
                    relayBox.getIP()+"",
                    relayBox.getPORT()+"",
                    relayBox.getMASK()+"",
                    relayBox.getPLATFORM_ADDR()+"",
                    relayBox.getPLATFORM_PORT()+"" ,
                    relayBox.getRELAY_ORDER()+"");

            relayBoxDataList.add(relayBoxData);

        }
        return relayBoxDataList;
    }



    /**
     * RelayBoxData  convert to  RelayBox
     */

    public static List<RelayBox> convertToRelayBox (List<RelayBoxData>  relayBoxDatas) {

        //由于 本地的实体类 字段不一致，需要进行转换
        List<RelayBox> relayBoxList  = new ArrayList<>();

        for (int i = 0; i < relayBoxDatas.size() ; i++){

            RelayBoxData relayBoxData = relayBoxDatas.get(i);

            RelayBox relayBox= new RelayBox();

            relayBox.setGUID(relayBoxData.getID());
            relayBox.setNAME(relayBoxData.getRELAYBOXNAME());
            relayBox.setBOX_ID(relayBoxData.getBOXID());

            relayBox.setMACHINE_CODE(relayBoxData.getMACHINECODE());
            relayBox.setHARDWARE_VERSION(relayBoxData.getHARDWAREVERSION());
            relayBox.setSOFTWARE_VERSION(relayBoxData.getSOFTWAREVERSION());

            relayBox.setIP(relayBoxData.getIP());
            relayBox.setMASK(relayBoxData.getMASK());
            relayBox.setPLATFORM_ADDR(relayBoxData.getPLATFORMADDR());
            relayBox.setPLATFORM_PORT(Integer.parseInt(relayBoxData.getPLATFORMPORT()));
            relayBox.setPORT(Integer.parseInt(relayBoxData.getPORT()));

            relayBox.setRELAY_ORDER(Integer.parseInt(relayBoxData.getRELAYORDER()));

            relayBoxList.add(relayBox);

        }
        return relayBoxList;
    }


    /**
     *  Devices convert to ScenesDevice
     */

    public static List<ScenesDevice> convertToScenesDevice (List<Devices>  devicesList , String scenesId) {

        //由于 本地的实体类 字段不一致，需要进行转换
        List<ScenesDevice> scenesDeviceList  = new ArrayList<>();

        for (int i = 0; i < devicesList.size() ; i++){

            Devices device = devicesList.get(i);

            ScenesDevice scenesDevice = new ScenesDevice();

            scenesDevice.setDEVICE_ID(device.getDEVICE_ID());
            scenesDevice.setTYPE(device.getTYPE());
            scenesDevice.setRELAYBOX_ID(device.getRELAY_ID());
            scenesDevice.setDEVICE_NAME(device.getNAME());
            scenesDevice.setSCENES_ID(scenesId);
            scenesDevice.setDEVICE_INFO("");
            scenesDevice.setDEVICE_VALUE("0000");
            scenesDevice.setGUID(device.getGUID());


            scenesDeviceList.add(scenesDevice);

        }
        return scenesDeviceList;
    }


    /**
     *  Devices convert to ScenesTrigger
     *
     *  设备转化成场景触发条件
     */

    public static List<ScenesTrigger> convertToScenesTrigger(List<Devices>  devicesList , String scenesId) {

        //由于 本地的实体类 字段不一致，需要进行转换
        List<ScenesTrigger> scenesTriggerList  = new ArrayList<>();

        for (int i = 0; i < devicesList.size() ; i++){

            Devices device = devicesList.get(i);

            ScenesTrigger scenesTrigger = new ScenesTrigger();

            scenesTrigger.setSCENES_ID(scenesId);

            scenesTrigger.setDEVICE_ID(device.getDEVICE_ID());//设备ID
            scenesTrigger.setGUID(device.getGUID());//设备GUID
            scenesTrigger.setDEVICE_NAME(device.getNAME());//设备名称
            scenesTrigger.setTYPE(device.getTYPE());//设备类型
            scenesTrigger.setRELAYBOX_ID(device.getRELAY_ID());//设备的中继盒子id
            if(device.getDEVICE_ID().length() > 8) {
                scenesTrigger.setTRIGGER_VALUE(device.getDEVICE_ID().substring(10, 16)); //触发条件的value
            }
            scenesTrigger.setTRIGGER_INFO("");//触发条件的显示信息eg: “10:20 周一，周二”

            scenesTriggerList.add(scenesTrigger);

        }
        return scenesTriggerList;
    }


    /**
     * Scenes convert to  ScenesData
     *
     * 场景数据 转换成服务器需要的数据格式
     *
     */
    public static List<ScenesData>  convertToScenesData(Scenes scenes , List<ScenesDevicesData> scenesDevicesDataList , List<ScenesTriggerData> scenesTriggerDataList){

        //ScenesData
        List<ScenesData>  scenesDataList  = new ArrayList<>();

        ScenesData scenesData = new ScenesData();
        scenesData.setID(scenes.getSCENE_ID());
        scenesData.setDEVICENUMBER(scenes.getDEVICE_NUMBER());
        scenesData.setSCENEICON(scenes.getSCENE_ICON()+"");
        scenesData.setSCENENAME(scenes.getSCENE_NAME());
        scenesData.setTRIGGERNUMBER(scenes.getTRIGGER_NUMBER());
        scenesData.setTRIGGERSTATUS(scenes.getTRIGGER_STATUS()+"");
        scenesData.setTYPE(scenes.getType()+"");

        scenesData.setSCENEDEVICE(scenesDevicesDataList);
        scenesData.setSCENETRIGGER(scenesTriggerDataList);

        scenesDataList.add(scenesData);


        return  scenesDataList;
    }


    /**
     * Scenes convert to  ScenesData
     * 场景数据 把服务器返回的数据转换成本地数据
     */
    public  static  List<Scenes>  convertToScenes(List<ScenesData> scenesDataList){
           List<Scenes> scenesList  = new ArrayList<>();

           for(int i = 0 ; i < scenesDataList.size() ; i++){
               Scenes scenes = new Scenes();

               ScenesData scenesData = new ScenesData();
               /**
                * GUID 没有数据
                */
               scenes.setGUID(scenesData.getID());

               scenes.setTRIGGER_STATUS(Integer.parseInt(scenesData.getTRIGGERSTATUS()));
               scenes.setType(Integer.parseInt(scenesData.getTYPE()));
               scenes.setTRIGGER_NUMBER(scenesData.getTRIGGERNUMBER());
               scenes.setDEVICE_NUMBER(scenesData.getDEVICENUMBER());
               scenes.setSCENE_ICON(Integer.parseInt(scenesData.getSCENEICON()));
               scenes.setSCENE_ID(scenesData.getID());
               scenes.setSCENE_NAME(scenesData.getSCENENAME());

               scenesList.add(scenes);

           }
        return  scenesList;
    }


    /**
     * Scenes convert to  ScenesData
     *
     * 场景数据 转换成服务器需要的数据格式  删除场景的时候只需要 场景ID就可以 其余参数省略
     *
     */
    public static List<ScenesData>  convertToScenesData(Scenes scenes){

        //ScenesData
        List<ScenesData>  scenesDataList  = new ArrayList<>();

        ScenesData scenesData = new ScenesData();
        scenesData.setID(scenes.getSCENE_ID());

        scenesDataList.add(scenesData);


        return  scenesDataList;
    }




    /**
     * ScenesDevice convert to  ScenesDevicesData
     *
     * 场景控制的设备数据 转换成服务器需要的数据格式
     *
     */
    public static List<ScenesDevicesData>  convertToScenesDevicesData( List<ScenesDevice> scenesDeviceList ){

        //ScenesDevicesData
        List<ScenesDevicesData> scenesDevicesDataList  = new ArrayList<>();

        for (int i = 0 ; i < scenesDeviceList.size() ; i++ ) {

            ScenesDevicesData scenesDevicesData = new ScenesDevicesData();

            ScenesDevice scenesDevice = scenesDeviceList.get(i);

            scenesDevicesData.setDEVICEID(scenesDevice.getDEVICE_ID());
            scenesDevicesData.setDEVICEINFO(scenesDevice.getDEVICE_INFO());
            scenesDevicesData.setDEVICEVALUE(scenesDevice.getDEVICE_VALUE());
            scenesDevicesData.setRELAYBOXID(scenesDevice.getRELAYBOX_ID());
            scenesDevicesData.setSCENEDEVICEGUID(scenesDevice.getGUID());
            scenesDevicesData.setDEVICETYPE(scenesDevice.getTYPE() + "");

            scenesDevicesDataList.add(scenesDevicesData);
        }


        return  scenesDevicesDataList;
    }



    /**
     * ScenesDevicesData convert to  ScenesDevice
     *
     * 场景控制的设备数据 服务器返回的数据转换成本地存储数据
     */
    public static List<ScenesDevice>  convertToScenesDevice( List<ScenesData> scenesDataList ){
        //ScenesDevicesData
        List<ScenesDevice> scenesDeviceList  = new ArrayList<>();

        for (int i = 0 ; i < scenesDataList.size() ; i++ ) {

            List<ScenesDevicesData> scenesDevicesDataList = scenesDataList.get(i).getSCENEDEVICE();

            for (int j = 0 ; i < scenesDevicesDataList.size() ; j++){

                ScenesDevice scenesDevice = new ScenesDevice();

                ScenesDevicesData scenesDevicesData = scenesDevicesDataList.get(j);

                scenesDevice.setGUID(scenesDevicesData.getSCENEDEVICEGUID());
                scenesDevice.setDEVICE_ID(scenesDevicesData.getDEVICEID());
                scenesDevice.setDEVICE_INFO(scenesDevicesData.getDEVICEINFO());
                scenesDevice.setDEVICE_VALUE(scenesDevicesData.getDEVICEVALUE());
                scenesDevice.setRELAYBOX_ID(scenesDevicesData.getRELAYBOXID());
                scenesDevice.setTYPE(Integer.parseInt(scenesDevicesData.getDEVICETYPE()));

                scenesDeviceList.add(scenesDevice);

            }
        }
        return  scenesDeviceList;
    }



    /**
     * ScenesTrigger convert to  ScenesTriggerData
     *
     * 场景触发条件数据 转换成服务器需要的数据格式
     *
     */
    public static List<ScenesTriggerData>  convertToScenesTriggerData( List<ScenesTrigger> scenesTriggerList ){

        //ScenesTriggerData
        List<ScenesTriggerData> scenesTriggerDataList  = new ArrayList<>();


        for (int i = 0 ; i < scenesTriggerList.size() ; i++ ) {

            ScenesTriggerData scenesTriggerData = new ScenesTriggerData();

            ScenesTrigger  scenesTrigger = scenesTriggerList.get(i);

            scenesTriggerData.setDEVICEID(scenesTrigger.getDEVICE_ID());
            scenesTriggerData.setTRIGGERINFO(scenesTrigger.getTRIGGER_INFO());
            scenesTriggerData.setSCENETRIGGERTYPE(scenesTrigger.getTYPE()+"");
            scenesTriggerData.setRELAYBOXID(scenesTrigger.getRELAYBOX_ID());
            scenesTriggerData.setSCENETRIGGERGUID(scenesTrigger.getGUID());
            scenesTriggerData.setTRIGGERVALUE(scenesTrigger.getTRIGGER_VALUE());

            scenesTriggerDataList.add(scenesTriggerData);
        }

        return  scenesTriggerDataList;
    }


    /**
     * ScenesDevicesData convert to  ScenesDevice
     *
     * 场景控制的设备数据 服务器返回的数据转换成本地存储数据
     */
    public static List<ScenesTrigger>  convertToScenesTrigger( List<ScenesData> scenesDataList ){

        //convertToScenesTrigger
        List<ScenesTrigger> scenesTriggerList  = new ArrayList<>();

        for (int i = 0 ; i < scenesDataList.size() ; i++ ) {

            List<ScenesTriggerData> scenesTriggerDataList = scenesDataList.get(i).getSCENETRIGGER();

            for (int j = 0 ; i < scenesTriggerDataList.size() ; j++){

                ScenesTrigger scenesTrigger = new ScenesTrigger();

                ScenesTriggerData scenesTriggerData = scenesTriggerDataList.get(j);

                scenesTrigger.setGUID(scenesTriggerData.getSCENETRIGGERGUID());
                scenesTrigger.setDEVICE_ID(scenesTriggerData.getDEVICEID());
                scenesTrigger.setTRIGGER_INFO(scenesTriggerData.getTRIGGERINFO());
                scenesTrigger.setTRIGGER_VALUE(scenesTriggerData.getTRIGGERVALUE());
                scenesTrigger.setRELAYBOX_ID(scenesTriggerData.getRELAYBOXID());
                scenesTrigger.setTYPE(Integer.parseInt(scenesTriggerData.getSCENETRIGGERTYPE()));

                scenesTriggerList.add(scenesTrigger);

            }
        }
        return  scenesTriggerList;
    }



    /**
     *  ScenesTrigger convert to UDPScenesTriggerData
     *
     *  转换成中继盒子需要的数据格式
     */
    public static List<UDPScenesDeviceData> convertToUDPScenesDeviceData(List<ScenesDevice> mNewScenesDeviceList) {

        List<UDPScenesDeviceData> udpScenesDeviceDatas = new ArrayList<>();

        for (int i = 0 ; i < mNewScenesDeviceList.size() ; i ++){

            UDPScenesDeviceData udpScenesDeviceData = new UDPScenesDeviceData();
            ScenesDevice scenesDevice = mNewScenesDeviceList.get(i);
            udpScenesDeviceData.setDEVICEID(scenesDevice.getDEVICE_ID());
            udpScenesDeviceData.setDEVICEVALUE(scenesDevice.getDEVICE_VALUE());
            udpScenesDeviceData.setRELAYBOXID(scenesDevice.getRELAYBOX_ID());
            udpScenesDeviceDatas.add(udpScenesDeviceData);
        }

        return udpScenesDeviceDatas;
    }


    /**
     *  ScenesTrigger convert to UDPScenesTriggerData
     *
     *  转换成中继盒子需要的数据格式
     */
    public static List<UDPScenesTriggerData> convertToUDPScenesTriggerData(List<ScenesTrigger> mNewScenesTriggerList) {

        List<UDPScenesTriggerData> udpScenesTriggerDatas = new ArrayList<>();

        for (int i = 0 ; i < mNewScenesTriggerList.size() ; i ++){

            UDPScenesTriggerData  udpScenesTriggerData = new UDPScenesTriggerData();

            ScenesTrigger scenesTrigger = mNewScenesTriggerList.get(i);
            udpScenesTriggerData.setDEVICEID(scenesTrigger.getDEVICE_ID());
            udpScenesTriggerData.setTRIGGERVALUE(scenesTrigger.getTRIGGER_VALUE());
            udpScenesTriggerData.setRELAYBOXID(scenesTrigger.getRELAYBOX_ID());

            udpScenesTriggerDatas.add(udpScenesTriggerData);
        }

        return udpScenesTriggerDatas;
    }


    /**
     *  Devices convert to DevicesData
     *
     *  转换成服务端需要的数据格式
     */
    public static List<DeviceData> convertToDevicesData(List<Devices> newDevices) {
           List<DeviceData> addDevicesData = new ArrayList<DeviceData>();
            for (int i = 0; i < newDevices.size(); i++) {
                Devices devices = newDevices.get(i);
                addDevicesData.add(new DeviceData(devices.getGUID(), devices.getROOM_GUID(), devices.getDEVICE_ID(), devices.getRELAY_ID(), devices.getTYPE() + "",
                        devices.getSUB_TYPE() + "", devices.getVALUE(), devices.getNAME(), devices.getDEVICE_ORDER() + "", devices.getUSE_FREQUENCY() + ""));
            }
            return addDevicesData;

    }


    /**
     *  DevicesData convert to Devices
     *
     *  转换成服务端需要的数据格式
     */
    public static List<Devices> convertToDevices(List<DeviceData> deviceDataList) {

        List<Devices> devicesList = new ArrayList<>();

        for (int i = 0 ; i < deviceDataList.size() ; i ++){
            Devices  device = new Devices();

            DeviceData deviceData = deviceDataList.get(i);

            device.setGUID(deviceData.getDEVICEID());
            device.setVALUE(deviceData.getVALUE());
            device.setRELAY_ID(deviceData.getRELAYBOXID());
            device.setTYPE(Integer.parseInt(deviceData.getTYPE()));
            device.setNAME(deviceData.getNAME());
            device.setDEVICE_ORDER(Integer.parseInt(deviceData.getDEVICEORDER()));
            device.setROOM_GUID(deviceData.getROOMID());
            device.setSUB_TYPE(Integer.parseInt(deviceData.getSUBTYPE()));
            device.setUSE_FREQUENCY(Integer.parseInt(deviceData.getUSEFREQUENCY()));

            devicesList.add(device);

        }
        return devicesList;

    }

    /**
     *  Devices convert to UDPDevicesData
     *  转换成中继盒子需要的数据格式
     */
    public static List<UDPDevicesData> convertToUDPDevicesData(List<Devices> newDevices) {
        List<UDPDevicesData> addDevicesDataList = new ArrayList<>();
        for (int i = 0; i < newDevices.size(); i++) {
            Devices device = newDevices.get(i);
            addDevicesDataList.add(new UDPDevicesData(device.getRELAY_ID(), device.getDEVICE_ID(), device.getVALUE()));
        }
        return  addDevicesDataList;
    }

    /**
     *  IRkeys convert to IRkeysData
     *  转换的数据格式  本地数据转换成服务器数据
     */
    public static List<IrkeysData> convertToIrKeysData(List<IRKey> irKeyList) {

        List<IrkeysData> irKeysDataList = new ArrayList<>();

        for (int i = 0; i < irKeyList.size(); i++) {

            IRKey irKey  = irKeyList.get(i);

            IrkeysData irkeysData = new IrkeysData();

            irkeysData.setBUTTONNAME(irKey.getBUTTON_NAME());
            irkeysData.setDEVICEID(irKey.getDEVICE_ID());
            irkeysData.setID(irKey.getGUID());
            irkeysData.setIFCODE("");
            irkeysData.setINDEX(irKey.getINDEX()+"");
            irkeysData.setKEY1(irKey.getKEY1());
            irkeysData.setKEY2(irKey.getKEY2());

            irKeysDataList.add(irkeysData);

            Log.e("HTTP", " 红外码库数据转换: irKeysDataList  " + irKeysDataList.size());
        }
        return  irKeysDataList;
    }

    /**
     *  IRkeysData convert to IRkeys
     *  转换的数据格式  服务器数据装换成本地数据
     */
    public static List<IRKey> convertToIrKeys(List<IrkeysData> irkeysDataList) {

        List<IRKey> irKeyList = new ArrayList<>();

        for (int i = 0; i < irkeysDataList.size(); i++) {

            IrkeysData irkeysData  = irkeysDataList.get(i);

            IRKey irKey = new IRKey();

            irKey.setBUTTON_NAME(irkeysData.getBUTTONNAME());
            irKey.setDEVICE_ID(irkeysData.getDEVICEID());
            irKey.setGUID(irkeysData.getID());
            irKey.setUSER_ID("");
            irKey.setINDEX(Integer.parseInt(irkeysData.getINDEX()));
            irKey.setKEY1(irKey.getKEY1());
            irKey.setKEY2(irKey.getKEY2());
            irKeyList.add(irKey);
        }
        return  irKeyList;
    }


    /**
     *  get ScenesControlDevices  属于控制设备的类型
     */
    public static  List<Devices> getScenesControlDevices(List<Devices> allDevices) {

        List<Devices> scenesControlDevicesList = new ArrayList<>();

        for (int i = 0; i < allDevices.size(); i++) {
            Devices  device = allDevices.get(i);
            switch (device.getTYPE()) {
                case DeviceHandleUtils.ADJUST_LAMP_SQL: //可调灯

                    scenesControlDevicesList.add(device);

                    break;
                case DeviceHandleUtils.UNADJUST_LAMP_SQL: //不可调灯

                    scenesControlDevicesList.add(device);

                    break;
                case DeviceHandleUtils.WIRELESS_SOCKET_SQL: //无线插座

                    scenesControlDevicesList.add(device);

                    break;
                case DeviceHandleUtils.DEFENCE_SQL: //安防设备

                    scenesControlDevicesList.add(device);

                    break;
                case DeviceHandleUtils.WIRELESS_CURTAIN_SQL: //无线窗帘

                    scenesControlDevicesList.add(device);

                    break;
                case DeviceHandleUtils.DEFENCE_PROBE_SQL: //探头性质

                    break;
                case DeviceHandleUtils.DEFENCE_TYPE_SQL: //探头类型

                    break;

                case DeviceHandleUtils.CAMERA_SQL: //摄像头

                    scenesControlDevicesList.add(device);
                    break;
                case DeviceHandleUtils.SCENE_SWITCH_SQL: //灯光联动控制器
                    scenesControlDevicesList.add(device);
                    break;
                case DeviceHandleUtils.IR_TV_SQL: //电视

                    scenesControlDevicesList.add(device);
                    break;
                case DeviceHandleUtils.IR_DVD_SQL: //DVD/VCD

                    scenesControlDevicesList.add(device);

                    break;
                case DeviceHandleUtils.IR_SOUND_SQL: //音响
                    scenesControlDevicesList.add(device);
                    break;
                case DeviceHandleUtils.IR_AIR_CONDITION_SQL: //空调

                    scenesControlDevicesList.add(device);
                    break;
                case DeviceHandleUtils.BACKGROUND_MUSIC_SQL: //红外背景音乐
                    scenesControlDevicesList.add(device);
                    break;
            }
        }
        return  scenesControlDevicesList;

    }


    /**
     *  get ScenesControlDevices  属于场景触发条件的类型
     */
    public static  List<Devices> getScenesTriggerDevices(List<Devices> allDevices) {

        List<Devices> scenesTriggerDevicesList = new ArrayList<>();

        for (int i = 0; i < allDevices.size(); i++) {
            Devices  device = allDevices.get(i);
            switch (device.getTYPE()) {
                case DeviceHandleUtils.SECURITY_PHOTOELECTRIC_SMOKE_DETECTOR_SQL: //光电烟雾探测器
                    scenesTriggerDevicesList.add(device);
                    break;

                case DeviceHandleUtils.SECURITY_LEAK_WATER_DETECTOR_SQL: //漏水探测器

                    scenesTriggerDevicesList.add(device);
                    break;

                case DeviceHandleUtils.SECURITY_CURTAIN_DETECTOR_SQL: //帘幕探测器
                    scenesTriggerDevicesList.add(device);

                    break;


                case DeviceHandleUtils.SECURITY_GAS_DETECTOR_SQL: //燃气探测器
                    scenesTriggerDevicesList.add(device);

                    break;

                case DeviceHandleUtils.SECURITY_SMOKE_DETECTOR_SQL: //烟雾探测器

                    scenesTriggerDevicesList.add(device);
                    break;

                case DeviceHandleUtils.SECURITY_GAS_LEAK_ALARM_SQL: //燃气泄漏报警器

                    scenesTriggerDevicesList.add(device);
                    break;

                case DeviceHandleUtils.SECURITY_EMERGENCY_BUTTON_SQL: //紧急按钮
                    scenesTriggerDevicesList.add(device);

                    break;
                case DeviceHandleUtils.SCENES_TIMER_SQL:
                    scenesTriggerDevicesList.add(device);
                    break;
            }
        }
        return  scenesTriggerDevicesList;
    }







}
