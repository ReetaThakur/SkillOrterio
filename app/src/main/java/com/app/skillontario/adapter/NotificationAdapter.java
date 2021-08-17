package com.app.skillontario.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillontario.BottomBarActivity;
import com.app.skillontario.activities.JobDetailsActivity;
import com.app.skillontario.activities.ResourcesDetailsActivity;
import com.app.skillontario.activities.ScholarDetailAc;
import com.app.skillontario.activities.SplashActivity;
import com.app.skillontario.activities.WebViewActivity;
import com.app.skillontario.models.NotificationModal;
import com.app.skillontario.models.ResourceModal;
import com.app.skillontario.models.ScholarShipModal;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.AdapterNotificationBinding;
import com.app.skillorterio.databinding.AdapterNotificationBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {


    Context context;
    ArrayList<NotificationModal> arrayListNotify = new ArrayList<>();

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
    public void onBindViewHolder(final NotificationAdapter.ViewHolder viewHolder, final int position) {

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

                    ResourceModal model = new ResourceModal();
                    model.setResUrl(arrayListNotify.get(position).getUrl());
                    model.setId(arrayListNotify.get(position).getSectionId());
                    // model.setResImage(arrayListNotify.get(position).ge);
                    // model.setResDesc(arrayListNotify.get(position).getD);
                    Intent intent = new Intent(context, ResourcesDetailsActivity.class);
                    intent.putExtra("model", (Serializable) model);
                    context.startActivity(intent);

                } else if (arrayListNotify.get(position).getNotifyType().equalsIgnoreCase("mgsafai")) { //  scloarship

                    Intent intent = new Intent(context, ScholarDetailAc.class);
                    intent.putExtra("id", arrayListNotify.get(position).getSectionId());
                    context.startActivity(intent);

                } else if (arrayListNotify.get(position).getNotifyType().equalsIgnoreCase("profile")) {
                    Intent intent = new Intent(context, JobDetailsActivity.class);
                    intent.putExtra("Popular", arrayListNotify.get(position).getSectionId());
                    context.startActivity(intent);

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

}