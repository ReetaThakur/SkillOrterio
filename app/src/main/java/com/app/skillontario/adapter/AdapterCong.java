package com.app.skillontario.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillontario.activities.JobDetailsActivity;
import com.app.skillontario.models.quizModel.QuizResultModel;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.FragmentQuizCBinding;
import com.app.skillorterio.databinding.FragmentQuizCBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterCong extends RecyclerView.Adapter<AdapterCong.ViewHolder> {


    Context context;
    boolean val = false;
    ArrayList<QuizResultModel> quizFinalResultModel = new ArrayList<>();

    public AdapterCong(Context contex, ArrayList<QuizResultModel> quizFinalResultModel) {
        this.context = contex;
        this.quizFinalResultModel = quizFinalResultModel;

    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        try {
            return quizFinalResultModel.size();
        } catch (Exception e) {
            return 0;
        }

    }

    @Override
    public void onBindViewHolder(final AdapterCong.ViewHolder viewHolder, final int position) {

        if (val) {
            val = false;
            viewHolder.binding.linBack.setBackgroundResource(R.drawable.rec_quiz_tanks1);
            //viewHolder.binding.ivHolder.setImageResource(R.drawable.new_person1);
           /* Glide.with(context)
                    .load(quizFinalResultModel.get(position).getImage())
                    .into(viewHolder.binding.ivHolder);
*/
            try {
                Glide.with(context)
                        .load(quizFinalResultModel.get(position).getImage())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                viewHolder.binding.progress.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                viewHolder.binding.progress.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(viewHolder.binding.ivHolder);
            }catch (Exception e){}

            viewHolder.binding.textCons.setText(quizFinalResultModel.get(position).getJobSector());
            viewHolder.binding.textWork.setText(quizFinalResultModel.get(position).getJobProfile());
            viewHolder.binding.textMoney.setText(quizFinalResultModel.get(position).getFee());

        } else {
            val = true;
            viewHolder.binding.linBack.setBackgroundResource(R.drawable.rec_quiz_tanks);
            /*Glide.with(context)
                    .load(quizFinalResultModel.get(position).getImage())
                    .into(viewHolder.binding.ivHolder);*/

            try {
                Glide.with(context)
                        .load(quizFinalResultModel.get(position).getImage())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                viewHolder.binding.progress.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                viewHolder.binding.progress.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(viewHolder.binding.ivHolder);
            }catch (Exception e){}


            viewHolder.binding.textCons.setText(quizFinalResultModel.get(position).getJobSector());
            viewHolder.binding.textWork.setText(quizFinalResultModel.get(position).getJobProfile());
            viewHolder.binding.textMoney.setText(quizFinalResultModel.get(position).getFee());
        }

        viewHolder.binding.maiLayClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, JobDetailsActivity.class);
                intent.putExtra("Popular", quizFinalResultModel.get(position).getId());
                context.startActivity(intent);

            }
        });

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

    public void addList(ArrayList<QuizResultModel> output) {
        if (output != null) {
            if (output.size() > 0) {
                this.quizFinalResultModel.addAll(output);
                notifyDataSetChanged();
            }
        }
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