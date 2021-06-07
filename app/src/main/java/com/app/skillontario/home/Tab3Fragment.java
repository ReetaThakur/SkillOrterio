package com.app.skillontario.home;

import android.view.View;

import com.app.skillontario.baseClasses.BaseFragment;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.FragmentHomeBinding;
import com.app.skillorterio.databinding.FragmentTab3Binding;

public class Tab3Fragment extends BaseFragment {

    private FragmentTab3Binding binding;

    public Tab3Fragment() {
        // Required empty public constructor
    }


    @Override
    protected void initUi() {
        binding = (FragmentTab3Binding) viewDataBinding;
    }

    @Override
    protected int getLayoutById() {
        return R.layout.fragment_tab3;
    }

    @Override
    public void onClick(View view) {

    }
}
