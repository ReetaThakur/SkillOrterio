package com.app.skillsontario.home;

import android.view.View;

import com.app.skillsontario.R;
import com.app.skillsontario.baseClasses.BaseFragment;
import com.app.skillsontario.databinding.FragmentTab4Binding;


public class Tab4Fragment extends BaseFragment {

    private FragmentTab4Binding binding;

    public Tab4Fragment() {
        // Required empty public constructor
    }


    @Override
    protected void initUi() {
        binding = (FragmentTab4Binding) viewDataBinding;
    }

    @Override
    protected int getLayoutById() {
        return R.layout.fragment_tab4;
    }

    @Override
    public void onClick(View view) {

    }
}
