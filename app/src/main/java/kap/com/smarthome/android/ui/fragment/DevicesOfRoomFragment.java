package kap.com.smarthome.android.ui.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.DATABean.DeviceData;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPReceiverData;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPResponseMsgBase;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.communication.udp.constants.UDPContants;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.RelayBox;
import kap.com.smarthome.android.presenter.constants.AllConstants;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.BeanDataConvertUtils;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.DeviceHandleUtils;
import kap.com.smarthome.android.presenter.control.RelayBoxUDPHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.presenter.utils.JsonUtils;
import kap.com.smarthome.android.ui.activity.AddRfDevicesActivity;
import kap.com.smarthome.android.ui.activity.DevicesTvControlActivity;
import kap.com.smarthome.android.ui.activity.EditDevicesActivity;
import kap.com.smarthome.android.ui.adapter.DevicesListRecyclerViewAdapter;
import kap.com.smarthome.android.ui.dialog.Curtain;
import kap.com.smarthome.android.ui.dialog.DeviceControlDialog;
import kap.com.smarthome.android.ui.dialog.Light;
import kap.com.smarthome.android.ui.view.UIPullRefreshView;

/**
 * Created by yushq on 2017/8/24 0024.
 */

public class DevicesOfRoomFragment extends BaseFragment {

    private static final String TAG = "CHRIS";
    private RecyclerView mRecyclerView;
    private Activity mActivity;
    private View mView;
    private String mRoomUUID = null;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    //ListView刷新的View
    private UIPullRefreshView refreshableView;


    /**
     * 显示的数据 , 得到单个房间下的数据
     */
    private List<Devices> mDevicesList;

    private Devices currentControlDevice;


    private Devices mDeleteDevices = null;
    private int mDeletePosition = -1 ;
    private String  mDeleteDeviceOfRelayBoxID = "";

    /**
     * RecyclerView的适配器
     */
    private DevicesListRecyclerViewAdapter devicesListRecyclerViewAdapter;


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mDeletePosition != -1) {
                if(mDevicesList != null &&  mDevicesList.size() > 0 ) {
                    mDevicesList.remove(mDeletePosition);
                    devicesListRecyclerViewAdapter.notifyItemRemoved(mDeletePosition);
                }

            }
        }
    };

    public static DevicesOfRoomFragment newInstance(String uuid) {
        DevicesOfRoomFragment fragment = new DevicesOfRoomFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AllConstants.ROOM_UUID, uuid);
        fragment.setArguments(bundle);
        return fragment;
    }

    public DevicesOfRoomFragment() {
        Log.e(TAG, "DevicesOfRoomFragment:");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate:");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_chose_room_devices, container, false);
        //获取当前Room的guid
        getRoomUUID();
        initData();
        initRecyclerView(mView);
        initRecycleViewClickListener();
        initSwipeRefreshLayout(mView);
        Log.e(TAG, "onCreateView: " + mRoomUUID);
        return mView;
    }


    /**
     * 当添加新设备的时候会执行onResume中的 ifAddNewDevices 方法
     */
    @Override
    public void onResume() {
        super.onResume();
        ifAddNewDevices();
    }

    /**
     * 当添加新设备的在OnResume中的方法
     */
    private void ifAddNewDevices() {
        if (AllVariable.IS_BROAD_CAST_ADD_NEW_DEVICE) {
            updateDevices();
        }
    }


    /**
     * 更新设备
     */
    private void updateDevices() {
        List<Devices> devicesList = DataBaseHandle.queryRoomDevices(mRoomUUID);
        if (mDevicesList != null) {
            mDevicesList.clear();
            for (int i = 0; i < devicesList.size(); i++) {
                mDevicesList.add(devicesList.get(i));
            }
        }
        devicesListRecyclerViewAdapter.notifyDataSetChanged();
        Log.w(TAG, "onDismiss: 更新设备");
    }


    /**
     * 下拉刷新的
     * SwipeRefreshLayout初始化
     *
     * @param view
     */
    private void initSwipeRefreshLayout(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.devices_fragment_swipe_container);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                List<DeviceData> deviceDatas = new ArrayList<DeviceData>();

                ServerCommunicationHandle.queryDevices(deviceDatas, new UIHttpCallBack() {
                    @Override
                    public void success(Object object) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure(Object object) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        });
    }

    /**
     * 获取Room的UUID
     */
    private void getRoomUUID() {
        Bundle bundle = getArguments();
        mRoomUUID = bundle.getString(AllConstants.ROOM_UUID);
    }


    private void initRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.devices_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        devicesListRecyclerViewAdapter = new DevicesListRecyclerViewAdapter(mActivity, mDevicesList);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(devicesListRecyclerViewAdapter);
    }


    private void initRecycleViewClickListener() {
        devicesListRecyclerViewAdapter.setOnItemClickListener(new DevicesListRecyclerViewAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemControlClick(View view, int position) {

                // TODO: 2017/9/28 0028 点击设备，进行不同的操作  进行控制
                currentControlDevice = mDevicesList.get(position);

                RelayBoxUDPHandle.createConnectToRelayBox();

                deviceControl(currentControlDevice);
            }


            //进入编辑页面
            @Override
            public void onItemEditClick(View view, int position) {

                Intent intent = new Intent(mActivity, EditDevicesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("edit_device", mDevicesList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onDeleteIconClick(View view, int position) {

                if(AllVariable.CONNECT_RELAY) {

                    mDeleteDevices = mDevicesList.get(position);
                    mDeletePosition = position;

                    showLoadingDialog("");
                    RelayBox relayBox = DataBaseHandle.queryOneRelayBox(mDeleteDevices.getRELAY_ID());
                    mDeleteDeviceOfRelayBoxID = relayBox.getBOX_ID();

                    List<Devices> devicesList = new ArrayList<>();
                    devicesList.add(mDeleteDevices);
                    RelayBoxUDPHandle.deleteDevicesList(devicesList);

                }else {

                    Toast.makeText(mActivity, "请在局域网环境删除设备！", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private DeviceControlDialog mDeviceControlDialog;
    long lastTime = System.currentTimeMillis();


    private void deviceControl(Devices controlDevice) {
        if(AllVariable.NO_CONNECT){
            Toast.makeText(mActivity , "当前无网络连接，请检查网络!" , Toast.LENGTH_SHORT).show();
        }else {
            switch (controlDevice.getTYPE()){
                case DeviceHandleUtils.WIRELESS_CURTAIN_SQL:
                    //if (System.currentTimeMillis() - curtainLastTime > 200) {
                    mDeviceControlDialog = new Curtain(mActivity, controlDevice);
                    mDeviceControlDialog.show();
                    // }
                    // curtainLastTime = System.currentTimeMillis();

                    mDeviceControlDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {

                            updateDevices();
                            Log.w("UDP", "onDismiss: 设备控制结束， 设备更新");

                        }
                    });
                    break;

                case DeviceHandleUtils.UNADJUST_LAMP_SQL:


                    break;
                case DeviceHandleUtils.ADJUST_LAMP_SQL:
                    // 可调光
                    //if (System.currentTimeMillis() - lastTime > 200) {
                    mDeviceControlDialog = new Light(mActivity, controlDevice);
                    mDeviceControlDialog.show();
                    //}
                    break;

                case DeviceHandleUtils.IR_TV_SQL:
                    //电视
                    Intent intent = new Intent(mActivity, DevicesTvControlActivity.class);
                    Bundle  bundle = new Bundle();
                    bundle.putSerializable("ir_device" , controlDevice);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;

                case DeviceHandleUtils.IR_AIR_CONDITION_SQL:
                    //电视
                    Intent intent1 = new Intent(mActivity, DevicesTvControlActivity.class);
                    Bundle  bundle1 = new Bundle();
                    bundle1.putSerializable("ir_device" , controlDevice);
                    intent1.putExtras(bundle1);
                    startActivity(intent1);
                    break;
            }
        }
    }


    /**
     * 获取该房间所拥有的设备
     */
    protected void initData(){
        //2017-09-22
        mDevicesList = DataBaseHandle.queryRoomDevices(mRoomUUID);

    }


    /**
     * 是否进入编辑状态
     * @param isEdit
     * @return
     */
    public boolean setPostionRoomEditState(boolean isEdit){
        if(devicesListRecyclerViewAdapter != null) {
            devicesListRecyclerViewAdapter.setEditState(isEdit);
            return true;
        }
        return  false;
    }

    public boolean getPostionRoomEditState(){
        if (devicesListRecyclerViewAdapter != null){
           return  devicesListRecyclerViewAdapter.getEditState();
        }
        return  false;
    }


    /**
     * 删除设备的回调
     * @param udpReceiver
     */
    @Override
    public void uiDeleteDevicesFromRelayBoxCallback(UDPReceiverData udpReceiver) {
        super.uiDeleteDevicesFromRelayBoxCallback(udpReceiver);

        UDPResponseMsgBase responseMsg = JsonUtils.stringToObject(udpReceiver.data , UDPResponseMsgBase.class);

        if(responseMsg.getHEAD().getDEVICEID().equals(mDeleteDeviceOfRelayBoxID)) {

            if (responseMsg.getBODY().getRESULT() == UDPContants.UDP_RESPONSE_SUCCESS) {

                if (DataBaseHandle.deleteDevice(mDeleteDevices)) {


                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(mDeletePosition != -1) {
                                if (mDevicesList != null && mDevicesList.size() > 0) {
                                    mDevicesList.remove(mDeletePosition);
                                    devicesListRecyclerViewAdapter.notifyItemRemoved(mDeletePosition);
                                }
                            }
                        }
                    });

                    List<Devices> devicesList = new ArrayList<>();
                    devicesList.add(mDeleteDevices);
                    List<DeviceData> deleteDeviceDataList = BeanDataConvertUtils.convertToDevicesData(devicesList);
                    ServerCommunicationHandle.deleteDevices(deleteDeviceDataList, new UIHttpCallBack() {
                        @Override
                        public void success(Object object) {
                            dismissLoadingDialog();
                            Toast.makeText(mActivity, "云端删除设备成功！", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void failure(Object object) {
                            dismissLoadingDialog();
                            Toast.makeText(mActivity, "云端删除设备失败！", Toast.LENGTH_SHORT).show();

                        }
                    });

                } else {
                    dismissLoadingDialog();
                    Toast.makeText(mActivity, "本地删除设备失败！", Toast.LENGTH_SHORT).show();
                }
            }

        }

    }

    /**
     * 设备控制  返回ACK
     * @param udpReceiver
     */
    @Override
    public void uiControlDeviceByRelayBoxCallback(UDPReceiverData udpReceiver) {
        super.uiControlDeviceByRelayBoxCallback(udpReceiver);
        Log.e("UDP", "uiControlDeviceByRelayBoxCallback: -------设备控制返回ACK--------");
    }

    /**
     * 控制设备之前先发送一个连接中继盒子的信息， 如果连接成功才判断使用局域网UDP控制设备方式
     * @param item
     */
    @Override
    public void uiRegisterRelayBoxCallback(UDPReceiverData item) {
        super.uiRegisterRelayBoxCallback(item);
        AllVariable.CONNECT_RELAY = true;
    }


    @Override
    public void uiUpdateOneDevicesStateFromRelayBoxCallback(UDPReceiverData udpReceiver) {
        super.uiUpdateOneDevicesStateFromRelayBoxCallback(udpReceiver);
        Log.e("UDP", "uiUpdateOneDevicesStateFromRelayBoxCallback: -------设备状态信息上报--------");
        UDPResponseMsgBase responseMsg = JsonUtils.stringToObject(udpReceiver.data , UDPResponseMsgBase.class);
        int   result = responseMsg.getBODY().getRESULT();
        if(result == 0){//成功
            if(responseMsg.getBODY().getDEVICEID().equals(currentControlDevice.getDEVICE_ID())){
                currentControlDevice.setVALUE(responseMsg.getBODY().getVALUE());
                DataBaseHandle.updateOnDevice(currentControlDevice);
            }
        }
    }
}
