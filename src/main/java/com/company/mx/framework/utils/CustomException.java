package com.company.mx.framework.utils;

public class CustomException extends Throwable {
    String code;
    String message;

    public CustomException(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode(){return this.code;}

    public String getMessage(){return this.message;}

}
