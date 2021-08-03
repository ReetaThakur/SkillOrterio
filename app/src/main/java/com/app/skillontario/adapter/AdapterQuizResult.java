package com.app.skillontario.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillontario.activities.BookmarkAc;
import com.app.skillontario.models.careerListModel.CareerListDetails;
import com.app.skillontario.models.quizModel.QuizResultModel;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.BookmarkItemBinding;
import com.squareup.picasso.Picasso;

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

    @Override
    public void onBindViewHolder(final AdapterQuizResult.ViewHolder viewHolder, final int position) {
        //  viewHolder.binding.imagePerson.setImageResource(dra[position]);

        //viewHolder.binding.imgBookmark.setVisibility(View.INVISIBLE);

        try {
            if (quizFinalResultModel.get(position).getbId().equalsIgnoreCase("")) {
                viewHolder.binding.imgBookmark.setImageResource(R.drawable.ic_home_main_batch);
                clickBookmark = false;
            } else {
                clickBookmark = true;
                viewHolder.binding.imgBookmark.setImageResource(R.drawable.ic_bookmark_fill);
            }
        }catch (Exception e){}

        viewHolder.binding.imgBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quizFinalResultModel.get(position).getbId().equalsIgnoreCase("")) {
                    deleteBookMark.delete(position, quizFinalResultModel.get(position).getbId(), quizFinalResultModel.get(position).getId(),true);
                    clickBookmark = true;
                    viewHolder.binding.imgBookmark.setImageResource(R.drawable.ic_bookmark_fill);
                }else {
                    clickBookmark = false;
                    deleteBookMark.delete(position, quizFinalResultModel.get(position).getbId(), quizFinalResultModel.get(position).getId(),false);
                    viewHolder.binding.imgBookmark.setImageResource(R.drawable.ic_home_main_batch);
                }

            }
        });

        //if(careerListDetails.get(position).get)

        try {
            Picasso.with(context).load(quizFinalResultModel.get(position).getImage()).into(viewHolder.binding.imagePerson);
            viewHolder.binding.textCons.setText(quizFinalResultModel.get(position).getJobSector());
            viewHolder.binding.textWork.setText(quizFinalResultModel.get(position).getJobProfile());
            viewHolder.binding.textMoney.setText(quizFinalResultModel.get(position).getFee());

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