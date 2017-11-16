package kap.com.smarthome.android.presenter.control;

import android.content.Context;

import java.util.List;

import kap.com.smarthome.android.communication.bean.base.UDP.UDPResponseBodyBase;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPResponseMsgBase;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.presenter.constants.AllVariable;

/**
 * Created by yushq on 2017/11/7 0007.
 *
 */

public class DeviceControlUtils {

    /**
     * 通过设备发送信息
     * @param device
     * @param value_state
     * @param box_ip
     */
      public static void  deviceControlSendMsg(Devices device, String value_state, String box_ip){
          if(AllVariable.WIFI_CONNECT){


          }
      }


    /**
     * 云端透传
     * @param control_json
     */
      public static  void deviceControlSendMsg(String control_json , Context context){

          ServerCommunicationHandle.controlDevice(control_json, new UIHttpCallBack() {
              @Override
              public void success(Object object) {

              }

              @Override
              public void failure(Object object) {

              }
          });

      }


    /**
     * 设备状态更新
     * @param responseMsg
     */
    public static void deviceStateUpdate(UDPResponseMsgBase responseMsg) {
        int   result = responseMsg.getBODY().getRESULT();
        if(result == 0){//成功
            UDPResponseBodyBase udpResponseBodyBase = responseMsg.getBODY();
            List<Devices>  allDevices = DataBaseHandle.queryAllDevices();
            for (int i = 0 ; i <allDevices.size() ; i++){
                Devices device = allDevices.get(i);
                if(device.getDEVICE_ID().equals(udpResponseBodyBase.getDEVICEID())){
                    device.setVALUE(udpResponseBodyBase.getVALUE());
                    DataBaseHandle.updateOnDevice(device);
                }
            }
        }
    }
}