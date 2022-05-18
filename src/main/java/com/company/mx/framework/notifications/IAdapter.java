package com.company.mx.framework.notifications;

public interface IAdapter {
    public boolean sendSMS(String phone, String msg);
}
