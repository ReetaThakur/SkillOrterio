package com.app.skillontario.baseClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.app.skillontario.activities.SplashActivity;
import com.app.skillontario.callbacks.XmlClickable;
import com.app.skillontario.utils.LocaleManager;
import com.app.skillontario.utils.topSnackBar.TSnackBar;
import com.app.skillorterio.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static android.content.pm.PackageManager.GET_META_DATA;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private static ProgressDialog dialog;
    protected ViewDataBinding viewBaseBinding;
    private List<Uri> mSelected;
    private XmlClickable xmlClickable;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.getInstance().setLocale(base));
    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
            Intent intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        resetTitles();
        viewBaseBinding = DataBindingUtil.setContentView(this, getLayoutById());
        backButton();
        initUi();
    }

    public void backButton(){
       ImageView ivBack=findViewById(R.id.iv_back);
       if (ivBack!=null){
           ivBack.setOnClickListener(v->finish());
       }
    }


    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        try {
            super.onRestoreInstanceState(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
            Intent intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    /**
     * Initialize ui parameters after layout inflation
     */
    protected abstract void initUi();

    /**
     * Returns the layout resource identifier
     *
     * @return Layout res id
     */
    protected abstract int getLayoutById();


    @Override
    public void onClick(View view) {

    }



    protected int getColorFromResource(int colorId) {
        return ContextCompat.getColor(this, colorId);
    }

    protected Drawable getDrawableFromResource(int drawableId) {
        return ContextCompat.getDrawable(this, drawableId);
    }

    protected String getStringFromResource(int sringId) {
        return this.getResources().getString(sringId);
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void showSuccessSnackBar(String msgStr) {
        TSnackBar.make(findViewById(android.R.id.content), msgStr, TSnackBar.LENGTH_SHORT).show();
    }

    protected void showErrorSnackBar(String msgStr) {
        TSnackBar.make(findViewById(android.R.id.content), msgStr, TSnackBar.LENGTH_SHORT, ContextCompat.getColor(this, R.color.red)).show();
    }

    public void createProgressDialog(Context context) {
        dialog = new ProgressDialog(context);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException ignored) {

        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progress_dialog);
    }

    public void dismissDialog() {
        if (!BaseActivity.this.isFinishing()) {
            if (dialog != null)
                dialog.dismiss();
        }
    }

    public void showKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(viewBaseBinding.getRoot().getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void onClickId(View view) {
        xmlClickable= BaseFragment.xmlClickable;
        xmlClickable.myClickMethod(view);
    }

    protected void resetTitles() {
        try {
            ActivityInfo info = getPackageManager().getActivityInfo(getComponentName(), GET_META_DATA);
            if (info.labelRes != 0) {
                setTitle(info.labelRes);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}