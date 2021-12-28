package com.app.skillsontario.adapter;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.skillsontario.R;
import com.app.skillsontario.activities.WebViewActivity;
import com.app.skillsontario.callbacks.CheckPermission;
import com.app.skillsontario.constants.SharedPrefsConstants;
import com.app.skillsontario.databinding.AdapterRecentBinding;
import com.app.skillsontario.models.EventsModal;
import com.app.skillsontario.utils.MySharedPreference;
import com.app.skillsontario.utils.Utils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecentEventsAdapter extends RecyclerView.Adapter<RecentEventsAdapter.ViewHolder> {

    ArrayList<EventsModal> eventsModalArrayList = new ArrayList<>();
    Context context;
    CheckPermission checkPermission;
    int permissionCheck = 0;

    public RecentEventsAdapter(ArrayList<EventsModal> eventsModalArrayList, Context activity, CheckPermission checkPermission) {
        this.eventsModalArrayList = eventsModalArrayList;
        this.context = activity;
        this.checkPermission = checkPermission;

    }

    public RecentEventsAdapter(Context activity) {
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
            Glide.with(context)
                    .load(eventsModalArrayList.get(position).getEvtImage())
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
                        Utils.deleteCalenderEvent(context, eventID, key);
                        viewHolder.binding.ivCal.setImageResource(R.drawable.event_calender);
                    } else {

                        try {
                            if (Utils.checkPermissionCalender(context)) {
                                Utils.openD(eventsModalArrayList.get(position).getId(), context, eventsModalArrayList.get(position).getEvtDate(), eventsModalArrayList.get(position).getEvtEndDate(), eventsModalArrayList.get(position).getEvtTitle(), eventsModalArrayList.get(position).getEvtDesc());
                                viewHolder.binding.ivCal.setImageResource(R.drawable.event_added);
                            } else {

                                if (!MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.CHECK_PERMISSION)) {
                                    MySharedPreference.getInstance().setBooleanData(SharedPrefsConstants.CHECK_PERMISSION, true);
                                    Utils.askPermison(context);
                                } else {
                                    if (setCheckPermission(context)) {
                                        Utils.openD(eventsModalArrayList.get(position).getId(), context, eventsModalArrayList.get(position).getEvtDate(), eventsModalArrayList.get(position).getEvtEndDate(), eventsModalArrayList.get(position).getEvtTitle(), eventsModalArrayList.get(position).getEvtDesc());
                                        viewHolder.binding.ivCal.setImageResource(R.drawable.event_added);
                                    } else {

                                        if (checkPermission != null) {
                                            checkPermission.onCheck();
                                        }
                                    }
                                }
                            }
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