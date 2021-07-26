package com.app.skillontario.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillontario.activities.NewsDetailAc;
import com.app.skillontario.models.EventsModal;
import com.app.skillontario.models.NewsModal;
import com.app.skillontario.utils.Utils;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.EventsItemBinding;
import com.app.skillorterio.databinding.NewsItemBinding;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

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
            Picasso.with(activity).load(newsModalArrayList.get(position).getNewsImage()).error(R.drawable.place_holder_news).placeholder(R.drawable.place_holder_news).into(holder.newsItemBinding.ivItem);
            holder.newsItemBinding.tvHead.setText(newsModalArrayList.get(position).getNewsTitle());
            if (newsModalArrayList.get(position).getNewsDate() != null) {
                holder.newsItemBinding.tvDate.setText(Utils.DateFormateNews(newsModalArrayList.get(position).getNewsDate()));
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
        return 10;
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
}
