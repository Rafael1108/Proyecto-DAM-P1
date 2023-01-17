package com.dennisgutierrez.dam_g7_proyecto_1p;
import androidx.annotation.NonNull;


public class atencion_library {
    public static  int getImagen(@NonNull String imgNombre)
    {
        switch (imgNombre.trim().toUpperCase())
        {
            case "M39":
                return  R.drawable.ic_m39;
            case "M40":
                return  R.drawable.ic_m40;
            case "M41":
                return  R.drawable.ic_m41;
            case "M42":
                return  R.drawable.ic_m42;
            default:
                return R.drawable.ic_uglogo;
        }
    }

    public static  int getIndicaciones(@NonNull String strNombre)
    {
        switch (strNombre.trim().toUpperCase()) {
            case "IGUAL":
                return R.string.atencion_strindica1;
            case "MENOR":
                return R.string.atencion_strindica2;
            case "MAYOR":
                return R.string.atencion_strindica3;
            default:
                return  R.string.percep_strindica_error;
        }

    }

}
