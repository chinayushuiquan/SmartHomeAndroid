package kap.com.smarthome.android.presenter.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/11/3 0003.
 */

public class DataLegitimacyCheckUtils {

    /**
     * 校验用户账号
     * @param input
     * 正则表达式 ：^[a-zA-Z\u4E00-\u9FA5]{1,20}  长度为1～20位的中文或者英文
     * @return
     */
    public static boolean checkUserAccount(String input){
        String regex = "^[a-zA-Z\\u4E00-\\u9FA5]{1,20}";
        return  regularExpression(regex , input);
    }


    /**
     * 校验账号信息
     * @param input
     * 正则表达式 ：^(?![0-9]+$)(?![a-zA-Z]+$)[a-zA-Z0-9]{6,18}   长度为6～18位的数字和字母组合
     * @return
     */
    public static boolean checkUserPsw(String input){
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[a-zA-Z0-9]{6,18}";
        return  regularExpression(regex , input);
    }


    /**
     * 6位验证码信息
     * @param input
     * 正则表达式 ：^[0-9]{6}  长度为6数字
     * @return
     */
    public static boolean checkVerifyCode(String input){
        String regex = "^[0-9]{6}";
        return  regularExpression(regex , input);
    }


    /**
     * @param regex
     * @param input
     * @return
     */
    private static  boolean  regularExpression(String regex , String input){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return  matcher.matches();
    }


}
