package com.app.skillontario.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.app.skillontario.SignIn.SignInActivity;
import com.app.skillontario.activities.SplashActivity;
import com.app.skillontario.callbacks.ConfirmDialogCallback;
import com.app.skillontario.constants.AppConstants;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillorterio.R;

import java.util.Objects;

import static com.app.skillontario.constants.AppConstants.IS_WALK_THROUGH;
import static com.google.firebase.analytics.FirebaseAnalytics.Event.LOGIN;


public class CustomAlertDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private String msg, head;
    private ConfirmDialogCallback dialogCallback;

    public CustomAlertDialog(@NonNull Context context, String msg, String heading) {
        super(context);
        this.context = context;
        this.msg = msg;
        this.head = heading;
    }

    public CustomAlertDialog(@NonNull Context context, String msg, String heading, ConfirmDialogCallback dialogCallback) {
        super(context);
        this.context = context;
        this.msg = msg;
        this.head = heading;
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
            TextView confirm_head_txt = findViewById(R.id.heading_txt);
            TextView ok_btn = findViewById(R.id.ok_btn);

            confirm_msg_txt.setText(msg);
            ok_btn.setOnClickListener(this);

            if (head != null)
                confirm_head_txt.setText(head);
            else
                confirm_head_txt.setText("");
        }catch (Exception e){}

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ok_btn) {
            onBackPressed();
            /*if (dialogCallback != null)
                dialogCallback.onPositiveClick(FILE_SUBMITTED);*/
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (msg.equalsIgnoreCase(context.getString(R.string.session_espired_msg))) {
            Intent intent = new Intent(context, SignInActivity.class);
            intent.putExtra(AppConstants.LOGIN_TYPE, "new");
            context.startActivity(intent);
            MySharedPreference.getInstance().clearSharedPrefs();
            MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.IS_LOGIN, false);
            ((Activity) context).finishAffinity();

        } else {
            if (dialogCallback != null)
                dialogCallback.onPositiveClick(1);
            dismiss();
        }
    }
}