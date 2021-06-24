package com.app.skillontario.activities;

import android.content.Intent;

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
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivitySearchBinding;

import java.util.ArrayList;

import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;


public class SearchActivity extends BaseActivity implements ApiResponseErrorCallback {

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

        //callApiList(pageNo);
       // setPagination();
    }

    void callApiList(int pageNo) {
        API_INTERFACE.getCareerList(RequestBodyGenerator.setCareerList(1)).enqueue(
                new ApiCallBack<>(SearchActivity.this, this, 9691, true));
    }

    private void showSearchRecycler() {
        ArrayList<CareerListDetails> list = new ArrayList<>();
        binding.recySearch.setHasFixedSize(true);
        adapter = new SearchAdapter(SearchActivity.this, list);
        binding.recySearch.setAdapter(adapter);

      /*  binding.recySearch.addOnItemTouchListener(new RecyclerItemClickListener(SearchActivity.this, (view, position) -> {

        }));*/
    }

    private void setPagination() {
        binding.recySearch.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                    if (!isLoading && hasNext) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                            isLoading = true;
                            ++pageNo;
                            callApiList(pageNo);
                        }
                    }
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
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

    @Override
    public void getApiResponse(Object responseObject, int flag) {
        if (flag == 9691) {
            BaseResponseModel<ArrayList<CareerListDetails>> responseModel = (BaseResponseModel<ArrayList<CareerListDetails>>) responseObject;
            // responseModel.getOutput().get(0).

            if (responseModel.getStatus()) {
                if (pageNo == 1) {
                    ArrayList<CareerListDetails> list = new ArrayList<>();
                    list.clear();
                    try {
                        if (responseModel.output != null) {
                            if (responseModel.getOutput().size() > 0)
                                list = responseModel.output;
                        }

                    } catch (Exception e) {
                    }


                    adapter = new SearchAdapter(SearchActivity.this, list);
                    binding.recySearch.setAdapter(adapter);
                } else {
                    ArrayList<CareerListDetails> list = new ArrayList<>();
                    try {
                        if (responseModel.output != null)
                            list = responseModel.output;
                    } catch (Exception e) {
                    }


                    adapter.addList(list);
                }
                isLoading = false;
                hasNext = responseModel.hasMore;
                hasNext = true;


            }
        }
    }

    @Override
    public void getApiError(Throwable t, int flag) {

    }
}