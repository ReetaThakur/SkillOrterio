package com.app.skillsontario.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.app.skillsontario.R;
import com.app.skillsontario.SignIn.SignInActivity;
import com.app.skillsontario.callbacks.ConfirmDialogCallback;
import com.app.skillsontario.constants.AppConstants;
import com.app.skillsontario.constants.SharedPrefsConstants;
import com.app.skillsontario.utils.MySharedPreference;


import static com.app.skillsontario.constants.AppConstants.FIREBASE_TOKEN;
import static com.app.skillsontario.constants.AppConstants.IS_WALK_THROUGH;


public class CustomAlertDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private String msg, head;
    private ConfirmDialogCallback dialogCallback;
    private int image;
    private int requestCode;
    private String postText = "";
    private String negText = "";

    public CustomAlertDialog(@NonNull Context context, String msg, String heading, String posText,
                             String negText, int image, ConfirmDialogCallback dialogCallback, int requestCode) {
        super(context);
        this.context = context;
        this.msg = msg;
        this.head = heading;
        this.postText = posText;
        this.negText = negText;
        this.image = image;
        this.requestCode = requestCode;
        this.dialogCallback = dialogCallback;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_custom_alert);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);

        initView();

    }

    private void initView() {
        try {
            TextView confirm_msg_txt = findViewById(R.id.msg_txt);
            // TextView confirm_head_txt = findViewById(R.id.heading_txt);
            TextView ok_btn = findViewById(R.id.positive_btn);

            confirm_msg_txt.setText(msg);
            ok_btn.setOnClickListener(this);


        } catch (Exception e) {
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.positive_btn) {
            // logout();
            onBackPressed();
            /*if (dialogCallback != null)
                dialogCallback.onPositiveClick(FILE_SUBMITTED);*/
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (requestCode == 98) {
            logout();

        } else {
            if (dialogCallback != null)
                dialogCallback.onPositiveClick(1);
            dismiss();
        }
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