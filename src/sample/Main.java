package sample;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Optional;


public class Main extends Application {

    public int rand;
    public Label randResult;
    public Label hasLandedText;
    public Label diceRollDesc;
    public Label clickButton;
    public Label playerTurnLabel;
    public Label LadderandSnakePositionLabel;

    Stage window;
    Scene introScreen, gameScene;
    public int[][] grid = new int[10][10];

    // setting tile size
    public static final int tileSize = 80;
    public static final int width = 10;
    public static final int height = 10;

    // piece circle
    public Circle playerOne;
    public Circle playerTwo;


    // checks position of player (number)
    public int positionOne = 1;
    public int positionTwo = 1;

    // checks player 1 or 2 turn
    public boolean playerOneTurn = true;
    public boolean playerTwoTurn = true;

    // coordinate position of player 1
    public static int positionOneX = 40;
    public static int positionOneY = 760;

    // coordinate position of player 2
    public static int positionTwoX = 40;
    public static int positionTwoY = 760;

    // checks if on even or odd line. Moves right or left depending on the line number.
    public int positionPlayerOneCircle = 1;
    public int positionPlayerTwoCircle = 1;

    // starts/stops game
    public boolean hasGameStarted = false;
    // start button
    public Button startButton;

    // board
    private final Group board = new Group();

    // change
    private Parent draw() {
        // change
        Pane root = new Pane();
        // size of board
        root.setPrefSize(1050, 800);
        root.getChildren().addAll(board);

        // adds a tile to the board depending on loop
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < height; y++) {
                // dimensions of tile
                Tile tile = new Tile(tileSize, tileSize);
                tile.setTranslateX(y * tileSize);
                tile.setTranslateY(x * tileSize);
                // add to board
                board.getChildren().add(tile);
                // adds the tile position to grid
                grid[x][y] = y * (tileSize-40);
            }
        }

        // creating the player pieces
        // Player One's piece (BLUE)
        playerOne = new Circle(40);
        playerOne.setId("Player 1");
        playerOne.setFill(Color.AQUA);
        playerOne.setTranslateX(positionOneX);
        playerOne.setTranslateY(positionOneY);

        // Player Two's piece (RED)
        playerTwo = new Circle(40);
        playerTwo.setId("Player 2");
        playerTwo.setFill(Color.RED);
        playerTwo.setTranslateX(positionTwoX);
        playerTwo.setTranslateY(positionTwoY);

        Button playerOneButton = new Button("Player 1");
        String playerOneButtonCSS = "-fx-background-color: #add8e6;" + "-fx-font-size: 18pt;" + "-fx-border-radius: 10;";
        playerOneButton.setStyle(playerOneButtonCSS);
        playerOneButton.setTranslateX(840);
        playerOneButton.setTranslateY(200);
        // https://docs.oracle.com/javafx/2/ui_controls/ButtonSample.java.html
        playerOneButton.setOnAction(new EventHandler<ActionEvent>() {
            // if clicked execute these commands
            @Override
            public void handle(ActionEvent actionEvent) {
                if (hasGameStarted) {
                    if (playerOneTurn) {
                        clickButton.setText("Click Player 2 Button");
                        diceRollDesc.setText("Player 1 has rolled a:");
                        hasLandedText.setText("");
                        LadderandSnakePositionLabel.setText("");
                        playerTurnLabel.setText("Player 2 Turn");
                        // picks a random number from 1 to 6
                        randomDice();
                        randResult.setText(String.valueOf(rand));
                        // keeps track of player 1's number position
                        positionOne += rand;
                        if (rand == 6) {
                            playerTurnLabel.setText("Player 1 Turn Again");
                            clickButton.setText("Click Player 1 Button");
                            diceRollDesc.setText("Player 1 has rolled a:");
                        }
                        if (rand != 6) {
                            playerOneTurn = false;
                            playerTwoTurn = true;
                        }
                        movePlayerOne();
                        movePlayer(positionOneX, positionOneY, playerOne);
                        // switch to player two


                        // LADDERS
                        // 3 to 39
                        if (positionOneX == 200 && positionOneY == 760) {
                            movePlayer(positionOneX = 120, positionOneY = 520, playerOne);
                            hasLandedText.setText("Player 1 has hit a ladder!");
                            LadderandSnakePositionLabel.setText("Player 1 has hit the 3 to 39 ladder");
                            positionOne += 36;
                            positionPlayerOneCircle = 4;
                        }
                        // 10 to 12
                        if (positionOneX == 760 && positionOneY == 760) {
                            movePlayer(positionOneX = 680, positionOneY = 680, playerOne);
                            hasLandedText.setText("Player 1 has hit a ladder!");
                            LadderandSnakePositionLabel.setText("Player 1 has hit the 10 to 12 ladder");
                            positionOne += 2;
                            positionPlayerOneCircle = 2;
                        }
                        // 27 to 53
                        if (positionOneX == 520 && positionOneY == 600) {
                            movePlayer(positionOneX = 600, positionOneY = 360, playerOne);
                            hasLandedText.setText("Player 1 has hit a ladder!");
                            LadderandSnakePositionLabel.setText("Player 1 has hit the 27 to 53 ladder");
                            positionOne += 26;
                            positionPlayerOneCircle = 6;
                        }
                        // 56 to 84
                        if (positionOneX == 360 && positionOneY == 360) {
                            movePlayer(positionOneX = 280, positionOneY = 120, playerOne);
                            hasLandedText.setText("Player 1 has hit a ladder!");
                            LadderandSnakePositionLabel.setText("Player 1 has hit the 56 to 84 ladder");
                            positionOne = 28;
                            positionPlayerOneCircle = 9;
                        }
                        // 61 to 99
                        if (positionOneX == 40 && positionOneY == 280) {
                            movePlayer(positionOneX = 120, positionOneY = 40, playerOne);
                            hasLandedText.setText("Player 1 has hit a ladder!");
                            LadderandSnakePositionLabel.setText("Player 1 has hit the 61 to 99 ladder");
                            positionOne += 38;
                            positionPlayerOneCircle = 10;
                        }
                        // 72 to 90
                        if (positionOneX == 680 && positionOneY == 200) {
                            movePlayer(positionOneX = 760, positionOneY = 120, playerOne);
                            hasLandedText.setText("Player 1 has hit a ladder!");
                            LadderandSnakePositionLabel.setText("Player 1 has hit the 72 to 90 ladder");
                            positionOne += 18;
                            positionPlayerOneCircle = 9;
                        }
                        // SNAKES
                        // 16 to 13
                        if (positionOneX == 360 && positionOneY == 680) {
                            movePlayer(positionOneX = 600, positionOneY, playerOne);
                            hasLandedText.setText("Player 1 has hit a snake!");
                            LadderandSnakePositionLabel.setText("Player 1 has hit the 16 to 13 snake");
                            positionOne -= 3;
                        }
                        // 31 to 4
                        if (positionOneX == 760 && positionOneY == 520) {
                            movePlayer(positionOneX = 280, positionOneY = 760, playerOne);
                            hasLandedText.setText("Player 1 has hit a snake!");
                            LadderandSnakePositionLabel.setText("Player 1 has hit the 31 to 4 snake");
                            positionOne -= 27;
                            positionPlayerOneCircle = 1;
                        }
                        // 47 to 25
                        if (positionOneX == 520 && positionOneY == 440) {
                            movePlayer(positionOneX = 360, positionOneY = 600, playerOne);
                            hasLandedText.setText("Player 1 has hit a snake!");
                            LadderandSnakePositionLabel.setText("Player 1 has hit the 47 to 25 snake");
                            positionOne -= 22;
                            positionPlayerOneCircle = 3;
                        }
                        // 66 to 52
                        if (positionOneX == 440 && positionOneY == 280) {
                            movePlayer(positionOneX = 680, positionOneY = 360, playerOne);
                            hasLandedText.setText("Player 1 has hit a snake!");
                            LadderandSnakePositionLabel.setText("Player 1 has hit the 66 to 52 snake");
                            positionOne -= 14;
                            positionPlayerOneCircle = 6;
                        }
                        // 63 to 60
                        if (positionOneX == 200 && positionOneY == 280) {
                            movePlayer(positionOneX = 40, positionOneY = 360, playerOne);
                            hasLandedText.setText("Player 1 has hit a snake!");
                            LadderandSnakePositionLabel.setText("Player 1 has hit the 63 to 60 snake");
                            positionOne -= 3;
                            positionPlayerOneCircle = 6;
                        }
                        // 97 to 75
                        if (positionOneX == 280 && positionOneY == 40) {
                            movePlayer(positionOneX = 440, positionOneY = 200, playerOne);
                            LadderandSnakePositionLabel.setText("Player 1 has hit the 97 to 75 snake");
                            hasLandedText.setText("Player 1 has hit a snake!");
                            positionOne -= 22;
                            positionPlayerOneCircle = 8;

                        }
                    }

                }
            }
        });

        // Player 2 Button for dice roll
        Button playerTwoButton = new Button("Player 2");
        String playerTwoButtonCSS = "-fx-background-color: red;" + "-fx-font-size: 18pt;" + "-fx-border-radius: 10;"
                + "-fx-cursor-pointer: true";
        playerTwoButton.setStyle(playerTwoButtonCSS);
        playerTwoButton.setTranslateX(840);
        playerTwoButton.setTranslateY(300);
        playerTwoButton.setOnAction(new EventHandler<ActionEvent>() {
            // if clicked execute these commands
            @Override
            public void handle(ActionEvent actionEvent) {
                if (hasGameStarted) {
                    if (playerTwoTurn) {
                        clickButton.setText("Click Player 1 Button");
                        diceRollDesc.setText("Player 2 has rolled a:");
                        hasLandedText.setText("");
                        LadderandSnakePositionLabel.setText("");
                        playerTurnLabel.setText("Player 1 Turn");
                        // picks a random number from 1 to 6
                        randomDice();
                        randResult.setText(String.valueOf(rand));
                        // keeps track of the number position of player 2
                        positionTwo += rand;
                        if (rand == 6) {
                            playerTurnLabel.setText("Player 2 Turn Again");
                            clickButton.setText("Click Player 2 Button");
                            diceRollDesc.setText("Player 2 has rolled a:");
                        }
                        if (rand != 6) {
                            playerOneTurn = true;
                            playerTwoTurn = false;
                        }
                        movePlayerTwo();
                        movePlayer(positionTwoX, positionTwoY, playerTwo);
                        // switch turns to Player 1


                        // LADDERS
                        // 3 to 39
                        if (positionTwoX == 200 && positionTwoY == 760) {
                            movePlayer(positionTwoX = 120, positionTwoY = 520, playerTwo);
                            hasLandedText.setText("Player 2 has hit a ladder!");
                            LadderandSnakePositionLabel.setText("Player 2 has hit the 3 to 39 ladder");
                            positionTwo += 36;
                            positionPlayerTwoCircle = 4;

                        }
                        // 10 to 12
                        if (positionTwoX == 760 && positionTwoY == 760) {
                            movePlayer(positionTwoX = 680, positionTwoY = 680, playerTwo);
                            hasLandedText.setText("Player 2 has hit a ladder!");
                            LadderandSnakePositionLabel.setText("Player 2 has hit the 10 to 12 ladder");
                            positionTwo += 2;
                            positionPlayerTwoCircle = 2;
                        }
                        // 27 to 53
                        if (positionTwoX == 520 && positionTwoY == 600) {
                            movePlayer(positionTwoX = 600, positionTwoY = 360, playerTwo);
                            hasLandedText.setText("Player 2 has hit a ladder!");
                            LadderandSnakePositionLabel.setText("Player 2 has hit the 27 to 53 ladder");
                            positionTwo += 26;
                            positionPlayerTwoCircle = 6;
                        }
                        // 56 to 84
                        if (positionTwoX == 360 && positionTwoY == 360) {
                            movePlayer(positionTwoX = 280, positionTwoY = 120, playerTwo);
                            hasLandedText.setText("Player 2 has hit a ladder!");
                            LadderandSnakePositionLabel.setText("Player 2 has hit the 56 to 84 ladder");
                            positionTwo += 28;
                            positionPlayerTwoCircle = 9;
                        }
                        // 61 to 99
                        if (positionTwoX == 40 && positionTwoY == 280) {
                            movePlayer(positionTwoX = 120, positionTwoY = 40, playerTwo);
                            hasLandedText.setText("Player 2 has hit a ladder!");
                            LadderandSnakePositionLabel.setText("Player 2 has hit the 61 to 99 ladder");
                            positionTwo += 38;
                            positionPlayerTwoCircle = 10;
                        }
                        // 72 to 90
                        if (positionTwoX == 680 && positionTwoY == 200) {
                            movePlayer(positionTwoX = 760, positionTwoY = 120, playerTwo);
                            hasLandedText.setText("Player 2 has hit a ladder!");
                            LadderandSnakePositionLabel.setText("Player 2 has hit the 72 to 90 ladder");
                            positionTwo += 18;
                            positionPlayerTwoCircle = 9;
                        }
                        // SNAKES
                        // 16 to 13
                        if (positionTwoX == 360 && positionTwoY == 680) {
                            movePlayer(positionTwoX = 600, positionTwoY, playerTwo);
                            hasLandedText.setText("Player 2 has hit a snake!");
                            LadderandSnakePositionLabel.setText("Player 2 has hit the 16 to 13 snake");
                            positionTwo -= 3;
                        }
                        // 31 to 4
                        if (positionTwoX == 760 && positionTwoY == 520) {
                            movePlayer(positionTwoX = 280, positionTwoY = 760, playerTwo);
                            LadderandSnakePositionLabel.setText("Player 2 has hit the 31 to 4 snake");
                            hasLandedText.setText("Player 2 has hit a snake!");
                            positionTwo -= 27;
                            positionPlayerTwoCircle = 1;
                        }
                        // 47 to 25
                        if (positionTwoX == 520 && positionTwoY == 440) {
                            movePlayer(positionTwoX = 360, positionTwoY = 600, playerTwo);
                            hasLandedText.setText("Player 2 has hit a snake!");
                            LadderandSnakePositionLabel.setText("Player 2 has hit the 47 to 25 snake");
                            positionTwo -= 22;
                            positionPlayerTwoCircle = 3;
                        }
                        // 66 to 52
                        if (positionTwoX == 440 && positionTwoY == 280) {
                            movePlayer(positionTwoX = 680, positionTwoY = 360, playerTwo);
                            LadderandSnakePositionLabel.setText("Player 2 has hit the 66 to 52 snake");
                            hasLandedText.setText("Player 2 has hit a snake!");
                            positionTwo -= 14;
                            positionPlayerTwoCircle = 6;
                        }
                        // 63 to 60
                        if (positionTwoX == 200 && positionTwoY == 280) {
                            movePlayer(positionTwoX = 40, positionTwoY = 360, playerTwo);
                            LadderandSnakePositionLabel.setText("Player 2 has hit the 63 to 60 snake");
                            hasLandedText.setText("Player 2 has hit a snake!");
                            positionTwo -= 3;
                            positionPlayerTwoCircle = 6;
                        }
                        // 97 to 75
                        if (positionTwoX == 280 && positionTwoY == 40) {
                            movePlayer(positionTwoX = 440, positionTwoY = 200, playerTwo);
                            hasLandedText.setText("Player 2 has hit a snake!");
                            LadderandSnakePositionLabel.setText("Player 2 has hit the 97 to 75 snake");
                            positionTwo -= 22;
                            positionPlayerTwoCircle = 8;
                        }
                    }
                }
            }
        });

        // The start button to initiate the game
        startButton = new Button(" START! ");
        startButton.setTranslateX(835);
        String startButtonCSS = "-fx-background-color: yellow;" + "-fx-font-size: 18pt;" + "-fx-border-radius: 10;";
        startButton.setStyle(startButtonCSS);
        startButton.setTranslateY(400);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            // if clicked, automatically resets to normal conditions
            @Override
            public void handle(ActionEvent actionEvent) {
                positionOne = 1;
                positionTwo = 1;

                positionPlayerOneCircle = 1;
                positionPlayerTwoCircle = 1;

                positionOneX = 40;
                positionOneY = 760;

                positionTwoX = 40;
                positionTwoY = 760;

                playerOne.setTranslateX(positionOneX);
                playerOne.setTranslateY(positionOneY);

                playerTwo.setTranslateX(positionTwoX);
                playerTwo.setTranslateY(positionTwoY);

                hasGameStarted = true;
                startButton.setText("Restart?");
                clickButton.setText("Click Player 1 Button");
                playerTurnLabel.setText("Player 1 Turn");
                playerTurnLabel.setText("Player 1 Turn");
                playerOneTurn = true;
                playerTwoTurn = false;
                randResult.setText("0");
                diceRollDesc.setText("");
            }
        });

        // Displays the random number after button dice roll clock
        randResult = new Label("0");
        randResult.setFont(new Font("Courier New", 60));
        randResult.setTranslateX(875);
        randResult.setTranslateY(600);
        String randResultCSS = "-fx-border-color: black;" + "-fx-border-width: 3px;";
        randResult.setStyle(randResultCSS);

        // Displays if the piece lands on a ladder or snake
        hasLandedText = new Label("");
        hasLandedText.setFont(new Font("Courier New", 14));
        hasLandedText.setTranslateY(485);
        hasLandedText.setTranslateX(810);

        // Displays "Player X rolled a:
        diceRollDesc = new Label("");
        diceRollDesc.setFont(new Font("Courier New", 14));
        diceRollDesc.setTranslateX(810);
        diceRollDesc.setTranslateY(550);

        clickButton = new Label("");
        clickButton.setFont(new Font("Courier New", 14));
        clickButton.setTranslateX(810);
        clickButton.setTranslateY(100);

        playerTurnLabel = new Label("");
        playerTurnLabel.setFont(new Font("Courier New", 14));
        playerTurnLabel.setTranslateX(835);
        playerTurnLabel.setTranslateY(50);

        LadderandSnakePositionLabel = new Label("");
        LadderandSnakePositionLabel.setFont(new Font("Courier New", 11));
        LadderandSnakePositionLabel.setTranslateX(810);
        LadderandSnakePositionLabel.setTranslateY(515);

        // getting the image from the assets directory
//        File img = new File("sample/assets/snakebg.jpg");
//         rendering the image
        Image bg = new Image("sample/snakebg.jpg");
        // setting the image as the background
        ImageView background = new ImageView();
        background.setImage(bg);
        background.setFitHeight(800);
        background.setFitWidth(800);

        // add to get children "background"
        board.getChildren().addAll(background, playerOne, playerTwo, playerOneButton, playerTwoButton, startButton,
                randResult, hasLandedText, diceRollDesc, clickButton, playerTurnLabel, LadderandSnakePositionLabel);
        return root;
    }

    // picks a random number from 1 to 6 (represents the dice)

    // pick a random number from 1 to 6
    private void randomDice() {
        rand = (int) (Math.random() * 6 + 1);
    }

    // moves player 1 depending on what line it is on
    // if player 1 is on an odd line (Line 1: 1-10, Line 3: 21-30, etc), move only to the right.
    // if player 1 on an even line (Line 2: 11:20, Line 4: 31-40, etc), move to the left.
    // if player 1 is on either end of the board, move up one line.
    // if the position of player 1 is 100 or greater, they win
    private void movePlayerOne() {
        for (int i = 0; i < rand; i++) {
            if (positionPlayerOneCircle % 2 == 1) {
                positionOneX += 80;
            }
            if (positionPlayerOneCircle % 2 == 0) {
                positionOneX -= 80;
            }
            if (positionOneX > 760) {
                positionOneY -= 80;
                positionOneX -= 80;
                positionPlayerOneCircle++;
            }
            if (positionOneX < 40) {
                positionOneY -= 80;
                positionOneX += 80;
                positionPlayerOneCircle++;
            }

            if (positionOne >= 100) {
                positionOneX = 40;
                positionOneY = 40;
                hasGameStarted = false;
                hasLandedText.setTranslateX(840);
                hasLandedText.setText("Player 1 Wins!");
                LadderandSnakePositionLabel.setText("");
                clickButton.setText("");
                playerTurnLabel.setText("");
                // Switch to Scene 3
            }
        }
    }

    // moves player 2 depending on what line it is on
    // if player 2 is on an odd line (Line 1: 1-10, Line 3: 21-30, etc), move only to the right.
    // if player 2 on an even line (Line 2: 11:20, Line 4: 31-40, etc), move to the left.
    // if player 2 is on either end of the board, move up one line.
    // if the position of player 2 is 100 or greater, they win
    private void movePlayerTwo() {
        for (int i = 0; i < rand; i++) {
            if (positionPlayerTwoCircle % 2 == 1) {
                positionTwoX += 80;
            }
            if (positionPlayerTwoCircle % 2 == 0) {
                positionTwoX -= 80;
            }
            if (positionTwoX > 760) {
                positionTwoY -= 80;
                positionTwoX -= 80;
                positionPlayerTwoCircle++;
            }
            if (positionTwoX < 40) {
                positionTwoY -= 80;
                positionTwoX += 80;
                positionPlayerTwoCircle++;
            }

            if (positionTwo >= 100) {
                positionTwoX = 40;
                positionTwoY = 40;
                hasGameStarted = false;
                hasLandedText.setTranslateX(840);
                hasLandedText.setText("Player 2 Wins!");
                LadderandSnakePositionLabel.setText("");
                clickButton.setText("");
                playerTurnLabel.setText("");
                // Switch to Scene 3
            }
        }
    }


    // adds animation to movement from point a to b
    private void movePlayer(int x, int y, Circle c) {
        TranslateTransition animation = new TranslateTransition(Duration.millis(1500), c);
        animation.setToX(x);
        animation.setToY(y);
        animation.setAutoReverse(false);
        animation.play();
    }


    // Stage
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Intro Screen Components
        window = primaryStage;
        Label label1 = new Label("Welcome to Snakes and Ladders");
        label1.setFont(new Font("Courier New", 20));
        label1.setTranslateX(350);
        label1.setTranslateY(100);

        Label label2 = new Label("How to Play:");
        label2.setFont(new Font("Courier New", 20));
        label2.setTranslateX(460);
        label2.setTranslateY(200);

        Button button1 = new Button("Let's Play Snakes and Ladders!");
        button1.setTranslateX(400);
        button1.setTranslateY(525);
        String button1CSS = "-fx-background-color: yellow;" + "-fx-font-size: 18;" + "-fx-border-radius: 10;";
        button1.setStyle(button1CSS);
        // if clicked go to game scene
        button1.setOnAction(e -> window.setScene(gameScene));

        // rules
        String rules = "1. Two Player Snakes and Ladders Game \n" +
                "2. Player 1 clicks \"Player 1\" button \n" +
                "3. The program will roll the dice for you \n" +
                "4. Your piece will move the number of spaces \n " +
                "that you get from dice roll \n" +
                "5. Repeat steps 2 - 4 for Player 2 \n" +
                "6. First person to hit the 100th tile wins \n" +
                "7. If you roll a 6 you get an extra turn";
        Label label3 = new Label(rules);
        label3.setTranslateX(275);
        label3.setTranslateY(250);
        label3.setFont(new Font("Courier New", 20));


        // Initialize the intro screen
        Pane introScreenPane = new Pane();
        introScreenPane.getChildren().addAll(label1, button1, label2, label3);
        introScreen = new Scene(introScreenPane, 1050, 800);

        window.setOnCloseRequest(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Quit Game?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                window.close();
            } else if (result.get() == ButtonType.CANCEL) {
                e.consume();
            }
        });

        // add all components of draw method
        gameScene = new Scene(draw());
        window.setScene(introScreen);
        window.setTitle("ICS4UI - Snakes and Ladders");
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


