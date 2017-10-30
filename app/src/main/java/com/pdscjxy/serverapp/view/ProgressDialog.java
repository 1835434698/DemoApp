package com.pdscjxy.serverapp.view;

import android.content.Context;

/**
 * Created by tangzy on 17/10/29.
 */

public class ProgressDialog {

    private CustomProgressDialog progressDialog = null;

    private ProgressDialog(){}

    private static ProgressDialog pd = null;

    public static ProgressDialog getInstance(){
        if (pd == null) {
            pd = new ProgressDialog();
        }
        return pd;
    }




    public void startProgressDialog(Context context, String message) {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(context);
            progressDialog.setMessage(message);
        }

        progressDialog.show();
    }
    public void startProgressDialog(Context context) {
        startProgressDialog(context, "");
    }

    public void stopProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()){
                progressDialog.dismiss();
                progressDialog = null;
            }

        }
    }

}
