package com.pdscjxy.serverapp.bean;

import com.pdscjxy.serverapp.util.JsonUtils;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/10/26.
 */

public class NetBean implements BaseBean {
    private String retCode;
    private String retMessage;
    private JSONObject retData;

    public String getRetCode() {
        return retCode;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public JSONObject getRetData() {
        return retData;
    }

    @Override
    public void parse(JSONObject jo) {
        retCode = JsonUtils.getString(jo, "retCode");
        retMessage = JsonUtils.getString(jo, "retMessage");
        retData = JsonUtils.getJSONObject(jo, "retData");
    }
}
