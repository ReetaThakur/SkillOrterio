package com.app.skillontario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.skillontario.adapter.BookmarkAdapter;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.BookmarkAcBinding;
import com.app.skillorterio.databinding.ContactUsAcBinding;

public class BookmarkAc  extends BaseActivity {
    private BookmarkAcBinding binding;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (BookmarkAcBinding) viewBaseBinding;

        binding.actionBar.tvTitle.setText("Bookmarked");

        binding.rvItems.setAdapter(new BookmarkAdapter(this));

    }

    @Override
    protected int getLayoutById() {
        return R.layout.bookmark_ac;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
    }

}