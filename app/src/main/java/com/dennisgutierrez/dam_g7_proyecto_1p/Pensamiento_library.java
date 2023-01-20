package com.dennisgutierrez.dam_g7_proyecto_1p;

import androidx.annotation.NonNull;


public class Pensamiento_library {
    public static  int getImagen(@NonNull String imgNombre)
    {
        switch (imgNombre.trim().toUpperCase())
        {
            case "PENS01":
                return  R.drawable.ic_m56;
            case "PENS02":
                return  R.drawable.ic_m57;
            case "PENS03":
                return  R.drawable.ic_m58;
            case "PENS04":
                return  R.drawable.ic_m59;
            case "PENS05":
                return  R.drawable.ic_m60;
            case "PENS06":
                return  R.drawable.ic_m61;
            case "PENS07":
                return  R.drawable.ic_m62;
            case "PENS08":
                return  R.drawable.ic_m63;
            case "PENS09":
                return  R.drawable.ic_m64;
            case "PENS10":
                return R.drawable.ic_m65;
            default:
                return R.drawable.ic_pensamiento;
        }
    }

    public static  int getIndicaciones(@NonNull String strNombre)
    {
        switch (strNombre.trim().toUpperCase()) {
            case "PENS01":
                return R.string.pensa_strindica1;
            case "PENS02":
                return R.string.pensa_strindica2;
            case "PENS03":
                return R.string.pensa_strindica3;
            case "PENS04":
                return R.string.pensa_strindica4;
            case "PENS05":
                return R.string.pensa_strindica5;
            case "PENS06":
                return R.string.pensa_strindica6;
            case "PENS07":
                return R.string.pensa_strindica7;
            case "PENS08":
                return R.string.pensa_strindica8;
            case "PENS09":
                return R.string.pensa_strindica9;
            case "PENS10":
                return R.string.pensa_strindica10;
            default:
                return  R.string.pensa_strindica_error;
        }

    }
}
