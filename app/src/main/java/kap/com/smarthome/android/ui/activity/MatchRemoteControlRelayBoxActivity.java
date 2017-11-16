package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPReceiverData;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.RelayBox;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.RelayBoxUDPHandle;
import kap.com.smarthome.android.ui.adapter.RelayAddListViewAdapter;
import kap.com.smarthome.android.ui.adapter.RelayMatchListAdapter;
import kap.com.smarthome.android.ui.view.MyLoadingDialog;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

/**
 * Created by yushq on 2017/10/19 0019.
 */

public class MatchRemoteControlRelayBoxActivity extends BaseActivity {

    private MyTopBarBuilder myTopBarBuilder;

    private ListView  mRelayBoxListView;

    private Button mMatchBtn;

    private RelayMatchListAdapter addListViewAdapter = null;

    private List<RelayBox>  mRelayBoxes;

    private MyLoadingDialog myLoadingDialog;

    private RelayBox mCurrentMatchRelayBox = null ;
    private RelayBox mLastMatchRelayBox= null;


    private int mCurrentIndex = 0;

    //需要添加的设备实例
    private Devices newIrRcDevice;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newIrRcDevice = (Devices) getIntent().getSerializableExtra("add_ir_device");

        setContentView(R.layout.activity_match_relay_box);

        mRelayBoxListView = (ListView) findViewById(R.id.relay_match_list_view);

        mMatchBtn = (Button) findViewById(R.id.add_rc_match_relay_box);

        mRelayBoxes = DataBaseHandle.queryAllRelayBox();

        addListViewAdapter = new RelayMatchListAdapter(this, mRelayBoxes);

        mRelayBoxListView.setAdapter(addListViewAdapter);

        mMatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRelayBoxes != null && mRelayBoxes.size() > mCurrentIndex){
                    matchMethod();
                }else if(mRelayBoxes != null &&  mRelayBoxes.size() == mCurrentIndex){
                    mCurrentIndex = 0 ;
                    matchMethod();
                }
            }
        });


        mRelayBoxListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addListViewAdapter.setSelectPosition(position);
                addListViewAdapter.notifyDataSetChanged();
                mCurrentIndex = position;
            }
        });



    }

    private void matchMethod() {
        if(mCurrentMatchRelayBox != null) {
            mLastMatchRelayBox = mCurrentMatchRelayBox;
            //终止匹配消息
            RelayBoxUDPHandle.sendStopMatchMessage(mLastMatchRelayBox.getBOX_ID(), mLastMatchRelayBox.getIP());
        }

        mCurrentMatchRelayBox = mRelayBoxes.get(mCurrentIndex);
        addListViewAdapter.setSelectPosition(mCurrentIndex);
        addListViewAdapter.notifyDataSetChanged();

        //
        RelayBoxUDPHandle.sendStartMatchMessage(mCurrentMatchRelayBox.getBOX_ID(), mCurrentMatchRelayBox.getIP());

        mCurrentIndex ++;
    }

    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {
        this.myTopBarBuilder = myTopBarBuilder.setLeftImage(R.drawable.back_icon_black)
                .setTitleBgRes(R.color.white)
                .setTitleText(R.string.match_relay_box)
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
                        if(mCurrentMatchRelayBox == null){
                            return;
                        }else{
                            //终止匹配消息
                            RelayBoxUDPHandle.sendStopMatchMessage(mCurrentMatchRelayBox.getBOX_ID(), mCurrentMatchRelayBox.getIP());
                            //选择模板进行红外学习
                            Intent intent  = new Intent(MatchRemoteControlRelayBoxActivity.this, RemoteControlTemplateLearnActivity.class);
                            newIrRcDevice.setRELAY_ID(mCurrentMatchRelayBox.getBOX_ID());
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("match_relay_box", mCurrentMatchRelayBox);
                            bundle.putSerializable("add_ir_device" , newIrRcDevice);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }


                    }
                });
    }



    /**
     * 开始匹配中继盒子的返回信息
     * @param item
     */
    @Override
    public void uiStartMatchRelayBoxCallback(UDPReceiverData item) {
        super.uiStartMatchRelayBoxCallback(item);

    }


    /**
     * 终止匹配某个中继盒子的返回信息
     * @param item
     */
    @Override
    public void uiStopMatchRelayBoxCallback(UDPReceiverData item) {
        super.uiStopMatchRelayBoxCallback(item);

    }
}



