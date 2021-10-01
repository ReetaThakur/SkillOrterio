package com.app.skillontario.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.skillontario.expandRecycler.SampleChildBean;
import com.app.skillontario.expandRecycler.SampleGroupBean;
import com.app.skillorterio.R;
import com.hgdendi.expandablerecycleradapter.BaseExpandableRecyclerViewAdapter;

import java.util.List;

public class ExpandRecyclerAdapter extends
        BaseExpandableRecyclerViewAdapter<SampleGroupBean, SampleChildBean, ExpandRecyclerAdapter.GroupVH, ExpandRecyclerAdapter.ChildVH> {

    private List<SampleGroupBean> mList;
    Context context;

    public ExpandRecyclerAdapter(List<SampleGroupBean> list, Context context) {
        mList = list;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public SampleGroupBean getGroupItem(int position) {
        return mList.get(position);
    }

    @Override
    public GroupVH onCreateGroupViewHolder(ViewGroup parent, int groupViewType) {
        return new GroupVH(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_group, parent, false));
    }

    @Override
    public ChildVH onCreateChildViewHolder(ViewGroup parent, int childViewType) {
        return new ChildVH(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_child, parent, false));
    }

    @Override
    public void onBindGroupViewHolder(GroupVH holder, SampleGroupBean sampleGroupBean, boolean isExpanding) {
        holder.nameTv.setText(sampleGroupBean.getName());
        if (sampleGroupBean.isExpandable()) {
            holder.foldIv.setVisibility(View.VISIBLE);
            // holder.nameTv.setVisibility(View.VISIBLE);
            holder.foldIv.setImageResource(isExpanding ? R.drawable.ic_arrow_expanding : R.drawable.ic_arrow_folding);
        } else {
            holder.foldIv.setVisibility(View.INVISIBLE);
            // holder.nameTv.setVisibility(View.GONE);

        }

    }

    @Override
    public void onBindChildViewHolder(ChildVH holder, SampleGroupBean groupBean, SampleChildBean sampleChildBean) {
        //  holder.nameTvWeb.setText(sampleChildBean.getName());
        holder.nameTvWeb.setMovementMethod(LinkMovementMethod.getInstance());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.nameTvWeb.setText(Html.fromHtml(sampleChildBean.getName(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.nameTvWeb.setText(Html.fromHtml(sampleChildBean.getName()));
        }


     /*   holder.nameTvWeb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                Intent intent= new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(request.getUrl().toString())
                );
                context.startActivity(intent);
                return true;
                //return super.shouldOverrideUrlLoading(view, request);
            }
        });
        holder.nameTvWeb.loadDataWithBaseURL(
                "",
                "<html>  <head><style type=\"text/css\"> @font-face {  font-family: Poppins;      src: url(\"file:///android_asset/fonts/poppins_regular.ttf\")  } </style> </head><body>" + sampleChildBean.getName() + "</body>",
                "text/html",
                "utf-8",
                null);
        holder.nameTvWeb.setBackgroundColor(Color.TRANSPARENT);*/


        if (groupBean.isExpandable()) {
            // holder.nameTvWeb.setVisibility(View.VISIBLE);
            holder.linearLayout.setVisibility(View.VISIBLE);
        } else {
            //holder.nameTvWeb.setVisibility(View.GONE);
            holder.linearLayout.setVisibility(View.GONE);
        }

    }

    static class GroupVH extends BaseExpandableRecyclerViewAdapter.BaseGroupViewHolder {
        ImageView foldIv;
        TextView nameTv;

        GroupVH(View itemView) {
            super(itemView);
            foldIv = (ImageView) itemView.findViewById(R.id.group_item_indicator);
            nameTv = (TextView) itemView.findViewById(R.id.group_item_name);
        }

        @Override
        protected void onExpandStatusChanged(RecyclerView.Adapter relatedAdapter, boolean isExpanding) {
            foldIv.setImageResource(isExpanding ? R.drawable.ic_arrow_expanding_ : R.drawable.ic_arrow_expanding_not_);
        }
    }

    static class ChildVH extends RecyclerView.ViewHolder {
        TextView nameTvWeb;
        LinearLayout linearLayout;

        ChildVH(View itemView) {
            super(itemView);
            nameTvWeb = (TextView) itemView.findViewById(R.id.child_item_name);
            //  nameTvWeb.getSettings().setJavaScriptEnabled(true);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.lin);
        }
    }
}
