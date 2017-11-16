package kap.com.smarthome.android.presenter.control;

import java.util.List;

import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.IRKey;
import kap.com.smarthome.android.data.bean.RelayBox;
import kap.com.smarthome.android.data.bean.Room;
import kap.com.smarthome.android.data.bean.Scenes;
import kap.com.smarthome.android.data.bean.ScenesDevice;
import kap.com.smarthome.android.data.bean.ScenesTrigger;
import kap.com.smarthome.android.data.bean.User;
import kap.com.smarthome.android.data.control.DoDBApi;
import kap.com.smarthome.android.data.dao.DaoSession;
import kap.com.smarthome.android.data.dao.IRKeyDao;
import kap.com.smarthome.android.mapp.MyApplication;
import kap.com.smarthome.android.presenter.utils.UUIDUtils;

/**
 * Created by yushq on 2017/9/20 0020.
 */

public class DataBaseHandle {


    //-------------------------------------------------------------用户操作

    /**
     * 注册成功，生成一条用户数据
     */
    public static boolean insertUser(String login_name, String phone) {
        String  uuid = UUIDUtils.getUUID();
        User  user = new User();
        user.setGUID(uuid);
        user.setLOGIN_NAME(login_name);
        user.setPHONE(phone);
        long result = DoDBApi.getDoDBApi().insertUser(user);
        if(result != -1){
            return true;
        }
        return  false;
    }


    /**
     *  更新用户数据  根据用户的账号更新用户表p中的数据
     * @param account 查询用户数据的主键 账号
     * @param userId  用户的 UserId
     * @param sessionId  用户的会话ID
     * @param statues   用户的状态 1， 表示登录 ，0 表示断开连接，退出登录
     */
    public static Long updateUserByAccount(String account  , String userId , String sessionId , int statues) {
        long result = DoDBApi.getDoDBApi().updateUserByAccount(account ,  userId , sessionId , statues);
        return result;
    }

    /**
     *  更新用户数据  根据用户的账号更新用户表p中的数据
     * @param phoneNum 查询用户数据的主键 账号
     * @param userId  用户的 UserId
     * @param sessionId  用户的会话ID
     * @param statues   用户的状态 1， 表示登录 ，0 表示断开连接，退出登录
     */
    public static Long updateUserByPhone(String phoneNum  , String userId , String sessionId , int statues) {
        long result = DoDBApi.getDoDBApi().updateUserByPhoneNum(phoneNum ,  userId , sessionId , statues);
        return result;
    }


    /**
     *  更新用户数据  根据用户的账号更新用户表p中的数据
     * @param userId 查询用户数据的主键 账号
     * @param modifyInfo  用户需要更新的用户个人信息
     *                    昵称：
     *                    手机号
     *                    微信：
     *                    QQ:
     *                    邮箱等
     * @param modifyInfoType 用户需要更改的信息类型
     */
    public static Long updateUserByUserId(String  userId , String  modifyInfo , String modifyInfoType) {
        long result = DoDBApi.getDoDBApi().updateUserByUserId(userId , modifyInfo , modifyInfoType);
        return result;
    }


    /**
     * 获取用户表在的一个用户数据
     */
    public static User queryUser() {
        User  user = DoDBApi.getDoDBApi().getUser();
        return user;
    }

    /**
     * 删除一个用户
     */
    public static  void  deleteAllUser(){
        DoDBApi.getDoDBApi().deleteAllUser();
    }


    // -------------------------------------------------------房间
    /**
     * 插入新的房间
     * @return
     */
    /*public static  boolean  insertOneRoom(String uuid, String roomName, int roomOrder, int roomType){
        Room Room = new Room(null, uuid, roomName, roomOrder, roomType);
        long result = DoDBApi.getDoDBApi().insertOneRoom(Room);
        if(result != -1){
            return true;
        }
        return false;
    }*/

    /**
     * 添加一个新的房间
     * @param room
     * @return
     */
    public static  boolean insertOneRoom(Room room){
        long result = DoDBApi.getDoDBApi().insertRoom(room);
        if(result != -1){
            return true;
        }
        return false;
    }


    /**
     * 添加多个房间
     */
    public static  boolean insertRoomList(List<Room>  roomList){
        long result = DoDBApi.getDoDBApi().insertRoomList(roomList);
        if(result != -1){
            return true;
        }
        return false;
    }



    /**
     * 获取所有的房间数据
     * @return List<Room>
     */
    public static List<Room> queryAllRooms(){
        List<Room> rooms = DoDBApi.getDoDBApi().getAllRooms();
        return rooms;
    }

    /**
     * 删除一个房间
     * @param room
     * @return
     */
    public static boolean deleteRoom(Room room){
        return  DoDBApi.getDoDBApi().deleteRoom(room);
    }


    /**
     * 根据房间的GUID获取该房间关联的设备。
     * @param mRoomUUID
     */
    public static List<Devices> queryRoomDevices(String mRoomUUID) {
        return DoDBApi.getDoDBApi().getRoomDevices(mRoomUUID);
    }


    /**
     * 更新一个房间
     */
    public static void updateOneRoom(Room room) {
        DoDBApi.getDoDBApi().updateOneRoom(room);
    }


    // -------------------------------------------------------设备

    /**
     * 删除一个设备
     */
    public static boolean deleteDevice(Devices device) {
        return  DoDBApi.getDoDBApi().deleteDevice(device);
    }

    /**
     * 添加设备list到 设备表
     * @param selectDevices
     */
    public static boolean  insertDevices(List<Devices> selectDevices) {
        if(selectDevices == null && selectDevices.size() == 0){
            return  false;
        }
        long result = DoDBApi.getDoDBApi().insertDevices(selectDevices);
        if(result == -1){
            return false;
        }

        return  true;
    }

    /**
     * 添加单个设备（红外设备）
     */
    public static boolean  insertOneDevice(Devices addDevice) {
        long result = DoDBApi.getDoDBApi().insertOneDevice(addDevice);
        if(result == -1){
            return false;
        }
        return  true;
    }

    /**
     * 更新设备列表中的一个设备信息
     * @param mEditDevice
     */
    public static void updateOnDevice(Devices mEditDevice) {

    }


    public static List<Devices> queryAllDevices() {
        return  DoDBApi.getDoDBApi().queryAllDevices();
    }



    // -------------------------------------------------------中继盒子
    /**
     * 查找本地数据库中所有的中继盒子
     * @return
     */
    public static List<RelayBox> queryAllRelayBox() {
        return  DoDBApi.getDoDBApi().queryAllRelayBox();

    }


    /**
     * 根据中继盒子的id 查找本地数据库中中继盒子
     * @return
     */
    public static RelayBox queryOneRelayBox(String boxId) {
        return  DoDBApi.getDoDBApi().queryOneRelayBox(boxId);
    }

    /**
     * 添加中继盒子
     * @return
     */
    public static  boolean  insertRelayBoxList(List<RelayBox> relayBoxList){
        if(relayBoxList == null && relayBoxList.size() == 0){
            return  false;
        }

        long result = DoDBApi.getDoDBApi().insertRelayBoxList(relayBoxList);
        if(result == -1){
            return false;
        }
        return  true;
    }

    /**
     * 删除一个中继盒子
     * @param relayBox
     */
    public static boolean deleteRelayBox(RelayBox relayBox) {
        return DoDBApi.getDoDBApi().deleteRelay(relayBox);
    }

    /**
     *  刷新中继盒子表， 把盒子中的所有数据删除重新覆盖
     */
    public static boolean refreshRelayBoxTable(List<RelayBox> relayBoxes) {
        long result =  DoDBApi.getDoDBApi().refreshRelayBoxTable(relayBoxes);
        if(result == -1){
            return false;
        }
        return  true;
    }

    /**
     * 更新一个中继盒子
     * @param mEditRelayBox
     */
    public static void updateOneRelayBox(RelayBox mEditRelayBox) {
        DoDBApi.getDoDBApi().updateOneRelayBox(mEditRelayBox);
    }


  //--------------------------------------------------------------红外key值
    /**
     * 添加一条红外的key值
     * @param mIrKey
     */
    public static boolean insertIrKey(IRKey mIrKey) {
        if(mIrKey == null){
            return  false;
        }

        long result = DoDBApi.getDoDBApi().insertOneIrKey(mIrKey);

        if(result == -1){
            return false;
        }
        return  true;
    }



    /**
     * 查询某个红外遥控器所有的按键实体集合
     * @param ir_device_id 红外设备的ID号
     * @return
     */
    public static  List<IRKey> queryOneIrDeviceAllKeys(String ir_device_id) {
        return  DoDBApi.getDoDBApi().queryOneIrDeviceAllKeys(ir_device_id);
    }


    /**
     * 查询某个红外遥控器 某个索引的按键实体
     * @param ir_device_id
     * @param index
     * @return
     */
    public static IRKey queryOneIrKey(String ir_device_id, int index){
        return DoDBApi.getDoDBApi().queryOneIrKey(ir_device_id , index);
    }

    //--------------------------------------------------------------场景

    /**
     * 添加场景
     * @param mNewScenes
     * @param mNewScenesDeviceList
     * @param mNewScenesTriggerList
     */
    public static boolean insertOneScenes(Scenes mNewScenes, List<ScenesDevice> mNewScenesDeviceList, List<ScenesTrigger> mNewScenesTriggerList) {
        long result = DoDBApi.getDoDBApi().insertOneScenes(mNewScenes , mNewScenesDeviceList , mNewScenesTriggerList);

        if(result == -1){
            return false;
        }
        return  true;
    }


    /**
     *  查询所有的场景数据
     * @return
     */
    public static List<Scenes> queryAllScenes() {
        return DoDBApi.getDoDBApi().queryAllScenes();
    }


    /**
     * 更据场景ID查询出一个场景
     */
    public static Scenes queryOneScenes(String scenes_id) {
        return DoDBApi.getDoDBApi().queryOneScenes(scenes_id);
    }


    /**
     *  查询所有的场景控制设备的数据
     * @return
     */
    public static List<ScenesDevice> queryAllScenesDevices() {
        return DoDBApi.getDoDBApi().queryAllScenesDevices();
    }

    /**
     *  查询所有的场景触发条件的数据
     * @return
     */
    public static List<ScenesTrigger> queryAllScenesTrigger() {
        return DoDBApi.getDoDBApi().queryAllScenesTrigger();
    }

    /**
     *  查询一个的场景控制设备的数据
     * @return
     */
    public static List<ScenesDevice> queryOneScenesDevices(String mEditScenesId) {
        return DoDBApi.getDoDBApi().queryOneScenesDevices(mEditScenesId);
    }


    /**
     *  查询一个的场景触发条件的数据
     */
    public static List<ScenesTrigger> queryOneScenesTriggers(String mEditScenesId) {
        return DoDBApi.getDoDBApi().queryOneScenesTriggers(mEditScenesId);
    }


    /**
     * 更新一个场景的数据
     */

    public static void updateOneScenes(Scenes mNewScenes, List<ScenesDevice> mNewScenesDeviceList, List<ScenesTrigger> mNewScenesTriggerList) {
        DoDBApi.getDoDBApi().updateOneScenes(mNewScenes , mNewScenesDeviceList , mNewScenesTriggerList);
    }

    /**
     * 删除一个场景
     * @param scenes
     */
    public static void deleteOneScenes(Scenes scenes) {
        DoDBApi.getDoDBApi().deleteOneScenes(scenes);
    }

    /**
     * 删除所有数据
     */
    public static boolean deleteALlData() {
       return DoDBApi.getDoDBApi().deleteALlData();
    }

    /**
     * 添加所有数据
     * @param roomList
     * @param relayBoxList
     * @param devicesList
     * @param scenesList
     * @param scenesDevicesList
     * @param scenesTriggerList
     * @param irKeyList
     */
    public static boolean insertAllData(List<Room> roomList, List<RelayBox> relayBoxList, List<Devices> devicesList, List<Scenes> scenesList, List<ScenesDevice> scenesDevicesList, List<ScenesTrigger> scenesTriggerList, List<IRKey> irKeyList) {

        return  DoDBApi.getDoDBApi().insertAllData(roomList , relayBoxList , devicesList , scenesList , scenesDevicesList , scenesTriggerList , irKeyList);

    }
}
