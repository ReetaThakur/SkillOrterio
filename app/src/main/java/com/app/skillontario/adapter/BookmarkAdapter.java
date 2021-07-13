package com.app.skillontario.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillorterio.R;
import com.app.skillorterio.databinding.AdapterNotificationBinding;
import com.app.skillorterio.databinding.BookmarkItemBinding;

import org.jetbrains.annotations.NotNull;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {


    Context context;
    boolean clickBookmark = false;
    int[] dra = new int[]{R.drawable.home_main_img1, R.drawable.temp_b1, R.drawable.temp_b2, R.drawable.home_main_img1};

    public BookmarkAdapter(Context context) {
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
    public void onBindViewHolder(final BookmarkAdapter.ViewHolder viewHolder, final int position) {
        //  viewHolder.binding.imagePerson.setImageResource(dra[position]);

        viewHolder.binding.imgBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickBookmark) {
                    clickBookmark = false;
                    viewHolder.binding.imgBookmark.setImageResource(R.drawable.bookmark_not_fill);
                } else {
                    clickBookmark = true;
                    viewHolder.binding.imgBookmark.setImageResource(R.drawable.ic_bookmark_fill);
                }
            }
        });


        if (position == 0) {
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
        }

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

}