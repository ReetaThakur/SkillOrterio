package com.app.skillontario.activities;

import android.content.Intent;

import com.app.skillontario.adapter.QuizAdapter;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.quiz.QuizStepAc;
import com.app.skillontario.utils.RecyclerItemClickListener;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.ActivityTakeQuizBinding;

public class TakeQuizActivity extends BaseActivity {

    private ActivityTakeQuizBinding binding;
    QuizAdapter adapter;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (ActivityTakeQuizBinding) viewBaseBinding;
        binding.tvTakeQuiz.setOnClickListener(v->startActivity(new Intent(this, QuizStepAc.class)));

        showPopularCareerRecycler();
    }


    private void showPopularCareerRecycler() {
        binding.recyQuiz.setHasFixedSize(true);
        adapter = new QuizAdapter(TakeQuizActivity.this, true);
        binding.recyQuiz.setAdapter(adapter);

      /*  binding.recyQuiz.addOnItemTouchListener(new RecyclerItemClickListener(TakeQuizActivity.this, (view, position) -> {
            startActivity(new Intent(this, JobDetailsActivity.class));
        }));*/
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
    }


    @Override
    protected int getLayoutById() {
        return R.layout.activity_take_quiz;
    }


}