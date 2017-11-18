package kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponse.UserHead;

import com.alibaba.fastjson.annotation.JSONField;

import kap.com.smarthome.android.communication.bean.base.JsonHeadBase;

/**
 * Created by Administrator on 2017/10/10 0010.
 */

public class HTTPResponseUpdateHeadMsg {

    @JSONField(name = "HEAD" , ordinal = 1)
    private JsonHeadBase HEAD;

    @JSONField(name = "BODY", ordinal = 2)
    private HTTPResponseUpdateHeadBody BODY;

   // private static int SERIAL_NUM=0x80000000;

    public HTTPResponseUpdateHeadMsg(JsonHeadBase HEAD, HTTPResponseUpdateHeadBody BODY) {
        this.HEAD = HEAD;
        this.BODY = BODY;
    }

    public void setHEAD(JsonHeadBase HEAD){
        this.HEAD = HEAD;
    }

    public JsonHeadBase getHEAD(){
        return this.HEAD;
    }

    public void setBODY(HTTPResponseUpdateHeadBody BODY){
        this.BODY = BODY;
    }

    public HTTPResponseUpdateHeadBody getBODY(){
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
    /*public void updateSERIALNUM(){
        HEAD.setSERIALNUM(Integer.toHexString(SERIAL_NUM));
        SERIAL_NUM+=1;
        if(SERIAL_NUM>0xFFFFFFFF){
            SERIAL_NUM=0x80000000;
        }
    }*/

}
