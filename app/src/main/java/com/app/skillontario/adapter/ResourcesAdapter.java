package com.app.skillontario.adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillontario.activities.JobDetailsActivity;
import com.app.skillontario.activities.ResourcesDetailsActivity;
import com.app.skillontario.models.ResourceModal;
import com.app.skillontario.utils.Utils;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ResourcesItemBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ResourcesAdapter extends RecyclerView.Adapter<ResourcesAdapter.MyViewHolder> {
    ArrayList<ResourceModal> resourceModalArrayList;
    FragmentActivity activity;

    public ResourcesAdapter(ArrayList<ResourceModal> resourceModalArrayList, FragmentActivity activity) {
        this.resourceModalArrayList = resourceModalArrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ResourcesItemBinding resourcesItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.resources_item, parent, false);
        return new MyViewHolder(resourcesItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //  holder..ivItem

        try {
            // Picasso.with(activity).load(resourceModalArrayList.get(position).getResImage()).into(holder.resourcesItemBinding.ivItem);
            holder.resourcesItemBinding.tvTitle.setText(resourceModalArrayList.get(position).getResTitle());
            if (resourceModalArrayList.get(position).getCreatedAt() != null) {
                holder.resourcesItemBinding.tvDate.setText(changeDate(resourceModalArrayList.get(position).getCreatedAt()));
            }
            holder.resourcesItemBinding.llMain.setOnClickListener(v -> {
                Intent intent = new Intent(activity, ResourcesDetailsActivity.class);
                intent.putExtra("model", (Serializable) resourceModalArrayList.get(position));
                activity.startActivity(intent);
            });
        } catch (Exception e) {
        }

        try {
            Glide.with(activity)
                    .load(resourceModalArrayList.get(position).getResImage())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.resourcesItemBinding.progress.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.resourcesItemBinding.progress.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.resourcesItemBinding.ivItem);
        } catch (Exception e) {
        }


    }

    @Override
    public int getItemCount() {
        return resourceModalArrayList.size();
    }

    public void addList(ArrayList<ResourceModal> output) {
        if (output != null) {
            if (output.size() > 0) {
                this.resourceModalArrayList.addAll(output);
                notifyDataSetChanged();
            }
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ResourcesItemBinding resourcesItemBinding;

        public MyViewHolder(ResourcesItemBinding resourcesItemBinding) {
            super(resourcesItemBinding.getRoot());
            this.resourcesItemBinding = resourcesItemBinding;

        }
    }

    public String changeDate(String dateString) {
        String dateStr = "", timeStr = "", finalDate = "";
        try {

            // .//String dateString = dateN;

            String inPattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
            //   String outPatternDate = "yyyy-MM-dd";
            String outPatternDate = "LLL dd, yyyy";
            String outPatternTime = "HH:mm aa";

            SimpleDateFormat inFormat = new SimpleDateFormat(inPattern, Locale.getDefault());
            SimpleDateFormat outFormat = new SimpleDateFormat(outPatternDate, Locale.getDefault());
            SimpleDateFormat outFormatTime = new SimpleDateFormat(outPatternTime, Locale.getDefault());

            try {
                Date inDate = inFormat.parse(dateString);
                dateStr = outFormat.format(inDate);
                timeStr = outFormatTime.format(inDate);

                Log.e("TEST", dateStr);
                finalDate = "" + dateStr;
            } catch (ParseException e) {
                e.printStackTrace();
                finalDate = dateString;
            }
        } catch (Exception e) {
            e.printStackTrace();
            finalDate = dateString;
        }

        return finalDate;
    }
}
