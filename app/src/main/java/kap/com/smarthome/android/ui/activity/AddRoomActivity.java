package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.DATABean.RoomData;
import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPResponseMsgBase;
import kap.com.smarthome.android.communication.http.constants.HTTPMsgINSIP;
import kap.com.smarthome.android.communication.http.constants.HttpResponseCode;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.data.bean.Room;
import kap.com.smarthome.android.presenter.constants.AllConstants;

import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.presenter.utils.UUIDUtils;
import kap.com.smarthome.android.ui.adapter.RoomIconChoseGvAdapter;
import kap.com.smarthome.android.ui.view.MyLoadingDialog;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

/**
 * Created by yushq on 2017/9/5 0005.
 */

public  class  AddRoomActivity  extends BaseActivity{

    private GridView mRoomIconChoseGv;
    private TextInputEditText mRoomNameEdit;
    private RoomIconChoseGvAdapter  mGvAdapter;

    //已经存在的房间个数
    private int mCurrentRoomNum;
    //选择的房间图标的下标
    private int mSelectRoomIconPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        mCurrentRoomNum = getIntent().getIntExtra(AllConstants.ROOM_NUM_EXTRA, 7);
        mRoomIconChoseGv = (GridView) findViewById(R.id.room_icon_chose_gv);
        mGvAdapter = new RoomIconChoseGvAdapter(this);
        mRoomIconChoseGv.setAdapter(mGvAdapter);
        mRoomNameEdit = (TextInputEditText)findViewById(R.id.room_name_edit);

        mRoomIconChoseGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 mGvAdapter.setSelectPosition(position);
                 mGvAdapter.notifyDataSetInvalidated();
                 mSelectRoomIconPosition = position;
            }
        });
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
                if(mRoomNameEdit.getText().length() <= 0){
                    Toast.makeText(AddRoomActivity.this, getString(R.string.add_room_name_notnull), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mSelectRoomIconPosition == -1){
                    Toast.makeText(AddRoomActivity.this, getString(R.string.please_chose_room_type), Toast.LENGTH_SHORT).show();
                    return;
                }

                final  String  uuid = UUIDUtils.getUUID();
                String  roomName = mRoomNameEdit.getText().toString().trim();
                List<RoomData>  roomDatas = new ArrayList<RoomData>();
                // guid name order, type
                roomDatas.add(new RoomData(uuid, roomName , mCurrentRoomNum+"" , mSelectRoomIconPosition+""));

                showLoadingDialog(roomName);

                ServerCommunicationHandle.addRoom(roomDatas, new UIHttpCallBack() {
                    @Override
                    public void success(Object object) {
                        if(object != null){
                            final HTTPResponseMsgBase httpResponseMsgBase = (HTTPResponseMsgBase) object;
                            if(httpResponseMsgBase.getBODY().getINSTP().equals(HTTPMsgINSIP.NEW_ROOM_RSP)){
                                if(httpResponseMsgBase.getBODY().getRESULT().equals(HttpResponseCode.SUCCESS)) {
                                    //添加房间到数据库成功
                                    Room room  = new Room(null ,uuid , mRoomNameEdit.getText().toString().trim(),
                                            mCurrentRoomNum , mSelectRoomIconPosition);
                                    if(DataBaseHandle.insertOneRoom(room)){
                                        //发送广播，通知RoomFragment进行界面的更新
                                        Intent intent = new Intent(AllConstants.BROAD_CAST_ADD_ROOM);
                                        sendBroadcast(intent);
                                        //设置添加成功标志
                                        AllVariable.IS_BROAD_CAST_ADD_ROOM = true;
                                        dismissLoadingDialog();
                                        finish();
                                    }else {
                                        Toast.makeText(AddRoomActivity.this, getString(R.string.add_room_fail), Toast.LENGTH_SHORT).show();
                                        dismissLoadingDialog();
                                    }

                                   }
                                }
                           }
                       }
                    @Override
                    public void failure(Object object) {
                        Toast.makeText(AddRoomActivity.this, getString(R.string.add_room_fail), Toast.LENGTH_SHORT).show();
                        dismissLoadingDialog();
                    }
                });

             }
        });
    }

}
