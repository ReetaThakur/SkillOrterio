package com.app.skillontario.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.skillontario.adapter.QuizStepAdapter;
import com.app.skillontario.adapter.TabAdapter;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.QuizAcBinding;
import com.app.skillorterio.databinding.QuizStepAcBinding;
import com.app.skillorterio.databinding.TakeQuizAcBinding;

import java.util.ArrayList;

public class QuizStepAc extends BaseActivity {
    private QuizStepAcBinding binding;
    int count = 1;
    ArrayList<String> questionList = new ArrayList<>();
    ArrayList<String> answerList = new ArrayList<>();

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (QuizStepAcBinding) viewBaseBinding;

        questionList.clear();
        answerList.clear();

        questionList.add("Where would you prefer to work?");
        questionList.add("How would you like to spend your day on the job?");
        questionList.add("Which problem you would like to solve?");
        questionList.add("What kind of working hours would you prefer?");
        questionList.add("Would you be comfortable working at heights?");

        validate();

        next();
    }

    private void next() {
        binding.done.setOnClickListener(v -> {
            if (count == binding.myProgress.getMax()) {
                startActivity(new Intent(this, QuizAc.class));
                return;
            }
            count++;
            countNotify();
        });
        binding.close.setOnClickListener(v -> {
            if (count == 1) {
                onBackPressed();
            }
            count--;
            countNotify();

        });
    }

    private void countNotify() {
        binding.myProgress.setProgress(count);
        binding.tvCount.setText(count + "/" + binding.myProgress.getMax());

        validate();

    }

    private void validate() {

        answerList.clear();
        if (count == 1) {
            binding.question.setText(questionList.get(0));
            answerList.add("Outdoors");
            answerList.add("Indoors");
            answerList.add("Both");

        } else if (count == 2) {
            binding.question.setText(questionList.get(1));
            answerList.add("Being physically active throughout the day");
            answerList.add("Assembling things within a defined process");
            answerList.add("Fixing and building motor vehicles");
            answerList.add("Helping and interacting with people");
            answerList.add("Working primarily with technology");
        } else if (count == 3) {
            binding.question.setText(questionList.get(2));
            answerList.add("Repair and restore buildings or roads");
            answerList.add("Use machinery to fix things");
            answerList.add("Make a client happy");
            answerList.add("Find bugs in a computer code");
        } else if (count == 4) {
            binding.question.setText(questionList.get(3));
            answerList.add("Fixed hours");
            answerList.add("Flexible hours");
            answerList.add("My own timing");
            // answerList.add("");
        } else if (count == 5) {
            binding.question.setText(questionList.get(4));
            answerList.add("Yes");
            answerList.add("No");
        }

        QuizStepAdapter quizStepAdapter = new QuizStepAdapter(getApplicationContext(), count, answerList);
        binding.rvItems.setVisibility(View.VISIBLE);
        binding.progressQues.setVisibility(View.GONE);
       /* if (count == 2) {
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            binding.rvItems.setLayoutManager(mLayoutManager);
            binding.rvItems.setAdapter(quizStepAdapter);
        } else if (count == 3) {
            binding.rvItems.setVisibility(View.GONE);
            binding.progressQues.setVisibility(View.VISIBLE);
        } else*/
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.rvItems.setLayoutManager(mLayoutManager);
        binding.rvItems.setAdapter(quizStepAdapter);


    }


    @Override
    protected int getLayoutById() {
        return R.layout.quiz_step_ac;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
    }

}