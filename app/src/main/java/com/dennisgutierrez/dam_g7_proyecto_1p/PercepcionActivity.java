package com.dennisgutierrez.dam_g7_proyecto_1p;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PercepcionActivity extends AppCompatActivity {
    private FirebaseFirestore firestoreService;
    private String IdUsuario;


    //region variables para Timer
    private Chronometer Cronometro;
    private long timeLeft_ms=0;
    //endregion

    //region Inicializacion de Objetos
    TextView txtScore,txtIndicaciones;
    ImageView imgReal,imgOpc1,imgOpc2,imgOpc3,imgOpc4,imgOpc5,imgOpc6;
    //endregion
    Button percepcionRegresarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percepcion);

        //Instancia la base de Firestore
        firestoreService= FirebaseFirestore.getInstance();

        //Configuracion de la vista
        this.setTitle("Percepción");

        //Se optiene valores de usuario para la session del activity
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            IdUsuario=user.getUid();
        }

        //Instancia TimerLapse
        Cronometro= (Chronometer) findViewById(R.id.Cronometro);
        TimerStart();

        //region Intancia de Objetos
        txtScore= (TextView) findViewById(R.id.txtScore);
        txtIndicaciones= (TextView) findViewById(R.id.txtIndicaciones);
        imgReal= (ImageView) findViewById(R.id.ImgBase);
        imgOpc1= (ImageView) findViewById(R.id.imgOpc1);
        imgOpc2= (ImageView) findViewById(R.id.imgOpc2);
        imgOpc3= (ImageView) findViewById(R.id.imgOpc3);
        imgOpc4= (ImageView) findViewById(R.id.imgOpc4);
        imgOpc5= (ImageView) findViewById(R.id.imgOpc5);
        imgOpc6= (ImageView) findViewById(R.id.imgOpc6);

        percepcionRegresarButton = findViewById(R.id.percepcionRegresarButton);
        //endregion
        percepcionRegresarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PercepcionActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    //region TimerStart()
    private  void TimerStart()
    {
        if (Cronometro.isActivated()){ Cronometro.stop();}

        Cronometro.setBase(SystemClock.elapsedRealtime());
        Cronometro.start();
    }
    //endregion


    //código para guardar en firebase
    private void postSave(String value){
        Map<String, Object> map= new HashMap<>();
        map.put("clave",value);

        firestoreService.collection("nombrecoleecion").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}