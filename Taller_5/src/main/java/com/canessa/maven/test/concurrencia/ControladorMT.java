package com.canessa.maven.test.concurrencia;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ControladorMT {

    private static ControladorMT instance;
    // Variables Globales
    private String marcaTemporalEscritura;
    private String marcaTemporalLectura;
    private DateTimeFormatter formato;

    // Singlenton
    public static ControladorMT getControladorMT() {
        if (instance == null) {
            instance = new ControladorMT();
            System.out.println("Creado y cargado");
        } else {
            System.out.println("Existe y cargado");
        }

        return instance;
    }

    // Constructor
    private ControladorMT() {
        marcaTemporalLectura = "";
        marcaTemporalEscritura = "";
        formato = DateTimeFormatter.ofPattern("uuuu_MM_dd_HH_mm_ss");
    }

    // Metodos Utiles
    public String getTimeStamp() {
        LocalDateTime dateTime = LocalDateTime.now();

        return formato.format(dateTime);
    }

    private int verifyTimeStamp(String timeStampGlobal, String timeStampLocal) {
        LocalDateTime fechaGlobal = LocalDateTime.parse(timeStampGlobal, formato);
        LocalDateTime fechaLocal = LocalDateTime.parse(timeStampLocal, formato);

        return fechaLocal.compareTo(fechaGlobal);
    }

    // Metodos
    public boolean verifyLectura(String timeStampLocal) {
        boolean permite = false;
        if (verifyTimeStamp(marcaTemporalLectura, timeStampLocal) >= 0) {
            permite = true;
            marcaTemporalLectura = timeStampLocal;
        }
        return permite;
    }

    public boolean verifyEscritura(String timeStampLocal) {
        boolean permite = false;
        if(verifyTimeStamp(marcaTemporalLectura, timeStampLocal) >= 0 && verifyTimeStamp(marcaTemporalEscritura, timeStampLocal) >= 0){
            permite = true;
            marcaTemporalEscritura = timeStampLocal;
        }
        return permite;
    }

    private boolean reglaThomas(String timeStampLocal) {
        boolean permite = false;

        return permite;
    }

}
