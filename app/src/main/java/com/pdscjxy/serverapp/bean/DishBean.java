package com.pdscjxy.serverapp.bean;

import com.pdscjxy.serverapp.util.JsonUtils;

import org.json.JSONObject;

/**
 * Created by tangzy on 17/10/28.
 */

public class DishBean implements BaseBean{
    private String id;
    private String name;
    private String no;
    private int number;
    private float price;
    private int type;//1 热菜 ，2 凉菜 ， 3 面点。
    private int state = 1;// 1 正常 ， 2 更换

    @Override
    public void parse(JSONObject jo) {
        id = JsonUtils.getString(jo, "id");
//        state = JsonUtils.getString(jo, "state");

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }


    public float getPrice() {
        return price;
    }
    public int getType() {
        return type;
    }

    public String getNo() {
        return no;
    }

    public int getState() {
        return state;
    }



    public void setState(int state) {
        this.state = state;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }


}
