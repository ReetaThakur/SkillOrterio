package com.app.skillontario.activities;

import android.content.Intent;
import android.view.View;

import com.app.skillontario.callbacks.KeywordSelected;
import com.app.skillontario.models.PartnerModal;
import com.app.skillontario.utils.GridSpacingItemDecoration;
import com.app.skillorterio.R;
import com.app.skillontario.adapter.PartnersAdapter;
import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.apiConnection.RequestBodyGenerator;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillorterio.databinding.ActivityPartnersBinding;
import com.app.skillontario.requestmodal.GetEventRequest;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillontario.utils.Utils;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;


public class PartnersActivity extends BaseActivity implements ApiResponseErrorCallback {

    private ActivityPartnersBinding binding;
    GetEventRequest getEventRequest;
    ApiResponseErrorCallback apiResponseErrorCallback;
    int Total_count = 10;
    int page = 1;
    ArrayList<PartnerModal> arrayList_platinum;
    ArrayList<PartnerModal> arrayList_premium;
    ArrayList<PartnerModal> arrayList_government;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityPartnersBinding) viewBaseBinding;
        MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.IS_HEADER, true);
        binding.actionBar.tvTitle.setText(R.string.Partners);
        arrayList_platinum = new ArrayList<>();
        arrayList_premium = new ArrayList<>();
        arrayList_government = new ArrayList<>();
        apiResponseErrorCallback = this;
        getEventRequest = new GetEventRequest(this);
        showRecycler();

        binding.actionBar.ivBack.setOnClickListener(v -> onBackPressed());

    }

    private void showRecycler() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(PartnersActivity.this, 3);
        binding.rcyGovernmentPartners.setLayoutManager(mLayoutManager);// set Horizontal Orientation
        binding.rcyGovernmentPartners.addItemDecoration(new GridSpacingItemDecoration(PartnersActivity.this, 3, Utils.dpToPx(PartnersActivity.this, 5), true));

        RecyclerView.LayoutManager mLayoutManager_par = new GridLayoutManager(PartnersActivity.this, 3);
        binding.rcyPlatinumPartners.setLayoutManager(mLayoutManager_par);// set Horizontal Orientation
        binding.rcyPlatinumPartners.addItemDecoration(new GridSpacingItemDecoration(PartnersActivity.this, 3, Utils.dpToPx(PartnersActivity.this, 5), true));

        RecyclerView.LayoutManager mLayoutManager_pri = new GridLayoutManager(PartnersActivity.this, 3);
        binding.rcyPremiumPartners.setLayoutManager(mLayoutManager_pri);// set Horizontal Orientation
        binding.rcyPremiumPartners.addItemDecoration(new GridSpacingItemDecoration(PartnersActivity.this, 3, Utils.dpToPx(PartnersActivity.this, 5), true));

        RecyclerView.LayoutManager mLayoutManager_silver = new GridLayoutManager(PartnersActivity.this, 3);
        binding.rcySilver.setLayoutManager(mLayoutManager_silver);// set Horizontal Orientation
        binding.rcySilver.addItemDecoration(new GridSpacingItemDecoration(PartnersActivity.this, 3, Utils.dpToPx(PartnersActivity.this, 5), true));

        RecyclerView.LayoutManager mLayoutManager_gold = new GridLayoutManager(PartnersActivity.this, 3);
        binding.rcyGold.setLayoutManager(mLayoutManager_gold);// set Horizontal Orientation
        binding.rcyGold.addItemDecoration(new GridSpacingItemDecoration(PartnersActivity.this, 3, Utils.dpToPx(PartnersActivity.this, 5), true));

        RecyclerView.LayoutManager mLayoutManager_bronze = new GridLayoutManager(PartnersActivity.this, 3);
        binding.rcyBronze.setLayoutManager(mLayoutManager_bronze);// set Horizontal Orientation
        binding.rcyBronze.addItemDecoration(new GridSpacingItemDecoration(PartnersActivity.this, 3, Utils.dpToPx(PartnersActivity.this, 5), true));

        RecyclerView.LayoutManager mLayoutManager_friends = new GridLayoutManager(PartnersActivity.this, 3);
        binding.rcyFriends.setLayoutManager(mLayoutManager_friends);// set Horizontal Orientation
        binding.rcyFriends.addItemDecoration(new GridSpacingItemDecoration(PartnersActivity.this, 3, Utils.dpToPx(PartnersActivity.this, 5), true));

        getEventRequest.seteType("partner");
        getEventRequest.setEventId("");
        getEventRequest.setPage(String.valueOf(page));
        getEventRequest.setPageLimit(String.valueOf(Total_count));
        getEventRequest.setSearch("");
        API_INTERFACE.getPartner(RequestBodyGenerator.getEvent(getEventRequest)).enqueue(
                new ApiCallBack<>(PartnersActivity.this, apiResponseErrorCallback, 10, true));

    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_partners;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }

    @Override
    public void getApiResponse(Object responseObject, int flag) {
        try {
            if (flag == 10) {
                BaseResponseModel<ArrayList<PartnerModal>> responseModel = (BaseResponseModel<ArrayList<PartnerModal>>) responseObject;
                if (responseModel.getStatus()) {
                    if (responseModel.getOutput() != null) {
                        if (responseModel.getOutput().size() > 0) {
                            if (responseModel.getOutput().get(0).getPlatinum() != null) {
                                if (responseModel.getOutput().get(0).getPlatinum().size() > 0) {
                                    // binding.rlPlatinumPartners.setVisibility(View.VISIBLE);
                                    PartnersAdapter adapter = new PartnersAdapter(responseModel.getOutput().get(0).getPlatinum(), PartnersActivity.this, text -> {
                                        Intent intent = new Intent(this, WebViewActivity.class);
                                        intent.putExtra("url", text);
                                        //intent.putExtra("title",getString(R.string.platinum_partners));
                                        intent.putExtra("title", getString(R.string.platinum_patner));
                                        startActivity(intent);
                                    });
                                    binding.rcyPlatinumPartners.setAdapter(adapter);
                                } else {
                                    binding.rlPlatinum.setVisibility(View.GONE);
                                    binding.rlPlatinum2.setVisibility(View.GONE);
                                }
                            } else {
                                binding.rlPlatinum.setVisibility(View.GONE);
                                binding.rlPlatinum2.setVisibility(View.GONE);
                            }
                            if (responseModel.getOutput().get(1).getPremium() != null) {
                                if (responseModel.getOutput().get(1).getPremium().size() > 0) {
                                    // binding.rlPremiumPartners.setVisibility(View.VISIBLE);
                                    PartnersAdapter adapter = new PartnersAdapter(responseModel.getOutput().get(1).getPremium(), PartnersActivity.this, text -> {
                                        Intent intent = new Intent(this, WebViewActivity.class);
                                        intent.putExtra("url", text);
                                        //intent.putExtra("title",getString(R.string.premium_partners));
                                        intent.putExtra("title", getString(R.string.premimun_ptner));
                                        startActivity(intent);
                                    });
                                    binding.rcyPremiumPartners.setAdapter(adapter);
                                } else {
                                    binding.rlPremium.setVisibility(View.GONE);
                                    binding.rlPremium2.setVisibility(View.GONE);
                                }
                            } else {
                                binding.rlPremium.setVisibility(View.GONE);
                                binding.rlPremium2.setVisibility(View.GONE);
                            }
                            if (responseModel.getOutput().get(2).getSilver() != null) {
                                if (responseModel.getOutput().get(2).getSilver().size() > 0) {
                                    binding.rlSilver.setVisibility(View.VISIBLE);
                                    PartnersAdapter adapter = new PartnersAdapter(responseModel.getOutput().get(2).getSilver(), PartnersActivity.this, text -> {
                                        Intent intent = new Intent(this, WebViewActivity.class);
                                        intent.putExtra("url", text);
                                        //intent.putExtra("title",getString(R.string.silver_partners));
                                        intent.putExtra("title", getString(R.string.silver_patner));
                                        startActivity(intent);
                                    });
                                    binding.rcySilver.setAdapter(adapter);
                                } else {
                                    binding.rlSilver.setVisibility(View.GONE);
                                    binding.rlSilver2.setVisibility(View.GONE);
                                }
                            } else {
                                binding.rlSilver.setVisibility(View.GONE);
                                binding.rlSilver2.setVisibility(View.GONE);
                            }
                            if (responseModel.getOutput().get(3).getGold() != null) {
                                if (responseModel.getOutput().get(3).getGold().size() > 0) {
                                    binding.rlGold.setVisibility(View.VISIBLE);
                                    PartnersAdapter adapter = new PartnersAdapter(responseModel.getOutput().get(3).getGold(), PartnersActivity.this, text -> {
                                        Intent intent = new Intent(this, WebViewActivity.class);
                                        intent.putExtra("url", text);
                                        intent.putExtra("title", getString(R.string.gold_partners));
                                        startActivity(intent);
                                    });
                                    binding.rcyGold.setAdapter(adapter);
                                } else {
                                    binding.rlGold.setVisibility(View.GONE);
                                    binding.rlGold2.setVisibility(View.GONE);
                                }
                            } else {
                                binding.rlGold.setVisibility(View.GONE);
                                binding.rlGold2.setVisibility(View.GONE);
                            }
                            if (responseModel.getOutput().get(4).getBronze() != null) {
                                if (responseModel.getOutput().get(4).getBronze().size() > 0) {
                                    binding.rlBronze.setVisibility(View.VISIBLE);
                                    PartnersAdapter adapter = new PartnersAdapter(responseModel.getOutput().get(4).getBronze(), PartnersActivity.this, text -> {
                                        Intent intent = new Intent(this, WebViewActivity.class);
                                        intent.putExtra("url", text);
                                        intent.putExtra("title", getString(R.string.bronze_partners));
                                        startActivity(intent);
                                    });
                                    binding.rcyBronze.setAdapter(adapter);
                                } else {
                                    binding.rlBronze.setVisibility(View.GONE);
                                    binding.rlBronze2.setVisibility(View.GONE);
                                }
                            } else {
                                binding.rlBronze.setVisibility(View.GONE);
                                binding.rlBronze2.setVisibility(View.GONE);
                            }
                            if (responseModel.getOutput().get(5).getFriends() != null) {
                                if (responseModel.getOutput().get(5).getFriends().size() > 0) {
                                    binding.rlFriends.setVisibility(View.VISIBLE);
                                    PartnersAdapter adapter = new PartnersAdapter(responseModel.getOutput().get(5).getFriends(), PartnersActivity.this, text -> {
                                        Intent intent = new Intent(this, WebViewActivity.class);
                                        intent.putExtra("url", text);
                                        intent.putExtra("title", getString(R.string.friends_partners));
                                        startActivity(intent);
                                    });
                                    binding.rcyFriends.setAdapter(adapter);
                                } else {
                                    binding.rlFriends.setVisibility(View.GONE);
                                    binding.rlFriends2.setVisibility(View.GONE);
                                }
                            } else {
                                binding.rlFriends.setVisibility(View.GONE);
                                binding.rlFriends2.setVisibility(View.GONE);
                            }
                            if (responseModel.getOutput().get(6).getGovernment() != null) {
                                if (responseModel.getOutput().get(6).getGovernment().size() > 0) {
                                    binding.rlGovernmentPartners.setVisibility(View.VISIBLE);
                                    PartnersAdapter adapter = new PartnersAdapter(responseModel.getOutput().get(6).getGovernment(), PartnersActivity.this, text -> {
                                        Intent intent = new Intent(this, WebViewActivity.class);
                                        intent.putExtra("url", text);
                                        intent.putExtra("title", getString(R.string.government_partners));
                                        startActivity(intent);
                                    });
                                    binding.rcyGovernmentPartners.setAdapter(adapter);
                                } else {
                                    binding.rlGovernmentPartners.setVisibility(View.GONE);
                                    binding.rlGovernment2.setVisibility(View.GONE);
                                }
                            } else {
                                binding.rlGovernmentPartners.setVisibility(View.GONE);
                                binding.rlGovernment2.setVisibility(View.GONE);
                            }
                        }
                    }
                } else {
                    try {
                        Utils.showToast(PartnersActivity.this, responseModel.getMessage());
                    } catch (Exception e) {
                        binding.ShowREl.setVisibility(View.GONE);
                    }
                }
            }

            binding.ShowREl.setVisibility(View.GONE);
        } catch (Exception e) {
            binding.ShowREl.setVisibility(View.GONE);
        }
    }

    @Override
    public void getApiError(Throwable t, int flag) {
        binding.ShowREl.setVisibility(View.GONE);
    }
}