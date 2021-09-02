package com.app.skillontario.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;


import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.skillontario.activities.NotificationActivity;

import com.app.skillontario.activities.ScholarOneAc;
import com.app.skillontario.activities.SearchActivity;
import com.app.skillontario.activities.TakeQuizActivity;
import com.app.skillontario.adapter.PopularCareerAdapter;
import com.app.skillontario.adapter.RecentEventsAdapter;
import com.app.skillontario.adapter.RecentNewsAdapter;
import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.apiConnection.RequestBodyGenerator;
import com.app.skillontario.baseClasses.BaseFragment;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.callbacks.CheckPermission;
import com.app.skillontario.callbacks.ConfirmDialogCallback;
import com.app.skillontario.constants.AppConstants;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.dialogs.DialogWithMsg;
import com.app.skillontario.models.CareerDetailModel;
import com.app.skillontario.models.CareerModal;
import com.app.skillontario.models.EventsModal;
import com.app.skillontario.models.HomeModal;
import com.app.skillontario.models.NewsModal;
import com.app.skillontario.quiz.QuizAc;
import com.app.skillontario.signup.SignUpActivity;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillontario.utils.Utils;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.HashMap;

import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;
import static com.app.skillontario.constants.AppConstants.NOTIFICATION_COUNT;
import static com.app.skillontario.utils.Utils.updatLocalLanguage;


public class HomeFragment extends BaseFragment implements ApiResponseErrorCallback, PopularCareerAdapter.BookMarkUpdateDelete, CheckPermission, ConfirmDialogCallback {

    private FragmentHomeBinding binding;
    PopularCareerAdapter popularCareerAdapter;
    RecentEventsAdapter eventAdapter;
    RecentNewsAdapter recentNewsAdapter;
    public static TextView tvNotificationCount;
    CheckPermission checkPermission;
    ConfirmDialogCallback callback;

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
        tvNotificationCount = (TextView) findViewById(R.id.tv_notification_count);
        checkPermission = this;
        callback = this;

        bookMarkUpdateDelete = this;
        careerModalArrayList.clear();
        newsModalArrayList.clear();
        eventsModalArrayList.clear();
        MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.IS_HEADER, true);

        callAPI(true);


        showPopularCareerRecycler();
        showRecentRecycler();
        showRecentNewsRecycler();
        refreshBookmark();

        binding.imgExpolre.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ScholarOneAc.class));
        });

        binding.rlTakeQuiz.setOnClickListener(v -> {
            //  startActivity(new Intent(getActivity(), TakeQuizActivity.class));

            if (!MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.GUEST_FLOW)) {
                startActivity(new Intent(getActivity(), TakeQuizActivity.class));
            } else {
                showDialogMessage();
                /*try {
                    Utils.guestMethod(getActivity(), "homeFragment");
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }

        });


        binding.rlFilter.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), HomeFilterActivity.class);
            intent.putExtra("search", "");
            startActivity(intent);
        });

        //   binding.imgNotification.setOnClickListener(v -> startActivity(new Intent(getActivity(), NotificationActivity.class)));

        binding.rlSearch.setOnClickListener(v -> startActivity(new Intent(getActivity(), SearchActivity.class)));

        binding.imgNotification.setOnClickListener(v -> {
            // User_Type = MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.GUEST_FLOW);


            HashMap<String, Object> object = new HashMap<>();
            object.put("userId", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID));
            API_INTERFACE.readNitification(object).enqueue(
                    new ApiCallBack<>(getActivity(), this, 105, true));

            startActivity(new Intent(getActivity(), NotificationActivity.class));


        });
    }

    void refreshBookmark() {
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.refreshLayout.setRefreshing(true);
                callAPI(false);
            }
        });
    }

    void callAPI(boolean c) {

        HashMap<String, Object> object = new HashMap<>();
        object.put("userId", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID));

        API_INTERFACE.getHomeData(object).enqueue(
                new ApiCallBack<>(getActivity(), this, 12, c));
    }


    private void showPopularCareerRecycler() {
        binding.recyPopularCareers.setHasFixedSize(true);
        popularCareerAdapter = new PopularCareerAdapter(getActivity(), true);
        binding.recyPopularCareers.setAdapter(popularCareerAdapter);

    }


    private void showRecentRecycler() {
        binding.recyRecentEvent.setHasFixedSize(true);
        eventAdapter = new RecentEventsAdapter(getActivity());
        // binding.recyRecentEvent.setAdapter(eventAdapter);
    }

    private void showRecentNewsRecycler() {
        binding.recyNews.setHasFixedSize(true);
        recentNewsAdapter = new RecentNewsAdapter(getActivity(), false);
        binding.recyNews.setAdapter(recentNewsAdapter);

       /* binding.recyNews.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), (view, position) -> {
            startActivity(new Intent(getActivity(), NewsDetailAc.class));
        }));*/
    }

    void showDialogMessage() {
        try {
            DialogWithMsg dialogWithMsg = new DialogWithMsg(getActivity(), 0, getString(R.string.app_name), getString(R.string.sign_result1), getString(R.string.okay), callback, 9);
            dialogWithMsg.show();
        } catch (Exception e) {
        }
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
        binding.refreshLayout.setRefreshing(false);
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
                                    eventAdapter = new RecentEventsAdapter(eventsModalArrayList, getActivity(), checkPermission);
                                    binding.recyRecentEvent.setAdapter(eventAdapter);
                                }
                            }
                            if (responseModel.getOutput().get(2).getNewsData() != null) {
                                if (responseModel.getOutput().get(2).getNewsData().size() > 0 && responseModel.getOutput().get(2).getNewsData() != null) {
                                    newsModalArrayList.addAll(responseModel.getOutput().get(2).getNewsData());
                                    recentNewsAdapter = new RecentNewsAdapter(newsModalArrayList, getActivity());
                                    binding.recyNews.setAdapter(recentNewsAdapter);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
            }

        } else if (flag == 103) {
            try {
                BaseResponseModel responseModel = (BaseResponseModel) responseObject;
                if (responseModel.getStatus()) {
                    careerModalArrayList.get(CareerPosition).setbId("");
                    popularCareerAdapter.notifyDataSetChanged();
                } else {
                    showToast(responseModel.getMessage());
                }
            } catch (Exception e) {
            }

        } else if (flag == 102) {
            try {
                BaseResponseModel<CareerDetailModel> responseModel = (BaseResponseModel<CareerDetailModel>) responseObject;
                if (responseModel.getStatus()) {
                    careerModalArrayList.get(CareerPosition).setbId(responseModel.getOutput().getId());
                    popularCareerAdapter.notifyDataSetChanged();
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
        if (MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.GUEST_FLOW)) {
            try {
                Utils.guestMethod(getActivity(), "homeFragment");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            CareerPosition = position;
            if (Bid.equalsIgnoreCase("")) {
                addBookmark(careerModalArrayList.get(position), careerId);
            } else {
                removeBookmark(Bid, careerId);
            }
        }
    }

    void addBookmark(CareerModal list, String careerId) {
        API_INTERFACE.addCareerBookmark(RequestBodyGenerator.setBookmarkadd(list, MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID), careerId)).enqueue(
                new ApiCallBack<>(getActivity(), this, 102, false));
    }

    void removeBookmark(String bid, String careerId) {
        HashMap<String, Object> object = new HashMap<>();

        object.put("careerId", careerId);
        object.put("userId", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID));
        object.put("bId", bid);

        API_INTERFACE.deleteCareerBookmark(object).enqueue(
                new ApiCallBack<>(getActivity(), this, 103, false));
    }

    @Override
    public void onResume() {
        super.onResume();
        setText();
        callAPI(false);
    }

    void setText() {

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    int count = Integer.parseInt(MySharedPreference.getInstance().getStringData(NOTIFICATION_COUNT));
                    if (count > 0) {
                        binding.tvNotificationCount.setText("" + count);
                    } else {
                        binding.tvNotificationCount.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                }
                try {
                    languageMethod(MySharedPreference.getInstance().getStringData(AppConstants.LANGUAGE));
                    binding.tvFindDream.setText(R.string.find_your_dream_career);
                    binding.tvSearch.setText(R.string.Search_careers);
                    binding.tvPopular.setText(R.string.Popular_Careers);
                    binding.tv.setText(R.string.discover_a_suitable_career);
                    binding.tvTakeQuiz.setText(R.string.take_quiz);
                    binding.tvRecentEvent.setText(R.string.Recent_Events);
                    binding.tvExplore.setText(R.string.explore);
                    binding.tvOpp.setText(R.string.opportunities_amp_scholarships);
                    binding.tvRecentNews.setText(R.string.Recent_News);
                } catch (Exception e) {
                }
            }
        }, 70);
    }

    private void languageMethod(String lang) {

        if (lang != null) {
            if (lang.isEmpty()) {

                updatLocalLanguage("en", getActivity());

            } else {

                updatLocalLanguage(lang, getActivity());
            }
        } else {

            updatLocalLanguage("en", getActivity());
        }
    }

    @Override
    public void onCheck() {

        if (Build.VERSION.SDK_INT >= 23) {
            int hasPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CALENDAR);
            int hasPermission1 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALENDAR);

            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                showDialog();
                return;
            }

            if (hasPermission1 != PackageManager.PERMISSION_GRANTED) {
                showDialog();

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

    @Override
    public void onPositiveClick(int requestCode) {

        startActivity(new Intent(getActivity(), TakeQuizActivity.class));

    }

    @Override
    public void onNegativeClick(int requestCode) {
        startActivity(new Intent(getActivity(), TakeQuizActivity.class));
    }
}
