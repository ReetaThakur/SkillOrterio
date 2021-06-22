package com.app.skillontario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.skillontario.adapter.NotificationAdapter;
import com.app.skillontario.adapter.SearchAdapter;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.utils.RecyclerItemClickListener;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityNotificationBinding;
import com.app.skillorterio.databinding.ActivitySearchBinding;

public class SearchActivity extends BaseActivity {

    private ActivitySearchBinding binding;
    private SearchAdapter adapter;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivitySearchBinding) viewBaseBinding;

        binding.actionBar.tvTitle.setText("Search");
        showSearchRecycler();

        binding.actionBar.ivBack.setOnClickListener(v -> onBackPressed());

    }

    private void showSearchRecycler() {
        binding.recySearch.setHasFixedSize(true);
        adapter = new SearchAdapter(SearchActivity.this);
        binding.recySearch.setAdapter(adapter);

      /*  binding.recySearch.addOnItemTouchListener(new RecyclerItemClickListener(SearchActivity.this, (view, position) -> {

        }));*/
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_search;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }
}