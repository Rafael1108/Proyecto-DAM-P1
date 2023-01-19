package com.dennisgutierrez.dam_g7_proyecto_1p;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

        String NombreModuloSeleccionado= v.getTransitionName().trim();
        mostrarDialogConfirm(NombreModuloSeleccionado, getInfoAcercaDe( NombreModuloSeleccionado));
    }

    //funcion que retorna el id del texto a mostrar.
    private int getInfoAcercaDe(String NombreModulo){
        switch (NombreModulo)
        {
            case "Aprendizaje": return   R.string.acerca_aprendizaje;
            case "Atencion": return   R.string.acerca_atencion;
            case "Percepcion": return   R.string.acerca_percepcion;
            case "Pensamiento": return  R.string.acerca_pensamiento;
            default: return 0;
        }

    }

    private void mostrarDialogConfirm(String title, int msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(AcercaDeActivity.this);
        builder.setTitle(title);
        builder.setMessage(msg)
                .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();

                    }
                })
                .setCancelable(false)
                .show();
    }
}