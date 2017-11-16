package kap.com.smarthome.android.communication.bean.base.DATABean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yushq on 2017/10/27 0027.
 *
 * 通过该实例把场景数据转换成中继盒子需要的json格式
 *
 * 中继盒子和云端的数据字段不同
 *
 */

public class UDPScenesData  implements Serializable {

    private static final long serialVersionUID=1L;

    private String SCENE_ID;

    private String SCENE_NAME;

    private String USER_ID;

    private String DEVICE_NUMBER;

    private String TRIGGER_NUMBER;

    private String TRIGGER_STATUS;

    private int type;

    private int SCENE_ICON;

    /*private List<UDPScenesDeviceData>

    private List<UDPScenesTriggerData>*/

}
