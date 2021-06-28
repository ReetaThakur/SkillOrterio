package com.app.skillontario.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillorterio.R;
import com.app.skillorterio.databinding.FragmentQuizCBinding;
import com.app.skillorterio.databinding.FragmentQuizCBinding;

import org.jetbrains.annotations.NotNull;

public class AdapterCong extends RecyclerView.Adapter<AdapterCong.ViewHolder> {


    Context context;


    public AdapterCong(Context contex) {
        this.context = context;

    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public void onBindViewHolder(final AdapterCong.ViewHolder viewHolder, final int position) {


    }

    @NotNull
    @Override
    public AdapterCong.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int position) {
        FragmentQuizCBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.fragment_quiz_c, parent, false);
        parent.setId(position);
        parent.setFocusable(false);
        parent.setFocusableInTouchMode(false);

        return new AdapterCong.ViewHolder(binding);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private FragmentQuizCBinding binding;

        public ViewHolder(FragmentQuizCBinding view) {
            super(view.getRoot());
            binding = view;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}