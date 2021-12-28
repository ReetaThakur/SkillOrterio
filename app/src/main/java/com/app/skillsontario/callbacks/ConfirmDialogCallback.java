package com.app.skillsontario.callbacks;

public interface ConfirmDialogCallback {
    void onPositiveClick(int requestCode);
    void onNegativeClick(int requestCode);
}