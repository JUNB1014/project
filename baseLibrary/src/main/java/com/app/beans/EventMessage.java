package com.app.beans;


public class EventMessage {

    private Object mObject;

    private int messageType;

    private int Num;

    public EventMessage(int type) {
        messageType = type;
    }

    public EventMessage(int type, Object object) {
        mObject = object;
        messageType = type;
    }

    public EventMessage(int type, Object object, int num) {
        mObject = object;
        messageType = type;
        Num = num;
    }

    public Object getmObject() {
        return mObject;
    }

    public int getMessageType() {
        return messageType;
    }

    public int getNum() {
        return Num;
    }

    //消息類型
    public static final int LOGIN = 1;//登陸
    public static final int LOGOUT = 2;//
    public static final int add = 3;//




    public static final int Refresh = 7;

    public static final int CHOICE_PIC = 8;


}
