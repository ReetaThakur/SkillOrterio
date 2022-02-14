package com.app.skillsontario.adapter;

import android.annotation.SuppressLint;
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


import com.app.skillsontario.R;
import com.app.skillsontario.activities.JobDetailsActivity;
import com.app.skillsontario.constants.SharedPrefsConstants;
import com.app.skillsontario.databinding.AdapterPopularCarrerBinding;
import com.app.skillsontario.models.CareerModal;
import com.app.skillsontario.utils.MySharedPreference;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PopularCareerAdapter extends RecyclerView.Adapter<PopularCareerAdapter.ViewHolder> {


    Context context;
    boolean clickBookmark = false;
    boolean popular = false;

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
    public void onBindViewHolder(final PopularCareerAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
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


                Glide.with(context)
                        .load(careerModalArrayList.get(position).getImage())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                try {
                                    viewHolder.binding.progress.setVisibility(View.GONE);
                                }catch (Exception ee){}
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                try {
                                    viewHolder.binding.progress.setVisibility(View.GONE);
                                }catch (Exception ee){}
                                return false;
                            }
                        })
                        .into(viewHolder.binding.imagePerson);

                if (careerModalArrayList.get(position).getbId().equalsIgnoreCase("")) {
                    viewHolder.binding.ivBookmark.setImageResource(R.drawable.ic_home_main_batch);
                    clickBookmark = false;
                    viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.white));
                } else {
                    clickBookmark = true;
                    viewHolder.binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_fill);
                    viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.bookmark_color));
                }


            } catch (Exception e) {
            }

        }

        viewHolder.binding.imagePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, JobDetailsActivity.class);
                intent.putExtra("Popular", careerModalArrayList.get(position).getId());
                context.startActivity(intent);
            }
        });

        viewHolder.binding.mainLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, JobDetailsActivity.class);
                intent.putExtra("Popular", careerModalArrayList.get(position).getId());
                context.startActivity(intent);
            }
        });

        viewHolder.binding.ivBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.GUEST_FLOW)) {
                    if (careerModalArrayList.get(position).getbId().equalsIgnoreCase("")) {
                        bookMarkUpdateDelete.checkBookMark("", position, careerModalArrayList.get(position).getId());
                    } else {
                        bookMarkUpdateDelete.checkBookMark(careerModalArrayList.get(position).getbId(), position, careerModalArrayList.get(position).getId());
                    }
                } else {
                    if (careerModalArrayList.get(position).getbId().equalsIgnoreCase("")) {
                        bookMarkUpdateDelete.checkBookMark("", position, careerModalArrayList.get(position).getId());
                    } else {
                        bookMarkUpdateDelete.checkBookMark(careerModalArrayList.get(position).getbId(), position, careerModalArrayList.get(position).getId());
                    }

                    if (clickBookmark) {
                        clickBookmark = false;
                        viewHolder.binding.ivBookmark.setImageResource(R.drawable.ic_home_main_batch);
                        viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.white));
                    } else {
                        clickBookmark = true;
                        viewHolder.binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_fill);
                        viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.bookmark_color));
                    }
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