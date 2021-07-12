package com.app.skillontario.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaController2;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillorterio.R;
import com.app.skillorterio.databinding.AdapterNotificationBinding;
import com.app.skillorterio.databinding.QuizStepItemABinding;
import com.app.skillorterio.databinding.QuizStepItemBBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class QuizStepAdapter extends RecyclerView.Adapter<QuizStepAdapter.ViewHolder> {
    Context context;
    //int count;
    ArrayList<String> answerList = new ArrayList<>();

    public QuizStepAdapter(Context context, int count, ArrayList<String> answerList) {
        this.context = context;
      //  this.count = count;
        this.answerList = answerList;
    }

    String hold = "";
  /*  String[] qus = new String[]{
            "Excited/Fulfilling",
            "Challenging/Intriguing",
            "Anxious/Draining",
            "Indifferent/Neutral"};*/

    String[] opt = new String[]{
            "A", "B", "C", "D","E","F","G"};

  /*  String[] optB = new String[]{
            "Excited",
            "Challenging",
            "Anxious",
            "Indifferent"
    };*/

    // int[] optBimg=new int[]{R.drawable.o_a,R.drawable.o_b,R.drawable.o_c,R.drawable.o_d};


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    @Override
    public void onBindViewHolder(final QuizStepAdapter.ViewHolder viewHolder, final int position) {

      /*  if(count==2){
            viewHolder.bindingb.ivOption.setImageResource(optBimg[position]);
            viewHolder.bindingb.tvTitle.setText(optB[position]);
            if (hold.equals(String.valueOf(position))) {
                viewHolder.bindingb.row.setImageResource(R.drawable.ic_selected_click);
             }
            viewHolder.bindingb.row.setOnClickListener(view -> {
                if (hold.equals(""))
                    hold = String.valueOf(position);
                notifyDataSetChanged();
            });
        }else {*/

        viewHolder.binding.tvOption.setText(opt[position]);
        viewHolder.binding.tvTitle.setText(answerList.get(position));
        if (hold.equals(String.valueOf(position))) {
            viewHolder.binding.row.getBackground().setColorFilter(Color.parseColor("#34C759"), PorterDuff.Mode.SRC_ATOP);
            viewHolder.binding.ivOption.setColorFilter(ContextCompat.getColor(context, R.color.select), android.graphics.PorterDuff.Mode.MULTIPLY);
            viewHolder.binding.tvOption.setTextColor(context.getResources().getColor(R.color.white));
            viewHolder.binding.tvTitle.setTextColor(context.getResources().getColor(R.color.white));
        }
        viewHolder.binding.row.setOnClickListener(view -> {
            if (hold.equals(""))
                hold = String.valueOf(position);
            notifyDataSetChanged();
        });
        // }

    }

    @NotNull
    @Override
    public QuizStepAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int position) {
       /* if (count == 2) {
            QuizStepItemBBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.quiz_step_item_b, parent, false);
            return new QuizStepAdapter.ViewHolder(binding);
        } else {*/
            QuizStepItemABinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.quiz_step_item_a, parent, false);
            return new QuizStepAdapter.ViewHolder(binding);
        //}
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private QuizStepItemABinding binding;

        public ViewHolder(QuizStepItemABinding view) {
            super(view.getRoot());
            binding = view;
        }

        private QuizStepItemBBinding bindingb;

        public ViewHolder(QuizStepItemBBinding view) {
            super(view.getRoot());
            bindingb = view;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}