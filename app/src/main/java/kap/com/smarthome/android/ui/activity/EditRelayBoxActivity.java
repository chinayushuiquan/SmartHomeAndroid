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
import kap.com.smarthome.android.communication.bean.base.DATABean.RelayBoxData;
import kap.com.smarthome.android.communication.bean.base.DATABean.RoomData;
import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPResponseMsgBase;
import kap.com.smarthome.android.communication.http.constants.HTTPMsgINSIP;
import kap.com.smarthome.android.communication.http.constants.HttpResponseCode;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.data.bean.RelayBox;
import kap.com.smarthome.android.presenter.constants.AllConstants;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.ui.adapter.RoomLinkedDevicesAdapter;
import kap.com.smarthome.android.ui.view.MyLoadingDialog;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

/**
 * Created by Administrator on 2017/10/17 0017.
 */

public  class EditRelayBoxActivity extends BaseActivity {

    private GridView mRelayBoxLinkedDevicesGv;

    private TextInputEditText mRelayBoxNameEdit;

    private RoomLinkedDevicesAdapter mGvAdapter;

    private RelayBox mEditRelayBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //先加中继盒子
        setContentView(R.layout.activity_edit_relay);

        mRelayBoxNameEdit = (TextInputEditText) findViewById(R.id.relay_box_name_edit);

        mRelayBoxLinkedDevicesGv = (GridView) findViewById(R.id.relay_box_linked_devices_gv);

        mGvAdapter  = new RoomLinkedDevicesAdapter(this);

        mRelayBoxLinkedDevicesGv.setAdapter(mGvAdapter);

        mEditRelayBox  = (RelayBox) getIntent().getSerializableExtra("edit_relay_box");

        mRelayBoxNameEdit.setHint(mEditRelayBox.getNAME());

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

                //2017-09-21  更改中继盒子
                if(mRelayBoxNameEdit.getText().length() <= 0){
                    Toast.makeText(EditRelayBoxActivity.this, getString(R.string.add_room_name_notnull), Toast.LENGTH_SHORT).show();
                    return;
                }

                final String  relayBoxName = mRelayBoxNameEdit.getText().toString().trim();
                List<RelayBoxData> relayBoxDatas = new ArrayList<RelayBoxData>();

                showLoadingDialog(relayBoxName);

                RelayBoxData relayBoxData = new RelayBoxData();
                relayBoxData.setID(mEditRelayBox.getGUID());
                relayBoxData.setBOXID(mEditRelayBox.getBOX_ID());
                relayBoxData.setRELAYBOXNAME(mEditRelayBox.getNAME());
                relayBoxData.setHARDWAREVERSION(mEditRelayBox.getHARDWARE_VERSION());
                relayBoxData.setSOFTWAREVERSION(mEditRelayBox.getSOFTWARE_VERSION());
                relayBoxData.setMACHINECODE(mEditRelayBox.getMACHINE_CODE());
                relayBoxData.setIP(mEditRelayBox.getIP());
                relayBoxData.setPORT(mEditRelayBox.getPORT()+"");
                relayBoxData.setMASK(mEditRelayBox.getMASK());
                relayBoxData.setPLATFORMADDR(mEditRelayBox.getPLATFORM_ADDR());
                relayBoxData.setPLATFORMPORT(mEditRelayBox.getPLATFORM_PORT()+"");
                relayBoxData.setRELAYORDER(mEditRelayBox.getRELAY_ORDER()+"");

                relayBoxDatas.add(relayBoxData);

                ServerCommunicationHandle.updateRelayBox(relayBoxDatas, new UIHttpCallBack() {
                    @Override
                    public void success(Object object) {
                        if(object != null) {
                            final HTTPResponseMsgBase httpResponseMsgBase = (HTTPResponseMsgBase) object;
                            if (httpResponseMsgBase.getBODY().getINSTP().equals(HTTPMsgINSIP.UPDATE_RELAYBOX_ANDUSER_RSP)) {
                                if (httpResponseMsgBase.getBODY().getRESULT().equals(HttpResponseCode.SUCCESS)) {
                                    mEditRelayBox.setNAME(relayBoxName);
                                    DataBaseHandle.updateOneRelayBox(mEditRelayBox);
                                    dismissLoadingDialog();
                                    //发送广播，通知 进行界面的更新  添加中继盒子成功
                                    Intent intent = new Intent(AllConstants.BROAD_CAST_ADD_RElAY_BOX);
                                    sendBroadcast(intent);
                                    finish();
                                }
                            }
                        }
                    }
                    @Override
                    public void failure(Object object) {
                        dismissLoadingDialog();
                        Toast.makeText(EditRelayBoxActivity.this, getString(R.string.update_relaybox_fail), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }




}
