package kap.com.smarthome.android.ui.dialog;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.RelayBox;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.RelayBoxUDPHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;


public class Curtain extends DeviceControlDialog {

	private Context context;
	private Devices device;
	private ImageView curtainState;
	private Button open;
	private Button close;
	private Button stop;

	private static final int OPEN = 0x00ff;
	private static final int STOP = 0x0080;
	private static final int CLOSE = 0x0000;

	//场景选择
	//SceneCommand sc;
	boolean isCommandSelect=false;
	//逻辑指令选择
	//LogicalCommand lc;

	//与设备关联中继盒子的ip
	private String boxIP =  "255.255.255.255";

	public Curtain(Context context, Devices device) {
		super(context, R.style.device_control_dialog_Theme);
		this.context = context;
		this.device = device;
		initCurtainDialog();
		setCanceledOnTouchOutside(true);
		initBoxIP();
		// sendCkeckMsg();
	}

	/**
	 * 初始化盒子的id
	 */
	private void initBoxIP() {
		RelayBox relayBox  = DataBaseHandle.queryOneRelayBox(device.getRELAY_ID());
		if(relayBox != null){
			boxIP = relayBox.getIP();
		}
	}

	/*public Curtain(Context context, SceneCommand sc) {
		super(context, R.style.Theme_base);
		this.context = context;
		isCommandSelect=true;
		this.sc = sc;
		try {
			device=DataManage.getDevice(sc.device_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initCurtainDialog();
		setCanceledOnTouchOutside(true);
		// sendCkeckMsg();
	}
	public Curtain(Context context, LogicalCommand lc) {
		super(context, R.style.Theme_base);
		this.context = context;
		isCommandSelect=true;
		this.lc = lc;
		try {
			device=DataManage.getDevice(sc.device_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initCurtainDialog();
		setCanceledOnTouchOutside(true);
		// sendCkeckMsg();
	}*/

	public void initCurtainDialog() {

		setContentView(R.layout.dialog_curtain);

		curtainState = (ImageView) findViewById(R.id.curtain_image);

		open = (Button)findViewById(R.id.open);
		close = (Button) findViewById(R.id.close);
		stop = (Button) findViewById(R.id.stop);

		refreshDialog();

		open.setOnClickListener(new Button.OnClickListener() {// 打开窗帘
			public void onClick(View v) {
				//if(!isCommandSelect){
					sendControllMsg("00ff");
				    curtainState.setBackgroundResource(R.drawable.device_curtain_open);
				/*}else{
					setSc("00ff",open.getText().toString());
				}*/
			}
		});

		close.setOnClickListener(new Button.OnClickListener() {// 关闭窗帘
			public void onClick(View v) {

				//if(!isCommandSelect){
					sendControllMsg("0000");
				    curtainState.setBackgroundResource(R.drawable.device_curtain_close);
				/*}else{
					setSc("0000",close.getText().toString());
				}*/
			}
		});

		stop.setOnClickListener(new Button.OnClickListener() {// 停止窗帘
			public void onClick(View v) {
				//if(!isCommandSelect){
				sendControllMsg("0080");
				curtainState.setBackgroundResource(R.drawable.device_curtain_stop);

				/*String control_json = "{\"HEAD\":{\"TIMESTAMP\":1508750858880,\"SERVICEID\":\"12345678\",\"VERSION\":\"V0.02\",\"SERIALNUM\":9,\"REPEATCOUNT\":1,\"USERID\":\"4028b8815f145833015f1466607b0000\",\"DEVICEID\":\"1001116016400000\"},\"BODY\":{\"INSTP\":\"DEVICECONTROLREQ\",\"TIMESTAMP\":0,\"DEVICEID\":\"0B100000\",\"RESULT\":0,\"CONTROLTYPE\":0,\"VALUE\":\"0000\"}}";
				ServerCommunicationHandle.controlDevice(control_json, new UIHttpCallBack() {
					@Override
					public void success(Object object) {

					}

					@Override
					public void failure(Object object) {

					}
				});*/
				/*}else{
					setSc("0080",stop.getText().toString());
				}*/
			}
		});
	}


	/*private void setSc(String action, String action_name){
		sc.action=action;
		sc.action_name=action_name;
		dismiss();
	}*/

	private void sendControllMsg(String actionvalue) {
		/*Msg msg = MsgFactory.getControllDeviveMsg(device.device_id,
				device.control_channel, actionvalue);
		// TODO getRfid
		Log.i("Curtain", "全能家电窗帘控制信息>>"+msg);
		KotiSuperApllication.getInstance().sendMsg(msg);*/

		if(AllVariable.WIFI_CONNECT && AllVariable.CONNECT_RELAY){//本地控制如果超时

           RelayBoxUDPHandle.controlDevices(device, actionvalue , boxIP);

		}else if(AllVariable.MOBILE_CONNECT || !AllVariable.CONNECT_RELAY){//远程控制

			    AllVariable.CURRENT_DEVICE_CONTROL =  RelayBoxUDPHandle.controlDevicesHttpData(device, actionvalue);

				ServerCommunicationHandle.controlDevice(AllVariable.CURRENT_DEVICE_CONTROL, new UIHttpCallBack() {
					@Override
					public void success(Object object) {

						Log.e("HTTP", "窗帘远程控制成功");

					}
					@Override
					public void failure(Object object) {

						Log.e("HTTP", "窗帘远程控制失败");

					}
				});
		     }
	}

	/*private void sendCkeckMsg() {
		Msg msg = MsgFactory.getCheckDeviceStateMsg(device.device_id,
				device.control_channel);
		// TODO getRfid
		// Msg msg = MsgFactory.getCheckDeviceStateMsg(device.getID(), device
		// .getRfid());
		KotiSuperApllication.getInstance().sendMsg(msg);
	}*/



	// 更新窗帘对话框窗帘状态
	@Override
	public void refreshDialog() {
		try {
			/*String deviceId = device.device_id;
			Devices device = DataManage.getDevice(deviceId);
			int state = device.state;*/
			//refreshDialogUI(state);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void refreshDialogUI(int state) {
		/*if (state == Devices.STATE_CLOSE || state == Devices.STATE_UNKNOWN) {
			curtainState.setBackgroundResource(R.drawable.device_curtain_close1);
		} else if (state == Devices.STATE_STOP) {
			curtainState.setBackgroundResource(R.drawable.device_curtain_stop);
		} else {
			curtainState.setBackgroundResource(R.drawable.device_curtain_open1);
		}*/
//		switch (state) {
//		case OPEN:
//			curtainState.setBackgroundResource(R.drawable.device_curtain_open1);
//			break;
//		case CLOSE:
//			curtainState.setBackgroundResource(R.drawable.device_curtain_close);
//			break;
//		case STOP:
//			curtainState.setBackgroundResource(R.drawable.device_curtain_stop);
//			break;
//		}
	}

	@Override
	public void refreshDialog(String deviceid, int state) {
		/*if (deviceid.equals(device.device_id)) {
			refreshDialogUI(state);
		}*/
	}
}
