package com.app.skillontario.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.skillontario.BottomBarActivity;
import com.app.skillontario.activities.NewsDetailAc;
import com.app.skillontario.adapter.EventAdapter;
import com.app.skillontario.adapter.NewsAdapter;
import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.apiConnection.RequestBodyGenerator;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.baseClasses.BaseFragment;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.callbacks.XmlClickable;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.models.EventsModal;
import com.app.skillontario.models.NewsModal;
import com.app.skillontario.models.RegistrationModal;
import com.app.skillontario.requestmodal.GetEventRequest;
import com.app.skillontario.signup.SignUpActivity;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillontario.utils.Utils;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.FragmentEventBinding;
import com.app.skillorterio.databinding.FragmentHomeBinding;
import com.bumptech.glide.Glide;
import com.github.pwittchen.infinitescroll.library.InfiniteScrollListener;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;
import static com.app.skillontario.utils.Utils.getDeviceId;

public class EventFragment extends BaseFragment implements XmlClickable, ApiResponseErrorCallback {

    private FragmentEventBinding binding;
    int Total_count = 6;
    int page = 1;
    int new_Total_count = 6;
    int new_page = 1;
    LinearLayoutManager linearLayoutManager,linearLayoutManagernew;
    GetEventRequest getEventRequest,getEventRequestNews;
    ApiResponseErrorCallback apiResponseErrorCallback;
    ArrayList<EventsModal> eventsModalArrayList;
    ArrayList<NewsModal> newsModalArrayList;
    EventAdapter eventAdapter;
    NewsAdapter newsAdapter;
    boolean hasMore = true;
    boolean nextdata = false;

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initUi() {
        binding = (FragmentEventBinding) viewDataBinding;
        setPhase(this);
        getEventRequest = new GetEventRequest(getActivity());
        getEventRequestNews = new GetEventRequest(getActivity());
        apiResponseErrorCallback = this;
        eventsModalArrayList = new ArrayList<>();
        newsModalArrayList = new ArrayList<>();
        binding.tab.tv1.setOnClickListener(v -> {
            setTint(binding.tab.tv1, R.color.buttonColor);
            setTint(binding.tab.tv2, R.color.white);
            binding.eventL.setVisibility(View.VISIBLE);
            binding.newsL.setVisibility(View.GONE);
        });
        binding.tab.tv2.setOnClickListener(v -> {
            setTint(binding.tab.tv1, R.color.white);
            setTint(binding.tab.tv2, R.color.buttonColor);
            binding.newsL.setVisibility(View.VISIBLE);
            binding.eventL.setVisibility(View.GONE);
        });
        MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.IS_HEADER, true);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.recyRecentEvent.setLayoutManager(linearLayoutManager);
        binding.recyRecentEvent.addOnScrollListener(createInfiniteScrollListener());

        linearLayoutManagernew = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rcyNews.setLayoutManager(linearLayoutManagernew);
        binding.rcyNews.addOnScrollListener(createInfiniteScrollListenerNews());

        getEventRequest.seteType("event");
        getEventRequest.setEventId("");
        getEventRequest.setPage(String.valueOf(page));
        getEventRequest.setPageLimit(String.valueOf(Total_count));
        getEventRequest.setSearch("");
        API_INTERFACE.getevent(RequestBodyGenerator.getEvent(getEventRequest)).enqueue(
                new ApiCallBack<>(getActivity(), apiResponseErrorCallback, 10, true));

        getEventRequestNews.seteType("news");
        getEventRequestNews.setEventId("");
        getEventRequestNews.setPage(String.valueOf(new_page));
        getEventRequestNews.setPageLimit(String.valueOf(new_Total_count));
        getEventRequestNews.setSearch("");
        API_INTERFACE.getNews(RequestBodyGenerator.getEvent(getEventRequestNews)).enqueue(
                new ApiCallBack<>(getActivity(), apiResponseErrorCallback, 12, false));

    }

    private InfiniteScrollListener createInfiniteScrollListener() {
        return new InfiniteScrollListener(Total_count, (LinearLayoutManager) binding.recyRecentEvent.getLayoutManager()) {
            @Override
            public void onScrolledToEnd(final int firstVisibleItemPosition) {
                // load your items here
                // logic of loading items will be different depending on your specific use case
                // when new items are loaded, combine old and new items, pass them to your adapter
                // and call refreshView(...) method from InfiniteScrollListener class to refresh RecyclerView
                //  refreshView(recyclerView, new MyAdapter(items), firstVisibleItemPosition);

                if (hasMore && nextdata) {
                    nextdata = false;
                    hasMore = false;
                    GetEventRequest  getEventRequest1 = new GetEventRequest(getActivity());
                    getEventRequest1.seteType("event");
                    getEventRequest1.setEventId("");
                    getEventRequest1.setPage(String.valueOf(page));
                    getEventRequest1.setPageLimit(String.valueOf(Total_count));
                    getEventRequest1.setSearch("");
                    API_INTERFACE.registerUser(RequestBodyGenerator.getEvent(getEventRequest1)).enqueue(
                            new ApiCallBack<>(getActivity(), apiResponseErrorCallback, 11, true));

                } else {
                    nextdata = true;
                }
            }
        };
    }
    private InfiniteScrollListener createInfiniteScrollListenerNews() {
        return new InfiniteScrollListener(Total_count, (LinearLayoutManager) binding.recyRecentEvent.getLayoutManager()) {
            @Override
            public void onScrolledToEnd(final int firstVisibleItemPosition) {
                // load your items here
                // logic of loading items will be different depending on your specific use case
                // when new items are loaded, combine old and new items, pass them to your adapter
                // and call refreshView(...) method from InfiniteScrollListener class to refresh RecyclerView
                //  refreshView(recyclerView, new MyAdapter(items), firstVisibleItemPosition);

              /*  if (hasMore && nextdata) {
                    nextdata = false;
                    hasMore = false;
                    GetEventRequest  getEventRequest1 = new GetEventRequest(getActivity());
                    getEventRequest1.seteType("news");
                    getEventRequest1.setEventId("");
                    getEventRequest1.setPage(String.valueOf(new_page));
                    getEventRequest1.setPageLimit(String.valueOf(new_Total_count));
                    getEventRequest1.setSearch("");
                    API_INTERFACE.registerUser(RequestBodyGenerator.getEvent(getEventRequest1)).enqueue(
                            new ApiCallBack<>(getActivity(), apiResponseErrorCallback, 11, true));

                } else {
                    nextdata = true;
                }*/
            }
        };
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
        if (v.getId() == R.id.ivCal) {
            openD();
        } else if (v.getId() == R.id.row) {
            startActivity(new Intent(getActivity(), NewsDetailAc.class));
        } else if (v.getId() == R.id.eventRow) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.skillsontario.com")));
        }
    }


    @Override
    public void getApiResponse(Object responseObject, int flag) {
        if (flag == 10) {
            BaseResponseModel<ArrayList<EventsModal>> responseModel = (BaseResponseModel<ArrayList<EventsModal>>) responseObject;
            if (page == 1) {
                eventsModalArrayList.clear();
            }
            if (responseModel.getStatus()==200) {
                if(responseModel.getOutput().size()>0&&responseModel.getOutput()!=null) {
                    eventsModalArrayList.addAll(responseModel.getOutput());
                    eventAdapter = new EventAdapter(eventsModalArrayList, getActivity());
                    binding.recyRecentEvent.setAdapter(eventAdapter);
                    page = page + 1;
                }
               // hasMore = responseModel.isHasMore();
            } else {
                Utils.showToast(getActivity(), responseModel.getMessage());
            }
        } else if (flag == 11) {
            BaseResponseModel<ArrayList<EventsModal>> responseModel = (BaseResponseModel<ArrayList<EventsModal>>) responseObject;
            if (responseModel.getStatus()==200) {
                if(responseModel.getOutput().size()>0&&responseModel.getOutput()!=null) {
                    eventsModalArrayList.addAll(responseModel.getOutput());
                    eventAdapter.notifyDataSetChanged();
                    page = page + 1;
                }
               // hasMore = responseModel.isHasMore();
            } else {
                Utils.showToast(getActivity(), responseModel.getMessage());
            }
        }else if(flag==12){
            BaseResponseModel<ArrayList<NewsModal>> responseModel = (BaseResponseModel<ArrayList<NewsModal>>) responseObject;
            if (new_page == 1) {
                newsModalArrayList.clear();
            }
            if (responseModel.getStatus()==200) {
                if(responseModel.getOutput().size()>0&&responseModel.getOutput()!=null) {
                    newsModalArrayList.addAll(responseModel.getOutput());
                    newsAdapter = new NewsAdapter(newsModalArrayList, getActivity());
                    binding.rcyNews.setAdapter(newsAdapter);
                    new_page = new_page++;
                }
               // hasMore = responseModel.isHasMore();
            } else {
                Utils.showToast(getActivity(), responseModel.getMessage());
            }
        }
    }

    @Override
    public void getApiError(Throwable t, int flag) {

    }
}