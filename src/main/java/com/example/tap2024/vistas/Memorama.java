package com.example.tap2024.vistas;

import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Memorama extends Stage {

    private Scene escena;
    private Label lblPares, lblJugador1, lblJugador2, lblTiempo, lblScore1, lblScore2;
    private TextField txtPares;
    private GridPane gdpJuego;
    private Button btnJugar;
    private VBox vbxPrincipal, vbxJugadores;
    private HBox hbxTiempo, hbxJuego, hbxJugador1, hbxJugador2;

    public Memorama(){
        CrearUI();
        this.setTitle("Memorama :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        lblPares = new Label("Número de Pares:");
        btnJugar = new Button("Inicar Juego");
        lblTiempo = new Label("00:00");
        hbxTiempo = new HBox(lblPares, btnJugar, lblTiempo);

        lblJugador1 = new Label("Jugador 1");
        lblScore1 = new Label("0");
        hbxJugador1 = new HBox(lblJugador1, lblScore1);

        lblJugador2 = new Label("Jugador 2");
        lblScore2 = new Label("0");
        hbxJugador2 = new HBox(lblJugador2, lblScore2);

        vbxJugadores = new VBox(hbxJugador1, hbxJugador2);
        gdpJuego = new GridPane();
        RevolverCartas();
        hbxJuego = new HBox(gdpJuego,vbxJugadores);

        vbxPrincipal = new VBox(hbxTiempo, hbxJuego);

        escena = new Scene(vbxPrincipal, 400, 300);
    }

    private void RevolverCartas() {

        String[] arImagenes = {"fantasma.png","hongo.png","mario-bros.png","personajes.png"};
        Button[][] arBtnCartas = new Button[2][4];

        ImageView imvCarta;
        int posx = 0;
        int posy = 0;
        int cont = 0;
        for (int i = 0; i < arImagenes.length ; ) {
            posx = (int)(Math.random()*2);
            posy = (int)(Math.random()*4);
            if( arBtnCartas[posx][posy] == null ){
                arBtnCartas[posx][posy] = new Button();
                imvCarta = new ImageView(
                        getClass().getResource("/images/"+arImagenes[i]).toString()
                );
                imvCarta.setFitHeight(150);
                imvCarta.setFitWidth(100);
                arBtnCartas[posx][posy].setGraphic(imvCarta);
                arBtnCartas[posx][posy].setPrefSize(100,150);
                gdpJuego.add(arBtnCartas[posx][posy],posy,posx);
                cont++;
                if( cont == 2 ) {
                    i++;
                    cont = 0;
                }
            }
        }
    }
}
