package kap.com.smarthome.android.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.DATABean.RelayBoxData;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPReceiverData;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPResponseMsgBase;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponseDataMsgClass;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponseQueryRelayBoxBody;
import kap.com.smarthome.android.communication.http.constants.HTTPMsgINSIP;
import kap.com.smarthome.android.communication.http.constants.HttpResponseCode;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.communication.udp.constants.UDPContants;
import kap.com.smarthome.android.data.bean.RelayBox;
import kap.com.smarthome.android.presenter.constants.AllConstants;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.BeanDataConvertUtils;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.RelayBoxUDPHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.presenter.utils.JsonUtils;
import kap.com.smarthome.android.ui.adapter.RelayBoxRecyclerViewAdapter;
import kap.com.smarthome.android.ui.view.MyLoadingDialog;
import kap.com.smarthome.android.ui.view.MyPopupWindow;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

public class RelayBoxActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private RelayBoxRecyclerViewAdapter relayBoxRecyclerViewAdapter;
    private MyTopBarBuilder myTopBarBuilder = null;
    private List<RelayBox>  mRelayBoxes;

    //实现弹出PopupWindow的时候设备背景界面变暗
    private Window mWindow;
    private float windowAlpha = 0.5f;
    private int popOffX = -200;
    private int popOffY = 30;


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MyLoadingDialog myLoadingDialog;


    /**
     * 删除中继盒子的数据
     */
    private RelayBox mDeleteRelayBox;
    private int mRemoveBoxPosition;
    private List<RelayBoxData> relayBoxDatas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeter_box);
        initData();
        initRecyclerView();
        initRecycleViewClickListener();
        initSwipeRefreshLayout();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mAddRelayReceiver, new IntentFilter(AllConstants.BROAD_CAST_ADD_RElAY_BOX));
        changeEditState(true);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mAddRelayReceiver);
    }


    /**
     * 下拉刷新的  重新添加数据
     * SwipeRefreshLayout初始化
     */
    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                final List<RelayBoxData> relayBoxDataList = new ArrayList<RelayBoxData>();

                //刷新中继盒子列表 查询的时候data 中的数据为空
                ServerCommunicationHandle.queryRelayBoxs(relayBoxDataList , new UIHttpCallBack() {
                    @Override
                    public void success(Object object) {
                        if(object != null) {
                            //返回的数据中包括一个Data数据
                            final HTTPResponseDataMsgClass httpResponseMsgBase = (HTTPResponseDataMsgClass) object;
                            HTTPResponseQueryRelayBoxBody httpResponseQueryRelayBoxBody = (HTTPResponseQueryRelayBoxBody)httpResponseMsgBase.getBODY();

                            if(httpResponseQueryRelayBoxBody.getINSTP().equals(HTTPMsgINSIP.FIND_RELAYBOX_ANDUSER_RSP)) {

                                 if (httpResponseQueryRelayBoxBody.getRESULT().equals(HttpResponseCode.SUCCESS)) {

                                     List<RelayBoxData> relayBoxesData = httpResponseQueryRelayBoxBody.getDATA();

                                     //如果请求的数据不为空 ，并且数据不等于本地数据
                                     if(relayBoxesData  != null && relayBoxesData.size() != mRelayBoxes.size()){

                                         //需要进行数据转换 RelayBoxData convert to  RelayBox 实例
                                         List<RelayBox> relayBoxes = BeanDataConvertUtils.convertToRelayBox(relayBoxesData);

                                         if(DataBaseHandle.refreshRelayBoxTable(relayBoxes)){

                                             updateUI(relayBoxes);

                                             Toast.makeText(RelayBoxActivity.this, "刷新列表成功", Toast.LENGTH_SHORT).show();
                                         }
                                     }else{
                                         Toast.makeText(RelayBoxActivity.this, "数据已经最新", Toast.LENGTH_SHORT).show();
                                     }
                                }
                            }
                        }
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
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {
        this.myTopBarBuilder =  myTopBarBuilder.setLeftImage(R.drawable.back_icon_black)
                   .setTitleBgRes(R.color.white)
                   .setLeftText(R.string.edit)
                   .setTitleText(R.string.system_relay_box)
                   .setRightImage(R.drawable.add)
                   .setLeftImageOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           //退出编辑状态
                           if(relayBoxRecyclerViewAdapter.getEditstate()){
                               changeEditState(true);
                           }
                           finish();
                       }
                   }).setLeftTextOnClickListener(new View.OnClickListener() { //左边编辑文字的点击事件
               @Override
               public void onClick(View v) {
                   changeEditState( relayBoxRecyclerViewAdapter.getEditstate());
               }
           }).setRightImageOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   //如果是编辑状态就退出编辑状态
                   if(relayBoxRecyclerViewAdapter.getEditstate()){
                       changeEditState(true);
                   }

                   showPopWindow(v);
               }
           });
    }





    // 2017-09-26 从数据库查询所有中继盒子
    protected void initData(){
        mRelayBoxes = DataBaseHandle.queryAllRelayBox();
    }


    private void initRecyclerView() {
        mRecyclerView = (RecyclerView)findViewById(R.id.relay_box_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        relayBoxRecyclerViewAdapter = new RelayBoxRecyclerViewAdapter(this, mRelayBoxes);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(relayBoxRecyclerViewAdapter);
    }


    private void initRecycleViewClickListener() {

        relayBoxRecyclerViewAdapter.setOnItemClickListener(new RelayBoxRecyclerViewAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                RelayBox relayBox =  mRelayBoxes.get(position);
                Intent intent = new Intent(RelayBoxActivity.this, EditRelayBoxActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("edit_relay_box", relayBox);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onDeleteIconClick(View view, final int position) {

                if(AllVariable.CONNECT_RELAY) {

                    mDeleteRelayBox = mRelayBoxes.get(position);

                    mRemoveBoxPosition = position;

                    showLoadingDialog(mDeleteRelayBox.getNAME());

                    //删除中继盒子的 UDP指令  成功之后回调 uiDeleteRelayBoxSuccessCallback方法
                    RelayBoxUDPHandle.deleteRelayBox(AllVariable.CURRENT_USER_ID, mDeleteRelayBox.getBOX_ID(), mDeleteRelayBox.getIP());
                }else {
                    Toast.makeText(RelayBoxActivity.this , "只能在局域网环境删除中继盒子" , Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    /**
     * 删除本地数据库的一个中继盒子
     * @param relayBox
     */
    private void deleteRelayBox(RelayBox relayBox) {
        DataBaseHandle.deleteRelayBox(relayBox);
    }


    /**
     * 改变是否编辑的状态
     * @param isEdit
     */
    private void changeEditState(boolean isEdit){
        if(relayBoxRecyclerViewAdapter != null){
            if(isEdit){
                 relayBoxRecyclerViewAdapter.setEditState(false);
                 myTopBarBuilder.setLeftText(R.string.edit);
            }else {
                 relayBoxRecyclerViewAdapter.setEditState(true);
                 myTopBarBuilder.setLeftText(R.string.cancel);
            }
        }
    }


    /**
     * 添加中级盒子的处理广播
     */
    private BroadcastReceiver mAddRelayReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<RelayBox> relayBoxes = DataBaseHandle.queryAllRelayBox();
            updateUI(relayBoxes);
        }
    };


    /**
     * 刷新UI
     * @param relayBoxes
     */
    private void updateUI(List<RelayBox> relayBoxes) {
        if(mRelayBoxes != null) {
            mRelayBoxes.clear();
            for (int i = 0 ; i < relayBoxes.size() ; i++){
                mRelayBoxes.add(relayBoxes.get(i));
            }
        }
        relayBoxRecyclerViewAdapter.notifyDataSetChanged();
    }


    /**
     * 删除中继盒子 的回调
     * @param udpReceiver
     */
    @Override
    public void uiDeleteRelayBoxSuccessCallback(UDPReceiverData udpReceiver) {
            super.uiDeleteRelayBoxSuccessCallback(udpReceiver);

            String jsonData = udpReceiver.data;
            UDPResponseMsgBase responseMsg = JsonUtils.stringToObject(jsonData , UDPResponseMsgBase.class);

            if(responseMsg.getBODY().getRESULT() == UDPContants.UDP_RESPONSE_SUCCESS){//udp result 为true

                //1. 从本地数据库解绑删除一条中继盒子数据
                deleteRelayBox(mDeleteRelayBox);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRelayBoxes.remove(mRemoveBoxPosition);
                        relayBoxRecyclerViewAdapter.notifyItemRemoved(mRemoveBoxPosition);
                    }
                });


                //2017-10-17 删除中继盒子
                relayBoxDatas = new ArrayList<RelayBoxData>();
                RelayBoxData relayBoxData = new RelayBoxData();
                relayBoxData.setID(mDeleteRelayBox.getGUID());
                relayBoxDatas.add(relayBoxData);
                //2. 删除云端的对应的数据
                ServerCommunicationHandle.deleteRelayBox(relayBoxDatas ,new UIHttpCallBack(){
                    @Override
                    public void success(Object object) {
                        dismissLoadingDialog();
                    }

                    @Override
                    public void failure(Object object) {
                        dismissLoadingDialog();
                    }
                });

         }else if(responseMsg.getBODY().getRESULT() == UDPContants.UDP_RESPONSE_FAIL){
                dismissLoadingDialog();
            }
    }



    /**
     * 弹出POPWindow     true
     * @param v
     */
    private void showPopWindow(View v) {
        View view = LayoutInflater.from(RelayBoxActivity.this).inflate(R.layout.popupwindow_add_devices , null);
        TextView textView1 = (TextView) view.findViewById(R.id.pop_window_tv_1);
        textView1.setText(R.string.smart_wifi);

        TextView textView2= (TextView) view.findViewById(R.id.pop_window_tv_2);
        textView2.setText(R.string.add_relay_box);

        final MyPopupWindow popWindow = new MyPopupWindow(RelayBoxActivity.this,view, R.style.right_popupWindow_anim);

        //弹出PopupWindow设置窗口背景变暗
        mWindow = getWindow();
        popWindow.setWindowAlpha(mWindow, windowAlpha);
        popWindow.showAsDropDown(v, popOffX , popOffY);

        RelativeLayout rl_1 = (RelativeLayout) view.findViewById(R.id.pop_window_rl_1);
        RelativeLayout rl_2 = (RelativeLayout) view.findViewById(R.id.pop_window_rl_2);

        rl_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelPopWindow(popWindow);
            }
        });

        rl_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RelayBoxActivity.this, AddRelayBoxActivity.class));
                cancelPopWindow(popWindow);
            }
        });
    }

    /**
     * 关闭PopWindow  true
     * @param popWindow
     */
    private void cancelPopWindow(MyPopupWindow popWindow) {
        if(popWindow != null && popWindow.isShowing()) {
            popWindow.dismiss();
        }
    }

}
