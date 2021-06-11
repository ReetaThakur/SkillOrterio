package com.app.skillontario.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.quiz.QuizStepAc;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.NewsDetailAcBinding;
import com.app.skillorterio.databinding.TakeQuizAcBinding;

public class NewsDetailAc extends BaseActivity {
    private NewsDetailAcBinding binding;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (NewsDetailAcBinding) viewBaseBinding;

    }



    @Override
    protected int getLayoutById() {
        return R.layout.news_detail_ac;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
    }

}