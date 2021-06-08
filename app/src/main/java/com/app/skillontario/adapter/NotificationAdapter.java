package com.app.skillontario.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillorterio.R;
import com.app.skillorterio.databinding.AdapterNotificationBinding;
import com.app.skillorterio.databinding.AdapterNotificationBinding;

import org.jetbrains.annotations.NotNull;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {


    Context context;


    public NotificationAdapter(Context context) {
        this.context = context;

    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public void onBindViewHolder(final NotificationAdapter.ViewHolder viewHolder, final int position) {
       /* if (popular)
            viewHolder.binding.imageView.setImageResource(imageArray[position]);
        else
            viewHolder.binding.imageView.setImageResource(imageArray1[position]);*/

        /*viewHolder.binding.lay.setOnClickListener(v -> {
            notifyItemChanged(selected_position);
            selected_position = position;
            notifyItemChanged(selected_position);
        });*/

        if (position == 0) {
            viewHolder.binding.imageBackground.setColorFilter(ContextCompat.getColor(context, R.color.noti_color1));
            viewHolder.binding.imageView.setImageResource(R.drawable.ic_shape__noti1);
            viewHolder.binding.tvTitle.setText("New Content");

        } else if (position == 1) {
            viewHolder.binding.imageBackground.setColorFilter(ContextCompat.getColor(context, R.color.noti_color2));
            viewHolder.binding.imageView.setImageResource(R.drawable.ic_shape__noti2);
            viewHolder.binding.tvTitle.setText("New Events");
        } else if (position == 2) {
            viewHolder.binding.imageBackground.setColorFilter(ContextCompat.getColor(context, R.color.noti_color3));
            viewHolder.binding.imageView.setImageResource(R.drawable.ic_shape__noti3);
            viewHolder.binding.tvTitle.setText("Upcoming Events");
        } else if (position == 3) {
            viewHolder.binding.imageBackground.setColorFilter(ContextCompat.getColor(context, R.color.noti_color4));
            viewHolder.binding.imageView.setImageResource(R.drawable.ic_shape__noti4);
            viewHolder.binding.tvTitle.setText("Broadcast Notification");
        }

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