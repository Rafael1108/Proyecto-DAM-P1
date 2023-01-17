package com.dennisgutierrez.dam_g7_proyecto_1p;

import android.app.Fragment;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class AprendizajeActivity extends AppCompatActivity {


    ImageButton imb00, imb01, imb02, imb03,
                imb04, imb05, imb06, imb07,
                imb08, imb09, imb10, imb11,
                imb12, imb13, imb14, imb15;
    ImageButton[] tablero = new ImageButton[16];
    Button botonReiniciar;
    TextView textPuntuacion, textAciertos, textErrores;

    int puntuacion;
    int aciertos;
    int errores;

    //imagenes
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

        tablero[0] = imb00;
        tablero[1] = imb01;
        tablero[2] = imb02;
        tablero[3] = imb03;
        tablero[4] = imb04;
        tablero[5] = imb05;
        tablero[6] = imb06;
        tablero[7] = imb07;
        tablero[8] = imb08;
        tablero[9] = imb09;
        tablero[10] = imb10;
        tablero[11] = imb11;
        tablero[12] = imb12;
        tablero[13] = imb13;
        tablero[14] = imb14;
        tablero[15] = imb15;
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
        aciertos = 0;
        errores = 0;
        puntuacion = (aciertos - errores) * 100;
        textPuntuacion.setText("Puntuación: " + puntuacion);
        textAciertos.setText("Aciertos: " + aciertos);
        textErrores.setText("Errores: " + errores );

    }


    private void cargarImagenes(){
        imagenes = new int[]{
                R.drawable.ic_m01,
                R.drawable.ic_m02,
                R.drawable.ic_m03,
                R.drawable.ic_m04,
                R.drawable.ic_m05,
                R.drawable.ic_m06,
                R.drawable.ic_m07,
                R.drawable.ic_m08
        };
        fondo = R.drawable.ic_aprendizaje;
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
                puntuacion = (aciertos - errores) * 100;
                textPuntuacion.setText("Puntuación: " + puntuacion);
                textAciertos.setText("Aciertos: " + aciertos);
                if (aciertos == imagenes.length){
                    Toast toast = Toast.makeText(getApplicationContext(), "¡¡Ganaste!!", Toast.LENGTH_LONG);
                    toast.show();
                    PauseTimer();
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
        TimerStart();
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
    }






}