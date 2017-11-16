package kap.com.smarthome.android.communication.http.listener;

/**
 * Created by Administrator on 2017/5/25 0025.
 * 继承DealHttpReponseListener,添加一个下载进度的监听
 */

public interface DealHttpResponseDownloadListener extends DealHttpResponseListener {

    public void onProgress(int progrss);

}
