package com.app.skillontario.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillontario.activities.NewsDetailAc;
import com.app.skillontario.activities.WebViewActivity;
import com.app.skillontario.baseClasses.AppController;
import com.app.skillontario.models.EventsModal;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillontario.utils.Utils;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.AdapterRecentBinding;
import com.app.skillorterio.databinding.AdapterRecentBinding;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecentEventsAdapter extends RecyclerView.Adapter<RecentEventsAdapter.ViewHolder> {

    ArrayList<EventsModal> eventsModalArrayList = new ArrayList<>();
    Context context;

    public RecentEventsAdapter(Context context) {
        this.context = context;

    }

    public RecentEventsAdapter(ArrayList<EventsModal> eventsModalArrayList, Context activity) {
        this.eventsModalArrayList = eventsModalArrayList;
        this.context = activity;

    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        try {
            return eventsModalArrayList.size();
        } catch (Exception e) {
            return 0;
        }

    }

    @Override
    public void onBindViewHolder(final RecentEventsAdapter.ViewHolder viewHolder, final int position) {

        try {
            viewHolder.binding.tvHead.setText(eventsModalArrayList.get(position).getEvtTitle());
            viewHolder.binding.tvAdd.setText(eventsModalArrayList.get(position).getEvtVenue());
            viewHolder.binding.tvEvtCategory.setText(eventsModalArrayList.get(position).getEvtCategory());
        } catch (Exception e) {
        }

        try {
            Glide.with(context).load(eventsModalArrayList.get(position).getEvtImage())
                    .placeholder(R.drawable.place_holder_events)
                    .error(R.drawable.ic_launcher_background)
                    .into(viewHolder.binding.ivItem);
        } catch (Exception e) {
        }

        try {
            if (eventsModalArrayList.get(position).getEvtDate() != null) {
                viewHolder.binding.tvDate.setText(Utils.DateFormate(eventsModalArrayList.get(position).getEvtDate()));
            }
        } catch (Exception e) {
        }


        viewHolder.binding.ivCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                   /// val =false;
                    if (val) {
                        Utils.deleteCalenderEvent(context,eventID,key);
                        viewHolder.binding.ivCal.setImageResource(R.drawable.event_calender);
                    } else {

                        try {
                            if (Utils.checkPermissionCalender(context)) {
                                Utils.openD(eventsModalArrayList.get(position).getId(), context, eventsModalArrayList.get(position).getEvtDate(), eventsModalArrayList.get(position).getEvtEndDate(), eventsModalArrayList.get(position).getEvtTitle(), eventsModalArrayList.get(position).getEvtDesc());
                                viewHolder.binding.ivCal.setImageResource(R.drawable.event_added);
                            } else
                                Utils.askPermison(context);
                        } catch (Exception e) {
                        }

                    }
                }
            }
        });

        //   viewHolder.binding.eventRow.setOnClickListener(v -> context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.skillsontario.com"))));
        viewHolder.binding.eventRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", eventsModalArrayList.get(position).getEvtURL());
                intent.putExtra("title", eventsModalArrayList.get(position).getEvtTitle());
                context.startActivity(intent);
            }
        });


        try {
            Map<String, Object> inputMap = new HashMap<>();
            inputMap = MySharedPreference.getInstance().loadMap();
            for (Map.Entry entry : inputMap.entrySet()) {
                if (entry.getKey().toString().equalsIgnoreCase(eventsModalArrayList.get(position).getId())) {
                    viewHolder.binding.ivCal.setImageResource(R.drawable.event_added);
                }
            }

        } catch (Exception e) {
        }
    }

    private void openD() {
        Dialog dialogMood = new Dialog(context);
        dialogMood.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogMood.setCancelable(true);
        if (dialogMood.getWindow() != null) {
            dialogMood.getWindow()
                    .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialogMood.setContentView(R.layout.news_dialog);
        dialogMood.findViewById(R.id.done).setOnClickListener(view1 -> dialogMood.dismiss());
        dialogMood.show();
    }

    @NotNull
    @Override
    public RecentEventsAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int position) {
        AdapterRecentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_recent, parent, false);
        parent.setId(position);
        parent.setFocusable(false);
        parent.setFocusableInTouchMode(false);

        return new RecentEventsAdapter.ViewHolder(binding);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private AdapterRecentBinding binding;

        public ViewHolder(AdapterRecentBinding view) {
            super(view.getRoot());
            binding = view;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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