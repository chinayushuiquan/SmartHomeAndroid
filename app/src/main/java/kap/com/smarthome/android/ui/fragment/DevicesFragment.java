package kap.com.smarthome.android.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.data.bean.Room;
import kap.com.smarthome.android.presenter.constants.AllConstants;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.ui.activity.AddRfDevicesActivity;
import kap.com.smarthome.android.ui.activity.AddRemoteControlActivity;
import kap.com.smarthome.android.ui.adapter.DevicesShowViewPagerAdapter;
import kap.com.smarthome.android.ui.view.MyPopupWindow;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;
import kap.com.smarthome.android.ui.view.MyTopPagerIndicator;

public class DevicesFragment extends BaseFragment {
    private static final String TAG = "CHRIS";
    private Activity mActivity;
    private ViewPager mViewPager;
    private List<Fragment>  mRoomDeviceFragments = null; //显示设备界列表的Fragment ，添加到viewpager中
    private List<Room>  mRoomList = null; //显示的房间列表
    private MyTopPagerIndicator mPagerIndicator;

    //实现弹出PopupWindow的时候设备背景界面变暗
    private Window mWindow;
    private float windowAlpha = 0.5f;
    private int popOffX = -200;
    private int popoffY = 30;

    //当前选择的房间和对应的呈现对应房间设备Fragment位置编号
    private int mCurrentRoom = 0;
    private DevicesShowViewPagerAdapter mShowDevicesPagerAdapter;

    private View  mContentView = null;

    private MyTopBarBuilder mTopBarBuilder;


    public static DevicesFragment newInstance(String param1) {
        DevicesFragment fragment = new DevicesFragment();
        return fragment;
    }

    public DevicesFragment() {
        mRoomDeviceFragments = new ArrayList<>();
        mRoomList = new ArrayList<>();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity  = activity;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_devices, container, false);

        mShowDevicesPagerAdapter = new DevicesShowViewPagerAdapter(getChildFragmentManager(), mRoomList , mRoomDeviceFragments);
        mViewPager = (ViewPager) mContentView.findViewById(R.id.devices_view_pager);
        mViewPager.setAdapter(mShowDevicesPagerAdapter);

        mPagerIndicator = (MyTopPagerIndicator) mContentView.findViewById(R.id.devices_top_pager_indicator);
        mPagerIndicator.setViewPager(mViewPager);

        //TODO: 2017/8/24 0024  tomorrow
        initMyTopBar(mContentView);
        return mContentView;
    }


    /**
     * 主界面的Fragment采用隐藏和显示的方式进行切换
     * 之后在不同的Fragment之间切换的时候才会执行这个
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //2017-09-21  添加房间成功，重新更新界面 ，顶部的房间导航要刷新
        if(hidden == false && AllVariable.IS_BROAD_CAST_ADD_ROOM){
            initData();
            addRoomNewPager();
            AllVariable.IS_BROAD_CAST_ADD_ROOM = false;
        }
    }

    /**
     * 初始化房间数据，按房间显示模块
     */
    private void initData() {

        if(mRoomDeviceFragments == null){
            mRoomDeviceFragments = new ArrayList<>();
        }

        mRoomDeviceFragments.clear();
        mRoomList = DataBaseHandle.queryAllRooms();

        for(int i  = 0 ; i< mRoomList.size() ; i++){
            DevicesOfRoomFragment devicesRoom =  DevicesOfRoomFragment.newInstance(mRoomList.get(i).getGUID());
            mRoomDeviceFragments.add(devicesRoom);
        }
    }

    /**
     * 添加了新的房间，初始化房间页面
     */
    private void addRoomNewPager() {
        mShowDevicesPagerAdapter = new DevicesShowViewPagerAdapter(getChildFragmentManager(), mRoomList , mRoomDeviceFragments);
        mViewPager.setAdapter(mShowDevicesPagerAdapter);
        mPagerIndicator.setViewPager(mViewPager);
        mPagerIndicator.setCurrentPosition(0);
    }

    /**
     * 初始化话顶部的导航栏
     * @param view
     */
    private void initMyTopBar(View view) {
        mTopBarBuilder =  MyTopBarBuilder.builder(view)
                .setLeftText(R.string.edit)
                .setTitleText(R.string.devices_control)
                .setRightImage(R.drawable.add)
                .setLeftTextOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        //获取当前的Fragment位置
                        mCurrentRoom  =  mPagerIndicator.getCurrentPosition();
                        DevicesOfRoomFragment  roomDevicesFragment = (DevicesOfRoomFragment) mRoomDeviceFragments.get(mCurrentRoom);
                        if(roomDevicesFragment.getPostionRoomEditState()){
                            //设置编辑状态
                            roomDevicesFragment.setPostionRoomEditState(false);
                            setTitleLeftCancel(false);
                        }else{
                            //退出编辑状态
                            roomDevicesFragment.setPostionRoomEditState(true);
                            setTitleLeftCancel(true);
                        }
                    }
                }).setRightImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater  inflater = mActivity.getLayoutInflater();

                View view = inflater.inflate(R.layout.popupwindow_add_devices , null);

                final  MyPopupWindow popWindow = new MyPopupWindow(getContext(), view, R.style.right_popupWindow_anim);
                //弹出PopupWindow设置窗口背景变暗
                mWindow = mActivity.getWindow();
                popWindow.setWindowAlpha(mWindow, windowAlpha);
                popWindow.showAsDropDown(v, popOffX , popoffY);

                RelativeLayout rl_1 = (RelativeLayout) view.findViewById(R.id.pop_window_rl_1);
                RelativeLayout rl_2 = (RelativeLayout) view.findViewById(R.id.pop_window_rl_2);

                //添加遥控器
                rl_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: 2017/8/24 0024 点击Popwindow Item1 添加遥控器
                        mCurrentRoom = mPagerIndicator.getCurrentPosition();
                        Intent intent = new Intent(mActivity, AddRemoteControlActivity.class);
                        intent.putExtra(AllConstants.CURRENT_ROOM_GUID , mRoomList.get(mCurrentRoom).getGUID());
                        startActivity(intent);
                        popWindow.dismiss();
                    }
                });

                //添加其他设备
                rl_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCurrentRoom = mPagerIndicator.getCurrentPosition();
                        Intent intent = new Intent(mActivity, AddRfDevicesActivity.class);
                        intent.putExtra(AllConstants.CURRENT_ROOM_GUID , mRoomList.get(mCurrentRoom).getGUID());
                        startActivity(intent);
                        popWindow.dismiss();
                    }
                });
            }
        });
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

}
