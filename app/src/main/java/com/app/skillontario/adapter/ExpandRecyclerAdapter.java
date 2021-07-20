package com.app.skillontario.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public ExpandRecyclerAdapter(List<SampleGroupBean> list) {
        mList = list;
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
        holder.nameTv.setText(sampleChildBean.getName());
        if (groupBean.isExpandable()) {
            holder.nameTv.setVisibility(View.VISIBLE);
            holder.linearLayout.setVisibility(View.VISIBLE);
        }else {
            holder.nameTv.setVisibility(View.GONE);
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
        TextView nameTv;
        LinearLayout linearLayout;

        ChildVH(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.child_item_name);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.lin);
        }
    }
}
