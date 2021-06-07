package com.app.skillontario.home;

import android.view.View;

import com.app.skillontario.baseClasses.BaseFragment;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.FragmentTab3Binding;
import com.app.skillorterio.databinding.FragmentTab4Binding;

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
