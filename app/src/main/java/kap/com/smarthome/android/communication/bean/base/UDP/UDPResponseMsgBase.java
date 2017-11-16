package kap.com.smarthome.android.communication.bean.base.UDP;


import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

import kap.com.smarthome.android.communication.bean.base.JsonHeadBase;

/**
 * Created by Administrator on 2017/6/1 0001.
 *
 * 构建出UDP通信的Json 的 head body
 *
 */

public  class UDPResponseMsgBase implements Serializable{

    @JSONField(name = "HEAD" , ordinal = 1)
    private JsonHeadBase HEAD;

    @JSONField(name = "BODY", ordinal = 2)
    private UDPResponseBodyBase BODY;

    public UDPResponseMsgBase() {

    }

    public UDPResponseMsgBase(JsonHeadBase HEAD, UDPResponseBodyBase BODY) {
        this.HEAD = HEAD;
        this.BODY = BODY;
    }

    public void setHEAD(JsonHeadBase HEAD){
        this.HEAD = HEAD;
    }

    public JsonHeadBase getHEAD(){
        return this.HEAD;
    }

    public void setBODY(UDPResponseBodyBase BODY){
        this.BODY = BODY;
    }

    public UDPResponseBodyBase getBODY(){
        return this.BODY;
    }

    @Override
    public String toString() {
        return "UDPJsonHeadBody{" +
                "HEAD=" + HEAD.toString() +
                ", BODY=" + BODY.toString() +
                '}';
    }


}
