package com.app.skillontario.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillontario.activities.JobDetailsActivity;
import com.app.skillontario.dialogs.GetClickBookmark;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.AdapterSearchBinding;
import com.app.skillontario.models.careerListModel.CareerListDetails;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    Context context;
    boolean clickBookmark = false;
    ArrayList<CareerListDetails> list = new ArrayList<>();
    GetClickBookmark listiner;

    public SearchAdapter(Context context, ArrayList<CareerListDetails> list, GetClickBookmark listiner) {
        this.context = context;
        this.list = list;
        this.listiner = listiner;
    }

    public void addList(ArrayList<CareerListDetails> listDetail) {
        if (listDetail != null) {
            if (listDetail.size() > 0) {
                this.list.addAll(listDetail);
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        /*viewHolder.binding.lay.setOnClickListener(v -> {
            notifyItemChanged(selected_position);
            selected_position = position;
            notifyItemChanged(selected_position);
        });*/
        // imagePerson
        Glide.with(context).load(list.get(position).getImage()).into(viewHolder.binding.imagePerson);
        viewHolder.binding.textCons.setText(list.get(position).getJobSector());
        viewHolder.binding.textWork.setText(list.get(position).getJobProfile());
        viewHolder.binding.textMoney.setText(list.get(position).getFee());
        if(list.get(position).getbId().equalsIgnoreCase("")){
            viewHolder.binding.imgBookmark.setBackgroundResource(R.drawable.ic_home_main_batch);
        }else {
            viewHolder.binding.imgBookmark.setBackgroundResource(R.drawable.ic_bookmark_fill);
        }
        viewHolder.binding.imgBookmark.setOnClickListener(v -> {
            if (listiner != null) {
                listiner.getValueBookmarkClick(false, list.get(position),position);
            }
        });
        viewHolder.binding.rlCareer.setOnClickListener(v->{
            Intent intent=new Intent(context, JobDetailsActivity.class);
            intent.putExtra("Popular", list.get(position).getId());
            context.startActivity(intent);
        });

    }
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int position) {
        AdapterSearchBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_search, parent, false);
        parent.setId(position);
        parent.setFocusable(false);
        parent.setFocusableInTouchMode(false);

        return new ViewHolder(binding);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private AdapterSearchBinding binding;

        public ViewHolder(AdapterSearchBinding view) {
            super(view.getRoot());
            binding = view;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void clearList() {
        try {
            if (list != null) {
                this.list.clear();
                notifyDataSetChanged();
            }
        } catch (Exception e) {
        }
    }

}