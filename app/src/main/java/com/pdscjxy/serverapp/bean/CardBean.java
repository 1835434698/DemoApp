package com.pdscjxy.serverapp.bean;

import com.pdscjxy.serverapp.util.JsonUtils;

import org.json.JSONObject;

/**
 * Created by tangzy on 17/10/28.
 */

public class CardBean implements BaseBean {
    private String id;
    private int state;
    private String text;
    private OrderBean orderBean;

    @Override
    public void parse(JSONObject jo) {
        id = JsonUtils.getString(jo, "id");
//        state = JsonUtils.getString(jo, "state");


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


    public String getId() {
        return id;
    }

    public OrderBean getOrderBean() {
        return orderBean;
    }


    public void setOrderBean(OrderBean orderBean) {
        this.orderBean = orderBean;
    }

    public int getState() {
        return state;
    }

    public String getText() {
        return text;
    }
}
