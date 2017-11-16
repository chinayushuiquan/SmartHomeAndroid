package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.DATABean.AllBeanData;
import kap.com.smarthome.android.communication.bean.base.DATABean.DeviceData;
import kap.com.smarthome.android.communication.bean.base.DATABean.IrkeysData;
import kap.com.smarthome.android.communication.bean.base.DATABean.RelayBoxData;
import kap.com.smarthome.android.communication.bean.base.DATABean.RoomData;
import kap.com.smarthome.android.communication.bean.base.DATABean.ScenesData;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponseQueryAllDataBody;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponseQueryAllDataMsg;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.IRKey;
import kap.com.smarthome.android.data.bean.RelayBox;
import kap.com.smarthome.android.data.bean.Room;
import kap.com.smarthome.android.data.bean.Scenes;
import kap.com.smarthome.android.data.bean.ScenesDevice;
import kap.com.smarthome.android.data.bean.ScenesTrigger;
import kap.com.smarthome.android.presenter.control.BeanDataConvertUtils;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;


public class SystemSetActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout mRelayBoxRl;
    private RelativeLayout mSyncDataRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_set);
        mRelayBoxRl = (RelativeLayout) findViewById(R.id.system_relay_box_rl);
        mSyncDataRl = (RelativeLayout) findViewById(R.id.system_sync_data_rl);
        mRelayBoxRl.setOnClickListener(this);
        mSyncDataRl.setOnClickListener(this);
    }


    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {
        myTopBarBuilder.setLeftImage(R.drawable.back_icon_black)
                .setTitleText(R.string.system_set)
                .setLeftImageOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.system_relay_box_rl:
                startActivity(new Intent(this, RelayBoxActivity.class));
                break;
            case R.id.system_sync_data_rl:
                //startActivity(new Intent(this, SyncDataActivity.class));
                showLoadingDialog("同步数据");
                ServerCommunicationHandle.queryOtherUserAllData(new UIHttpCallBack() {
                    @Override
                    public void success(Object object){
                        Log.e("HTTP", "success: ");
                        final HTTPResponseQueryAllDataMsg response = (HTTPResponseQueryAllDataMsg) object;
                        HTTPResponseQueryAllDataBody body = (HTTPResponseQueryAllDataBody)response.getBODY();

                        if(body.getRESULT().equals("0")){
                            AllBeanData allData = body.getDATA();
                            /**
                             * 房间
                             */
                            List<RoomData> roomDataList = allData.getROOMDATA();
                            List<Room> roomList = BeanDataConvertUtils.convertToRoom(roomDataList);

                            /**
                             * 中继盒子
                             */
                            List<RelayBoxData> relayBoxDataList = allData.getBOXDATA();
                            List<RelayBox> relayBoxList = BeanDataConvertUtils.convertToRelayBox(relayBoxDataList);

                            /**
                             * 设备
                             */
                            List<DeviceData> deviceDataList = allData.getDEVICEDATA();
                            List<Devices>  devicesList = BeanDataConvertUtils.convertToDevices(deviceDataList);

                            /**
                             * 场景
                             */
                            List<ScenesData> scenesDataList = allData.getSCENEDATA();
                            List<Scenes> scenesList = BeanDataConvertUtils.convertToScenes(scenesDataList);

                            /**
                             * 场景设备
                             */
                            List<ScenesDevice> scenesDevicesList = BeanDataConvertUtils.convertToScenesDevice(scenesDataList);
                            /**
                             * 场景触发条件
                             */
                            List<ScenesTrigger> scenesTriggerList = BeanDataConvertUtils.convertToScenesTrigger(scenesDataList);

                            /**
                             * 红外码库表
                             */
                            List<IrkeysData>  irkeysDataList = allData.getREDCODEDATA();
                            List<IRKey>  irKeyList =  BeanDataConvertUtils.convertToIrKeys(irkeysDataList);

                            if(DataBaseHandle.deleteALlData()){
                                if(DataBaseHandle.insertAllData(roomList ,relayBoxList , devicesList , scenesList , scenesDevicesList , scenesTriggerList , irKeyList)){

                                    Toast.makeText(SystemSetActivity.this , "数据同步成功" , Toast.LENGTH_LONG).show();
                                    dismissLoadingDialog();
                                }else {
                                    Toast.makeText(SystemSetActivity.this , "数据保存失败" , Toast.LENGTH_LONG).show();
                                    dismissLoadingDialog();
                                }
                            } else {
                                Toast.makeText(SystemSetActivity.this , "数据删除失败" , Toast.LENGTH_LONG).show();
                                dismissLoadingDialog();
                            }
                        }else {
                            Toast.makeText(SystemSetActivity.this , "同步数据失败失败" , Toast.LENGTH_LONG).show();
                            dismissLoadingDialog();
                        }
                    }
                    @Override
                    public void failure(Object object) {
                        Toast.makeText(SystemSetActivity.this , "服务器返回失败" , Toast.LENGTH_LONG).show();
                        dismissLoadingDialog();
                    }
                });
            break;
        }
    }
}
