package com.app.skillontario.baseClasses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.app.skillorterio.R;

import org.jetbrains.annotations.NotNull;


public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected View view;
    protected Context mContext;
    protected ViewDataBinding viewDataBinding;
    private static ProgressDialog dialog;

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutById(), container, false);
            if (viewDataBinding != null) {
                return viewDataBinding.getRoot();
            } else {
                return inflater.inflate(getLayoutById(), container, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return inflater.inflate(getLayoutById(), container, false);
        }
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        initUi();
    }

    /**
     * Method to find view by id, just like activities findViewById
     * @param resId Resource id to fine
     * @return View instance of res id
     */
    protected View findViewById(int resId) {
        return view.findViewById(resId);
    }

    /**
     * Initialize ui parameters after layout inflation
     */
    protected abstract void initUi();

    /**
     * Returns the layout resource identifier
     * @return Layout res id
     */
    protected abstract int getLayoutById();

    public void createProgressDialog(Context context, String msg) {
        dialog = new ProgressDialog(context);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException ignored) {

        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progress_dialog);
        if (msg!= null)
            dialog.setMessage(msg);
    }

    public void dismissDialog(){
        if (!((Activity)mContext).isFinishing()){
            if (dialog!= null)
                dialog.dismiss();
        }
    }

    protected String getStringFromResource(int id){
        return mContext.getResources().getString(id);
    }

    protected void showToast(String msg){
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();

    }

}