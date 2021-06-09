package com.app.skillontario.quiz;
import com.app.skillontario.adapter.TabAdapter;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.TakeQuizAcBinding;

public class TakeQuizAc extends BaseActivity {
    private TabAdapter tabAdapter;
    private TakeQuizAcBinding binding;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (TakeQuizAcBinding) viewBaseBinding;

        binding.noData.ivNoData.setImageResource(R.drawable.ic_take_quiz);
        binding.noData.tvNoData.setText(getResources().getText(R.string.take_qz));

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