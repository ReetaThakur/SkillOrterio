package com.app.skillsontario.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillsontario.R;
import com.app.skillsontario.activities.ScholarDetailAc;
import com.app.skillsontario.activities.ScholarOneAc;
import com.app.skillsontario.databinding.ScollarItem1Binding;
import com.app.skillsontario.models.ScholarShipModal;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class ScholarAdapter extends RecyclerView.Adapter<ScholarAdapter.MyViewHolder> {
    ArrayList<ScholarShipModal> scholarShipModalArrayList;
    ScholarOneAc activity;

    public ScholarAdapter(ArrayList<ScholarShipModal> scholarShipModalArrayList, ScholarOneAc scholarOneAc) {
        this.scholarShipModalArrayList = scholarShipModalArrayList;
        this.activity = scholarOneAc;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ScollarItem1Binding scollarItem1Binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.scollar_item1, parent, false);
        return new MyViewHolder(scollarItem1Binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.scollarItem1Binding.tvHead.setText(scholarShipModalArrayList.get(position).getTitle());
        Picasso.with(activity).load(scholarShipModalArrayList.get(position).getImage()).into(holder.scollarItem1Binding.ivItem);

        holder.scollarItem1Binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ScholarDetailAc.class);
                intent.putExtra("scholar", (Serializable) scholarShipModalArrayList.get(position));
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return scholarShipModalArrayList.size();
    }

    public void addList(ArrayList<ScholarShipModal> listDetail) {
        if (listDetail != null) {
            if (listDetail.size() > 0) {
                this.scholarShipModalArrayList.addAll(listDetail);
                notifyDataSetChanged();
            }
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ScollarItem1Binding scollarItem1Binding;

        public MyViewHolder(ScollarItem1Binding scollarItem1Binding) {
            super(scollarItem1Binding.getRoot());
            this.scollarItem1Binding = scollarItem1Binding;

        }
    }
}
