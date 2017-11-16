package kap.com.smarthome.android.ui.fragment;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.DataSetObservable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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
import java.util.Date;
import java.util.List;
import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.DATABean.RoomData;
import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPResponseMsgBase;
import kap.com.smarthome.android.communication.http.constants.HTTPMsgINSIP;
import kap.com.smarthome.android.communication.http.constants.HttpResponseCode;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.data.bean.Room;
import kap.com.smarthome.android.presenter.constants.AllConstants;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.ui.activity.AddRoomActivity;
import kap.com.smarthome.android.ui.activity.EditRoomActivity;
import kap.com.smarthome.android.ui.adapter.RoomMainRecyclerViewAdapter;
import kap.com.smarthome.android.ui.view.MyLoadingDialog;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;



public class RoomFragment extends BaseFragment{

    private RecyclerView mRecyclerView;
    private Activity mActivity;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    /**
     * 房间数据R
     */
    private List<Room>  mRoomList = null;

    /**
     * RecyclerView的适配器
     */
    private RoomMainRecyclerViewAdapter roomMainRecyclerViewAdapter;

    private MyTopBarBuilder mTopBarBuilder;


    private MyLoadingDialog myLoadingDialog ;


    public static RoomFragment newInstance(String param1) {
        RoomFragment fragment = new RoomFragment();
        return fragment;
    }

    public RoomFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity.registerReceiver(mAddRoomReceiver , new IntentFilter(AllConstants.BROAD_CAST_ADD_ROOM));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room, container, false);
        initMyTopBar(view);
        initData();
        initRecyclerView(view);
        initRecycleViewClickListener();
        initSwipeRefreshLayout(view);
        return view;
    }


    //注销添加房间的广播
    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity.unregisterReceiver(mAddRoomReceiver);
    }


    /**
     * 初始化话顶部的导航栏
     * @param view
     */
    private void initMyTopBar(View view) {
        mTopBarBuilder =  MyTopBarBuilder.builder(view)
                .setTitleBgRes(R.color.white)
                .setLeftText(R.string.edit)
                .setTitleText(R.string.room_control)
                .setRightImage(R.drawable.add)
                .setLeftTextOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: 2017/8/24 0024  进入编辑状态
                        setTitleLeftCancel(setScenesEditState());
                    }
                }).setRightImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/9/5 0005 跳转到添加房间的界面  需要传入现在已经存在的房间总数
                Intent intent = new Intent(mActivity , AddRoomActivity.class);
                intent.putExtra(AllConstants.ROOM_NUM_EXTRA, mRoomList.size());
                startActivity(intent);
            }
        });
    }

    /**
     * 从数据库读取房间列表数据
     */
    protected void initData(){
        if(mRoomList != null){
            mRoomList.clear();
            mRoomList = null;
        }
        mRoomList = DataBaseHandle.queryAllRooms();
        for (int i = 0 ; i < mRoomList.size() ; i++){
            Log.e("UDP", "initData:  mRoomList.get(i).getGUID() = " +  mRoomList.get(i).getGUID());
        }
    }

    private void initRecyclerView(View view) {
        mRecyclerView = (RecyclerView)view.findViewById(R.id.room_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        roomMainRecyclerViewAdapter = new RoomMainRecyclerViewAdapter(mActivity, mRoomList);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(roomMainRecyclerViewAdapter);
    }


    private void initRecycleViewClickListener() {
        roomMainRecyclerViewAdapter.setOnItemClickListener(new RoomMainRecyclerViewAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                    Room room = mRoomList.get(position);
                    Intent intent = new Intent(mActivity, EditRoomActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("edit_room", room);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            @Override
            public void onDeleteIconClick(View view, final  int position) {

                Room room = mRoomList.get(position);
                List<RoomData>  roomDatas = new ArrayList<RoomData>();
                RoomData roomData = new RoomData();
                roomData.setID(room.getGUID());
                roomDatas.add(roomData);

                showLoadingDialog("删除" + room.getNAME());

                ServerCommunicationHandle.deleteRooms(roomDatas, new UIHttpCallBack() {
                    @Override
                    public void success(Object object) {
                        if(object != null){
                            final HTTPResponseMsgBase httpResponseMsgBase = (HTTPResponseMsgBase) object;
                            if(httpResponseMsgBase.getBODY().getINSTP().equals(HTTPMsgINSIP.DELETE_ROOM_RSP)){
                                if(httpResponseMsgBase.getBODY().getRESULT().equals(HttpResponseCode.SUCCESS)) {
                                        deleteRoom(mRoomList.get(position));
                                        roomMainRecyclerViewAdapter.notifyItemRemoved(position);
                                        mRoomList.remove(position);
                                        dismissLoadingDialog();
                                     }
                                  }
                                }else{
                                dismissLoadingDialog();
                                Toast.makeText(mActivity, getString(R.string.delete_room_fail), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void failure(Object object) {
                        dismissLoadingDialog();
                        Toast.makeText(mActivity, getString(R.string.delete_room_fail), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 编辑和常规状态切换状态
     * @return
     */
    private boolean setScenesEditState() {
        if(roomMainRecyclerViewAdapter == null) {
               return  false;
        }
        if(roomMainRecyclerViewAdapter.getEditstate()){
            return roomMainRecyclerViewAdapter.setEditState(false);
        }else {
            return roomMainRecyclerViewAdapter.setEditState(true);
        }
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
     * 下拉刷新的
     * SwipeRefreshLayout初始化
     * @param view
     */
    private void initSwipeRefreshLayout(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.room_fragment_swipe_container);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
    }


    /**
     * 删除一个房间数据
     */
    private boolean deleteRoom(Room deleteRoom){
        return  DataBaseHandle.deleteRoom(deleteRoom);
    }


    /**
     * 添加房间的处理广播
     */
    private BroadcastReceiver mAddRoomReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<Room> roomList = DataBaseHandle.queryAllRooms();
            if(mRoomList != null) {
                mRoomList.clear();
                for (int i = 0 ; i < roomList.size() ; i++){
                    mRoomList.add(roomList.get(i));
                }
            }
            roomMainRecyclerViewAdapter.notifyDataSetChanged();
        }
    };



}
