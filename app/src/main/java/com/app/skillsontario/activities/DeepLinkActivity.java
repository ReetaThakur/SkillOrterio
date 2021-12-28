package com.app.skillsontario.activities;

import android.content.Intent;

import com.app.skillsontario.R;
import com.app.skillsontario.baseClasses.BaseActivity;
import com.app.skillsontario.quiz.TakeQuizAc;


public class DeepLinkActivity extends BaseActivity {


    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    protected void initUi() {
        Intent intent = getIntent();


        if (intent.hasExtra("type")) {
            String type = intent.getStringExtra("type");

            if (type.equalsIgnoreCase("jobProfile")) {
                final String id = intent.getStringExtra("id");
                Intent intent1 = new Intent(DeepLinkActivity.this, JobDetailsActivity.class);
                intent1.putExtra("Popular", id);
                startActivity(intent1);
                finish();

            } else if (type.equalsIgnoreCase("quiz")) {
                // final String id = intent.getStringExtra("id");
                startActivity(new Intent(DeepLinkActivity.this, TakeQuizAc.class));
                finish();
            } else if (type.equalsIgnoreCase("home")) {
                // final String id = intent.getStringExtra("id");
                Intent intent1 = new Intent(DeepLinkActivity.this, TakeQuizActivity.class);
                startActivity(intent1);
                finish();
            }
        }

    }

      /*  Type:  instruction , tips , video, news, beefCut,
                id: beefCut , cooking MethodID ,
                videoUrl: news. Video

        Scheme :com.roundup
        Deeplinking Url:  “com.roundup://tips?id=1234567”*/


    @Override
    protected int getLayoutById() {
        return R.layout.activity_deeplinking;
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}
