package com.clickbusbackend.philippe.models;

import java.io.Serializable;

public class AppResponse implements Serializable {
    public Object data;
    public String message;
    public Boolean success;

    public AppResponse(Boolean success, String message, Object data){
        this.data = data;
        this.message = message;
        this.success = success;
    }

    public AppResponse(Boolean success, String message){
        this.message = message;
        this.success = success;
    }
}
