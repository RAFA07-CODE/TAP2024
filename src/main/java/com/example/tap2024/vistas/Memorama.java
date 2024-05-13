package com.example.tap2024.vistas;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Memorama extends Stage {

    private final int cardWidth = 100;
    private final int cardHeight = 150;
    private final String coverImagePath = "/images/cover.png";
    private final int maxPairs = 15;
    private final int turnTimeLimitSeconds = 30;

    private GridPane grid;
    private ArrayList<ImageView> cards;
    private Set<ImageView> matchedCards;
    private int pairsFound;
    private int currentPlayer;
    private int[] playerPairsFound;
    private PauseTransition turnTimeout;
    private ImageView selectedCard;

    private Label player1ScoreLabel;
    private Label player2ScoreLabel;
    private Label turnLabel;

    public Memorama() {
        createUI();
        this.setTitle("Memorama");
        this.show();
    }

    private void createUI() {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 900, 750);
        this.setScene(scene);

        grid = new GridPane();
        root.setCenter(grid);

        Label pairsLabel = new Label("Número de pares");
        TextField pairsInput = new TextField();
        pairsInput.setPromptText("Ingrese número de pares (entre 3 y " + maxPairs + ")");

        Button startButton = new Button("Comenzar Juego");
        startButton.setOnAction(event -> {
            String pairsText = pairsInput.getText();
            int pairs;
            try {
                pairs = Integer.parseInt(pairsText);
                if (pairs >= 3 && pairs <= maxPairs) {
                    initializeGame(pairs);
                } else {
                    showAlert("El número de pares debe estar entre 3 y " + maxPairs);
                }
            } catch (NumberFormatException e) {
                showAlert("Por favor, ingrese un número válido.");
            }
        });

        BorderPane inputPane = new BorderPane();
        inputPane.setLeft(pairsLabel);
        inputPane.setCenter(pairsInput);
        inputPane.setRight(startButton);
        root.setTop(inputPane);

        player1ScoreLabel = new Label("Jugador 1: 0 Pares");
        player2ScoreLabel = new Label("Jugador 2: 0 Pares");
        turnLabel = new Label("Turno del Jugador 1");

        BorderPane statusPane = new BorderPane();
        statusPane.setLeft(player1ScoreLabel);
        statusPane.setCenter(turnLabel);
        statusPane.setRight(player2ScoreLabel);
        root.setBottom(statusPane);
    }

    private void initializeGame(int pairs) {
        grid.getChildren().clear();
        cards = new ArrayList<>();
        matchedCards = new HashSet<>();
        pairsFound = 0;
        currentPlayer = 1;
        playerPairsFound = new int[2];
        selectedCard = null;

        ArrayList<Integer> imageIndices = new ArrayList<>();
        for (int i = 0; i < pairs; i++) {
            imageIndices.add(i);
            imageIndices.add(i);
        }
        Collections.shuffle(imageIndices);

        int numRows = (pairs * 2) / 4;
        int numCols = (pairs * 2) / numRows;

        for (int i = 0; i < numRows * numCols; i++) {
            Image coverImage = new Image(getClass().getResourceAsStream(coverImagePath));
            ImageView card = createCard(coverImage, imageIndices.get(i));
            cards.add(card);
        }

        Collections.shuffle(cards);

        int index = 0;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                grid.add(cards.get(index), col, row);
                index++;
            }
        }

        startNextTurn();
    }

    private ImageView createCard(Image image, int imageIndex) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(cardWidth);
        imageView.setFitHeight(cardHeight);
        imageView.setOnMouseClicked(event -> {
            if (!matchedCards.contains(imageView) && imageView.getImage().getUrl().equals(coverImagePath)) {
                handleCardClick(imageView, imageIndex);
            }
        });
        return imageView;
    }

    private void handleCardClick(ImageView card, int imageIndex) {
        if (turnTimeout != null) {
            turnTimeout.stop();
        }

        if (selectedCard == null) {
            selectedCard = card;
            flipCard(selectedCard, imageIndex);
        } else if (selectedCard != card) {
            flipCard(card, imageIndex);
            checkForMatch(selectedCard, card);
        }
    }

    private void flipCard(ImageView card, int imageIndex) {
        Image image = new Image(getClass().getResourceAsStream("/images/" + (imageIndex + 1) + ".png"));
        card.setImage(image);
    }

    private void checkForMatch(ImageView card1, ImageView card2) {
        if (card1.getImage().getUrl().equals(card2.getImage().getUrl())) {
            matchedCards.add(card1);
            matchedCards.add(card2);

            pairsFound++;
            playerPairsFound[currentPlayer - 1]++;

            if (pairsFound == cards.size() / 2) {
                String winner = (playerPairsFound[0] > playerPairsFound[1]) ? "Jugador 1" : "Jugador 2";
                showAlert("¡Has encontrado todas las parejas!\n" + winner + " ha ganado.");
            }
        } else {
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                card1.setImage(new Image(getClass().getResourceAsStream(coverImagePath)));
                card2.setImage(new Image(getClass().getResourceAsStream(coverImagePath)));
                currentPlayer = (currentPlayer == 1) ? 2 : 1;
                updateTurnLabel();
            });
            pause.play();
        }

        selectedCard = null;
        startNextTurn();
    }

    private void startNextTurn() {
        turnTimeout = new PauseTransition(Duration.seconds(turnTimeLimitSeconds));
        turnTimeout.setOnFinished(event -> {
            showAlert("¡Se acabó el tiempo! Turno del Jugador " + (currentPlayer == 1 ? 2 : 1));
            currentPlayer = currentPlayer == 1 ? 2 : 1;
            updateTurnLabel();
        });
        turnTimeout.play();
    }

    private void updateTurnLabel() {
        turnLabel.setText("Turno del Jugador " + currentPlayer);
        player1ScoreLabel.setText("Jugador 1: " + playerPairsFound[0] + " Pares");
        player2ScoreLabel.setText("Jugador 2: " + playerPairsFound[1] + " Pares");
    }

    private void showAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.show();
        });
    }
}
