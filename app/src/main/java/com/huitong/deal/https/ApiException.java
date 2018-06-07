package com.huitong.deal.https;

/**
 * Created by Zheng on 2018/5/17.
 */

public class ApiException extends Exception {
    private int code;
    private String displayMessage;

    public ApiException(int code, String displayMessage){
        this.code= code;
        this.displayMessage= displayMessage;
    }

    public ApiException(int code, String message, String displayMessage){
        super(message);
        this.code= code;
        this.displayMessage= displayMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "code=" + code +
                ", displayMessage='" + displayMessage + '\'' +
                '}';
    }
}