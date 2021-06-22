package com.app.skillontario.apiConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.WindowManager;

import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.callbacks.ConfirmDialogCallback;
import com.app.skillontario.dialogs.CustomAlertDialog;
import com.app.skillontario.utils.Utils;
import com.app.skillorterio.R;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApiCallBack<T> implements Callback<T>, ConfirmDialogCallback {

    private Context context;
    private ApiResponseErrorCallback responseErrorCallback;
    private int flag;
    private ProgressDialog pDialog;


    public ApiCallBack(Context context, ApiResponseErrorCallback responseErrorCallback, int flag, boolean isCustomProgress) {
        this.context = context;
        this.responseErrorCallback = responseErrorCallback;
        this.flag = flag;
        if (isCustomProgress) {
            createProgressDialog(context);
        }
    }

    @Override
    public void onResponse(@NotNull Call<T> call, Response<T> response) {
        dismissDialog();
        if (response.isSuccessful()) {
            BaseResponseModel model = (BaseResponseModel) response.body();
            try {
                if (model != null && model.getStatus() == 200) {
                    responseErrorCallback.getApiResponse(response.body(), flag);
                } else if (model != null && model.getError().getErrorCode() == 5) {
                    new CustomAlertDialog(context, context.getResources().getString(R.string.session_espired_msg),
                            context.getResources().getString((R.string.session_expired))).show();
                } else
                    responseErrorCallback.getApiResponse(response.body(), flag);
            } catch (Exception e) {
            }

        } else
            Utils.showToast(context, "Server Not Responding");

    }

    @Override
    public void onFailure(@NotNull Call<T> call, Throwable t) {
        Log.e("@@Error@@", t.toString());

        dismissDialog();
        responseErrorCallback.getApiError(t, flag);

        if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
            if (context instanceof Activity)
                ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            CustomAlertDialog alertDialog = new CustomAlertDialog(context, "Please check internet connectivity", "No Internet", this);
            alertDialog.setCancelable(false);
            alertDialog.show();
        } else {
            if (!checkInternet()) {
                Utils.showToast(context, "Please check internet connectivity");
            } else
                Utils.showToast(context, t.toString());
        }
    }

    private void createProgressDialog(Context context) {
        pDialog = new ProgressDialog(context);
        try {
            pDialog.show();
        } catch (WindowManager.BadTokenException ignored) {
        }

        pDialog.setCancelable(false);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.setContentView(R.layout.progress_dialog);
    }

    private void dismissDialog() {
        if (!((Activity) context).isFinishing()) {
            if (pDialog != null) {
                pDialog.dismiss();
                pDialog = null;
            }
        }

    }

    @Override
    public void onPositiveClick(int requestCode) {
        Intent intent = new Intent("android.settings.DATA_ROAMING_SETTINGS");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onNegativeClick(int requestCode) {

    }

    boolean checkInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}