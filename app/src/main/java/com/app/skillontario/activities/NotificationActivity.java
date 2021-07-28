package com.app.skillontario.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;

import com.app.skillontario.adapter.NotificationAdapter;
import com.app.skillontario.adapter.PopularCareerAdapter;
import com.app.skillontario.adapter.ResourcesAdapter;
import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.home.HomeFragment;
import com.app.skillontario.models.NotificationModal;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillontario.utils.RecyclerItemClickListener;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityNotificationBinding;
import com.app.skillorterio.databinding.ActivitySettingBinding;

import java.util.ArrayList;
import java.util.HashMap;

import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;
import static com.app.skillontario.constants.AppConstants.NOTIFICATION_COUNT;

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

        //  HomeFragment.notification_badge.setVisibility(View.GONE);
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
                        binding.tvNotiCount.setText(" " + responseModel.getTotalCount()+" ");
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