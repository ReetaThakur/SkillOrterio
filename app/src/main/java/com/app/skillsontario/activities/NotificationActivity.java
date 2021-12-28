package com.app.skillsontario.activities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;

import com.app.skillsontario.R;
import com.app.skillsontario.adapter.NotificationAdapter;
import com.app.skillsontario.apiConnection.ApiCallBack;
import com.app.skillsontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillsontario.baseClasses.BaseActivity;
import com.app.skillsontario.baseClasses.BaseResponseModel;
import com.app.skillsontario.constants.SharedPrefsConstants;
import com.app.skillsontario.databinding.ActivityNotificationBinding;
import com.app.skillsontario.home.HomeFragment;
import com.app.skillsontario.models.NotificationModal;
import com.app.skillsontario.utils.MySharedPreference;


import java.util.ArrayList;
import java.util.HashMap;

import static com.app.skillsontario.constants.ApiConstants.API_INTERFACE;
import static com.app.skillsontario.constants.AppConstants.NOTIFICATION_COUNT;

public class NotificationActivity extends BaseActivity implements ApiResponseErrorCallback {

    private ActivityNotificationBinding binding;
    private NotificationAdapter adapter;
    ArrayList<NotificationModal> arrayListNotify;

    boolean isLoading = false;
    boolean hasNext = false;
    int pageNo = 1;
    int total_count = 10;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityNotificationBinding) viewBaseBinding;
        arrayListNotify = new ArrayList<>();
        binding.actionBar.tvTitle.setText(R.string.notification);
        linearLayoutManager = new LinearLayoutManager(NotificationActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.rcyNotification.setLayoutManager(linearLayoutManager);


        binding.actionBar.ivBack.setOnClickListener(v -> onBackPressed());
        try {
            HomeFragment.tvNotificationCount.setVisibility(View.GONE);
        } catch (Exception e) {
        }

        MySharedPreference.getInstance().setStringData(NOTIFICATION_COUNT, "0");
        apiCall(true);
        pegination();
        refreshNews();

    }


    void refreshNews() {
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.refreshLayout.setRefreshing(true);
                isLoading = false;
                hasNext = false;
                pageNo = 1;
                apiCall(false);
            }
        });
    }

    private void apiCall(boolean cus) {
        HashMap<String, Object> object = new HashMap<>();
        object.put("userId", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID));
        object.put("page", String.valueOf(pageNo));
        object.put("pageLimit", String.valueOf(total_count));
        API_INTERFACE.getNitification(object).enqueue(
                new ApiCallBack<>(NotificationActivity.this, this, 105, cus));
    }


    private void pegination() {
        binding.rcyNotification.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                    if (!isLoading && hasNext) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                            binding.progress.setVisibility(View.VISIBLE);
                            isLoading = true;
                            ++pageNo;
                            apiCall(false);
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
        return R.layout.activity_notification;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }

    @Override
    public void getApiResponse(Object responseObject, int flag) {
        binding.refreshLayout.setRefreshing(false);
        if (flag == 105) {
            BaseResponseModel<ArrayList<NotificationModal>> responseModel = (BaseResponseModel<ArrayList<NotificationModal>>) responseObject;
            try {
                if (responseModel.getStatus() && responseModel.output.size() > 0) {
                    if (pageNo == 1) {
                        arrayListNotify.clear();

                        arrayListNotify.addAll(responseModel.getOutput());
                        adapter = new NotificationAdapter(arrayListNotify, NotificationActivity.this);
                        binding.rcyNotification.setAdapter(adapter);

                    } else {
                        adapter.addList(responseModel.getOutput());
                    }
                    try {
                        binding.tvNotiCount.setText(" " + responseModel.getTotalCount() + " ");
                    } catch (Exception ee) {
                    }
                    isLoading = false;
                    hasNext = responseModel.hasMore;
                    binding.progress.setVisibility(View.GONE);
                    binding.rcyNotification.setVisibility(View.VISIBLE);
                    binding.llNoRes.setVisibility(View.GONE);
                } else {
                    try {
                        binding.tvNotiCount.setText(" 0 ");
                    } catch (Exception ee) {
                    }

                    binding.rcyNotification.setVisibility(View.GONE);
                    binding.llNoRes.setVisibility(View.VISIBLE);
                    binding.noData.ivNoData.setImageResource(R.drawable.no_data_notification);
                    binding.noData.tvNoData.setText(getString(R.string.no_data_notification));

                }
            } catch (Exception e) {
                binding.progress.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void getApiError(Throwable t, int flag) {

    }
}