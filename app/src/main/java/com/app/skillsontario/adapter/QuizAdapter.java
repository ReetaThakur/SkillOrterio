package com.app.skillsontario.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillsontario.R;
import com.app.skillsontario.activities.JobDetailsActivity;
import com.app.skillsontario.constants.SharedPrefsConstants;
import com.app.skillsontario.databinding.AdapterQuizBinding;
import com.app.skillsontario.models.CareerModal;
import com.app.skillsontario.utils.MySharedPreference;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {

    boolean clickBookmark = false;
    Context context;
    boolean popular = false;
    BookMarkUpdateDelete bookMarkUpdateDelete;
    ArrayList<CareerModal> careerModalArrayList;


    public QuizAdapter(Context context, boolean popular) {
        this.context = context;
        this.popular = popular;
    }

    public QuizAdapter(ArrayList<CareerModal> careerModalArrayList, Context context, boolean b, BookMarkUpdateDelete bookMarkUpdateDelete) {
        this.context = context;
        this.bookMarkUpdateDelete = bookMarkUpdateDelete;
        this.careerModalArrayList = careerModalArrayList;
        this.popular = false;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        try {
            if (popular) {
                return 0;
            } else {
                return careerModalArrayList.size();
            }
        } catch (Exception e) {
            return 0;
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final QuizAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

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
                        viewHolder.binding.cardCDH.setCardBackgroundColor(context.getColor(R.color.white));
                    } else {
                        clickBookmark = true;
                        viewHolder.binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_fill);
                        viewHolder.binding.cardCDH.setCardBackgroundColor(context.getColor(R.color.bookmark_color));
                    }
                }
            }
        });

        viewHolder.binding.imagePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, JobDetailsActivity.class);
                intent.putExtra("Popular", careerModalArrayList.get(position).getId());
                context.startActivity(intent);
            }
        });


        viewHolder.binding.lyMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, JobDetailsActivity.class);
                intent.putExtra("Popular", careerModalArrayList.get(position).getId());
                context.startActivity(intent);
            }
        });

        try {
            try {
                viewHolder.binding.textCons.setText(careerModalArrayList.get(position).getJobSector());
                viewHolder.binding.textWork.setText(careerModalArrayList.get(position).getJobProfile());
                viewHolder.binding.textMoney.setText(careerModalArrayList.get(position).getFee());

               /* Glide.with(context).load(careerModalArrayList.get(position).getImage())
                        .error(R.drawable.new_person1)
                        .into(viewHolder.binding.imagePerson);*/

                if (careerModalArrayList.get(position).getbId().equalsIgnoreCase("")) {
                    viewHolder.binding.ivBookmark.setImageResource(R.drawable.ic_home_main_batch);
                    clickBookmark = false;
                    viewHolder.binding.cardCDH.setCardBackgroundColor(context.getColor(R.color.white));
                } else {
                    clickBookmark = true;
                    viewHolder.binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_fill);
                    viewHolder.binding.cardCDH.setCardBackgroundColor(context.getColor(R.color.bookmark_color));
                }


            } catch (Exception e) {
            }
        } catch (Exception e) {
        }

        try {
            try {
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
            } catch (Exception e) {
            }

        } catch (Exception e) {
        }

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

    public interface BookMarkUpdateDelete {
        public void checkBookMark(String Bid, int position, String careerId);
    }

}
