package com.pdscjxy.serverapp.bean;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/26.
 */

public interface BaseBean extends Serializable{
    public void parse(JSONObject jo);
}
