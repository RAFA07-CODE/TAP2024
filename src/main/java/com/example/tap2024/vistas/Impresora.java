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
        BorderPane root = new BorderPane();
        VBox vbox = new VBox(new Label("Simulador de Impresión"), tablaTareas, btnAgregarTarea, btnIniciarDetenerSimulacion, barraProgreso);
        root.setCenter(vbox);

        this.setScene(new Scene(root, 600, 400));
        this.show();
    }

    private void crearUI() {
        tablaTareas = new TableView<>();
        TableColumn<TareaImpresion, Number> colNoArchivo = new TableColumn<>("No. Archivo");
        colNoArchivo.setCellValueFactory(new PropertyValueFactory<>("noArchivo"));
        TableColumn<TareaImpresion, String> colNombreArchivo = new TableColumn<>("Nombre de Archivo");
        colNombreArchivo.setCellValueFactory(new PropertyValueFactory<>("nombreArchivo"));
        TableColumn<TareaImpresion, Number> colNumHojas = new TableColumn<>("No. de Hojas");
        colNumHojas.setCellValueFactory(new PropertyValueFactory<>("numHojas"));
        TableColumn<TareaImpresion, LocalDateTime> colHoraAcceso = new TableColumn<>("Hora de Acceso");
        colHoraAcceso.setCellValueFactory(new PropertyValueFactory<>("horaAcceso"));
        tablaTareas.getColumns().addAll(colNoArchivo, colNombreArchivo, colNumHojas, colHoraAcceso);
        listaTareas = FXCollections.observableArrayList();
        tablaTareas.setItems(listaTareas);

        btnAgregarTarea = new Button("Agregar Tarea");
        btnAgregarTarea.setOnAction(e -> agregarTarea());

        btnIniciarDetenerSimulacion = new Button("Iniciar Simulación");
        btnIniciarDetenerSimulacion.setOnAction(e -> toggleSimulacion());

        barraProgreso = new ProgressBar(0);
    }

    private void agregarTarea() {
        Random rand = new Random();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        LocalDateTime now = LocalDateTime.now();

        String nombreArchivo = "Archivo_" + now.format(formatter) + ".txt";
        int numHojas = rand.nextInt(20) + 1;

        TareaImpresion tarea = new TareaImpresion(listaTareas.size() + 1, nombreArchivo, numHojas, now);
        listaTareas.add(tarea);
    }

    private void toggleSimulacion() {
        simulacionActivada = !simulacionActivada;
        if (simulacionActivada) {
            btnIniciarDetenerSimulacion.setText("Detener Simulación");
            iniciarSimulacion();
        } else {
            btnIniciarDetenerSimulacion.setText("Iniciar Simulación");
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
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        final double progreso = (i + 1.0) / tarea.getNumHojas();
                        Platform.runLater(() -> barraProgreso.setProgress(progreso));
                    }
                    Platform.runLater(() -> listaTareas.remove(tarea));
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        simulacionThread.setDaemon(true);
        simulacionThread.start();
    }
}
