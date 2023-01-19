package com.dennisgutierrez.dam_g7_proyecto_1p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserActivity extends AppCompatActivity {

    TextView emailTextView;
    TextView usuarioTextView;
    MaterialButton logoutButton;
    ImageView atencionImageView, aprendizajeImageView, percepcionImageView, pensamientoImageVIew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        emailTextView = findViewById(R.id.emailTextView);
        usuarioTextView = findViewById(R.id.usuarioTextView);
        logoutButton = findViewById(R.id.logoutButton);
        atencionImageView = findViewById(R.id.atencionImageView);
        aprendizajeImageView = findViewById(R.id.aprendizajeImageView);
        percepcionImageView = findViewById(R.id.percepcionImageView);
        pensamientoImageVIew = findViewById(R.id.pensamientoImageView);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            emailTextView.setText(user.getEmail());
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        atencionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, AtencionActivity.class);
                startActivity(intent);
                finish();
            }
        });

        aprendizajeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, AprendizajeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        percepcionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, PercepcionActivity.class);
                startActivity(intent);
                finish();
            }
        });

        pensamientoImageVIew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, PensamientoActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.acercaDe:
               Intent call_principal = new Intent(this, AcercaDeActivity.class);
               startActivity(call_principal);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}