package com.app.beans;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class UserBean extends DataSupport implements Serializable {

    public int id;
    public String user_id;
    public String name;
    public String password;
    public String photo;
    public String mobile;
    public String remark;
    public int type; //0 用戶  1 管理員
}
