package com.app.skillontario.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.app.skillontario.callbacks.GetClickBookmark;
import com.app.skillontario.models.CareerDetailModel;
import com.app.skillontario.models.EducationModal;
import com.app.skillontario.models.SectorModal;
import com.app.skillorterio.R;
import com.app.skillontario.adapter.SearchAdapter;
import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.apiConnection.RequestBodyGenerator;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillorterio.databinding.ActivitySearchBinding;
import com.app.skillontario.home.HomeFilterActivity;
import com.app.skillontario.models.careerListModel.CareerListDetails;
import com.app.skillontario.utils.MySharedPreference;

import java.util.ArrayList;


import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;


public class SearchActivity extends BaseActivity implements ApiResponseErrorCallback, GetClickBookmark {

    private ActivitySearchBinding binding;
    private SearchAdapter adapter;
    GetClickBookmark getClickBookmark;
    boolean isLoading = false, hasNext = false;
    LinearLayoutManager linearLayoutManager;
    int pageNo = 1;

    String searchString = "";
    String sector = "";
    String education = "";
    String redFlag = "";
    String ApplyFilter = "";
    String UserId;
    String search="";
    ArrayList<CareerListDetails> list;
    int position;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivitySearchBinding) viewBaseBinding;
        CareerListDetails CareerListDetails = new CareerListDetails();
        UserId = MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID);
        MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.IS_HEADER, true);
        binding.actionBar.tvTitle.setText(R.string.search);
        getClickBookmark = this;
        list = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        binding.recySearch.setLayoutManager(linearLayoutManager);
        showSearchRecycler();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sector = getIntent().getStringExtra("sector");
            education = getIntent().getStringExtra("education");
            redFlag = getIntent().getStringExtra("redFlag");
            ApplyFilter = getIntent().getStringExtra("ApplyFilter");
            search = getIntent().getStringExtra("search");
        }
        binding.actionBar.ivBack.setOnClickListener(v -> onBackPressed());
      /*  binding.rlFilter.setOnClickListener(v -> {
            startActivity(new Intent(SearchActivity.this, HomeFilterActivity.class));
        });*/
        binding.rlFilter.setOnClickListener(v -> {
            Intent intent=new Intent(SearchActivity.this, HomeFilterActivity.class);
            intent.putExtra("search",binding.etSearch.getText().toString().trim());
            finish();
            startActivity(intent);

        });
        if (ApplyFilter.equalsIgnoreCase("Apply")) {
            callApiListFilter(pageNo, UserId, sector, education, redFlag,search);
        } else {
            callApiList(UserId, pageNo);
        }

        setPagination();
        search();
        refreshBookmark();
    }

    void callApiList(String userId, int pageNo) {
        API_INTERFACE.getCareerList(RequestBodyGenerator.setCareerList(userId, 1, "")).enqueue(
                new ApiCallBack<>(SearchActivity.this, this, 9691, true));
    }

    void callApiListFilter(int pageNo, String userId, String sector, String education, String redFlag,String search) {
        API_INTERFACE.getCareerList(RequestBodyGenerator.setCareerFilter(pageNo, UserId, sector, education, redFlag,search)).enqueue(
                new ApiCallBack<>(SearchActivity.this, this, 1000, true));
    }

    void callApiList(int pageNo, boolean progress) {
        API_INTERFACE.getCareerList(RequestBodyGenerator.setCareerList(UserId, pageNo, "")).enqueue(
                new ApiCallBack<>(SearchActivity.this, this, 9691, progress));
    }

    void callSearchApiList(int pageNo, String searchString) {
        API_INTERFACE.getCareerList(RequestBodyGenerator.setCareerList(UserId, pageNo, searchString)).enqueue(
                new ApiCallBack<>(SearchActivity.this, this, 9692, true));
    }

    private void showSearchRecycler() {
        ArrayList<CareerListDetails> list = new ArrayList<>();
        binding.recySearch.setHasFixedSize(true);
        adapter = new SearchAdapter(SearchActivity.this, list, getClickBookmark);
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
                            if (ApplyFilter.equalsIgnoreCase("Apply")) {
                                callApiListFilter(pageNo, UserId, sector, education, redFlag,search);
                            } else {
                                callApiList(pageNo, false);
                            }
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

    void refreshBookmark() {
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.refreshLayout.setRefreshing(true);
                isLoading = false;
                hasNext = false;
                pageNo = 1;
                //setData(false);
                if (ApplyFilter.equalsIgnoreCase("Apply")) {
                    callApiListFilter(pageNo, UserId, sector, education, redFlag,search);
                } else {
                    callApiList(pageNo, false);
                }
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
        binding.refreshLayout.setRefreshing(false);
        if (flag == 9691 || flag == 9692) {
            BaseResponseModel<ArrayList<CareerListDetails>> responseModel = (BaseResponseModel<ArrayList<CareerListDetails>>) responseObject;
            // responseModel.getData().get(0).

            if (responseModel.getStatus()) {
                if (pageNo == 1) {
                    list.clear();
                    try {
                        if (responseModel.getOutput() != null) {
                            if (responseModel.getOutput().size() > 0)
                                list = responseModel.output;
                        }
                    } catch (Exception e) {
                    }
                    if(list.size()>0){
                        binding.llNodata.setVisibility(View.GONE);
                        adapter = new SearchAdapter(SearchActivity.this, list, this);
                        binding.recySearch.setAdapter(adapter);
                    }else {
                       // binding.noData.ivNoData.setBackgroundResource(R.drawable.no_data_bookmarked);
                        binding.noData.tvNoData.setText(R.string.no_data_search);
                        binding.llNodata.setVisibility(View.VISIBLE);
                    }

                } else {
                    try {
                        if (responseModel.output != null)
                            list = responseModel.output;
                    } catch (Exception e) {
                    }
                    adapter.addList(list);
                }
                isLoading = false;
                hasNext = responseModel.hasMore;
            }
        } else if (flag == 1000) {
            BaseResponseModel<ArrayList<CareerListDetails>> responseModel = (BaseResponseModel<ArrayList<CareerListDetails>>) responseObject;
            if (responseModel.getStatus()) {
                if (pageNo == 1) {
                    list.clear();
                    try {
                        if (responseModel.output != null) {
                            if (responseModel.getOutput().size() > 0)
                                list = responseModel.output;
                        }

                    } catch (Exception e) {
                    }
                    if(list.size()>0) {
                        binding.llNodata.setVisibility(View.GONE);
                        adapter = new SearchAdapter(SearchActivity.this, list, this);
                        binding.recySearch.setAdapter(adapter);
                    }else {
                       // binding.noData.ivNoData.setBackgroundResource(R.drawable.no_data_bookmarked);
                        binding.noData.tvNoData.setText(R.string.no_data_search);
                        binding.llNodata.setVisibility(View.VISIBLE);
                    }
                } else {
                    try {
                        if (responseModel.output != null)
                            list = responseModel.output;
                    } catch (Exception e) {
                    }
                    adapter.addList(list);
                }
                isLoading = false;
                hasNext = responseModel.hasMore;
                //hasNext = true;


            }
        } else if (flag == 103){
            BaseResponseModel responseModel = (BaseResponseModel)responseObject;
            if(responseModel.getStatus()) {
                list.get(position).setbId("");
                adapter.notifyDataSetChanged();
            }else {
                showToast(responseModel.getMessage());
            }
        }else if (flag == 102) {
            BaseResponseModel<CareerDetailModel> responseModel = (BaseResponseModel<CareerDetailModel>) responseObject;
            if (responseModel.getStatus()) {
                list.get(position).setbId(responseModel.getOutput().getId());
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void getApiError(Throwable t, int flag) {

    }

  /*  @Override
    public void getApiError500(String msg, int flag) {

    }*/

    void search() {
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            private Timer timer = new Timer();
            private final long DELAY = 2000; // milliseconds
            @Override
            public void afterTextChanged(Editable s) {
                //------------------
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                mHandler.obtainMessage(1).sendToTarget();
                            }
                        },
                        DELAY
                );
            }
        });
    }

    @Override
    public void getValueBookmarkClick(boolean value,final CareerListDetails list,final  int Position1) {
        this.position=Position1;
        if (list.getbId().equalsIgnoreCase("")) { /// add bookmarl
            addBookmark(list);
        } else {  /// remove bookmark
            removeBookmark(list);
        }
    }

    void addBookmark(CareerListDetails list) {
        API_INTERFACE.addCareerBookmark(RequestBodyGenerator.setBookmark(list, MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID), list.getId())).enqueue(
                new ApiCallBack<>(SearchActivity.this, this, 102, false));
    }

    void removeBookmark(CareerListDetails list) {
        HashMap<String, Object> object = new HashMap<>();

        object.put("careerId", list.getId());
        object.put("userId", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID));
        object.put("bId", list.getbId());

        API_INTERFACE.deleteCareerBookmark(object).enqueue(
                new ApiCallBack<>(SearchActivity.this, this, 103, false));
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

            try {
                if (binding.etSearch.getText().toString().length() > 2) {
                    pageNo = 1;
                    isLoading = false;
                    if (adapter != null)
                        adapter.clearList();
                    if (searchString == null) {
                        searchString = "";
                    }
                    if (ApplyFilter.equalsIgnoreCase("Apply")) {
                        callApiListFilter(pageNo, UserId, sector, education, redFlag,binding.etSearch.getText().toString());
                    } else {
                        callSearchApiList(pageNo, binding.etSearch.getText().toString());
                    }
                }
                else {
                    if (binding.etSearch.getText().toString().equals("")){
                        if (adapter != null)
                            adapter.clearList();
                        searchString = "";

                        if (ApplyFilter.equalsIgnoreCase("Apply")) {
                            callApiListFilter(pageNo, UserId, sector, education, redFlag,binding.etSearch.getText().toString());
                        } else {
                            callSearchApiList(pageNo, binding.etSearch.getText().toString());
                        }
                    }
                }

            } catch (Exception ex) {

            }
        }
    };
}