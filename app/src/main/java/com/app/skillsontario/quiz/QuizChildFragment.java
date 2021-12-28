package com.app.skillsontario.quiz;

import android.view.View;

import com.app.skillsontario.R;
import com.app.skillsontario.baseClasses.BaseFragment;
import com.app.skillsontario.databinding.FragmentQuizCBinding;


public class QuizChildFragment extends BaseFragment {
    private String type;

    public QuizChildFragment() {
        // Required empty public constructor
    }

    public QuizChildFragment(String type) {
        // Required empty public constructor
        this.type = type;
    }
    private FragmentQuizCBinding binding;



    @Override
    protected void initUi() {
        binding = (FragmentQuizCBinding) viewDataBinding;
        getList();
    }

    private void getList() {
        if (binding != null) {
            try {
              //  binding.rlHolder.setVisibility(View.GONE);

                if (type == null)
                    type = "";
                if (type.equalsIgnoreCase("1")) {
                  //  binding.ivHolder.setImageResource(R.drawable.n1);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
    @Override
    protected int getLayoutById() {
        return R.layout.fragment_quiz_c;
    }

    @Override
    public void onClick(View view) {

    }
}
