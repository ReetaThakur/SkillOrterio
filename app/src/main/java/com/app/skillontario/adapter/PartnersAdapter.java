package com.app.skillontario.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.skillontario.callbacks.KeywordSelected;
import com.app.skillontario.models.PlatinumModal;
import com.app.skillorterio.R;
import com.app.skillontario.activities.NewsDetailAc;
import com.app.skillontario.activities.PartnersActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PartnersAdapter extends RecyclerView.Adapter<PartnersAdapter.ViewHolder> {

    private ArrayList<PlatinumModal> listingDetails;
    private Context context;
    private KeywordSelected callback;

    public PartnersAdapter(ArrayList<PlatinumModal> listingDetails, PartnersActivity context,KeywordSelected callback) {
        this.context = context;
        this.listingDetails =listingDetails;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.partners_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Picasso.with(context).load(listingDetails.get(position).getLogo()).into( holder.iv_image);
        holder.iv_image.setOnClickListener(v->{
           // callback.onTextClick(listingDetails.get(position).getWebUrl());
            Intent intent=new Intent(context, NewsDetailAc.class);
            intent.putExtra("url",listingDetails.get(position).getWebUrl());
            intent.putExtra("title",listingDetails.get(position).getTitle());
            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return listingDetails.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_image;

        public ViewHolder(View v) {
            super(v);
            iv_image=v.findViewById(R.id.iv_image);
        }

    }
}