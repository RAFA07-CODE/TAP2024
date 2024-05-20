package com.example.tap2024.vistas;

import java.time.LocalDateTime;

public class TareaImpresion {
    private int noArchivo;
    private String nombreArchivo;
    private int numHojas;
    private LocalDateTime horaAcceso;

    public TareaImpresion(int noArchivo, String nombreArchivo, int numHojas, LocalDateTime horaAcceso) {
        this.noArchivo = noArchivo;
        this.nombreArchivo = nombreArchivo;
        this.numHojas = numHojas;
        this.horaAcceso = horaAcceso;
    }

    public int getNoArchivo() {
        return noArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public int getNumHojas() {
        return numHojas;
    }

    public LocalDateTime getHoraAcceso() {
        return horaAcceso;
    }
}
