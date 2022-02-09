package com.app.skillsontario.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillsontario.R;
import com.app.skillsontario.activities.WebViewActivity;
import com.app.skillsontario.baseClasses.CrashLogger;
import com.app.skillsontario.callbacks.CheckPermission;
import com.app.skillsontario.constants.SharedPrefsConstants;
import com.app.skillsontario.databinding.EventsItemBinding;
import com.app.skillsontario.models.EventsModal;
import com.app.skillsontario.utils.MySharedPreference;
import com.app.skillsontario.utils.Utils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    ArrayList<EventsModal> eventsModalArrayList;
    FragmentActivity activity;
    int permissionCheck = 0;
    CheckPermission checkPermission;

    public EventAdapter(ArrayList<EventsModal> eventsModalArrayList, FragmentActivity activity, CheckPermission checkPermission) {
        this.eventsModalArrayList = eventsModalArrayList;
        this.activity = activity;
        this.checkPermission = checkPermission;
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
            Glide.with(activity)
                    .load(eventsModalArrayList.get(position).getEvtImage())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.eventsItemBinding.progress.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.eventsItemBinding.progress.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.eventsItemBinding.ivItem);
        } catch (Exception e) {
        }

        try {
            //   Picasso.with(activity).load(eventsModalArrayList.get(position).getEvtImage()).error(R.drawable.place_holder_events).placeholder(R.drawable.place_holder_events).into(holder.eventsItemBinding.ivItem);
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
                        // val =false;
                        if (val) {
                            Utils.deleteCalenderEvent(activity, eventID, key);
                            holder.eventsItemBinding.ivCal.setImageResource(R.drawable.event_calender);
                        } else {
                            if (Utils.checkPermissionCalender(activity)) {
                                Utils.openD(eventsModalArrayList.get(position).getId(), activity,
                                        eventsModalArrayList.get(position).getEvtDate(), eventsModalArrayList.get(position).getEvtEndDate(), eventsModalArrayList.get(position).getEvtTitle(), eventsModalArrayList.get(position).getEvtDesc());
                                holder.eventsItemBinding.ivCal.setImageResource(R.drawable.event_added);
                            } else {
                                if (!MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.CHECK_PERMISSION)) {
                                    MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.CHECK_PERMISSION, true);
                                    Utils.askPermison(activity);
                                } else {
                                    if (setCheckPermission(activity)) {
                                        Utils.openD(eventsModalArrayList.get(position).getId(), activity,
                                                eventsModalArrayList.get(position).getEvtDate(), eventsModalArrayList.get(position).getEvtEndDate(), eventsModalArrayList.get(position).getEvtTitle(), eventsModalArrayList.get(position).getEvtDesc());
                                        holder.eventsItemBinding.ivCal.setImageResource(R.drawable.event_added);
                                    } else {

                                        if (checkPermission != null) {
                                            checkPermission.onCheck();
                                        }
                                    }
                                }

                            }
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

                try {
                    CrashLogger.INSTANCE.trackEventsFirebase("Participate_In_Events ", "EventsFragment");
                }catch (Exception e){}

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

    public boolean setCheckPermission(Context context) {
        boolean val = false;

        if (Build.VERSION.SDK_INT >= 23) {
            int hasPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR);
            int hasPermission1 = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR);

            if (hasPermission == PackageManager.PERMISSION_GRANTED && hasPermission1 == PackageManager.PERMISSION_GRANTED) {
                MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.CHECK_PERMISSION, true);
                val = true;
            }


        }

        return val;
    }

}
