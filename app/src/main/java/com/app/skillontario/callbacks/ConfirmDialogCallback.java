package com.app.skillontario.callbacks;

public interface ConfirmDialogCallback {
    void onPositiveClick(int requestCode);
    void onNegativeClick(int requestCode);
}