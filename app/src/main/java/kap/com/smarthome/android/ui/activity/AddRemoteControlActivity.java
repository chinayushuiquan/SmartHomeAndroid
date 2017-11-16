package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;


import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.Room;
import kap.com.smarthome.android.presenter.constants.AllConstants;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.DeviceHandleUtils;
import kap.com.smarthome.android.presenter.utils.DeviceIdInitUtils;
import kap.com.smarthome.android.ui.adapter.AddRemoteControlChooseRoomGvAdapter;
import kap.com.smarthome.android.ui.adapter.AddRemoteControlChoossIconGvAdapter;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;
import kap.com.smarthome.android.ui.view.SetHeightGridView;

/**
 * Created by yushq on 2017/10/19 0019.
 */

public class AddRemoteControlActivity extends BaseActivity{

    private SetHeightGridView mRoomChoseGv;
    private AddRemoteControlChooseRoomGvAdapter mChooseRoomGvAdapter;

    //选择的房间图标的下标
    private int mSelectRoomIconPosition = -1;

    private SetHeightGridView mRcTypeChoseGv;

    private AddRemoteControlChoossIconGvAdapter mRcTypeAdapter;

    //选择的遥控器类型的下标
    private int mChooseRcTypePosition = -1;

    //当前从哪个房间跳转过来的 ，默认设备添加到此房间
    private  String   mCurrentRoomGuid;

    //所有的房间
    private List<Room> mAllRooms;

    //新的红外遥控器设备
    private Devices newIrRcDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //默认从哪个房间跳转过来就添加到哪个房间
        mCurrentRoomGuid = getIntent().getStringExtra(AllConstants.CURRENT_ROOM_GUID);

        mAllRooms  = DataBaseHandle.queryAllRooms();

        setContentView(R.layout.activity_add_remote_control);

        mRoomChoseGv = (SetHeightGridView) findViewById(R.id.room_chose_gv);

        mChooseRoomGvAdapter = new AddRemoteControlChooseRoomGvAdapter(this, mAllRooms);

        mRoomChoseGv.setAdapter(mChooseRoomGvAdapter);

        mRoomChoseGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mChooseRoomGvAdapter.setSelectPosition(position);
                mChooseRoomGvAdapter.notifyDataSetInvalidated();
                mSelectRoomIconPosition = position;
                mCurrentRoomGuid = mAllRooms.get(mChooseRoomGvAdapter.getSelectPosition()).getGUID();
            }
        });


        mRcTypeChoseGv = (SetHeightGridView) findViewById(R.id.remote_control_chose_gv);
        mRcTypeAdapter = new AddRemoteControlChoossIconGvAdapter(this);
        mRcTypeChoseGv.setAdapter(mRcTypeAdapter);
        mRcTypeChoseGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mRcTypeAdapter.setSelectPosition(position);
                mRcTypeAdapter.notifyDataSetInvalidated();
                mChooseRcTypePosition = position;
            }
        });
    }


    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {
        myTopBarBuilder
                .setLeftImage(R.drawable.back_icon_black)
                .setTitleText(R.string.add_remote_control)
                .setRightText(R.string.next_tip)
                .setRightTextColor(R.color.orange)
                .setLeftImageOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new 一个新的IR红外遥控器设备
                newIrRcDevice = new Devices();
                newIrRcDevice.setROOM_GUID(mCurrentRoomGuid);
                newIrRcDevice.setTYPE(mRcTypeAdapter.getIrRcDeviceType());
                newIrRcDevice.setNAME(mRcTypeAdapter.getIrRcDeviceName());
                newIrRcDevice.setVALUE("00FF");
                newIrRcDevice.setDEVICE_ID(DeviceIdInitUtils.createDeviceID(DeviceHandleUtils.IR_TV));
                Intent intent = new Intent(AddRemoteControlActivity.this,MatchRemoteControlRelayBoxActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("add_ir_device", newIrRcDevice);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }

}
