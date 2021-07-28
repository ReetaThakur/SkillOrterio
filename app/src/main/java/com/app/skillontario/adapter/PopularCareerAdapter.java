package com.app.skillontario.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.app.skillontario.activities.JobDetailsActivity;
import com.app.skillontario.models.CareerModal;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.AdapterPopularCarrerBinding;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PopularCareerAdapter extends RecyclerView.Adapter<PopularCareerAdapter.ViewHolder> {


    Context context;
    public static int numberOfPerson;
    boolean clickBookmark = false;
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

    ArrayList<CareerModal> careerModalArrayList;
    BookMarkUpdateDelete bookMarkUpdateDelete;

    public PopularCareerAdapter(ArrayList<CareerModal> careerModalArrayList, Context activitiy, boolean popular, BookMarkUpdateDelete bookMarkUpdateDelete) {
        this.careerModalArrayList = careerModalArrayList;
        this.context = activitiy;
        this.popular = popular;
        this.bookMarkUpdateDelete = bookMarkUpdateDelete;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        try {
            if (popular)
                return 0;
            else
                return careerModalArrayList.size();
        } catch (Exception e) {
            return 0;
        }

    }

    @Override
    public void onBindViewHolder(final PopularCareerAdapter.ViewHolder viewHolder, final int position) {
       /* if (popular)
            viewHolder.binding.imageView.setImageResource(imageArray[position]);
        else
            viewHolder.binding.imageView.setImageResource(imageArray1[position]);*/

        /*viewHolder.binding.lay.setOnClickListener(v -> {
            notifyItemChanged(selected_position);
            selected_position = position;
            notifyItemChanged(selected_position);
        });*/

        if (popular) {

            if (position == 0) {
                viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color1));
                viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color1));
                viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person1);

                viewHolder.binding.textCons.setText("Technology");
                viewHolder.binding.textWork.setText("Photographer");

            } else if (position == 1) {
                viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color2));
                viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color2));
                viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person2);

                viewHolder.binding.textCons.setText("Industrial");
                viewHolder.binding.textWork.setText(R.string.gen);

            } else if (position == 2) {
                viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color1));
                viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color1));
                viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person3);

                viewHolder.binding.textCons.setText("Service");
                viewHolder.binding.textWork.setText(R.string.ho);

            } else if (position == 3) {
                viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color2));
                viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color2));
                viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person4);

                viewHolder.binding.textCons.setText("Industrial");
                viewHolder.binding.textWork.setText(R.string.in);

            } else if (position == 4) {
                viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color1));
                viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color1));
                viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person5);

                viewHolder.binding.textCons.setText("Motive Power");
                viewHolder.binding.textWork.setText(R.string.tru);

            } else if (position == 5) {
                viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color2));
                viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color2));
                viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person6);

                viewHolder.binding.textCons.setText("Service");
                viewHolder.binding.textWork.setText(R.string.childs);

            } else if (position == 6) {
                viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color1));
                viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color1));
                viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person1);

                viewHolder.binding.textCons.setText("Technology");
                viewHolder.binding.textWork.setText("Photographer");

            } else {
                viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color2));
                viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color2));
                viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person1);

                viewHolder.binding.textCons.setText("Technology");
                viewHolder.binding.textWork.setText("Photographer");
            }
        } else {
            try {
                viewHolder.binding.textCons.setText(careerModalArrayList.get(position).getJobSector());
                viewHolder.binding.textWork.setText(careerModalArrayList.get(position).getJobProfile());
                viewHolder.binding.textMoney.setText(careerModalArrayList.get(position).getFee());

                Glide.with(context).load(careerModalArrayList.get(position).getImage())
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.new_person1)
                        .into(viewHolder.binding.imagePerson);

                if (careerModalArrayList.get(position).getbId().equalsIgnoreCase("")) {
                    viewHolder.binding.ivBookmark.setImageResource(R.drawable.ic_home_main_batch);
                    clickBookmark = false;
                } else {
                    clickBookmark = true;
                    viewHolder.binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_fill);
                }


            } catch (Exception e) {
            }

        }

        viewHolder.binding.imagePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfPerson = position;
                context.startActivity(new Intent(context, JobDetailsActivity.class));
            }
        });

        viewHolder.binding.mainLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfPerson = position;
                // context.startActivity(new Intent(context, JobDetailsActivity.class));

                Intent intent = new Intent(context, JobDetailsActivity.class);
                intent.putExtra("Popular", careerModalArrayList.get(position).getId());
                context.startActivity(intent);
            }
        });

        viewHolder.binding.ivBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (careerModalArrayList.get(position).getbId().equalsIgnoreCase("")) {
                    bookMarkUpdateDelete.checkBookMark("", position, careerModalArrayList.get(position).getId());
                } else {
                    bookMarkUpdateDelete.checkBookMark(careerModalArrayList.get(position).getbId(), position, careerModalArrayList.get(position).getId());
                }

                if (clickBookmark) {
                    clickBookmark = false;
                    viewHolder.binding.ivBookmark.setImageResource(R.drawable.ic_home_main_batch);
                } else {
                    clickBookmark = true;
                    viewHolder.binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_fill);
                }
            }
        });
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

    public interface BookMarkUpdateDelete {
        public void checkBookMark(String Bid, int position, String careerId);
    }

}