package com.app.skillsontario.adapter;

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

import com.app.skillsontario.R;
import com.app.skillsontario.activities.NewsDetailAc;
import com.app.skillsontario.databinding.NewsItemBinding;
import com.app.skillsontario.models.NewsModal;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    ArrayList<NewsModal> newsModalArrayList;
    FragmentActivity activity;

    public NewsAdapter(ArrayList<NewsModal> newsModalArrayList, FragmentActivity activity) {
        this.newsModalArrayList = newsModalArrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewsItemBinding newsItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.news_item, parent, false);
        return new NewsAdapter.MyViewHolder(newsItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try {
            Glide.with(activity)
                    .load(newsModalArrayList.get(position).getNewsImage())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.newsItemBinding.progress.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.newsItemBinding.progress.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.newsItemBinding.ivItem);
        }catch (Exception e){}

        try {
          //  Picasso.with(activity).load(newsModalArrayList.get(position).getNewsImage()).error(R.drawable.place_holder_news).placeholder(R.drawable.place_holder_news).into(holder.newsItemBinding.ivItem);
            holder.newsItemBinding.tvHead.setText(newsModalArrayList.get(position).getNewsTitle());
            if (newsModalArrayList.get(position).getCreatedAt() != null) {
                holder.newsItemBinding.tvDate.setText(changeDate(newsModalArrayList.get(position).getCreatedAt()));
            }
            holder.newsItemBinding.llMain.setOnClickListener(v -> {
                Intent intent = new Intent(activity, NewsDetailAc.class);
                intent.putExtra("model", (Serializable) newsModalArrayList.get(position));
                activity.startActivity(intent);
            });
        } catch (Exception e) {
        }


        //  holder..ivItem
        //  Picasso.with(activity).load(newsModalArrayList.get(position).getNewsImage()).into(holder.newsItemBinding.ivItem);
        //  holder.newsItemBinding.tvHead.setText(newsModalArrayList.get(position).getNewsTitle());
        //  holder.newsItemBinding.tvDate.setText(Utils.DateFormate(newsModalArrayList.get(position).getNewsDate()));
    }

    @Override
    public int getItemCount() {
        return newsModalArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        NewsItemBinding newsItemBinding;

        public MyViewHolder(NewsItemBinding newsItemBinding) {
            super(newsItemBinding.getRoot());
            this.newsItemBinding = newsItemBinding;

        }
    }

    public void addList(ArrayList<NewsModal> output) {
        if (output != null) {
            if (output.size() > 0) {
                this.newsModalArrayList.addAll(output);
                notifyDataSetChanged();
            }


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
