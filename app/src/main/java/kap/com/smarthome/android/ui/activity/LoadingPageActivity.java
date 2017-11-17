package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

import com.baidu.android.pushservice.BasicPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.DATABean.DeviceData;
import kap.com.smarthome.android.communication.bean.base.DATABean.RoomData;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPReceiverData;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.Room;
import kap.com.smarthome.android.presenter.broadcast.PushTestReceiver;
import kap.com.smarthome.android.presenter.constants.AllConstants;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.RelayBoxUDPHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.presenter.control.SharedPreferencesHandle;
import kap.com.smarthome.android.presenter.utils.ResourcesUtils;
import kap.com.smarthome.android.presenter.utils.UUIDUtils;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

public class LoadingPageActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        //koti
        PushManager.startWork(this, PushConstants.LOGIN_TYPE_API_KEY , PushTestReceiver.API_KEY);
        BasicPushNotificationBuilder bBuilder = new BasicPushNotificationBuilder();

      /*  PushNotificationBuilder pushNotificationBuilder = new PushNotificationBuilder() {
            @Override
            public Notification construct(Context context) {
                return null;
            }
        };

        pushNotificationBuilder.setStatusbarIcon(R.mipmap.m_icon);
        pushNotificationBuilder.setNotificationTitle("收到报警信息");
        pushNotificationBuilder.setNotificationText("");
        */
        PushManager.setDefaultNotificationBuilder(this, bBuilder); //使自定义channel生效


        /**
         * 加载页面停留3秒，3秒超时后自动跳转到主页面
         */
        new Handler().postDelayed(new Runnable() {
            public void run() {

                String  userId = SharedPreferencesHandle.initSharedPreferencesHandle(getApplicationContext()).getCurrentLoginUserId();

                if(userId != null && !userId.isEmpty()){

                    AllVariable.CURRENT_USER_ID = userId;

                }else {
                    AllVariable.CURRENT_USER_ID = AllConstants.DEFAULT_USER_ID;

                }
                startActivity(new Intent(LoadingPageActivity.this, MainHomeActivity.class));
                finish();
            }
        }, 3000);


        /**
         * 连接中继盒子，类似心跳消息
         */
        RelayBoxUDPHandle.createConnectToRelayBox();

        /**
         * 初始化话结束之后需要设置为false , 下次进入不再初始化判断是否是安装App后第一次打开
         *
         * 如果是第一次打开需要进行数据库的初始化
         */
        if(SharedPreferencesHandle.initSharedPreferencesHandle(getApplicationContext()).isFirstOpenApp()) {
            initDefaultData();
        }

    }

    /**
     * 初始化本地数据
     */
    private void initDefaultData() {

        initDefaultRoom();

        initAddDefaultDevices();

        //初始化数据库数据之后，设置为false
        SharedPreferencesHandle.initSharedPreferencesHandle(getApplicationContext()).setFirstOpenAppFlag(false);
    }


    /**
     * 添加默认房间
     * 初始化七个默认房间
     */
    private void initDefaultRoom() {

        String[] baseRooms = ResourcesUtils.getStringArray(this, R.array.default_rooms);

        //本地需要的房间 数据结构 Room
        List<Room> roomList = new ArrayList<>();

        //服务器需要的房间 数据结构RoomData
        List<RoomData> roomDataList = new ArrayList<RoomData>();

        for (int i= 0; i < baseRooms.length ; i++){

            String uuid = UUIDUtils.getUUID();

            Room room = new Room(null, uuid, baseRooms[i], i , i);
            roomList.add(room);

            RoomData roomData = new RoomData(uuid , baseRooms[i] ,i+"" , i+"");
            roomDataList.add(roomData);
        }

        //添加默认房间数据到数据库
        if(DataBaseHandle.insertRoomList(roomList)){
            //上传房间数据
            ServerCommunicationHandle.addRoom(roomDataList , new UIHttpCallBack() {
                @Override
                public void success(Object object) {

                }
                @Override
                public void failure(Object object) {


                }
            });
        }
    }


    /**
     * 添加默认设备  eg：闹钟设备
     *
     @Index(unique  = true)
     @NotNull
     private String  GUID ;
     private String ROOM_GUID;
     @NotNull
     private String DEVICE_ID;
     @NotNull
     private String RELAY_ID;
     private int TYPE;
     private int SUB_TYPE;
     private String VALUE;
     @NotNull
     private String NAME;
     private int DEVICE_ORDER;
     private int USE_FREQUENCY;
     */
    private void initAddDefaultDevices() {

        Devices device =  new Devices();

        device.setGUID(UUIDUtils.getUUID());
        device.setDEVICE_ID("0000040000000000");
        device.setRELAY_ID("0000040000000000");
        device.setNAME("定时器");
        device.setTYPE(4);


        List<DeviceData> newDeviceDataList = new ArrayList<DeviceData>();
        //newDeviceDataList.add(deviceData);

        ServerCommunicationHandle.addDevices(newDeviceDataList, new UIHttpCallBack() {

            @Override
            public void success(Object object) {


            }

            @Override
            public void failure(Object object) {


            }
        });
    }


    /**
     * 建立连接的信息回调
     * @param item
     */
    @Override
    public void uiRegisterRelayBoxCallback(UDPReceiverData item) {
            super.uiRegisterRelayBoxCallback(item);
             AllVariable.CONNECT_RELAY = true;
             Log.e("UDP", "中继盒子连接" + AllVariable.CONNECT_RELAY);
         }



    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {}

}
