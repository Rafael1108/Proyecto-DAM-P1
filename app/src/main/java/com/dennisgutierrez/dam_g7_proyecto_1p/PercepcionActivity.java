package com.dennisgutierrez.dam_g7_proyecto_1p;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class PercepcionActivity extends AppCompatActivity {
    private FirebaseFirestore bdService;
    private FirebaseStorage stService  ;
    private String IdUsuario;


    //region variables para Timer
    private Chronometer Cronometro;
    private long timeLeft_ms=0;
    //endregion

    //region variables para preguntas
    private String repuesta;
    private int intentos;
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
        bdService= FirebaseFirestore.getInstance();
        stService = FirebaseStorage.getInstance();

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

        CargarPreguntas();

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

    //region Preguntas
    private void  CargarPreguntas()
    {
        intentos=0;
        repuesta="";

        FirebaseFirestore bd= FirebaseFirestore.getInstance();
        DocumentReference pregunta= bd.collection("percepcion").document("Q001");

        pregunta.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(PercepcionActivity.this, document.get("ImgBase").toString(), Toast.LENGTH_LONG).show();


                        txtIndicaciones.setText(document.get("indicaciones").toString());
                        imgReal.setImageResource(percepcion_library.getImagen(document.get("ImgBase").toString()));

                    }
                    else{
                        Toast.makeText(PercepcionActivity.this,"No such document", Toast.LENGTH_LONG).show();
                    }
                } else {
                    txtIndicaciones.setText("get failed with " + task.getException().toString());
                    Toast.makeText(PercepcionActivity.this, "get failed with " + task.getException(), Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    //endregion

    //código para guardar en firebase
    private void postSave(String value){
        Map<String, Object> map= new HashMap<>();
        map.put("clave",value);

        bdService.collection("nombrecoleecion").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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