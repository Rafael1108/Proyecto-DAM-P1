package com.dennisgutierrez.dam_g7_proyecto_1p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PercepcionActivity extends AppCompatActivity {

    Button percepcionRegresarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percepcion);

        percepcionRegresarButton = findViewById(R.id.percepcionRegresarButton);

        percepcionRegresarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PercepcionActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}