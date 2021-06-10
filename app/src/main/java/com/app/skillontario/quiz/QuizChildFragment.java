package com.app.skillontario.quiz;

import android.view.View;

import com.app.skillontario.baseClasses.BaseFragment;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.FragmentQuizCBinding;
import com.app.skillorterio.databinding.FragmentTab4Binding;

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
