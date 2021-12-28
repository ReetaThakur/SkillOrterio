package com.app.skillsontario.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillsontario.R;
import com.app.skillsontario.databinding.QuizStepItemABinding;
import com.app.skillsontario.databinding.QuizStepItemBBinding;
import com.app.skillsontario.interfaceClass.Controller;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class QuizStepAdapter extends RecyclerView.Adapter<QuizStepAdapter.ViewHolder> {
    Context context;
    int count;
    Controller controller;
    ArrayList<String> questionList = new ArrayList<>();
    ArrayList<String> optList = new ArrayList<>();
    // boolean checkClick = false;


    public QuizStepAdapter(Context context, int count, ArrayList<String> questionList, Controller controller, ArrayList<String> optList) {
        this.context = context;
        this.count = count;
        this.questionList = questionList;
        this.controller = controller;
        hold = "";
        this.optList = optList;
    }

    String hold = "";


  /*  String[] opt = new String[]{
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};*/

    String[] optB = new String[]{  // not use
            "Excited",
            "Challenging",
            "Anxious",
            "Indifferent"
    };

    int[] optBimg = new int[]{R.drawable.o_a, R.drawable.o_b, R.drawable.o_c, R.drawable.o_d};


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {

        try {
            return questionList.size();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        if (count == 2) {
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
        } else {  /// work on this
            viewHolder.binding.tvOption.setText(optList.get(position));
            viewHolder.binding.tvTitle.setText(questionList.get(position));
            if (hold.equals(String.valueOf(position))) {
                viewHolder.binding.row.getBackground().setColorFilter(Color.parseColor("#34C759"), PorterDuff.Mode.SRC_ATOP);
                viewHolder.binding.ivOption.setColorFilter(ContextCompat.getColor(context, R.color.select), PorterDuff.Mode.MULTIPLY);
                viewHolder.binding.tvOption.setTextColor(context.getResources().getColor(R.color.white));
                viewHolder.binding.tvTitle.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                viewHolder.binding.row.getBackground().clearColorFilter();
                viewHolder.binding.ivOption.clearColorFilter();
                viewHolder.binding.tvOption.setTextColor(context.getResources().getColor(R.color.white));
                viewHolder.binding.tvTitle.setTextColor(context.getResources().getColor(R.color.black_semi));
            }
            viewHolder.binding.row.setOnClickListener(view -> {
                if (controller != null) {
                    controller.callback(position);

                }
                try {
                    hold = String.valueOf(position);
                } catch (Exception e) {
                }

                notifyDataSetChanged();
            });
        }

    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int position) {
        if (count == 2) {
            QuizStepItemBBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.quiz_step_item_b, parent, false);
            return new ViewHolder(binding);
        } else {
            QuizStepItemABinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.quiz_step_item_a, parent, false);
            return new ViewHolder(binding);
        }
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