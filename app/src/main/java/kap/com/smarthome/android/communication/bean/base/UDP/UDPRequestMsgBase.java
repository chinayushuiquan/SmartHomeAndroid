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

public  class UDPRequestMsgBase implements Serializable{

    @JSONField(name = "HEAD" , ordinal = 1)
    private JsonHeadBase HEAD;

    @JSONField(name = "BODY", ordinal = 2)
    private UDPRequestBodyBase BODY;

    public UDPRequestMsgBase() {

    }

    public UDPRequestMsgBase(JsonHeadBase HEAD, UDPRequestBodyBase BODY) {
        this.HEAD = HEAD;
        this.BODY = BODY;
    }

    public void setHEAD(JsonHeadBase HEAD){
        this.HEAD = HEAD;
    }

    public JsonHeadBase getHEAD(){
        return this.HEAD;
    }

    public void setBODY(UDPRequestBodyBase BODY){
        this.BODY = BODY;
    }

    public UDPRequestBodyBase getBODY(){
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
