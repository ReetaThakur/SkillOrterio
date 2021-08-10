package com.app.skillontario.adapter;

import android.content.Intent;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillontario.activities.NewsDetailAc;
import com.app.skillontario.activities.WebViewActivity;
import com.app.skillontario.models.EventsModal;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillontario.utils.Utils;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.EventsItemBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    ArrayList<EventsModal> eventsModalArrayList;
    FragmentActivity activity;

    public EventAdapter(ArrayList<EventsModal> eventsModalArrayList, FragmentActivity activity) {
        this.eventsModalArrayList = eventsModalArrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EventsItemBinding eventsItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.events_item, parent, false);
        return new EventAdapter.MyViewHolder(eventsItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            Picasso.with(activity).load(eventsModalArrayList.get(position).getEvtImage()).error(R.drawable.place_holder_events).placeholder(R.drawable.place_holder_events).into(holder.eventsItemBinding.ivItem);
            holder.eventsItemBinding.tvHead.setText(eventsModalArrayList.get(position).getEvtTitle());
            holder.eventsItemBinding.tvAdd.setText(eventsModalArrayList.get(position).getEvtVenue());
            holder.eventsItemBinding.tvEvtCategory.setText(eventsModalArrayList.get(position).getEvtCategory());
            if (eventsModalArrayList.get(position).getEvtDate() != null) {
                holder.eventsItemBinding.tvDate.setText(Utils.DateFormate(eventsModalArrayList.get(position).getEvtDate()));
            }
            holder.eventsItemBinding.ivCal.setOnClickListener(v -> {
                try {
                    if (eventsModalArrayList.get(position).getEvtDate() != null) {

                        boolean val = false;
                        String eventID = "";
                        String key = "";
                        try {
                            Map<String, Object> inputMap = new HashMap<>();
                            inputMap = MySharedPreference.getInstance().loadMap();
                            for (Map.Entry entry : inputMap.entrySet()) {
                                if (entry.getKey().toString().equalsIgnoreCase(eventsModalArrayList.get(position).getId())) {
                                    //viewHolder.binding.ivCal.setImageResource(R.drawable.event_added);
                                    eventID = entry.getValue().toString();
                                    key = entry.getKey().toString();
                                    val = true;
                                    break;
                                }
                            }

                        } catch (Exception e) {
                        }
                        val =false;
                        if (val) {
                            Utils.deleteCalenderEvent(activity, eventID, key);
                            holder.eventsItemBinding.ivCal.setImageResource(R.drawable.event_calender);
                        } else {
                            if (Utils.checkPermissionCalender(activity)) {
                                Utils.openD(eventsModalArrayList.get(position).getId(), activity,
                                        Utils.DateFormate(eventsModalArrayList.get(position).getEvtDate()), Utils.DateFormate(eventsModalArrayList.get(position).getEvtEndDate()), eventsModalArrayList.get(position).getEvtTitle(), eventsModalArrayList.get(position).getEvtDesc());
                                holder.eventsItemBinding.ivCal.setImageResource(R.drawable.event_added);
                            } else
                                Utils.askPermison(activity);
                        }
                    }
                } catch (Exception e) {
                }

            });
            holder.eventsItemBinding.eventRow.setOnClickListener(v -> {
                if (!Patterns.WEB_URL.matcher(eventsModalArrayList.get(position).getEvtURL()).matches()) {
                    Toast.makeText(activity, "Url not support", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(activity, WebViewActivity.class);
                    intent.putExtra("url", eventsModalArrayList.get(position).getEvtURL());
                    intent.putExtra("title", eventsModalArrayList.get(position).getEvtTitle());
                    activity.startActivity(intent);
                }
                //  activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(eventsModalArrayList.get(position).getEvtURL())));
            });
        } catch (Exception e) {
        }


        try {
            Map<String, Object> inputMap = new HashMap<>();
            inputMap = MySharedPreference.getInstance().loadMap();
            for (Map.Entry entry : inputMap.entrySet()) {
                //   Log.d("Sunny ", " key  " + entry.getKey() + "; value: " + entry.getValue());

                if (entry.getKey().toString().equalsIgnoreCase(eventsModalArrayList.get(position).getId())) {
                    holder.eventsItemBinding.ivCal.setImageResource(R.drawable.event_added);
                }
            }

        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return eventsModalArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        EventsItemBinding eventsItemBinding;

        public MyViewHolder(EventsItemBinding eventsItemBinding) {
            super(eventsItemBinding.getRoot());
            this.eventsItemBinding = eventsItemBinding;

        }
    }

    public void addList(ArrayList<EventsModal> listDetail) {
        if (listDetail != null) {
            if (listDetail.size() > 0) {
                this.eventsModalArrayList.addAll(listDetail);
                notifyDataSetChanged();
            }
        }
    }


}
