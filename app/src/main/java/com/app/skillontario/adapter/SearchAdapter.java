package com.app.skillontario.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.models.careerListModel.CareerListDetails;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.AdapterSearchBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {


    Context context;
    boolean clickBookmark = false;
    ArrayList<CareerListDetails> list = new ArrayList<>();

    public SearchAdapter(Context context, ArrayList<CareerListDetails> list) {
        this.context = context;
        this.list = list;
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
    public void onBindViewHolder(final SearchAdapter.ViewHolder viewHolder, final int position) {


        viewHolder.binding.textCons.setText(list.get(position).getJobSector());
        viewHolder.binding.textWork.setText(list.get(position).getJobProfile());
        viewHolder.binding.textMoney.setText(list.get(position).getFee());


        viewHolder.binding.imgBookmark.setOnClickListener(v -> {
            if (clickBookmark) {
                clickBookmark = false;
                viewHolder.binding.imgBookmark.setImageResource(R.drawable.ic_home_main_batch);
            } else {
                clickBookmark = true;
                viewHolder.binding.imgBookmark.setImageResource(R.drawable.ic_bookmark_fill);
            }

        });


    }

    @NotNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int position) {
        AdapterSearchBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_search, parent, false);
        parent.setId(position);
        parent.setFocusable(false);
        parent.setFocusableInTouchMode(false);

        return new SearchAdapter.ViewHolder(binding);
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

}