package kap.com.smarthome.android.communication.http.constants;

/**
 * Created by yushq on 2017/9/30 0030.
 * 和服务器端通信的信息标志 INSIP
 */

public class HTTPMsgINSIP {
    /**
     * 验证码
     */
    // 验证码请求命令
   public static final String  GET_VALIDCODE_REQ=  "GETVALIDCODEREQ";
    //验证码响应命令
    public static final String GET_VALIDCODE_ACK= "GETVALIDCODEACK";

    /**
     * 用户注册
     */
    // 注册请求命令
    public static final String  NEW_ACCOUNT_REQ=  "NEWACCOUNTREQ";

    //注册响应命令
    public static final String NEW_ACCOUNT_ACK= "NEWACCOUNTACK";


    /**
     * 用户登录
     */
    //用户登录请求命令
    public static final String  USER_LOGIN_REQ=  "USERLOGINREQ";

    //用户登录响应命令
    public static final String USER_LOGIN_ACK= "USERLOGINACK";

    /**
     * 修改个人信息
     */
    //用户的个人信息修改请求
    public static final String  EDIT_USERINFO_REQ=  "EDITUSERINFOREQ";

    //修改个人信息相应
    public static final String EDIT_USERINFO_ACK= "EDITUSERINFOACK";


   /**
    * 修改用户密码
    */
   //用户密码修改请求
   public static final String  CHANGE_PWD_REQ=  "CHANGEPWDREQ";

   //修改密码的相应
   public static final String CHANGE_PWD_ACK= "CHANGEPWDACK";


    /**
     * 用户退出登录
     */
    //用户登出请求命令
    public static final String  USER_LOGOUT_REQ=  "USERLOGOUTREQ";

    //用户登出响应命令
    public static final String USER_LOGOUT_ACK= "USERLOGOUTACK";


 /**
  * 用户头像
  */
 //修改用户头像请求命令
 public static final String  EDIT_USER_HEADLOGO_REQ=  "EDITUSERHEADLOGOREQ";

 //修改用户头像响应命令
 public static final String EDIT_USER_HEADLOGO_ACK= "EDITUSERHEADLOGOACK";


  /**
  * 用户授权
  */
  //用户授权的请求
  public static final String  GRANT_NEW_REQ=  "GRANTNEWREQ";

  //用户授权的相应
  public static final String GRANT_NEW_ACK= "GRANTNEWACK";



/*  NEWACCOUNTREQ	注册请求命令
    NEWACCOUNTACK	注册响应命令

    USERLOGINREQ	用户登录请求命令
    USERLOGINACK	用户登录响应命令
    USERLOGOUTREQ	用户登出请求命令
    USERLOGOUTACK	用户登出响应命令
    CHANGEPWDREQ	修改密码请求命令
    CHANGEPWDACK	修改密码响应命令
    FORGETPWDREQ	忘记密码请求命令
    FORGETPWDACK	忘记密码响应命令
    GETVALIDCODEREQ	验证码请求命令
    GETVALIDCODEACK	验证码响应命令
    GRANTNEWREQ	新用户授权请求命令
    GRANTNEWACK	新用户授权响应命令
    GETUSERINFOREQ	获取用户信息请求命令
    GETUSERINFOACK	获取用户信息响应命令
    EDITUSERINFOREQ	修改用户信息请求命令
    EDITUSERINFOACK	修改用户信息响应命令


    EDITUSERHEADLOGOREQ	修改用户头像请求命令
    EDITUSERHEADLOGOACK	修改用户头像响应命令*/


//-------------------------------------房间

    /**
     * 添加房间
     */
    //添加房间请求命令
    public static final String  NEW_ROOM_REQ=  "NEWROOMREQ";

    //添加房间响应命令
    public static final String NEW_ROOM_RSP= "NEWROOMRSP";



   /**
    * 删除房间
    */
   //删除房间请求命令
   public static final String  DELETE_ROOM_REQ=  "DELETEROOMREQ";

   //删除房间响应命令
   public static final String DELETE_ROOM_RSP= "DELETEROOMRSP";


    /**
     * 更新房间
     */
    //更新房间请求命令
    public static final String  UPDATE_ROOM_REQ= "UPDATEROOMREQ";

    //更新房间响应命令
    public static final String UPDATE_ROOM_RSP= "UPDATEROOMRSP";


 //-------------------------------------中继盒子

    /**
     * 添加中继盒子
     */
    //绑定添加盒子请求命令
    public static final String  NEW_RELAYBOX_ANDUSER_REQ= "NEWRELAYBOXANDUSERREQ";

    //绑定添加盒子响应命令
    public static final String NEW_RELAYBOX_ANDUSER_RSP= "NEWRELAYBOXANDUSERRSP";


    /**
     * 查询中继盒子
     */
    //查询当前用户盒子请求命令
    public static final String  FIND_RELAYBOX_ANDUSER_REQ= "FINDRELAYBOXANDUSERREQ";

    //查询当前用户盒子响应命令
    public static final String FIND_RELAYBOX_ANDUSER_RSP= "FINDRELAYBOXANDUSERRSP";


    /**
     * 更新中继盒子
     */
    //查询当前用户盒子请求命令
    public static final String  UPDATE_RELAYBOX_ANDUSER_REQ= "UPDATERELAYBOXANDUSERREQ";

    //查询当前用户盒子响应命令
    public static final String UPDATE_RELAYBOX_ANDUSER_RSP= "UPDATERELAYBOXANDUSERRSP";


    /**
     * 删除中继盒子
     */
    //查询当前用户盒子请求命令
    public static final String  DELETE_RELAYBOX_ANDUSER_REQ= "DELETERELAYBOXANDUSERREQ";

    //查询当前用户盒子响应命令
    public static final String  DELETE_RELAYBOX_ANDUSER_RSP= "DELETERELAYBOXANDUSERRSP";


 //-------------------------------------------------------------------------------设备


/* FINDDEVICEREQ	查询盒子添加的设备请求命令
 FINDDEVICEREQRSP	查询盒子添加的设备响应命令

 UPDATEDEVICEREQ	更新盒子添加的设备请求命令
 UPDATEDEVICEREQRSP	更新盒子添加的设备响应命令

 DELETEDEVICEREQ	删除盒子添加的设备请求命令
 DELETEDEVICEREQRSP	删除盒子添加的设备响应命令

 NEWDEVICEREQ	     给盒子添加设备请求命令
 NEWDEVICEREQRSP	给盒子添加设备响应命令*/

    /**
     * 添加设备
     */
    //给盒子添加设备请求命令
    public static final String  NEW_DEVICE_REQ= "NEWDEVICEREQ";

    //给盒子添加设备响应命令
    public static final String NEW_DEVICEREQ_RSP= "NEWDEVICEREQRSP";


    /**
     * 查询设备
     */
    //查询盒子添加的设备请求命令
    public static final String  FIND_DEVICE_REQ= "FINDDEVICEREQ";

    //查询盒子添加的设备响应命令
    public static final String FIND_DEVICEREQ_RSP= "FINDDEVICEREQRSP";


    /**
     * 更新设备
     */
    //更新盒子添加的设备请求命令
    public static final String  UPDATE_DEVICE_REQ= "UPDATEDEVICEREQ";

    //更新盒子添加的设备响应命令
    public static final String UPDATE_DEVICEREQ_RSP= "UPDATEDEVICEREQRSP";


    /**
     * 删除设备
     */
    //删除盒子添加的设备请求命令
    public static final String  DELETE_DEVICE_REQ= "DELETEDEVICEREQ";

    //删除盒子添加的设备响应命令
    public static final String  DELETE_DEVICEREQ_RSP= "DELETEDEVICEREQRSP";



 /* NEWRELAYBOXANDUSERREQ	绑定添加盒子请求命令
    NEWRELAYBOXANDUSERRSP	绑定添加盒子响应命令

    FINDRELAYBOXANDUSERREQ	查询当前用户盒子请求命令
    FINDRELAYBOXANDUSERRSP	查询当前用户盒子响应命令

    UPDATERELAYBOXANDUSERREQ	更新当前用户盒子请求命令
    UPDATERELAYBOXANDUSERRSP	更新当前用户盒子响应命令

    DELETERELAYBOXANDUSERREQ	删除当前用户盒子请求命令
    DELETERELAYBOXANDUSERRSP	删除当前用户盒子响应命令


    FINDDEVICEREQ	查询盒子添加的设备请求命令
    FINDDEVICEREQRSP	查询盒子添加的设备响应命令
    UPDATEDEVICEREQ	更新盒子添加的设备请求命令
    UPDATEDEVICEREQRSP	更新盒子添加的设备响应命令
    DELETEDEVICEREQ	删除盒子添加的设备请求命令
    DELETEDEVICEREQRSP	删除盒子添加的设备响应命令
    NEWDEVICEREQ	给盒子添加设备请求命令
    NEWDEVICEREQRSP	给盒子添加设备响应命令

    NEWROOMREQ	添加房间请求命令
    NEWROOMRSP	添加房间响应命令

    FINDROOMREQ	查询房间请求命令
    FINDROOMRSP	查询房间响应命令

    UPDATEROOMREQ	更新房间请求命令
    UPDATEROOMRSP	更新房间响应命令

    DELETEROOMEQ	删除房间请求命令
    DELETEROOMRSP	删除房间响应命令

    NEWSCENEREQ	添加场景请求命令
    NEWSCENERSP	添加场景响应命令
    FINDSCENEREQ	查询场景请求命令
    FINDSCENERSP	查询场景响应命令
    UPDATESCENEREQ	更新场景请求命令
    UPDATESCENERSP	更新场景响应命令
    DELETESCENEREQ	删除场景请求命令
    DELETESCENERSP	删除场景响应命令*/





}
