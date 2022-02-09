package com.app.skillsontario.quiz;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.app.skillsontario.BottomBarActivity;
import com.app.skillsontario.R;
import com.app.skillsontario.adapter.AdapterCong;
import com.app.skillsontario.baseClasses.BaseActivity;
import com.app.skillsontario.baseClasses.CrashLogger;
import com.app.skillsontario.constants.SharedPrefsConstants;
import com.app.skillsontario.databinding.QuizAcBinding;
import com.app.skillsontario.dialogs.DialogWithMsg;
import com.app.skillsontario.utils.MySharedPreference;
import com.app.skillsontario.utils.Utils;


import static com.app.skillsontario.quiz.QuizStepAc.quizFinalResultModel;

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

            try {
                CrashLogger.INSTANCE.trackEventsFirebase("Share_Quiz_Result", "QuizAc");
            } catch (Exception e) {
            }

        });

        binding.sendInvite.setOnClickListener(v -> {

            Utils.share1(QuizAc.this, getString(R.string.invite_share) +
                    "", quizFinalResultModel.get(0).getImage(), quizFinalResultModel.get(0).getJobSector(), "home", quizFinalResultModel.get(0).getId());

            try {
                CrashLogger.INSTANCE.trackEventsFirebase("Send_Invite", "QuizAc");
            } catch (Exception e) {
            }

        });
        // binding.retake.tvRetake.setOnClickListener(v -> startActivity(new Intent(this, QuizStepAc.class)));
        binding.home.setOnClickListener(v -> startActivity(new Intent(this, BottomBarActivity.class)));


        binding.retake.tvRetake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizAc.this, QuizStepAc.class));
                try {
                    CrashLogger.INSTANCE.trackEventsFirebase("Retake_a_Quiz", "QuizAc");
                } catch (Exception e) {
                }
            }
        });
        //setAdapter();
        showRecycler();

        //  binding.mainLay.post(() -> showDialog());

        try {
            CrashLogger.INSTANCE.trackEventsFirebase("Completing_a_Quiz", "QuizAc");
        } catch (Exception e) {
        }

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