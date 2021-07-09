package com.app.skillontario.activities;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillontario.adapter.SearchAdapter;
import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.apiConnection.RequestBodyGenerator;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.home.HomeFilterActivity;
import com.app.skillontario.models.careerListModel.CareerListDetails;
import com.app.skillontario.utils.Utils;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivitySearchBinding;

import java.util.ArrayList;

import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;


public class SearchActivity extends BaseActivity  {

    private ActivitySearchBinding binding;
    private SearchAdapter adapter;

    boolean isLoading = false, hasNext = false;
    LinearLayoutManager linearLayoutManager;
    int pageNo = 1;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivitySearchBinding) viewBaseBinding;

        binding.actionBar.tvTitle.setText("Search");
        linearLayoutManager = new LinearLayoutManager(this);
        binding.recySearch.setLayoutManager(linearLayoutManager);
        showSearchRecycler();

        binding.actionBar.ivBack.setOnClickListener(v -> onBackPressed());
        binding.rlFilter.setOnClickListener(v -> {
            startActivity(new Intent(SearchActivity.this, HomeFilterActivity.class));
        });

        binding.im.setOnClickListener(v -> {
            try {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            } catch (Exception e) {
            }
        });

        binding.tvS.setOnClickListener(v -> {
            try {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            } catch (Exception e) {
            }
        });


    }



    private void showSearchRecycler() {
        ArrayList<CareerListDetails> list = new ArrayList<>();
        binding.recySearch.setHasFixedSize(true);
        adapter = new SearchAdapter(SearchActivity.this, list);
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