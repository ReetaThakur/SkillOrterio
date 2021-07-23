package com.app.skillontario.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillontario.SignIn.ResetPasswordActivity;
import com.app.skillontario.adapter.BookmarkAdapter;
import com.app.skillontario.adapter.ScholarAdapter;
import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.apiConnection.RequestBodyGenerator;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.models.ScholarShipModal;
import com.app.skillontario.models.careerListModel.CareerListDetails;
import com.app.skillontario.requestmodal.GetEventRequest;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityHelpBinding;
import com.app.skillorterio.databinding.ScholarOneAcBinding;

import java.util.ArrayList;

import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;

public class ScholarOneAc extends BaseActivity implements ApiResponseErrorCallback {

    private ScholarOneAcBinding binding;

    //


    ApiResponseErrorCallback apiResponseErrorCallback;
    ArrayList<ScholarShipModal> scholarShipModalArrayList = new ArrayList<>();
    ScholarAdapter scholarAdapter;
    private LinearLayoutManager linearLayoutManager;
    boolean isLoading = false;
    boolean hasNext = false;
    int pageNo = 1;
    int Total_count = 20;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ScholarOneAcBinding) viewBaseBinding;
        apiResponseErrorCallback = this;
        scholarShipModalArrayList.clear();
        binding.rvItems.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(ScholarOneAc.this, LinearLayoutManager.VERTICAL, false);
        binding.rvItems.setLayoutManager(linearLayoutManager);


        setData(true);
        Peginattion();
        //binding.rclick.setOnClickListener(v-> startActivity(new Intent(this,ScholarDetailAc.class)));
    }

    private void setData(boolean cus) {
        GetEventRequest getEventRequest = new GetEventRequest(ScholarOneAc.this);
        getEventRequest.seteType("mgsafai");
        getEventRequest.setEventId("");
        getEventRequest.setPage(String.valueOf(pageNo));
        getEventRequest.setPageLimit(String.valueOf(Total_count));
        getEventRequest.setSearch("");
        CallApi(getEventRequest, 10, cus);
    }

    private void CallApi(GetEventRequest getEventRequest, int flag, boolean customeFlag) {
        API_INTERFACE.getScholarShip(RequestBodyGenerator.getEvent(getEventRequest)).enqueue(
                new ApiCallBack<>(ScholarOneAc.this, apiResponseErrorCallback, flag, customeFlag));
    }

    private void Peginattion() {
        binding.rvItems.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
                            setData(false);
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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }


    @Override
    protected int getLayoutById() {
        return R.layout.scholar_one_ac;
    }


    @Override
    public void getApiResponse(Object responseObject, int flag) {
        if (flag == 10) {
            BaseResponseModel<ArrayList<ScholarShipModal>> responseModel = (BaseResponseModel<ArrayList<ScholarShipModal>>) responseObject;
            try {

                if (responseModel.getStatus() && responseModel.output.size() > 0) {
                    if (pageNo == 1) {
                        scholarShipModalArrayList.clear();

                        scholarShipModalArrayList.addAll(responseModel.getOutput());
                        scholarAdapter = new ScholarAdapter(scholarShipModalArrayList, ScholarOneAc.this);
                        binding.rvItems.setAdapter(scholarAdapter);

                    } else {
                        scholarAdapter.addList(responseModel.getOutput());
                    }
                    isLoading = false;
                    hasNext = responseModel.hasMore;
                    binding.progress.setVisibility(View.GONE);
                    binding.llNoSclor.setVisibility(View.GONE);
                } else {

                    binding.rvItems.setVisibility(View.GONE);
                    binding.llNoSclor.setVisibility(View.VISIBLE);

                    binding.noData.ivNoData.setImageResource(R.drawable.no_data_scholarship);
                    binding.noData.tvNoData.setText(R.string.no_data_scholership);

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