package com.app.skillsontario.home;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.skillsontario.R;
import com.app.skillsontario.adapter.EventAdapter;
import com.app.skillsontario.adapter.NewsAdapter;
import com.app.skillsontario.apiConnection.ApiCallBack;
import com.app.skillsontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillsontario.apiConnection.RequestBodyGenerator;
import com.app.skillsontario.baseClasses.BaseFragment;
import com.app.skillsontario.baseClasses.BaseResponseModel;
import com.app.skillsontario.callbacks.CheckPermission;
import com.app.skillsontario.callbacks.XmlClickable;
import com.app.skillsontario.constants.SharedPrefsConstants;
import com.app.skillsontario.databinding.FragmentEventBinding;
import com.app.skillsontario.models.EventsModal;
import com.app.skillsontario.models.NewsModal;
import com.app.skillsontario.requestmodal.GetEventRequest;
import com.app.skillsontario.utils.MySharedPreference;


import java.util.ArrayList;

import static com.app.skillsontario.constants.ApiConstants.API_INTERFACE;

public class EventFragment extends BaseFragment implements XmlClickable, ApiResponseErrorCallback, CheckPermission {

    private static final Object REQUEST_LOCATION = 100;
    private FragmentEventBinding binding;

    int news_Total_count = 20;

    LinearLayoutManager linearLayoutManager, linearLayoutManagernew;
    GetEventRequest getEventRequest, getEventRequestNews;
    ApiResponseErrorCallback apiResponseErrorCallback;
    ArrayList<EventsModal> eventsModalArrayList;
    ArrayList<NewsModal> newsModalArrayList;
    EventAdapter eventAdapter;
    NewsAdapter newsAdapter;
    CheckPermission checkPermission;

    boolean isLoading = false;
    boolean hasNext = false;
    int pageNoNews = 1;

    boolean isLoading1 = false;
    boolean hasNext1 = false;
    int pageNoNews1 = 1;

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initUi() {
        binding = (FragmentEventBinding) viewDataBinding;
        setPhase(this);
        checkPermission = this;
        getEventRequest = new GetEventRequest(getActivity());
        getEventRequestNews = new GetEventRequest(getActivity());
        apiResponseErrorCallback = this;
        eventsModalArrayList = new ArrayList<>();
        newsModalArrayList = new ArrayList<>();

        binding.tab.tv1.setOnClickListener(v -> {
            //    setTint(binding.tab.tv1, R.color.buttonColor);
            //   setTint(binding.tab.tv2, R.color.white);
            binding.eventL.setVisibility(View.VISIBLE);
            binding.newsL.setVisibility(View.GONE);

            binding.tab.tv1.setBackgroundResource(R.drawable.rec_blue_left);
            binding.tab.tv1.setTextColor(Color.WHITE);

            binding.tab.tv2.setBackgroundResource(R.drawable.rec_white_right);
            binding.tab.tv2.setTextColor(Color.BLACK);
        });
        binding.tab.tv2.setOnClickListener(v -> {
            //  setTint(binding.tab.tv1, R.color.white);
            //  setTint(binding.tab.tv2, R.color.buttonColor);
            binding.newsL.setVisibility(View.VISIBLE);
            binding.eventL.setVisibility(View.GONE);

            binding.tab.tv1.setBackgroundResource(R.drawable.rec_left_white);
            binding.tab.tv1.setTextColor(Color.BLACK);

            binding.tab.tv2.setBackgroundResource(R.drawable.rec_blue_right);
            binding.tab.tv2.setTextColor(Color.WHITE);
        });
        MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.IS_HEADER, true);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.recyRecentEvent.setLayoutManager(linearLayoutManager);

        linearLayoutManagernew = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rcyNews.setLayoutManager(linearLayoutManagernew);

        callEventApi(true);
        callNewsAPI(true);
        pegination();
        refreshEvent();
        refreshNews();
    }

    @Override
    public void onResume() {
        super.onResume();

        isLoading1 = false;
        hasNext1 = false;
        pageNoNews1 = 1;

        isLoading = false;
        hasNext = false;
        pageNoNews = 1;
        callNewsAPI(false);
        callEventApi(false);

        try {
            Intent intent = getActivity().getIntent();
            String id = intent.getStringExtra("iff");
            if (id.equalsIgnoreCase("news")) {
                binding.newsL.setVisibility(View.VISIBLE);
                binding.eventL.setVisibility(View.GONE);

                binding.tab.tv1.setBackgroundResource(R.drawable.rec_left_white);
                binding.tab.tv1.setTextColor(Color.BLACK);

                binding.tab.tv2.setBackgroundResource(R.drawable.rec_blue_right);
                binding.tab.tv2.setTextColor(Color.WHITE);
            }
        } catch (Exception e) {
        }


    }

    void refreshEvent() {
        binding.refreshLayoutEvent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.refreshLayoutEvent.setRefreshing(true);
                isLoading1 = false;
                hasNext1 = false;
                pageNoNews1 = 1;
                callEventApi(false);
            }
        });
    }

    void refreshNews() {
        binding.refreshLayoutNews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.refreshLayoutNews.setRefreshing(true);
                isLoading = false;
                hasNext = false;
                pageNoNews = 1;
                callNewsAPI(false);
            }
        });
    }


    private void pegination() {
        binding.recyRecentEvent.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                    if (!isLoading1 && hasNext1) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                            binding.progress1.setVisibility(View.VISIBLE);
                            isLoading1 = true;
                            ++pageNoNews1;
                            callEventApi(false);
                        }
                    }
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        binding.rcyNews.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = linearLayoutManagernew.getChildCount();
                    int totalItemCount = linearLayoutManagernew.getItemCount();
                    int firstVisibleItemPosition = linearLayoutManagernew.findFirstVisibleItemPosition();

                    if (!isLoading && hasNext) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                            binding.progress.setVisibility(View.VISIBLE);
                            isLoading = true;
                            ++pageNoNews;
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


    void callNewsAPI(boolean custome) {
        GetEventRequest getEventRequest1 = new GetEventRequest(getActivity());
        getEventRequest1.seteType("news");
        getEventRequest1.setEventId("");
        getEventRequest1.setPage(String.valueOf(pageNoNews));
        getEventRequest1.setPageLimit(String.valueOf(news_Total_count));
        getEventRequest1.setSearch("");
        API_INTERFACE.getNews(RequestBodyGenerator.getEvent(getEventRequest1)).enqueue(
                new ApiCallBack<>(getActivity(), apiResponseErrorCallback, 1012, custome));
    }

    void callEventApi(boolean custome) {
        getEventRequest.seteType("event");
        getEventRequest.setEventId("");
        getEventRequest.setPage(String.valueOf(pageNoNews1));
        getEventRequest.setPageLimit(String.valueOf(news_Total_count));
        getEventRequest.setSearch("");
        API_INTERFACE.getevent(RequestBodyGenerator.getEvent(getEventRequest)).enqueue(
                new ApiCallBack<>(getActivity(), apiResponseErrorCallback, 1013, custome));
    }

    private void openD() {
        Dialog dialogMood = new Dialog(getActivity());
        dialogMood.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogMood.setCancelable(true);
        if (dialogMood.getWindow() != null) {
            dialogMood.getWindow()
                    .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialogMood.setContentView(R.layout.news_dialog);
        dialogMood.findViewById(R.id.done).setOnClickListener(view1 -> dialogMood.dismiss());
        dialogMood.show();
    }


    private void setTint(TextView imageView, int color_) {
        DrawableCompat.setTint(imageView.getBackground(), ContextCompat.getColor(getActivity(), color_));
        if (R.color.buttonColor == color_)
            imageView.setTextColor(getResources().getColor(R.color.white));
        else
            imageView.setTextColor(getResources().getColor(R.color.black));
    }


    @Override
    protected int getLayoutById() {
        return R.layout.fragment_event;
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void myClickMethod(View v) {
       /* if (v.getId() == R.id.ivCal) {
            //openD();
        } else if (v.getId() == R.id.row) {
           // startActivity(new Intent(getActivity(), NewsDetailAc.class));
        } else if (v.getId() == R.id.eventRow) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.skillsontario.com")));
        }*/
    }


    @Override
    public void getApiResponse(Object responseObject, int flag) {
        binding.refreshLayoutEvent.setRefreshing(false);
        binding.refreshLayoutNews.setRefreshing(false);

        if (flag == 1012) {
            BaseResponseModel<ArrayList<NewsModal>> responseModel = (BaseResponseModel<ArrayList<NewsModal>>) responseObject;
            try {
                if (responseModel.getStatus() && responseModel.output.size() > 0) {
                    if (pageNoNews == 1) {
                        newsModalArrayList.clear();

                        newsModalArrayList.addAll(responseModel.getOutput());
                        newsAdapter = new NewsAdapter(newsModalArrayList, getActivity());
                        binding.rcyNews.setAdapter(newsAdapter);

                    } else {
                        newsAdapter.addList(responseModel.getOutput());
                    }
                    isLoading = false;
                    hasNext = responseModel.hasMore;
                    binding.progress.setVisibility(View.GONE);
                    binding.rcyNews.setVisibility(View.VISIBLE);
                    binding.llNoNews.setVisibility(View.GONE);
                } else {

                    binding.rcyNews.setVisibility(View.GONE);
                    binding.llNoNews.setVisibility(View.VISIBLE);
                    binding.noDataNew.ivNoData.setImageResource(R.drawable.no_data_news);
                    binding.noDataNew.tvNoData.setText(getString(R.string.no_data_news));

                }
            } catch (Exception e) {
                binding.progress.setVisibility(View.GONE);
            }
        } else if (flag == 1013) {
            BaseResponseModel<ArrayList<EventsModal>> responseModel = (BaseResponseModel<ArrayList<EventsModal>>) responseObject;
            try {
                if (responseModel.getStatus() && responseModel.output.size() > 0) {
                    if (pageNoNews1 == 1) {
                        eventsModalArrayList.clear();

                        eventsModalArrayList.addAll(responseModel.getOutput());
                        eventAdapter = new EventAdapter(eventsModalArrayList, getActivity(), checkPermission);
                        binding.recyRecentEvent.setAdapter(eventAdapter);

                    } else {
                        eventAdapter.addList(responseModel.getOutput());
                    }
                    isLoading1 = false;
                    hasNext1 = responseModel.hasMore;
                    binding.progress1.setVisibility(View.GONE);
                    binding.llNoEvent.setVisibility(View.GONE);
                    binding.recyRecentEvent.setVisibility(View.VISIBLE);
                } else {

                    binding.recyRecentEvent.setVisibility(View.GONE);
                    binding.llNoEvent.setVisibility(View.VISIBLE);
                    binding.noDataEvent.ivNoData.setImageResource(R.drawable.no_data_events);
                    binding.noDataEvent.tvNoData.setText(getString(R.string.no_data_events));

                }
            } catch (Exception e) {
                binding.progress1.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void getApiError(Throwable t, int flag) {
        binding.progress1.setVisibility(View.GONE);
        binding.progress.setVisibility(View.GONE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCheck() {

        if (Build.VERSION.SDK_INT >= 23) {
            int hasPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CALENDAR);
            int hasPermission1 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALENDAR);

            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                showDialog();
                return;
               /* if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CALENDAR)) {

                    showDialog();
                    return;
                } else {
                    getActivity().requestPermissions(new String[]{Manifest.permission.WRITE_CALENDAR}, 100);

                }*/
                //return;
            }

            if (hasPermission1 != PackageManager.PERMISSION_GRANTED) {
                showDialog();
               /* if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CALENDAR)) {

                    return;
                } else {
                    getActivity().requestPermissions(new String[]{Manifest.permission.READ_CALENDAR}, 200);

                }
*/
            }


        }

    }

    void showDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.permi)
                .setMessage(R.string.scc)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, 1);
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}