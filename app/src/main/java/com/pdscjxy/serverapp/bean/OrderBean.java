package com.pdscjxy.serverapp.bean;

import com.pdscjxy.serverapp.util.JsonUtils;

import org.json.JSONObject;

/**
 * Created by tangzy on 17/10/28.
 */

public class OrderBean implements BaseBean {
    private String id;
    private String sellerName;
    private String text;
    private int state;

    @Override
    public void parse(JSONObject jo) {
        id = JsonUtils.getString(jo, "id");
//        state = JsonUtils.getString(jo, "state");

    }

    public String getId() {
        return id;
    }

    public int getState() {
        return state;
    }

    public String getText() {
        return text;
    }

    public String getSellerName() {
        return sellerName;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
}
