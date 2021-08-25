package com.app.skillontario.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillontario.activities.JobDetailsActivity;
import com.app.skillontario.callbacks.GetClickBookmark;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.utils.MySharedPreference;
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        // imagePerson
        Glide.with(context).load(list.get(position).getImage()).into(viewHolder.binding.imagePerson);
        viewHolder.binding.textCons.setText(list.get(position).getJobSector());
        viewHolder.binding.textWork.setText(list.get(position).getJobProfile());
        viewHolder.binding.textMoney.setText(list.get(position).getFee());

        if (list.get(position).getbId().equalsIgnoreCase("")) {
            viewHolder.binding.imgBookmark.setBackgroundResource(R.drawable.bookmark_not_fill);
            viewHolder.binding.cdH.setCardBackgroundColor(context.getColor(R.color.white));
        } else {
            viewHolder.binding.cdH.setCardBackgroundColor(context.getColor(R.color.bookmark_color));
            viewHolder.binding.imgBookmark.setBackgroundResource(R.drawable.ic_bookmark_fill);
        }
        viewHolder.binding.imgBookmark.setOnClickListener(v -> {
            if (!MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.GUEST_FLOW)) {
                if (list.get(position).getbId().equalsIgnoreCase("")) {
                    viewHolder.binding.imgBookmark.setBackgroundResource(R.drawable.ic_bookmark_fill);
                } else {
                    viewHolder.binding.imgBookmark.setBackgroundResource(R.drawable.bookmark_not_fill);
                }
            }

            if (listiner != null) {
                listiner.getValueBookmarkClick(false, list.get(position), position);
            }


        });
        viewHolder.binding.cdH.setOnClickListener(v -> {
            Intent intent = new Intent(context, JobDetailsActivity.class);
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