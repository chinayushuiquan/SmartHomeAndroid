package kap.com.smarthome.android.presenter.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kap.com.smarthome.android.presenter.utils.MyLogUtils;

/**
 * Created by Administrator on 2017/6/1 0001.
 */

public class JsonUtils {

    public final static String SEARCH_BOX  = "{\"HEAD\":{\n" +
            "      \"TIMESTAMP\":1496294692,\n" +
            "\t  \"VERSION\":\"V0.01\",\n" +
            "\t  \"SERVICEID\":\"12345678\",\n" +
            "\t  \"SERIALNUM\":0,\n" +
            "\t  \"DEVICEID\":\"0000000000000000\"\n" +
            "\t  },\n" +
            "\t  \"BODY\":{\n" +
            "\t  \"INSTP\":\"DEVICEFINDREQ\"\n" +
            "\t  }\n" +
            "}";


    public final static String SET_RELAY_BOX_MAGICTOUCHREQ  =
            "{\"HEAD\":" +
            "{\"TIMESTAMP\":1506477148," +
            "\"SERVICEID\":\"12345678\"," +
            "\"VERSION\":\"V0.01\"," +
            "\"DEVICEID\":\"1001116016400000\"," +
            "\"SERIALNUM\":15}," +
            "\"BODY\":\n" +
            "{\"INSTP\":\"REGISTERMAGICTOUCHREQ\"}}";


    public final static String SET_RELAY_BOX_STARTREQ =
                    "{\"HEAD\":" +
                    "{\"TIMESTAMP\":1506477148," +
                    "\"SERVICEID\":\"12345678\"," +
                    "\"VERSION\":\"V0.01\"," +
                    "\"DEVICEID\":\"1001116016400000\"," +
                    "\"SERIALNUM\":17}," +
                    "\"BODY\":{\"INSTP\":\"REGISTERSTARTREQ\"}}";


    public static final String CONTROL_DEVICE =
            "{\"HEAD\":" +
            "{\"TIMESTAMP\":1506477148," +
            "\"SERVICEID\":\"12345678\"," +
            "\"VERSION\":\"V0.01\"," +
            "\"DEVICEID\":\"1001116016400000\"," +
            "\"SERIALNUM\":17}," +
            "\"BODY\":" +
            "{\"INSTP\":\"DEVICECONTROLREQ\"," +
            "\"CONTROLTYPE\":0," +
            "\"DEVICEID\":\"0B100000\"," +
            "\"VALUE\":\"0000\"}}";

    public static final String CONTROL_DEVICE1 =
            "{\"HEAD\":" +
            "{\"TIMESTAMP\":947029597," +
            "\"VERSION\":\"V1.0\"," +
            "\"DEVICEID\":\"1001116016400000\"," +
            "\"SERVICEID\":\"1001201000123456\"," +
            "\"SERIALNUM\":126}," +
            "\"BODY\":" +
            "{\"INSTP\":\"DEVICECONTROLREQ\"," +
            "\"CONTROLTYPE\":0," +
            "\"DEVICEID\":\"0B100000\"," +
            "\"VALUE\":\"00ff\"}} ";


    /**
     * 将一个bean对象转换成Json格式的String类型
     * @param clazz
     * 注明：由于Gson的字典顺序的问题，UDP的Json数据由fastJson进行处理
     * @return
     */
    public static String fastJsonToString(Object clazz){
        String jsonOutput = JSON.toJSONString(clazz);
        Log.w("UDP", "UDP: jsonOutput = " + jsonOutput);
        return  jsonOutput;
    }

    /**
     * 将一个bean对象转换成Json格式的String类型
     * @param clazz
     * 注明：云端请求的Json数据默认有Gson处理，不必考虑顺序问题
     * @return
     */
    public static String gsonJsonToString(Object clazz){
        Gson gson = new Gson();
        String jsonOutput = gson.toJson(clazz);
        Log.w("HTTP", "HTTP: jsonOutput = " + jsonOutput);
        return  jsonOutput;
    }



    /**
     * 将一个字符串转化成一个bean对象
     * @param jsonStr
     * @param T
     * @param <T>
     * @return
     */
    public static <T> T  stringToObject(String jsonStr,Class<T> T){
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, T);
    }
}
