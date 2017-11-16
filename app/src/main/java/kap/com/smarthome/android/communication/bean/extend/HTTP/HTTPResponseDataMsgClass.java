package kap.com.smarthome.android.communication.bean.extend.HTTP;

import com.alibaba.fastjson.annotation.JSONField;

import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPRequestBodyBase;
import kap.com.smarthome.android.communication.bean.base.JsonHeadBase;

/**
 * Created by Administrator on 2017/10/17 0017.
 */

public class HTTPResponseDataMsgClass<T> {

    @JSONField(name = "HEAD" , ordinal = 1)
    private JsonHeadBase HEAD;

    @JSONField(name = "BODY", ordinal = 2)
    private T BODY;

   // private static int SERIAL_NUM=0x80000000;

    public HTTPResponseDataMsgClass() {
    }

    public HTTPResponseDataMsgClass(JsonHeadBase HEAD, T BODY) {
        this.HEAD = HEAD;
        this.BODY = BODY;
    }

    public void setHEAD(JsonHeadBase HEAD){
        this.HEAD = HEAD;
    }

    public JsonHeadBase getHEAD(){
        return this.HEAD;
    }

    public void setBODY(T BODY){
        this.BODY = BODY;
    }

    public T getBODY(){
        return this.BODY;
    }

    @Override
    public String toString() {
        return "UDPJsonHeadBody{" +
                "HEAD=" + HEAD.toString() +
                ", BODY=" + BODY.toString() +
                '}';
    }

    /**
     * 流水号加一：主动发送msg的时候使用,中控器的流水号（0x80000000到0xFFFFFFFF）
     */
   /* public void updateSERIALNUM(){
        HEAD.setSERIALNUM(Integer.toHexString(SERIAL_NUM));
        SERIAL_NUM+=1;
        if(SERIAL_NUM>0xFFFFFFFF){
            SERIAL_NUM=0x80000000;
        }
    }*/

}
