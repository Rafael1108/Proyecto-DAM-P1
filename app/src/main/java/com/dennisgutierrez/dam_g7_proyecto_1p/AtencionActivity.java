package com.dennisgutierrez.dam_g7_proyecto_1p;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AtencionActivity extends AppCompatActivity {

    private static int MAXPREGUNTAS=3;
    private FirebaseFirestore bdService;
    //private FirebaseStorage stService  ;
    private String IdUsuario;


    //region variables para Timer
    private Chronometer Cronometro;
    private long timeLeft_ms=0;
    //endregion

    //region variables para preguntas
    private String respuesta;
    private String idJuego;
    private int intentos;
    //endregion


    //region variables para score
    private int scoreActual;
    private int totalJuegos;
    private int totalIntentos;
    //endregion

    //region Inicializacion de Objetos
    TextView txtScore,txtIndicaciones;
    ImageView imgBase,imgAt1,imgAt2,imgAt3,imgAt4,imgAt5,imgAt6,imgAt7,imgAt8,imgAt9,
    imgAt10,imgAt11,imgAt12,imgAt13,imgAt14,imgAt15,imgAt16,imgAt17,imgAt18,imgAt19,imgAt20;

    Button atencionRegresarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atencion);

        //Instancia la base de Firestore
        bdService= FirebaseFirestore.getInstance();
        //stService = FirebaseStorage.getInstance();

        //Configuracion de la vista
        this.setTitle("Atención");

        //Se optiene valores de usuario para la session del activity
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            IdUsuario=user.getUid();
        }

        //Instancia TimerLapse
        Cronometro= (Chronometer) findViewById(R.id.Cronometro);
        //TimerStart();

        //CargarPreguntas();

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
        imgAt16= (ImageView) findViewById(R.id.imgAt16);
        imgAt17= (ImageView) findViewById(R.id.imgAt17);
        imgAt18= (ImageView) findViewById(R.id.imgAt18);
        imgAt19= (ImageView) findViewById(R.id.imgAt19);
        imgAt20= (ImageView) findViewById(R.id.imgAt20);


        atencionRegresarButton = findViewById(R.id.atencionRegresarButton);

        atencionRegresarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AtencionActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        cargarScoreUsuario();
        buscarPregunta();
        txtIndicaciones.setText(R.string. percep_strindica_found);

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
    private void limpiarPreguntas()
    {
        //progStatus.setVisibility(View.VISIBLE);
        intentos=1;
        respuesta = "";
        txtIndicaciones.setText(R.string. percep_strindica_found);
        imgBase.setImageResource(0);
        imgAt1.setImageResource(0);
        imgAt2.setImageResource(0);
        imgAt3.setImageResource(0);
        imgAt4.setImageResource(0);
        imgAt5.setImageResource(0);
        imgAt6.setImageResource(0);
        imgAt7.setImageResource(0);
        imgAt8.setImageResource(0);
        imgAt9.setImageResource(0);
        imgAt10.setImageResource(0);
        imgAt11.setImageResource(0);
        imgAt12.setImageResource(0);
        imgAt13.setImageResource(0);
        imgAt14.setImageResource(0);
        imgAt15.setImageResource(0);
        imgAt16.setImageResource(0);
        imgAt17.setImageResource(0);
        imgAt18.setImageResource(0);
        imgAt19.setImageResource(0);
        imgAt20.setImageResource(0);
    }

    private void buscarPregunta()
    {
        if (totalJuegos>MAXPREGUNTAS)
        {
            limpiarPreguntas();
            txtIndicaciones.setText(R.string.percep_strindica_error);
        }
        else {
            //Se busca un juego al azar
            Random rand = new Random();
            int intId = rand.nextInt(MAXPREGUNTAS);
            //para no empezar en 0
            intId++;
            String tmpPregunta = "Q" + intId;


            //Crga en memoria la coleccion
            CollectionReference logUsuario = bdService.collection("atencion_score").document(IdUsuario).collection("logs");

            // Consulta, se verifica si el juego ya fue realizado.
            Query query = logUsuario.whereEqualTo("idjuego", tmpPregunta);

            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        int rows = 0;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            rows++;
                        }

                        if (rows == 0) {
                            CargarPreguntas(tmpPregunta);
                        } else {
                            buscarPregunta();
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
        }
    }



    private void  CargarPreguntas(String juego)
    {
        //intentos=0;
        //repuesta="";
        //FirebaseFirestore bd= FirebaseFirestore.getInstance();
        limpiarPreguntas();
        idJuego=juego;
        DocumentReference pregunta = bdService.collection("atencion").document(juego);

        pregunta.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                //        Toast.makeText(AtencionActivity.this, document.get("ImgBase").toString(), Toast.LENGTH_LONG).show();

                        txtIndicaciones.setText(atencion_library.getIndicaciones(document.get("indicaciones").toString()));
                        respuesta= document.get("respuesta").toString().toUpperCase();
                        imgBase.setImageResource(atencion_library.getImagen(document.get("ImgBase").toString()));
                        imgAt1.setImageResource(atencion_library.getImagen(document.get("ImgAt1").toString()));
                        imgAt2.setImageResource(atencion_library.getImagen(document.get("ImgAt2").toString()));
                        imgAt3.setImageResource(atencion_library.getImagen(document.get("ImgAt3").toString()));
                        imgAt4.setImageResource(atencion_library.getImagen(document.get("ImgAt4").toString()));
                        imgAt5.setImageResource(atencion_library.getImagen(document.get("ImgAt5").toString()));
                        imgAt6.setImageResource(atencion_library.getImagen(document.get("ImgAt6").toString()));
                        imgAt7.setImageResource(atencion_library.getImagen(document.get("ImgAt7").toString()));
                        imgAt8.setImageResource(atencion_library.getImagen(document.get("ImgAt8").toString()));
                        imgAt9.setImageResource(atencion_library.getImagen(document.get("ImgAt9").toString()));
                        imgAt10.setImageResource(atencion_library.getImagen(document.get("ImgAt10").toString()));
                        imgAt11.setImageResource(atencion_library.getImagen(document.get("ImgAt11").toString()));
                        imgAt12.setImageResource(atencion_library.getImagen(document.get("ImgAt12").toString()));
                        imgAt13.setImageResource(atencion_library.getImagen(document.get("ImgAt13").toString()));
                        imgAt14.setImageResource(atencion_library.getImagen(document.get("ImgAt14").toString()));
                        imgAt15.setImageResource(atencion_library.getImagen(document.get("ImgAt15").toString()));
                        imgAt16.setImageResource(atencion_library.getImagen(document.get("ImgAt16").toString()));
                        imgAt17.setImageResource(atencion_library.getImagen(document.get("ImgAt17").toString()));
                        imgAt18.setImageResource(atencion_library.getImagen(document.get("ImgAt18").toString()));
                        imgAt19.setImageResource(atencion_library.getImagen(document.get("ImgAt19").toString()));
                        imgAt20.setImageResource(atencion_library.getImagen(document.get("ImgAt20").toString()));

                        TimerStart();

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

    public void onImgClick(View v){
        ImageView ImgOpc= (ImageView) v;
        if(v.getTransitionName().trim().toUpperCase().equals(respuesta)){
            Cronometro.stop();
            guardarProgreso();
            actualizarScoreUsuario();
            Toast.makeText(AtencionActivity.this, "Correcto!", Toast.LENGTH_LONG).show();
            buscarPregunta();
        }
        else {
            Toast.makeText(AtencionActivity.this, "Intento # "+intentos + " incorrecto!", Toast.LENGTH_SHORT).show();
            intentos ++;
        }
    }

    //region Métodos CRUD en firebase
    private void guardarProgreso(){
        Map<String, Object> progreso= new HashMap<>();
        progreso.put("intentos",intentos);
        progreso.put("tiempo",Cronometro.getText());
        progreso.put("idjuego",idJuego);


        //almacena el log progreso
        bdService.collection("atencion_score")
                .document(IdUsuario).collection("logs").document(Calendar.getInstance().getTime().toString()).set(progreso)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }
    private void cargarScoreUsuario(){
        DocumentReference pregunta= bdService.collection("atencion_score").document(IdUsuario);
        pregunta.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot document,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (document != null && document.exists()) {
                    totalIntentos= Integer.parseInt( document.get("intentos").toString() );
                    totalJuegos= Integer.parseInt( document.get("total_juegos").toString() );
                    scoreActual= Integer.parseInt( document.get("tasa_exito").toString() );

                } else {
                    totalIntentos= 0;
                    totalJuegos=0;
                    scoreActual= 100;
                }

                //progAciertos.setProgress(scoreActual);
                txtScore.setText(scoreActual + "% de aciertos." );
            }
        });

    }
    private void actualizarScoreUsuario(){
        totalJuegos++;
        totalIntentos+= intentos;
        Map<String, Object> score= new HashMap<>();
        score.put("intentos", (totalIntentos));
        score.put("total_juegos", totalJuegos );
        score.put("tasa_exito",Integer.divideUnsigned(totalJuegos*100,totalIntentos) );


        bdService.collection("atencion_score")
                .document(IdUsuario)
                .set(score)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }
    //endregion

}