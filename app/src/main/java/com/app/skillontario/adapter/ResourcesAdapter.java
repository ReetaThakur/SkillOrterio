package com.app.skillontario.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillontario.activities.ResourcesDetailsActivity;
import com.app.skillontario.models.ResourceModal;
import com.app.skillontario.utils.Utils;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ResourcesItemBinding;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

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
            Picasso.with(activity).load(resourceModalArrayList.get(position).getResImage()).into(holder.resourcesItemBinding.ivItem);
            holder.resourcesItemBinding.tvTitle.setText(resourceModalArrayList.get(position).getResTitle());
            if (resourceModalArrayList.get(position).getCreatedAt() != null) {
                holder.resourcesItemBinding.tvDate.setText(Utils.DateFormate(resourceModalArrayList.get(position).getCreatedAt()));
            }
            holder.resourcesItemBinding.llMain.setOnClickListener(v -> {
                Intent intent = new Intent(activity, ResourcesDetailsActivity.class);
                intent.putExtra("model", (Serializable) resourceModalArrayList.get(position));
                activity.startActivity(intent);
            });
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
}
