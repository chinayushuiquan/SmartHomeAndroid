package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.DATABean.RelayBoxData;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPReceiverData;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPResponseMsgBase;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.communication.udp.constants.UDPContants;
import kap.com.smarthome.android.data.bean.RelayBox;
import kap.com.smarthome.android.presenter.constants.AllConstants;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.BeanDataConvertUtils;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.DataToBeanConversion;
import kap.com.smarthome.android.presenter.control.RelayBoxUDPHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.presenter.utils.JsonUtils;
import kap.com.smarthome.android.presenter.utils.UUIDUtils;
import kap.com.smarthome.android.ui.adapter.RelayAddListViewAdapter;
import kap.com.smarthome.android.ui.view.UIPullRefreshView;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

public class AddRelayBoxActivity extends BaseActivity {

    //刷新搜索到的中继盒子列表
    private static final int UPDATE_BOX_LIST = 0;

    //ListView刷新的View
    private UIPullRefreshView refreshableView;

    private ListView listView;
    private RelayAddListViewAdapter addListViewAdapter = null;

    //搜索到的中继盒子
    private List<RelayBox>  mNewSearchList;

    //已经存在本地数据库中的中继盒子列表数据
    private List<RelayBox>  mAlreadySaveList;

    //选中需要添加的中继盒子
    private List<RelayBox>  selectRelayBoxes = null;

    //收到确认信息能够添加的盒子
    private List<RelayBox>  canAddRelayBoxes;

    //添加的中继盒子的个数
    private int udpAddRelayBoxCount;

    private TextView loginNoteTv;
    private TextView mSelectAllBoxTv;
    private boolean  isCheckAll = false;

    /**
     * 执行插入中继盒子到本地数据库的线程
     */
    private Runnable mDoAddRelayBoxRunnable;



    private Handler addRelayHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATE_BOX_LIST:
                    updateAdapter();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_repeter_box);


        listView = (ListView) findViewById(R.id.relay_add_list_view);

        loginNoteTv = (TextView) findViewById(R.id.relay_add_note_login_tv);
        mSelectAllBoxTv= (TextView) findViewById(R.id.select_all_relay_box_tv);


        mNewSearchList = new ArrayList<>();
        mAlreadySaveList = DataBaseHandle.queryAllRelayBox();
        canAddRelayBoxes = new ArrayList<>();

        addListViewAdapter = new RelayAddListViewAdapter(this , mNewSearchList , mAlreadySaveList.size());
        listView.setAdapter(addListViewAdapter);

        refreshableView = (UIPullRefreshView) findViewById(R.id.relay_refreshable_view);
        refreshableView.setOnRefreshListener(new UIPullRefreshView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                searchRelayBox();
                refreshableView.finishRefreshing();
            }
        }, 0);

        /*//全选和取消全选
        mSelectAllBoxTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isCheckAll){
                    addListViewAdapter.selectAllRelayBoxes();
                    isCheckAll = true;
                    mSelectAllBoxTv.setText(getString(R.string.cancel_check_all));
                }else{
                    addListViewAdapter.cancelSelectAllRelayBoxes();
                    isCheckAll = false;
                    mSelectAllBoxTv.setText(getString(R.string.check_all));
                }
            }
        });*/

    }


    /**
     * 进入Activity，默认开始搜索可用的中继盒子
     */
    @Override
    protected void onStart() {
        super.onStart();
        searchRelayBox();
    }




    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {
         myTopBarBuilder.setLeftImage(R.drawable.back_icon_black)
                .setTitleBgRes(R.color.white)
                .setTitleText(R.string.add_relay_box)
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

                        selectRelayBoxes = addListViewAdapter.getmSelectRelayBox();

                        //没有添加中继盒子的时候直接返回，不进行任何处理
                        if(selectRelayBoxes == null || selectRelayBoxes.size() < 1){
                            Toast.makeText(AddRelayBoxActivity.this, "没有选择中继盒子！", Toast.LENGTH_SHORT).show();
                            return ;
                        }

                        //如果没有获取到任何在局域网的盒子信息，也不执行任何操作
                        if(!AllVariable.CONNECT_RELAY){
                            Toast.makeText(AddRelayBoxActivity.this, "只能在局域网添加中继盒子！", Toast.LENGTH_SHORT).show();
                            return ;
                        }

                        showLoadingDialog("");
                        //给选定的中继盒子发送添加信息
                        selectRelayBoxes = addListViewAdapter.getmSelectRelayBox();
                        for (int i = 0 ; i < selectRelayBoxes.size() ; i++){
                            //udp 添加中继盒子
                            RelayBoxUDPHandle.addRelayBox(AllVariable.CURRENT_USER_ID, selectRelayBoxes.get(i).getBOX_ID(), selectRelayBoxes.get(i).getIP());
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        mDoAddRelayBoxRunnable = new Runnable() {
                            @Override
                            public void run() {
                                addRelayBoxMethod();
                            }
                        };
                        //超时定时器 超时时间3s
                        addRelayHandler.postDelayed(mDoAddRelayBoxRunnable, AllConstants.UDP_REQ_OVER_TIME);
                    }
                });
          }





    /**
     * 搜索中继盒子的UDP命令
     */
    private void searchRelayBox(){
        RelayBoxUDPHandle.searchRelayBox();
    }

    /**
     * UDP 搜索中继盒子回调
     * @param item
     */
    @Override
    public void uiSearchRelayBoxSuccessCallback(UDPReceiverData item) {
        super.uiSearchRelayBoxSuccessCallback(item);
        scanOneRelayBox(item);
    }


    /**
     * 搜索到一个中继盒子 加锁处理一次只处理一个盒子的信息
     * @param item
     */
    private  synchronized  void  scanOneRelayBox(UDPReceiverData item) {
        //实例化一个盒子信息
        RelayBox relayBox = DataToBeanConversion.udpJsonConvertToRelay(item.data);

        //判断中继盒子是否已经被添加过了 已经添加过的盒子不再界面上显示

        int  save_box_size = mAlreadySaveList.size();
        for(int i = 0 ; i < save_box_size ; i++){
            if(relayBox.getBOX_ID().equals(mAlreadySaveList.get(i).getBOX_ID())){
                return ;
            }
        }

        int  scan_box_size = mNewSearchList.size();
        for (int i = 0; i < scan_box_size; i++) {
            if(mNewSearchList.get(i).getBOX_ID().equals(relayBox.getBOX_ID())){
               return ;
            }
        }

        //设置盒子的默认名称
        nameAndAddOneBox(relayBox);
    }


    /**
     * 设置中继盒子的默认名字
     * @param relayBox
     */
    private void nameAndAddOneBox(RelayBox relayBox) {
        if(mNewSearchList != null)
            mNewSearchList.add(relayBox);
        /*int num = mAlreadySaveList.size() + mNewSearchList.size();
        String  boxDefaultName = String.format(getResources().getString(R.string.relay_box_default_name), num);*/
        relayBox.setGUID(UUIDUtils.getUUID());
        relayBox.setNAME(relayBox.getBOX_ID());

        //到主线程刷新 显示中继盒子
        setAdapterUpdate();
    }

    /** 刷新搜索到的中继盒子列表 之前的方法都是在子线程中执行的，刷新界面的方法需要在主线程UI线程执行。
     */
    private void setAdapterUpdate() {
        Message msg = new Message();
        msg.what = UPDATE_BOX_LIST;
        addRelayHandler.sendMessage(msg);
    }

    /**
     * 搜索到中继盒子在handler中刷新界面
     */
    private void updateAdapter() {
        addListViewAdapter.notifyDataSetChanged();
    }




    /**
     * UDP 添加中继盒子的回调方法
     * @param item
     */
    @Override
    public void uiAddRelayBoxSuccessCallback(UDPReceiverData item) {
        super.uiAddRelayBoxSuccessCallback(item);
        UDPResponseMsgBase responseMsg = JsonUtils.stringToObject(item.data , UDPResponseMsgBase.class);
        //如果返回结果正确
        if(responseMsg.getBODY().getRESULT() == UDPContants.UDP_RESPONSE_SUCCESS){
            //首先判断接收到的中继盒子添加信息是否是选中要添加的盒子
            String boxId = responseMsg.getBODY().getBOXID();
            for(int i = 0 ; i < selectRelayBoxes.size() ; i++){
                if (boxId.equals(selectRelayBoxes.get(i).getBOX_ID())) {
                    udpAddRelayBoxCount++;
                    canAddRelayBoxes.add(selectRelayBoxes.get(i));
                }
            }
        }


        //如果收到了所有选中的中继盒子的添加信息 ， 就直接存储到数据库， 并进行跳转到上一个界面
        // 如果5秒内 没有收到所有的选择的中继盒子就不再等待，initTopBar addRelayHandler 中直接进行操作
        if(udpAddRelayBoxCount == selectRelayBoxes.size()){
            addRelayHandler.removeCallbacks(mDoAddRelayBoxRunnable);
            //添加中继盒子
            addRelayBoxMethod();
        }

    }


    /**
     * 保存中继盒子到本地和云端
     */
    private void addRelayBoxMethod() {
        if(canAddRelayBoxes != null && canAddRelayBoxes.size() > 0) {
            //把选中的中继盒子插入到数据库中 如果插入成功 发送广播，
            if (DataBaseHandle.insertRelayBoxList(canAddRelayBoxes)) {
                //数据转换成云端需要的数据
                List<RelayBoxData> relayBoxDataList = BeanDataConvertUtils.convertToRelayBoxData(canAddRelayBoxes);
                //本地添加中继盒子成功之后，将数据备份到云端
                ServerCommunicationHandle.addRelayBox(relayBoxDataList, new UIHttpCallBack() {
                    @Override
                    public void success(Object object) {
                        //发送广播，通知 进行界面的更新
                        saveRelayBoxLocalSuccess();
                    }

                    @Override
                    public void failure(Object object) {
                        setFailDataTag();
                        saveRelayBoxLocalSuccess();
                    }
                });
            } else {
                //插入数据库失败
                Toast.makeText(AddRelayBoxActivity.this, "插入数据到数据库失败！", Toast.LENGTH_SHORT).show();
                dismissLoadingDialog();
            }
        }else {
            Toast.makeText(AddRelayBoxActivity.this, "添加盒子失败！", Toast.LENGTH_SHORT).show();
            dismissLoadingDialog();
        }
    }


    /**
     * 添加盒子成功，跳转界面
     */
    private void saveRelayBoxLocalSuccess() {
        dismissLoadingDialog();
        //发送广播，通知 进行界面的更新
        Intent intent = new Intent(AllConstants.BROAD_CAST_ADD_RElAY_BOX);
        sendBroadcast(intent);
        finish();
    }


    /**
     * 上传数据到云端 失败
     */
    private void  setFailDataTag(){


    }




}
