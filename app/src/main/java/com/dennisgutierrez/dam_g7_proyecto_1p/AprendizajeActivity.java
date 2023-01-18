package com.dennisgutierrez.dam_g7_proyecto_1p;

import static android.content.ContentValues.TAG;

import android.app.Fragment;
import android.content.Intent;
import android.icu.util.Calendar;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dennisgutierrez.dam_g7_proyecto_1p.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AprendizajeActivity extends AppCompatActivity {


    ImageButton imb00, imb01, imb02, imb03,
                imb04, imb05, imb06, imb07,
                imb08, imb09, imb10, imb11,
                imb12, imb13, imb14, imb15,
                imb16, imb17, imb18, imb19;
    ImageButton[] allTablero;
    ImageButton[] tablero;
    Button botonReiniciar;
    TextView txtNivel, textPuntuacion, textAciertos, textErrores;

    //CONTROLES DEL JUEGO
    int nivel = 1;
    int cantImagenes = 6;
    int cantTablero = 12;
    int puntuacion, aciertos, errores, intentos;

    //CONTROLES GLOBALES DE LOS AVANCES
    String totalTiempo;
    int totalJuegos, totalScore;
    //imagenes
    int[] allImagenes;
    int[] imagenes;
    int fondo;

    //variables del juego
    ArrayList<Integer> arrayDesordenado;
    ImageButton primero;
    int numeroPrimero, numeroSegundo;
    boolean bloqueo = false;
    final Handler handler = new Handler();

    private FirebaseFirestore bdService;
    private FirebaseStorage stService  ;
    private String IdUsuario;

    //region variables para Timer
    private Chronometer Cronometro;
    private long pauseOffset;
    private boolean running;

    //endregion
    Button aprendizajeRegresarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprendizaje);

        //Instancia la base de Firestore
        bdService= FirebaseFirestore.getInstance();
        stService = FirebaseStorage.getInstance();

        //Configuracion de la vista
        this.setTitle("Aprendizaje");

        //Se optiene valores de usuario para la session del activity
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            IdUsuario=user.getUid();
        }

        //Instancia TimerLapse
        Cronometro= (Chronometer) findViewById(R.id.Cronometro);
        Cronometro.setFormat("Tiempo: %s");


        aprendizajeRegresarButton = findViewById(R.id.aprendizajeRegresarButton);

        aprendizajeRegresarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AprendizajeActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        init();
    }
    //region TimerStart()
    private  void TimerStart()
    {
        if (!running){
            Cronometro.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            Cronometro.start();
            running = true;
        }


    }

    private void PauseTimer(){
        if (running){
            Cronometro.stop();
            pauseOffset = SystemClock.elapsedRealtime() - Cronometro.getBase();
            running = false;
        }
    }

    private void ResetTimer(){
        Cronometro.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }
    //endregion
    private void cargarTablero(){
        imb00 = findViewById(R.id.boton00);
        imb01 = findViewById(R.id.boton01);
        imb02 = findViewById(R.id.boton02);
        imb03 = findViewById(R.id.boton03);
        imb04 = findViewById(R.id.boton04);
        imb05 = findViewById(R.id.boton05);
        imb06 = findViewById(R.id.boton06);
        imb07 = findViewById(R.id.boton07);
        imb08 = findViewById(R.id.boton08);
        imb09 = findViewById(R.id.boton09);
        imb10 = findViewById(R.id.boton10);
        imb11 = findViewById(R.id.boton11);
        imb12 = findViewById(R.id.boton12);
        imb13 = findViewById(R.id.boton13);
        imb14 = findViewById(R.id.boton14);
        imb15 = findViewById(R.id.boton15);
        imb16 = findViewById(R.id.boton16);
        imb17 = findViewById(R.id.boton17);
        imb18 = findViewById(R.id.boton18);
        imb19 = findViewById(R.id.boton19);

        imb00.setVisibility(View.GONE);
        imb01.setVisibility(View.GONE);
        imb02.setVisibility(View.GONE);
        imb03.setVisibility(View.GONE);
        imb04.setVisibility(View.GONE);
        imb05.setVisibility(View.GONE);
        imb06.setVisibility(View.GONE);
        imb07.setVisibility(View.GONE);
        imb08.setVisibility(View.GONE);
        imb09.setVisibility(View.GONE);
        imb10.setVisibility(View.GONE);
        imb11.setVisibility(View.GONE);
        imb12.setVisibility(View.GONE);
        imb13.setVisibility(View.GONE);
        imb14.setVisibility(View.GONE);
        imb15.setVisibility(View.GONE);
        imb16.setVisibility(View.GONE);
        imb17.setVisibility(View.GONE);
        imb18.setVisibility(View.GONE);
        imb19.setVisibility(View.GONE);

        allTablero = new ImageButton[]{
                imb00,imb01,imb02,imb03,imb04,
                imb05,imb06,imb07,imb08,imb09,
                imb10,imb11,imb12,imb13,imb14,
                imb15,imb16,imb17,imb18,imb19
        };
        tablero = new ImageButton[cantTablero];
        for (int i = 0; i < cantTablero; i++) {
            tablero[i] = allTablero[i];
            tablero[i].setVisibility(View.VISIBLE);
        }


    }

    private void cargarBotones(){
        botonReiniciar= findViewById(R.id.botonJuegoReiniciar);
        botonReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });
    }

    private void cargarTexto(){
        textPuntuacion = findViewById(R.id.txtPuntuacion);
        textAciertos = findViewById(R.id.txtAciertos);
        textErrores = findViewById(R.id.txtErrores);
        txtNivel = findViewById(R.id.txtNivel);
        aciertos = 0;
        errores = 0;
        puntuacion = (aciertos - errores) * 100;
        txtNivel.setText("NIVEL " + nivel);
        textPuntuacion.setText("Puntuación: " + puntuacion);
        textAciertos.setText("Aciertos: " + aciertos);
        textErrores.setText("Errores: " + errores );

    }


    private void cargarImagenes(){
        allImagenes = new int[]{
                R.drawable.ic_m17,
                R.drawable.ic_m34,
                R.drawable.ic_m36,
                R.drawable.ic_m35,
                R.drawable.ic_m07,
                R.drawable.ic_m05,
                R.drawable.ic_m01,
                R.drawable.ic_m08,
                R.drawable.ic_m04,
                R.drawable.ic_m06
        };

        imagenes = new int[cantImagenes];

        for (int i = 0; i < cantImagenes; i++) {
            imagenes[i] = allImagenes[i];
        }
        fondo = R.drawable.mentalgame_icono;
    }

    private ArrayList<Integer> barajar(int longitud){
        ArrayList<Integer> result = new ArrayList<>();
        for (int i=0; i<longitud*2;i++){
            result.add(i%longitud);
        }
        Collections.shuffle(result);
        return result;
    }

    private void comprobar (int i, final ImageButton imgb){
        if (primero == null){
            primero = imgb;
            primero.setScaleType(ImageView.ScaleType.CENTER_CROP);
            primero.setImageResource(imagenes[arrayDesordenado.get(i)]);
            primero.setEnabled(false);
            numeroPrimero = arrayDesordenado.get(i);
        } else {
            bloqueo = true;
            imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgb.setImageResource(imagenes[arrayDesordenado.get(i)]);
            imgb.setEnabled(false);
            numeroSegundo = arrayDesordenado.get(i);
            if (numeroPrimero == numeroSegundo) {
                primero = null;
                bloqueo = false;
                aciertos++;
                intentos++;
                puntuacion = (aciertos - errores) * 100;

                textPuntuacion.setText("Puntuación: " + puntuacion);
                textAciertos.setText("Aciertos: " + aciertos);
                if (aciertos == imagenes.length){
                    Toast toast = Toast.makeText(getApplicationContext(), "¡¡Ganaste!!", Toast.LENGTH_LONG);
                    toast.show();
                    PauseTimer();
                    guardarProgreso();
                    actualizarScoreUsuario();
                    if (nivel < 5){
                        nivel++;
                        cantImagenes++;
                        cantTablero += 2;
                        init();
                    } else {
                        Toast toastFinish = Toast.makeText(getApplicationContext(), "¡Módulo de Aprendizaje culminado con éxito!", Toast.LENGTH_LONG);
                        toastFinish.show();
                        Intent intent = new Intent(AprendizajeActivity.this, UserActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

            } else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        primero.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        primero.setImageResource(fondo);
                        primero.setEnabled(true);
                        imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imgb.setImageResource(fondo);
                        imgb.setEnabled(true);
                        bloqueo = false;
                        primero = null;
                        errores++;
                        intentos++;
                        puntuacion = (aciertos - errores) * 100;

                        textPuntuacion.setText("Puntuación: " + puntuacion);
                        textAciertos.setText("Aciertos: " + aciertos);
                        textErrores.setText("Errores: " + errores );

                    }
                },2000);
            }

        }
    }

    private void init(){
        cargarTablero();
        cargarBotones();
        cargarTexto();
        cargarImagenes();
        ResetTimer();
        cargarScoreUsuario();
        arrayDesordenado = barajar(imagenes.length);
        for (int i = 0; i < tablero.length; i++) {
            tablero[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
            tablero[i].setImageResource(imagenes[arrayDesordenado.get(i)]);

        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < tablero.length; i++) {
                    tablero[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
                    tablero[i].setImageResource(fondo);
                }
            }
        }, 4000);
        for (int i = 0; i < tablero.length; i++) {
            final int j = i;
            tablero[i].setEnabled(true);
            tablero[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!bloqueo)
                        comprobar(j,tablero[j]);
                }
            });
        }
        TimerStart();
    }

    private static String formatTimeStamp(long s){
        if (s < 10) return "0" + s;
        else return "" + s;
    }

    //=====================================================================
    //region Métodos CRUD en firebase

    private void guardarProgreso(){
        String tmp = Cronometro.getText().toString().replace("Tiempo: ","00:");
        Map<String, Object> progreso= new HashMap<>();
        progreso.put("intentos",intentos);
        progreso.put("tiempo",tmp);
        progreso.put("idjuego",nivel);

        //====================
        progreso.put("score", puntuacion);
        progreso.put("aciertos", aciertos);
        progreso.put("errores", errores);

        //almacena el log progreso
        bdService.collection("aprendizaje_score")
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
        DocumentReference historial= bdService.collection("aprendizaje_score").document(IdUsuario);
        historial.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot document,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (document != null && document.exists()) {
                    totalTiempo= document.get("total_tiempo").toString();
                    totalJuegos= Integer.parseInt( document.get("total_juegos").toString() );
                    totalScore= Integer.parseInt( document.get("total_score").toString() );

                } else {
                    totalTiempo= "00:00:00";
                    totalJuegos=0;
                    totalScore= 0;
                }

            }
        });

    }
    private void actualizarScoreUsuario(){

        long tm = 0;
        String tmp = Cronometro.getText().toString().replace("Tiempo: ","00:");
        ArrayList<String> timestampsList = new ArrayList<String>();
        timestampsList.add(totalTiempo);
        timestampsList.add(tmp);
        for (String time : timestampsList) {
            String[] arr = time.split(":");
            tm += Integer.parseInt(arr[2]);
            tm += 60 * Integer.parseInt(arr[1]);
            tm += 3600 * Integer.parseInt(arr[0]);
        }

        long hh = tm / 3600;
        tm %= 3600;
        long mm = tm / 60;
        tm %= 60;
        long ss = tm;


        totalTiempo = formatTimeStamp(hh) + ":" + formatTimeStamp(mm) + ":" + formatTimeStamp(ss);

        totalJuegos++;
        totalScore +=puntuacion;
        Map<String, Object> score= new HashMap<>();
        score.put("total_tiempo", totalTiempo);
        score.put("total_juegos", totalJuegos );
        score.put("total_score", totalScore);

        bdService.collection("aprendizaje_score")
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
    //=====================================================================



}