package com.dennisgutierrez.dam_g7_proyecto_1p;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class AcercaDeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //Método para mostrar el acerca de modulos
    public void onImgClick(View v){
        ImageView ImgOpc= (ImageView) v;

        String NombreModuloSeleccionado= v.getTransitionName().trim().toUpperCase();

        Toast.makeText(AcercaDeActivity.this, getInfoAcercaDe( NombreModuloSeleccionado), Toast.LENGTH_SHORT);
    }

    //funcion que retorna el id del texto a mostrar.
    private int getInfoAcercaDe(String NombreModulo){
        switch (NombreModulo)
        {
            case "M1": return   R.string.acerca_aprendizaje;
            case "M2": return   R.string.acerca_atencion;
            case "M3": return   R.string.acerca_percepcion;
            case "M4": return   R.string.acerca_pensamiento;
            default: return 0;
        }

    }
}