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

    private final Context context;
    private final int image;
    private final int requestCode;
    private final String msg;
    private final String head;
    private final String postText;
    private final String negText;
    private ConfirmDialogCallback dialogCallback;

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

    public CustomAlertDialog(@NonNull Context context, String msg, String heading, String posText,
                             String negText, int image, int requestCode) {
        super(context);
        this.context = context;
        this.msg = msg;
        this.head = heading;
        this.postText = posText;
        this.negText = negText;
        this.image = image;
        this.requestCode = requestCode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_custom_alert);
        Objects.requireNonNull(getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);

        initView();

    }

    private void initView() {
        TextView msgText = findViewById(R.id.msg_txt);
        TextView headingText = findViewById(R.id.heading_txt);
        TextView posBtn = findViewById(R.id.positive_btn);
        TextView negBtn = findViewById(R.id.negative_btn);
        ImageView imageView = findViewById(R.id.dialog_image);

        posBtn.setOnClickListener(this);
        negBtn.setOnClickListener(this);

        msgText.setText(msg);
        headingText.setText(head != null ? head : "");
        posBtn.setText(postText != null ? postText : "");
        negBtn.setText(negText != null ? negText : "");

        headingText.setVisibility(head != null ? View.VISIBLE : View.GONE);
        posBtn.setVisibility(postText != null ? View.VISIBLE : View.GONE);
        negBtn.setVisibility(negText != null ? View.VISIBLE : View.GONE);

        if (image != 0) {
            try {
                imageView.setImageDrawable(ContextCompat.getDrawable(context, image));
                imageView.setVisibility(View.VISIBLE);
            } catch (OutOfMemoryError w) {
                imageView.setVisibility(View.GONE);
            } catch (Exception e) {
                imageView.setVisibility(View.GONE);
            }

        } else
            imageView.setVisibility(View.GONE);

        if (msg.equalsIgnoreCase("You logged in other device. Please log in again."))
            headingText.setText("Session Expired");

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.positive_btn) {
            if (msg.equalsIgnoreCase("You logged in other device. Please log in again.")) {
                MySharedPreference.getInstance().clearSharedPrefs();
                MySharedPreference.getInstance().setBooleanData(IS_WALK_THROUGH, true);
                context.startActivity(new Intent(context, SignInActivity.class));
                ((Activity) context).finishAffinity();
            } else if (dialogCallback != null)
                dialogCallback.onPositiveClick(requestCode);
            dismiss();
        } else if (view.getId() == R.id.negative_btn) {
            if (dialogCallback != null)
                dialogCallback.onNegativeClick(requestCode);
            dismiss();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (msg.equalsIgnoreCase("Please log in again")) {
            MySharedPreference.getInstance().setBooleanData(LOGIN, false);
            ((Activity) context).finishAffinity();
        } else {
            if (dialogCallback != null)
                dialogCallback.onPositiveClick(1);
            dismiss();
        }
    }
}