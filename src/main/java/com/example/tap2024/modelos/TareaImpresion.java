package com.example.tap2024.modelos;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TareaImpresion {
    public StringProperty noArchivo;
    public StringProperty nombreArchivo;
    public IntegerProperty numHojas;
    public StringProperty horaAcceso;

    public TareaImpresion(String noArchivo, String nombreArchivo, int numHojas, String horaAcceso) {
        this.noArchivo = new SimpleStringProperty(noArchivo);
        this.nombreArchivo = new SimpleStringProperty(nombreArchivo);
        this.numHojas = new SimpleIntegerProperty(numHojas);
        this.horaAcceso = new SimpleStringProperty(horaAcceso);
    }

    public String getNoArchivo() {
        return noArchivo.get();
    }

    public void setNoArchivo(String noArchivo) {
        this.noArchivo.set(noArchivo);
    }

    public StringProperty noArchivoProperty() {
        return noArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo.get();
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo.set(nombreArchivo);
    }

    public StringProperty nombreArchivoProperty() {
        return nombreArchivo;
    }

    public int getNumHojas() {
        return numHojas.get();
    }

    public void setNumHojas(int numHojas) {
        this.numHojas.set(numHojas);
    }

    public IntegerProperty numHojasProperty() {
        return numHojas;
    }

    public String getHoraAcceso() {
        return horaAcceso.get();
    }

    public void setHoraAcceso(String horaAcceso) {
        this.horaAcceso.set(horaAcceso);
    }

    public StringProperty horaAccesoProperty() {
        return horaAcceso;
    }
}

