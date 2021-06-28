package com.app.skillontario.quiz;

import android.content.Intent;
import android.net.Uri;

import androidx.core.app.ShareCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.app.skillontario.MainActivity;
import com.app.skillontario.activities.NewsDetailAc;
import com.app.skillontario.adapter.AdapterCong;
import com.app.skillontario.adapter.RecentNewsAdapter;
import com.app.skillontario.adapter.TabAdapter;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.utils.RecyclerItemClickListener;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.QuizAcBinding;

public class QuizAc extends BaseActivity {
    private TabAdapter tabAdapter;
    private QuizAcBinding binding;
    AdapterCong adapter2;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (QuizAcBinding) viewBaseBinding;

        binding.share.setOnClickListener(v -> {
            ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setChooserTitle(getResources().getText(R.string.app_name))
                    .setText("https://www.skillsontario.com")
                    .startChooser();
        });

        binding.sendInvite.setOnClickListener(v -> {
            onShareClicked();
        });
        binding.retake.tvRetake.setOnClickListener(v -> startActivity(new Intent(this, QuizStepAc.class)));
        binding.home.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));

        //setAdapter();
        showRecycler();
    }

    private void showRecycler() {
        binding.recyCong.setHasFixedSize(true);
        adapter2 = new AdapterCong(QuizAc.this);
        binding.recyCong.setAdapter(adapter2);

        binding.recyCong.addOnItemTouchListener(new RecyclerItemClickListener(QuizAc.this, (view, position) -> {

        }));
    }

/*   private void setAdapter() {
       tabAdapter = new TabAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
       tabAdapter.addFragment(new QuizChildFragment("1"), "");
       tabAdapter.addFragment(new QuizChildFragment("2"), "");
       tabAdapter.addFragment(new QuizChildFragment("3"), "");
       tabAdapter.addFragment(new QuizChildFragment("4"), "");
       tabAdapter.addFragment(new QuizChildFragment("5"), "");


       binding.viewPager.setAdapter(tabAdapter);
   }*/


    @Override
    protected int getLayoutById() {
        return R.layout.quiz_ac;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
    }

    private void onShareClicked() {

        String link = "https://play.google.com/store/apps/details?id=com.whatsapp";

        Uri uri = Uri.parse(link);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, link.toString());
        intent.putExtra(Intent.EXTRA_TITLE, "Sample App");

        startActivity(Intent.createChooser(intent, "Share Link"));
    }
}