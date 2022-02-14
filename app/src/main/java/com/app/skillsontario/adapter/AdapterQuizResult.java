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
import com.app.skillsontario.databinding.BookmarkItemBinding;
import com.app.skillsontario.models.careerListModel.CareerListDetails;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterQuizResult extends RecyclerView.Adapter<AdapterQuizResult.ViewHolder> {
    //BookmarkAc bookmarkAc;
    ArrayList<CareerListDetails> quizFinalResultModel = new ArrayList<>();
    AdapterQuizResult.DeleteBookMarkCall deleteBookMark;

    Context context;
    boolean clickBookmark = false;

    public AdapterQuizResult(Context context) {
        this.context = context;

    }

    public AdapterQuizResult(Context context, ArrayList<CareerListDetails> quizFinalResultModel, AdapterQuizResult.DeleteBookMarkCall deleteBookMark) {
        this.context = context;
        this.quizFinalResultModel = quizFinalResultModel;
        this.deleteBookMark = deleteBookMark;
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final AdapterQuizResult.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        //  viewHolder.binding.imagePerson.setImageResource(dra[position]);

        //viewHolder.binding.imgBookmark.setVisibility(View.INVISIBLE);

        try {
            if (!quizFinalResultModel.get(position).isHasBookmark()) {
                viewHolder.binding.imgBookmark.setImageResource(R.drawable.ic_home_main_batch);
                clickBookmark = false;
                viewHolder.binding.cardCDH.setCardBackgroundColor(context.getColor(R.color.white));
            } else {
                clickBookmark = true;
                viewHolder.binding.imgBookmark.setImageResource(R.drawable.ic_bookmark_fill);
                viewHolder.binding.cardCDH.setCardBackgroundColor(context.getColor(R.color.bookmark_color));
            }
        } catch (Exception e) {
        }

        viewHolder.binding.imgBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!quizFinalResultModel.get(position).isHasBookmark()) {
                    deleteBookMark.delete(position, quizFinalResultModel.get(position).getbId(), quizFinalResultModel.get(position).getId(), true);
                    clickBookmark = true;
                    viewHolder.binding.imgBookmark.setImageResource(R.drawable.ic_bookmark_fill);
                } else {
                    clickBookmark = false;
                    deleteBookMark.delete(position, quizFinalResultModel.get(position).getbId(), quizFinalResultModel.get(position).getId(), false);
                    viewHolder.binding.imgBookmark.setImageResource(R.drawable.ic_home_main_batch);
                }

            }
        });

        //if(careerListDetails.get(position).get)

        try {
            // Picasso.with(context).load(quizFinalResultModel.get(position).getImage()).into(viewHolder.binding.imagePerson);
            viewHolder.binding.textCons.setText(quizFinalResultModel.get(position).getJobSector());
            viewHolder.binding.textWork.setText(quizFinalResultModel.get(position).getJobProfile());
            viewHolder.binding.textMoney.setText(quizFinalResultModel.get(position).getFee());

        } catch (Exception e) {
        }

        try {
            viewHolder.binding.relClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, JobDetailsActivity.class);
                    intent.putExtra("Popular", quizFinalResultModel.get(position).getId());
                    context.startActivity(intent);
                }
            });
        } catch (Exception e) {
        }

        try {
            try {
                Glide.with(context)
                        .load(quizFinalResultModel.get(position).getImage())
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
    public AdapterQuizResult.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int position) {
        BookmarkItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.bookmark_item, parent, false);

        return new AdapterQuizResult.ViewHolder(binding);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private BookmarkItemBinding binding;

        public ViewHolder(BookmarkItemBinding view) {
            super(view.getRoot());
            binding = view;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public interface DeleteBookMarkCall {
        public void delete(int position, String Bid, String Id, boolean val);
    }

    public void addList(ArrayList<CareerListDetails> listDetail) {
        if (listDetail != null) {
            if (listDetail.size() > 0) {
                this.quizFinalResultModel.addAll(listDetail);
                notifyDataSetChanged();
            }
        }
    }

}