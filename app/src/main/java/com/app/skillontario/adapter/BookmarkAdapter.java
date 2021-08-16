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

import com.app.skillontario.activities.BookmarkAc;
import com.app.skillontario.activities.JobDetailsActivity;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.models.careerListModel.CareerListDetails;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.AdapterNotificationBinding;
import com.app.skillorterio.databinding.BookmarkItemBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {

    ArrayList<CareerListDetails> careerListDetails = new ArrayList<>();
    DeleteBookMark deleteBookMark;

    Context context;
    boolean clickBookmark = false;
    //  int[] dra = new int[]{R.drawable.home_main_img1, R.drawable.temp_b1, R.drawable.temp_b2, R.drawable.home_main_img1};

    public BookmarkAdapter(Context context) {
        this.context = context;

    }

    public BookmarkAdapter(Context context, ArrayList<CareerListDetails> careerListDetails, DeleteBookMark deleteBookMark) {
        this.context = context;
        this.careerListDetails = careerListDetails;
        this.deleteBookMark = deleteBookMark;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        try {
            return careerListDetails.size();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void onBindViewHolder(final BookmarkAdapter.ViewHolder viewHolder, final int position) {
        //  viewHolder.binding.imagePerson.setImageResource(dra[position]);

        try {
            viewHolder.binding.imgBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.GUEST_FLOW)) {
                        if (clickBookmark) {
                            clickBookmark = false;
                            viewHolder.binding.imgBookmark.setImageResource(R.drawable.bookmark_not_fill);
                        } else {
                            clickBookmark = true;
                            viewHolder.binding.imgBookmark.setImageResource(R.drawable.ic_bookmark_fill);
                        }
                    }
                    deleteBookMark.delete(position, careerListDetails.get(position).getbId(), careerListDetails.get(position).getId());

                }
            });
        } catch (Exception e) {
        }


        //if(careerListDetails.get(position).get)

        try {
            // Picasso.with(context).load(careerListDetails.get(position).getImage()).into(viewHolder.binding.imagePerson);
            viewHolder.binding.textCons.setText(careerListDetails.get(position).getJobSector());
            viewHolder.binding.textWork.setText(careerListDetails.get(position).getJobProfile());
            viewHolder.binding.textMoney.setText(careerListDetails.get(position).getFee());

        } catch (Exception e) {
        }

        try {
            try {
                Glide.with(context)
                        .load(careerListDetails.get(position).getImage())
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
                        .into(viewHolder.binding.imagePerson);
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }

        viewHolder.binding.relClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, JobDetailsActivity.class);
                intent.putExtra("Popular", careerListDetails.get(position).getId());
                context.startActivity(intent);
            }
        });

        viewHolder.binding.imagePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, JobDetailsActivity.class);
                intent.putExtra("Popular", careerListDetails.get(position).getId());
                context.startActivity(intent);
            }
        });

    }

    @NotNull
    @Override
    public BookmarkAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int position) {
        BookmarkItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.bookmark_item, parent, false);

        return new BookmarkAdapter.ViewHolder(binding);
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

    public interface DeleteBookMark {
        public void delete(int position, String Bid, String Id);
    }

    public void addList(ArrayList<CareerListDetails> listDetail) {
        if (listDetail != null) {
            if (listDetail.size() > 0) {
                this.careerListDetails.addAll(listDetail);
                notifyDataSetChanged();
            }
        }
    }

}