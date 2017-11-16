package kap.com.smarthome.android.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yushq on 2017/10/9 0009.
 */

@Entity
public class User {

    @Id(autoincrement =  true)
    private Long ID;

    @Index(unique  = true)
    private String  GUID ;

    private String  LOGIN_NAME;

    private String  PHONE;

    private String  EMAIL;

    private String  WECHAT;

    private String  QQ;

    private int  status;

    private String  USER_ID;

    private String  USER_HEAD;

    private String  NICK_NAME;

    private String  SESSION_ID;

    @Generated(hash = 1383091989)
    public User(Long ID, String GUID, String LOGIN_NAME, String PHONE, String EMAIL,
            String WECHAT, String QQ, int status, String USER_ID, String USER_HEAD,
            String NICK_NAME, String SESSION_ID) {
        this.ID = ID;
        this.GUID = GUID;
        this.LOGIN_NAME = LOGIN_NAME;
        this.PHONE = PHONE;
        this.EMAIL = EMAIL;
        this.WECHAT = WECHAT;
        this.QQ = QQ;
        this.status = status;
        this.USER_ID = USER_ID;
        this.USER_HEAD = USER_HEAD;
        this.NICK_NAME = NICK_NAME;
        this.SESSION_ID = SESSION_ID;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getID() {
        return this.ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getGUID() {
        return this.GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getLOGIN_NAME() {
        return this.LOGIN_NAME;
    }

    public void setLOGIN_NAME(String LOGIN_NAME) {
        this.LOGIN_NAME = LOGIN_NAME;
    }

    public String getPHONE() {
        return this.PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getEMAIL() {
        return this.EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getWECHAT() {
        return this.WECHAT;
    }

    public void setWECHAT(String WECHAT) {
        this.WECHAT = WECHAT;
    }

    public String getQQ() {
        return this.QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUSER_ID() {
        return this.USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getUSER_HEAD() {
        return this.USER_HEAD;
    }

    public void setUSER_HEAD(String USER_HEAD) {
        this.USER_HEAD = USER_HEAD;
    }

    public String getNICK_NAME() {
        return this.NICK_NAME;
    }

    public void setNICK_NAME(String NICK_NAME) {
        this.NICK_NAME = NICK_NAME;
    }

    public String getSESSION_ID() {
        return this.SESSION_ID;
    }

    public void setSESSION_ID(String SESSION_ID) {
        this.SESSION_ID = SESSION_ID;
    }


    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", GUID='" + GUID + '\'' +
                ", LOGIN_NAME='" + LOGIN_NAME + '\'' +
                ", PHONE='" + PHONE + '\'' +
                ", EMAIL='" + EMAIL + '\'' +
                ", WECHAT='" + WECHAT + '\'' +
                ", QQ='" + QQ + '\'' +
                ", status=" + status +
                ", USER_ID='" + USER_ID + '\'' +
                ", USER_HEAD='" + USER_HEAD + '\'' +
                ", NICK_NAME='" + NICK_NAME + '\'' +
                ", SESSION_ID='" + SESSION_ID + '\'' +
                '}';
    }
}
