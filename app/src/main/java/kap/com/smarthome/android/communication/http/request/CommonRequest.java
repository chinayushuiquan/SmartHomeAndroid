package kap.com.smarthome.android.communication.http.request;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/5/25 0025.
 * build some need request
 *
 */

public class CommonRequest {

    /**
     * 表单的get方式的Request
     * ressemble the params to the url
     * @param url
     * @param params
     * @return
     */
    public static Request createGetRequest(String url, RequestParams params) {
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.mUrlParams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        return new Request.Builder().url(urlBuilder.substring(0, urlBuilder.length() - 1)).get().build();
    }

    /**
     * 传输表单的Post方式的Request
     * build a Post Request
     * @param url
     * @param params
     * @return
     */
    public  static Request  createPostRequest(String url , RequestParams params){
        FormBody.Builder  builder  = new FormBody.Builder();
        if(params != null){
            for (Map.Entry<String , String > entry :  params.mUrlParams.entrySet()) {
                  builder.add(entry.getKey() , entry.getValue());
            }
        }
        FormBody  formBody  = builder.build();
        return  new Request.Builder().url(url).post(formBody).build();
    }


    private  static final  MediaType JSON = MediaType.parse("application/json");

    /**
     * 傳入一個Json字符串進行post傳輸
     * @param url
     * @param jsonStr
     * @return
     */
    public static  Request  createJsonPostRequest(String url , String jsonStr){
        RequestBody requestBody = RequestBody.create(JSON , jsonStr);
        return  new Request.Builder().url(url).post(requestBody).build();
    }


    /**
     * 构建传输文件的Request
     * @param url
     * @param params
     * @return
     */
     private  static final  MediaType FIFE_TYPE = MediaType.parse("application/octet-stream");

     public  static  Request  createFilePostRequest(String url , RequestParams params){
         MultipartBody.Builder  multiBuilder  = new MultipartBody.Builder();
         multiBuilder.setType(MultipartBody.FORM);
        if(params != null){
            for (Map.Entry<String , Object >  entry :  params.mFileParams.entrySet()) {
                if(entry.getValue()  instanceof  File){
                    multiBuilder.addPart(MultipartBody.Part.createFormData(entry.getKey() ,
                        null,
                        RequestBody.create(FIFE_TYPE,(File)entry.getValue())));
                }else {
                    multiBuilder.addFormDataPart(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
        }
         return  new Request.Builder().url(url).post(multiBuilder.build()).build();
     }


    /**
     * 只有URL 的post 请求
     * @param url
     * @return
     */
    public  static  Request  createUrlPostRequest(String url ){
        return  new Request.Builder().url(url).build();
    }


}
