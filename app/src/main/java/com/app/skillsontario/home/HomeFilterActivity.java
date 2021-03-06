package com.app.skillsontario.home;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.adroitandroid.chipcloud.ChipCloud;
import com.adroitandroid.chipcloud.ChipListener;
import com.app.skillsontario.R;
import com.app.skillsontario.constants.AppConstants;
import com.app.skillsontario.databinding.ActivityHomeFilterBinding;
import com.app.skillsontario.models.EducationModal;
import com.app.skillsontario.models.InterestModal;
import com.app.skillsontario.models.RedSealModal;
import com.app.skillsontario.models.SectorModal;

import com.app.skillsontario.activities.SearchActivity;
import com.app.skillsontario.apiConnection.ApiCallBack;
import com.app.skillsontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillsontario.baseClasses.BaseActivity;
import com.app.skillsontario.baseClasses.BaseResponseModel;
import com.app.skillsontario.callbacks.KeywordSelected;
import com.app.skillsontario.constants.SharedPrefsConstants;
import com.app.skillsontario.utils.MySharedPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.app.skillsontario.constants.ApiConstants.API_INTERFACE;

public class HomeFilterActivity extends BaseActivity implements KeywordSelected, ApiResponseErrorCallback {

    private ActivityHomeFilterBinding binding;

    ArrayList<SectorModal> sectorarrarylist;
    ArrayList<EducationModal> educationAraylist;
    ArrayList<RedSealModal> redSealarraylist;
    ApiResponseErrorCallback apiResponseErrorCallback;
    String redFlag = "";
    String search = "";

    public static HashMap<Integer, String> sectorHashMap = new HashMap<>();
    public static HashMap<Integer, String> eduactionHashMap = new HashMap<>();
    public static HashMap<Integer, String> redHashMap = new HashMap<>();

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityHomeFilterBinding) viewBaseBinding;
        apiResponseErrorCallback = this;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            search = getIntent().getStringExtra("search");
        }

        MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.IS_HEADER, true);
        sectorarrarylist = new ArrayList<>();
        educationAraylist = new ArrayList<>();
        redSealarraylist = new ArrayList<>();
        API_INTERFACE.jobOption().enqueue(
                new ApiCallBack<>(HomeFilterActivity.this, apiResponseErrorCallback, 12, true));


        try {
            binding.actionBarN.ivBack.setOnClickListener(v -> manageBackPressed());
        } catch (Exception e) {
        }

        try {
            binding.actionBarN.ivReset.setOnClickListener(v -> {
                try {
                    eduactionHashMap.clear();
                    redHashMap.clear();
                    sectorHashMap.clear();
                } catch (Exception e) {
                }

                redFlag = "";
                String[] stringsarr = new String[sectorarrarylist.size()];
                for (int i = 0; i < sectorarrarylist.size(); i++) {
                    sectorarrarylist.get(i).setSelect(false);
                    stringsarr[i] = sectorarrarylist.get(i).getJobSector();
                }
                sector(stringsarr);
                String[] stringsarredu = new String[educationAraylist.size()];
                for (int i = 0; i < educationAraylist.size(); i++) {
                    educationAraylist.get(i).setSelector(false);
                    stringsarredu[i] = educationAraylist.get(i).getJobSector();
                }
                education(stringsarredu);

                String[] stringred = new String[redSealarraylist.size()];
                for (int i = 0; i < redSealarraylist.size(); i++) {
                    redSealarraylist.get(i).setSelector(false);
                    if (i == 0) {
                        stringred[i] = "Yes";
                    } else {
                        stringred[i] = "No";
                    }
                }
                readDeal(stringred);
            });
        } catch (Exception e) {
        }

        try {
            binding.actionBarN.tvTitle.setText(R.string.set_your_filter);
        } catch (Exception e) {
        }

        try {
            binding.actionBarN.ivReset.setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }


        binding.cvApplyFilter.setOnClickListener(v -> {
            String sector_id = "";
            String education_id = "";
            ArrayList<String> arr_sector = new ArrayList<>();
            ArrayList<String> arr_education = new ArrayList<>();
            for (int i = 0; i < sectorarrarylist.size(); i++) {
                if (sectorarrarylist.get(i).isSelect()) {
                    arr_sector.add(sectorarrarylist.get(i).getId());

                }
            }
            for (int i = 0; i < educationAraylist.size(); i++) {
                if (educationAraylist.get(i).isSelector()) {
                    arr_education.add(educationAraylist.get(i).getId());

                }
            }

            for (int i = 0; i < redSealarraylist.size(); i++) {
                if (redSealarraylist.get(i).isSelector()) {
                    //  arr_education.add(redSealarraylist.get(i).getId());
                    if (i == 1) {
                        redFlag = "0";
                    } else {
                        redFlag = "1";
                    }
                }
            }


            if (arr_sector.size() > 0 || !arr_sector.isEmpty()) {
                sector_id = TextUtils.join(",", arr_sector);
            }

            if (arr_education.size() > 0 || !arr_education.isEmpty()) {
                education_id = TextUtils.join(",", arr_education);
            }
            if ((arr_sector.size() == 0 || arr_sector.isEmpty()) && (arr_education.size() == 0 || arr_education.isEmpty()) && redFlag.equalsIgnoreCase("")) {
                showToast(getString(R.string.filter_select));
            } else {

                Intent intent = new Intent(HomeFilterActivity.this, SearchActivity.class);
                intent.putExtra("sector", sector_id);
                intent.putExtra("education", education_id);
                intent.putExtra("redFlag", redFlag);
                intent.putExtra("ApplyFilter", "Apply");
                intent.putExtra("search", search);
                startActivity(intent);
                finish();


            }
        });
    }


    @Override
    protected int getLayoutById() {
        return R.layout.activity_home_filter;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
        manageBackPressed();
    }

    private void manageBackPressed() {
        startActivity(new Intent(HomeFilterActivity.this, SearchActivity.class));
        finish();
    }

    @Override
    public void onTextClick(String text) {

    }

    @Override
    public void getApiResponse(Object responseObject, int flag) {
        if (flag == 12) {
            if (sectorarrarylist != null) {
                sectorarrarylist.clear();
            }
            if (educationAraylist != null) {
                educationAraylist.clear();
            }
            if (redSealarraylist != null) {
                redSealarraylist.clear();
            }
            BaseResponseModel<ArrayList<InterestModal>> responseModel = (BaseResponseModel<ArrayList<InterestModal>>) responseObject;
            if (responseModel.getStatus()) {
                if (responseModel.getOutput() != null) {
                    if (responseModel.getOutput().size() > 0) {
                        if (responseModel.getOutput().get(0).getSector() != null) {
                            if (responseModel.getOutput().get(0).getSector().size() > 0) {
                                binding.tvSector.setVisibility(View.VISIBLE);
                                sectorarrarylist.addAll(responseModel.getOutput().get(0).getSector());
                                String[] stringsarr = new String[sectorarrarylist.size()];
                                for (int i = 0; i < sectorarrarylist.size(); i++) {
                                    stringsarr[i] = sectorarrarylist.get(i).getJobSector();
                                }

                                sector(stringsarr);
                            }
                        } else {
                            binding.tvSector.setVisibility(View.GONE);
                        }
                        if (responseModel.getOutput().get(1).getEducation() != null)
                            if (responseModel.getOutput().get(1).getEducation().size() > 0) {
                                binding.tvEducation.setVisibility(View.VISIBLE);
                                educationAraylist.addAll(responseModel.getOutput().get(1).getEducation());
                                String[] stringsarr = new String[educationAraylist.size()];
                                for (int i = 0; i < educationAraylist.size(); i++) {
                                    stringsarr[i] = educationAraylist.get(i).getJobSector();
                                }
                                education(stringsarr);
                            }
                    } else {
                        binding.tvEducation.setVisibility(View.GONE);
                    }
                    if (responseModel.getOutput().get(2).getRedSeal() != null) {
                        if (responseModel.getOutput().get(2).getRedSeal().size() > 0) {
                            binding.tvReadseal.setVisibility(View.VISIBLE);
                            redSealarraylist.addAll(responseModel.getOutput().get(2).getRedSeal());
                            String[] stringsarr = new String[redSealarraylist.size()];
                            for (int i = 0; i < redSealarraylist.size(); i++) {
                                if (MySharedPreference.getInstance().getStringData(AppConstants.LANGUAGE).equalsIgnoreCase("en")) {
                                    if (i == 0) {
                                        stringsarr[i] = "Yes";
                                    } else {
                                        stringsarr[i] = "No";
                                    }

                                } else {
                                    if (i == 0) {
                                        stringsarr[i] = "Oui";
                                    } else {
                                        stringsarr[i] = "Non";
                                    }
                                }

                            }
                            readDeal(stringsarr);
                        }
                    } else {
                        binding.tvReadseal.setVisibility(View.GONE);
                    }
                }
            } else {
                showToast(responseModel.getMessage());
            }
        }
    }

    @Override
    public void getApiError(Throwable t, int flag) {

    }

    /*@Override
    public void getApiError500(String msg, int flag) {

    }*/

    private void sector(String[] stringsarr) {

        new ChipCloud.Configure()
                .chipCloud(binding.cpSector)
                .labels(stringsarr)
                .mode(ChipCloud.Mode.MULTI)
                .allCaps(false)
                .gravity(ChipCloud.Gravity.LEFT)
                .chipListener(new ChipListener() {
                    @Override
                    public void chipSelected(int index) {
                        sectorarrarylist.get(index).setSelect(true);
                        try {
                            sectorHashMap.put(index, String.valueOf(index));
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void chipDeselected(int index) {
                        sectorarrarylist.get(index).setSelect(false);
                        try {
                            sectorHashMap.remove(index);
                        } catch (Exception e) {
                        }

                    }
                })
                .build();

        try {
            for (Map.Entry m : sectorHashMap.entrySet()) {
                System.out.println(m.getKey() + " " + m.getValue());

                if (binding.cpSector != null && binding.cpSector.getChildCount() != 0) {
                    int key = (int) m.getKey();
                    binding.cpSector.setSelectedChip(key);
                }
            }
        } catch (Exception e) {
        }

    }

    private void education(String[] stringsarr) {
        new ChipCloud.Configure()
                .chipCloud(binding.cpEducation)
                .labels(stringsarr)
                .mode(ChipCloud.Mode.MULTI)
                .allCaps(false)
                .gravity(ChipCloud.Gravity.LEFT)
                .chipListener(new ChipListener() {
                    @Override
                    public void chipSelected(int index) {
                        educationAraylist.get(index).setSelector(true);

                        try {
                            eduactionHashMap.put(index, String.valueOf(index));
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void chipDeselected(int index) {
                        educationAraylist.get(index).setSelector(false);
                        try {
                            eduactionHashMap.remove(index);
                        } catch (Exception e) {
                        }
                    }
                })
                .build();

        try {
            for (Map.Entry m : eduactionHashMap.entrySet()) {

                if (binding.cpEducation != null && binding.cpEducation.getChildCount() != 0) {
                    int key = (int) m.getKey();
                    binding.cpEducation.setSelectedChip(key);
                }
            }
        } catch (Exception e) {
        }

    }

    private void readDeal(String[] stringsarr) {
        new ChipCloud.Configure()
                .chipCloud(binding.cpRedSeal)
                .labels(stringsarr)
                .mode(ChipCloud.Mode.SINGLE)
                .allCaps(false)
                .gravity(ChipCloud.Gravity.LEFT)
                .chipListener(new ChipListener() {
                    @Override
                    public void chipSelected(int index) {
                      /*  if (index == 1) {
                            redFlag = "0";
                        } else {
                            redFlag = "1";
                        }*/
                        redSealarraylist.get(index).setSelector(true);

                        try {
                            redHashMap.put(index, String.valueOf(index));
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void chipDeselected(int index) {
                        redSealarraylist.get(index).setSelector(false);

                        try {
                            redHashMap.remove(index);
                        } catch (Exception e) {
                        }
                    }
                })
                .build();

        try {
            for (Map.Entry m : redHashMap.entrySet()) {

                if (binding.cpRedSeal != null && binding.cpRedSeal.getChildCount() != 0) {
                    int key = (int) m.getKey();
                    binding.cpRedSeal.setSelectedChip(key);
                }
            }
        } catch (Exception e) {
        }

    }
}