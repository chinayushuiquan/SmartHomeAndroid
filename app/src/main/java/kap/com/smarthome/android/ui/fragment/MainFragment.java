package kap.com.smarthome.android.ui.fragment;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.RelayBox;
import kap.com.smarthome.android.data.bean.Scenes;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.BeanDataConvertUtils;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.DeviceHandleUtils;
import kap.com.smarthome.android.presenter.control.RelayBoxUDPHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.ui.activity.DevicesTvControlActivity;
import kap.com.smarthome.android.ui.activity.LoginActivity;
import kap.com.smarthome.android.ui.activity.MainHomeActivity;
import kap.com.smarthome.android.ui.adapter.MainFragmentDevicesGvAdapter;
import kap.com.smarthome.android.ui.adapter.MainFragmentScenesGvAdapter;
import kap.com.smarthome.android.ui.dialog.Curtain;
import kap.com.smarthome.android.ui.dialog.DeviceControlDialog;
import kap.com.smarthome.android.ui.dialog.Light;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;


public class MainFragment extends BaseFragment {

    private Activity mActivity;
    private GridView mScenesGridview;
    private GridView mDevicesGridview;

    private List<Scenes> mScenesList;

    private List<Devices> mDevicesList;

    private Devices mCurrentControlDevice = null;


    private List<RelayBox> mRelayBoxList = null;

    public static MainFragment newInstance(String param1) {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    public MainFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRelayBoxList = DataBaseHandle.queryAllRelayBox();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //初始化常用场景
        mScenesGridview = (GridView) view.findViewById(R.id.main_scenes_gv);
        mScenesList = DataBaseHandle.queryAllScenes();

        if(mScenesList.size() > 4){
            mScenesList =  mScenesList.subList(0,4);
        }

        MainFragmentScenesGvAdapter scenesGvAdapter = new MainFragmentScenesGvAdapter(mActivity , mScenesList);
        mScenesGridview.setAdapter(scenesGvAdapter);

        scenesGvAdapter.setMainHomeScenesControlClickListen(new MainFragmentScenesGvAdapter.MainHomeScenesControlClickListen() {
            @Override
            public void onExecute(View view, int position) {
                //立即执行
                Toast.makeText(mActivity , "执行场景" + position , Toast.LENGTH_LONG).show();

                if(AllVariable.WIFI_CONNECT && AllVariable.CONNECT_RELAY){//本地控制如果超时

                    //UDP发送到中继盒子
                    for (int i = 0 ; i < mRelayBoxList.size() ; i++){
                        RelayBox relayBox = mRelayBoxList.get(i);
                        RelayBoxUDPHandle.executeOneScenes(mScenesList.get(position).getSCENE_ID() , relayBox.getBOX_ID(), relayBox.getIP());
                    }
                }else if(AllVariable.MOBILE_CONNECT || !AllVariable.CONNECT_RELAY){//远程控制
                    //UDP发送到中继盒子
                    for (int i = 0 ; i < mRelayBoxList.size() ; i++){
                        RelayBox relayBox = mRelayBoxList.get(i);

                        String httpData =  RelayBoxUDPHandle.executeOneScenesHttpData(mScenesList.get(position).getSCENE_ID() , relayBox.getBOX_ID());

                        ServerCommunicationHandle.controlScenes(httpData, new UIHttpCallBack() {
                            @Override
                            public void success(Object object) {
                                Log.e("HTTP", "场景远程控制成功");
                            }
                            @Override
                            public void failure(Object object) {
                                Log.e("HTTP", "场景远程控制失败");
                            }
                        });
                    }
                }
            }
        });

        //初始化常用设备
        mDevicesGridview = (GridView) view.findViewById(R.id.main_devices_gv);

        //所有的可以控制的设备
        mDevicesList = BeanDataConvertUtils.getScenesControlDevices(DataBaseHandle.queryAllDevices());

        horizontal_layout();

        //初始化顶部的标题栏，添加标题栏的点击事件
        initMyTopBar(view);
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    /**
     * 设置横向的Gridview
     * 适配宽度和高度
     */
    public void horizontal_layout(){
        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int allWidth = (int) (135 * mDevicesList.size() * density);
        int itemWidth = (int) (130 * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(allWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        mDevicesGridview.setLayoutParams(params);// 设置GirdView布局参数
        mDevicesGridview.setColumnWidth(itemWidth);// 列表项宽
        mDevicesGridview.setHorizontalSpacing(1);// 列表项水平间距
        mDevicesGridview.setStretchMode(GridView.NO_STRETCH);
        mDevicesGridview.setNumColumns(4);//总长度
        MainFragmentDevicesGvAdapter devicesGvAdapter  =  new MainFragmentDevicesGvAdapter(mActivity, mDevicesList);
        mDevicesGridview.setAdapter(devicesGvAdapter);

        mDevicesGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                mCurrentControlDevice = mDevicesList.get(i);
                deviceControl(mCurrentControlDevice);

            }
        });
    }


    /**
     * 初始化话顶部的导航栏
     * @param view
     */
    private void initMyTopBar(View view) {
        MyTopBarBuilder.builder(view).setLeftImage(R.drawable.home_sliding_menu_icon)
        .setTitleText(R.string.home_title).setRightImage(R.drawable.home_top_login_icon)
        .setLeftImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用MainHomeActivity的方法 滑出侧边栏
                if(mActivity != null  && mActivity instanceof MainHomeActivity){
                    ((MainHomeActivity) mActivity).showSlidingDrawerLayout();
                }
            }
        }).setRightImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity() , LoginActivity.class));
            }
        });
    }


    private DeviceControlDialog mDeviceControlDialog;

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
}
