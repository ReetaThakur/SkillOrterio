package com.app.skillontario.home;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.app.skillontario.adapter.PopularCareerAdapter;
import com.app.skillontario.baseClasses.BaseFragment;
import com.app.skillontario.utils.RecyclerItemClickListener;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.FragmentHomeBinding;


public class HomeFragment extends BaseFragment {

    private FragmentHomeBinding binding;
    PopularCareerAdapter adapter, adapter1;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initUi() {
        binding = (FragmentHomeBinding) viewDataBinding;
        adapter = new PopularCareerAdapter(getActivity(), true);
        showPopularCareerRecycler();
        showRecentRecycler();

        binding.rlFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HomeFilterActivity.class));
            }
        });
    }


    private void showPopularCareerRecycler() {
        binding.recyPopularCareers.setHasFixedSize(true);
        adapter = new PopularCareerAdapter(getActivity(), true);
        binding.recyPopularCareers.setAdapter(adapter);

        binding.recyPopularCareers.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), (view, position) -> {

        }));
    }


    private void showRecentRecycler() {
        binding.recyRecentEvent.setHasFixedSize(true);
        adapter1 = new PopularCareerAdapter(getActivity(), false);
        binding.recyRecentEvent.setAdapter(adapter1);

        binding.recyRecentEvent.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), (view, position) -> {

        }));
    }


    @Override
    protected int getLayoutById() {
        return R.layout.fragment_home;
    }

    @Override
    public void onClick(View view) {

    }
}
