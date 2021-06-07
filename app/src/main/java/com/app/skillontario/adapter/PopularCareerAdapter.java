package com.app.skillontario.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillorterio.R;
import com.app.skillorterio.databinding.AdapterPopularCarrerBinding;

import org.jetbrains.annotations.NotNull;

public class PopularCareerAdapter extends RecyclerView.Adapter<PopularCareerAdapter.ViewHolder> {


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

    public PopularCareerAdapter(Context context, boolean popular) {
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
    public void onBindViewHolder(final PopularCareerAdapter.ViewHolder viewHolder, final int position) {
        if (popular)
            viewHolder.binding.imageView.setImageResource(imageArray[position]);
        else
            viewHolder.binding.imageView.setImageResource(imageArray1[position]);

        /*viewHolder.binding.lay.setOnClickListener(v -> {
            notifyItemChanged(selected_position);
            selected_position = position;
            notifyItemChanged(selected_position);
        });*/
    }

    @NotNull
    @Override
    public PopularCareerAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int position) {
        AdapterPopularCarrerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_popular_carrer, parent, false);
        parent.setId(position);
        parent.setFocusable(false);
        parent.setFocusableInTouchMode(false);

        return new PopularCareerAdapter.ViewHolder(binding);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private AdapterPopularCarrerBinding binding;

        public ViewHolder(AdapterPopularCarrerBinding view) {
            super(view.getRoot());
            binding = view;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}