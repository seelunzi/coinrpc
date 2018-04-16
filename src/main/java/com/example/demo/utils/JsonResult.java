package com.example.demo.utils;

import lombok.Data;

import java.io.Serializable;

/***
 * @author tangwenbo
 * */
@Data
public class JsonResult implements Serializable {

    private Boolean success = false;
    private String msg = "";
    private Object obj = null;
    private String code = "";

    public JsonResult() {
    }
}
