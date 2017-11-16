package kap.com.smarthome.android.presenter.control;

import com.google.gson.Gson;


import kap.com.smarthome.android.communication.bean.base.UDP.UDPResponseMsgBase;
import kap.com.smarthome.android.communication.bean.extend.UDP.UDPSearchRelayBoxResponseMsg;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.RelayBox;

/**
 * Created by Administrator on 2017/9/25 0025.
 */

public class DataToBeanConversion {

    /**
     * 接收到json数据转换成RelayBox
     * @return
     */
    public static  RelayBox udpJsonConvertToRelay(String data){
        Gson gson = new Gson();

        UDPSearchRelayBoxResponseMsg udpRelayBoxMsg = gson.fromJson(data, UDPSearchRelayBoxResponseMsg.class);
        RelayBox relayBox = new RelayBox();

        relayBox.setBOX_ID(udpRelayBoxMsg.getHEAD().getDEVICEID());//BOXID
        relayBox.setIP(udpRelayBoxMsg.getBODY().getIP());//IP
        relayBox.setHARDWARE_VERSION(udpRelayBoxMsg.getBODY().getHARDWAREVERSION());//硬件版本号
        relayBox.setSOFTWARE_VERSION(udpRelayBoxMsg.getBODY().getSOFTWAREVERSION());//软件版本号
        relayBox.setMACHINE_CODE(udpRelayBoxMsg.getBODY().getMACHINECODE());//机器码

        return relayBox;
    }


    /**
     * UDP接收到Json数据转成 设备实例, 设置设备的ID
     */
    public static Devices rfJsonConvertToDevice(String data){
        Gson gson = new Gson();
        UDPResponseMsgBase udpJsonHeadBody = gson.fromJson(data, UDPResponseMsgBase.class);
        Devices device = new Devices();
        //设备本身的ID
        device.setDEVICE_ID(udpJsonHeadBody.getBODY().getDEVICEID());
        //中继盒子的ID
        device.setRELAY_ID(udpJsonHeadBody.getHEAD().getDEVICEID());
        return device;
    }


    /**
     * UDP接收到安防设备的 Json数据转成 设备实例, 设置设备的ID
     */
    public static Devices securityJsonConvertToDevice(String data){
        Gson gson = new Gson();
        UDPResponseMsgBase udpJsonHeadBody = gson.fromJson(data, UDPResponseMsgBase.class);
        Devices device = new Devices();
        //设备本身的ID
        device.setDEVICE_ID(udpJsonHeadBody.getBODY().getSECURITYDEVICEID());
        //中继盒子的ID
        device.setRELAY_ID(udpJsonHeadBody.getHEAD().getDEVICEID());
        return device;
    }


}
