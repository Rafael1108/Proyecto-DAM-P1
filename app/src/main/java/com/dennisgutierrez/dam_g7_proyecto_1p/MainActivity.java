package com.dennisgutierrez.dam_g7_proyecto_1p;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.service.autofill.TextValueSanitizer;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Animaciones splash

        Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);

        TextView proyectoTextView = findViewById(R.id.splashProyectoTextView);
        TextView damTextView = findViewById(R.id.splashDamTextView);
        ImageView logoUgImageView = findViewById(R.id.splashLogoUgImageView);

        logoUgImageView.setAnimation(animacion1);
        proyectoTextView.setAnimation(animacion2);
        damTextView.setAnimation(animacion2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(MainActivity.this, UserActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    Pair[] pairs = new Pair [2];
                    pairs[0] = new Pair<View, String>(logoUgImageView,  "logoImageTrans");
                    pairs[1] = new Pair<View, String>(proyectoTextView,  "textTrans");

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                        startActivity(intent,  options.toBundle());
                    }else {
                        startActivity(intent);
                        finish();
                    }
                    startActivity(intent);
                    finish();
                }
            }
        }, 4000);
    }
}