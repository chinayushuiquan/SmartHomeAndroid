package kap.com.smarthome.android.ui.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.DATABean.DeviceData;
import kap.com.smarthome.android.communication.bean.base.DATABean.UDPDevicesData;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPReceiverData;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPResponseMsgBase;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.RelayBox;
import kap.com.smarthome.android.data.bean.Room;
import kap.com.smarthome.android.presenter.constants.AllConstants;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.BeanDataConvertUtils;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.DataToBeanConversion;
import kap.com.smarthome.android.presenter.control.RelayBoxUDPHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.presenter.utils.JsonUtils;
import kap.com.smarthome.android.ui.adapter.AddSecurityDeviceListAdapter;
import kap.com.smarthome.android.ui.adapter.RoomChooseListAdapter;
import kap.com.smarthome.android.ui.adapter.ScanDevicesListRecyclerAdapter;
import kap.com.smarthome.android.ui.view.MyPopupWindow;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

/**
 * Created by yushq on 2017/8/31 0031.
 *
 * 添加RF 设备
 *
 */

public class AddRfDevicesActivity extends  BaseActivity  implements View.OnClickListener{

    //刷新搜索到的设备列表
    private static final int UPDATE_DEVICES_LIST = 0;
    //弹出RF设备列表选择的pop_window
    private static final int SHOW_RF_LIST_POP_WINDOW = 1;

    private Button mScanControlBtn;
    private ImageView mScanAnimIv;

    //显示扫描时间的字体
    private TextView mScanTimeTv;

    private boolean isSearch;

    //设备需要选择房间，需要房间列表
    private List<Room> mRoomsList;

    //当前从哪个房间跳转过来的 ， 默认设备添加到此房间
    private  String   mCurrentRoomGuid;

    //扫描的设备
    private List<Devices> mSearchDevicesList;

    //是否选择添加该设备
    private List<Boolean> mSearchDevicesIsSelect;
    private ScanDevicesListRecyclerAdapter.OnAddDevicesRecyclerItemClickListener  onAddDevicesRecyclerItemClickListener;

    private RecyclerView mSearchScanDevicesRecyclerView;
    private ScanDevicesListRecyclerAdapter mSearchDevicesRecycleViewAdapter;

    //实现弹出PopupWindow的时候设备背景界面变暗
    private Window mWindow;
    private float windowAlpha = 0.3f;

    //安防设备列表弹窗 是否显示
    private  boolean isShowRfDeviceList = false;
    private  String  boxIp = "255.255.255.255";

    // 扫描时间常量
    private static  final int  GET_CODE_TIME = 100*1000;
    private static  final int  CHANGE_TIME = 1000;

    //
    private static  boolean IS_CLICK_DONE = false;

    private List<RelayBox>  mRelayBoxList = null;



    private Handler addRelayHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATE_DEVICES_LIST:
                    updateAdapter((Devices) msg.obj);
                    break;
                case SHOW_RF_LIST_POP_WINDOW:
                    if(isShowRfDeviceList)
                        return;
                    chooseOneSecurityDevice((UDPReceiverData)msg.obj);
                    isShowRfDeviceList = true;
                    break;

            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_rf_devices);

        //添加控件和初始化一些数据
        initData();

        //设置ScanTimeTv的特殊字体
        setTextTypeFace();

        //实例化显示添加设备的RecyclerView
        initAdapter();

        //初始化adapter房间选择的监听
        initAdapterListen();
    }

    /**
     * 添加控件和初始化一些数据
     */
    private void initData() {

        //默认从哪个房间跳转过来就添加到哪个房间
        mCurrentRoomGuid = getIntent().getStringExtra(AllConstants.CURRENT_ROOM_GUID);

        mScanControlBtn = (Button) findViewById(R.id.devices_search_start_scan_btn);
        mScanAnimIv = (ImageView) findViewById(R.id.devices_search_circle_iv);
        mScanTimeTv = (TextView) findViewById(R.id.devices_scan_time_note);

        mScanControlBtn.setOnClickListener(this);

        mSearchDevicesList = new ArrayList<>();
        mSearchDevicesIsSelect = new ArrayList<>();

        //从数据库查出所有的房间列表
        mRoomsList = DataBaseHandle.queryAllRooms();

        mRelayBoxList = DataBaseHandle.queryAllRelayBox();
    }

    /**
     *  实例化显示添加设备的RecyclerView
     */
    private void initAdapter() {
        mSearchScanDevicesRecyclerView = (RecyclerView) findViewById(R.id.devices_add_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mSearchScanDevicesRecyclerView.setLayoutManager(linearLayoutManager);
        mSearchDevicesRecycleViewAdapter = new ScanDevicesListRecyclerAdapter(this , mSearchDevicesList , mSearchDevicesIsSelect , mRoomsList);
        mSearchScanDevicesRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mSearchScanDevicesRecyclerView.setAdapter(mSearchDevicesRecycleViewAdapter);
    }


    /**
     * 初始化adapter房间选择的监听
     * 给要添加的设备选择要加入的房间 ， 把房间的Id 号发出去
     */
    private void initAdapterListen(){
        onAddDevicesRecyclerItemClickListener = new ScanDevicesListRecyclerAdapter.OnAddDevicesRecyclerItemClickListener(){
            //点击选择房间的按钮
            @Override
            public void onNoteSelectRoomTvClick(View view, final int devicesPosition){

                View popWindView = getLayoutInflater().inflate(R.layout.popupwindow_choose_room , null);

                ListView  roomListView = (ListView) popWindView.findViewById(R.id.room_list);

                final RoomChooseListAdapter  roomChooseListAdapter = new RoomChooseListAdapter(AddRfDevicesActivity.this, mRoomsList);

                roomListView.setAdapter(roomChooseListAdapter);

                final MyPopupWindow popWindow = new MyPopupWindow(AddRfDevicesActivity.this , popWindView , R.style.right_popupWindow_anim);

                //弹出PopupWindow设置窗口背景变暗
                mWindow = getWindow();
                popWindow.setWindowAlpha(mWindow, windowAlpha);
                popWindow.showAtLocation(view , Gravity.CENTER_VERTICAL | Gravity.RIGHT , 0 , 0);

                roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int roomPosition, long id) {

                        Room room = (Room) roomChooseListAdapter.getItem(roomPosition);

                        if(room != null){
                            //将选择的房间的GUID传入进去
                            mSearchDevicesList.get(devicesPosition).setROOM_GUID(room.getGUID());
                            mSearchDevicesRecycleViewAdapter.notifyDataSetChanged();
                            popWindow.dismiss();
                        }
                    }
                });
            }
        };

        mSearchDevicesRecycleViewAdapter.setOnItemClickListener(onAddDevicesRecyclerItemClickListener);
    }


    /**
     * 设置字体
     */
    private void setTextTypeFace() {
        Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/Demi-Bold.ttf");
        mScanTimeTv.setTypeface(typeFace);
        mScanTimeTv.setText("0%");
    }

    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {
         myTopBarBuilder
                 .setLeftImage(R.drawable.back_icon)
                 .setTitleText(R.string.add_devices)
                 .setTitleColor(R.color.white)
                 .setRightText(R.string.done)
                 .setRightTextColor(R.color.orange)
                 .setLeftImageOnClickListener(new View.OnClickListener(){
                     @Override
                     public void onClick(View v) {
                         finish();
                     }
                 }).setRightTextOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v) {

                 //是否点击完成按钮
                 IS_CLICK_DONE = true;

                 //发送命令让中继 退出设备注册状态
                 RelayBoxUDPHandle.setRelayBoxExitAddDeviceState();

             }
         });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.devices_search_start_scan_btn:
                if(isSearch){
                    if(mScanAnimIv.getAnimation() != null){
                        mScanAnimIv.getAnimation().cancel();
                        mScanAnimIv.clearAnimation();
                    }

                    //退出 设备注册 添加状态
                    isSearch = false;
                    RelayBoxUDPHandle.setRelayBoxExitAddDeviceState();
                    mScanTimeTv.setText("100%");
                    downTimer.cancel();
                    mScanControlBtn.setText(getString(R.string.start_add_devices));

                }else {
                    if (AllVariable.CONNECT_RELAY) {
                        if (mRelayBoxList != null && mRelayBoxList.size() > 0) {
                            Animation searchAnim = AnimationUtils.loadAnimation(AddRfDevicesActivity.this, R.anim.devices_search_scan_anim);
                            LinearInterpolator interpolator = new LinearInterpolator();
                            searchAnim.setInterpolator(interpolator);
                            mScanAnimIv.startAnimation(searchAnim);
                            isSearch = true;

                            RelayBoxUDPHandle.setRelayBoxRegisterAddDevicesState(mRelayBoxList);
                            downTimer.start();
                            mScanControlBtn.setText(getString(R.string.stop_add_devices));
                            IS_CLICK_DONE = false;
                        } else {
                            Toast.makeText(AddRfDevicesActivity.this, "请先添加中继盒子", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(AddRfDevicesActivity.this, "请在局域网环境添加设备！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }


    /**
     * UDP通信回复
     * 1 . 开始进入注册状态 的回调
     * @param item
     */
    @Override
    public void uiNoteRelayBoxStartRegisterRFDeviceCallback(UDPReceiverData item) {
        super.uiNoteRelayBoxStartRegisterRFDeviceCallback(item);
         Log.e("UDP", ": 进入RF注册状态 ");

    }


    /**
     * 退出RF注册状态
     * @param item
     */
    @Override
    public void uiNoteRelayBoxExitRegisterRFDeviceCallback(UDPReceiverData item) {
        super.uiNoteRelayBoxExitRegisterRFDeviceCallback(item);

        /**
         * 点击完成按钮和 结束扫描注册的按钮都会收到该回调信息，所以需要进行判断
         */
        if(IS_CLICK_DONE) {//是否点击完成按钮 ，让设备瑞出注册状态

            List<Devices> selectDevices = mSearchDevicesRecycleViewAdapter.getmNewSelectDevicesList();

            if (selectDevices == null && selectDevices.size() == 0) {

                Toast.makeText(AddRfDevicesActivity.this, "请先勾选添加的设备", Toast.LENGTH_SHORT).show();

            } else {
                List<UDPDevicesData> udpDevicesDataList = BeanDataConvertUtils.convertToUDPDevicesData(selectDevices);
                //UDP 中继盒子添加设备
                RelayBoxUDPHandle.addRfDevice(udpDevicesDataList);
            }
        }
    }

    /**
     * UDP通信回复
     *
     * 2. 收到一个自有的RF设备  确定匹配的设备类型
     *
     * @param udpReceiver
     */
    @Override
    public void uiNoteRelayBoxAddDeviceCallback(UDPReceiverData udpReceiver){
        super.uiNoteRelayBoxAddDeviceCallback(udpReceiver);
        Devices device = DataToBeanConversion.rfJsonConvertToDevice(udpReceiver.data);
        listNewRFDevices(device);
    }


    /**
     *
     * UDP通信回复
     *
     * 3. 收到安防设备的安防码值  需要用户自己选择具体的设备类型 返回给中继盒子
     * @param udpReceiver
     */
    @Override
    public void uiGetSecurityCodeCallback(final UDPReceiverData udpReceiver){
        super.uiGetSecurityCodeCallback(udpReceiver);
        getSecurityCodeShowPopWindow(udpReceiver);

    }


    /**
     * UDP 通信回复
     *
     * 4. 收到安防设备的设备ID = 和  消息2 uiNoteRelayBoxAddDeviceCallback 类似处理 将设备显示到需要添加的设备列表中
     */
    @Override
    public void uiReceiverSecurityDeviceIdCallback(UDPReceiverData udpReceiver) {
        super.uiReceiverSecurityDeviceIdCallback(udpReceiver);
        Devices device = DataToBeanConversion.securityJsonConvertToDevice(udpReceiver.data);
        listNewRFDevices(device);
    }


    /**
     * UDP 通信回复
     *
     * 5. 中继盒子已经添加设备
     *
     * @param udpReceiver
     */
    @Override
    public void uiReceiverAddDevicesCallback(UDPReceiverData udpReceiver) {

        super.uiReceiverAddDevicesCallback(udpReceiver);

        List<Devices> selectDevices =  mSearchDevicesRecycleViewAdapter.getmNewSelectDevicesList();

        if(DataBaseHandle.insertDevices(selectDevices)){

            AllVariable.IS_BROAD_CAST_ADD_NEW_DEVICE = true;

            List<DeviceData>  HTTPDeviceDataList  =  BeanDataConvertUtils.convertToDevicesData(selectDevices);

            //服务器添加设备
            ServerCommunicationHandle.addDevices(HTTPDeviceDataList, new UIHttpCallBack() {

                @Override
                public void success(Object object) {


                }

                @Override
                public void failure(Object object) {


                }
            });
        }else {
            Toast.makeText(AddRfDevicesActivity.this, "添加设备失败" , Toast.LENGTH_SHORT).show();
        }

        finish();

    }


    private void listNewRFDevices(Devices device) {
        if(device != null){
            device.setROOM_GUID(mCurrentRoomGuid);
            setAdapterUpdate(device);
        }
    }



    private void getSecurityCodeShowPopWindow(UDPReceiverData udpResponse) {
        Message msg = new Message();
        msg.what = SHOW_RF_LIST_POP_WINDOW;
        msg.obj = udpResponse;
        addRelayHandler.sendMessage(msg);
    }


    /**
     * 刷新搜索到的设备列表
     * @param device
     */
    private void setAdapterUpdate(Devices device) {
        Message msg = new Message();
        msg.what = UPDATE_DEVICES_LIST;
        msg.obj = device;
        addRelayHandler.sendMessage(msg);
    }

    /**
     * 搜索到中继盒子在handler中刷新界面
     */
    private void updateAdapter(Devices device) {
        //判断新搜到的中继盒子是否已经存在
        mSearchDevicesList.add(device);
        mSearchDevicesIsSelect.add(false);
        mSearchDevicesRecycleViewAdapter.notifyDataSetChanged();
    }


    /**
     * 弹出PopWindow 让用户选择安防设备的类型  完成true
     *
     * @param udpReceiverData
     */
    private void chooseOneSecurityDevice(final UDPReceiverData udpReceiverData) {

        UDPResponseMsgBase udpResponse = JsonUtils.stringToObject(udpReceiverData.data , UDPResponseMsgBase.class);

        final String SECURITY_CODE = udpResponse.getBODY().getDEVICEID();
        final String RELAY_BOX_ID = udpResponse.getHEAD().getDEVICEID();

        //弹出一个界面让用户选择需要添加的安防设备
        LayoutInflater  inflater = getLayoutInflater();
        View popWindView = inflater.inflate(R.layout.popupwindow_choose_security , null);
        ListView  listView = (ListView) popWindView.findViewById(R.id.security_list);

        final AddSecurityDeviceListAdapter securityDeviceListAdapter = new AddSecurityDeviceListAdapter(AddRfDevicesActivity.this);

        listView.setAdapter(securityDeviceListAdapter);

        final MyPopupWindow popWindow = new MyPopupWindow(AddRfDevicesActivity.this , popWindView , R.style.right_popupWindow_anim);
        //弹出PopupWindow设置窗口背景变暗
        mWindow = getWindow();
        popWindow.setWindowAlpha(mWindow, windowAlpha);
        popWindow.showAtLocation( mScanTimeTv , Gravity.CENTER_VERTICAL , 0 , 0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String  SECURITYSUBTYPE = (String) securityDeviceListAdapter.getItem(position);
                RelayBoxUDPHandle.getSecurityDeviceIdReq(SECURITYSUBTYPE , SECURITY_CODE ,RELAY_BOX_ID , udpReceiverData.ipAddr);
                popWindow.dismiss();
            }
        });

        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(isShowRfDeviceList){
                    isShowRfDeviceList = false;
                }
                popWindow.setWindowAlpha(mWindow,1);
            }
        });


    }


    /**
     * 实现设备扫描添加的100秒倒计时功能  完成true
     */
    private CountDownTimer downTimer = new CountDownTimer(GET_CODE_TIME , CHANGE_TIME) {
        @Override
        public void onTick(long l) {

            mScanTimeTv.setText(100 - (l / 1000) + "%");
        }

        @Override
        public void onFinish() {

            if(isSearch){
                if(mScanAnimIv.getAnimation() != null){
                    mScanAnimIv.getAnimation().cancel();
                    mScanAnimIv.clearAnimation();
                    isSearch = false;
                    //退出设备添加界面
                    RelayBoxUDPHandle.setRelayBoxExitAddDeviceState();
                    mScanTimeTv.setText("0%");
                }
            }
        }
    };


}
