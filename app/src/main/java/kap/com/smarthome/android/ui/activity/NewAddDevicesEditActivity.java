package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.presenter.constants.AllConstants;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

/**
 * Created by Administrator on 2017/9/27 0027.
 */

public class NewAddDevicesEditActivity extends  BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_devices_edit);


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
                /*//2017-09-21
                if(mRoomNameEdit.getText().length() <= 0){
                    Toast.makeText(AddRoomActivity.this, getString(R.string.add_room_name_notnull), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mSelectRoomIconPosition == -1){
                    Toast.makeText(AddRoomActivity.this, getString(R.string.please_chose_room_type), Toast.LENGTH_SHORT).show();
                    return;
                }

                //添加房间到数据库成功
                if(DataBaseHandle.insertOneRoom(mRoomNameEdit.getText().toString().trim(),
                        mCurrentRoomNum,mSelectRoomIconPosition)){
                    //发送广播，通知RoomFragment进行界面的更新
                    Intent intent = new Intent(AllConstants.BROAD_CAST_ADD_ROOM);
                    sendBroadcast(intent);
                    //设置添加成功标志
                    AllConstants.IS_BROAD_CAST_ADD_ROOM = true;
                    finish();
                }else {
                    Toast.makeText(AddRoomActivity.this, getString(R.string.add_room_fail), Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }
}
