package kap.com.smarthome.android.ui.fragment;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.DATABean.DeviceData;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.data.bean.RelayBox;
import kap.com.smarthome.android.data.bean.Scenes;
import kap.com.smarthome.android.data.bean.ScenesDevice;
import kap.com.smarthome.android.data.bean.ScenesTrigger;
import kap.com.smarthome.android.presenter.constants.AllConstants;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.RelayBoxUDPHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.ui.activity.AddScenesActivity;
import kap.com.smarthome.android.ui.activity.EditScenesActivity;
import kap.com.smarthome.android.ui.adapter.ScenesListRecyclerAdapter;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;


public class ScenesFragment extends BaseFragment {

    private RecyclerView mScenesListRecyclerView;
    private ScenesListRecyclerAdapter  mRecyclerAdapter;

    private MyTopBarBuilder mTopBarBuilder;
    private Activity mActivity;

    //场景数据集合
    private List<Scenes> mALLScenesList;

    //场景控制设备数据集合
    private List<ScenesDevice> mALLScenesDevicesList;

    //场景触发条件数据集合
    private List<ScenesTrigger> mALLScenesTriggerList;

    private Scenes mCurrentDeleteScenes = null;
    private int mDeletePosition;


    private List<RelayBox> mRelayBoxList = null;

    private SwipeRefreshLayout mSwipeRefreshLayout;


    public static ScenesFragment newInstance(String param1) {
        ScenesFragment fragment = new ScenesFragment();
        return fragment;
    }

    public ScenesFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity  = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity.registerReceiver(mAddScenesReceiver , new IntentFilter(AllConstants.BROAD_CAST_ADD_SCENES));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scenes, container, false);

        initMyTopBar(view);

        initData();

        initRecycleView(view);

        initSwipeRefreshLayout(view);

        return view;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity.unregisterReceiver(mAddScenesReceiver);
    }

    /**
     * 初始化话顶部的导航栏
     * @param view
     */
    private void initMyTopBar(View view) {
        mTopBarBuilder = MyTopBarBuilder.builder(view)
                       .setTitleBgRes(R.color.white)
                       .setLeftText(R.string.edit)
                       .setTitleText(R.string.scenes_control)
                       .setRightImage(R.drawable.add)
                       .setLeftTextOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               boolean isEdit = setScenesEditState();
                               setTitleLeftCancel(isEdit);
                           }
                       }).setRightImageOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(mActivity , AddScenesActivity.class));
                            }
                        });


    }

    /**
     * 初始化所有的场景数据
     */
    protected void initData(){

        if(mALLScenesList != null){
            mALLScenesList.clear();
            mALLScenesList = null;
        }

        mRelayBoxList = DataBaseHandle.queryAllRelayBox();

        mALLScenesList = DataBaseHandle.queryAllScenes();
        for (int i = 0 ; i < mALLScenesList.size() ; i++){
            Log.e("DATA", " 场景数据 " +  mALLScenesList.get(i).toString());
        }


        if(mALLScenesDevicesList != null){
            mALLScenesDevicesList.clear();
            mALLScenesDevicesList = null;
        }
        mALLScenesDevicesList = DataBaseHandle.queryAllScenesDevices();
        for (int i = 0 ; i < mALLScenesDevicesList.size() ; i++){
            Log.i("DATA", " 场景控制设备数据 " +  mALLScenesDevicesList.get(i).toString());
        }

        if(mALLScenesTriggerList != null){
            mALLScenesTriggerList.clear();
            mALLScenesTriggerList = null;
        }
        mALLScenesTriggerList = DataBaseHandle.queryAllScenesTrigger();
        for (int i = 0 ; i < mALLScenesTriggerList.size() ; i++){
            Log.w("DATA", " 场景触发条件数据 " +  mALLScenesTriggerList.get(i).toString());
        }

    }


    /**
     * 初始化界面的UI
     * @param view
     */
    private void initRecycleView(View view) {
        mScenesListRecyclerView = (RecyclerView) view.findViewById(R.id.scenes_list_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mScenesListRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerAdapter = new ScenesListRecyclerAdapter(getActivity() , mALLScenesList, mALLScenesDevicesList , mALLScenesTriggerList);


        ScenesListRecyclerAdapter.ScenesControlClickListen scenesControlClickListen = new ScenesListRecyclerAdapter.ScenesControlClickListen() {
            @Override
            public void onExecute(View view, int position) {
                //立即执行
                Toast.makeText(mActivity , "执行场景" + position , Toast.LENGTH_LONG).show();

                if(AllVariable.WIFI_CONNECT && AllVariable.CONNECT_RELAY){//本地控制如果超时

                    //UDP发送到中继盒子
                    for (int i = 0 ; i < mRelayBoxList.size() ; i++){
                        RelayBox relayBox = mRelayBoxList.get(i);
                        RelayBoxUDPHandle.executeOneScenes(mALLScenesList.get(position).getSCENE_ID() , relayBox.getBOX_ID(), relayBox.getIP());
                    }

                }else if(AllVariable.MOBILE_CONNECT || !AllVariable.CONNECT_RELAY){//远程控制
                    //UDP发送到中继盒子
                    for (int i = 0 ; i < mRelayBoxList.size() ; i++){
                        RelayBox relayBox = mRelayBoxList.get(i);

                        String httpData =  RelayBoxUDPHandle.executeOneScenesHttpData(mALLScenesList.get(position).getSCENE_ID() , relayBox.getBOX_ID());

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

            @Override
            public void onTriggerControl(View view, int position) {//触发条件开关
                //if(AllVariable.CONNECT_RELAY)
                    if (mALLScenesList.get(position).getTRIGGER_STATUS() == 1) {
                        mALLScenesList.get(position).setTRIGGER_STATUS(0);
                    } else {
                        mALLScenesList.get(position).setTRIGGER_STATUS(1);
                    }

                //获取所有的中继盒子
                mRelayBoxList = DataBaseHandle.queryAllRelayBox();
                //UDP发送到中继盒子
                for (int i = 0; i < mRelayBoxList.size(); i++) {
                    RelayBox relayBox = mRelayBoxList.get(i);
                    RelayBoxUDPHandle.setScenesTriggerState(mALLScenesList.get(position).getSCENE_ID(), mALLScenesList.get(position).getTRIGGER_STATUS(), relayBox.getBOX_ID(), relayBox.getIP());
                }


                mRecyclerAdapter.notifyItemChanged(position);

            }

            @Override
            public void onEdit(View view, int position) {//编辑
                if(mRecyclerAdapter.getEditState()) {
                    String scenes_id = mALLScenesList.get(position).getSCENE_ID();
                    Intent intent = new Intent(mActivity, EditScenesActivity.class);
                    intent.putExtra("edit_scenes_id", scenes_id);
                    startActivity(intent);
                }
            }

            @Override
            public void onDelete(View view, int position) {

                if(AllVariable.CONNECT_RELAY) {

                    mCurrentDeleteScenes = mALLScenesList.get(position);
                    mDeletePosition = position;

                    //获取所有的中继盒子
                    List<RelayBox> relayBoxes = DataBaseHandle.queryAllRelayBox();


                    //UDP 发送到中继盒子 删除场景
                    for (int i = 0; i < relayBoxes.size(); i++) {
                        RelayBox relayBox = relayBoxes.get(i);
                        RelayBoxUDPHandle.deleteOneScenes(mCurrentDeleteScenes.getSCENE_ID(), relayBox.getBOX_ID(), relayBox.getIP());
                    }


                    //本地数据库删除场景数据
                    DataBaseHandle.deleteOneScenes(mCurrentDeleteScenes);
                    mALLScenesList.remove(mDeletePosition);
                    mRecyclerAdapter.notifyItemRemoved(mDeletePosition);


                    //发送到云端删除场景
                    ServerCommunicationHandle.deleteScenes(mCurrentDeleteScenes, new UIHttpCallBack() {
                        @Override
                        public void success(Object object) {

                        }
                        @Override
                        public void failure(Object object) {

                        }
                    });
                }else {
                    Toast.makeText(mActivity , "只能在局域网环境删除场景！" , Toast.LENGTH_LONG).show();
                }
            }
        };

        mRecyclerAdapter.setScenesControlClickListen(scenesControlClickListen);
        mScenesListRecyclerView.setAdapter(mRecyclerAdapter);

    }


    /**
     * 编辑和常规状态切换状态
     * @return
     */
    private boolean setScenesEditState() {
        if(mRecyclerAdapter != null){
            boolean isEdit = mRecyclerAdapter.getEditState();
            if(isEdit){
                return mRecyclerAdapter.setEditState(false);
            }else {
               return mRecyclerAdapter.setEditState(true);
            }
        }
        return  false;
    }

    /**
     * 切换标题栏的左边编辑提示
     * @param isEdit
     */
    private void setTitleLeftCancel(boolean isEdit) {
        if(isEdit) {
            mTopBarBuilder.setLeftText(R.string.cancel);
        }else {
            mTopBarBuilder.setLeftText(R.string.edit);
        }
    }


    /**
     * 接收到添加场景成功的广播， 场景界面更新数据
     */
    private BroadcastReceiver mAddScenesReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<Scenes> scenesList = DataBaseHandle.queryAllScenes();
            if(mALLScenesList != null) {
                mALLScenesList.clear();
                for (int i = 0 ; i < scenesList.size() ; i++){
                    mALLScenesList.add(scenesList.get(i));
                }
            }


            List<ScenesDevice> scenesDeviceList = DataBaseHandle.queryAllScenesDevices();
            if(mALLScenesDevicesList != null) {
                mALLScenesDevicesList.clear();
                for (int i = 0 ; i < scenesDeviceList.size() ; i++){
                    mALLScenesDevicesList.add(scenesDeviceList.get(i));
                }
            }


            List<ScenesTrigger> scenesTriggerList = DataBaseHandle.queryAllScenesTrigger();
            if(mALLScenesTriggerList != null) {
                mALLScenesTriggerList.clear();
                for (int i = 0 ; i < scenesTriggerList.size() ; i++){
                    mALLScenesTriggerList.add(scenesTriggerList.get(i));
                }
            }

            mRecyclerAdapter.notifyDataSetChanged();
        }
    };






}
