package com.app.skillontario.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillontario.BottomBarActivity;
import com.app.skillontario.activities.EditProfileAc;
import com.app.skillontario.activities.JobDetailsActivity;
import com.app.skillontario.activities.ResourcesDetailsActivity;
import com.app.skillontario.activities.ScholarDetailAc;
import com.app.skillontario.activities.WebViewActivity;
import com.app.skillontario.apiConnection.ApiCallBack;
import com.app.skillontario.apiConnection.ApiResponseErrorCallback;
import com.app.skillontario.apiConnection.RequestBodyGenerator;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.dialogs.DialogWithMsg;
import com.app.skillontario.models.NotificationModal;
import com.app.skillontario.models.ResourceModal;
import com.app.skillontario.models.ScholarModel;
import com.app.skillontario.models.careerListModel.CareerListDetails;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.AdapterNotificationBinding;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

import static com.app.skillontario.constants.ApiConstants.API_INTERFACE;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> implements ApiResponseErrorCallback {


    Context context;
    ArrayList<NotificationModal> arrayListNotify = new ArrayList<>();
    String profileID;
    String id;

    public NotificationAdapter(Context context) {
        this.context = context;

    }

    public NotificationAdapter(ArrayList<NotificationModal> arrayListNotify, Context context) {
        this.arrayListNotify = arrayListNotify;
        this.context = context;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        try {
            return arrayListNotify.size();
        } catch (Exception e) {
            return 0;
        }

    }

    @Override
    public void onBindViewHolder(final NotificationAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        try {
            viewHolder.binding.imageBackground.setColorFilter(ContextCompat.getColor(context, R.color.noti_color1));
            //    viewHolder.binding.imageView.setImageResource(R.drawable.ic_shape__noti1);
            viewHolder.binding.tvTitle.setText(arrayListNotify.get(position).getTitle());
            viewHolder.binding.tvDesc.setText(arrayListNotify.get(position).getBody());
        } catch (Exception e) {
        }

        if (arrayListNotify.get(position).getNotifyType().equalsIgnoreCase("event")) {
            viewHolder.binding.imageView.setImageResource(R.drawable.ic_shape__noti3);
            viewHolder.binding.imageBackground.setColorFilter(ContextCompat.getColor(context, R.color.noti_color3));
        } else if (arrayListNotify.get(position).getNotifyType().equalsIgnoreCase("resource")) {
            viewHolder.binding.imageView.setImageResource(R.drawable.ic_shape__noti1);
            viewHolder.binding.imageBackground.setColorFilter(ContextCompat.getColor(context, R.color.noti_color1));
        } else if (arrayListNotify.get(position).getNotifyType().equalsIgnoreCase("mgsafai")) { //  scloarship
            viewHolder.binding.imageView.setImageResource(R.drawable.ic_shape__noti2);
            viewHolder.binding.imageBackground.setColorFilter(ContextCompat.getColor(context, R.color.noti_color2));
        } else if (arrayListNotify.get(position).getNotifyType().equalsIgnoreCase("profile")) {
            viewHolder.binding.imageView.setImageResource(R.drawable.ic_shape__noti1);
            viewHolder.binding.imageBackground.setColorFilter(ContextCompat.getColor(context, R.color.noti_color1));
        } else if (arrayListNotify.get(position).getNotifyType().equalsIgnoreCase("news")) {
            viewHolder.binding.imageView.setImageResource(R.drawable.ic_shape__noti4);
            viewHolder.binding.imageBackground.setColorFilter(ContextCompat.getColor(context, R.color.noti_color4));
        } else if (arrayListNotify.get(position).getNotifyType().equalsIgnoreCase("push")) {
            viewHolder.binding.imageView.setImageResource(R.drawable.ic_shape__noti4);
            viewHolder.binding.imageBackground.setColorFilter(ContextCompat.getColor(context, R.color.noti_color4));
        } else {
            viewHolder.binding.imageView.setImageResource(R.drawable.ic_shape__noti3);
            viewHolder.binding.imageBackground.setColorFilter(ContextCompat.getColor(context, R.color.noti_color3));
        }

        viewHolder.binding.openNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayListNotify.get(position).getNotifyType().equalsIgnoreCase("event")) {
                    Intent intent = new Intent(context, BottomBarActivity.class);
                    intent.putExtra("if", "2");
                    context.startActivity(intent);
                } else if (arrayListNotify.get(position).getNotifyType().equalsIgnoreCase("resource")) {
                    id = arrayListNotify.get(0).getId();
                    callCommanAPI(false, arrayListNotify.get(0).getId(), "resource", 101);


                } else if (arrayListNotify.get(position).getNotifyType().equalsIgnoreCase("mgsafai")) { //  scloarship

                    id = arrayListNotify.get(0).getId();
                    callCommanAPI(false, arrayListNotify.get(0).getId(), "mgsafai", 102);


                } else if (arrayListNotify.get(position).getNotifyType().equalsIgnoreCase("profile")) {
                    CallApi(arrayListNotify.get(position).getSectionId());
                    profileID = arrayListNotify.get(position).getSectionId();


                } else if (arrayListNotify.get(position).getNotifyType().equalsIgnoreCase("news")) {
                    Intent intent = new Intent(context, BottomBarActivity.class);
                    intent.putExtra("if", "2");
                    intent.putExtra("iff", "news");
                    context.startActivity(intent);
                } else if (arrayListNotify.get(position).getNotifyType().equalsIgnoreCase("push")) {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("url", arrayListNotify.get(position).getUrl());
                    context.startActivity(intent);
                }
            }
        });

    }

    @NotNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int position) {
        AdapterNotificationBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_notification, parent, false);
        parent.setId(position);
        parent.setFocusable(false);
        parent.setFocusableInTouchMode(false);

        return new NotificationAdapter.ViewHolder(binding);
    }

    public void addList(ArrayList<NotificationModal> output) {
        if (output != null) {
            if (output.size() > 0) {
                this.arrayListNotify.addAll(output);
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public void getApiResponse(Object responseObject, int flag) {
        if (flag == 121) {
            try {
                BaseResponseModel<ArrayList<CareerListDetails>> responseModel = (BaseResponseModel<ArrayList<CareerListDetails>>) responseObject;

                if (responseModel.getStatus()) {
                    try {
                        if (responseModel.getOutput() != null) {
                            if (responseModel.getOutput().size() > 0) {
                                Intent intent = new Intent(context, JobDetailsActivity.class);
                                intent.putExtra("Popular", profileID);
                                context.startActivity(intent);
                            } else {
                                showDialog();
                            }
                        } else {
                            showDialog();
                        }

                    } catch (Exception e) {
                        showDialog();
                    }
                }
            } catch (Exception e) {
                showDialog();
            }
        } else if (flag == 101) {
            BaseResponseModel<ArrayList<ScholarModel>> responseModel = (BaseResponseModel<ArrayList<ScholarModel>>) responseObject;
            try {
                if (responseModel.getStatus()) {
                    if (responseModel.getOutput().size() > 0) {
                        Intent intent = new Intent(context, ResourcesDetailsActivity.class);
                        intent.putExtra("id", id);
                        context.startActivity(intent);
                    } else {
                        showDialog();
                    }

                }
            } catch (Exception e) {
                showDialog();
            }

        } else if (flag == 102) {
            BaseResponseModel<ArrayList<ScholarModel>> responseModel = (BaseResponseModel<ArrayList<ScholarModel>>) responseObject;
            try {
                if (responseModel.getStatus()) {
                    if (responseModel.getOutput().size() > 0) {
                        Intent intent = new Intent(context, ScholarDetailAc.class);
                        intent.putExtra("id", id);
                        context.startActivity(intent);
                    } else {
                        showDialog();
                    }

                }
            } catch (Exception e) {
                showDialog();
            }


        }
    }

    @Override
    public void getApiError(Throwable t, int flag) {

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private AdapterNotificationBinding binding;

        public ViewHolder(AdapterNotificationBinding view) {
            super(view.getRoot());
            binding = view;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private void CallApi(String carrerId) {
        API_INTERFACE.getCareerList(RequestBodyGenerator.setCareerDetailData(MySharedPreference.getInstance().getStringData(SharedPrefsConstants.USER_ID), carrerId)).enqueue(
                new ApiCallBack<>(context, this, 121, false));
    }

    void showDialog() {
        try {
            DialogWithMsg dialogWithMsg = new DialogWithMsg(context, 0, context.getString(R.string.app_name), context.getString(R.string.pro), context.getString(R.string.okay), null,1);
            dialogWithMsg.show();
        } catch (Exception e) {
        }
    }

    void callCommanAPI(boolean custome, String id, String type, int flag) {
        API_INTERFACE.geteventListScholar(RequestBodyGenerator.getCommanAPI(id, type)).enqueue(
                new ApiCallBack<>(context, this, flag, custome));
    }

}