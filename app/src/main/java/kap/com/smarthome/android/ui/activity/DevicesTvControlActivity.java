package kap.com.smarthome.android.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import java.util.List;
import kap.com.smarthome.android.R;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.IRKey;
import kap.com.smarthome.android.data.bean.RelayBox;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.RelayBoxUDPHandle;
import kap.com.smarthome.android.ui.view.MyPopupWindow;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

/**
 * Created by Administrator on 2017/11/1 0001.
 */

public class DevicesTvControlActivity  extends  BaseActivity implements View.OnClickListener{

    //所有的按钮
    private Button btn0 , btn1 , btn2 , btn3 , btn4, btn5 ,
            btn6 , btn7 , btn8 , btn9 , btn10, btn11,
            btn12 ;

    private Button number_1 , number_2 , number_3 , number_4, number_5,
            number_6 , number_7 , number_8 , number_9, number_choose_channal, number_0, number_back;


    private IRKey irKey;

    private List<IRKey> irKeyList;


    //实现弹出PopupWindow的时候设备背景界面变暗
    private Window mWindow;
    private float windowAlpha = 0.8f;
    private View mNumberView  = null;
    private MyPopupWindow numberPopWindow;


    private String device_id;
    private Devices controlIrDevices;
    private RelayBox relayBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_control_templte_1);


        controlIrDevices = (Devices) getIntent().getSerializableExtra("ir_device");
        device_id = controlIrDevices.getDEVICE_ID();

        Log.e("CHRIS", "onCreate: 红外遥控id =  " + device_id);
        irKeyList = DataBaseHandle.queryOneIrDeviceAllKeys(device_id);
        relayBox = DataBaseHandle.queryOneRelayBox(controlIrDevices.getRELAY_ID());


        btn0 = (Button) findViewById(R.id.tv1_btn_enter);
        btn1 = (Button) findViewById(R.id.tv1_btn_power);
        btn2 = (Button) findViewById(R.id.tv1_btn_vol_mute);
        btn3 = (Button) findViewById(R.id.tv1_btn_vol_add);
        btn4 = (Button) findViewById(R.id.tv1_btn_vol_sub);
        btn5 = (Button) findViewById(R.id.tv1_btn_left);
        btn6 = (Button) findViewById(R.id.tv1_btn_top);
        btn7 = (Button) findViewById(R.id.tv1_btn_right);
        btn8 = (Button) findViewById(R.id.tv1_btn_bottom);
        btn9 = (Button) findViewById(R.id.tv1_btn_ok);
        btn10 = (Button) findViewById(R.id.tv1_btn_number);
        btn11 = (Button) findViewById(R.id.tv1_btn_back);
        btn12 = (Button) findViewById(R.id.tv1_btn_menu);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);


        //初始化数据键盘
        mNumberView  = LayoutInflater.from(this).inflate(R.layout.tv1_number_dialog , null);
        number_1 = (Button) mNumberView.findViewById(R.id.bt_1);
        number_2 = (Button) mNumberView.findViewById(R.id.bt_2);
        number_3 = (Button) mNumberView.findViewById(R.id.bt_3);
        number_4 = (Button) mNumberView.findViewById(R.id.bt_4);
        number_5 = (Button) mNumberView.findViewById(R.id.bt_5);
        number_6 = (Button) mNumberView.findViewById(R.id.bt_6);
        number_7 = (Button) mNumberView.findViewById(R.id.bt_7);
        number_8 = (Button) mNumberView.findViewById(R.id.bt_8);
        number_9 = (Button) mNumberView.findViewById(R.id.bt_9);
        number_choose_channal = (Button) mNumberView.findViewById(R.id.bt_ds);
        number_0 = (Button) mNumberView.findViewById(R.id.bt_0);
        number_back = (Button) mNumberView.findViewById(R.id.bt_back);

        number_1.setOnClickListener(this);
        number_2.setOnClickListener(this);
        number_3.setOnClickListener(this);
        number_4.setOnClickListener(this);
        number_5.setOnClickListener(this);
        number_6.setOnClickListener(this);
        number_7.setOnClickListener(this);
        number_8.setOnClickListener(this);
        number_9.setOnClickListener(this);
        number_choose_channal.setOnClickListener(this);
        number_0.setOnClickListener(this);
        number_back.setOnClickListener(this);

    }

    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {
        myTopBarBuilder
                .setLeftImage(R.drawable.back_icon_black)
                .setTitleText(R.string.tv_rc)
                .setLeftImageOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setRightTextOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {



            }
        });
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv1_btn_enter:
                // 获取到红外值发送
                IrKeyControl(0);
                break;

            case R.id.tv1_btn_power:
                // 获取到红外值发送
                IrKeyControl(1);
                break;
            case R.id.tv1_btn_vol_mute:
                // 获取到红外值发送
                IrKeyControl(2);
                break;

            case R.id.tv1_btn_vol_add:
                // 获取到红外值发送
                IrKeyControl(3);
                break;

            case R.id.tv1_btn_vol_sub:
                 // 获取到红外值发送
                IrKeyControl(4);
                break;


            case R.id.tv1_btn_left:

                // 获取到红外值发送
                IrKeyControl(5);

                break;


            case R.id.tv1_btn_top:
// 获取到红外值发送
                IrKeyControl(6);
                break;



            case R.id.tv1_btn_right:
                IrKeyControl(7);
                break;


            case R.id.tv1_btn_bottom:
                IrKeyControl(8);



                break;
            case R.id.tv1_btn_ok:

                IrKeyControl(9);

                break;
            case R.id.tv1_btn_number:
                //数字键盘
                if(numberPopWindow == null) {
                    numberPopWindow = new MyPopupWindow(this , mNumberView, R.style.right_popupWindow_anim);
                }
                //弹出PopupWindow设置窗口背景变暗
                mWindow = getWindow();
                numberPopWindow.setWindowAlpha(mWindow, windowAlpha);
                numberPopWindow.showAtLocation(mNumberView, Gravity.CENTER_VERTICAL, 0, 0);
                break;
            case R.id.tv1_btn_back:

                IrKeyControl(21);
                break;

            case R.id.tv1_btn_menu:
                IrKeyControl(22);
                break;
            case R.id.bt_1:

                IrKeyControl(10);

                break;
            case R.id.bt_2:


                IrKeyControl(11);

                break;
            case R.id.bt_3:

                IrKeyControl(12);

                break;
            case R.id.bt_4:

                IrKeyControl(13);
                break;
            case R.id.bt_5:

                IrKeyControl(14);

                break;
            case R.id.bt_6:

                IrKeyControl(15);

                break;
            case R.id.bt_7:

                IrKeyControl(16);

                break;
            case R.id.bt_8:

                IrKeyControl(17);

                break;
            case R.id.bt_9:

                IrKeyControl(18);

                break;
            case R.id.bt_ds:

                IrKeyControl(19);

                break;
            case R.id.bt_0:

                IrKeyControl(20);

                break;
            case R.id.bt_back:

                showClick("数字键盘返回键");

                if(numberPopWindow != null && numberPopWindow.isShowing()){
                    numberPopWindow.dismiss();
                    numberPopWindow = null;
                }
                break;
        }
    }

    private void IrKeyControl(int keyIndex) {
        if(irKeyList != null){

            IRKey irKey = null;

            for (int i = 0 ; i <irKeyList.size() ; i++){
                if(keyIndex  == irKeyList.get(i).getINDEX()){
                    irKey = irKeyList.get(i);
                }
            }

            if(irKey != null){
                RelayBoxUDPHandle.controlDevices(controlIrDevices, irKey.getKEY1() , relayBox.getIP());
            }else{
                showClick("没有相应的学习值");
            }
            Log.e("UDP", "红外按键值: " + irKey.toString());

        }
    }

    private void showClick(String btn) {
        Toast.makeText(this, "按键"+btn , Toast.LENGTH_LONG).show();
    }
}
