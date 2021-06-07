package com.app.skillontario.home;

import android.view.View;

import com.app.skillontario.baseClasses.BaseFragment;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.FragmentEventBinding;
import com.app.skillorterio.databinding.FragmentHomeBinding;

public class EventFragment extends BaseFragment {

    private FragmentEventBinding binding;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initUi() {
        binding = (FragmentEventBinding) viewDataBinding;
    }

    @Override
    protected int getLayoutById() {
        return R.layout.fragment_event;
    }

    @Override
    public void onClick(View view) {

    }
}