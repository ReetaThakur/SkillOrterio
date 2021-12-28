package com.app.skillsontario.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;



import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillsontario.R;

public class PartnersPremiumAdapter extends RecyclerView.Adapter<PartnersPremiumAdapter.ViewHolder> {

    private ArrayList<String> listingDetails;
    private Context context;

    public PartnersPremiumAdapter(Context context, List<String> listingDetails) {
        this.context = context;
        this.listingDetails = new ArrayList<>(listingDetails);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.partners_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.bindData(context, position);
        //  holder.info.setOnClickListener(v -> Utils.showToast(context,"You must be a registered breeder in order to sell cats and dogs on the Alpha Match platform."));
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

    public void addList(ArrayList<String> listDetail) {
        this.listingDetails.addAll(listDetail);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView pet;

        public ViewHolder(View v) {
            super(v);
            //pet = v.findViewById(R.id.iv_pet_img);
        }

        void bindData(Context context, int position) {
        }
    }
}