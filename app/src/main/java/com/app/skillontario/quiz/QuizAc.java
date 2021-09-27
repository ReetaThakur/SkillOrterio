package com.app.skillontario.quiz;

import android.content.Intent;
import android.net.Uri;

import com.app.skillontario.BottomBarActivity;
import com.app.skillontario.adapter.AdapterCong;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.dialogs.DialogWithMsg;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillontario.utils.Utils;
import com.app.skillorterio.R;
import com.app.skillorterio.databinding.QuizAcBinding;

import static com.app.skillontario.quiz.QuizStepAc.quizFinalResultModel;

public class QuizAc extends BaseActivity {
    //private TabAdapter tabAdapter;
    private QuizAcBinding binding;
    AdapterCong adapter2;

    @Override
    protected void initUi() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        binding = (QuizAcBinding) viewBaseBinding;

        binding.share.setOnClickListener(v -> {

            if (!MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.GUEST_FLOW)) {
                Utils.share1(QuizAc.this, getString(R.string.result_share) +
                        "", quizFinalResultModel.get(0).getImage(), quizFinalResultModel.get(0).getJobSector(), "quiz", quizFinalResultModel.get(0).getId());

            } else {
                // showDialog();
                try {
                    Utils.guestMethod(QuizAc.this, "homeFragment");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        });

        binding.sendInvite.setOnClickListener(v -> {

            Utils.share1(QuizAc.this, getString(R.string.invite_share) +
                    "", quizFinalResultModel.get(0).getImage(), quizFinalResultModel.get(0).getJobSector(), "home", quizFinalResultModel.get(0).getId());

        });
        binding.retake.tvRetake.setOnClickListener(v -> startActivity(new Intent(this, QuizStepAc.class)));
        binding.home.setOnClickListener(v -> startActivity(new Intent(this, BottomBarActivity.class)));

        //setAdapter();
        showRecycler();

        //  binding.mainLay.post(() -> showDialog());

    }


    private void showRecycler() {
        binding.recyCong.setHasFixedSize(true);
        adapter2 = new AdapterCong(QuizAc.this, quizFinalResultModel);
        binding.recyCong.setAdapter(adapter2);

       /* binding.recyCong.addOnItemTouchListener(new RecyclerItemClickListener(QuizAc.this, (view, position) -> {

        }));*/
    }


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
        intent.putExtra(Intent.EXTRA_TITLE, getString(R.string.came_and_join));

        startActivity(Intent.createChooser(intent, "Share Link"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        quizFinalResultModel = null;
    }

    void showDialog() {
        try {
            DialogWithMsg dialogWithMsg = new DialogWithMsg(QuizAc.this, 0, getString(R.string.app_name), getString(R.string.sign_result), getString(R.string.okay), null, 1);
            dialogWithMsg.show();
        } catch (Exception e) {
        }
    }

    void showDialogMessage() {
        try {
            DialogWithMsg dialogWithMsg = new DialogWithMsg(QuizAc.this, 0, getString(R.string.app_name), getString(R.string.sign_result1), getString(R.string.okay), null, 1);
            dialogWithMsg.show();
        } catch (Exception e) {
        }
    }
}