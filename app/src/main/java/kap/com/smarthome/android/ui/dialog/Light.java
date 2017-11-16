package kap.com.smarthome.android.ui.dialog;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.RelayBox;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.RelayBoxUDPHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;

public class Light extends DeviceControlDialog {

	private Context mContext;

	private Devices device;
	private SeekBar seekBar;
	private Button lightOpen;
	private Button lightClose;
	private Button half;
	private ImageView lightImage;

	//与设备关联中继盒子的ip
	private String boxIP =  "255.255.255.255";





	public static final short[] STATE = { 0x00, 0x11, 0x22, 0x33, 0x44, 0x55,
			0x66, 0x77, 0x88, 0x99, 0xAA, 0xBB, 0xFF };

	public static final String[] VALUE = { "0000", "0011", "0022", "0033",
			"0044", "0055", "0066", "0077", "0088", "0099", "00AA", "00BB",
			"00FF" };

	//场景指令选择
	//SceneCommand sc;

	//boolean isCommandSelect=false;

	//逻辑指令选择
	//LogicalCommand lc;

	public Light(Context context, Devices device) {
		super(context, R.style.device_control_dialog_Theme);
		this.mContext = context;
		this.device = device;
		initLightDialog();
		setCanceledOnTouchOutside(true);
		sendCkeckMsg();
		initBoxIP();
	}

	/**
	 * 初始化盒子的id
	 */
	private void initBoxIP() {
		RelayBox  relayBox  = DataBaseHandle.queryOneRelayBox(device.getRELAY_ID());
		if(relayBox != null){
			boxIP = relayBox.getIP();
		}
	}

	/*public Light(Context context, SceneCommand sc) {
		super(context, R.style.device_control_dialog_Theme);
		this.mContext = context;
		this.sc = sc;
		isCommandSelect=true;
		try {
			device=DataManage.getDevice(sc.device_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initLightDialog();
		setCanceledOnTouchOutside(true);
		sendCkeckMsg();
	}

	public Light(Context context, LogicalCommand lc) {
		super(context, R.style.Theme_base);
		this.mContext = context;
		this.lc = lc;
		isCommandSelect=true;
		try {
			device=DataManage.getDevice(sc.device_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initLightDialog();
		setCanceledOnTouchOutside(true);
		sendCkeckMsg();
	}
	private void setSc(String action, String action_name){
		if(sc!=null){
			sc.action=action;
			sc.action_name=action_name;
		}
		if(lc!=null){
			lc.action=action;
			lc.action_name=action_name;
		}

		dismiss();

	}*/

	public void initLightDialog() {
		setContentView(R.layout.dialog_light);
		/*lightOpen = (Button) findViewById(R.id.open);
		lightClose = (Button) findViewById(R.id.close);
		half = (Button) findViewById(R.id.half);*/
		seekBar = (SeekBar) findViewById(R.id.lamp_control_seek_bar);
		lightImage = (ImageView) findViewById(R.id.light_image);
		refreshDialog();

		/*lightOpen.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!isCommandSelect){
					sendLightControlMsg("00E0");
				}else{
					setSc("00E0",lightOpen.getText().toString());
				}

			}



		});
		half.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {

				if(!isCommandSelect){
					sendLightControlMsg("0066");
				}else{
					setSc("0066",half.getText().toString());
				}

			}



		});
		lightClose.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {

				if(!isCommandSelect){
					sendLightControlMsg("0000");
				}else{
					setSc("0000",lightClose.getText().toString());
				}

			}
		});*/

		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

				    String actionValue = VALUE[seekBar.getProgress()];
				//if(!isCommandSelect){
				Log.e("CHRIS", " seekBar.getProgress() " + (float)seekBar.getProgress()/10);

				if(seekBar.getProgress() < 11 ){
					    lightImage.setBackgroundResource(R.drawable.device_adjust_lamp);
						lightImage.getBackground().setAlpha(seekBar.getProgress()*20+50);
					}else{
					    lightImage.setBackgroundResource(R.drawable.device_adjust_lamp_red);
					}

					sendLightControlMsg(actionValue);

				/*String con = "{\"HEAD\":{\"TIMESTAMP\":1508751568711,\"SERVICEID\":\"12345678\",\"VERSION\":\"V0.02\",\"SERIALNUM\":11,\"REPEATCOUNT\":1,\"USERID\":\"4028b8815f145833015f1466607b0000\",\"DEVICEID\":\"1001116016400000\"}," +
						"\"BODY\":{\"INSTP\":\"DEVICECONTROLREQ\",\"TIMESTAMP\":0,\"DEVICEID\":\"01101E0A\",\"RESULT\":0,\"CONTROLTYPE\":0,\"VALUE\":\"00FF\"}}";

				ServerCommunicationHandle.controlDevice(con, new UIHttpCallBack() {
					@Override
					public void success(Object object) {


					}

					@Override
					public void failure(Object object) {

					}
				});*/

				//}else{
				//	setSc(actionValue,"调光"+actionValue);
				//}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

			}
		});

	}

	private int getProgress(int state) {
		int progress = 0;
		for (int i = 0; i < STATE.length; i++) {
			if (STATE[i] == state) {
				progress = i;
				break;
			}
		}
		return progress;
	}

	//TODO  发送控制
	private void sendLightControlMsg(String actionvalue) {
	    /*Msg msg = MsgFactory.getControllDeviveMsg(device.device_id, device
				.getRfid(), actionvalue);*/

		/*Msg msg = MsgFactory.getControllDeviveMsg(device.device_id, device
				.control_channel, actionvalue);

		KotiSuperApllication.getInstance().sendMsg(msg);*/

		if(AllVariable.WIFI_CONNECT && AllVariable.CONNECT_RELAY){//本地控制如果超时

			RelayBoxUDPHandle.controlDevices(device, actionvalue , boxIP);

		}else if(AllVariable.MOBILE_CONNECT || !AllVariable.CONNECT_RELAY){//远程控制

			AllVariable.CURRENT_DEVICE_CONTROL =  RelayBoxUDPHandle.controlDevicesHttpData(device, actionvalue);

			ServerCommunicationHandle.controlDevice(AllVariable.CURRENT_DEVICE_CONTROL, new UIHttpCallBack() {
				@Override
				public void success(Object object) {

					Log.e("HTTP", "可调灯远程控制成功");

				}
				@Override
				public void failure(Object object) {

					Log.e("HTTP", "可调灯远程控制失败");

				}
			});
		}

		//RelayBoxUDPHandle.controlDevices(device,  actionvalue , boxIP);

	}

	private void sendCkeckMsg(){
		/*Msg msg = MsgFactory.getCheckDeviceStateMsg(device.device_id, device
				.control_channel);
		KotiSuperApllication.getInstance().sendMsg(msg);*/
	}



	// 更新灯光对话框中灯光状态
	@Override
	public void refreshDialog() {
		try {
			String deviceId = device.getDEVICE_ID();
			//HiDevice device = DataManage.getDevice(deviceId);
			//int state = device.state;
			//refreshDialogUI(state);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void refreshDialog(String deviceid, int state) {
		if(deviceid.equals(device.getDEVICE_ID())){
			refreshDialogUI(state);
		}
	}

	private void refreshDialogUI(int state) {
		int progress = getProgress(state);
		seekBar.setProgress(progress);
		if (progress > 5) {
			lightImage.setBackgroundResource(R.drawable.device_adjust_lamp);
		} else {
			lightImage.setBackgroundResource(R.drawable.device_adjust_lamp_red);
		}
	}

}
