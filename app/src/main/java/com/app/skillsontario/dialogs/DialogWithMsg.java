package com.app.skillsontario.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.app.skillsontario.R;
import com.app.skillsontario.callbacks.ConfirmDialogCallback;
import com.app.skillsontario.databinding.DialogWithMsgBinding;


public class DialogWithMsg extends Dialog {

    private Context context;
    private int imageId, requestCode = 0;
    private String heading, content, actionMsg;
    private ConfirmDialogCallback callback;
    private DialogWithMsgBinding binding;

    public DialogWithMsg(@NonNull Context context, int imageId, String heading, String content,
                         String actionMsg, ConfirmDialogCallback callback) {
        super(context);
        this.context = context;
        this.imageId = imageId;
        this.heading = heading;
        this.content = content;
        this.actionMsg = actionMsg;
        this.callback = callback;
        requestCode = 0;
    }

    public DialogWithMsg(@NonNull Context context, int imageId, String heading, String content,
                         String actionMsg, ConfirmDialogCallback callback, int requestCode) {
        super(context);
        this.context = context;
        this.imageId = imageId;
        this.heading = heading;
        this.content = content;
        this.actionMsg = actionMsg;
        this.callback = callback;
        this.requestCode = requestCode;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_with_msg, null, false);
        setContentView(binding.getRoot());
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        initView();
    }

    private void initView() {
        if (requestCode > 0)
            binding.confirmHeadingTxt.setVisibility(View.VISIBLE);
        else
            binding.confirmHeadingTxt.setVisibility(View.GONE);


        binding.ivClose.setVisibility(View.GONE);
        binding.confirmHeadingTxt.setText(heading);
        binding.confirmMsgTxt.setText(Html.fromHtml(content));


        if (requestCode == 9) {
            binding.okBtn.setText(R.string.signup);
            binding.tvPayNow1.setVisibility(View.VISIBLE);
        } else {
            binding.okBtn.setText(actionMsg);
            binding.tvPayNow1.setVisibility(View.GONE);
        }



        if (imageId != 0)
            binding.ivDialogImage.setImageDrawable(ContextCompat.getDrawable(context, imageId));

        binding.ivClose.setOnClickListener(v -> {
          /*  try {
                if (requestCode == 9)
                    callback.onNegativeClick(requestCode);
            } catch (Exception e) {
            }*/


            dismiss();
        });

        binding.continueAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (requestCode == 9)
                        callback.onNegativeClick(requestCode);
                } catch (Exception e) {
                }
                dismiss();
            }
        });

        binding.okBtn.setOnClickListener(view -> {
            try {
                if (requestCode == 9)
                    callback.onPositiveClick(requestCode);
            } catch (Exception e) {
            }

            dismiss();
        });
    }
}
