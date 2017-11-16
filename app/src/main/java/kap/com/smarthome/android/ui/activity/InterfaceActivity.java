package kap.com.smarthome.android.ui.activity;

import android.support.v7.app.AppCompatActivity;

import kap.com.smarthome.android.communication.bean.base.UDP.UDPReceiverData;
import kap.com.smarthome.android.communication.udp.doapi.UDPDataManage;
import kap.com.smarthome.android.communication.udp.inter.UIThreadUDPDataChangeCallback;

/**
 * Created by Administrator on 2017/9/18 0018.
 */

public class InterfaceActivity  extends AppCompatActivity  implements  UIThreadUDPDataChangeCallback {

    @Override
    protected void onStart() {
        super.onStart();
        UDPDataManage.getInstance().addConnectionListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        UDPDataManage.getInstance().removeConnectionListener(this);
    }


    @Override
    public void uiSearchRelayBoxSuccessCallback(UDPReceiverData item) {

    }

    @Override
    public void uiAddRelayBoxSuccessCallback(UDPReceiverData item) {

    }


    @Override
    public void uiDeleteRelayBoxSuccessCallback(UDPReceiverData item) {

    }

    @Override
    public void uiRegisterRelayBoxCallback(UDPReceiverData item) {

    }

    @Override
    public void uiReadRelayBoxNetConfigCallback(UDPReceiverData item) {

    }

    @Override
    public void uiWriteRelayBoxNetConfigCallback(UDPReceiverData item) {

    }

    @Override
    public void uiNoteRelayBoxStartRegisterRFDeviceCallback(UDPReceiverData item) {

    }

    @Override
    public void uiNoteRelayBoxExitRegisterRFDeviceCallback(UDPReceiverData item) {

    }

    @Override
    public void uiNoteRelayBoxIRDeviceRegisterCallback(UDPReceiverData item) {

    }

    @Override
    public void uiNoteRelayBoxExitIRDeviceRegisterCallback(UDPReceiverData item) {

    }

    @Override
    public void uiSendIRStudyDataToRelayBoxCallback(UDPReceiverData item) {

    }

    @Override
    public void uiControlDeviceByRelayBoxCallback(UDPReceiverData item) {

    }

    @Override
    public void uiUpdateAllDevicesStateFromRelayBoxCallback(UDPReceiverData item) {

    }

    @Override
    public void uiSendSceneDataToRelayBoxCallback(UDPReceiverData item) {

    }

    @Override
    public void uiDeleteDevicesFromRelayBoxCallback(UDPReceiverData item) {

    }

    @Override
    public void uiNoteRelayBoxAddDeviceCallback(UDPReceiverData item) {

    }

    @Override
    public void uiStartMatchRelayBoxCallback(UDPReceiverData item) {

    }

    @Override
    public void uiStopMatchRelayBoxCallback(UDPReceiverData item) {

    }

    @Override
    public void uiLearnIrDataSaveListen(UDPReceiverData item) {

    }

    @Override
    public void uiGetSecurityCodeCallback(UDPReceiverData item) {

    }

    @Override
    public void uiReceiverSecurityDeviceIdCallback(UDPReceiverData data) {

    }

    @Override
    public void uiReceiverAddDevicesCallback(UDPReceiverData udpReceiver) {

    }

    @Override
    public void uiUpdateOneDevicesStateFromRelayBoxCallback(UDPReceiverData udpReceiver) {

    }
}