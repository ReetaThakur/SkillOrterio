package com.app.skillontario.quiz;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.skillontario.SignIn.WelcomeActivity;
import com.app.skillontario.adapter.AdapterCong;
import com.app.skillontario.adapter.ResourcesAdapter;
import com.app.skillontario.adapter.TabAdapter;
import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.models.quizModel.QuizResultModel;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.TakeQuizAcBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;
import static com.app.skillontario.quiz.QuizStepAc.quizFinalResultModel;

public class TakeQuizAc extends BaseActivity implements ApiResponseErrorCallback {
    private TakeQuizAcBinding binding;
    ArrayList<QuizResultModel> quizFinalResultModel = new ArrayList<>();
    AdapterCong adapter;

    boolean isLoading = false;
    boolean hasNext = false;
    int pageNo = 1;
    int total_count = 20;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (TakeQuizAcBinding) viewBaseBinding;
        binding.actionBarL.tvTitle.setText(getResources().getText(R.string.take_qz_ttl));
        adapter = new AdapterCong(TakeQuizAc.this, quizFinalResultModel);

        MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.IS_HEADER, true);
        linearLayoutManager = new LinearLayoutManager(TakeQuizAc.this, LinearLayoutManager.VERTICAL, false);
        binding.rcyResoursces.setLayoutManager(linearLayoutManager);

        binding.done.setOnClickListener(v -> startActivity(new Intent(this, QuizStepAc.class)));
        callFinalApi(true);
        setPegination();
        refreshNews();
        noDataSet();
    }

    private void noDataSet() {
        binding.noData.ivNoData.setImageResource(R.drawable.ic_take_quiz);
        binding.noData.tvNoData.setText(getResources().getText(R.string.take_qz));
        binding.noData.tvNoData.setTextColor(getResources().getColor(R.color.black));
    }

    private void setPegination() {
        binding.rcyResoursces.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
                            callFinalApi(false);
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


    void refreshNews() {
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.refreshLayout.setRefreshing(true);
                isLoading = false;
                hasNext = false;
                pageNo = 1;
                callFinalApi(false);
            }
        });
    }

    @Override
    protected int getLayoutById() {
        return R.layout.take_quiz_ac;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
    }

    void callFinalApi(boolean cus) {
        HashMap<String, Object> object = new HashMap<>();
        object.put("userId", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID));
        object.put("pageLimit", total_count);
        object.put("page", pageNo);


        API_INTERFACE.getQuizResultList(object).enqueue(
                new ApiCallBack<>(TakeQuizAc.this, this, 108, cus));

    }

    @Override
    public void getApiResponse(Object responseObject, int flag) {
        binding.refreshLayout.setRefreshing(false);
        if (flag == 108) {
            BaseResponseModel<ArrayList<QuizResultModel>> responseModel = (BaseResponseModel<ArrayList<QuizResultModel>>) responseObject;

            try {
                if (responseModel.getStatus() && responseModel.output.size() > 0) {
                    if (pageNo == 1) {
                        quizFinalResultModel.clear();

                        quizFinalResultModel.addAll(responseModel.getOutput());
                        adapter = new AdapterCong(TakeQuizAc.this, quizFinalResultModel);
                        binding.rcyResoursces.setAdapter(adapter);

                    } else {
                        adapter.addList(responseModel.getOutput());
                    }
                    isLoading = false;
                    hasNext = responseModel.hasMore;
                    binding.progress.setVisibility(View.GONE);

                    binding.noDataShow.setVisibility(View.GONE);
                    binding.newsL.setVisibility(View.VISIBLE);
                } else {
                    binding.progress.setVisibility(View.GONE);
                    binding.newsL.setVisibility(View.GONE);
                    binding.noDataShow.setVisibility(View.VISIBLE);

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