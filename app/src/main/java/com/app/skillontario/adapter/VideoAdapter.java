package com.app.skillontario.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

    public VideoAdapter(Context context) {
        this.context = context;
    }

    ArrayList<ResourceURLModal> resourceURLModalArrayList;

    public VideoAdapter(ArrayList<ResourceURLModal> resourceURLModalArrayList, Context context) {
        this.resourceURLModalArrayList = resourceURLModalArrayList;
        this.context = context;
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
    public void onBindViewHolder(final VideoAdapter.ViewHolder viewHolder, final int position) {

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


        viewHolder.binding.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean contains = resourceURLModalArrayList.get(position).getPath()
                        .toLowerCase().contains("https://www.youtube.com/");


                if (contains == false)
                    context.startActivity(new Intent(context, PlayVideoActivity.class));
                else {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(resourceURLModalArrayList.get(position).getPath()));
                        context.startActivity(intent);
                    } catch (Exception e) {
                    }
                }
            }
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