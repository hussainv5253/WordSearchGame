package application;
	

import java.io.File;
//import java.util.Random;

import scene.GameManager;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static scene.Turorial.getTHbox;
//import static scene.Media.getMHbox;

public class Main extends Application implements EventHandler<KeyEvent>
{
	public enum Level {
		LEVEL1, LEVEL2, LEVEL3
    }

    final int Width = 620; // Sets the window size
    int Size;
    int colSelect = 3; // The column that the user currently has selected
    int rowSelect = 6; // The row that the user currently has selected
    final int VBOX_SPACING = 25;
    Level level; // Enum variable to contain difficulty setting
    Scene difficultyScene;
	Scene mainGame;
	Scene gameOver; // The three scenes to contain the three views of Difficulty Selection,
                                                // Main Game, and Game Over
    GraphicsContext gc; // Graphics Context used to print the Main Game screen
    String input = ""; // Stores the word that the user selects
    GameManager game = new GameManager();
    
    private MediaPlayer mediaPlayer;
    private boolean playMusic;
    
    private void initGameScene(Stage stage)
	{
//		Media sound = new Media(new File("src/backgroundImage/bgm.mp3").toURI().toString());
//		MediaPlayer mediaPlayer = new MediaPlayer(sound);
//		mediaPlayer.play();



		Canvas canvas = new Canvas(Width, Width);
		gc = canvas.getGraphicsContext2D();

		VBox keyboardNode = new VBox();
		keyboardNode.setFocusTraversable(true);
		keyboardNode.requestFocus();
		keyboardNode.setOnKeyPressed(this);

		Button buttonBack = new Button("Back");
		buttonBack.setStyle("-fx-padding:0 ; -fx-pref-height:20; -fx-pref-width: 50;"
				+ "   -fx-text-fill: #fef9f3; -fx-background-color: #b8acac;");
		buttonBack.setFocusTraversable(false);
		buttonBack.setId("buttonBack");


		Group rootGroup = new Group();
		rootGroup.setLayoutX(50);
		rootGroup.setLayoutY(50);
		rootGroup.getChildren().addAll(canvas, keyboardNode,buttonBack); // Adds canvas for printing to and the keyboard listener


		mainGame = new Scene(rootGroup, Width, Width);// Sets the window size and content to show

		mainGame.setFill(Color.web("#fce9df"));
		mainGame.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		buttonBack.setOnAction(e -> {
//	            mainGame.stop();
			stage.setScene(difficultyScene);
		});
	}
	Button buttonEasy;
	Button buttonMedium;
	Button buttonHard;

	Button changeBackGround;
	Button buttonYes;
	Button buttonNo;



	AnimationTimer animationTimer;


//	MediaPlayer mediaPlayer;
//	boolean playMusic;
	
	public void initOver(Stage stage){
		Text close = new Text("Good Job!");
		close.setId("fancytext");
		close.setText("             Good Job!\n You found all of the words");
		Text retry = new Text("Do you want to play again?");
		retry.setId("labeltext");
		retry.setText("Do you want to play again?");

		VBox labelClosing = new VBox();
		labelClosing.setAlignment(Pos.CENTER);
		labelClosing.getChildren().addAll(close,retry);

		//create Hbox for button to message game difficulty
		buttonYes = new Button("Yes !");
		buttonYes.setId("button");
		buttonNo = new Button("No!! ");
		buttonNo.setId("button");


		HBox btnForRetry=new HBox();
		btnForRetry.setAlignment(Pos.CENTER);
		btnForRetry.getChildren().addAll(buttonYes, buttonNo);   // Adds Buttons
		btnForRetry.setSpacing(30);

		VBox rootClosing = new VBox(VBOX_SPACING);

		rootClosing.setBackground(new Background(
				new BackgroundImage(
						new Image("/backgroundImage/img_1.png"),
						BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
						new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
						new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
				)));

		rootClosing.setAlignment(Pos.CENTER);
		rootClosing.getChildren().addAll(labelClosing, btnForRetry);
		gameOver = new Scene(rootClosing, Width, Width);

		//set frontend
		gameOver.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(gameOver);
		stage.show();
	}

	
	@Override
	public void start(Stage primaryStage) {
		try {
			/* Setting Scene1 The Launch Page of Game 
			 * */

		    //create Vbox for welcomeing text
			   //
			primaryStage.setResizable(false);
			primaryStage.setTitle("Search Game");

//			initDiffculty(primaryStage);
			Text welGame = new Text("Word Search Game.....");
			welGame.setId("fancytext");

			Text message = new Text("Please Choose Level ");
			message.setId("labeltext");

			VBox labelContainer = new VBox();
			labelContainer.setAlignment(Pos.CENTER);
			labelContainer.getChildren().addAll(welGame,message);
			labelContainer.setSpacing(30);

			//create Hbox for button to message game difficulty
			buttonEasy = new Button("Easy");
			buttonEasy.setId("button");
			buttonMedium = new Button("Medium");
			buttonMedium.setId("button");
			buttonHard= new Button("Hard");
			buttonHard.setId("button");
			changeBackGround=new Button("Change Background");
			changeBackGround.setId("button");


			VBox buttonContainer=new VBox();
			buttonContainer.setAlignment(Pos.CENTER);
			buttonContainer.getChildren().addAll(buttonEasy, buttonMedium, buttonHard,changeBackGround);   // Adds Buttons
			buttonContainer.setSpacing(30);

			VBox rootContainer = new VBox(VBOX_SPACING);
			rootContainer.setBackground(new Background(
					new BackgroundImage(
							new Image("/backgroundImage/img_1.png"),
							BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
							new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
							new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
					)));

			rootContainer.setAlignment(Pos.CENTER);
			HBox hBox=getTHbox();
//			HBox hmbox= getMHbox();
			rootContainer.getChildren().addAll(labelContainer, buttonContainer, hBox);
			difficultyScene = new Scene(rootContainer, Width, Width);
					//set frontend
			difficultyScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());


			Media sound = new Media(new File("src/backgroundImage/bgm.mp3").toURI().toString());
			mediaPlayer = new MediaPlayer(sound);
			Button button=new Button();
			ImageView image=new ImageView("backgroundImage/img_6.png");
			image.setPreserveRatio(true);
			image.setFitHeight(30);
			button.setGraphic(image);
			button.setBackground(null);
			button.setPrefSize(Region.USE_PREF_SIZE,30);
			button.setContentDisplay(ContentDisplay.CENTER);
			button.setPadding(new Insets(0,0,0,450));


			hBox.getChildren().add(button);
			button.setOnAction(e->{
				if(playMusic){
					playMusic=false;
					mediaPlayer.pause();
					ImageView image1=new ImageView("backgroundImage/img_7.png");
					image1.setPreserveRatio(true);
					image1.setFitHeight(30);
					button.setGraphic(image1);
				}else{
					playMusic=true;
					mediaPlayer.play();
					ImageView image1=new ImageView("backgroundImage/img_6.png");
					image1.setPreserveRatio(true);
					image1.setFitHeight(30);
					button.setGraphic(image1);
				}

			});


			mediaPlayer.setAutoPlay(true);
			playMusic=true;

//			stage.setScene(difficultyScene);
//			stage.show();
			//game/main scene
			initGameScene(primaryStage);
	        initOver(primaryStage);
	        //Scene2 for closing
	        
	        animationTimer = new AnimationTimer() {
	            @Override
	            public void handle(long arg0) {
	                gc.clearRect(0, 0, Width, Width);

	                gc.setTextAlign(null);;

	                runGame();
	                input = game.getInput();
	                checkEnd(primaryStage);
	            }
	        };
	        
	        primaryStage.setScene(difficultyScene);
	        primaryStage.show();
			addAction(primaryStage);

			

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addAction(Stage primaryStage)
	{
		buttonEasy.setOnAction(e -> {
			level = Level.LEVEL1;
			game.initMap(level);
			Size = game.getMatrixSize();
			primaryStage.setScene(this.mainGame);
//	            primaryStage.centerOnScreen();
			animationTimer.start();
		});


		buttonMedium.setOnAction(e -> { // When Medium selected
			level = Level.LEVEL2;
			game.initMap(level);
			Size = game.getMatrixSize();
			primaryStage.setScene(this.mainGame);
			animationTimer.start();

		});

		buttonHard.setOnAction(e -> { // When Hard selected
			level = Level.LEVEL3;
			game.initMap(level);
			Size = game.getMatrixSize();
			primaryStage.setScene(this.mainGame);
			animationTimer.start();

		});

		buttonYes.setOnAction(e -> { // If use chooses to play again
			animationTimer.stop(); // Stops main game loop
			primaryStage.setScene(difficultyScene); // Resets stage to show starting screen
		});

		buttonNo.setOnAction(e -> Platform.exit()); // If user doesnt want to play again, quit
		changeBackGround.setOnAction(e->{
			String[] s1={"img_1.png","img_2.png","img_3.jpg","img_3.png"};
			((VBox)(difficultyScene.getRoot())).setBackground(new Background(
					new BackgroundImage(
							new Image("/backgroundImage/"+s1[i]),
							BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
							new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
							new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
					)));
			i=(i+1)%4;
		});

	}
	int i=1;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	 /**
     * This prints the gameBoard char array to the canvas, and is used to
     * graphically show the game board to the user
     */
    public void fillBoard() {
		int width=(Width-50)/(Size+1);
		int height=(Width-100)/(Size+4);
        for (int i = 0; i < Size; i++) {
            for (int j = 0; j < Size; j++) {
                gc.fillText(String.valueOf(game.getCharPos(i, j)), (25 + (width * j)), (100 + (height * i)));
            }
        }
    }
    
    public void fillWord() {
        int rowCounter = 1;
        int colCounter = 0;
        int indent = 15;
        for (int i = 0; i < game.getWordListSize(); i++) {
            gc.fillText(game.getWordListValue(i), (indent + (125 * colCounter)), (indent + (20 * rowCounter)));
            colCounter++;
            if (colCounter == 3) {
                colCounter = 0;
                rowCounter++;
            }
        }
    }
    
    //reverse b/w color to show current select letter 
    public void changeColor() {
		int width=(Width-50)/(Size+1);
		int height=(Width-100)/(Size+4);
        gc.save(); 
        gc.setFill(Color.BLACK);
        gc.fillRect((22 + (width * colSelect)), (88 + (height * rowSelect)), 15, 15);
        gc.setFill(Color.WHITE);
        gc.fillText(String.valueOf(game.getCharPos(rowSelect, colSelect)), (25 + (width * colSelect)),
                (100 + (height * rowSelect)));
        gc.restore(); 
    }

    //method for animation timer, contains everything in the game scene
    public void runGame() {
        fillBoard();
        fillWord();
        changeColor();
		gc.fillText("Word Selected: " + input, 15, 75);
    }
    
    //handle selecting letter
    public void handle(KeyEvent e) {

        if (e.getCode() == KeyCode.A) {
            if (colSelect - 1 >= 0) {
                colSelect--;
            }
        }
        if (e.getCode() == KeyCode.D) {
            if (colSelect + 1 < Size) {
                colSelect++;
            }
        }
        if (e.getCode() == KeyCode.W) {
            if (rowSelect - 1 >= 0) {
                rowSelect--;
            }
        }
        if (e.getCode() == KeyCode.S) {
            if (rowSelect + 1 < Size) {
                rowSelect++;
            }
        }
        if (e.getCode() == KeyCode.ENTER) { 
            game.checkCurrentSelect(rowSelect, colSelect);
        }
        if (e.getCode() == KeyCode.DELETE || e.getCode() == KeyCode.BACK_SPACE) { // If delete or backspace is pressed
            game.deleteLastLetter();
            game.wordSelectedToString();
        }
    }
    
    public void checkEnd(Stage gameStage) {
        if (game.getWordListSize() == 0) {
            gameStage.setScene(gameOver);
        }
    }
}


