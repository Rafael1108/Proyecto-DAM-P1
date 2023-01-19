package com.dennisgutierrez.dam_g7_proyecto_1p;

import androidx.annotation.NonNull;

public class Percepcion_library {

    public static  int getImagen(@NonNull String imgNombre)
    {
        switch (imgNombre.trim().toUpperCase())
        {
            case "M01":
                return  R.drawable.ic_m01;
            case "M02":
                return  R.drawable.ic_m02;
            case "M03":
                return  R.drawable.ic_m03;
            case "M04":
                return  R.drawable.ic_m04;
            case "M05":
                return  R.drawable.ic_m05;
            case "M06":
                return  R.drawable.ic_m07;
            case "M08":
                return  R.drawable.ic_m08;
            case "M09":
                return  R.drawable.ic_m09;
            case "M10":
                return  R.drawable.ic_m10;
            case "M11":
                return  R.drawable.ic_m11;
            case "M12":
                return  R.drawable.ic_m12;
            case "M13":
                return  R.drawable.ic_m13;
            case "M14":
                return  R.drawable.ic_m14;
            case "M15":
                return  R.drawable.ic_m15;
            case "M16":
                return  R.drawable.ic_m16;
            case "M17":
                return  R.drawable.ic_m17;
            case "M18":
                return  R.drawable.ic_m18;
            case "M19":
                return  R.drawable.ic_m19;
            case "M20":
                return  R.drawable.ic_m20;
            case "M21":
                return  R.drawable.ic_m21;
            case "M22":
                return  R.drawable.ic_m22;
            case "M23":
                return  R.drawable.ic_m23;
            case "M24":
                return  R.drawable.ic_m24;
            case "M25":
                return  R.drawable.ic_m25;
            case "M26":
                return  R.drawable.ic_m26;
            case "M27":
                return R.drawable.ic_m27;
            case "M28":
                return  R.drawable.ic_m28;
            case "M29":
                return  R.drawable.ic_m29;
            case "M30":
                return  R.drawable.ic_m30;
            case "M31":
                return  R.drawable.ic_m31;
            case "M32":
                return  R.drawable.ic_m32;
            case "M33":
                return  R.drawable.ic_m33;
            case "M34":
                return  R.drawable.ic_m34;
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
            default:
                return R.drawable.ic_percepcion;
        }
    }

    public static  int getIndicaciones(@NonNull String strNombre)
    {
        switch (strNombre.trim().toUpperCase()) {
            case "IGUAL":
                return R.string.percep_strindica1;
            case "MENOR":
                return R.string.percep_strindica2;
            case "MAYOR":
                return R.string.percep_strindica3;
            default:
                return  R.string.percep_strindica_error;
        }

    }


}
