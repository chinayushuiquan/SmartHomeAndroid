package kap.com.smarthome.android.ui.dialog;

import android.app.Dialog;
import android.content.Context;

public abstract class DeviceControlDialog extends Dialog {

	public DeviceControlDialog(Context context) {
		super(context);
		
	}
	
	public DeviceControlDialog(Context context, int theme) {
		super(context, theme);
		
	}
	
	public abstract void refreshDialog();
	public abstract void refreshDialog(String deviceid, int state);
}
