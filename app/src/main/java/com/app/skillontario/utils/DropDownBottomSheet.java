package com.app.skillontario.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.callbacks.OnYearSelectInterface;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ValuePickerLayoutBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;


public class DropDownBottomSheet extends BottomSheetDialogFragment implements ApiResponseErrorCallback , OnYearSelectInterface{



    private final int requestCode;
    Context context;
    OnYearSelectInterface onYearSelectInterface;

    public DropDownBottomSheet(Context context, int requestCode, OnYearSelectInterface onYearSelectInterface) {
        this.requestCode = requestCode;
        this.context = context;
        this.onYearSelectInterface = onYearSelectInterface;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*try {
            int currentOrientation = getResources().getConfiguration().orientation;
            if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                //BottomSheetBehavior.from(bottomSheetInternal).peekHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
            }
        } catch (Exception e) {
        }*/

        try {
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
                    FrameLayout bottomSheet = (FrameLayout)
                            dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                    BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    behavior.setPeekHeight(0);
                }
            });
        } catch (Exception e) {
        }


    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ValuePickerLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.value_picker_layout, container, false);

        ArrayList<String> list = new ArrayList<>();
        list.add("15-20 years");
        list.add("20-25 years");
        list.add("25-30 years");
        list.add("30-35 years");
        list.add("35-40 years");
        list.add("40-45 years");
        list.add("50-55 years");
        list.add("55-60 years");
        list.add("60-65 years");
        list.add("70-75 years");
        list.add("75-80 years");
        list.add("80-85 years");
        list.add("90-95 years");
        list.add("95-100 years");


        binding.loopview.setInitPosition(0);
        binding.loopview.setItems(list);
        try {
            if (isTablet(getContext())) {
                binding.loopview.setTextSize(56f);
            } else {

                int currentOrientation = getResources().getConfiguration().orientation;
                if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    binding.loopview.setTextSize(20f);
                    //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    binding.loopview.setTextSize(56f);
                    // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        } catch (Exception e) {
            binding.loopview.setTextSize(20f);
        }


        binding.loopview.setNotLoop();
        binding.loopview.setDividerColor(ContextCompat.getColor(getActivity(), R.color.buttonColor));


        binding.tvDone.setOnClickListener(view -> {
            if (onYearSelectInterface != null) {
                int num = binding.loopview.getSelectedItem();
                onYearSelectInterface.onTextClick(list.get(num));
            }

            dismiss();
        });

        return binding.getRoot();
    }

    @Override
    public void getApiResponse(Object responseObject, int flag) {

    }

    @Override
    public void getApiError(Throwable t, int flag) {

    }

    @Override
    public void onTextClick(String text) {

    }
}
