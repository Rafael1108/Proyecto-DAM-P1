package com.dennisgutierrez.dam_g7_proyecto_1p;

import androidx.annotation.NonNull;

import kotlin.contracts.Returns;

public class Pensamiento_library {
    public static  int getImagen(@NonNull String imgNombre)
    {
        switch (imgNombre.trim().toUpperCase())
        {
            case "M01":
                return  R.drawable.ic_m56;
            case "M02":
                return  R.drawable.ic_m57;
            case "M03":
                return  R.drawable.ic_m58;
            case "M04":
                return  R.drawable.ic_m59;
            case "M05":
                return  R.drawable.ic_m60;
            case "M06":
                return  R.drawable.ic_m61;
            case "M08":
                return  R.drawable.ic_m62;
            case "M09":
                return  R.drawable.ic_m63;
            case "M10":
                return  R.drawable.ic_m64;
            case "M11":
                return R.drawable.ic_m65;
            default:
                return R.drawable.ic_pensamiento;
        }
    }

    public static  int getIndicaciones(@NonNull String strNombre)
    {
        switch (strNombre.trim().toUpperCase()) {
            case "P1":
                return R.string.pensa_strindica1;
            case "P2":
                return R.string.pensa_strindica2;
            case "P3":
                return R.string.pensa_strindica3;
            case "P4":
                return R.string.pensa_strindica4;
            case "P5":
                return R.string.pensa_strindica5;
            case "P6":
                return R.string.pensa_strindica6;
            case "P7":
                return R.string.pensa_strindica7;
            case "P8":
                return R.string.pensa_strindica8;
            case "P9":
                return R.string.pensa_strindica9;
            case "P10":
                return R.string.pensa_strindica10;
            default:
                return  R.string.pensa_strindica_error;
        }

    }
}
