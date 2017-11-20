package kap.com.smarthome.android.communication.http.control;

import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.communication.bean.base.DATABean.DeviceData;
import kap.com.smarthome.android.communication.bean.base.DATABean.IrkeysData;
import kap.com.smarthome.android.communication.bean.base.DATABean.RelayBoxData;
import kap.com.smarthome.android.communication.bean.base.DATABean.ScenesData;
import kap.com.smarthome.android.communication.bean.base.DATABean.ScenesDevicesData;
import kap.com.smarthome.android.communication.bean.base.DATABean.ScenesTriggerData;
import kap.com.smarthome.android.communication.bean.base.HTTP.HTTPRequestBodyBase;
import kap.com.smarthome.android.communication.bean.base.DATABean.RoomData;
import kap.com.smarthome.android.communication.bean.base.JsonHeadBase;

import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPRequest.HTTPExitLoginRequestBody;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPRequest.HTTPLoginRequestBody;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPRequest.HTTPModifyPassWordRequestBody;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPRequest.HTTPModifyUserInfoRequestBody;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPRequest.HTTPRegisterRequestBody;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPRequest.HTTPUpdateHeadRequestBody;
import kap.com.smarthome.android.communication.bean.extend.HTTP.HTTPRequest.HTTPVerificodeRequestBody;
import kap.com.smarthome.android.communication.http.constants.HTTPMsgINSIP;
import kap.com.smarthome.android.communication.udp.constants.UDPContants;
import kap.com.smarthome.android.data.bean.IRKey;
import kap.com.smarthome.android.data.bean.Scenes;
import kap.com.smarthome.android.data.bean.ScenesDevice;
import kap.com.smarthome.android.data.bean.ScenesTrigger;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.BeanDataConvertUtils;
import kap.com.smarthome.android.presenter.utils.TimeUtils;

/**
 * Created by yushq on 2017/9/30 0030.
 */

public class CreateHTTPJson {

    /**
     * 发送HTTP Json请求的头部信息 HEAD
     * @return
     */
    public static JsonHeadBase buildHTTPJsonHead(){
        JsonHeadBase  headBean = new JsonHeadBase();
        headBean.setTIMESTAMP(TimeUtils.getSystemCurrentTime());
        headBean.setSERVICEID(UDPContants.UDP_BROADCAST_HEAD_SERVICE_ID);
        headBean.setVERSION("2.0");
        headBean.setDEVICEID("");
        headBean.setREPEATCOUNT(UDPContants.UDP_REPEAT_SEND_COUNT);

        headBean.setUSERID(AllVariable.CURRENT_USER_ID);

        headBean.updateSERIALNUM();
        return  headBean;
    }


    /**
     *构建获取验证码的body体
     */
    public static HTTPRequestBodyBase buildGetVerifiCodeBody(String phone , String local , String type){
        HTTPVerificodeRequestBody httpVerificodeBody = new HTTPVerificodeRequestBody();
        httpVerificodeBody.setINSTP(HTTPMsgINSIP.GET_VALIDCODE_REQ);
        httpVerificodeBody.setTELEPHONE(phone);
        httpVerificodeBody.setLOCATION(local);
        httpVerificodeBody.setACTIONTYPE(type);
        return httpVerificodeBody;
    }


    /**
     *构建手机号进行注册的的body体
     */
    public static HTTPRequestBodyBase buildSignUpUserAccountBody(String account , String phoneNum, String verification, String password ,String type){
        HTTPRegisterRequestBody httpSignUpUserAccountBody = new HTTPRegisterRequestBody();
        httpSignUpUserAccountBody.setINSTP(HTTPMsgINSIP.NEW_ACCOUNT_REQ);
        httpSignUpUserAccountBody.setTELEPHONE(phoneNum);
        httpSignUpUserAccountBody.setLOGINNAME(account);
        httpSignUpUserAccountBody.setPASSWORD(password);
        httpSignUpUserAccountBody.setVALIDCODE(verification);
        httpSignUpUserAccountBody.setEMAIL("");
        httpSignUpUserAccountBody.setQQ("");
        httpSignUpUserAccountBody.setWECHAT("");
        httpSignUpUserAccountBody.setREGTYPE(type);
        return httpSignUpUserAccountBody;
    }


    /**
     *构建手机号进行注册的的body体
     */
    public static HTTPRequestBodyBase buildLoginRemoteBody(String account , String phoneNum, String verification, String password ,String type){
        HTTPLoginRequestBody httpLoginRemoteBody = new HTTPLoginRequestBody();
        httpLoginRemoteBody.setINSTP(HTTPMsgINSIP.USER_LOGIN_REQ);
        httpLoginRemoteBody.setTELEPHONE(phoneNum);
        httpLoginRemoteBody.setLOGINNAME(account);
        httpLoginRemoteBody.setPASSWORD(password);
        httpLoginRemoteBody.setVALIDCODE(verification);
        httpLoginRemoteBody.setEMAIL("");
        httpLoginRemoteBody.setQQ("");
        httpLoginRemoteBody.setWECHAT("");
        httpLoginRemoteBody.setLOGINTYPE(type);
        return httpLoginRemoteBody;
    }


    /**
     *  构建修改用户个人信息的 body体
     */
    public static HTTPRequestBodyBase buildModifyUserInfoBody(String userId ,String actionType, String info){
        HTTPModifyUserInfoRequestBody httpModifyUserInfoRequestBody = new HTTPModifyUserInfoRequestBody();
        httpModifyUserInfoRequestBody.setINSTP(HTTPMsgINSIP.EDIT_USERINFO_REQ);
        httpModifyUserInfoRequestBody.setUSERID(userId);
        httpModifyUserInfoRequestBody.setACTIONTYPE(actionType);
        httpModifyUserInfoRequestBody.setQQ("");
        httpModifyUserInfoRequestBody.setWECHAT("");
        httpModifyUserInfoRequestBody.setEMAIL("");
        httpModifyUserInfoRequestBody.setNICKNAME("");
        httpModifyUserInfoRequestBody.setTELEPHONE("");
        httpModifyUserInfoRequestBody.setVALIDCODE("");
        if(actionType.equals("1")){
            httpModifyUserInfoRequestBody.setNICKNAME(info);
        }else if(actionType.equals("2")){
            httpModifyUserInfoRequestBody.setTELEPHONE(info);
        }else if(actionType.equals("3")){
            httpModifyUserInfoRequestBody.setWECHAT(info);
        }else if(actionType.equals("4")){
            httpModifyUserInfoRequestBody.setQQ(info);
        }else if(actionType.equals("5")){
            httpModifyUserInfoRequestBody.setEMAIL(info);
        }
        return httpModifyUserInfoRequestBody;
    }


    /**
     *构建修改用户登录密码的 body体
     */
    public static HTTPRequestBodyBase buildModifyPassWord(String userId , String mNewPasswordStr, String mOldPasswordStr) {
        HTTPModifyPassWordRequestBody httpModifyPassWordRequestBody = new HTTPModifyPassWordRequestBody();
        httpModifyPassWordRequestBody.setINSTP(HTTPMsgINSIP.CHANGE_PWD_REQ);
        httpModifyPassWordRequestBody.setPASSWORD(mNewPasswordStr);
        httpModifyPassWordRequestBody.setOLDPASSWORD(mOldPasswordStr);
        httpModifyPassWordRequestBody.setUSERID(userId);
        return  httpModifyPassWordRequestBody;
    }

    /**
     *构建退出登录 body体
     */
    public static HTTPRequestBodyBase buildExitServerLogin(String sessionId) {
        HTTPExitLoginRequestBody httpExitLoginRequestBody = new HTTPExitLoginRequestBody();
        httpExitLoginRequestBody.setINSTP(HTTPMsgINSIP.USER_LOGOUT_REQ);
        httpExitLoginRequestBody.setSESSIONID(sessionId);
        return  httpExitLoginRequestBody;
    }


    /**
     *构建用户头像 body体
     */
    public static HTTPRequestBodyBase buildUpdateHeadLogin(String userId , String base64_head) {
        HTTPUpdateHeadRequestBody httpUpdateHeadRequestBody = new HTTPUpdateHeadRequestBody();
        httpUpdateHeadRequestBody.setINSTP(HTTPMsgINSIP.EDIT_USER_HEADLOGO_REQ);
        httpUpdateHeadRequestBody.setHEADLOGO(base64_head);
        httpUpdateHeadRequestBody.setUSERID(userId);
        return  httpUpdateHeadRequestBody;
    }


    /**
     * 构建用户授权的请求 body
     * @param accredit_account
     * @param verify_code
     * @param req_type
     * @return
     */
    public static HTTPRequestBodyBase buildUserAccredit(String accredit_account, String verify_code, int req_type) {
        HTTPLoginRequestBody httpLoginRemoteBody = new HTTPLoginRequestBody();
        httpLoginRemoteBody.setINSTP(HTTPMsgINSIP.GRANT_NEW_REQ);
        httpLoginRemoteBody.setLOGINNAME(accredit_account);
        httpLoginRemoteBody.setVALIDCODE(verify_code);
        httpLoginRemoteBody.setLOGINTYPE(req_type +"");
        httpLoginRemoteBody.setUSERID(AllVariable.CURRENT_USER_ID);
        return  httpLoginRemoteBody;
    }

//-----------------------------------------房间----------------------------------------------------
    /**
     * 构建添加房间的数据
     * @param datas
     * @return
     */
    public static HTTPRequestBodyBase buildAddRooms(List<RoomData> datas) {
        HTTPRequestBodyBase<RoomData>  httpRequestBodyBase = new HTTPRequestBodyBase<>();
        httpRequestBodyBase.setINSTP(HTTPMsgINSIP.NEW_ROOM_REQ);
        httpRequestBodyBase.setDATA(datas);
        return  httpRequestBodyBase;
    }

    /**
     * 构建删除房间的数据
     * @param datas
     * @return
     */
    public static HTTPRequestBodyBase buildDeleteRooms(List<RoomData> datas) {
        HTTPRequestBodyBase<RoomData>  httpRequestBodyBase = new HTTPRequestBodyBase<>();
        httpRequestBodyBase.setINSTP(HTTPMsgINSIP.DELETE_ROOM_REQ);
        httpRequestBodyBase.setDATA(datas);
        return  httpRequestBodyBase;
    }

    /**
     * 构建修改房间的数据
     * @param datas
     * @return
     */
    public static HTTPRequestBodyBase buildUpdateRooms(List<RoomData> datas) {
        HTTPRequestBodyBase<RoomData>  httpRequestBodyBase = new HTTPRequestBodyBase<>();
        httpRequestBodyBase.setINSTP(HTTPMsgINSIP.UPDATE_ROOM_REQ);
        httpRequestBodyBase.setDATA(datas);
        return  httpRequestBodyBase;
    }


    /**
     * 构建拉取房间数据的body
     * @param roomDataList
     * @return
     */
    public static HTTPRequestBodyBase buildQueryRooms(List<RoomData> roomDataList) {
        HTTPRequestBodyBase<RoomData>  httpRequestBodyBase = new HTTPRequestBodyBase<>();
        httpRequestBodyBase.setINSTP(HTTPMsgINSIP.UPDATE_ROOM_REQ);
        httpRequestBodyBase.setDATA(roomDataList);
        return  httpRequestBodyBase;
    }


//-----------------------------------------中继盒子--------------------------------------------------
    /**
     * 添加中继盒子
     * @param relayBoxDatas
     * @return
     */
    public static HTTPRequestBodyBase buildAddRelayBox(List<RelayBoxData> relayBoxDatas) {
        HTTPRequestBodyBase<RelayBoxData>  httpRequestBodyBase = new HTTPRequestBodyBase<>();
        httpRequestBodyBase.setINSTP(HTTPMsgINSIP.NEW_RELAYBOX_ANDUSER_REQ);
        httpRequestBodyBase.setDATA(relayBoxDatas);
        return httpRequestBodyBase;
    }

    /**
     * 删除中继盒子
     * @param relayBoxDatas
     * @return
     */
    public static HTTPRequestBodyBase buildDeleteRelayBox(List<RelayBoxData> relayBoxDatas) {
        HTTPRequestBodyBase<RelayBoxData>  httpRequestBodyBase = new HTTPRequestBodyBase<>();
        httpRequestBodyBase.setINSTP(HTTPMsgINSIP.DELETE_RELAYBOX_ANDUSER_REQ);
        httpRequestBodyBase.setDATA(relayBoxDatas);
        return  httpRequestBodyBase;
    }


    /**
     * 更新中继盒子
     * @param relayBoxDatas
     * @return
     */
    public static HTTPRequestBodyBase buildUpdateRelayBox(List<RelayBoxData> relayBoxDatas) {
        HTTPRequestBodyBase<RelayBoxData>  httpRequestBodyBase = new HTTPRequestBodyBase<>();
        httpRequestBodyBase.setINSTP(HTTPMsgINSIP.UPDATE_RELAYBOX_ANDUSER_REQ);
        httpRequestBodyBase.setDATA(relayBoxDatas);
        return  httpRequestBodyBase;
    }

    /**
     * 查询中继盒子
     * @return
     */
    public static HTTPRequestBodyBase buildQueryRelayBoxs(List<RelayBoxData> relayBoxDatas) {
        HTTPRequestBodyBase<RelayBoxData>  httpRequestBodyBase = new HTTPRequestBodyBase<>();
        httpRequestBodyBase.setINSTP(HTTPMsgINSIP.FIND_RELAYBOX_ANDUSER_REQ);
        httpRequestBodyBase.setDATA(relayBoxDatas);
        return  httpRequestBodyBase;
    }

//--------------------------------------设备-------------------------------------------------------
    /**
     * 添加设备
     * @param deviceDatas
     * @return
     */
    public static HTTPRequestBodyBase buildAddDevices(List<DeviceData> deviceDatas) {
        HTTPRequestBodyBase<DeviceData>  httpRequestBodyBase = new HTTPRequestBodyBase<>();
        httpRequestBodyBase.setINSTP(HTTPMsgINSIP.NEW_DEVICE_REQ);
        httpRequestBodyBase.setDATA(deviceDatas);
        return httpRequestBodyBase;
    }

    /**
     * 删除设备
     */
    public static HTTPRequestBodyBase buildDeleteDevices(List<DeviceData> deviceDatas) {
        HTTPRequestBodyBase<DeviceData>  httpRequestBodyBase = new HTTPRequestBodyBase<>();
        httpRequestBodyBase.setINSTP(HTTPMsgINSIP.DELETE_DEVICE_REQ);
        httpRequestBodyBase.setDATA(deviceDatas);
        return httpRequestBodyBase;
    }


    /**
     * 更新设备
     */
    public static HTTPRequestBodyBase buildUpdateDevices(List<DeviceData> deviceDatas) {
        HTTPRequestBodyBase<DeviceData>  httpRequestBodyBase = new HTTPRequestBodyBase<>();
        httpRequestBodyBase.setINSTP(HTTPMsgINSIP.UPDATE_DEVICE_REQ);
        httpRequestBodyBase.setDATA(deviceDatas);
        return httpRequestBodyBase;
    }



    /**
     *  查询设备
     *  查询的body
     * "BODY":{"INSTP":"FINDDEVICEREQ","DATA":[{"ROOMID":"11","RELAYBOXID":"211"}]}
     */
    public static HTTPRequestBodyBase buildQueryDevices(List<DeviceData> deviceDatas) {
        HTTPRequestBodyBase<DeviceData>  httpRequestBodyBase = new HTTPRequestBodyBase<>();
        httpRequestBodyBase.setINSTP(HTTPMsgINSIP.FIND_DEVICE_REQ);
        httpRequestBodyBase.setDATA(deviceDatas);
        return  httpRequestBodyBase;
    }

//---------------------------------------------------------------------场景----------------------
    /**
     * 添加场景
     *"BODY":{"INSTP":"NEWSCENEREQ",
     * "DATA":[{"ID":"122","SCENENAME":"场景名称","DEVICENUMBER":"1","SCENEICON":"1","TRIGGERNUMBER":"1","TYPE":"1","TRIGGERSTATUS":"1",
     * "SCENEDEVICE":[{"DEVICEID":"20a2061907d2476b","SCENEDEVICEGUID":"20a2061907d2476b","DEVICEINFO":"9","DEVICETYPE":"2","DEVICEVALUE":"设备值","RELAYBOXID":"1001116016400001"}],
     * "SCENETRIGGER":[{"DEVICEID":"20a2061907d2476b","SCENETRIGGERGUID":"20a2061907d2476b","SCENETRIGGERTYPE":"2","TRIGGERINFO":"2","TRIGGERVALUE":"2","RELAYBOXID":"1001116016400001"}]}]}
     * @param mNewScenes
     * @param mNewScenesDeviceList
     * @param mNewScenesTriggerList
     */

    public static HTTPRequestBodyBase buildAddScenes(Scenes mNewScenes, List<ScenesDevice> mNewScenesDeviceList, List<ScenesTrigger> mNewScenesTriggerList) {

        List<ScenesDevicesData> scenesDevicesDataList = BeanDataConvertUtils.convertToScenesDevicesData(mNewScenesDeviceList);

        List<ScenesTriggerData> scenesTriggerDataList = BeanDataConvertUtils.convertToScenesTriggerData(mNewScenesTriggerList);

        List<ScenesData>  scenesDataList  = BeanDataConvertUtils.convertToScenesData(mNewScenes, scenesDevicesDataList ,scenesTriggerDataList);

        HTTPRequestBodyBase<ScenesData>  httpRequestBodyBase = new HTTPRequestBodyBase<>();
        httpRequestBodyBase.setINSTP("NEWSCENEREQ");
        httpRequestBodyBase.setDATA(scenesDataList);

        return  httpRequestBodyBase;
    }


    /**
     * 更新场景
     * @param mEditScenes
     * @param mEditScenesDeviceList
     * @param mEditScenesTriggerList
     * @return
     */
    public static HTTPRequestBodyBase buildUpdateScenes(Scenes mEditScenes, List<ScenesDevice> mEditScenesDeviceList, List<ScenesTrigger> mEditScenesTriggerList) {

        List<ScenesDevicesData> scenesDevicesDataList = BeanDataConvertUtils.convertToScenesDevicesData(mEditScenesDeviceList);
        List<ScenesTriggerData> scenesTriggerDataList = BeanDataConvertUtils.convertToScenesTriggerData(mEditScenesTriggerList);
        List<ScenesData>  scenesDataList  = BeanDataConvertUtils.convertToScenesData(mEditScenes, scenesDevicesDataList ,scenesTriggerDataList);

        HTTPRequestBodyBase<ScenesData>  httpRequestBodyBase = new HTTPRequestBodyBase<>();
        httpRequestBodyBase.setINSTP("UPDATESCENEREQ");
        httpRequestBodyBase.setDATA(scenesDataList);

        return  httpRequestBodyBase;

    }


    /**
     * 删除场景
     * @param scenes
     * @return HTTPRequestBodyBase
     *
     * 删除场景的时候只需要传入 场景ID
     */
    public static HTTPRequestBodyBase buildDeleteScenes(Scenes scenes) {
             List<ScenesData>  scenesDataList = BeanDataConvertUtils.convertToScenesData(scenes);
             HTTPRequestBodyBase<ScenesData> httpRequestBodyBase = new HTTPRequestBodyBase<>();
             httpRequestBodyBase.setINSTP("DELETESCENEREQ");
             httpRequestBodyBase.setDATA(scenesDataList);
             return httpRequestBodyBase;

    }


    /**
     * 查询场景
     * @return
     */
    public static HTTPRequestBodyBase buildqueryScenes() {
        List<ScenesData>  scenesDataList = new ArrayList<>();
        HTTPRequestBodyBase<ScenesData> httpRequestBodyBase = new HTTPRequestBodyBase<>();
        httpRequestBodyBase.setINSTP("DELETESCENEREQ");
        httpRequestBodyBase.setDATA(scenesDataList);
        return httpRequestBodyBase;

    }

    //------------------------------------------------------------------------------


    /**
     *
     * 红外码库数据
     * @param irKeyList
     * @return
     */
    public static HTTPRequestBodyBase buildQueryIrKeysData(List<IRKey> irKeyList) {

        List<IrkeysData> irKeysDataList = BeanDataConvertUtils.convertToIrKeysData(irKeyList);

        HTTPRequestBodyBase<IrkeysData> httpRequestBodyBase = new HTTPRequestBodyBase<>();
        httpRequestBodyBase.setINSTP("NEWREDCODEREQ");
        httpRequestBodyBase.setDATA(irKeysDataList);

        return  httpRequestBodyBase;
    }

    /**
     * 查询其他用户的所有数据
     * @return
     */
    public static HTTPRequestBodyBase buildQueryOtherUserAllData() {
        HTTPRequestBodyBase<String> requestBodyBase = new HTTPRequestBodyBase<>();
        requestBodyBase.setINSTP("USERGETDATAREQ");
        return requestBodyBase;

    }



}
