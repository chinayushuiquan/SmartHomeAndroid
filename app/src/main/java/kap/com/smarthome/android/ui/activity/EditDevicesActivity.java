package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.DATABean.DeviceData;
import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPResponseBaseMsg;
import kap.com.smarthome.android.communication.http.constants.HTTPMsgINSIP;
import kap.com.smarthome.android.communication.http.constants.HttpResponseCode;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.presenter.constants.AllConstants;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.DeviceHandleUtils;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.ui.adapter.DeviceBelongRoomAdapter;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

/**
 * Created by yushq on 2017/10/19 0019.
 */

public class EditDevicesActivity extends  BaseActivity{

    private GridView mDeviceBelongRoomGv;

    private TextInputEditText mDeviceNameEdit;

    private DeviceBelongRoomAdapter mGvAdapter;

    private Devices mEditDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_device);

        mDeviceNameEdit = (TextInputEditText) findViewById(R.id.device_name_edit);

        mDeviceBelongRoomGv = (GridView) findViewById(R.id.device_choose_room_gv);

        mGvAdapter  = new DeviceBelongRoomAdapter(this);

        mDeviceBelongRoomGv.setAdapter(mGvAdapter);

        mEditDevice = (Devices) getIntent().getSerializableExtra("edit_device");

        mDeviceNameEdit.setHint(mEditDevice.getNAME());

        typeDeviceInitView(mEditDevice.getTYPE());

    }

    /**
     * 根据设备的类型 显示不同的编辑界面  （不同的设备的编辑参数不同）
     *
     * 主要类型有 ：
     *  1. RF 控制设备
     *  2. RF 安防设备
     *  3. IR 红外遥控设备
     *  4. 其余第三方设备
     *
     *  设备类型字段定义在 DeviceHandleUtils 类中
     *
     * @param type
     */
    private void typeDeviceInitView(int type) {
        switch (type){
            case DeviceHandleUtils.ADJUST_LAMP_SQL:

                break;
            case DeviceHandleUtils.UNADJUST_LAMP_SQL:

                break;
            case DeviceHandleUtils.WIRELESS_CURTAIN_SQL:

                break;
        }

    }

    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {
        myTopBarBuilder
                .setLeftImage(R.drawable.back_icon_black)
                .setTitleText(R.string.add_room)
                .setRightText(R.string.done)
                .setRightTextColor(R.color.orange)
                .setLeftImageOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //2017-09-21
                if(mDeviceNameEdit.getText().length() <= 0){
                    Toast.makeText(EditDevicesActivity.this, getString(R.string.add_room_name_notnull), Toast.LENGTH_SHORT).show();
                    return;
                }

                final String  deviceName = mDeviceNameEdit.getText().toString().trim();

                List<DeviceData> deviceDatas = new ArrayList<DeviceData>();

                // guid name order, type
                /*this.ID = ID;
                this.ROOMID = ROOMID;
                this.DEVICEID = DEVICEID;
                this.RELAYBOXID = RELAYBOXID;
                this.TYPE = TYPE;
                this.SUBTYPE = SUBTYPE;
                this.VALUE = VALUE;
                this.NAME = NAME;
                this.DEVICEORDER = DEVICEORDER;
                this.USEFREQUENCY = USEFREQUENCY;*/

                deviceDatas.add(new DeviceData(mEditDevice.getGUID() , mEditDevice.getROOM_GUID(), mEditDevice.getGUID(), mEditDevice.getRELAY_ID(),
                        mEditDevice.getTYPE()+"", mEditDevice.getSUB_TYPE()+"", mEditDevice.getVALUE(), deviceName , mEditDevice.getDEVICE_ORDER()+"" , mEditDevice.getUSE_FREQUENCY()+""));

                showLoadingDialog(deviceName);

                ServerCommunicationHandle.updateDevices(deviceDatas, new UIHttpCallBack() {
                    @Override
                    public void success(Object object) {
                        if(object != null) {
                            final HTTPResponseBaseMsg httpResponseBaseMsg = (HTTPResponseBaseMsg) object;
                            if (httpResponseBaseMsg.getBODY().getINSTP().equals(HTTPMsgINSIP.UPDATE_ROOM_RSP)) {
                                if (httpResponseBaseMsg.getBODY().getRESULT().equals(HttpResponseCode.SUCCESS)) {
                                    mEditDevice.setNAME(deviceName);
                                    Log.e("HTTP", "success: 更新设备 = " + mEditDevice.toString());
                                    DataBaseHandle.updateOnDevice(mEditDevice);
                                    Intent intent = new Intent(AllConstants.BROAD_CAST_ADD_ROOM);
                                    sendBroadcast(intent);
                                    //设置跟新成功标志
                                    AllVariable.IS_BROAD_CAST_ADD_ROOM = true;
                                    dismissLoadingDialog();
                                    finish();
                                }
                            }
                        }
                    }

                    @Override
                    public void failure(Object object) {
                        dismissLoadingDialog();
                        Toast.makeText(EditDevicesActivity.this, getString(R.string.update_room_fail), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }





}
