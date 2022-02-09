package com.app.skillsontario.home;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.skillsontario.R;
import com.app.skillsontario.adapter.ResourcesAdapter;
import com.app.skillsontario.apiConnection.ApiCallBack;
import com.app.skillsontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillsontario.apiConnection.RequestBodyGenerator;
import com.app.skillsontario.baseClasses.BaseFragment;
import com.app.skillsontario.baseClasses.BaseResponseModel;
import com.app.skillsontario.baseClasses.CrashLogger;
import com.app.skillsontario.constants.SharedPrefsConstants;
import com.app.skillsontario.databinding.FragmentResourcesBinding;
import com.app.skillsontario.models.ResourceModal;
import com.app.skillsontario.requestmodal.GetEventRequest;
import com.app.skillsontario.utils.MySharedPreference;


import java.util.ArrayList;

import static com.app.skillsontario.constants.ApiConstants.API_INTERFACE;

public class ResourcesFragment extends BaseFragment implements ApiResponseErrorCallback {

    private FragmentResourcesBinding binding;
    ApiResponseErrorCallback apiResponseErrorCallback;
    ResourcesAdapter resourcesAdapter;
    LinearLayoutManager linearLayoutManager;
    GetEventRequest getEventRequest;
    ArrayList<ResourceModal> resourceModalArrayList;

    boolean isLoading = false;
    boolean hasNext = false;
    int pageNo = 1;
    int total_count = 10;

    public ResourcesFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initUi() {
        apiResponseErrorCallback = this;
        getEventRequest = new GetEventRequest(getActivity());
        binding = (FragmentResourcesBinding) viewDataBinding;
        resourceModalArrayList = new ArrayList<>();

        MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.IS_HEADER, true);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rcyResoursces.setLayoutManager(linearLayoutManager);

        callNewsAPI(true);
        setPegination();
        refreshNews();

        try {
            CrashLogger.INSTANCE.trackEventsFirebase("Check_The_Resources ", "ResourcesFragment");
        }catch (Exception e){}
    }

    @Override
    public void onResume() {
        super.onResume();
        isLoading = false;
        hasNext = false;
        pageNo = 1;
        callNewsAPI(false);

    }

    void callNewsAPI(boolean cus) {
        getEventRequest.seteType("resource");
        getEventRequest.setEventId("");
        getEventRequest.setPage(String.valueOf(pageNo));
        getEventRequest.setPageLimit(String.valueOf(total_count));
        getEventRequest.setSearch("");

        API_INTERFACE.getresource(RequestBodyGenerator.getEvent(getEventRequest)).enqueue(
                new ApiCallBack<>(getActivity(), apiResponseErrorCallback, 10, cus));
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
                            callNewsAPI(false);
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
                callNewsAPI(false);
            }
        });
    }

    @Override
    protected int getLayoutById() {
        return R.layout.fragment_resources;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void getApiResponse(Object responseObject, int flag) {
        binding.refreshLayout.setRefreshing(false);
        if (flag == 10) {
            BaseResponseModel<ArrayList<ResourceModal>> responseModel = (BaseResponseModel<ArrayList<ResourceModal>>) responseObject;

            try {
                if (responseModel.getStatus() && responseModel.output.size() > 0) {
                    if (pageNo == 1) {
                        resourceModalArrayList.clear();

                        resourceModalArrayList.addAll(responseModel.getOutput());
                        resourcesAdapter = new ResourcesAdapter(resourceModalArrayList, getActivity());
                        binding.rcyResoursces.setAdapter(resourcesAdapter);

                    } else {
                        resourcesAdapter.addList(responseModel.getOutput());
                    }
                    isLoading = false;
                    hasNext = responseModel.hasMore;
                    binding.progress.setVisibility(View.GONE);
                    binding.rcyResoursces.setVisibility(View.VISIBLE);
                    binding.llNoRes.setVisibility(View.GONE);
                } else {

                    binding.rcyResoursces.setVisibility(View.GONE);
                    binding.llNoRes.setVisibility(View.VISIBLE);
                    binding.noData.ivNoData.setImageResource(R.drawable.no_data_resourse);
                    binding.noData.tvNoData.setText(getString(R.string.no_data_resoures));

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
