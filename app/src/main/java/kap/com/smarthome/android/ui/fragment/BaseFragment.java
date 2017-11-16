package kap.com.smarthome.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import kap.com.smarthome.android.communication.bean.base.UDP.UDPReceiverData;
import kap.com.smarthome.android.communication.udp.doapi.UDPDataManage;
import kap.com.smarthome.android.communication.udp.inter.UIThreadUDPDataChangeCallback;
import kap.com.smarthome.android.ui.view.MyLoadingDialog;

/**
 * Created by Administrator on 2017/10/19 0019.
 */

public class BaseFragment extends Fragment implements UIThreadUDPDataChangeCallback {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UDPDataManage.getInstance().addConnectionListener(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        UDPDataManage.getInstance().removeConnectionListener(this);
    }

    private MyLoadingDialog myLoadingDialog ;

    /**
     * 显示注册的loadingDialog
     */
    public void showLoadingDialog(String info) {
        if(myLoadingDialog == null) {
            myLoadingDialog = new MyLoadingDialog(getContext()).setMessage(info);
        }
        myLoadingDialog.show();
    }


    /**
     * 关闭注册的loadingDialog
     */
    public void dismissLoadingDialog() {
        if(myLoadingDialog != null && myLoadingDialog.isShowing()) {
            myLoadingDialog.dismiss();
        }
        myLoadingDialog = null;
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
    public void uiReadRelayBoxNetConfigCallback(UDPReceiverData item) {

    }

    @Override
    public void uiWriteRelayBoxNetConfigCallback(UDPReceiverData item) {

    }

    @Override
    public void uiRegisterRelayBoxCallback(UDPReceiverData item) {

    }

    @Override
    public void uiNoteRelayBoxStartRegisterRFDeviceCallback(UDPReceiverData item) {

    }

    @Override
    public void uiNoteRelayBoxExitRegisterRFDeviceCallback(UDPReceiverData item) {

    }

    @Override
    public void uiNoteRelayBoxAddDeviceCallback(UDPReceiverData item) {

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
