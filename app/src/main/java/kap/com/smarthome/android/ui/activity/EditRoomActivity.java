package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.DATABean.RoomData;
import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPResponseBaseMsg;
import kap.com.smarthome.android.communication.http.constants.HTTPMsgINSIP;
import kap.com.smarthome.android.communication.http.constants.HttpResponseCode;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.data.bean.Room;
import kap.com.smarthome.android.presenter.constants.AllConstants;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.ui.adapter.RoomLinkedDevicesAdapter;
import kap.com.smarthome.android.ui.view.MyLoadingDialog;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

/**
 * Created by Administrator on 2017/10/16 0016.
 */

public class EditRoomActivity extends BaseActivity {

    private GridView mRoomLinkedDevicesGv;

    private TextInputEditText mRoomNameEdit;

    private RoomLinkedDevicesAdapter mGvAdapter;

    private MyLoadingDialog myLoadingDialog ;

    private Room mEditRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_room);

        mRoomNameEdit = (TextInputEditText) findViewById(R.id.room_name_edit);

        mRoomLinkedDevicesGv  = (GridView) findViewById(R.id.room_linked_devices_gv);

        mGvAdapter  = new RoomLinkedDevicesAdapter(this);

        mRoomLinkedDevicesGv.setAdapter(mGvAdapter);

        mEditRoom  = (Room) getIntent().getSerializableExtra("edit_room");

        mRoomNameEdit.setHint(mEditRoom.getNAME());

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
                    Toast.makeText(EditRoomActivity.this, getString(R.string.add_room_name_notnull), Toast.LENGTH_SHORT).show();
                    return;
                }

                final String  roomName = mRoomNameEdit.getText().toString().trim();
                List<RoomData> roomDatas = new ArrayList<RoomData>();
                // guid name order, type
                roomDatas.add(new RoomData( mEditRoom.getGUID() , roomName , mEditRoom.getROOM_ORDER()+"" , mEditRoom.getTYPE()+""));
                showLoadingDialog(roomName);


                ServerCommunicationHandle.updateOneRoom(roomDatas, new UIHttpCallBack() {
                    @Override
                    public void success(Object object) {
                        if(object != null) {
                            final HTTPResponseBaseMsg httpResponseBaseMsg = (HTTPResponseBaseMsg) object;
                            if (httpResponseBaseMsg.getBODY().getINSTP().equals(HTTPMsgINSIP.UPDATE_ROOM_RSP)) {
                                if (httpResponseBaseMsg.getBODY().getRESULT().equals(HttpResponseCode.SUCCESS)) {
                                    mEditRoom.setNAME(roomName);
                                    Log.e("HTTP", "success: 更新房间 = " + mEditRoom.toString());
                                    DataBaseHandle.updateOneRoom(mEditRoom);
                                    Intent intent = new Intent(AllConstants.BROAD_CAST_ADD_ROOM);
                                    sendBroadcast(intent);
                                    //设置跟新成功标志
                                    AllVariable.IS_BROAD_CAST_ADD_ROOM = true;
                                    dismissLoadingDialog();
                                    finish();
                                }
                            }
                        }
                    }

                    @Override
                    public void failure(Object object) {
                        dismissLoadingDialog();
                        Toast.makeText(EditRoomActivity.this, getString(R.string.update_room_fail), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }


}
