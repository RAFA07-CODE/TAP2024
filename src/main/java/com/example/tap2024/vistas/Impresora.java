package com.example.tap2024.vistas;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Impresora extends Stage {

    private TableView<TareaImpresion> tablaTareas;
    private ObservableList<TareaImpresion> listaTareas;
    private Button btnAgregarTarea;
    private Button btnIniciarDetenerSimulacion;
    private ProgressBar barraProgreso;
    private boolean simulacionActivada;


    public Impresora() {
        crearUI();
        this.setTitle("Simulador de Impresión");
        this.setScene(new Scene(new BorderPane(new VBox(new Label("Simulador de Impresión"), tablaTareas, btnAgregarTarea, btnIniciarDetenerSimulacion, barraProgreso)), 400, 400));
        this.show();
    }

    private void crearUI() {
        // Crear tabla y columnas
        tablaTareas = new TableView<>();
        TableColumn<TareaImpresion, Integer> colNoArchivo = new TableColumn<>("No. Archivo");
        colNoArchivo.setCellValueFactory(new PropertyValueFactory<>("noArchivo"));
        TableColumn<TareaImpresion, String> colNombreArchivo = new TableColumn<>("Nombre de Archivo");
        colNombreArchivo.setCellValueFactory(new PropertyValueFactory<>("nombreArchivo"));
        TableColumn<TareaImpresion, Integer> colNumHojas = new TableColumn<>("No. de Hojas");
        colNumHojas.setCellValueFactory(new PropertyValueFactory<>("numHojas"));
        TableColumn<TareaImpresion, String> colHoraAcceso = new TableColumn<>("Hora de Acceso");
        colHoraAcceso.setCellValueFactory(new PropertyValueFactory<>("horaAcceso"));
        tablaTareas.getColumns().addAll(colNoArchivo, colNombreArchivo, colNumHojas, colHoraAcceso);
        listaTareas = FXCollections.observableArrayList();
        tablaTareas.setItems(listaTareas);

        // Botón para agregar tarea
        btnAgregarTarea = new Button("Agregar Tarea");
        btnAgregarTarea.setOnAction(e -> agregarTarea());

        // Botón para iniciar/detener simulación
        btnIniciarDetenerSimulacion = new Button("Iniciar Simulación");
        btnIniciarDetenerSimulacion.setOnAction(e -> toggleSimulacion());

        // Barra de progreso
        barraProgreso = new ProgressBar();
    }

    private void agregarTarea() {
        Random rand = new Random();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        LocalDateTime now = LocalDateTime.now();

        String nombreArchivo = "Archivo_" + now.format(formatter) + ".txt";
        int numHojas = rand.nextInt(20) + 1; // Número de hojas aleatorio entre 1 y 20
        String horaAcceso = now.format(formatter);

        TareaImpresion tarea = new TareaImpresion((listaTareas.size() + 1) + "", nombreArchivo, numHojas, horaAcceso);
        listaTareas.add(tarea);
    }


    private void toggleSimulacion() {
        simulacionActivada = !simulacionActivada;
        if (simulacionActivada) {
            btnIniciarDetenerSimulacion.setText("Detener Simulación");
            // Iniciar hilo de simulación
            iniciarSimulacion();
        } else {
            btnIniciarDetenerSimulacion.setText("Iniciar Simulación");
            // Detener hilo de simulación
        }
    }

    private void iniciarSimulacion() {
        Thread simulacionThread = new Thread(() -> {
            while (simulacionActivada) {
                if (!listaTareas.isEmpty()) {
                    TareaImpresion tarea = listaTareas.get(0);
                    Platform.runLater(() -> barraProgreso.setProgress(0));
                    for (int i = 0; i < tarea.getNumHojas(); i++) {
                        try {
                            Thread.sleep(100); // Simular impresión de una hoja
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        final double progreso = (i + 1.0) / tarea.getNumHojas();
                        Platform.runLater(() -> barraProgreso.setProgress(progreso));
                    }
                    Platform.runLater(() -> listaTareas.remove(tarea));
                } else {
                    try {
                        Thread.sleep(1000); // Esperar antes de revisar de nuevo la lista de tareas
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        simulacionThread.start();
    }
}

class TareaImpresion {
    private final StringProperty noArchivo;
    private final StringProperty nombreArchivo;
    private final IntegerProperty numHojas;
    private final StringProperty horaAcceso;

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
