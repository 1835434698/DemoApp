package com.pdscjxy.serverapp.net;

import android.app.Activity;

import com.pdscjxy.serverapp.bean.NetBean;
import com.pdscjxy.serverapp.manager.Constant;
import com.pdscjxy.serverapp.util.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import okio.BufferedSink;

/**
 * Created by Administrator on 2017/10/26.
 */

public class OkHttpManager {

    private static final String TAG = "OkHttpManager";
    private static String url;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static String strResult;

    private static PersistentCookieStore cookieStore;
    private static CookieJarImpl cookieJarImpl;
    public static void asyncRequest(final String uri, final Map<String, String> httpParams, final ResponseListener listener, final boolean isPost) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    url = Constant.url+uri;
                    if (cookieStore == null){
                        cookieStore = new PersistentCookieStore(Constant.context);
                    }
                    //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
                    Request request = null;
                    if (isPost){
                        request = httpPost(httpParams, url);
                    }else {
                        request = httpGet(httpParams, url);
                    }
                    Logger.d(TAG, "onReq = "+httpParams);
                    cookieJarImpl = new CookieJarImpl(cookieStore);
                    okHttpClient = okHttpClient.newBuilder().cookieJar(cookieJarImpl).build();

                    //发送请求获取响应
                    final Response response = okHttpClient.newCall(request).execute();

                    if(response.isSuccessful()){
                        strResult=response.body().string();
                        ((Activity)(Constant.context)).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                NetBean netBean = new NetBean();
                                try {
                                    netBean.parse(new JSONObject(strResult));
                                    listener.onResp(netBean.getRetData(),uri);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    ((Activity)(Constant.context)).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            listener.onErr(Constant.MESSSAGEJSON,uri);

                                        }
                                    });
                                }

                            }
                        });
                    }else{
                        strResult=response.body().string();
                        ((Activity)(Constant.context)).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onErr(strResult,uri);

                            }
                        });
                    }
                    Logger.d(TAG, "onResp = "+strResult);
                } catch (Exception e){
                    Logger.e(TAG, "error = " + e);
                    ((Activity)(Constant.context)).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.onErr(Constant.MESSSAGE, uri);
                        }
                    });
                }
            }
        };
        thread.start();
    }

    private static Request httpPost(Map<String, String> httpParams, String url) {
        Request request;
        FormBody.Builder  builder = new FormBody.Builder();
        if (httpParams != null && !httpParams.isEmpty()) {
            Set<String> strings = httpParams.keySet();
            for (String key : strings) {
                String value = httpParams.get(key);
                builder.add(key,value);
            }
        }
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
//               RequestBody requestBody = builder.build();

        request =  new Request.Builder().url(url).post(builder.build()).build();
        return request;
    }


    private static Request httpGet(Map<String, String> httpParams, String url) throws UnsupportedEncodingException {
        Request request = null;
        StringBuilder sb = new StringBuilder();
        if (httpParams != null && !httpParams.isEmpty()) {
            Set<String> strings = httpParams.keySet();
            for (String key : strings) {
                sb.append(key);
                if (httpParams.get(key) != null){
                    sb.append("=");
                    sb.append(URLEncoder.encode(httpParams.get(key), "UTF-8"));
                }
                sb.append("&");
            }
            if(url.indexOf("?") >= 0){
                if(sb.length() > 0){
                    url = url + "&" + sb.substring(0, sb.length() - 1);
                }
            } else if(sb.length() > 0){
                url = url + "?" + sb.substring(0, sb.length() - 1);
            }
            request = new Request.Builder().url(url).build();
        }
        return request;
    }


    public static interface ResponseListener {
        public void onResp(JSONObject respons, String uri);

        public void onErr(String respons, String uri);
    }

    /** Returns a new request body that transmits {@code content}. */
    public static RequestBody create(final MediaType contentType, final byte[] content) {
        return create(contentType, content, 0, content.length);
    }

    /** Returns a new request body that transmits {@code content}. */
    public static RequestBody create(final MediaType contentType, final byte[] content,
                                     final int offset, final int byteCount) {
        if (content == null) throw new NullPointerException("content == null");
        Util.checkOffsetAndCount(content.length, offset, byteCount);
        return new RequestBody() {
            @Override public MediaType contentType() {
                return contentType;
            }

            @Override public long contentLength() {
                return byteCount;
            }

            @Override public void writeTo(BufferedSink sink) throws IOException {
                sink.write(content, offset, byteCount);
            }
        };
    }
}
