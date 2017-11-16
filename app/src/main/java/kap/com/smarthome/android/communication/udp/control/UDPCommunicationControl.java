package kap.com.smarthome.android.communication.udp.control;


import android.util.Log;

import kap.com.smarthome.android.communication.bean.base.UDP.UDPReceiverData;
import kap.com.smarthome.android.communication.udp.doapi.UDPDataManage;

/**
 * Created by Administrator on 2017/5/31 0031.
 */

public  class UDPCommunicationControl {
    //UDP控制基类
    UDPSocketClient mUDPSocketClient;
    //UDP 数据封装基类
    UDPReceiverData mUDPReceiverData;

    //防丢包重复发送的次数和时间
    private int mReTryCount;
    private int mRetryWaitTime;
    //发送和接收的线程
    private Thread  mSendThread;
    private Thread  mReceiverThread;
    //是否成功和开始发送和接收数据
    private boolean isStarted = false;
    //是否成功收到数据
    private boolean isSuccess;
    //接收到数据的回调监听
    private UDPDataManage mReceiveListen;

    public UDPCommunicationControl(String  data, String ipAddr, int port , int reTryCount,
                                   int retryWaitTime , UDPDataManage receiveListen) {
        mUDPReceiverData = new UDPReceiverData(data,ipAddr,port);
        mReTryCount = reTryCount;
        mRetryWaitTime = retryWaitTime;
        mReceiveListen = receiveListen;
        //开始UDPControl基类的发送和接收线程的监听
        mUDPSocketClient = UDPSocketClient.getInstance();

    }


    /**
     * 开始
     */
    public synchronized void startUDP() {
        if (!isStarted) {
            isStarted = true;
            if (mSendThread == null) {
                startSendThread();
            }
            if (mReceiverThread == null) {
                startReceiverThread();
            }
        }
    }


    /**
     * 结束
     */
    public synchronized void stopUDP() {
        if (isStarted) {	// 首次stop
            isStarted = false;
            isSuccess = false;
            if (mSendThread != null) {
                mSendThread.interrupt();
                mSendThread = null;
            }
            if (mReceiverThread != null) {
                mReceiverThread.interrupt();
                mReceiverThread = null;
            }
            // 向上层报告：运行结果失败。
            if (!isSuccess && mReceiveListen != null) {
                mReceiveListen.udpReceiverFailure();
            }
        }

    }

    /**
     * 直接把要发送的数据压入发送队列
     * @param commUDPDataQueueItem
     */
    public void sendData(UDPReceiverData commUDPDataQueueItem) {
          if(mUDPSocketClient == null) {
              mUDPSocketClient = UDPSocketClient.getInstance();
          }
            mUDPSocketClient.sendData(commUDPDataQueueItem);
    }


    /**
     * 从接收队列中直接取出单个结果
     */
    public UDPReceiverData receiveData() {
        if(mUDPSocketClient != null){
            try {
               return  mUDPSocketClient.receiveData();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return  null;
    }


    private void startSendThread(){
        mSendThread = new Thread(sendUDPDataRunnable);
        mSendThread.start();
    }


    private void startReceiverThread(){
        mReceiverThread = new Thread(receiverUDPDataMethod);
        mReceiverThread.start();
    }

    /**
     * 发送数据的线程
     */
    private Runnable sendUDPDataRunnable = new Runnable() {
        @Override
        public void run() {
            sendUDPDataMethod();
        }
    };

    /**
     *发送数据的Method
     */
  private  void   sendUDPDataMethod() {
      //防止丢包，进行重复发送，发送的次数可以进行自定义, 每次发送的间隔时间可以自己设定
      try {
          Thread.sleep(200);
          for (int i = 0; i < mReTryCount; i++) {
              mUDPSocketClient.sendData(mUDPReceiverData);
              Thread.sleep(mRetryWaitTime);
          }
      } catch (InterruptedException e) {
          e.printStackTrace();
          stopUDP();
      }
  }


    /**
     * 接收数据的线程
     */
    private  Runnable receiverUDPDataMethod = new Runnable() {
        @Override
        public void run() {
            receiveUDPDataMethod();
        }
    };

    /**
     * 接收数据的Method
     */
    private void receiveUDPDataMethod() {
        try {
            while (!Thread.interrupted()){
                UDPReceiverData queueItem = mUDPSocketClient.receiveData();
                if(queueItem != null){
                    isSuccess = true;
                    Log.i("UDP", " receiveUDPDataMethod 进行回调 ");
                    mReceiveListen.udpReceiverSuccess(queueItem);
                   }
                }
        }catch (InterruptedException e) {
            e.printStackTrace();
            stopUDP();
        }finally {
          if (!isSuccess){
              mReceiveListen.udpReceiverFailure();
          }
    }
}













}
