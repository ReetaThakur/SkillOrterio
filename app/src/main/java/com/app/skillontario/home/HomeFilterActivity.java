package com.app.skillontario.home;


import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.skillontario.adapter.KeywordsAdapter;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.callbacks.KeywordSelected;
import com.app.skillontario.utils.RecyclerItemClickListener;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityHomeFilterBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dev.amin.tagadapter.Chack.TagAdapter1;
import dev.amin.tagadapter.Tag;
import dev.amin.tagadapter.TagAdapter;
import dev.amin.tagadapter.check2.TagAdapter2;

public class HomeFilterActivity extends BaseActivity implements KeywordSelected {

    private ActivityHomeFilterBinding binding;
    private TagAdapter adapter;
    private TagAdapter1 adapter1;
    private TagAdapter2 adapter2;
    //  public static HashMap<Integer, Object> hashMapList = new HashMap<>();
    ArrayList<Integer> list = new ArrayList<>();
    ArrayList<Integer> list1 = new ArrayList<>();
    ArrayList<Integer> list2 = new ArrayList<>();

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityHomeFilterBinding) viewBaseBinding;

        adapter = new TagAdapter(getList(), HomeFilterActivity.this);
        adapter1 = new TagAdapter1(getList1(), HomeFilterActivity.this);
        adapter2 = new TagAdapter2(getList2(), HomeFilterActivity.this);
        list.clear();
        list1.clear();
        list2.clear();

        binding.actionBar.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.actionBar.tvTitle.setText(R.string.set_your_filter);
        binding.actionBar.ivReset.setVisibility(View.VISIBLE);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);


        binding.rcvKeywords.setLayoutManager(linearLayoutManager);
        binding.rcvKeywords.setAdapter(adapter);
        binding.rcvKeywords.addOnItemTouchListener(new RecyclerItemClickListener(HomeFilterActivity.this, (view, position) -> {
            Log.d("Sunny", " pos   " + position);
            list.add(position);
            adapter.getClickPosition(list, 0);
        }));

        binding.rcvKeywordsEducation.setLayoutManager(linearLayoutManager1);
        binding.rcvKeywordsEducation.setAdapter(adapter1);
        binding.rcvKeywordsEducation.addOnItemTouchListener(new RecyclerItemClickListener(HomeFilterActivity.this, (view, position) -> {
            Log.d("Sunny", " pos   " + position);
         //   list1.clear();
            list1.add(position);
            adapter1.getClickPosition(list1, 1);
        }));

        binding.rcvKeywordsTraining.setLayoutManager(linearLayoutManager2);
        binding.rcvKeywordsTraining.setAdapter(adapter2);
        binding.rcvKeywordsTraining.addOnItemTouchListener(new RecyclerItemClickListener(HomeFilterActivity.this, (view, position) -> {
            Log.d("Sunny", " pos   " + position);
           // list2.clear();
            list2.add(position);
            adapter2.getClickPosition(list2, 2);
        }));


        binding.actionBar.ivReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                list1.clear();
                list2.clear();
                adapter.getClickPosition(list, 0);
                adapter1.getClickPosition(list, 0);
                adapter2.getClickPosition(list, 0);
            }
        });

        /*binding.rcvKeywordsTraining.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false));
        binding.rcvKeywordsTraining.setAdapter(new KeywordsAdapter(this, this, sub));

        binding.rcvKeywordsEducation.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false));
        binding.rcvKeywordsEducation.setAdapter(new KeywordsAdapter(this, this, sub));*/

    }


    @Override
    protected int getLayoutById() {
        return R.layout.activity_home_filter;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);

    }

    @Override
    public void onTextClick(String text) {

    }

    private List<Tag> getList() {
        List<Tag> sub = new ArrayList<>();
        sub.add(new Tag("Construction"));
        sub.add(new Tag("Industrial"));
        sub.add(new Tag("Motive Power"));
        sub.add(new Tag("Service"));
        sub.add(new Tag("Technology"));
       // sub.add(new Tag("Education"));

        return sub;

    }

    private List<Tag> getList1() {
        List<Tag> sub = new ArrayList<>();
        sub.add(new Tag("Apprenticeship"));
        sub.add(new Tag("Secondary School Diploma"));
        sub.add(new Tag("College Diploma"));
        sub.add(new Tag("University Degree"));

        return sub;

    }

    private List<Tag> getList2() {
        List<Tag> sub = new ArrayList<>();
        sub.add(new Tag("Yes"));
        sub.add(new Tag("No"));
        // sub.add(new Tag("Option 03"));

        return sub;

    }

}