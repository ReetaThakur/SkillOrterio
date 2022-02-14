package com.app.skillsontario.activities;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.app.skillsontario.R;
import com.app.skillsontario.adapter.ExpandRecyclerAdapter;
import com.app.skillsontario.adapter.VideoAdapter;
import com.app.skillsontario.apiConnection.ApiCallBack;
import com.app.skillsontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillsontario.apiConnection.RequestBodyGenerator;
import com.app.skillsontario.baseClasses.BaseActivity;
import com.app.skillsontario.baseClasses.BaseResponseModel;
import com.app.skillsontario.constants.SharedPrefsConstants;
import com.app.skillsontario.databinding.ActivityJobDetailsBinding;
import com.app.skillsontario.expandRecycler.SampleChildBean;
import com.app.skillsontario.expandRecycler.SampleGroupBean;
import com.app.skillsontario.models.CareerDetailModel;
import com.app.skillsontario.models.ResourceURLModal;
import com.app.skillsontario.models.careerListModel.CareerListDetails;
import com.app.skillsontario.utils.MySharedPreference;
import com.app.skillsontario.utils.Utils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.app.skillsontario.constants.ApiConstants.API_INTERFACE;

public class JobDetailsActivity extends BaseActivity implements ApiResponseErrorCallback {

    private ActivityJobDetailsBinding binding;
    ExpandRecyclerAdapter adapter;
    private boolean clickBookMark = false;
    String modal_Id;
    List<SampleGroupBean> list = new ArrayList<>();
    public static String bidData = "";
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
        bidData = "";
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
                if (!MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.GUEST_FLOW)) {
                    if (clickBookMark) {
                        clickBookMark = false;
                        binding.imgBookmark.setImageResource(R.drawable.ic_book_mark_de);
                        removeBookmark(careerListDetails.get(0).getbId(), careerListDetails.get(0).getId());
                    } else {
                        clickBookMark = true;
                        binding.imgBookmark.setImageResource(R.drawable.ic_book_mark_de_select);
                        addBookmark(careerListDetails.get(0), careerListDetails.get(0).getId());
                    }
                } else {
                    try {
                        Utils.guestMethod(JobDetailsActivity.this, "JobDetailsActivity");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        binding.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Utils.share1(JobDetailsActivity.this, getString(R.string.profile_share), careerListDetails.get(0).getImage(), careerListDetails.get(0).getJobDesc(), "jobProfile", careerListDetails.get(0).getId());

                } catch (Exception e) {
                }
            }


        });


    }

    void addBookmark(CareerListDetails list, String careerId) {
        API_INTERFACE.addCareerBookmark(RequestBodyGenerator.setBookmark(list, MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID), careerId)).enqueue(
                new ApiCallBack<>(JobDetailsActivity.this, this, 102, true));
    }

    void removeBookmark(String bid, String careerId) {
        HashMap<String, Object> object = new HashMap<>();

        object.put("careerId", careerId);
        object.put("userId", MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID));
        object.put("bId", bidData);

        API_INTERFACE.deleteCareerBookmark(object).enqueue(
                new ApiCallBack<>(JobDetailsActivity.this, this, 103, true));
    }

    private void CallApi(String carrerId) {
        API_INTERFACE.getCareerList(RequestBodyGenerator.setCareerDetailData(MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID), carrerId)).enqueue(
                new ApiCallBack<>(JobDetailsActivity.this, this, 121, false));
    }


    private void showExpandRecycler(ArrayList<CareerListDetails> careerListDetails) {
        list.clear();

        if (careerListDetails.get(0).getJobDesc() != null) {
            if (!careerListDetails.get(0).getJobDesc().isEmpty()) {
                List<SampleChildBean> childList = new ArrayList<>();
                childList.add(new SampleChildBean(careerListDetails.get(0).getJobDesc()));
                list.add(new SampleGroupBean(childList, getString(R.string.job_desc)));
            }
        }

        //   childList.clear();
        if (careerListDetails.get(0).getJobResp() != null) {
            if (!careerListDetails.get(0).getJobResp().isEmpty()) {
                List<SampleChildBean> childList = new ArrayList<>();
                childList.add(new SampleChildBean(careerListDetails.get(0).getJobResp()));
                list.add(new SampleGroupBean(childList, getString(R.string.job_resp)));
            }
        }


        //  childList.clear();
        if (careerListDetails.get(0).getJobArea() != null) {
            if (!careerListDetails.get(0).getJobArea().isEmpty()) {
                List<SampleChildBean> childList = new ArrayList<>();
                childList.add(new SampleChildBean(careerListDetails.get(0).getJobArea()));
                list.add(new SampleGroupBean(childList, getString(R.string.where_they)));
            }
        }


        // childList.clear();
        if (careerListDetails.get(0).getAdvice() != null) {
            if (!careerListDetails.get(0).getAdvice().isEmpty()) {
                List<SampleChildBean> childList = new ArrayList<>();
                childList.add(new SampleChildBean(careerListDetails.get(0).getAdvice()));
                list.add(new SampleGroupBean(childList, getString(R.string.advice)));
            }
        }


        //  childList.clear();
        if (careerListDetails.get(0).getEduReq() != null) {
            if (!careerListDetails.get(0).getEduReq().isEmpty()) {
                List<SampleChildBean> childList = new ArrayList<>();
                childList.add(new SampleChildBean(careerListDetails.get(0).getEduReq()));
                list.add(new SampleGroupBean(childList, getString(R.string.education_required)));
            }
        }

        //   childList.clear();
        if (careerListDetails.get(0).getTraReq() != null) {
            if (!careerListDetails.get(0).getTraReq().isEmpty()) {
                List<SampleChildBean> childList = new ArrayList<>();
                childList.add(new SampleChildBean(careerListDetails.get(0).getTraReq()));
                list.add(new SampleGroupBean(childList, getString(R.string.training_req)));
            }
        }


        //  childList.clear();
        if (careerListDetails.get(0).getExpeReq() != null) {
            if (!careerListDetails.get(0).getExpeReq().isEmpty()) {
                List<SampleChildBean> childList = new ArrayList<>();
                childList.add(new SampleChildBean(careerListDetails.get(0).getExpeReq()));
                list.add(new SampleGroupBean(childList, getString(R.string.exp_req)));
            }
        }


        binding.recyExpand.setHasFixedSize(false);
        binding.recyExpand.setNestedScrollingEnabled(false);
        adapter = new ExpandRecyclerAdapter(list, JobDetailsActivity.this);
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
                binding.imgBookmark.setBackgroundResource(R.drawable.ic_book_mark_de);
            } else {
                //showToast(responseModel.getMessage());
            }
        } else if (flag == 102) {
            BaseResponseModel<CareerDetailModel> responseModel = (BaseResponseModel<CareerDetailModel>) responseObject;
            if (responseModel != null) {
                if (responseModel.getStatus()) {
                    clickBookMark = true;
                    binding.imgBookmark.setBackgroundResource(R.drawable.ic_book_mark_de_select);
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
                            if (responseModel.getOutput().size() > 0) {
                                bidData = responseModel.output.get(0).getbId();
                                careerListDetails = responseModel.output;
                                setData(careerListDetails);
                                showExpandRecycler(careerListDetails);
                            } else {
                                showToast(getString(R.string.pro));
                                finish();
                            }
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
            binding.imgBookmark.setBackgroundResource(R.drawable.ic_book_mark_de);
        } else {
            clickBookMark = true;
            binding.imgBookmark.setBackgroundResource(R.drawable.ic_book_mark_de_select);
        }
        // Picasso.with(JobDetailsActivity.this).load(careerListDetails.get(0).getImage()).into(binding.imageP);


        Glide.with(JobDetailsActivity.this)
                .load(careerListDetails.get(0).getImage())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        binding.progress.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        binding.progress.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(binding.imageP);


        binding.textCons.setText(careerListDetails.get(0).getJobSector());
        binding.textWork.setText(careerListDetails.get(0).getJobProfile());
        binding.textFee.setText(careerListDetails.get(0).getFee());

        resourceURLModalArrayList.clear();
        if (careerListDetails.get(0).getResourceURL() != null) {

            boolean contains = true;
           /* for (int i = 0; i < careerListDetails.get(0).getResourceURL().size(); i++) {
                contains = careerListDetails.get(0).getResourceURL().get(i).getPath()
                        .toLowerCase().contains("https://www.youtube.com/");


            }*/

            if (contains) {
                binding.tvHelpFull.setVisibility(View.VISIBLE);
                binding.recyVideo.setVisibility(View.VISIBLE);
                resourceURLModalArrayList.addAll(careerListDetails.get(0).getResourceURL());
                VideoAdapter videoAdapter = new VideoAdapter(resourceURLModalArrayList, JobDetailsActivity.this, careerListDetails.get(0).getJobProfile());
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