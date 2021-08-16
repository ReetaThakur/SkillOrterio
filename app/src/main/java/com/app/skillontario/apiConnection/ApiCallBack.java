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
import android.widget.Toast;

import com.app.skillontario.SignIn.SignInActivity;
import com.app.skillontario.activities.SettingActivity;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.callbacks.ConfirmDialogCallback;
import com.app.skillontario.constants.AppConstants;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.dialogs.CustomAlertDialog;
import com.app.skillontario.models.careerListModel.CareerListOutput;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillontario.utils.Utils;
import com.app.skillorterio.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.skillontario.constants.AppConstants.FIREBASE_TOKEN;
import static com.app.skillontario.constants.AppConstants.IS_WALK_THROUGH;


public class ApiCallBack<T> implements Callback<T>, ConfirmDialogCallback {

    private Context context;
    private ApiResponseErrorCallback responseErrorCallback;
    private int flag;
    private ProgressDialog pDialog;
    //  CustomAlertDialog customAlertDialog;


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

            if (flag == 969) {
                CareerListOutput model = (CareerListOutput) response.body();
                try {
                    if (model != null && model.getStatus()) {
                        responseErrorCallback.getApiResponse(response.body(), flag);
                    } else if (model != null && model.getStatus() == false) {
                        responseErrorCallback.getApiResponse(response.body(), flag);
                    } else
                        responseErrorCallback.getApiResponse(response.body(), flag);
                } catch (Exception e) {
                }
            } else {

                BaseResponseModel model = (BaseResponseModel) response.body();
                try {
                    if (model != null && model.getStatus()) {
                        responseErrorCallback.getApiResponse(response.body(), flag);
                    } else if (model != null && model.getStatus() == false) {
                        responseErrorCallback.getApiResponse(response.body(), flag);
                    } else
                        responseErrorCallback.getApiResponse(response.body(), flag);
                } catch (Exception e) {
                }
            }

        } else if (response.code() == 500) {  /// 101,102,104 error code logout

            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                Utils.showToast(context, jObjError.getString("message"));

                int errorCode = jObjError.getInt("errorCode");
                if (errorCode == 101 || errorCode == 102 || errorCode == 104) {
                    // logout();
                    new CustomAlertDialog(context, context.getString(R.string.logdevice), context.getString(R.string.app_name), context.getString(R.string.okay), null, 0, this, 98).show();
                }
            } catch (Exception e) {
                Utils.showToast(context, context.getString(R.string.ser));
            }

        } else {
            //logout();
            new CustomAlertDialog(context, context.getString(R.string.logdevice), context.getString(R.string.app_name), context.getString(R.string.okay), null, 0, this, 98).show();
        }

    }

    @Override
    public void onFailure(@NotNull Call<T> call, Throwable t) {
        Log.e("@@Error@@", t.toString());

        dismissDialog();
        responseErrorCallback.getApiError(t, flag);

        if (t instanceof SocketTimeoutException || t instanceof UnknownHostException) {
            if (context instanceof Activity)
                ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            Utils.showToast(context, context.getString(R.string.no_internet_msg));
        } else {
            if (!checkInternet()) {
                Utils.showToast(context, context.getString(R.string.no_internet_msg));
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
        if (requestCode == 98) {
            logout();
        }
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

    void logout() {
        String lang = MySharedPreference.getInstance().getStringData(AppConstants.LANGUAGE);
        String token = MySharedPreference.getInstance().getStringData(FIREBASE_TOKEN);
        MySharedPreference.getInstance().clearSharedPrefs();
        MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.GUEST_FLOW, false);
        MySharedPreference.getInstance().setBooleanData(IS_WALK_THROUGH, true);
        MySharedPreference.getInstance().setStringData(AppConstants.LANGUAGE, lang);
        if (lang.equalsIgnoreCase("en")) {
            MySharedPreference.getInstance().setStringData(SharedPrefsConstants.LANGUAGE_API, "eng");
        } else {
            MySharedPreference.getInstance().setStringData(SharedPrefsConstants.LANGUAGE_API, "fre");
        }

        MySharedPreference.getInstance().setStringData(FIREBASE_TOKEN, token);

        try {
            Intent intent = new Intent(context, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
            ((Activity) context).finishAffinity();
        } catch (Exception e) {
        }
    }
}