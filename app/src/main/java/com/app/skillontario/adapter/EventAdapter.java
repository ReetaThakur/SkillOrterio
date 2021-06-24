package com.app.skillontario.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillontario.models.EventsModal;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.EventsItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder>{
    ArrayList<EventsModal> eventsModalArrayList;
    FragmentActivity activity;
    public EventAdapter(ArrayList<EventsModal> eventsModalArrayList, FragmentActivity activity) {
        this.eventsModalArrayList=eventsModalArrayList;
        this.activity=activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EventsItemBinding eventsItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.events_item,parent,false);
        return new EventAdapter.MyViewHolder(eventsItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.with(activity).load(eventsModalArrayList.get(position).getEvtImage()).into(holder.eventsItemBinding.ivItem);
        holder.eventsItemBinding.tvHead.setText(eventsModalArrayList.get(position).getEvtTitle());
        holder.eventsItemBinding.tvAdd.setText(eventsModalArrayList.get(position).getEvtVenue());
    }

    @Override
    public int getItemCount() {
        return eventsModalArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        EventsItemBinding eventsItemBinding;
        public MyViewHolder(EventsItemBinding eventsItemBinding) {
            super(eventsItemBinding.getRoot());
            this.eventsItemBinding=eventsItemBinding;

        }
    }
}
