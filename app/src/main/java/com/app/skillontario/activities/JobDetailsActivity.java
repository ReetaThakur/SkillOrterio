package com.app.skillontario.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.app.skillontario.adapter.ExpandRecyclerAdapter;
import com.app.skillontario.adapter.NotificationAdapter;
import com.app.skillontario.adapter.VideoAdapter;
import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.apiConnection.RequestBodyGenerator;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.callbacks.ScrollViewListener;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.expandRecycler.SampleChildBean;
import com.app.skillontario.expandRecycler.SampleGroupBean;
import com.app.skillontario.models.CareerDetailModel;
import com.app.skillontario.models.ResourceURLModal;
import com.app.skillontario.models.careerListModel.CareerListDetails;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillontario.utils.ObservableScrollView;
import com.app.skillontario.utils.RecyclerItemClickListener;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityJobDetailsBinding;
import com.app.skillorterio.databinding.ActivitySettingBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.app.skillontario.adapter.PopularCareerAdapter.numberOfPerson;
import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;

public class JobDetailsActivity extends BaseActivity implements ApiResponseErrorCallback {

    private ActivityJobDetailsBinding binding;
    ExpandRecyclerAdapter adapter;
    private boolean clickBookMark = false;
    String modal_Id;
    List<SampleGroupBean> list = new ArrayList<>();

    ArrayList<CareerListDetails> careerListDetails = new ArrayList<>();
    ArrayList<ResourceURLModal> resourceURLModalArrayList = new ArrayList<>();

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityJobDetailsBinding) viewBaseBinding;

        MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.IS_HEADER, true);
        // UserId = MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            modal_Id = getIntent().getStringExtra("Popular");
        }
        if (modal_Id == null) {
            modal_Id = "";
        }
        CallApi(modal_Id);


        binding.imgBack.setOnClickListener(v -> onBackPressed());
        //showExpandRecycler();

        //VideoAdapter videoAdapter = new VideoAdapter(JobDetailsActivity.this);
        binding.recyVideo.setHasFixedSize(true);
        binding.recyVideo.setNestedScrollingEnabled(false);
        //binding.recyVideo.setAdapter(videoAdapter);


        binding.tvsSroll.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (binding.tvsSroll.getChildAt(0).getBottom()
                                <= (binding.tvsSroll.getHeight() + binding.tvsSroll.getScrollY()) + binding.tvsSroll.getScrollY()) {
                            //scroll view is at bottom

                            changeColor(binding.imgBookmark, true);
                            changeColor(binding.imgShare, true);
                            changeColor(binding.imgBack, true);
                        } else {
                            //scroll view is not at bottom
                            changeColor(binding.imgBack, false);
                            changeColor(binding.imgBookmark, false);
                            changeColor(binding.imgShare, false);

                        }
                    }
                });


        binding.imgBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickBookMark) {
                    clickBookMark = false;
                    binding.imgBookmark.setImageResource(R.drawable.ic_job_uppar1);
                    removeBookmark(careerListDetails.get(0).getbId(), careerListDetails.get(0).getId());
                } else {
                    clickBookMark = true;
                    binding.imgBookmark.setImageResource(R.drawable.job_bookmark_click);
                    addBookmark(careerListDetails.get(0), careerListDetails.get(0).getId());
                }
            }
        });

        binding.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareCompat.IntentBuilder.from(JobDetailsActivity.this)
                        .setType("text/plain")
                        .setChooserTitle(getResources().getText(R.string.app_name))
                        .setText("https://www.skillsontario.com")
                        .startChooser();
            }
        });


    }

    void addBookmark(CareerListDetails list, String careerId) {
        API_INTERFACE.addCareerBookmark(RequestBodyGenerator.setBookmark(list, MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID), careerId)).enqueue(
                new ApiCallBack<>(JobDetailsActivity.this, this, 102, false));
    }

    void removeBookmark(String bid, String careerId) {
        HashMap<String, Object> object = new HashMap<>();

        object.put("careerId", careerId);
        object.put("userId", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID));
        object.put("bId", bid);

        API_INTERFACE.deleteCareerBookmark(object).enqueue(
                new ApiCallBack<>(JobDetailsActivity.this, this, 103, false));
    }

    private void CallApi(String carrerId) {
        API_INTERFACE.getCareerList(RequestBodyGenerator.setCareerDetailData(MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID), carrerId)).enqueue(
                new ApiCallBack<>(JobDetailsActivity.this, this, 121, true));
    }


    private void showExpandRecycler(ArrayList<CareerListDetails> careerListDetails) {
        list.clear();

        if (careerListDetails.get(0).getJobDesc() != null) {
            List<SampleChildBean> childList = new ArrayList<>();
            childList.add(new SampleChildBean(careerListDetails.get(0).getJobDesc()));
            list.add(new SampleGroupBean(childList, "Job Description"));
        }

        //   childList.clear();
        if (careerListDetails.get(0).getJobResp() != null) {
            List<SampleChildBean> childList = new ArrayList<>();
            childList.add(new SampleChildBean(careerListDetails.get(0).getJobResp()));
            list.add(new SampleGroupBean(childList, "Job Responsibilities"));
        }


        //  childList.clear();
        if (careerListDetails.get(0).getJobArea() != null) {
            List<SampleChildBean> childList = new ArrayList<>();
            childList.add(new SampleChildBean(careerListDetails.get(0).getJobArea()));
            list.add(new SampleGroupBean(childList, "Where they Work"));
        }


        // childList.clear();
        if (careerListDetails.get(0).getAdvice() != null) {
            List<SampleChildBean> childList = new ArrayList<>();
            childList.add(new SampleChildBean(careerListDetails.get(0).getAdvice()));
            list.add(new SampleGroupBean(childList, "Advice"));
        }


        //  childList.clear();
        if (careerListDetails.get(0).getEduReq() != null) {
            List<SampleChildBean> childList = new ArrayList<>();
            childList.add(new SampleChildBean(careerListDetails.get(0).getEduReq()));
            list.add(new SampleGroupBean(childList, "Education Required"));
        }


        //   childList.clear();
        if (careerListDetails.get(0).getTraReq() != null) {
            List<SampleChildBean> childList = new ArrayList<>();
            childList.add(new SampleChildBean(careerListDetails.get(0).getTraReq()));
            list.add(new SampleGroupBean(childList, "Training Required"));
        }


        //  childList.clear();
        if (careerListDetails.get(0).getExpeReq() != null) {
            List<SampleChildBean> childList = new ArrayList<>();
            childList.add(new SampleChildBean(careerListDetails.get(0).getExpeReq()));
            list.add(new SampleGroupBean(childList, "Experience Required"));
        }


        binding.recyExpand.setHasFixedSize(false);
        binding.recyExpand.setNestedScrollingEnabled(false);
        adapter = new ExpandRecyclerAdapter(list);
        binding.recyExpand.setAdapter(adapter);

       /* binding.recyExpand.addOnItemTouchListener(new RecyclerItemClickListener(JobDetailsActivity.this, (view, position) -> {

        }));*/
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_job_details;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    void changeColor(ImageView imageView, boolean black) {
        if (black)
            imageView.setColorFilter(ContextCompat.getColor(JobDetailsActivity.this, R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);
        else
            imageView.setColorFilter(ContextCompat.getColor(JobDetailsActivity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public void getApiResponse(Object responseObject, int flag) {
        if (flag == 103) {
            BaseResponseModel responseModel = (BaseResponseModel) responseObject;
            if (responseModel.getStatus()) {
                clickBookMark = false;
                binding.imgBookmark.setBackgroundResource(R.drawable.ic_job_uppar1);
            } else {
                showToast(responseModel.getMessage());
            }
        } else if (flag == 102) {
            BaseResponseModel<CareerDetailModel> responseModel = (BaseResponseModel<CareerDetailModel>) responseObject;
            if (responseModel != null) {
                if (responseModel.getStatus()) {
                    clickBookMark = true;
                    binding.imgBookmark.setBackgroundResource(R.drawable.ic_bookmark_fill);
                }
            }
        } else if (flag == 121) {
            try {
                if (careerListDetails != null) {
                    careerListDetails.clear();
                }
                BaseResponseModel<ArrayList<CareerListDetails>> responseModel = (BaseResponseModel<ArrayList<CareerListDetails>>) responseObject;

                if (responseModel.getStatus()) {
                    try {
                        if (responseModel.getOutput() != null) {
                            if (responseModel.getOutput().size() > 0)
                                careerListDetails = responseModel.output;
                            setData(careerListDetails);
                            showExpandRecycler(careerListDetails);
                        }

                    } catch (Exception e) {
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void getApiError(Throwable t, int flag) {

    }

    private void setData(ArrayList<CareerListDetails> careerListDetails) {
        if (careerListDetails.get(0).getbId().equalsIgnoreCase("")) {
            clickBookMark = false;
            binding.imgBookmark.setBackgroundResource(R.drawable.ic_job_uppar1);
        } else {
            clickBookMark = true;
            binding.imgBookmark.setBackgroundResource(R.drawable.ic_bookmark_fill);
        }
        Picasso.with(JobDetailsActivity.this).load(careerListDetails.get(0).getImage()).into(binding.imageP);
        binding.textCons.setText(careerListDetails.get(0).getJobSector());
        binding.textWork.setText(careerListDetails.get(0).getJobProfile());
        binding.textFee.setText(careerListDetails.get(0).getFee());

        resourceURLModalArrayList.clear();
        if (careerListDetails.get(0).getResourceURL() != null) {

            boolean contains = false;
            for (int i = 0; i < careerListDetails.get(0).getResourceURL().size(); i++) {
                contains = careerListDetails.get(0).getResourceURL().get(i).getPath()
                        .toLowerCase().contains("https://www.youtube.com/");

                if (contains) {
                    break;
                }
            }

            if (contains) {
                binding.tvHelpFull.setVisibility(View.VISIBLE);
                binding.recyVideo.setVisibility(View.VISIBLE);
                resourceURLModalArrayList.addAll(careerListDetails.get(0).getResourceURL());
                VideoAdapter videoAdapter = new VideoAdapter(resourceURLModalArrayList, JobDetailsActivity.this);
                binding.recyVideo.setAdapter(videoAdapter);
            } else {
                binding.recyVideo.setVisibility(View.GONE);
                binding.tvHelpFull.setVisibility(View.GONE);
            }
        } else {
            binding.recyVideo.setVisibility(View.GONE);
            binding.tvHelpFull.setVisibility(View.GONE);
        }
    }
}