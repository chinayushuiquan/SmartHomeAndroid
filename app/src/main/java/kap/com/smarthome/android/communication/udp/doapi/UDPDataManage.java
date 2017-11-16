package kap.com.smarthome.android.communication.udp.doapi;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.communication.bean.base.UDP.UDPReceiverData;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPResponseMsgBase;
import kap.com.smarthome.android.communication.udp.constants.MsgINSIP;
import kap.com.smarthome.android.communication.udp.control.UDPCommunicationControl;
import kap.com.smarthome.android.communication.udp.inter.RelayBoxUDPDataListener;
import kap.com.smarthome.android.communication.udp.inter.UIThreadUDPDataChangeCallback;
import kap.com.smarthome.android.presenter.utils.JsonUtils;

/**
 * Created by Administrator on 2017/5/31 0031.
 */

public class UDPDataManage implements RelayBoxUDPDataListener {

    //回调监听的集合
    private List<UIThreadUDPDataChangeCallback> mCallBackListens = new ArrayList<>();

    /**
     * 单例模式
     */
    private UDPDataManage() {}

    private static UDPDataManage mUdpDataManageInstance;

    public synchronized  static UDPDataManage getInstance(){
        if(mUdpDataManageInstance == null){
            mUdpDataManageInstance = new UDPDataManage();
        }
        return mUdpDataManageInstance;
    }


    //初始化CommonUDPClient客户端发送数据
    public void sendUDPDataBox(String  data, String ipAddr, int port , int reTryCount,
                               int retryWaitTime){
        new UDPCommunicationControl(data, ipAddr, port , reTryCount , retryWaitTime, this).startUDP();
    }


    /**
     * 接收UDP信息，通过UDP信息的
     * @param udpReceiver
     */
    public  void  udpReceiverSuccess(final UDPReceiverData udpReceiver){
                UDPResponseMsgBase responseMsg = JsonUtils.stringToObject(udpReceiver.data , UDPResponseMsgBase.class);
                String  inSIP = responseMsg.getBODY().getINSTP();
                //取出信息的类型
                Log.i("UDP", " 接收消息的 INSIP = " + inSIP);
                if(inSIP.equals(MsgINSIP.ACK_SEARCH_REPETERBOX)){//搜索中继盒子返回信息
                    udpSearchRelayBoxListen(udpReceiver);
                }else if(inSIP.equals(MsgINSIP.ACK_EGISTER_REPETERBOX)){//连接中继盒子
                    udpRegisterRelayBoxListen(udpReceiver);
                }else if(inSIP.equals(MsgINSIP.ACK_REGISTER_DEVICE_TO_REPETERBOX)){//添加设备
                    udpNoteRelayBoxStartRegisterRFDeviceListen(udpReceiver);
                }else if(inSIP.equals(MsgINSIP.REPETER_BOX_UPDATA_TO_PHONE)){//
                    udpNoteRelayBoxAddDeviceListen(udpReceiver);
                }else if(inSIP.equals(MsgINSIP.ACK_ADD_REPETERBOX)){
                     udpAddRelayBoxListen(udpReceiver);
                }else if(inSIP.equals(MsgINSIP.ACK_UNBIND_ING)){
                    udpDeleteRelayBoxListen(udpReceiver);
                }else if(inSIP.equals(MsgINSIP.ACK_DELETE_DEVICE_LIST_ON_RELAY_BOX)){//删除多个设备
                    udpDeleteDevicesFromRelayBoxListen(udpReceiver);
                }else if(inSIP.equals(MsgINSIP.ACK_DELETE_ONE_DEVICE_ON_RELAY_BOX)){//删除单个设备
                    udpDeleteDevicesFromRelayBoxListen(udpReceiver);
                }else if(inSIP.equals(MsgINSIP.SELECTED_ACK)){
                    udpStartMatchRelayBoxListen(udpReceiver);
                }else if(inSIP.equals(MsgINSIP.UN_SELECTED_ACK)){
                    udpStopMatchRelayBoxListen(udpReceiver);
                }else if(inSIP.equals(MsgINSIP.REQ_IRSTUDYDATA_TO_PHONE)) {
                    udpLearnIrDataListen(udpReceiver);
                }else if(inSIP.equals(MsgINSIP.EXIT_REGISTER_IR_STUDY_TO_RELAYRBOX)){
                    udpNoteRelayBoxIRDeviceRegisterListen(udpReceiver);
                }else if(inSIP.equals(MsgINSIP.ACK_EXIT_REGISTER_IR_STUDY_TO_RELAYRBOX)){
                    udpNoteRelayBoxExitIRDeviceRegisterListen(udpReceiver);
                }else if(inSIP.equals(MsgINSIP.SECURITY_CODE_REQ)){//收到安防码值
                    udpGetSecurityCodeListen(udpReceiver);
                }else if(inSIP.equals(MsgINSIP.SECURITY_DEVICEID_ACK)){//收到安防设备的ID号
                    udpReceiverSecurityDeviceIdListen(udpReceiver);
                }else if(inSIP.equals(MsgINSIP.ADD_TERMINAL_DEVICELIST_ACK)){//添加设备
                    udpReceiverAddDevicesListen(udpReceiver);
                }else if(inSIP.equals(MsgINSIP.ACK_EXIT_REGISTER_DEVICE_TO_REPETERBOX)){
                    udpNoteRelayBoxExitRegisterRFDeviceListen(udpReceiver);
                }else if(inSIP.equals(MsgINSIP.DEL_SCENE_DATA_ACK)){

                }else if(inSIP.equals(MsgINSIP.PHONE_UPDATE_DEVICES_STATE)){//设备状态上报 , 收到设备信息，进行下一面的处理
                    //DeviceControlUtils.deviceStateUpdate(responseMsg);
                    udpUpdateOneDevicesStateFromRelayBoxListen(udpReceiver);

                }else if(inSIP.equals(MsgINSIP.ACK_PHONE_CONTROL_DEVICES)){
                    udpControlDeviceByRelayBoxListen(udpReceiver);

                }else if (inSIP.equals(MsgINSIP.ACK_UPDATE_SCENE_DATA_TO_RELAY_BOX)){ //添加场景的回调
                    udpSendSceneDataToRelayBoxListen(udpReceiver);
                }

         }




    public  void  udpReceiverFailure(){

    }

    /**
     * 添加监听到监听列集合中
     * @param listen
     */
    public void addConnectionListener(UIThreadUDPDataChangeCallback listen) {
        if (listen == null)
            return;
          synchronized (mCallBackListens) {
              if (!mCallBackListens.contains(listen)) {
                  mCallBackListens.add(listen);
            }
        }
    }

    /**
     * 移除一个监听
     * @param listen
     */
    public boolean removeConnectionListener(UIThreadUDPDataChangeCallback listen) {
        synchronized (mCallBackListens) {
            if(mCallBackListens.contains(listen))
             return  mCallBackListens.remove(listen);
        }
        return false;
    }

//-----------------------------------------------------------------------UDP返回
    /**
     *  1 搜索中继盒子的返回信息
     * @param udpReceiver
     */
    @Override
    public void udpSearchRelayBoxListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiSearchRelayBoxSuccessCallback(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }


    /**
     * 2 添加中继盒子的返回信息
     */
    @Override
    public void udpAddRelayBoxListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiAddRelayBoxSuccessCallback(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }


    /**
     * 3 删除中继盒子的返回信息
     */

    public void udpDeleteRelayBoxListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiDeleteRelayBoxSuccessCallback(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }



    /**
     * 4 对中继盒子进行注册 准备接受添加设备的指令
     */
    @Override
    public void udpRegisterRelayBoxListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiRegisterRelayBoxCallback(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }


    /**
     * 5	通知中继盒子进入RF设备注册状态
     */
    @Override
    public void udpNoteRelayBoxStartRegisterRFDeviceListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiNoteRelayBoxStartRegisterRFDeviceCallback(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }



    /**
     * 6 通知中继盒子退出RF设备注册状态
     */
    @Override
    public void udpNoteRelayBoxExitRegisterRFDeviceListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiNoteRelayBoxExitRegisterRFDeviceCallback(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }



    /**
     * 7 终端发送删除指定设备消息到中继盒子
     */
    @Override
    public void udpDeleteDevicesFromRelayBoxListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiDeleteDevicesFromRelayBoxCallback(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }


    /**
     * 8 终端发送删除指定设备消息到中继盒子
     */
    @Override
    public void udpStartMatchRelayBoxListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiStartMatchRelayBoxCallback(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }


    /**
     * 9 终端发送删除指定设备消息到中继盒子
     */
    @Override
    public void udpStopMatchRelayBoxListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiStopMatchRelayBoxCallback(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }


    /**
     * 10 终端发送删除指定设备消息到中继盒子
     */
    @Override
    public void udpLearnIrDataListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiLearnIrDataSaveListen(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }


    /**
     * 11	通知中继盒子进入红外设备注册状态
     */
    @Override
    public void udpNoteRelayBoxIRDeviceRegisterListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiNoteRelayBoxIRDeviceRegisterCallback(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }


    /**
     * 12 通知中继盒子退出红外设备注册状态
     * @return
     */
    @Override
    public void udpNoteRelayBoxExitIRDeviceRegisterListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiNoteRelayBoxExitIRDeviceRegisterCallback(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }

    /**
     * 13 收到安防码值
     * @return
     */
    @Override
    public void udpGetSecurityCodeListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiGetSecurityCodeCallback(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }


    /**
     * 14 收到安防设备的ID号
     * @return
     */
    @Override
    public void udpReceiverSecurityDeviceIdListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiReceiverSecurityDeviceIdCallback(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }


    /**
     * 15 收到添加设备的返回监听
     */
    @Override
    public void udpReceiverAddDevicesListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiReceiverAddDevicesCallback(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }



    /**
     * 16 设备控制 返回监听
     * @param udpReceiver
     */
    @Override
    public void udpControlDeviceByRelayBoxListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiControlDeviceByRelayBoxCallback(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }


    /**
     * 收到一个设备的状态更新信息
     * @param udpReceiver
     */
    @Override
    public void udpUpdateOneDevicesStateFromRelayBoxListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiUpdateOneDevicesStateFromRelayBoxCallback(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }


    /**
     * 1.16	终端发送场景联动数据到中继盒子
     */
    @Override
    public void udpSendSceneDataToRelayBoxListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiSendSceneDataToRelayBoxCallback(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }


    //-----------------------------------------------------------------------------------------------


    /**
     * 1.3 APP读取中继盒子的网络配置信息
     */
    @Override
    public void udpReadRelayBoxNetConfigListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiReadRelayBoxNetConfigCallback(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }



    /**
     *
     * 1.4 终端写入中继盒子的网络配置信息
     */
    @Override
    public void udpWriteRelayBoxNetConfigListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiWriteRelayBoxNetConfigCallback(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }


    /**
     * 1.8	中继盒子上传 RF设备注册信息到终端
     * @param udpReceiver
     */
    @Override
    public void udpNoteRelayBoxAddDeviceListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiNoteRelayBoxAddDeviceCallback(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }





    /**
     * 1.12	 终端发送一组学习的有效红外数据给中继盒子（中继盒子上传到云端）
     */
    @Override
    public void udpSendIRStudyDataToRelayBoxListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiSendIRStudyDataToRelayBoxCallback(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }



    /**
     * 1.15	终端获取所有设备列表和设备状态
     */
    @Override
    public void udpUpdateAllDevicesStateFromRelayBoxListen(UDPReceiverData udpReceiver) {
        for (UIThreadUDPDataChangeCallback l : mCallBackListens) {
            try {
                l.uiUpdateAllDevicesStateFromRelayBoxCallback(udpReceiver);
            } catch (final RuntimeException e) {
                removeConnectionListener(l);
            }
        }
    }




}

