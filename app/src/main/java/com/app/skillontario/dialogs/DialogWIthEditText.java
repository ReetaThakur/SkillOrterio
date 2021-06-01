package com.app.skillontario.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.app.skillontario.utils.Utils;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.DialogWithEdittextBinding;


public class DialogWIthEditText extends Dialog {

    private Context context;
    private int imageId, code;
    private String hint, heading, actionText;
    private EditDialogCallback callback;
    private DialogWithEdittextBinding binding;
    private boolean shoowCloseButton = true;

    public DialogWIthEditText(@NonNull Context context, int imageId, String hint, String heading, String actionText, EditDialogCallback callback) {
        super(context);
        this.context = context;
        this.imageId = imageId;
        this.hint = hint;
        this.heading = heading;
        this.actionText = actionText;
        this.callback = callback;
    }

    public DialogWIthEditText(@NonNull Context context, int imageId, String hint, String heading, String actionText, EditDialogCallback callback, int requestCode) {
        super(context);
        this.context = context;
        this.imageId = imageId;
        this.hint = hint;
        this.code = requestCode;
        this.heading = heading;
        this.callback = callback;
        this.actionText = actionText;
    }

    public DialogWIthEditText(@NonNull Context context, int imageId, String hint, String heading, String actionText, EditDialogCallback callback, int requestCode, boolean shoowCloseButton) {
        super(context);
        this.context = context;
        this.imageId = imageId;
        this.hint = hint;
        this.code = requestCode;
        this.heading = heading;
        this.callback = callback;
        this.actionText = actionText;
        this.shoowCloseButton = shoowCloseButton;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_with_edittext, null, false);
        setContentView(binding.getRoot());
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {
        binding.confirmHeadingTxt.setText(heading);
        binding.etMsgTxt.setHint(hint);
        binding.okBtn.setText(actionText);

        binding.ivDialogImage.setImageDrawable(ContextCompat.getDrawable(context, imageId));

        if (!shoowCloseButton) {
            binding.ivClose.setVisibility(View.INVISIBLE);
            setCanceledOnTouchOutside(false);
            setCancelable(false);
        }
        binding.ivClose.setOnClickListener(view -> dismiss());
        binding.okBtn.setOnClickListener(view -> {

            if (!binding.etMsgTxt.getText().toString().isEmpty()) {
                callback.onButtonClick(binding.etMsgTxt.getText().toString(), code);
                dismiss();
            } else {
                Utils.showToast(context, "Please provide some more detail.");
            }
        });
    }

    public interface EditDialogCallback {
        void onButtonClick(String note, int requestCode);
    }
}
