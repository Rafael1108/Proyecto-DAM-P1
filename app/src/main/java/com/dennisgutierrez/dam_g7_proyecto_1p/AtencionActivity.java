package com.dennisgutierrez.dam_g7_proyecto_1p;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
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

public class AtencionActivity extends AppCompatActivity {

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
    ImageView imgBase,imgAt1,imgAt2,imgAt3,imgAt4,imgAt5,imgAt6,imgAt7,imgAt8,imgAt9,
    imgAt10,imgAt11,imgAt12,imgAt13,imgAt14,imgAt15;

    Button atencionRegresarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atencion);

        //Instancia la base de Firestore
        bdService= FirebaseFirestore.getInstance();
        stService = FirebaseStorage.getInstance();

        //Configuracion de la vista
        this.setTitle("Atención");

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
        imgBase= (ImageView) findViewById(R.id.ImgBase);
        imgAt1= (ImageView) findViewById(R.id.imgAt1);
        imgAt2= (ImageView) findViewById(R.id.imgAt2);
        imgAt3= (ImageView) findViewById(R.id.imgAt3);
        imgAt4= (ImageView) findViewById(R.id.imgAt4);
        imgAt5= (ImageView) findViewById(R.id.imgAt5);
        imgAt6= (ImageView) findViewById(R.id.imgAt6);
        imgAt7= (ImageView) findViewById(R.id.imgAt7);
        imgAt8= (ImageView) findViewById(R.id.imgAt8);
        imgAt9= (ImageView) findViewById(R.id.imgAt9);
        imgAt10= (ImageView) findViewById(R.id.imgAt10);
        imgAt11= (ImageView) findViewById(R.id.imgAt11);
        imgAt12= (ImageView) findViewById(R.id.imgAt12);
        imgAt13= (ImageView) findViewById(R.id.imgAt13);
        imgAt14= (ImageView) findViewById(R.id.imgAt14);
        imgAt15= (ImageView) findViewById(R.id.imgAt15);

        atencionRegresarButton = findViewById(R.id.atencionRegresarButton);

        atencionRegresarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AtencionActivity.this, UserActivity.class);
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
        DocumentReference pregunta = bd.collection("atencion").document("Q002");

        pregunta.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                //        Toast.makeText(AtencionActivity.this, document.get("ImgBase").toString(), Toast.LENGTH_LONG).show();


                        txtIndicaciones.setText(atencion_library.getIndicaciones(document.get("indicaciones").toString()));
                        imgBase.setImageResource(atencion_library.getImagen(document.get("ImgBase").toString()));
                        imgAt1.setImageResource(atencion_library.getImagen(document.get("ImgAt1").toString()));
                        imgAt2.setImageResource(atencion_library.getImagen(document.get("ImgAt2").toString()));
                        imgAt3.setImageResource(atencion_library.getImagen(document.get("ImgAt1").toString()));
                        imgAt4.setImageResource(atencion_library.getImagen(document.get("ImgAt2").toString()));
                        imgAt5.setImageResource(atencion_library.getImagen(document.get("ImgAt1").toString()));

                    }
                    else{
                        Toast.makeText(AtencionActivity.this,"No such document", Toast.LENGTH_LONG).show();
                    }
                } else {
                    txtIndicaciones.setText("get failed with " + task.getException().toString());
                    Toast.makeText(AtencionActivity.this, "get failed with " + task.getException(), Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    //endregion

    //código para guardar en firebase
    private void postSave(String value){
        Map<String, Object> map= new HashMap<>();
        map.put("clave",value);

        bdService.collection("nombrecoleccion").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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