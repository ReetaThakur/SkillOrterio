package com.app.skillontario.home;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.app.skillontario.activities.JobDetailsActivity;
import com.app.skillontario.activities.NewsDetailAc;
import com.app.skillontario.activities.NotificationActivity;
import com.app.skillontario.activities.ScholarDetailAc;
import com.app.skillontario.activities.ScholarOneAc;
import com.app.skillontario.activities.SearchActivity;
import com.app.skillontario.activities.TakeQuizActivity;
import com.app.skillontario.adapter.EventAdapter;
import com.app.skillontario.adapter.PopularCareerAdapter;
import com.app.skillontario.adapter.RecentEventsAdapter;
import com.app.skillontario.adapter.RecentNewsAdapter;
import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.baseClasses.BaseFragment;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.models.CareerModal;
import com.app.skillontario.models.EventsModal;
import com.app.skillontario.models.HomeModal;
import com.app.skillontario.models.NewsModal;
import com.app.skillontario.quiz.TakeQuizAc;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillontario.utils.RecyclerItemClickListener;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.HashMap;

import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;


public class HomeFragment extends BaseFragment implements ApiResponseErrorCallback, PopularCareerAdapter.BookMarkUpdateDelete {

    private FragmentHomeBinding binding;
    PopularCareerAdapter popularCareerAdapter;
    RecentEventsAdapter eventAdapter;
    RecentNewsAdapter newsAdapter;

    // ApiResponseErrorCallback apiResponseErrorCallback;
    ArrayList<CareerModal> careerModalArrayList = new ArrayList<>();
    ArrayList<NewsModal> newsModalArrayList = new ArrayList<>();
    ArrayList<EventsModal> eventsModalArrayList = new ArrayList<>();

    PopularCareerAdapter.BookMarkUpdateDelete bookMarkUpdateDelete;
    boolean User_Type = false;
    int CareerPosition;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initUi() {
        binding = (FragmentHomeBinding) viewDataBinding;

        bookMarkUpdateDelete = this;
        careerModalArrayList.clear();
        newsModalArrayList.clear();
        eventsModalArrayList.clear();
        MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.IS_HEADER, true);

        callAPI();

        showPopularCareerRecycler();
        showRecentRecycler();
        showRecentNewsRecycler();

        binding.imgExpolre.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ScholarOneAc.class));
        });

        binding.rlTakeQuiz.setOnClickListener(v -> startActivity(new Intent(getActivity(), TakeQuizActivity.class)));

        binding.rlFilter.setOnClickListener(v -> startActivity(new Intent(getActivity(), HomeFilterActivity.class)));

        binding.imgNotification.setOnClickListener(v -> startActivity(new Intent(getActivity(), NotificationActivity.class)));

        binding.rlSearch.setOnClickListener(v -> startActivity(new Intent(getActivity(), SearchActivity.class)));

    }

    void callAPI() {

        HashMap<String, Object> object = new HashMap<>();
        object.put("userId", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID));

        API_INTERFACE.getHomeData(object).enqueue(
                new ApiCallBack<>(getActivity(), this, 12, true));
    }


    private void showPopularCareerRecycler() {
        binding.recyPopularCareers.setHasFixedSize(true);
        popularCareerAdapter = new PopularCareerAdapter(getActivity(), true);
        binding.recyPopularCareers.setAdapter(popularCareerAdapter);

    }


    private void showRecentRecycler() {
        binding.recyRecentEvent.setHasFixedSize(true);
        eventAdapter = new RecentEventsAdapter(getActivity());
        binding.recyRecentEvent.setAdapter(eventAdapter);
    }

    private void showRecentNewsRecycler() {
        binding.recyNews.setHasFixedSize(true);
        newsAdapter = new RecentNewsAdapter(getActivity(), false);
        binding.recyNews.setAdapter(newsAdapter);

       /* binding.recyNews.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), (view, position) -> {
            startActivity(new Intent(getActivity(), NewsDetailAc.class));
        }));*/
    }


    @Override
    protected int getLayoutById() {
        return R.layout.fragment_home;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void getApiResponse(Object responseObject, int flag) {
        if (flag == 12) {

            try {
                if (careerModalArrayList != null) {
                    careerModalArrayList.clear();
                }
                if (newsModalArrayList != null) {
                    newsModalArrayList.clear();
                }
                if (eventsModalArrayList != null) {
                    eventsModalArrayList.clear();
                }

                BaseResponseModel<ArrayList<HomeModal>> responseModel = (BaseResponseModel<ArrayList<HomeModal>>) responseObject;
                if (responseModel.getStatus()) {
                    if (responseModel.getOutput() != null) {
                        if (responseModel.getOutput().size() > 0) {
                            if (responseModel.getOutput().get(0).getCareerData() != null) {
                                if (responseModel.getOutput().get(0).getCareerData().size() > 0) {
                                    careerModalArrayList.addAll(responseModel.getOutput().get(0).getCareerData());
                                    popularCareerAdapter = new PopularCareerAdapter(careerModalArrayList, getActivity(), false, bookMarkUpdateDelete);
                                    binding.recyPopularCareers.setAdapter(popularCareerAdapter);
                                }
                            }
                            if (responseModel.getOutput().get(1).getEventData() != null) {
                                if (responseModel.getOutput().get(1).getEventData().size() > 0) {
                                    eventsModalArrayList.addAll(responseModel.getOutput().get(1).getEventData());
                                      eventAdapter = new RecentEventsAdapter(eventsModalArrayList, getActivity());
                                      binding.recyRecentEvent.setAdapter(eventAdapter);
                                }
                            }
                            if (responseModel.getOutput().get(2).getNewsData() != null) {
                                if (responseModel.getOutput().get(2).getNewsData().size() > 0 && responseModel.getOutput().get(2).getNewsData() != null) {
                                    newsModalArrayList.addAll(responseModel.getOutput().get(2).getNewsData());
                                    //recentNewsAdapter = new RecentNewsAdapter(newsModalArrayList, getActivity());
                                    //binding.recyNews.setAdapter(recentNewsAdapter);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
            }

        }
    }

    @Override
    public void getApiError(Throwable t, int flag) {

    }

    @Override
    public void checkBookMark(String Bid, int position, String careerId) {

    }
}
