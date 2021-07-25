package com.app.skillontario.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillontario.models.NotificationModal;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.AdapterNotificationBinding;
import com.app.skillorterio.databinding.AdapterNotificationBinding;

import org.jetbrains.annotations.NotNull;

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
            viewHolder.binding.imageView.setImageResource(R.drawable.ic_shape__noti1);
            viewHolder.binding.tvTitle.setText(arrayListNotify.get(position).getTitle());
            viewHolder.binding.tvDesc.setText(arrayListNotify.get(position).getBody());
        } catch (Exception e) {
        }

     /*   if (position == 0) {
            viewHolder.binding.imageBackground.setColorFilter(ContextCompat.getColor(context, R.color.noti_color1));
            viewHolder.binding.imageView.setImageResource(R.drawable.ic_shape__noti1);
            viewHolder.binding.tvTitle.setText("New Content");
            viewHolder.binding.tvDesc.setText("Read about scholarships, news stories, and more!");

        } else if (position == 1) {
            viewHolder.binding.imageBackground.setColorFilter(ContextCompat.getColor(context, R.color.noti_color2));
            viewHolder.binding.imageView.setImageResource(R.drawable.ic_shape__noti2);
            viewHolder.binding.tvTitle.setText("New Events");
            viewHolder.binding.tvDesc.setText("Check out events recently announced by Skills Ontario.");
        } else if (position == 2) {
            viewHolder.binding.imageBackground.setColorFilter(ContextCompat.getColor(context, R.color.noti_color3));
            viewHolder.binding.imageView.setImageResource(R.drawable.ic_shape__noti3);
            viewHolder.binding.tvTitle.setText("Upcoming Events");
            viewHolder.binding.tvDesc.setText("Mark your calendar for these events!");
        } else if (position == 3) {
            viewHolder.binding.imageBackground.setColorFilter(ContextCompat.getColor(context, R.color.noti_color4));
            viewHolder.binding.imageView.setImageResource(R.drawable.ic_shape__noti4);
            viewHolder.binding.tvTitle.setText("Broadcast Notification");
            viewHolder.binding.tvDesc.setText("Mark your calendar for these events!");
        }*/

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