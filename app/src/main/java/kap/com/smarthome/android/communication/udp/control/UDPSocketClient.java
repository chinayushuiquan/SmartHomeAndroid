package kap.com.smarthome.android.communication.udp.control;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;

import kap.com.smarthome.android.communication.bean.base.UDP.UDPReceiverData;
import kap.com.smarthome.android.communication.udp.constants.UDPContants;


/**
 * Created by Administrator on 2017/5/15 0015.
 */

public  class UDPSocketClient {

    private static final String TAG = "UDPSocketClient";

    private DatagramSocket mUDPSocket;

    /**
     * 将发送和接收的数据封装成对象CommQueueItem，并加入队列
     */
    private final LinkedBlockingQueue<UDPReceiverData> senderDataQueue;
    private final LinkedBlockingQueue<UDPReceiverData> receiverDataQueue;
    /**
     * 停止信号
     */
    private final UDPReceiverData stopSignal = new UDPReceiverData();

    /**
     * 接收和发送的线程类
     */
    private SenderThread senderThread = null;
    private ReceiverThread receiverThread = null;
    private boolean isStarted = false;


    public static UDPSocketClient instance = null;


    private UDPSocketClient(){
        senderDataQueue = new LinkedBlockingQueue<>();
        receiverDataQueue = new LinkedBlockingQueue<>();
        start();
    }

    public  synchronized  static UDPSocketClient getInstance(){
        if(instance == null){
            instance = new UDPSocketClient();
        }
        return  instance;
    }


    /**
     * 实例化一个
     * @throws SocketException
     */
    private   void  createSocket() throws SocketException {
        mUDPSocket = new DatagramSocket();
       // mUDPSocket.setReuseAddress(true);
    }

    /**
     * 发送数据，将封装数据添加到发送队列中
     * 该方法供外面调用调用
     * @param data
     */
    public void sendData(UDPReceiverData data) {
        senderDataQueue.offer(data);

    }

    /**
     * 接收数据，从对列中返回封装的好的数据实体
     * @return
     * @throws InterruptedException
     */
    public UDPReceiverData receiveData() throws InterruptedException {
        if(receiverDataQueue != null) {
            return receiverDataQueue.take();
        }
        return  null;
    }


    /**
     * 实例化发送和接收数据的监听线程
     */
    public void start() {
        if (!isStarted) {
            isStarted = true;
            try {
                 createSocket();
            } catch (SocketException e) {
                e.printStackTrace();
                isStarted = false;
                return;
            }
            senderThread = new SenderThread();
            senderThread.start();
            receiverThread = new ReceiverThread();
            receiverThread.start();
        }
    }


    /**
     * 停止UDP接收和发送线程，并将Socket端口关闭
     */
    public synchronized void stop() {
        if (isStarted) {
            if (senderThread != null) {
                senderThread.interrupt();
                senderThread = null;
            }
            if (receiverThread != null) {
                receiverThread.interrupt();
                receiverThread = null;
            }
            if (mUDPSocket != null) {
                mUDPSocket.close();
                mUDPSocket = null;
            }
            isStarted = false;
        }
    }


    /**
     * 发送线程
     */
    private class SenderThread extends Thread {
        @Override
        public void run() {
            try {
                runSenderThread();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            } finally {
                UDPSocketClient.this.stop();
            }
        }
    }

    /**
     * UDP发送的具体方法
     * @throws InterruptedException
     * @throws IOException
     */
    protected void runSenderThread() throws InterruptedException, IOException {
        //先让发送线程等待一下，先启动接收线程
        Thread.sleep(1);
        Thread.yield();
        while (!Thread.interrupted()) {
            UDPReceiverData queueItem = senderDataQueue.take();
            // 若是结束信号，退出运行
            if (queueItem == stopSignal) {
                break;
            }
            byte[] data = queueItem.data.getBytes();
            DatagramPacket packet = new DatagramPacket(data, 0, data.length);
            packet.setAddress(InetAddress.getByName(queueItem.ipAddr));
            packet.setPort(queueItem.port);
            mUDPSocket.send(packet);
            Log.w("UDP" , "send data:" + queueItem.data + " to " +
                    packet.getAddress().toString() + ":" + packet.getPort());
        }
    }

    /**
     * 接收线程
     */
    private class ReceiverThread extends Thread {
        @Override
        public void run() {
            try {
                runReceiverThread();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                UDPSocketClient.this.stop();
            }
        }
    }

    /**
     * UDP接收数据的具体方法
     */
    protected void runReceiverThread() throws IOException {
        byte[] buffer = new byte[UDPContants.UDP_RECEIVER_BUF_SIZE];
        while (!Thread.interrupted()) {
            DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);
            mUDPSocket.receive(packet);
            int recLen = packet.getLength();
            if (recLen > 0) {
                String packetString = new String(buffer, 0, recLen);
                UDPReceiverData queueItem = new UDPReceiverData(packetString,
                        packet.getAddress().getHostAddress() , packet.getPort());
                if(receiverDataQueue  != null) {
                    receiverDataQueue.offer(queueItem);
                }
                Log.i("UDP", " 接收到UDP数据 = " + queueItem.data + " from " +
                        queueItem.ipAddr + ":" + queueItem.port);
            }
        }
    }
}
