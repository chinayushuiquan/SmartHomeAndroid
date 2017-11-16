package kap.com.smarthome.android.communication.http.response;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import kap.com.smarthome.android.communication.http.exception.MyOkHttpException;
import kap.com.smarthome.android.communication.http.listener.DealHttpResponseDownloadListener;
import kap.com.smarthome.android.communication.http.listener.DealHttpResponseHandle;
import kap.com.smarthome.android.ui.utils.ImageUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/25 0025.
 */

public class CommonFileDownResponseCallback implements Callback{
    /**
     * the java layer exception, do not same to the logic error
     */
    protected final int NETWORK_ERROR = -1; // the network relative error
    protected final int IO_ERROR = -2; // the JSON relative error
    protected final String EMPTY_MSG = "";
    /**
     * 将其它线程的数据转发到UI线程
     */
    private static final int PROGRESS_MESSAGE = 0x01;
    private Handler mDeliveryHandler;
    private DealHttpResponseDownloadListener mListener;
    private String mFilePath;
    private int mProgress;

    public CommonFileDownResponseCallback(DealHttpResponseHandle handle) {
        this.mListener = (DealHttpResponseDownloadListener) handle.dealHttpResponseListener;
        this.mFilePath = handle.mSource;
        this.mDeliveryHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case PROGRESS_MESSAGE:
                        mListener.onProgress((int) msg.obj);
                        break;
                }
            }
        };
    }

    @Override
    public void onFailure(final Call call, final IOException ioexception) {
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onHttpFail(new MyOkHttpException(NETWORK_ERROR,ioexception));
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final  boolean bool =  handleResponse(response);
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                if (bool) {
                    mListener.onHttpSuccess(bool);
                } else {
                    //mListener.onFailure(new OkHttpException(IO_ERROR, EMPTY_MSG));
                    mListener.onHttpFail(new MyOkHttpException(IO_ERROR, EMPTY_MSG));
                }
            }
        });
    }

    /**
     * 此时还在子线程中，不则调用回调接口
     * @param response
     * @return
     */
   /* private File handleResponse(Response response) {
        if (response == null) {
            return null;
        }
        InputStream inputStream = null;
        File file = null;
        FileOutputStream fos = null;
        byte[] buffer = new byte[2048];
        int length = -1;
        int currentLength = 0;
        double sumLength = 0;
        try {
            file = new File(mFilePath);
            fos = new FileOutputStream(file);
            inputStream = response.body().byteStream();
            sumLength = (double) response.body().contentLength();
            while ((length = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, length);
                currentLength += length;
                mProgress = (int) (currentLength / sumLength * 100);
                mDeliveryHandler.obtainMessage(PROGRESS_MESSAGE, mProgress).sendToTarget();
            }
            fos.flush();
        } catch (Exception e) {
            file = null;
        } finally {
            try {
                fos.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }*/


    /**
     * 下载图片
     * @param response
     * @return
     */
    private boolean  handleResponse(Response response) {
        if (response == null) {
            return false;
        }
        try {
            byte[] bytes = response.body().bytes();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            ImageUtils.saveFile(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  true;
    }


}
