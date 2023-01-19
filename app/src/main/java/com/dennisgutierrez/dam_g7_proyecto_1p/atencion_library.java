package com.dennisgutierrez.dam_g7_proyecto_1p;
import androidx.annotation.NonNull;


public class atencion_library {
    public static  int getImagen(@NonNull String imgNombre)
    {
        switch (imgNombre.trim().toUpperCase())
        {

            case "M35":
                return  R.drawable.ic_m35;
            case "M36":
                return  R.drawable.ic_m36;
            case "M37":
                return  R.drawable.ic_m37;
            case "M38":
                return  R.drawable.ic_m38;
            case "M39":
                return  R.drawable.ic_m39;
            case "M40":
                return  R.drawable.ic_m40;
            case "M41":
                return  R.drawable.ic_m41;
            case "M42":
                return  R.drawable.ic_m42;
            case "M43":
                return  R.drawable.ic_m43;
            case "M44":
                return  R.drawable.ic_m44;
            case "M45":
                return  R.drawable.ic_m45;
            case "M46":
                return  R.drawable.ic_m46;
            case "M47":
                return  R.drawable.ic_m47;
            case "M48":
                return  R.drawable.ic_m48;
            case "M49":
                return  R.drawable.ic_m49;
            case "M50":
                return  R.drawable.ic_m50;
            case "M51":
                return  R.drawable.ic_m51;
            case "M52":
                return  R.drawable.ic_m52;
            case "M53":
                return  R.drawable.ic_m53;
            case "M54":
                return  R.drawable.ic_m54;
            case "M55":
                return  R.drawable.ic_m55;
            default:
                return R.drawable.ic_uglogo;
        }
    }

    public static  int getIndicaciones(@NonNull String strNombre)
    {
        switch (strNombre.trim().toUpperCase()) {
            case "IGUAL":
                return R.string.atencion_strindica1;
            case "LADOS":
                return R.string.atencion_strindica2;
            case "MAYOR":
                return R.string.atencion_strindica3;
            default:
                return  R.string.percep_strindica_error;
        }

    }

}
