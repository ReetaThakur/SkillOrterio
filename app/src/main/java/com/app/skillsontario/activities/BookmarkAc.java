package com.app.skillsontario.activities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;

import com.app.skillsontario.R;
import com.app.skillsontario.adapter.BookmarkAdapter;
import com.app.skillsontario.apiConnection.ApiCallBack;
import com.app.skillsontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillsontario.apiConnection.RequestBodyGenerator;
import com.app.skillsontario.baseClasses.BaseActivity;
import com.app.skillsontario.baseClasses.BaseResponseModel;
import com.app.skillsontario.constants.SharedPrefsConstants;
import com.app.skillsontario.databinding.BookmarkAcBinding;
import com.app.skillsontario.models.careerListModel.CareerListDetails;
import com.app.skillsontario.utils.MySharedPreference;
import com.app.skillsontario.utils.Utils;


import java.util.ArrayList;
import java.util.HashMap;

import static com.app.skillsontario.constants.ApiConstants.API_INTERFACE;

public class BookmarkAc extends BaseActivity implements ApiResponseErrorCallback, BookmarkAdapter.DeleteBookMark {
    private BookmarkAcBinding binding;
    ApiResponseErrorCallback apiResponseErrorCallback;
    ArrayList<CareerListDetails> careerListDetails;

    BookmarkAdapter bookmarkAdapter;
    BookmarkAdapter.DeleteBookMark deleteBookMark;
    int position;
    private LinearLayoutManager linearLayoutManager;

    boolean isLoading = false;
    boolean hasNext = false;
    int pageNo = 1;
    int Total_count = 10;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (BookmarkAcBinding) viewBaseBinding;
        deleteBookMark = this;
        binding.actionBar.tvTitle.setText(R.string.bookmarked);
        binding.actionBar.ivBack.setOnClickListener(v -> onBackPressed());
        apiResponseErrorCallback = this;
        careerListDetails = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(BookmarkAc.this, LinearLayoutManager.VERTICAL, false);
        binding.rcyBookmark.setLayoutManager(linearLayoutManager);


        callBookMarkList(true);
        Peginattion();
        refreshBookmark();
    }

    void refreshBookmark() {
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.refreshLayout.setRefreshing(true);
                isLoading = false;
                hasNext = false;
                pageNo = 1;
                callBookMarkList(false);
            }
        });
    }

    private void Peginattion() {
        binding.rcyBookmark.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
                            callBookMarkList(false);
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

    void callBookMarkList(boolean progress) {
        API_INTERFACE.getBookMarkList(RequestBodyGenerator.getBookMark(MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID), pageNo, Total_count)).enqueue(
                new ApiCallBack<>(BookmarkAc.this, apiResponseErrorCallback, 101, progress));
    }


    void removeBookmark(String bid, String careerId) {
        HashMap<String, Object> object = new HashMap<>();

        object.put("careerId", careerId);
        object.put("userId", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID));
        object.put("bId", bid);

        API_INTERFACE.deleteCareerBookmark(object).enqueue(
                new ApiCallBack<>(BookmarkAc.this, this, 103, false));
    }

    @Override
    protected int getLayoutById() {
        return R.layout.bookmark_ac;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
    }

    @Override
    public void getApiResponse(Object responseObject, int flag) {
        binding.refreshLayout.setRefreshing(false);
        if (flag == 103) {
            BaseResponseModel responseModel = (BaseResponseModel) responseObject;
            if (responseModel.getStatus()) {
                careerListDetails.remove(position);
                bookmarkAdapter.notifyDataSetChanged();
            } else {
                showToast(responseModel.getMessage());
            }
        } else if (flag == 101) {
            BaseResponseModel<ArrayList<CareerListDetails>> responseModel = (BaseResponseModel<ArrayList<CareerListDetails>>) responseObject;
            try {

                if (responseModel.getStatus() && responseModel.output.size() > 0) {
                    if (pageNo == 1) {
                        careerListDetails.clear();

                        careerListDetails.addAll(responseModel.getOutput());
                        bookmarkAdapter = new BookmarkAdapter(BookmarkAc.this, careerListDetails, deleteBookMark);
                        binding.rcyBookmark.setAdapter(bookmarkAdapter);

                    } else {
                        bookmarkAdapter.addList(responseModel.getOutput());
                    }
                    isLoading = false;
                    hasNext = responseModel.hasMore;
                    binding.progress.setVisibility(View.GONE);
                    binding.llNoRes.setVisibility(View.GONE);
                    binding.rcyBookmark.setVisibility(View.VISIBLE);
                } else {

                    binding.rcyBookmark.setVisibility(View.GONE);
                    binding.llNoRes.setVisibility(View.VISIBLE);

                    binding.noData.ivNoData.setImageResource(R.drawable.no_data_bookmarked);
                    binding.noData.tvNoData.setText(R.string.no_bookmark);

                }
            } catch (Exception e) {
                binding.progress.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void getApiError(Throwable t, int flag) {
        binding.progress.setVisibility(View.GONE);
    }

    @Override
    public void delete(int position, String Bid, String Id) {

        if (!MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.GUEST_FLOW)) {
            this.position = position;
            removeBookmark(Bid, Id);
        } else {
            try {
                Utils.guestMethod(BookmarkAc.this, "BookmarkAc");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}