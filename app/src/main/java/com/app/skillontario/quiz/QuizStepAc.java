package com.app.skillontario.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.app.skillontario.adapter.QuizStepAdapter;
import com.app.skillontario.adapter.TabAdapter;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.QuizAcBinding;
import com.app.skillorterio.databinding.QuizStepAcBinding;
import com.app.skillorterio.databinding.TakeQuizAcBinding;

public class QuizStepAc extends BaseActivity {
    private QuizStepAcBinding binding;
    int count=1;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (QuizStepAcBinding) viewBaseBinding;
        validate();

        next();
    }

    private void next() {
        binding.done.setOnClickListener(v->{
            if(count==binding.myProgress.getMax()){
                startActivity(new Intent(this, QuizAc.class));
                return;
            }
            count++;
     countNotify();
        });
        binding.close.setOnClickListener(v->{
            if(count==1){
                return;
            }
            count--;
            countNotify();
        });
    }

    private void countNotify() {
        binding.myProgress.setProgress(count);
        binding.tvCount.setText(count+"/"+binding.myProgress.getMax());
        if(count==3){

        }else {
            validate();
        }
    }

    private void validate() {
        QuizStepAdapter quizStepAdapter=new QuizStepAdapter(getApplicationContext(),count);
        if(count==4){
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            binding.rvItems.setLayoutManager(mLayoutManager);
        }else {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
            binding.rvItems.setLayoutManager(mLayoutManager);
        }
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