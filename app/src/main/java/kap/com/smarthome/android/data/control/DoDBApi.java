package kap.com.smarthome.android.data.control;


import android.util.Log;

import org.greenrobot.greendao.query.Query;

import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import kap.com.smarthome.android.communication.bean.base.DATABean.RoomData;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.IRKey;
import kap.com.smarthome.android.data.bean.RelayBox;
import kap.com.smarthome.android.data.bean.Room;
import kap.com.smarthome.android.data.bean.Scenes;
import kap.com.smarthome.android.data.bean.ScenesDevice;
import kap.com.smarthome.android.data.bean.ScenesTrigger;
import kap.com.smarthome.android.data.bean.User;
import kap.com.smarthome.android.data.dao.DaoSession;
import kap.com.smarthome.android.data.dao.DevicesDao;
import kap.com.smarthome.android.data.dao.IRKeyDao;
import kap.com.smarthome.android.data.dao.RelayBoxDao;
import kap.com.smarthome.android.data.dao.RoomDao;
import kap.com.smarthome.android.data.dao.ScenesDao;
import kap.com.smarthome.android.data.dao.ScenesDeviceDao;
import kap.com.smarthome.android.data.dao.ScenesTriggerDao;
import kap.com.smarthome.android.data.dao.UserDao;
import kap.com.smarthome.android.mapp.MyApplication;
import kap.com.smarthome.android.presenter.utils.UUIDUtils;

/**
 * Created by yushq on 2017/9/14 0014.
 * 这是一个本地数据库和表的直接操作类，采用单例模式，保证全局一个实例。
 */

public class DoDBApi {

    private DoDBApi(){}

    private static DoDBApi doDBApi;

    public static synchronized DoDBApi getDoDBApi(){
        if(doDBApi == null){
            doDBApi = new DoDBApi();
        }
        return doDBApi;
    }


    //-------------------------------------------------------------用户操作s

    /**
     * 添加一个用户到用户表中
     * @param user
     * @return
     */
    public long insertUser(User user) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        UserDao  userDao  = daoSession.getUserDao();
        return userDao.insert(user);
    }

    /**
     * 更新一个用户表中的一条数据 根据用户账号
     * (原理上用户表只存在一条用户数据 ，此处的循环避免调试错误， 后期需要需改)
     * @param
     * @return
     */
    public long updateUserByAccount(String account, String userId, String sessionId , int loginStatus) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        UserDao  userDao  = daoSession.getUserDao();
        long result = -1;

        User user = userDao.queryBuilder().where(UserDao.Properties.LOGIN_NAME.eq(account)).unique();

        if(user == null){
            user = new User();
            user.setGUID(UUIDUtils.getUUID());
            user.setLOGIN_NAME(account);
        }
        user.setUSER_ID(userId);
        user.setSESSION_ID(sessionId);
        user.setStatus(loginStatus);
        result = userDao.insertOrReplace(user);
        return result;
    }


    /**
     * 更新一个用户表中的一条数据 根据手机号码
     * (原理上用户表只存在一条用户数据 ，此处的循环避免调试错误， 后期需要需改)
     * @param
     * @return
     */
    public long updateUserByPhoneNum(String phoneNum, String userId, String sessionId , int loginStatus) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        UserDao  userDao  = daoSession.getUserDao();
        long result = -1  ;
        User user = userDao.queryBuilder().where(UserDao.Properties.PHONE.eq(phoneNum)).unique();
        if(user == null){
            user = new User();
            user.setGUID(UUIDUtils.getUUID());
            user.setPHONE(phoneNum);
        }
            user.setUSER_ID(userId);
            user.setSESSION_ID(sessionId);
            user.setStatus(loginStatus);
            result = userDao.insertOrReplace(user);
        return result;
    }


    /**
     *  更新一个用户表中的一条数据 根据用户的id
     * @param
     * @return
     */
    public long updateUserByUserId(String userId, String modifyInfo , String modifyInfoType) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        UserDao  userDao  = daoSession.getUserDao();
        long result = -1  ;
        User user = userDao.queryBuilder().where(UserDao.Properties.USER_ID.eq(userId)).unique();
            if(modifyInfoType.equals("1")){
                user.setNICK_NAME(modifyInfo);
            }else if(modifyInfoType.equals("2")){
                user.setPHONE(modifyInfo);
            }else if(modifyInfoType.equals("3")){
                user.setWECHAT(modifyInfo);
            }else if(modifyInfoType.equals("4")){
                user.setQQ(modifyInfo);
            }else if(modifyInfoType.equals("5")){
                user.setEMAIL(modifyInfo);
            }else if(modifyInfoType.equals("6")){//远程头像地址
                user.setUSER_HEAD(modifyInfo);
            }
            result = userDao.insertOrReplace(user);
        return result;
    }

    /**
     * 得到用户表的User数据
     * 如果表中有多个用户数据，只取第一条（原则上需要保证表中只有一条用户数据）
     * @return
     */
    public User getUser() {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        UserDao userDao = daoSession.getUserDao();
        List<User>  users = userDao.queryBuilder().list();
        if(users.size() >= 1){
            return  users.get(0);
        }
        return null;
    }

    /**
     * 删除用户表
     */
    public void deleteAllUser(){
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        UserDao userDao = daoSession.getUserDao();
        userDao.deleteAll();
    }


// -------------------------------------------------------房间

    /**
     * 插入单个房间
     * @param room
     * @return Long
     */
    public Long insertRoom(Room room){
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        RoomDao roomDao = daoSession.getRoomDao();
        return roomDao.insert(room);
    }

    /**
     * 插入多个房间
     * @param rooms
     * @return Long
     */
    public Long insertRoomList(List<Room> rooms){
        long result = -1L;
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        RoomDao roomDao = daoSession.getRoomDao();
        for (int i = 0 ; i < rooms.size() ; i++){
           result =  roomDao.insert(rooms.get(i));
        }
        return  result;
    }


    /**
     * 更新一个房间
     */
    public void updateOneRoom(Room room) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        RoomDao roomDao = daoSession.getRoomDao();
        roomDao.update(room);
    }


    /**
     * 查询所有的房间列表
     */
    public List<Room> getAllRooms(){
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        RoomDao roomDao = daoSession.getRoomDao();
        List<Room>  mRoomList =  roomDao.queryBuilder().list();
        return  mRoomList;
    }


    /**
     * 删除一个房间
     */
    public boolean deleteRoom(Room room){
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        RoomDao roomDao = daoSession.getRoomDao();
        roomDao.delete(room);
        return true;
    }


    // -------------------------------------------------------设备

    /**
     * 根据房间的GUID获取该房间关联的设备。
     * @param mRoomUUID
     */
    public  List<Devices> getRoomDevices(String mRoomUUID) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        DevicesDao deviceDao = daoSession.getDevicesDao();
        List<Devices>  devices = deviceDao.queryBuilder().where(DevicesDao.Properties.ROOM_GUID.eq(mRoomUUID)).list();
        return devices;
    }


    /**
     * 删除一个设备
     * @param device
     * @return
     */
    public boolean deleteDevice(Devices device) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        DevicesDao deviceDao = daoSession.getDevicesDao();
        deviceDao.delete(device);
        return true;
    }


    /**
     * 添加一个设备list到设备表中
     * @param selectDevices
     * @return
     */
    public long insertDevices(List<Devices> selectDevices) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        DevicesDao  devicesDao  = daoSession.getDevicesDao();
        long result = -1L;
        for (int i = 0 ; i < selectDevices.size() ; i++){
            String  uuid = UUIDUtils.getUUID();
            Devices  device = selectDevices.get(i);
            device.setGUID(uuid);
            Log.e("UDP", "newDevice  = " + device.toString());
            result = devicesDao.insert(device);
        }
        return result;
    }


    public long insertOneDevice(Devices addDevice) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        DevicesDao  devicesDao  = daoSession.getDevicesDao();
        long result = -1L;
        String  uuid = UUIDUtils.getUUID();
        addDevice.setGUID(uuid);
        result = devicesDao.insert(addDevice);
        return result;
    }


    /**
     * 查询所有的设备
     * @return
     */
    public List<Devices> queryAllDevices() {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        DevicesDao  devicesDao  = daoSession.getDevicesDao();
        List<Devices> allDevices = devicesDao.queryBuilder().list();
        return allDevices;
    }



    // -------------------------------------------------------中继hezi

    /**
     *
     * @return
     */
    public List<RelayBox> queryAllRelayBox() {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        RelayBoxDao relayBoxDao  = daoSession.getRelayBoxDao();
        List<RelayBox>  relayBoxes = relayBoxDao.queryBuilder().list();
        return   relayBoxes;
    }


    public RelayBox  queryOneRelayBox(String boxId) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        RelayBoxDao relayBoxDao  = daoSession.getRelayBoxDao();
        RelayBox relayBox = relayBoxDao.queryBuilder().where(RelayBoxDao.Properties.BOX_ID.eq(boxId)).unique();
        return relayBox;
    }



    /**
     * 插入多个中继盒子到数据库中
     * 前面已经判断是否为空，不再需要判断出入的list是否为空或者数值为0
     * UUID （GUID）在保存到数据库中时才保存
     * @param relayBoxList
     */
    public long insertRelayBoxList(List<RelayBox> relayBoxList) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        RelayBoxDao relayBoxDao  = daoSession.getRelayBoxDao();
        long result = -1L;
        for (int i = 0 ; i < relayBoxList.size() ; i++){
            RelayBox  relayBox = relayBoxList.get(i);
            result = relayBoxDao.insert(relayBox);
        }
        return result;
    }

    /**
     * 删除一个中继盒子
     * @param relayBox
     * @return
     */
    public boolean deleteRelay(RelayBox relayBox) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        RelayBoxDao relayBoxDao  = daoSession.getRelayBoxDao();
        relayBoxDao.delete(relayBox);
        return true;
    }


    /**
     * 刷新 中继盒子数据表 删除原来的数据，插入新的数据
     * @param relayBoxList
     */
    public long refreshRelayBoxTable(List<RelayBox> relayBoxList) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        RelayBoxDao relayBoxDao  = daoSession.getRelayBoxDao();
        relayBoxDao.deleteAll();
        long result = -1L;
        for (int i = 0 ; i < relayBoxList.size() ; i++){
            RelayBox  relayBox = relayBoxList.get(i);
            result = relayBoxDao.insert(relayBox);
        }
        return result;
    }

    /**
     * 更新一个中继盒子
     * @param mEditRelayBox
     */
    public void updateOneRelayBox(RelayBox mEditRelayBox) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        RelayBoxDao relayBoxDao  = daoSession.getRelayBoxDao();
        relayBoxDao.update(mEditRelayBox);
    }


 //------------------------------------------------------------红外


    /**
     * 添加一条红外数据
     * @param mIrKey
     * @return
     */
    public long insertOneIrKey(IRKey mIrKey) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        IRKeyDao irKeyDao  = daoSession.getIRKeyDao();
        long result = -1L;
            result = irKeyDao.insert(mIrKey);
        return result;
    }


    /**
     * 查询某个红外遥控器所有的按键集合
     * @param ir_device_id 红外设备的ID号
     * @return
     */
    public List<IRKey> queryOneIrDeviceAllKeys(String ir_device_id) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        IRKeyDao irKeyDao  = daoSession.getIRKeyDao();
        List<IRKey>  irKeyList =  irKeyDao.queryBuilder().where(IRKeyDao.Properties.DEVICE_ID.eq(ir_device_id)).list();
        return  irKeyList;
    }


    /**
     * 查询某个红外遥控器 某个位置的按键值
     * @param ir_device_id 遥控设备的id
     * @param index  按键在遥控器上的索引值
     * @return
     */
    public IRKey queryOneIrKey(String ir_device_id, int index) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        IRKeyDao irKeyDao  = daoSession.getIRKeyDao();

        List<IRKey>  irKeyList =  irKeyDao.queryBuilder().where(IRKeyDao.Properties.DEVICE_ID.eq(ir_device_id)).list();

        for(int i = 0 ; i < irKeyList.size() ; i++){
            if(index == irKeyList.get(i).getINDEX()){
                return  irKeyList.get(i);
            }
        }

        return  null;
    }

    //----------------------------------------------场景-------------------------------------------

    /**
     * 保存一个场景数据
     * @param mNewScenes
     * @param mNewScenesDeviceList 先添加场景的控制设备 、 控制条件
     * @param mNewScenesTriggerList
     */
    public long insertOneScenes(Scenes mNewScenes, List<ScenesDevice> mNewScenesDeviceList, List<ScenesTrigger> mNewScenesTriggerList) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        long result = -1L;

        ScenesDeviceDao scenesDeviceDao = daoSession.getScenesDeviceDao();
        scenesDeviceDao.insertInTx(mNewScenesDeviceList);

        ScenesTriggerDao scenesTriggerDao = daoSession.getScenesTriggerDao();
        scenesTriggerDao.insertInTx(mNewScenesTriggerList);

        ScenesDao scenesDao = daoSession.getScenesDao();
        result = scenesDao.insert(mNewScenes);

        return result;
    }


    /**
     * 查询所有场景数据
     * @return
     */
    public List<Scenes> queryAllScenes() {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        ScenesDao scenesDao = daoSession.getScenesDao();
        List<Scenes>  scenesList = scenesDao.queryBuilder().list();
        return   scenesList;
    }

    public Scenes queryOneScenes(String scenes_id) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        ScenesDao scenesDao = daoSession.getScenesDao();
        Scenes scenes = scenesDao.queryBuilder().where(ScenesDao.Properties.SCENE_ID.eq(scenes_id)).unique();
        return scenes;
    }



    /**
     * 查询所有场景的控制设备数据
     * @return
     */
    public List<ScenesDevice> queryAllScenesDevices() {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        ScenesDeviceDao scenesDeviceDao = daoSession.getScenesDeviceDao();
        List<ScenesDevice>  scenesDeviceList = scenesDeviceDao.queryBuilder().list();
        return   scenesDeviceList;
    }


    /**
     * 查询所有场景的触发条件数据
     * @return
     */
    public List<ScenesTrigger> queryAllScenesTrigger() {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        ScenesTriggerDao scenesTriggerDao = daoSession.getScenesTriggerDao();
        List<ScenesTrigger>  scenesTriggerList = scenesTriggerDao.queryBuilder().list();
        return   scenesTriggerList;
    }

    /**
     * 查询一个场景的控制设备数据
     * @return
     */
    public List<ScenesDevice> queryOneScenesDevices(String mEditScenesId) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        ScenesDeviceDao scenesDeviceDao = daoSession.getScenesDeviceDao();
        List<ScenesDevice>  scenesDeviceList = scenesDeviceDao.queryBuilder().where(ScenesDeviceDao.Properties.SCENES_ID.eq(mEditScenesId)).list();
        return   scenesDeviceList;
    }


    /**
     * 查询一个场景的触发条件数据
     * @return
     */
    public List<ScenesTrigger> queryOneScenesTriggers(String mEditScenesId) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        ScenesTriggerDao scenesTriggerDao = daoSession.getScenesTriggerDao();
        List<ScenesTrigger>  scenesDeviceList = scenesTriggerDao.queryBuilder().where(ScenesTriggerDao.Properties.SCENES_ID.eq(mEditScenesId)).list();
        return   scenesDeviceList;
    }

    /**
     * 更新一个场景
     * @param mNewScenes
     * @param mNewScenesDeviceList
     * @param mNewScenesTriggerList
     */
    public void updateOneScenes(Scenes mNewScenes, List<ScenesDevice> mNewScenesDeviceList, List<ScenesTrigger> mNewScenesTriggerList) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();


        ScenesDeviceDao scenesDeviceDao = daoSession.getScenesDeviceDao();
        scenesDeviceDao.updateInTx(mNewScenesDeviceList);

        ScenesTriggerDao scenesTriggerDao = daoSession.getScenesTriggerDao();
        scenesTriggerDao.updateInTx(mNewScenesTriggerList);

        ScenesDao scenesDao = daoSession.getScenesDao();
        scenesDao.update(mNewScenes);
    }


    /**
     * 删除一个场景
     * @param scenes
     */
    public void deleteOneScenes(Scenes scenes) {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();

        //删除一个场景的所有控制设备
        ScenesDeviceDao scenesDeviceDao = daoSession.getScenesDeviceDao();
        List<ScenesDevice>  scenesDeviceList = scenesDeviceDao.queryBuilder().where(ScenesDeviceDao.Properties.SCENES_ID.eq(scenes.getSCENE_ID())).list();
        for (int i = 0 ; i <scenesDeviceList.size() ; i++){
            scenesDeviceDao.delete(scenesDeviceList.get(i));
        }

        //删除一个场景的所有触发条件
        ScenesTriggerDao scenesTriggerDao = daoSession.getScenesTriggerDao();
        List<ScenesTrigger>  scenesTriggerList = scenesTriggerDao.queryBuilder().where(ScenesTriggerDao.Properties.SCENES_ID.eq(scenes.getSCENE_ID())).list();
        for (int i = 0 ; i <scenesTriggerList.size() ; i++){
            scenesTriggerDao.delete(scenesTriggerList.get(i));
        }

        //删除场景数据
        ScenesDao scenesDao = daoSession.getScenesDao();
        scenesDao.delete(scenes);
    }

    /**
     * 删除本地所有的数据
     */
    public boolean deleteALlData() {
        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        /**
         * 房间
         */
        RoomDao roomDao = daoSession.getRoomDao();
        roomDao.deleteAll();

        /**
         * 中继盒子
         */
        RelayBoxDao relayBoxDao  = daoSession.getRelayBoxDao();
        relayBoxDao.deleteAll();

        /**
         * 场景
         */
        ScenesDao  scenesDao = daoSession.getScenesDao();
        scenesDao.deleteAll();

        /**
         * 场景关联的设备
         */
        ScenesDeviceDao scenesDeviceDao = daoSession.getScenesDeviceDao();
        scenesDeviceDao.deleteAll();

        /**
         * 场景关联的触发条件
         */
        ScenesTriggerDao scenesTriggerDao = daoSession.getScenesTriggerDao();
        scenesTriggerDao.deleteAll();

        /**
         * 红外码库
         */

        IRKeyDao  irKeyDao = daoSession.getIRKeyDao();
        irKeyDao.deleteAll();

        /**
         * 设备
         */
        DevicesDao devicesDao = daoSession.getDevicesDao();
        devicesDao.deleteAll();

        return true;
    }

    public boolean insertAllData(List<Room> roomList, List<RelayBox> relayBoxList, List<Devices> devicesList, List<Scenes> scenesList, List<ScenesDevice> scenesDevicesList, List<ScenesTrigger> scenesTriggerList, List<IRKey> irKeyList) {

        DaoSession daoSession = MyApplication.getApplication().getDbManage().getmDaoSession();
        /**
         * 房间
         */
        RoomDao roomDao = daoSession.getRoomDao();
        roomDao.insertInTx(roomList);

        /**
         * 中继盒子
         */
        RelayBoxDao relayBoxDao  = daoSession.getRelayBoxDao();
        relayBoxDao.insertInTx(relayBoxList);

        /**
         * 场景
         */
        ScenesDao  scenesDao = daoSession.getScenesDao();
        scenesDao.insertInTx(scenesList);

        /**
         * 场景关联的设备
         */
        ScenesDeviceDao scenesDeviceDao = daoSession.getScenesDeviceDao();
        scenesDeviceDao.insertInTx(scenesDevicesList);

        /**
         * 场景关联的触发条件
         */
        ScenesTriggerDao scenesTriggerDao = daoSession.getScenesTriggerDao();
        scenesTriggerDao.insertInTx(scenesTriggerList);

        /**
         * 红外码库
         */

        IRKeyDao  irKeyDao = daoSession.getIRKeyDao();
        irKeyDao.insertInTx(irKeyList);

        /**
         * 设备
         */
        DevicesDao devicesDao = daoSession.getDevicesDao();
        devicesDao.insertInTx(devicesList);

        return true;
    }













  /*  //插入数据
    public void insertUser(MyTestBeanDao myTestBeanDao){
        TestBean user = new TestBean(null, "jianguotang", "男", 18 , 2000);
        myTestBeanDao.insert(user);
        Log.e("CHRIS", "insertUser: ----插入数据到数据库---jianguotang--");
    }


    //删除特定位置的数据
    public void deleteUser(MyTestBeanDao myTestBeanDao){

        myTestBeanDao.deleteByKey(5l);
    }*/



    /**
     * 对位置 为position的的数据进行修改
     * @param position
     *//*
    public void updateUser(Long position , MyTestBeanDao myTestBeanDao){
        //查询id是1位置的数据
        TestBean user = myTestBeanDao.load(5l);
        //对其进行修改
        user.setName("简国堂");
        myTestBeanDao.update(user);

        //这个方法也可以修改
        myTestBeanDao.insertOrReplace(user);

    }

    *//**
     * 查询所有
     * @param myTestBeanDao
     *//*

    public void getAll (MyTestBeanDao myTestBeanDao){

        List<TestBean>  lists =  myTestBeanDao.queryBuilder().list();
        for (TestBean myBean : lists) {
            Log.e("CHRIS", "getAll: lists  = " + myBean.toString());
        }

        //懒加载模式
        LazyList<TestBean> lazyList =  myTestBeanDao.queryBuilder().listLazy();
        //myTestBeanDao.queryBuilder().

        for (TestBean myBean : lazyList) {
            Log.e("CHRIS", "getAll: lazyList  = " + myBean.toString());
        }


        //用另一整个遍历查询的方法
        Iterator  myIterator = myTestBeanDao.queryBuilder().listIterator();
        while(myIterator.hasNext()){
            TestBean bean  = (TestBean) myIterator.next();
            Log.i("CHRIS", "getAll: myIterator  = " + bean.toString());
        }

        lazyList.close();

    }

    public void delete(MyTestBeanDao myTestBeanDao){
        //删除
        myTestBeanDao.delete(new TestBean());
        myTestBeanDao.deleteByKey(1L);

    }


    *//**
     * 条件查询  等于 Eq
     *//*
    public void queryEq(MyTestBeanDao myTestBeanDao){

        List beans =   myTestBeanDao.queryBuilder().where(MyTestBeanDao.Properties.Id.eq(1)).list();

        Log.i("CHRIS", "getAll: queryEq  = " + beans.toString());
    }

    *//**
     * 条件查询  不等于 NotEq
     *//*
    public void queryNotEq(MyTestBeanDao myTestBeanDao){

        List beans =   myTestBeanDao.queryBuilder().where(MyTestBeanDao.Properties.Id.notEq(1)).list();

        Log.i("CHRIS", "getAll: queryNotEq  = " + beans.toString());
    }


    *//**
     * 条件查询  匹配查找 Like
     *//*
    public void queryLike(MyTestBeanDao myTestBeanDao){

        List beans =   myTestBeanDao.queryBuilder().where(MyTestBeanDao.Properties.Name.like("jiang")).list();

        Log.i("CHRIS", "getAll: queryLike  = " + beans.toString());
    }

    *//**
     * 条件查询  区间查找 Between
     *//*
    public void queryBetween(MyTestBeanDao myTestBeanDao){

        List beans =   myTestBeanDao.queryBuilder().where(MyTestBeanDao.Properties.Age.between(10,20)).list();

        Log.i("CHRIS", "getAll: queryBetween  = " + beans.toString());

    }


    *//**
     * 条件查询  大于查找  Gt
     *//*
    public void queryGt(MyTestBeanDao myTestBeanDao){

        List beans =   myTestBeanDao.queryBuilder().where(MyTestBeanDao.Properties.Age.gt(20)).list();

        Log.i("CHRIS", "getAll: queryGt  = " + beans.toString());

    }

    *//**
     * 条件查询  大于等于查找 Ge
     *//*
    public void queryGe(MyTestBeanDao myTestBeanDao){

        List beans =   myTestBeanDao.queryBuilder().where(MyTestBeanDao.Properties.Age.ge(20)).list();

        Log.i("CHRIS", "getAll: queryGe  = " + beans.toString());

    }

    *//**
     * 条件查询  小于查找  Lt
     *//*
    public void queryLt(MyTestBeanDao myTestBeanDao){

        List beans =   myTestBeanDao.queryBuilder().where(MyTestBeanDao.Properties.Age.lt(20)).list();

        Log.i("CHRIS", "getAll: queryLt  = " + beans.toString());
    }

    *//**
     * 条件查询  小于等于查找 Le
     *//*
    public void queryLe(MyTestBeanDao myTestBeanDao){

        List beans =   myTestBeanDao.queryBuilder().where(MyTestBeanDao.Properties.Age.le(20)).list();

        Log.i("CHRIS", "getAll: queryLe  = " + beans.toString());
    }


    *//**
     * 条件查询  排序查询
     * 升序  OrderAsc
     * 升序  OrderDsc
     *//*
    public void queryOrderAsc(MyTestBeanDao myTestBeanDao){

        List beans = myTestBeanDao.queryBuilder().where(MyTestBeanDao.Properties.Age.le(20)).
                orderAsc(MyTestBeanDao.Properties.Id).list();

        Log.i("CHRIS", "getAll: queryLe  = " + beans.toString());
    }

    *//**
     * 原生方法查询
     * 升序  OrderAsc
     * 升序  OrderDsc
     */
   /* public void querySQL(MyTestBeanDao myTestBeanDao){
        Query<TestBean> query = myTestBeanDao.queryBuilder().where(
                new WhereCondition.StringCondition("_ID IN " +
                        "(SELECT USER_ID FROM USER_MESSAGE WHERE READ_FLAG = 0)")
        ).build();

        Log.i("CHRIS", "getAll: queryLe  = " + query.toString());
    }


    public void queryThread(MyTestBeanDao myTestBeanDao){
        final Query query = myTestBeanDao.queryBuilder().build();
        new Thread(){
            @Override
            public void run() {
                super.run();
                // org.greenrobot.greendao.DaoException:
                // Method may be called only in owner thread,
                // use forCurrentThread to get an instance for this thread  Query創建和調用需要在同一個線程中

                List data = query.forCurrentThread().list();
                Log.e("CHRIS", "run: == data == " + data);
            }
        }.start();

    }*/

}
