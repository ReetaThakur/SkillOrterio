package com.app.skillontario.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillontario.activities.NewsDetailAc;
import com.app.skillontario.models.NewsModal;
import com.app.skillontario.utils.Utils;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.AdapterRecentNewsBinding;
import com.squareup.picasso.Picasso;


import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

public class RecentNewsAdapter extends RecyclerView.Adapter<RecentNewsAdapter.ViewHolder> {


    Context context;


    public RecentNewsAdapter(Context context, boolean popular) {
        this.context = context;

    }

    ArrayList<NewsModal> newsModalArrayList = new ArrayList<>();

    public RecentNewsAdapter(ArrayList<NewsModal> newsModalArrayList, Context activity) {
        this.newsModalArrayList = newsModalArrayList;
        this.context = activity;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return newsModalArrayList.size();
    }

    @Override
    public void onBindViewHolder(final RecentNewsAdapter.ViewHolder viewHolder, final int position) {

        Picasso.with(context).load(newsModalArrayList.get(position).getNewsImage()).
                error(R.drawable.place_holder_news_home).placeholder(R.drawable.place_holder_news_home).into(viewHolder.binding.ivImage);

        viewHolder.binding.tvTitle.setText(newsModalArrayList.get(position).getNewsTitle());
        if (newsModalArrayList.get(position).getCreatedAt() != null) {
            viewHolder.binding.tvDate.setText(Utils.DateFormateNews(newsModalArrayList.get(position).getCreatedAt()));
        }
        viewHolder.binding.rlAll.setOnClickListener(v -> {
            Intent intent = new Intent(context, NewsDetailAc.class);
            intent.putExtra("model", (Serializable) newsModalArrayList.get(position));
            context.startActivity(intent);
        });

    }

    @NotNull
    @Override
    public RecentNewsAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int position) {
        AdapterRecentNewsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_recent_news, parent, false);
        parent.setId(position);
        parent.setFocusable(false);
        parent.setFocusableInTouchMode(false);

        return new RecentNewsAdapter.ViewHolder(binding);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private AdapterRecentNewsBinding binding;

        public ViewHolder(AdapterRecentNewsBinding view) {
            super(view.getRoot());
            binding = view;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}