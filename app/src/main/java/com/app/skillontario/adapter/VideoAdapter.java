package com.app.skillontario.adapter;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillontario.activities.PlayVideoActivity;
import com.app.skillontario.models.ResourceURLModal;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.VideoAdapterBinding;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {


    Context context;
    String jobProfile;

    public VideoAdapter(Context context) {
        this.context = context;
    }

    ArrayList<ResourceURLModal> resourceURLModalArrayList;

    public VideoAdapter(ArrayList<ResourceURLModal> resourceURLModalArrayList, Context context, String jobProfile) {
        this.resourceURLModalArrayList = resourceURLModalArrayList;
        this.context = context;
        this.jobProfile = jobProfile;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        try {
            return resourceURLModalArrayList.size();
        } catch (Exception e) {
            return 0;
        }

    }

    @Override
    public void onBindViewHolder(final VideoAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        try {
            final String videoUrl = resourceURLModalArrayList.get(position).getPath();
            String videoId = videoUrl.split("v=")[1];
            String thumbnailHq = "http://img.youtube.com/vi/" + videoId + "/hqdefault.jpg"; //high quality thumbnail

            // "http://img.youtube.com/vi/bDtxF7qSofg/hqdefault.jpg

            Glide.with(context)
                    .load(thumbnailHq)
                    .into(viewHolder.binding.videoThumbnailImageView);
        } catch (Exception e) {
        }

        try {
            viewHolder.binding.textHelpTitle.setText("" +context.getResources().getString(R.string.helpful_and_relevant_resources)+
                    " "+context.getResources().getString(R.string.for1)+" "+jobProfile);
        } catch (Exception e) {
        }

        try {
            if (resourceURLModalArrayList.get(position).getPath()
                    .toLowerCase().contains("https://www.youtube.com/")) {
                viewHolder.binding.ley.setVisibility(View.VISIBLE);
                viewHolder.binding.viewNotYouTube.setVisibility(View.GONE);
            } else {
                viewHolder.binding.ley.setVisibility(View.GONE);
                viewHolder.binding.viewNotYouTube.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
        }

        viewHolder.binding.viewNotYouTube.setOnClickListener(v -> {
            String url = "";
            if (resourceURLModalArrayList.get(position).getPath().toLowerCase().contains("https") || resourceURLModalArrayList.get(position).getPath().toLowerCase().contains("http")) {
                url = "" + resourceURLModalArrayList.get(position).getPath().toLowerCase();
                url = url.replaceAll("\\s", "");
            } else {
                url = "https://" + resourceURLModalArrayList.get(position).getPath().toLowerCase();
                url = url.replaceAll("\\s", "");
            }

            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException ac) {
                    // Chrome browser presumably not installed and open Kindle Browser
                    intent.setPackage("com.amazon.cloud9");
                    context.startActivity(intent);
                }
            } catch (Exception e) {
            }
        });

        viewHolder.binding.ley.setOnClickListener(v -> {
            String url = "";
            if (resourceURLModalArrayList.get(position).getPath().toLowerCase().contains("https") || resourceURLModalArrayList.get(position).getPath().toLowerCase().contains("http")) {
                url = "" + resourceURLModalArrayList.get(position).getPath().toLowerCase();
                url = url.replaceAll("\\s", "");
            } else {
                url = "https://" + resourceURLModalArrayList.get(position).getPath().toLowerCase();
                url = url.replaceAll("\\s", "");
            }

            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException ac) {
                    // Chrome browser presumably not installed and open Kindle Browser
                    intent.setPackage("com.amazon.cloud9");
                    context.startActivity(intent);
                }
            } catch (Exception e) {
            }
        });


        viewHolder.binding.ivPlay.setOnClickListener(v -> {
            boolean contains = resourceURLModalArrayList.get(position).getPath()
                    .toLowerCase().contains("https://www.youtube.com/");

            /*if (contains == false)
                context.startActivity(new Intent(context, PlayVideoActivity.class));
            else {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(resourceURLModalArrayList.get(position).getPath()));
                    context.startActivity(intent);
                } catch (Exception e) {
                }*/

            ////new code
            String url = "";
            if (resourceURLModalArrayList.get(position).getPath().toLowerCase().contains("https") || resourceURLModalArrayList.get(position).getPath().toLowerCase().contains("http")) {
                url = "" + resourceURLModalArrayList.get(position).getPath().toLowerCase();
                url = url.replaceAll("\\s", "");
            } else {
                url = "https://" + resourceURLModalArrayList.get(position).getPath().toLowerCase();
                url = url.replaceAll("\\s", "");
            }

            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException ac) {
                    // Chrome browser presumably not installed and open Kindle Browser
                    intent.setPackage("com.amazon.cloud9");
                    context.startActivity(intent);
                }
            } catch (Exception e) {
            }

            /// end here


        });

    }

    @NotNull
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int position) {
        VideoAdapterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.video_adapter, parent, false);
        parent.setId(position);
        parent.setFocusable(false);
        parent.setFocusableInTouchMode(false);

        return new VideoAdapter.ViewHolder(binding);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private VideoAdapterBinding binding;

        public ViewHolder(VideoAdapterBinding view) {
            super(view.getRoot());
            binding = view;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}