package com.app.skillontario.home;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.app.skillontario.activities.JobDetailsActivity;
import com.app.skillontario.activities.NewsDetailAc;
import com.app.skillontario.activities.NotificationActivity;
import com.app.skillontario.activities.ScholarDetailAc;
import com.app.skillontario.activities.ScholarOneAc;
import com.app.skillontario.activities.SearchActivity;
import com.app.skillontario.adapter.PopularCareerAdapter;
import com.app.skillontario.adapter.RecentEventsAdapter;
import com.app.skillontario.adapter.RecentNewsAdapter;
import com.app.skillontario.baseClasses.BaseFragment;
import com.app.skillontario.quiz.TakeQuizAc;
import com.app.skillontario.utils.RecyclerItemClickListener;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.FragmentHomeBinding;


public class HomeFragment extends BaseFragment {

    private FragmentHomeBinding binding;
    PopularCareerAdapter adapter;
    RecentEventsAdapter adapter1;
    RecentNewsAdapter adapter2;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initUi() {
        binding = (FragmentHomeBinding) viewDataBinding;
        adapter = new PopularCareerAdapter(getActivity(), true);
        showPopularCareerRecycler();
        showRecentRecycler();
        showRecentNewsRecycler();

        binding.imgExpolre.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ScholarOneAc.class));
        });

        binding.rlTakeQuiz.setOnClickListener(v -> startActivity(new Intent(getActivity(), TakeQuizAc.class)));

        binding.rlFilter.setOnClickListener(v -> startActivity(new Intent(getActivity(), HomeFilterActivity.class)));

        binding.imgNotification.setOnClickListener(v -> startActivity(new Intent(getActivity(), NotificationActivity.class)));

        binding.rlSearch.setOnClickListener(v -> startActivity(new Intent(getActivity(), SearchActivity.class)));
    }


    private void showPopularCareerRecycler() {
        binding.recyPopularCareers.setHasFixedSize(true);
        adapter = new PopularCareerAdapter(getActivity(), true);
        binding.recyPopularCareers.setAdapter(adapter);

        binding.recyPopularCareers.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), (view, position) -> {
            startActivity(new Intent(getActivity(), JobDetailsActivity.class));
        }));
    }


    private void showRecentRecycler() {
        binding.recyRecentEvent.setHasFixedSize(true);
        adapter1 = new RecentEventsAdapter(getActivity(), false);
        binding.recyRecentEvent.setAdapter(adapter1);

        binding.recyRecentEvent.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), (view, position) -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.skillsontario.com")));
        }));
    }

    private void showRecentNewsRecycler() {
        binding.recyNews.setHasFixedSize(true);
        adapter2 = new RecentNewsAdapter(getActivity(), false);
        binding.recyNews.setAdapter(adapter2);

        binding.recyNews.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), (view, position) -> {
            startActivity(new Intent(getActivity(), NewsDetailAc.class));
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
