package kap.com.smarthome.android.presenter.control;

import java.util.List;

import kap.com.smarthome.android.communication.bean.base.DATABean.DeviceData;
import kap.com.smarthome.android.communication.bean.base.DATABean.RelayBoxData;
import kap.com.smarthome.android.communication.bean.base.DATABean.RoomData;
import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPResponseBaseMsg;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponse.AccreditUser.HTTPResponseAccreditUserMsg;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponse.Devices.HTTPResponseQueryDevicesMsg;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponse.Login.HTTPResponseLoginMsg;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponse.RelayBox.HTTPResponseQueryRelayBoxMsg;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponse.Room.HTTPResponseQueryRoomMsg;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPResponse.UserHead.HTTPResponseUpdateHeadMsg;
import kap.com.smarthome.android.communication.http.control.BuildHTTPJsonData;
import kap.com.smarthome.android.communication.http.control.CreateHTTPJson;
import kap.com.smarthome.android.communication.http.doapi.DoHTTPApi;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.data.bean.IRKey;
import kap.com.smarthome.android.data.bean.Scenes;
import kap.com.smarthome.android.data.bean.ScenesDevice;
import kap.com.smarthome.android.data.bean.ScenesTrigger;

/**
 * Created by yushq on 2017/9/29 0029.
 *
 * 当服务器返回的数据 body 只有INSTP 、 RESULT 两个键值的时候
 * 示例 :{"INSTP":"GETVALIDCODEACK","RESULT":"0"}
 * 使用DoHTTPApi.httpPostBaseClazz方法进行发送 传入HTTPResponseMsgBase 实例进行接收
 *
 *
 *
 */

public class ServerCommunicationHandle {
    /**
     * 用户数据 服务器操作的地址
     */
    private static  final  String  USER_URL = "http://113.105.134.90:10080/ams/api/v4";

    /**
     * 中继盒子服务器操作的地址
     */
    private static  final  String RELAY_BOX_URL = "http://113.105.134.90:10080/shs/app/api/v4";

    /**
     * 设备数据 服务器操作的地址
     */
    private static  final  String DEVICES_URL = "http://113.105.134.90:10080/shs/app/api/v4";


    /**
     * 场景数据 服务器操作的地址
     */
    private static  final  String  SCENES_URL = "http://113.105.134.90:10080/shs/app/api/v4";


    /**
     * 房间数据 服务器操作的地址
     */
    private static  final  String  ROOM_URL = "http://113.105.134.90:10080/shs/app/api/v4";


    /**
     * 透传数据 服务器操作的地址  设备控制 ，场景控制等都直接透传信息
     */
    private static  final  String  PENETRATE_URL = "http://113.105.134.90:10080/shs/app/api/v4";



//----------------------------------------------------用户信息-----------------------------
    /**
     *  进行用户注册
     * response body: "BODY":{"INSTP":"NEWACCOUNTACK","RESULT":"0"}
     */
    public static void registerUserAccount(String account , String phoneNum, String verification,
                                           String password, String type ,UIHttpCallBack uiHttpCallBack){
        String  registerAccount = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildSignUpUserAccountBody(account , phoneNum,  verification, password , type));
        DoHTTPApi.httpPostBaseClazz(USER_URL , registerAccount , uiHttpCallBack , HTTPResponseBaseMsg.class);
    }


    /**
     *
     * @param phoneNum 手机号
     * eg:86-1885555555
     * @param localCode 地区代号
     *  地区码 eg:
        zh – 中文短信
        en —英文短信
     * @param getType  验证码的获取类型
     * 获取验证码操作类型
       1-注册用户；
       2-忘记密码；
       3-授权新用户

       response body:"BODY":{"INSTP":"GETVALIDCODEACK","RESULT":"0"}
     */
    public static void getRegisterVerification(String phoneNum , String localCode , String getType , UIHttpCallBack uiHttpCallBack) {
        String  getVerification = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildGetVerifiCodeBody(phoneNum,localCode,getType));
        DoHTTPApi.httpPostBaseClazz(USER_URL , getVerification , uiHttpCallBack , HTTPResponseBaseMsg.class);
    }


    /**
     *
     操作结果类型
     0 - 成功；
     9000 - 数据非法；
     1002 - 指定帐户不存在；
     1003 - 指定帐户被锁定；
     9999 - 其它错误（未知异常）
     response body: BODY":{"INSTP":"USERLOGINACK","USERID":"4028b8815e2c95c0015e2c9663180000"," SESSIONID ":"4028b8815e2c95c0015e2c9663180000","RESULT":"0"}
     *
     */
    public static void loginRemoteServer(String account , String phoneNum, String verification, String password, String type ,UIHttpCallBack uiHttpCallBack) {
        String loginRemote = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildLoginRemoteBody(account , phoneNum,  verification, password , type));
        DoHTTPApi.httpPostLoginClazz(USER_URL , loginRemote, uiHttpCallBack ,HTTPResponseLoginMsg.class);
    }


    /**
     * 退出登录
     * @param sessionId APP 同服务器的会话ID
     * @param uiHttpCallBack
     * "BODY":{"INSTP":"USERLOGOUTACK","RESULT":"0"}
     */
    public static void exitServerLogin(String sessionId , UIHttpCallBack uiHttpCallBack){
        String loginRemote = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildExitServerLogin(sessionId));
        DoHTTPApi.httpPostBaseClazz(USER_URL , loginRemote, uiHttpCallBack , HTTPResponseBaseMsg.class);
    }

    /**
     * 修改用户个人信息
     * @param actionType  修改的信息类型 由服务器端定义
                            1-昵称
                            2-电话号
                            3-微信
                            4-QQ
                            5-E-MAIL
     *
     * @param info         需要修改的信息内容
     *"BODY":{"INSTP":" EDITUSERINFOACK ","RESULT":"0"}
     */
    public static void modifyUserInfo(String userId , String actionType, String info ,UIHttpCallBack uiHttpCallBack){
        String modifyReq = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildModifyUserInfoBody(userId,actionType,info));
        DoHTTPApi.httpPostBaseClazz(USER_URL , modifyReq, uiHttpCallBack , HTTPResponseBaseMsg.class);
    }


    /**
     * 修改用户密码，
     * @param mNewPasswordStr 新密码
     * @param mOldPasswordStr 旧密码
     * @param uiHttpCallBack  回调
     *
     *
     * "BODY":{"INSTP":"CHANGEPWDACK","RESULT":"0"}
     */
    public static void modifyPassWord(String userId , String mNewPasswordStr , String mOldPasswordStr, UIHttpCallBack uiHttpCallBack) {
        String modifyPassWord = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildModifyPassWord(userId , mNewPasswordStr, mOldPasswordStr));
        DoHTTPApi.httpPostBaseClazz(USER_URL , modifyPassWord, uiHttpCallBack , HTTPResponseBaseMsg.class);
    }


    /**
     *
     * @param userId 用户ID
     * @param base64_head  用户头像
     * @param uiHttpCallBack 回调
     *
     *
     * BODY":{"INSTP":"EDITUSERHEADLOGOACK","HEADLOGOURL":"http://10.20.110.31:8080/ams/uploads/headlogo/aaa.jpg","RESULT":"0"}
     */
    public static void updateUserHead(String userId , String base64_head , UIHttpCallBack uiHttpCallBack) {
        String updateHead = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildUpdateHeadLogin(userId , base64_head));
        DoHTTPApi.httpUpdateHeadClazz(USER_URL , updateHead, uiHttpCallBack , HTTPResponseUpdateHeadMsg.class);
    }

    /**
     * 获取用户头像
     *
     * 获取头像的URL 从外面传入
     */
    public static void getUserHead(String url , UIHttpCallBack uiHttpCallBack) {
        DoHTTPApi.httpGetUserHead(url , uiHttpCallBack);
    }


    /**
     * 用户授权
     * @param accredit_account
     * @param verify_code
     * @param req_type
     * @param uiHttpCallBack
     */
    public static void reqOtherUserAccredit(String accredit_account, String verify_code, int  req_type , UIHttpCallBack uiHttpCallBack) {
        String user_accredit = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildUserAccredit(accredit_account , verify_code , req_type ));
        DoHTTPApi.httpReqOtherUserAccredit(USER_URL , user_accredit, uiHttpCallBack , HTTPResponseAccreditUserMsg.class);
    }


    /**
     * 用户授权获取其他用户的所有数据
     */

    public static void  queryOtherUserAllData(UIHttpCallBack uiHttpCallBack){
        String query_other_data = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildQueryOtherUserAllData());
        DoHTTPApi.httpQueryOtherUserData(ROOM_URL , query_other_data , uiHttpCallBack);

    }


    //--------------------------------------------------------房间信息----------------------------

    /**
     * 添加房间
     *
     * 返回参数   "BODY":{"INSTP":" NEWROOMRSP ","RESULT":"0"}
     *
     */
    public static void addRoom(List<RoomData> rooms , UIHttpCallBack uiHttpCallBack){
        String addRooms = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildAddRooms(rooms));
        DoHTTPApi.httpPostBaseClazz(ROOM_URL, addRooms , uiHttpCallBack , HTTPResponseBaseMsg.class);
    }


    /**
     * 删除房间
     * @param rooms
     * @param uiHttpCallBack
     *
     *"BODY":{"INSTP":" DELETEROOMRSP ","RESULT":"0"}}
     */
    public static void deleteRooms(List<RoomData> rooms , UIHttpCallBack uiHttpCallBack) {
        String deleteRooms = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildDeleteRooms(rooms));
        DoHTTPApi.httpPostBaseClazz(ROOM_URL , deleteRooms , uiHttpCallBack , HTTPResponseBaseMsg.class);
    }


    /**
     * 更新房间
     *
     * 返回参数   "BODY":{"INSTP":" UPDATERSP  ","RESULT":"0"}
     *
     */
    public static void updateOneRoom(List<RoomData> rooms , UIHttpCallBack uiHttpCallBack){
        String  updateRooms = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildUpdateRooms(rooms));
        DoHTTPApi.httpPostBaseClazz(ROOM_URL , updateRooms , uiHttpCallBack , HTTPResponseBaseMsg.class);
    }


    /**
     * 查询房间
     */
    public static void queryRooms(List<RoomData> roomDataList , UIHttpCallBack uiHttpCallBack) {
        String  queryRoomJson= BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildQueryRooms(roomDataList));
        DoHTTPApi.httpPostQueryRooms(ROOM_URL , queryRoomJson , uiHttpCallBack , HTTPResponseQueryRoomMsg.class);
    }


    //----------------------------------------------------------------------中继盒子

    /**
     * 添加中继盒子
     *
     *"BODY":{"INSTP":"NEWRELAYBOXANDUSERRSP ","RESULT":"0"}}
     *
     */
    public static void addRelayBox(List<RelayBoxData> relayBoxDatas, UIHttpCallBack uiHttpCallBack) {
        String  addRelayBoxStr = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildAddRelayBox(relayBoxDatas));
        DoHTTPApi.httpPostBaseClazz(RELAY_BOX_URL , addRelayBoxStr , uiHttpCallBack , HTTPResponseBaseMsg.class);
    }

    /**
     *  删除中继盒子
     *
     *  返回参数 BODY":{"INSTP":" DELETERELAYBOXANDUSERRSP ","RESULT":"0"}
     */
    public static void deleteRelayBox(List<RelayBoxData> relayBoxDatas, UIHttpCallBack uiHttpCallBack) {
        String  deleteRelayBoxStr = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildDeleteRelayBox(relayBoxDatas));
        DoHTTPApi.httpPostBaseClazz(RELAY_BOX_URL, deleteRelayBoxStr , uiHttpCallBack , HTTPResponseBaseMsg.class);
    }


    /**
     *  更新中继盒子
     *
     *  返回参数 BODY":{"INSTP":"NEWRELAYBOXANDUSERRSP ","RESULT":"0"}
     *
     */
    public static void  updateRelayBox(List<RelayBoxData> relayBoxDatas, UIHttpCallBack uiHttpCallBack) {
        String  updateRelayBoxStr = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildUpdateRelayBox(relayBoxDatas));
        DoHTTPApi.httpPostBaseClazz(RELAY_BOX_URL , updateRelayBoxStr , uiHttpCallBack , HTTPResponseBaseMsg.class);
    }


    /**
     * 查询中继盒子
     *
     * BODY":{"INSTP":"FINDRELAYBOXANDUSERRSP","RESULT":"0", “DATA”:[“ID”:”1111”,其他参数省略] }
     */
    public static void  queryRelayBoxs(List<RelayBoxData> relayBoxDatas , UIHttpCallBack uiHttpCallBack) {
        String  updateRooms = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildQueryRelayBoxs(relayBoxDatas));
        DoHTTPApi.httpQueryRelayBoxs(RELAY_BOX_URL , updateRooms , uiHttpCallBack , HTTPResponseQueryRelayBoxMsg.class);
    }

//-------------------------------------------------------------------------------有关设备的服务器存储

    /**
     * 添加设备
     *
     * "BODY":{"INSTP":"NEWDEVICERSP","RESULT":"0"}}
     */
    public static void addDevices(List<DeviceData> devicesDatas, UIHttpCallBack uiHttpCallBack) {
        String  addDevicesStr = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildAddDevices(devicesDatas));
        DoHTTPApi.httpPostBaseClazz(DEVICES_URL , addDevicesStr , uiHttpCallBack , HTTPResponseBaseMsg.class);
    }


    /**
     * 删除设备
     *
     * "BODY":{"INSTP":" DELETEDEVICERSP ","RESULT":"0"}}
     */
    public static void deleteDevices(List<DeviceData> devicesDatas, UIHttpCallBack uiHttpCallBack) {
        String  deleteDevicesStr = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildDeleteDevices(devicesDatas));
        DoHTTPApi.httpPostBaseClazz(DEVICES_URL, deleteDevicesStr , uiHttpCallBack , HTTPResponseBaseMsg.class);
    }


    /**
     * 更新设备
     *
     * "BODY":{"INSTP":" UPDATEDEVICERSP ","RESULT":"0"}}
     */
    public static void updateDevices(List<DeviceData> devicesDatas, UIHttpCallBack uiHttpCallBack) {
        String  addDevicesStr = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildUpdateDevices(devicesDatas));
        DoHTTPApi.httpPostBaseClazz(DEVICES_URL, addDevicesStr , uiHttpCallBack , HTTPResponseBaseMsg.class);
    }


    /**
     * 查询设备
     *
     * "BODY":{"INSTP":"FINDDEVICERSP","RESULT":"0", “DATA”:[“ID”:”1111”,其他参数省略] }
     */
    public static void queryDevices(List<DeviceData> devicesDatas, UIHttpCallBack uiHttpCallBack) {
        String  queryDevicesStr = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildQueryDevices(devicesDatas));
        DoHTTPApi.httpQueryDevices(DEVICES_URL , queryDevicesStr , uiHttpCallBack , HTTPResponseQueryDevicesMsg.class);
    }
//----------------------------------------------------------------------红外数据--------------------


    /**
     *
     *
     *
     * "BODY":{"INSTP":"FINDDEVICERSP","RESULT":"0", “DATA”:[“ID”:”1111”,其他参数省略] }
     */
    public static void addIrLearnKeysData(List<IRKey> irkeyList , UIHttpCallBack uiHttpCallBack) {
        String  addIrKeysDataStr = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildQueryIrKeysData(irkeyList));
        DoHTTPApi.httpQueryDevices(DEVICES_URL , addIrKeysDataStr , uiHttpCallBack , HTTPResponseQueryDevicesMsg.class);
    }





//-----------------------------------------------------------------------场景数据--------------------

    /**
     * 添加场景
     * @param mNewScenes
     * @param mNewScenesDeviceList
     * @param mNewScenesTriggerList
     *
     * "BODY": {"INSTP": "NEWSCENERSP","RESULT": "0"}
     *
     */
    public static void addScenes(Scenes mNewScenes, List<ScenesDevice> mNewScenesDeviceList, List<ScenesTrigger> mNewScenesTriggerList , UIHttpCallBack uiHttpCallBack) {
        String  addScenesStr = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildAddScenes(mNewScenes ,mNewScenesDeviceList ,mNewScenesTriggerList));
        DoHTTPApi.httpPostBaseClazz(SCENES_URL , addScenesStr , uiHttpCallBack , HTTPResponseBaseMsg.class);
    }


    /**
     * 删除场景
     * @param scenes
     * @param uiHttpCallBack
     */
    public static void deleteScenes(Scenes scenes , UIHttpCallBack uiHttpCallBack) {
        String  addScenesStr = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildDeleteScenes(scenes));
        DoHTTPApi.httpPostBaseClazz(SCENES_URL , addScenesStr , uiHttpCallBack , HTTPResponseBaseMsg.class);
    }

    /**
     * 更新场景
     * @param mEditScenes
     * @param mEditScenesDeviceList
     * @param mEditScenesTriggerList
     * @param uiHttpCallBack
     */
    public static void updateScenes(Scenes mEditScenes, List<ScenesDevice> mEditScenesDeviceList, List<ScenesTrigger> mEditScenesTriggerList, UIHttpCallBack uiHttpCallBack) {
        String  addScenesStr = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildUpdateScenes(mEditScenes ,mEditScenesDeviceList ,mEditScenesTriggerList));
        DoHTTPApi.httpPostBaseClazz(SCENES_URL , addScenesStr , uiHttpCallBack , HTTPResponseBaseMsg.class);
    }


    /**
     * 查询场景
     */
    public static void queryScenes(UIHttpCallBack uiHttpCallBack) {
        String  addScenesStr = BuildHTTPJsonData.buildData(CreateHTTPJson.buildHTTPJsonHead(),
                CreateHTTPJson.buildqueryScenes());
        DoHTTPApi.httpPostBaseClazz(SCENES_URL , addScenesStr , uiHttpCallBack , HTTPResponseBaseMsg.class);
    }




//----------------------------------------------------透传消息--------------------------------------
    /**
     * 透传控制设备
     * @param control_json
     * @param uiHttpCallBack
     */
    public static void controlDevice(String control_json ,UIHttpCallBack uiHttpCallBack) {
        DoHTTPApi.httpControlDevices(PENETRATE_URL, control_json , uiHttpCallBack , HTTPResponseQueryDevicesMsg.class);

    }


    /**
     * 透传控制场景
     * @param control_json
     * @param uiHttpCallBack
     */
    public static void controlScenes(String control_json ,UIHttpCallBack uiHttpCallBack) {
        DoHTTPApi.httpControlSenes(PENETRATE_URL, control_json , uiHttpCallBack , HTTPResponseQueryDevicesMsg.class);

    }



}
