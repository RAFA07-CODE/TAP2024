package com.example.tap2024.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Calculadora extends Stage {
    private TextField txtPantalla;
    private Button[][] arBotones = new Button[4][4];
    private char[] arSignos = {'7', '8', '9', '/', '4', '5', '6', '*', '1', '2', '3', '-', '0', '.', '=', '+'};
    private double numero1 = 0;
    private char operador = ' ';
    private boolean ultimoSigno = false;
    private boolean esperandoSegundoNumero = false;

    public Calculadora() {
        inicializarComponentes();
        CrearUI();
        this.setTitle("Mi primer Calculadora :)");
        this.show();
    }

    private void inicializarComponentes() {
        txtPantalla = new TextField("0");
        txtPantalla.setEditable(false);
    }

    private void CrearUI() {
        GridPane gdpTeclado = new GridPane();
        CrearTeclado(gdpTeclado);

        Button btnLimpiar = new Button("Limpiar");
        btnLimpiar.setOnAction(event -> {
            txtPantalla.setText("0");
            operador = ' ';
            numero1 = 0;
            ultimoSigno = false;
            esperandoSegundoNumero = false;
        });
        btnLimpiar.setId("btnLimpiar");

        VBox vContenedor = new VBox(txtPantalla, gdpTeclado, btnLimpiar);
        vContenedor.setSpacing(5);
        Scene escena = new Scene(vContenedor, 200, 270);
        escena.getStylesheets().add(getClass().getResource("/estilos/calculadora.css").toString());
        this.setScene(escena);
    }

    private void CrearTeclado(GridPane gdpTeclado) {
        int pos = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Button btn = new Button(String.valueOf(arSignos[pos]));
                btn.setPrefSize(50, 50);
                int finalPos = pos;
                btn.setOnAction(event -> {
                    char signo = arSignos[finalPos];
                    if (Character.isDigit(signo) || signo == '.') {
                        if (ultimoSigno || esperandoSegundoNumero) {
                            txtPantalla.setText("");
                            ultimoSigno = false;
                            esperandoSegundoNumero = false;
                        }
                        if (txtPantalla.getText().equals("0")) {
                            txtPantalla.setText("");
                        }
                        txtPantalla.appendText(String.valueOf(signo));
                    } else if (signo == '=') {
                        if (operador == ' ' || esperandoSegundoNumero) return;
                        calcular();
                    } else {
                        if (!ultimoSigno) {
                            operador = signo;
                            ultimoSigno = true;
                            numero1 = Double.parseDouble(txtPantalla.getText());
                            esperandoSegundoNumero = true;
                        }
                    }
                });

                if (arSignos[pos] == '+' || arSignos[pos] == '-' || arSignos[pos] == '*' || arSignos[pos] == '/')
                    btn.setId("color-operador");
                if (arSignos[pos] == '1' || arSignos[pos] == '2' || arSignos[pos] == '3' || arSignos[pos] == '4' || arSignos[pos] == '5' || arSignos[pos] == '6' || arSignos[pos] == '7' || arSignos[pos] == '8' || arSignos[pos] == '9' || arSignos[pos] == '0')
                    btn.setId("color-numeros");
                if (arSignos[pos] == '.' || arSignos[pos] == '=')
                    btn.setId("color-signos");

                arBotones[i][j] = btn;
                gdpTeclado.add(btn, j, i);
                pos++;
            }
        }
    }

    private void calcular() {
        String textoPantalla = txtPantalla.getText();
        if (textoPantalla.equals("Syntax Error")) {
            return;
        }
        if (operador == '/' && textoPantalla.equals(".")) {
            txtPantalla.setText("Syntax Error");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                txtPantalla.setText("0");
                operador = ' ';
                numero1 = 0;
                ultimoSigno = false;
                esperandoSegundoNumero = false;
            }));
            timeline.play();
            return;
        }
        double numero2 = Double.parseDouble(textoPantalla);
        if (operador == '/' && numero2 == 0) {
            txtPantalla.setText("Syntax Error");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                txtPantalla.setText("0");
                operador = ' ';
                numero1 = 0;
                ultimoSigno = false;
                esperandoSegundoNumero = false;
            }));
            timeline.play();
            return;
        }
        double resultado = 0;
        switch (operador) {
            case '+':
                resultado = numero1 + numero2;
                break;
            case '-':
                resultado = numero1 - numero2;
                break;
            case '*':
                resultado = numero1 * numero2;
                break;
            case '/':
                resultado = numero1 / numero2;
                break;
        }
        txtPantalla.setText(String.valueOf(resultado));
        operador = ' ';
        numero1 = resultado;
        ultimoSigno = true;
        esperandoSegundoNumero = true;
    }
}
