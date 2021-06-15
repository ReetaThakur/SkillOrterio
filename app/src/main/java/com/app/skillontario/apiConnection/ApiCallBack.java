package com.app.skillontario.apiConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.WindowManager;


import com.app.skillontario.SignIn.SignInActivity;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.callbacks.ConfirmDialogCallback;
import com.app.skillontario.dialogs.CustomAlertDialog;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillorterio.R;

import org.jetbrains.annotations.NotNull;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.skillontario.constants.AppConstants.IS_WALK_THROUGH;


public class ApiCallBack<T> implements Callback<T>, ConfirmDialogCallback {

    private final Context context;
    private final ApiResponseErrorCallback responseErrorCallback;
    private final int flag;
    private ProgressDialog dialog;


    public ApiCallBack(Context context, ApiResponseErrorCallback responseErrorCallback, int flag) {
        this.context = context;
        this.responseErrorCallback = responseErrorCallback;
        this.flag = flag;
    }

    public ApiCallBack(Boolean progress, Context context, ApiResponseErrorCallback responseErrorCallback, int flag) {
        this.context = context;
        this.responseErrorCallback = responseErrorCallback;
        this.flag = flag;
        //-------------
        if (progress) {
            try {
                dialog = new ProgressDialog(context);
                try {
                    dialog.show();
                } catch (WindowManager.BadTokenException ignored) {
                }
                dialog.setCancelable(false);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.progress_dialog);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onResponse(@NotNull Call<T> call, @NotNull Response<T> response) {
        if (dialog != null)
            dialog.dismiss();
        //-------------
        try {
            if (response.isSuccessful()) {
                BaseResponseModel responseModel = (BaseResponseModel) response.body();
                if (responseModel != null) {
                    if (responseModel.status)
                        responseErrorCallback.getApiResponse(response.body(), flag);

                } else
                    responseErrorCallback.getApiResponse(response.body(), flag);

            } else {
                if (response.code() == 500) {
//                    Utils.showToast(context, "500 Server Not Responding");
                } else if (response.code() == 404) {
                    new CustomAlertDialog(context, "You've logged in from other device. Please log in again.", "MaxSold", "OK", null, 0, this, 98).show();
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onFailure(@NotNull Call<T> call, @NotNull Throwable t) {
        Log.e("-----", t.toString());
        if (dialog != null)
            dialog.dismiss();
        if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
            try {
                if (!((Activity) context).isFinishing()) {
                    new CustomAlertDialog(context, context.getString(R.string.no_internet_msg), null,
                            context.getString(R.string.button_ok), null, R.drawable.ic_no_internet, 99).show();
                } else
                    responseErrorCallback.getApiError(new Throwable(context.getString(R.string.no_internet_msg)), flag);
                if (context instanceof Activity)
                    ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            } catch (Exception ignored) {

            }

        } else
            responseErrorCallback.getApiError(t, flag);

    }

    @Override
    public void onPositiveClick(int requestCode) {
        if (requestCode == 98) {
            MySharedPreference.getInstance().clearSharedPrefs();
            MySharedPreference.getInstance().setBooleanData(IS_WALK_THROUGH, true);
            context.startActivity(new Intent(context, SignInActivity.class));
            ((Activity) context).finishAffinity();
        }
    }

    @Override
    public void onNegativeClick(int requestCode) {

    }
}