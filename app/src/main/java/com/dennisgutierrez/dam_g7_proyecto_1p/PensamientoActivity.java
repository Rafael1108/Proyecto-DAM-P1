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
import android.widget.ProgressBar;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PensamientoActivity extends AppCompatActivity {

    private static int MAXPREGUNTAS=10;
    private FirebaseFirestore bdService;
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
    ImageView imgReal,imgOpc1,imgOpc2;
    //imgOpc3,imgOpc4,imgOpc5,imgOpc6;
    ProgressBar progStatus, progAciertos;
    //endregion

    Button pensamientoRegresarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pensamiento);

        //Instancia la base de Firestore
        bdService= FirebaseFirestore.getInstance();

        //Configuracion de la vista
        this.setTitle("Pensamiento");

        //Se optiene valores de usuario para la session del activity
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            IdUsuario=user.getUid();
        }

        //Instancia TimerLapse
        Cronometro= (Chronometer) findViewById(R.id.Cronometro);

        //region Intancia de Objetos
        txtScore= (TextView) findViewById(R.id.txtScore);
        txtIndicaciones= (TextView) findViewById(R.id.txtIndicaciones);
        imgReal= (ImageView) findViewById(R.id.ImgBase);
        imgOpc1= (ImageView) findViewById(R.id.imgOpc1);
        imgOpc2= (ImageView) findViewById(R.id.imgOpc2);
        progStatus= (ProgressBar) findViewById(R.id.progBar);
        progAciertos= (ProgressBar) findViewById(R.id.progBar);

        pensamientoRegresarButton = findViewById(R.id.pensamientoRegresarButton);

        pensamientoRegresarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PensamientoActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        cargarScoreUsuario();
        buscarPregunta();
        txtIndicaciones.setText(R.string. pensa_strindica_found);
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
        progStatus.setVisibility(View.VISIBLE);
        intentos=1;
        respuesta="";
        txtIndicaciones.setText(R.string. pensa_strindica_found);
        imgReal.setImageResource(0);
        imgOpc1.setImageResource(0);
        imgOpc2.setImageResource(0);
    }

    private void buscarPregunta()
    {
        if (totalJuegos>MAXPREGUNTAS)
        {
            limpiarPreguntas();
            txtIndicaciones.setText(R.string.pensa_strindica_error);
        }
        else {
            //Se busca un juego al azar
            Random rand = new Random();
            int intId = 2;//rand.nextInt(MAXPREGUNTAS);
            //para no empezar en 0
            intId++;
            String tmpPregunta = "Q" + intId;

            //Crga en memoria la coleccion
            CollectionReference logUsuario = bdService.collection("pensamiento_score").document(IdUsuario).collection("logs");

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
                            cargarPregunta(tmpPregunta);
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
    private void  cargarPregunta(String juego)
    {
        limpiarPreguntas();
        idJuego=juego;
        DocumentReference pregunta= bdService.collection("pensamiento_questions").document(juego);

        pregunta.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Establece la escala de la pantalla
                        final float scale = PensamientoActivity.this.getResources().getDisplayMetrics().density;
                        int newHeight=0;

                        txtIndicaciones.setText(Pensamiento_library.getIndicaciones(document.get("indicaciones").toString()));
                        respuesta= document.get("respuesta").toString().toUpperCase();

                        newHeight=(int )(Integer.parseInt(document.get("ImgBaseHeight").toString()));
                        imgReal.setImageResource(Pensamiento_library.getImagen(document.get("ImgBase").toString()) );
                        imgReal.getLayoutParams().height =(newHeight);

                        newHeight=(int )(Integer.parseInt(document.get("ImgOpc1").toString()));
                        //imgOpc1.setImageResource(Pensamiento_library.getImagen(document.get("ImgBase").toString()) );
                        imgOpc1.setImageResource(R.drawable.ic_m67);
                        //imgOpc1.getLayoutParams().height =(newHeight);

                        newHeight=(int )(Integer.parseInt(document.get("ImgOpc2").toString()));
                        //imgOpc2.setImageResource(Pensamiento_library.getImagen(document.get("ImgBase").toString()));
                        imgOpc2.setImageResource(R.drawable.ic_m66);
                        //imgOpc2.getLayoutParams().height =(newHeight);


                        TimerStart();
                        progStatus.setVisibility(View.GONE);
                    }
                    else{
                        Toast.makeText(PensamientoActivity.this,"No such document", Toast.LENGTH_LONG).show();
                    }
                } else {
                    txtIndicaciones.setText("get failed with " + task.getException().toString());
                    Toast.makeText(PensamientoActivity.this, "get failed with " + task.getException(), Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    //endregion

    //Método para validar las repuestas
    public void onImgClick(View v){
        ImageView ImgOpc= (ImageView) v;
        if(v.getTransitionName().trim().toUpperCase().equals(respuesta)){
            Cronometro.stop();
            guardarProgreso();
            actualizarScoreUsuario();
            Toast.makeText(PensamientoActivity.this, "Correcto!", Toast.LENGTH_LONG).show();
            buscarPregunta();
        }
        else {
            Toast.makeText(PensamientoActivity.this, "Intento # "+intentos + " incorrecto!", Toast.LENGTH_SHORT).show();
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
        bdService.collection("pensamiento_score")
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
        DocumentReference pregunta= bdService.collection("pensamiento_score").document(IdUsuario);
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
                progAciertos.setProgress(scoreActual);
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


        bdService.collection("pensamiento_score")
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
}