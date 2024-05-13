package com.example.tap2024.vistas;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;

public class CuadroMagico extends Stage {

    private Scene escena;
    private GridPane gridPane;

    public CuadroMagico() {
        this.setTitle("Cuadro Mágico");
        gridPane = new GridPane();
        CrearUI();
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER);

        TextField textField = new TextField();
        textField.setPromptText("Ingrese un número");
        textField.getStyleClass().add("text-ingresar");

        Button botonGenerar = new Button("Generar");
        botonGenerar.setOnAction(event -> generarCuadroMagico(Integer.parseInt(textField.getText())));
        botonGenerar.getStyleClass().add("btn-generar");

        HBox contenedorGenerar = new HBox(10, textField, botonGenerar);
        contenedorGenerar.setAlignment(Pos.CENTER);

        VBox root = new VBox(10, contenedorGenerar, gridPane);
        root.setAlignment(Pos.CENTER);

        escena = new Scene(root, 500, 500);
        escena.getStylesheets().add(getClass().getResource("/estilos/cuadroMagico.css").toString());
    }

    private void generarCuadroMagico(int numero) {
        gridPane.getChildren().clear();

        if (numero < 3 || numero % 2 == 0) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, ingrese un número impar mayor o igual a 3.");
            alerta.showAndWait();
            return;
        }

        String filePath = "cuadro_magico.dat";

        try (RandomAccessFile file = new RandomAccessFile(filePath, "rw")) {
            file.setLength(0);

            int row = 0;
            int col = numero / 2;

            for (int i = 1; i <= numero * numero; i++) {
                file.writeInt(i);

                int newRow = (row - 1 + numero) % numero;
                int newCol = (col + 1) % numero;

                if (file.length() > 4 * (newRow * numero + newCol)) {
                    row = (row + 1) % numero;
                } else {
                    row = newRow;
                    col = newCol;
                }
            }

            file.seek(0);
            for (int i = 0; i < numero; i++) {
                for (int j = 0; j < numero; j++) {
                    int value = file.readInt();
                    Button boton = new Button(Integer.toString(value));
                    boton.getStyleClass().add("btn-cuadro");
                    boton.setMinSize(50, 50);
                    gridPane.add(boton, j, i);
                }
            }
            System.out.println("Cuadro mágico generado y guardado en cuadro_magico.dat");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
