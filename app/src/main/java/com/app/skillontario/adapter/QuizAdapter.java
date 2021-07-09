package com.app.skillontario.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
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
        return 6;
    }

    @Override
    public void onBindViewHolder(final QuizAdapter.ViewHolder viewHolder, final int position) {


        //  viewHolder.binding.lyMain.setOnClickListener(v -> context.startActivity(new Intent(context, JobDetailsActivity.class)));
        // viewHolder.binding.mainLay.setOnClickListener(v -> context.startActivity(new Intent(context, JobDetailsActivity.class)));

        viewHolder.binding.ivBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickBookmark) {
                    clickBookmark = false;
                    viewHolder.binding.ivBookmark.setImageResource(R.drawable.ic_home_main_batch);
                } else {
                    clickBookmark = true;
                    viewHolder.binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_fill);
                }
            }
        });


        if (position == 0) {
            // viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color1));
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color1));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person1);

            viewHolder.binding.textCons.setText("Photographer");
            viewHolder.binding.textWork.setText("Photographer");

        } else if (position == 1) {
            //  viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color2));
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color2));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person2);

            viewHolder.binding.textCons.setText("Industrial");
            viewHolder.binding.textWork.setText(R.string.gen);

        } else if (position == 2) {
            // viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color1));
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color1));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person3);

            viewHolder.binding.textCons.setText("Service");
            viewHolder.binding.textWork.setText(R.string.ho);

        } else if (position == 3) {
            //  viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color2));
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color2));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person4);

            viewHolder.binding.textCons.setText("Industrial");
            viewHolder.binding.textWork.setText(R.string.in);

        } else if (position == 4) {
            // viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color1));
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color1));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person5);

            viewHolder.binding.textCons.setText("Motive Power");
            viewHolder.binding.textWork.setText(R.string.tru);

        } else if (position == 5) {
            //  viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color2));
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color2));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person6);

            viewHolder.binding.textCons.setText("Service");
            viewHolder.binding.textWork.setText(R.string.childs);

        } else if (position == 6) {
            //  viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color1));
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color1));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person1);

            viewHolder.binding.textCons.setText("Photographer");
            viewHolder.binding.textWork.setText("Photographer");

        } else {
            //   viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color2));
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color2));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person1);

            viewHolder.binding.textCons.setText("Photographer");
            viewHolder.binding.textWork.setText("Photographer");
        }

        viewHolder.binding.imagePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopularCareerAdapter.numberOfPerson = position;
                context.startActivity(new Intent(context, JobDetailsActivity.class));
            }
        });

        viewHolder.binding.lyMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopularCareerAdapter.numberOfPerson = position;
                context.startActivity(new Intent(context, JobDetailsActivity.class));
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
