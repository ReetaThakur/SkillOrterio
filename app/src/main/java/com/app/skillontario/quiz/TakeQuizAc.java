package com.app.skillontario.quiz;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.skillontario.activities.SearchActivity;
import com.app.skillontario.adapter.AdapterQuizResult;
import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.apiConnection.RequestBodyGenerator;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.models.CareerDetailModel;
import com.app.skillontario.models.CareerModal;
import com.app.skillontario.models.careerListModel.CareerListDetails;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillontario.utils.Utils;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.TakeQuizAcBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;


public class TakeQuizAc extends BaseActivity implements ApiResponseErrorCallback, AdapterQuizResult.DeleteBookMarkCall {
    private TakeQuizAcBinding binding;
    ArrayList<CareerListDetails> quizFinalResultModel = new ArrayList<>();
    AdapterQuizResult adapter;
    int position;
    AdapterQuizResult.DeleteBookMarkCall listiner;
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
        listiner = this;
        adapter = new AdapterQuizResult(TakeQuizAc.this, quizFinalResultModel, listiner);

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
            BaseResponseModel<ArrayList<CareerListDetails>> responseModel = (BaseResponseModel<ArrayList<CareerListDetails>>) responseObject;

            try {
                if (responseModel.getStatus() && responseModel.output.size() > 0) {
                    if (pageNo == 1) {
                        quizFinalResultModel.clear();

                        quizFinalResultModel.addAll(responseModel.getOutput());
                        adapter = new AdapterQuizResult(TakeQuizAc.this, quizFinalResultModel, listiner);
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
        } else if (flag == 103) {
            BaseResponseModel responseModel = (BaseResponseModel) responseObject;
            if (responseModel.getStatus()) {
                quizFinalResultModel.remove(position);
                adapter.notifyDataSetChanged();
            } else {
                showToast(responseModel.getMessage());
            }
        } else if (flag == 102) {
            try {
                BaseResponseModel<CareerDetailModel> responseModel = (BaseResponseModel<CareerDetailModel>) responseObject;
                if (responseModel.getStatus()) {
                    quizFinalResultModel.get(position).setbId(responseModel.getOutput().getId());
                    adapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void getApiError(Throwable t, int flag) {

    }


    @Override
    public void delete(int position, String Bid, String Id, boolean val) {
        if (MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.GUEST_FLOW)) {
            try {
                Utils.guestMethod(TakeQuizAc.this, "homeFragment");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.position = position;
            if (Bid.equalsIgnoreCase("")) {
                addBookmark(quizFinalResultModel.get(position));
            } else {
                removeBookmark(Bid, Id);
            }
        }
    }

    void removeBookmark(String bid, String careerId) {
        HashMap<String, Object> object = new HashMap<>();

        object.put("careerId", careerId);
        object.put("userId", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID));
        object.put("bId", bid);

        API_INTERFACE.deleteCareerBookmark(object).enqueue(
                new ApiCallBack<>(TakeQuizAc.this, this, 103, false));
    }

    void addBookmark(CareerListDetails list) {
        API_INTERFACE.addCareerBookmark(RequestBodyGenerator.setBookmark(list, MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID), list.getId())).enqueue(
                new ApiCallBack<>(TakeQuizAc.this, this, 102, false));
    }
}