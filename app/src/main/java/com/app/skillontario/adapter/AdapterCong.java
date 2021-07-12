package com.app.skillontario.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
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
        return 6;
    }

    @Override
    public void onBindViewHolder(final AdapterCong.ViewHolder viewHolder, final int position) {

        if (position == 0) {
           // viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color1));
         //   viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color1));
            viewHolder.binding.ivHolder.setImageResource(R.drawable.new_person1);

            viewHolder.binding.textCons.setText("Photographer");
            viewHolder.binding.textWork.setText("Photographer");

        } else if (position == 1) {
        //    viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color2));
         //   viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color2));
            viewHolder.binding.ivHolder.setImageResource(R.drawable.new_person2);

            viewHolder.binding.textCons.setText("Industrial");
            viewHolder.binding.textWork.setText(R.string.gen);

        } else if (position == 2) {
          //  viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color1));
         //   viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color1));
            viewHolder.binding.ivHolder.setImageResource(R.drawable.new_person3);

            viewHolder.binding.textCons.setText("Service");
            viewHolder.binding.textWork.setText(R.string.ho);

        } else if (position == 3) {
         //   viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color2));
         //   viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color2));
            viewHolder.binding.ivHolder.setImageResource(R.drawable.new_person4);

            viewHolder.binding.textCons.setText("Industrial");
            viewHolder.binding.textWork.setText(R.string.in);

        } else if (position == 4) {
        //    viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color1));
        //    viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color1));
            viewHolder.binding.ivHolder.setImageResource(R.drawable.new_person5);

            viewHolder.binding.textCons.setText("Motive Power");
            viewHolder.binding.textWork.setText(R.string.tru);

        } else if (position == 5) {
        //    viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color2));
        //    viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color2));
            viewHolder.binding.ivHolder.setImageResource(R.drawable.new_person6);

            viewHolder.binding.textCons.setText("Service");
            viewHolder.binding.textWork.setText(R.string.childs);

        } else if (position == 6) {
         //   viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color1));
          //  viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color1));
            viewHolder.binding.ivHolder.setImageResource(R.drawable.new_person1);

            viewHolder.binding.textCons.setText("Photographer");
            viewHolder.binding.textWork.setText("Photographer");

        } else {
         //   viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color2));
         //   viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color2));
            viewHolder.binding.ivHolder.setImageResource(R.drawable.new_person1);

            viewHolder.binding.textCons.setText("Photographer");
            viewHolder.binding.textWork.setText("Photographer");
        }
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