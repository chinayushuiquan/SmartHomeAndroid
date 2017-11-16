package kap.com.smarthome.android.communication.http.exception;

/**
 * Created by Administrator on 2017/9/15 0015.
 */

public class MyOkHttpException extends  Exception {

    private static  final  long  serialVersionUID = 1L;

    /**
     * 详细的错误码code
     */
    private int eCode;

    /**
     *  详细的错误信息
     */
    private Object eMsg;

    public MyOkHttpException(int code , Object  emsg){
        this.eCode = code;
        this.eMsg = emsg;
    }

    public int getEcode() {
        return eCode;
    }

    public Object getEmsg() {
        return eMsg;
    }
}
