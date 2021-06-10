package com.app.skillontario.quiz;
import android.content.Intent;

import com.app.skillontario.SignIn.WelcomeActivity;
import com.app.skillontario.adapter.TabAdapter;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.TakeQuizAcBinding;

public class TakeQuizAc extends BaseActivity {
    private TakeQuizAcBinding binding;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (TakeQuizAcBinding) viewBaseBinding;
        binding.actionBarL.tvTitle.setText(getResources().getText(R.string.take_qz_ttl));

        binding.done.setOnClickListener(v->startActivity(new Intent(this, QuizStepAc.class)));
       noDataSet();
    }

    private void noDataSet() {
        binding.noData.ivNoData.setImageResource(R.drawable.ic_take_quiz);
        binding.noData.tvNoData.setText(getResources().getText(R.string.take_qz));
        binding.noData.tvNoData.setTextColor(getResources().getColor(R.color.black));
    }

    @Override
    protected int getLayoutById() {
        return R.layout.take_quiz_ac;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
    }

}