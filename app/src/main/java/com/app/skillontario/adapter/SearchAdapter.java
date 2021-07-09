package com.app.skillontario.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillontario.activities.JobDetailsActivity;
import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.models.careerListModel.CareerListDetails;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.AdapterSearchBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {


    Context context;
    boolean clickBookmark = false;
    ArrayList<CareerListDetails> list = new ArrayList<>();

    public SearchAdapter(Context context, ArrayList<CareerListDetails> list) {
        this.context = context;
        this.list = list;
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
        return 6;
    }

    @Override
    public void onBindViewHolder(final SearchAdapter.ViewHolder viewHolder, final int position) {


        if (position == 0) {
            //  viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color1));
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color1));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person1);

            viewHolder.binding.textCons.setText("Photographer");
            viewHolder.binding.textWork.setText("Photographer");

        } else if (position == 1) {
            // viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color2));
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.text_error_color));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person2);

            viewHolder.binding.textCons.setText("Industrial");
            viewHolder.binding.textWork.setText("General Machinist");

        } else if (position == 2) {
            //viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color1));
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color3));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person3);

            viewHolder.binding.textCons.setText("Service");
            viewHolder.binding.textWork.setText("Horticultural Technician");

        } else if (position == 3) {
            //   viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color2));
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color2));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person4);

            viewHolder.binding.textCons.setText("Industrial");
            viewHolder.binding.textWork.setText("Industrial Mechanic");

        } else if (position == 4) {
            // viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color1));
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color1));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person5);

            viewHolder.binding.textCons.setText("Motive Power");
            viewHolder.binding.textWork.setText("Truck and Coach Technician");

        } else if (position == 5) {
            // viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color2));
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.text_error_color));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person6);

            viewHolder.binding.textCons.setText("Service");
            viewHolder.binding.textWork.setText("Child & Youth Worker");

        } else if (position == 6) {
            // viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color1));
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color3));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person1);

            viewHolder.binding.textCons.setText("Photographer");
            viewHolder.binding.textWork.setText("Photographer");

        } else {
            //  viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color2));
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color1));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person1);

            viewHolder.binding.textCons.setText("Photographer");
            viewHolder.binding.textWork.setText("Photographer");
        }


        viewHolder.binding.imgBookmark.setOnClickListener(v -> {
            if (clickBookmark) {
                clickBookmark = false;
                viewHolder.binding.imgBookmark.setImageResource(R.drawable.ic_home_main_batch);
            } else {
                clickBookmark = true;
                viewHolder.binding.imgBookmark.setImageResource(R.drawable.ic_bookmark_fill);
            }

        });

        viewHolder.binding.cdH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopularCareerAdapter.numberOfPerson = position;
                context.startActivity(new Intent(context, JobDetailsActivity.class));
            }
        });
    }

    @NotNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int position) {
        AdapterSearchBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_search, parent, false);
        parent.setId(position);
        parent.setFocusable(false);
        parent.setFocusableInTouchMode(false);

        return new SearchAdapter.ViewHolder(binding);
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

}