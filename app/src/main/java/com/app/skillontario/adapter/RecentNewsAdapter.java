package com.app.skillontario.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillorterio.R;
import com.app.skillorterio.databinding.AdapterRecentNewsBinding;


import org.jetbrains.annotations.NotNull;

public class RecentNewsAdapter extends RecyclerView.Adapter<RecentNewsAdapter.ViewHolder> {


    Context context;
    boolean popular = false;
    private int[] imageArray = {
            R.drawable.recy1,
            R.drawable.recy2
    };

    private int[] imageArray1 = {
            R.drawable.recent_event,
            R.drawable.recent_event
    };

    public RecentNewsAdapter(Context context, boolean popular) {
        this.context = context;
        this.popular = popular;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (popular)
            return imageArray.length;
        else
            return imageArray1.length;
    }

    @Override
    public void onBindViewHolder(final RecentNewsAdapter.ViewHolder viewHolder, final int position) {
       /* if (popular)
            viewHolder.binding.imageView.setImageResource(imageArray[position]);
        else
            viewHolder.binding.imageView.setImageResource(imageArray1[position]);*/

        /*viewHolder.binding.lay.setOnClickListener(v -> {
            notifyItemChanged(selected_position);
            selected_position = position;
            notifyItemChanged(selected_position);
        });*/

       /* if (position == 0) {
            viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color1));
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color1));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.home_main_img1);

        } else {
            viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color2));
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color2));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.home_main_img2);
        }*/

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