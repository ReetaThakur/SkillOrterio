package com.app.skillontario.home;

import android.content.Intent;
import android.view.View;

import com.app.skillontario.activities.ResourcesDetailsActivity;
import com.app.skillontario.baseClasses.BaseFragment;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.FragmentResourcesBinding;
import com.app.skillorterio.databinding.FragmentTab3Binding;

public class ResourcesFragment extends BaseFragment {

    private FragmentResourcesBinding binding;

    public ResourcesFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initUi() {
        binding = (FragmentResourcesBinding) viewDataBinding;

        binding.layResorce.llMain.setOnClickListener(v -> startActivity(new Intent(getActivity(), ResourcesDetailsActivity.class)));

        binding.layResorce.ll1Main.setOnClickListener(v -> startActivity(new Intent(getActivity(), ResourcesDetailsActivity.class)));

        binding.layResorce.ll2Main.setOnClickListener(v -> startActivity(new Intent(getActivity(), ResourcesDetailsActivity.class)));

        binding.layResorce.ll3Main.setOnClickListener(v -> startActivity(new Intent(getActivity(), ResourcesDetailsActivity.class)));
    }

    @Override
    protected int getLayoutById() {
        return R.layout.fragment_resources;
    }

    @Override
    public void onClick(View view) {

    }
}
