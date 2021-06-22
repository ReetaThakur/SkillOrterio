package com.app.skillontario.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillontario.activities.JobDetailsActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.AdapterQuizBinding;
import com.app.skillorterio.databinding.AdapterQuizBinding;

import org.jetbrains.annotations.NotNull;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {

    boolean clickBookmark = false;
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

    public QuizAdapter(Context context, boolean popular) {
        this.context = context;
        this.popular = popular;
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
    public void onBindViewHolder(final QuizAdapter.ViewHolder viewHolder, final int position) {
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

        viewHolder.binding.lyMain.setOnClickListener(v -> context.startActivity(new Intent(context, JobDetailsActivity.class)));
       // viewHolder.binding.mainLay.setOnClickListener(v -> context.startActivity(new Intent(context, JobDetailsActivity.class)));

        viewHolder.binding.ivBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickBookmark) {
                    clickBookmark = false;
                    viewHolder.binding.ivBookmark.setImageResource(R.drawable.ic_home_main_batch);
                } else {
                    clickBookmark=true;
                    viewHolder.binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_fill);
                }
            }
        });

    }

    @NotNull
    @Override
    public QuizAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int position) {
        AdapterQuizBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_quiz, parent, false);
        parent.setId(position);
        parent.setFocusable(false);
        parent.setFocusableInTouchMode(false);

        return new QuizAdapter.ViewHolder(binding);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private AdapterQuizBinding binding;

        public ViewHolder(AdapterQuizBinding view) {
            super(view.getRoot());
            binding = view;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
