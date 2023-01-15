package com.dennisgutierrez.dam_g7_proyecto_1p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AprendizajeActivity extends AppCompatActivity {

    Button aprendizajeRegresarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprendizaje);

        aprendizajeRegresarButton = findViewById(R.id.aprendizajeRegresarButton);

        aprendizajeRegresarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AprendizajeActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}