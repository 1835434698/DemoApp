package com.pdscjxy.serverapp.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/10/26.
 */

public class JsonUtils {

    public static JSONArray getJsonArray(Object obj, String key) {
        JSONArray array = null;

        try{
            JSONObject js = new JSONObject(obj.toString());
            array = js.optJSONArray(key);
        } catch (JSONException e){
            e.printStackTrace();
            System.out.println("get json array error: " + e);
        }

        return array;
    }

    public static String getJsonArrayString(JSONArray array, int i, String key) {        String res = "";

        res = array.optJSONObject(i).optString(key);
        return res;
    }

    public static String getSecondJsonString(Object obj, String key1, String key2) {
        String res = "";

        try{
            JSONObject ob = new JSONObject(obj.toString());
            res = ob.optJSONObject(key1).optString(key2);
        } catch (JSONException e){
            e.printStackTrace();
            System.out.println("get second json error: " + e);
        }

        return res;
    }

    public static JSONArray getSecondArray(JSONObject object, String key1, String key2){
        JSONArray array = new JSONArray();
        JSONObject object2 = object.optJSONObject(key1);
        array = getJSONArray(object2, key2);
        return array;
    }

    // 从jsonarray数组得到索引的json object
    public static String getJsonArrayObjStr(JSONArray array, int i) {
        String str = "";
        str = array.optJSONObject(i).toString();
        return str;
    }

    /**
     * 由字符串得到JsonObject
     *
     * @param str
     * @return 得到的Obj 或者 null
     */
    public static JSONObject getObjFromStr(String str) {
        try{
            return new JSONObject(str);
        } catch (JSONException e){
            e.printStackTrace();
            System.out.println("get Obj From Str error: " + e);
        }
        return null;
    }

    /**
     * 从jsonarray数组中得到JsonObject
     * @param array
     * @param i
     * @return
     */
    public static JSONObject getObjFromArray(JSONArray array, int i){
        JSONObject object = new JSONObject();
        object = array.optJSONObject(i);
        return object;
    }

    /**
     * 得到Json中对于key值的数据
     *
     * @param obj
     * @param key
     * @return
     */
    public static String getObjString(Object obj, String key) {
        String res = null;
        if(obj==null){
            return res;
        }
        try{
            JSONObject ob = new JSONObject(obj.toString());
            res = ob.optString(key);
        } catch (JSONException e){
            e.printStackTrace();
            System.out.println("get obj json string error: " + e);
        }

        return res;
    }

    /**
     * 两个JSONArray合并
     * @param mData -- 前一个
     * @param array -- 后一个
     * @return 返回合并完成后的结果
     */
    public static JSONArray joinJSONArray(JSONArray mData, JSONArray array) {
        StringBuffer buffer = new StringBuffer();
        try{
            int len = mData.length();
            for (int i = 0; i < len; i++){
                JSONObject obj1 = (JSONObject) mData.get(i);
                if (i == len - 1)
                    buffer.append(obj1.toString());
                else
                    buffer.append(obj1.toString()).append(",");
            }
            len = array.length();
            if (len > 0)
                buffer.append(",");
            for (int i = 0; i < len; i++){
                JSONObject obj1 = (JSONObject) array.get(i);
                if (i == len - 1)
                    buffer.append(obj1.toString());
                else
                    buffer.append(obj1.toString()).append(",");
            }
            buffer.insert(0, "[").append("]");
            return new JSONArray(buffer.toString());
        } catch (Exception e){
        }
        return null;
    }

    public static String getString(JSONObject response, String key) {
        String ret = "";
        if (response != null){
//			try{
            ret = response.optString(key);
//			} catch (JSONException e){
//				e.printStackTrace();
//				System.out.println("JsonUtils.getString err: " + key);
//			}
        }
        return ret;
    }

    public static boolean getBoolean(JSONObject jo, String key) {
        boolean ret = false;
        if (jo != null){
            ret = jo.optBoolean(key);
        }
        return ret;
    }

    public static int getInt(JSONObject jo, String key) {
        int ret = 0;
        if (jo != null){
            ret = jo.optInt(key);
        }
        return ret;
    }

    public static long getLong(JSONObject jo, String key) {
        if (jo != null){
            return jo.optLong(key);
        }
        return 0;
    }

    public static double getDouble(JSONObject jo, String key) {
        if (jo != null){
            return jo.optDouble(key);
        }
        return 0;
    }

    public static JSONObject getJSONObject(JSONObject jo, String key) {
        if (jo != null){

            return jo.optJSONObject(key);
        }
        return null;
    }

    public static JSONArray getJSONArray(JSONObject response, String key) {
        JSONArray jsonArray = null;
        jsonArray = (JSONArray) response.optJSONArray(key);
        if (jsonArray == null)
            jsonArray = new JSONArray();
        return jsonArray;
    }

    public static JSONArray getSecondJsonArray(JSONArray array, int i, String key){
        JSONArray jsonArray = null;
        JSONObject obj = array.optJSONObject(i);
        jsonArray = obj.optJSONArray(key);
        return jsonArray;
    }

    public static JSONArray getJSONArray(String str) {
        try{
            return new JSONArray(str);
        } catch (JSONException e){
            e.printStackTrace();
            System.out.println("JsonUtils.parseActionArray err: " + e);
        }
        return null;
    }

    public static JSONObject getJsonObject(String str) {
        try{
            return new JSONObject(str);
        } catch (JSONException e){
            e.printStackTrace();
            System.out.println("JsonUtils.parseActionArray err: " + e);
        }
        return null;
    }


    public static void putString(JSONObject jo, String key, String value) {
        try{
            jo.put(key, value);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    /**
     * 转换给定的 JSON 字符串中某个字段。
     *
     * @param JsonStr
     * @param name
     * @return
     */
    public static String getJsonStr(String JsonStr, String name) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject != null) {
            return jsonObject.optString(name);
        } else {
            return "";
        }
    }
}
