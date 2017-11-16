package kap.com.smarthome.android.communication.bean.base.UDP;

/**
 * Created by Administrator on 2017/5/31 0031.
 */


/**
 * UDP发送和接收的数据的基类，封装发送的数据的实体
 *
 */

public class UDPReceiverData<T>{
    //
    public String data;
    public T mUDPData;
    public String ipAddr;
    public int port;

    public UDPReceiverData() {

    }

    public UDPReceiverData(String data, String ipAddr, int port) {
        this();
        this.data = data;
        this.ipAddr = ipAddr;
        this.port = port;
    }

    public UDPReceiverData(T data, String ipAddr, int port) {
        this();
        this.mUDPData = data;
        this.ipAddr = ipAddr;
        this.port = port;
    }

    @Override
    public String toString() {
        return "data=" + data + ", ipAddr=" + ipAddr + ", port=" + port;
    }

}
