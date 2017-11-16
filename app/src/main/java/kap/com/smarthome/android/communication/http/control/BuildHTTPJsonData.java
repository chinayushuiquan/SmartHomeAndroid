package kap.com.smarthome.android.communication.http.control;



import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPRequestBodyBase;
import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPRequestMsgBase;
import kap.com.smarthome.android.communication.bean.base.JsonHeadBase;

import kap.com.smarthome.android.presenter.utils.JsonUtils;


/**
 * Created by Administrator on 2017/9/30 0030.
 */

public class BuildHTTPJsonData {

    /**
     * 构建包含Head 、body的 JSON转换Object
     * @param HTTPJsonHead
     * @param HTTPJsonBody
     * @return HTTPJsonMsgBase
     */
    public static HTTPRequestMsgBase buildUDPJsonMsg(JsonHeadBase HTTPJsonHead, HTTPRequestBodyBase HTTPJsonBody){
        HTTPRequestMsgBase httpJsonHeadBody = new HTTPRequestMsgBase(HTTPJsonHead, HTTPJsonBody);
        return httpJsonHeadBody;
    }


    /**
     * 可以从外部传入head 和 body 生成一个UDP数据
     * @param head
     * @param body
     * @return
     */
    public static String buildData(JsonHeadBase head , HTTPRequestBodyBase body){
        return JsonUtils.gsonJsonToString(buildUDPJsonMsg(head, body));
    }


}
