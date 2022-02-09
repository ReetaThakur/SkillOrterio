package com.app.skillsontario.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import com.app.skillsontario.R;
import com.app.skillsontario.adapter.QuizAdapter;
import com.app.skillsontario.apiConnection.ApiCallBack;
import com.app.skillsontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillsontario.apiConnection.RequestBodyGenerator;
import com.app.skillsontario.baseClasses.BaseActivity;
import com.app.skillsontario.baseClasses.BaseResponseModel;
import com.app.skillsontario.baseClasses.CrashLogger;
import com.app.skillsontario.constants.SharedPrefsConstants;
import com.app.skillsontario.databinding.ActivityTakeQuizBinding;
import com.app.skillsontario.models.CareerDetailModel;
import com.app.skillsontario.models.CareerModal;
import com.app.skillsontario.models.HomeModal;
import com.app.skillsontario.quiz.QuizStepAc;
import com.app.skillsontario.utils.MySharedPreference;
import com.app.skillsontario.utils.Utils;


import java.util.ArrayList;
import java.util.HashMap;

import static com.app.skillsontario.constants.ApiConstants.API_INTERFACE;

public class TakeQuizActivity extends BaseActivity implements ApiResponseErrorCallback, QuizAdapter.BookMarkUpdateDelete {
    int CareerPosition;
    private ActivityTakeQuizBinding binding;
    QuizAdapter adapter;
    ArrayList<CareerModal> careerModalArrayList = new ArrayList<>();
    QuizAdapter.BookMarkUpdateDelete bookMarkUpdateDelete;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityTakeQuizBinding) viewBaseBinding;
        bookMarkUpdateDelete = this;
        //  binding.tvTakeQuiz.setOnClickListener(v->startActivity(new Intent(this, QuizStepAc.class)));
        callAPI(true);

        binding.tvTakeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Dialog dialog = new Dialog(TakeQuizActivity.this, android.R.style.Theme_Light);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.quiz_transparent_view);

                    dialog.findViewById(R.id.tv_gotit).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(TakeQuizActivity.this, QuizStepAc.class));
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } catch (Exception e) {
                    startActivity(new Intent(TakeQuizActivity.this, QuizStepAc.class));
                }

                try {
                    CrashLogger.INSTANCE.trackEventsFirebase("Take_a_Quiz", "TakeQuizActivity");
                } catch (Exception e) {
                }

            }
        });

        binding.ivBack.setOnClickListener(v -> onBackPressed());
        showPopularCareerRecycler();
    }

    void callAPI(boolean c) {

        HashMap<String, Object> object = new HashMap<>();
        object.put("userId", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID));

        API_INTERFACE.getHomeData(object).enqueue(
                new ApiCallBack<>(TakeQuizActivity.this, this, 12, c));
    }


    private void showPopularCareerRecycler() {
        binding.recyQuiz.setHasFixedSize(true);
        adapter = new QuizAdapter(TakeQuizActivity.this, true);
        binding.recyQuiz.setAdapter(adapter);

      /*  binding.recyQuiz.addOnItemTouchListener(new RecyclerItemClickListener(TakeQuizActivity.this, (view, position) -> {
            startActivity(new Intent(this, JobDetailsActivity.class));
        }));*/
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
    }


    @Override
    protected int getLayoutById() {
        return R.layout.activity_take_quiz;
    }


    @Override
    public void getApiResponse(Object responseObject, int flag) {
        if (flag == 12) {
            if (careerModalArrayList != null) {
                careerModalArrayList.clear();
            }


            BaseResponseModel<ArrayList<HomeModal>> responseModel = (BaseResponseModel<ArrayList<HomeModal>>) responseObject;
            if (responseModel.getStatus()) {
                //
                if (responseModel.getOutput() != null) {
                    if (responseModel.getOutput().size() > 0) {
                        if (responseModel.getOutput().get(0).getCareerData() != null) {
                            if (responseModel.getOutput().get(0).getCareerData().size() > 0) {
                                careerModalArrayList.addAll(responseModel.getOutput().get(0).getCareerData());
                                adapter = new QuizAdapter(careerModalArrayList, TakeQuizActivity.this, false, bookMarkUpdateDelete);
                                binding.recyQuiz.setAdapter(adapter);

                            }
                        }
                    }
                }
                //


            }
        } else if (flag == 103) {
            try {
                BaseResponseModel responseModel = (BaseResponseModel) responseObject;
                if (responseModel.getStatus()) {
                    careerModalArrayList.get(CareerPosition).setbId("");
                    adapter.notifyDataSetChanged();
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
    public void checkBookMark(String Bid, int position, String careerId) {
        if (MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.GUEST_FLOW)) {
            try {
                Utils.guestMethod(TakeQuizActivity.this, "homeFragment");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                CareerPosition = position;
                if (Bid.equalsIgnoreCase("")) {
                    addBookmark(careerModalArrayList.get(position), careerId);
                } else {
                    removeBookmark(Bid, careerId);
                }
            } catch (Exception e) {
            }

        }
    }

    void addBookmark(CareerModal list, String careerId) {
        API_INTERFACE.addCareerBookmark(RequestBodyGenerator.setBookmarkadd(list, MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID), careerId)).enqueue(
                new ApiCallBack<>(TakeQuizActivity.this, this, 102, false));
    }

    void removeBookmark(String bid, String careerId) {
        HashMap<String, Object> object = new HashMap<>();

        object.put("careerId", careerId);
        object.put("userId", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID));
        object.put("bId", bid);

        API_INTERFACE.deleteCareerBookmark(object).enqueue(
                new ApiCallBack<>(TakeQuizActivity.this, this, 103, false));
    }
}