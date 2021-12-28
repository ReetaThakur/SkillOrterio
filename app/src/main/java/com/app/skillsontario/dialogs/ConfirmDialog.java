package com.app.skillsontario.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.app.skillsontario.R;
import com.app.skillsontario.callbacks.ConfirmDialogCallback;


import static com.app.skillsontario.constants.AppConstants.LOGOUT;


public class ConfirmDialog extends Dialog implements View.OnClickListener {
    private String msg;
    private String negativeText;
    private String positiveText;
    private ConfirmDialogCallback dialogCallback;
    private int flag;

    public ConfirmDialog(@NonNull Context context, String msg, String negativeText, String positiveText, ConfirmDialogCallback dialogCallback, int flag) {
        super(context);
        this.msg = msg;
        this.negativeText = negativeText;
        this.positiveText = positiveText;
        this.dialogCallback=dialogCallback;
        this.flag=flag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_confirm);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        TextView confirm_msg_txt = findViewById(R.id.confirm_msg_txt);
        TextView cancel_btn = findViewById(R.id.cancel_btn);
        TextView ok_btn = findViewById(R.id.ok_btn);


        confirm_msg_txt.setText(msg);
        cancel_btn.setText(negativeText);
        ok_btn.setText(positiveText);
        cancel_btn.setOnClickListener(this);
        ok_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok_btn:
                if (flag == LOGOUT) {
                    dialogCallback.onPositiveClick(LOGOUT);
                    dismiss();
                }else {
                    dialogCallback.onPositiveClick(flag);
                    dismiss();
                }

                break;

            case R.id.cancel_btn:
                dismiss();
                break;
        }

    }
}