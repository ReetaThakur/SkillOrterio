package com.app.skillontario.activities;

import android.content.Intent;
import android.graphics.Color;

import com.app.skillontario.BottomBarActivity;
import com.app.skillontario.SignIn.SignInActivity;
import com.app.skillontario.baseClasses.BaseActivity;
import com.app.skillontario.quiz.TakeQuizAc;
import com.app.skillorterio.R;


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
                Intent intent1 = new Intent(DeepLinkActivity.this, BottomBarActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
