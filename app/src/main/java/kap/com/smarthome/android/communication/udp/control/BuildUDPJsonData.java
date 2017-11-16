package kap.com.smarthome.android.communication.udp.control;



import kap.com.smarthome.android.communication.bean.base.JsonHeadBase;

import kap.com.smarthome.android.communication.bean.base.UDP.UDPRequestBodyBase;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPRequestMsgBase;
import kap.com.smarthome.android.communication.udp.constants.UDPContants;
import kap.com.smarthome.android.communication.udp.doapi.UDPDataManage;
import kap.com.smarthome.android.presenter.utils.JsonUtils;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public class BuildUDPJsonData{
    /**
     * 构建包含Head 、body的 JSON转换Object
     * @param UDPJsonHead
     * @param UDPJsonBody
     * @return
     */
   public static UDPRequestMsgBase buildUDPJsonMsg(JsonHeadBase UDPJsonHead, UDPRequestBodyBase UDPJsonBody){
       UDPRequestMsgBase udpJsonHeadBody = new UDPRequestMsgBase(UDPJsonHead, UDPJsonBody);
       return udpJsonHeadBody;
   }

    /**
     * 可以从外部传入head 和 body 生成一个UDP数据
     * @param head
     * @param body
     * @return
     */
    public static String buildData(JsonHeadBase head , UDPRequestBodyBase body){
        return JsonUtils.fastJsonToString(buildUDPJsonMsg(head, body));
    }


    /**
     * 发送UDP广播  传入要进行的操作编号，构造出不同的Json信息
     */
    public static void  sendUDPDataToAllRelay(String jsonData){
        //发送数据
        UDPDataManage.getInstance().sendUDPDataBox(
                jsonData,
                UDPContants.UDP_BROADCAST_RELAY_BXO_IP,
                UDPContants.UDP_TO_RELAY_BOX_PORT,
                UDPContants.UDP_REPEAT_SEND_COUNT,
                UDPContants.UDP_REPEAT_SEND_TIME);
    }

    /**
     * 点对点发送UDP
     * @param relayBoxIP
     */
    public  static void sendUDPDataToOneRelayBox(String jsonData, String  relayBoxIP ){
        UDPDataManage.getInstance().sendUDPDataBox(
                jsonData,
                relayBoxIP,
                UDPContants.UDP_TO_RELAY_BOX_PORT,
                UDPContants.UDP_REPEAT_SEND_COUNT,
                UDPContants.UDP_REPEAT_SEND_TIME);
    }



}
