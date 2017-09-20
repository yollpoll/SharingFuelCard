package com.sharingfuelcard.sharingfuelcard.http;

/**
 * Created by 鹏祺 on 2017/9/20.
 */

public class ResponseData<T> {
    private boolean ifTure;
    private String message;
    private T data;

    public boolean isIfTure() {
        return ifTure;
    }

    public void setIfTure(boolean ifTure) {
        this.ifTure = ifTure;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
