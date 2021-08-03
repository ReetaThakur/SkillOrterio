package com.app.skillontario.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {
    BookmarkAc bookmarkAc;
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

        //if(careerListDetails.get(position).get)

        try {
            Picasso.with(bookmarkAc).load(careerListDetails.get(position).getImage()).into(viewHolder.binding.imagePerson);
            viewHolder.binding.textCons.setText(careerListDetails.get(position).getJobSector());
            viewHolder.binding.textWork.setText(careerListDetails.get(position).getJobProfile());
            viewHolder.binding.textMoney.setText(careerListDetails.get(position).getFee());

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


  /*      if (position == 0) {
            //viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color1));
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color1));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person1);

            viewHolder.binding.textCons.setText("Technology");
            viewHolder.binding.textWork.setText("Photographer");

        } else if (position == 1) {
            // viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color2));
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.text_error_color));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person2);
            viewHolder.binding.textCons.setText("Industrial");
            viewHolder.binding.textWork.setText(R.string.gen);
        } else if (position == 2) {
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color3));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person3);
            viewHolder.binding.textCons.setText("Service");
            viewHolder.binding.textWork.setText(R.string.ho);
        } else if (position == 3) {
            //viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color1));
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color1));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person4);
            viewHolder.binding.textCons.setText("Industrial");
            viewHolder.binding.textWork.setText(R.string.in);

        } else if (position == 4) {
            // viewHolder.binding.imgBackground.setColorFilter(ContextCompat.getColor(context, R.color.home_color2));
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.text_error_color));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person5);
            viewHolder.binding.textCons.setText("Motive Power");
            viewHolder.binding.textWork.setText(R.string.tru);
        } else if (position == 5) {
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color3));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person6);
            viewHolder.binding.textCons.setText("Service");
            viewHolder.binding.textWork.setText(R.string.childs);
        } else {
            viewHolder.binding.imgOvl.setColorFilter(ContextCompat.getColor(context, R.color.home_oval_color1));
            viewHolder.binding.imagePerson.setImageResource(R.drawable.new_person1);
            viewHolder.binding.textCons.setText("Technology");
            viewHolder.binding.textWork.setText("Photographer");
        }*/

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