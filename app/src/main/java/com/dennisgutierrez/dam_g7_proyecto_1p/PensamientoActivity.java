package com.dennisgutierrez.dam_g7_proyecto_1p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PensamientoActivity extends AppCompatActivity {

    Button pensamientoRegresarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pensamiento);

        pensamientoRegresarButton = findViewById(R.id.pensamientoRegresarButton);

        pensamientoRegresarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PensamientoActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}