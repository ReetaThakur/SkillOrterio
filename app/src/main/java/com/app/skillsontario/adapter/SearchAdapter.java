package com.app.skillsontario.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillsontario.R;
import com.app.skillsontario.activities.JobDetailsActivity;
import com.app.skillsontario.callbacks.GetClickBookmark;
import com.app.skillsontario.constants.SharedPrefsConstants;
import com.app.skillsontario.databinding.AdapterSearchBinding;
import com.app.skillsontario.utils.MySharedPreference;

import com.app.skillsontario.models.careerListModel.CareerListDetails;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

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
       // Glide.with(context).load(list.get(position).getImage()).centerCrop().into(viewHolder.binding.imagePerson);


        try {
            try {
                Glide.with(context)
                        .load(list.get(position).getImage())
                        .centerCrop()
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                              try {
                                  viewHolder.binding.progress.setVisibility(View.GONE);
                              }catch (Exception ee){}

                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                try {
                                    viewHolder.binding.progress.setVisibility(View.GONE);
                                }catch (Exception ee){}
                                return false;
                            }
                        })
                        .into(viewHolder.binding.imagePerson);
            } catch (Exception e) {
            }

        } catch (Exception e) {
        }


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