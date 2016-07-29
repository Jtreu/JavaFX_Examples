package com.space.pack1;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.shape.Polygon;

public class GameMenu extends Application {
	private int livesLeft = 3;
	private int NUM_INVADERS = 10;
	private int invaderCount = (NUM_INVADERS * 3);
	private int score = 0;
	private Timer timer = new Timer();
	private Timer enemyTimer = new Timer();
	private Timer enemyBulletTimer = new Timer();
	private Timer enemyBulletInterval = new Timer();
	private Timer ufoTimer = new Timer();
    private int seconds = 0;
    Group plebsHitboxes, squidsHitboxes, brutesHitboxes;
    ArrayList<Rectangle> plebsHitboxesList, squidsHitboxesList, brutesHitboxesList;
	
	URL resource = getClass().getResource("assets/MenuMusic.wav");
	AudioClip mainMenuMusic = new AudioClip(resource.toString());
	
	URL ufoResource = getClass().getResource("assets/ufo_lowpitch.wav");
	AudioClip ufoSound = new AudioClip(ufoResource.toString());
	
	URL invaderResource4 = getClass().getResource("assets/fastinvader4.wav");
	URL invaderResource3 = getClass().getResource("assets/fastinvader3.wav");
	URL invaderResource2 = getClass().getResource("assets/fastinvader2.wav");
	URL invaderResource1 = getClass().getResource("assets/fastinvader1.wav");
	
	AudioClip invaderSound4 = new AudioClip(invaderResource4.toString());
	AudioClip invaderSound3 = new AudioClip(invaderResource3.toString());
	AudioClip invaderSound2 = new AudioClip(invaderResource2.toString());
	AudioClip invaderSound1 = new AudioClip(invaderResource1.toString());
	
	URL inaderKilledResource = getClass().getResource("assets/invaderkilled.wav");
	AudioClip invaderKilledSound = new AudioClip(inaderKilledResource.toString());
	
	private Game gameMenu;
	private Pane gamePane;
	private Scene gameScene;
    private Stage gameStage;
    
    private Pane gameOverPane;
    private Pane gameOverScene;

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		gameStage = primaryStage;
		Pane menuPane = new Pane();
		gameMenu = new Game();
		menuPane.setPrefSize(800, 600);
		
		InputStream iStream = Files.newInputStream(Paths.get("res/images/spaceInvadersBackground.jpg"));
		Image img = new Image(iStream);
		iStream.close();
		
		ImageView imgView = new ImageView(img);
		imgView.setFitWidth(800);
		imgView.setFitHeight(600);
		
		menuPane.getChildren().addAll(imgView, gameMenu);
		
		Scene menuScene = new Scene(menuPane);
		
		mainMenuMusic.play();
		
		primaryStage.setScene(menuScene);
		primaryStage.setTitle("Space Invaders");
		primaryStage.show();
	}
	
	// Menu Buttons
	private static class MenuButton extends StackPane {
		private Text text;
		
		public MenuButton(String name) {
			text = new Text(name);
			text.setFont(new Font(20));
			text.setFill(Color.WHITE);
			
			Rectangle bg = new Rectangle(250, 30);
            bg.setOpacity(0.6);
            bg.setFill(Color.BLACK);
            bg.setEffect(new GaussianBlur(3.5));
            
            setAlignment(Pos.CENTER_LEFT);
            setRotate(-0.5);
            getChildren().addAll(bg, text);
            
            setOnMouseEntered(event -> {
                bg.setTranslateX(10);
                text.setTranslateX(10);
                bg.setFill(Color.WHITE);
                text.setFill(Color.BLACK);
            });
            
            setOnMouseExited(event -> {
                bg.setTranslateX(0);
                text.setTranslateX(0);
                bg.setFill(Color.BLACK);
                text.setFill(Color.WHITE);
            });

            DropShadow drop = new DropShadow(50, Color.WHITE);
            drop.setInput(new Glow());
            
            setOnMousePressed(event -> setEffect(drop));
            setOnMouseReleased(event -> setEffect(null));
		}
	}
	
	// Game Container
	private class Game extends Parent {
        public Game() {
            VBox menu0 = new VBox(10);
            VBox menu1 = new VBox(10);

            menu0.setTranslateX(100);
            menu0.setTranslateY(200);

//            menu1.setTranslateX(100);
//            menu1.setTranslateY(200);

            final int offset = 400;

            menu1.setTranslateX(offset);

            MenuButton btnNewGame = new MenuButton("PLAY");
            btnNewGame.setOnMouseClicked(event -> {
                FadeTransition ft = new FadeTransition(Duration.seconds(0.5));
                ft.setFromValue(1);
                ft.setToValue(0);
                ft.setOnFinished(evt -> setVisible(false));
                ft.play();
                
                mainMenuMusic.stop();
                
                gamePane = new Pane();
                gamePane.setStyle("-fx-background-color: black;");
                
                /* *********************************************** */
                /* *******************GAME CODE******************* */
                /* *********************************************** */
                // Text HUD
        		Group HUD = new Group();
        		Text scoreText = new Text("SCORE");
        		scoreText.setX(50);
        		scoreText.setY(25);
        		scoreText.setFill(Color.WHITE);
        		scoreText.setStroke(Color.WHITE);
        		Text livesText = new Text("LIVES");
        		livesText.setX(300);
        		livesText.setY(25);
        		livesText.setFill(Color.WHITE);
        		livesText.setStroke(Color.WHITE);
        		
        		// Sounds
        		URL shootResource = getClass().getResource("assets/shoot.wav");
        		AudioClip audioShoot = new AudioClip(shootResource.toString());
        		
        		URL invaderKilledResource = getClass().getResource("assets/invaderkilled.wav");
        		AudioClip audioInvaderKilled = new AudioClip(invaderKilledResource.toString());
        		
        		// Player HUD
        		Group playerHUD = new Group();
        		ArrayList<Polygon> playerLives = new ArrayList<Polygon>();
        		for(int i = 0; i < livesLeft; i++) {
        			playerLives.add(new Polygon());
        			playerLives.get(i).getPoints().addAll(new Double[]{
        			        0.0, 0.0, 0.0, 7.5, 25.0, 7.5,
        			        25.0, 0.0, 22.5, 0.0, 22.5, -2.5,
        			        15.0, -2.5, 15.0, -5.0, 14.0, -5.0,
        			        14.0, -6.5, 11.0, -6.5, 11.0, -5.0,
        			        10.0, -5.0, 10.0, -2.5, 2.5, -2.5,
        			        2.5, 0.0
        			});
        			playerLives.get(i).setLayoutX(350.0 + (i * 40)); // starting location
        			playerLives.get(i).setLayoutY(20.0); 
        			playerLives.get(i).setFill(Color.GREEN);
        			playerLives.get(i).setStroke(Color.GREEN);
        			playerHUD.getChildren().addAll(playerLives.get(i));
        		}
        		
        		// Add HUD To Group
        		HUD.getChildren().addAll(scoreText, livesText, playerHUD);
        		
        		// Game Boundary
        		Rectangle bottom = new Rectangle(0, 650, 600, 5);
        		bottom.setFill(Color.GREEN);
        		bottom.setStroke(Color.GREEN);
        		
        		// Player
        		Polygon player = new Polygon();
        		player.getPoints().addAll(new Double[]{
        		        0.0, 0.0, 0.0, 15.0, 50.0, 15.0,
        		        50.0, 0.0, 45.0, 0.0, 45.0, -5.0,
        		        30.0, -5.0, 30.0, -10.0, 28.0, -10.0,
        		        28.0, -13.0, 22.0, -13.0, 22.0, -10.0,
        		        20.0, -10.0, 20.0, -5.0, 5.0, -5.0,
        		        5.0, 0.0
        		});
        		player.setLayoutX(273.0); // starting location
        		player.setLayoutY(625.0); 
        		player.setFill(Color.GREEN);
        		player.setStroke(Color.GREEN);
        		//////////////////////////////////////////////////
        		
        		// Player Hitbox
        		Rectangle playerHitbox = new Rectangle(273, 610, 50, 35);
        		// set color to hitboxes
        		playerHitbox.setFill(null);
        		playerHitbox.setStroke(Color.WHITE);
        		// make hitboxes invisible
        		playerHitbox.setVisible(false);
        		
        		// Enemy UFO
        		Group ufo = new Group();
                Enemy enemyUFO = new Enemy();
        		enemyUFO.setUFO();
        		enemyUFO.getEnemy().setLayoutX(100.0); // starting location
        		enemyUFO.getEnemy().setLayoutY(75.0);
        		enemyUFO.getEnemy().setFill(Color.RED);
        		enemyUFO.getEnemy().setStroke(Color.RED);
        		Rectangle ufoWindow1 = new Rectangle(112, 70, 4, 2);
        		ufoWindow1.setFill(Color.BLACK);
        		ufoWindow1.setStroke(Color.BLACK);
        		Rectangle ufoWindow2 = new Rectangle(122, 70, 4, 2);
        		ufoWindow2.setFill(Color.BLACK);
        		ufoWindow2.setStroke(Color.BLACK);
        		Rectangle ufoWindow3 = new Rectangle(134, 70, 4, 2);
        		ufoWindow3.setFill(Color.BLACK);
        		ufoWindow3.setStroke(Color.BLACK);
        		Rectangle ufoWindow4 = new Rectangle(144, 70, 4, 2);
        		ufoWindow4.setFill(Color.BLACK);
        		ufoWindow4.setStroke(Color.BLACK);
        		ufo.getChildren().addAll(enemyUFO.getEnemy(), ufoWindow1, ufoWindow2, ufoWindow3, ufoWindow4);
        		//////////////////////////////////////////////////////////////////////////////////////////////
        		
        		// Enemy UFO Hitbox
        		Rectangle ufoHitbox = new Rectangle(95, 52, 60, 35);
        		// set color to hitboxes
        		ufoHitbox.setFill(null);
        		ufoHitbox.setStroke(Color.WHITE);
        		// make hitboxes invisible
        		ufoHitbox.setVisible(false);
        		
        		// Enemy Squid
        		Group squids = new Group();
        		ArrayList<Enemy> squidsList = new ArrayList<Enemy>();
        		for(int i = 0; i < NUM_INVADERS; i++) {
        			// create squid
        			squidsList.add(new Enemy());
        			squidsList.get(i).setSquid();
        			// set position of squid
        			squidsList.get(i).getEnemy().setLayoutX(80.0 + (i * 40.0));
        			squidsList.get(i).getEnemy().setLayoutY(115.0);
        			// set color of squid
        			squidsList.get(i).getEnemy().setFill(Color.WHITE);
        			squidsList.get(i).getEnemy().setStroke(Color.WHITE);
        			squids.getChildren().addAll(squidsList.get(i).getEnemy());
        		}
        		
        		// Squids Hitboxes
        		squidsHitboxes = new Group();
        		squidsHitboxesList = new ArrayList<Rectangle>();
        		for(int i = 0; i < NUM_INVADERS; i++) { // squids hitboxes
        			squidsHitboxesList.add(new Rectangle(80 + (i * 40), 105, 20, 30));
        			// set color to hitboxes
        			squidsHitboxesList.get(i).setFill(null);
        			squidsHitboxesList.get(i).setStroke(Color.WHITE);
        			// make hitboxes invisible
        			squidsHitboxesList.get(i).setVisible(false);
        			// add hotboxes to hitboxes group
        			squidsHitboxes.getChildren().addAll(squidsHitboxesList.get(i));
        		}
        		
        		// Enemy Brute
        		Group brutes = new Group();
        		ArrayList<Enemy> brutesList = new ArrayList<Enemy>();
        		for(int i = 0; i < NUM_INVADERS; i++) {
        			// create brute
        			brutesList.add(new Enemy());
        			brutesList.get(i).setBrute();
        			// set position of brute
        			brutesList.get(i).getEnemy().setLayoutX(80.0 + (i * 40.0));
        			brutesList.get(i).getEnemy().setLayoutY(150.0);
        			// set color of brute
        			brutesList.get(i).getEnemy().setFill(Color.WHITE);
        			brutesList.get(i).getEnemy().setStroke(Color.WHITE);
        			// add brute to brutes group
        			brutes.getChildren().addAll(brutesList.get(i).getEnemy());
        		}
        		
        		// Brutes Hitboxes
        		brutesHitboxes = new Group();
        		brutesHitboxesList = new ArrayList<Rectangle>();
        		for(int i = 0; i < NUM_INVADERS; i++) { // squids hitboxes
        			brutesHitboxesList.add(new Rectangle(80 + (i * 40), 150, 20, 20));
        			// set color to hitboxes
        			brutesHitboxesList.get(i).setFill(null);
        			brutesHitboxesList.get(i).setStroke(Color.WHITE);
        			// make hitboxes invisible
        			brutesHitboxesList.get(i).setVisible(false);
        			// add hotboxes to hitboxes group
        			brutesHitboxes.getChildren().addAll(brutesHitboxesList.get(i));
        		}
        		
        		// Enemy Plebs
        		Group plebs = new Group();
        		ArrayList<Enemy> plebsList = new ArrayList<Enemy>();
        		for(int i = 0; i < NUM_INVADERS; i++) {
        			// create pleb
        			plebsList.add(new Enemy());
        			plebsList.get(i).setPleb();
        			// set position of pleb
        			plebsList.get(i).getEnemy().setLayoutX(80.0 + (i * 40.0));
        			plebsList.get(i).getEnemy().setLayoutY(185.0);
        			// set color of pleb
        			plebsList.get(i).getEnemy().setFill(Color.WHITE);
        			plebsList.get(i).getEnemy().setStroke(Color.WHITE);
        			// add pleb to plebs group
        			plebs.getChildren().addAll(plebsList.get(i).getEnemy());
        		}
        		
        		// Plebs Hitboxes
        		plebsHitboxes = new Group();
        		plebsHitboxesList = new ArrayList<Rectangle>();
        		for(int i = 0; i < NUM_INVADERS; i++) { // squids hitboxes
        			plebsHitboxesList.add(new Rectangle(80 + (i * 40), 180, 20, 25));
        			// set color to hitboxes
        			plebsHitboxesList.get(i).setFill(null);
        			plebsHitboxesList.get(i).setStroke(Color.WHITE);
        			// make hitboxes invisible
        			plebsHitboxesList.get(i).setVisible(false);
        			// add hitboxes to hitboxes group
        			plebsHitboxes.getChildren().addAll(plebsHitboxesList.get(i));
        		}
        		
        		// Player Bullet
        		Rectangle playerBullet = new Rectangle(123, 600, 3, 6);
        		playerBullet.setFill(Color.WHITE);
        		playerBullet.setStroke(Color.WHITE);
        		
        		// Add Everything To Pane
        		gamePane.getChildren().addAll(HUD, bottom, player, playerBullet, 
        				plebsHitboxes, brutesHitboxes, squidsHitboxes, playerHitbox, ufoHitbox,
        				ufo, squids, brutes, plebs);
 
        		// Set Scene
        		gameScene = new Scene(gamePane, 600, 700);
        		gameStage.setTitle("Space_Invaders");
        		gameStage.setScene(gameScene);
        		gameStage.setMinWidth(615);
        		gameStage.setMinHeight(750);
        		gameStage.setMaxWidth(615);
        		gameStage.setMaxHeight(750);
        		gameStage.show();
        		
        		/* Move Enemies */
        		moveEnemies(plebsHitboxesList, squidsHitboxesList, brutesHitboxesList, 
        						plebs, squids, brutes);
        		
        		/* Move UFO */
        		moveUFO(ufo, ufoHitbox);
        		
        		/* Enemy bullets */
        		// instantiate 
        		for(int i=0;i<plebsList.size();i++) {
        			plebsList.get(i).setBullet();
        			brutesList.get(i).setBullet();
        			squidsList.get(i).setBullet();
        			
        			gamePane.getChildren().add(plebsList.get(i).getBullet());
        			gamePane.getChildren().add(brutesList.get(i).getBullet());
        			gamePane.getChildren().add(squidsList.get(i).getBullet());
        		}
        		enemyBoolets(squidsList, brutesList, plebsList, player, scoreText);
        		
        		// Handle Controls
        		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
        			@Override public void handle(KeyEvent event) {
        				switch (event.getCode()) {
        					case RIGHT: 
        						player.setLayoutX(player.getLayoutX() + 5);
        						playerHitbox.setLayoutX(playerHitbox.getLayoutX() + 5);
        						if(player.getLayoutX() >= 550.0) {
        							player.setLayoutX(550.0); 
        						}
        						if(playerHitbox.getLayoutX() >= 277.0) {
        							playerHitbox.setLayoutX(277.0);
        						}
        						break;
        					case LEFT:  
        						player.setLayoutX(player.getLayoutX() - 5);
        						playerHitbox.setLayoutX(playerHitbox.getLayoutX() - 5);
        						if(player.getLayoutX() <= 0.0) {
        							player.setLayoutX(0.0);
        						}
        						if(playerHitbox.getLayoutX() <= -273.0) {
        							playerHitbox.setLayoutX(-273.0);
        						}
        						break;
        					case DOWN:
        						audioShoot.play();
        						resetBulletPosition(playerBullet, player);
        						shootPlayerBullet(playerBullet, player, ufoHitbox, plebsHitboxesList, 
								          			squidsHitboxesList, brutesHitboxesList, ufo, plebsList,
								          			squidsList, brutesList, scoreText);
        						break;
        					case SPACE:	
        						audioShoot.play();
        						resetBulletPosition(playerBullet, player);
        						shootPlayerBullet(playerBullet, player, ufoHitbox, plebsHitboxesList, 
					          			squidsHitboxesList, brutesHitboxesList, ufo, plebsList,
					          			squidsList, brutesList, scoreText);
        						break;
        					default:
        						break;
        				}
        			}
        		});
            });
            /////////////////////////////////////////////////////////////////////////////////
            
            
            // Game Menu Option Button
            MenuButton btnOptions = new MenuButton("OPTIONS");
            btnOptions.setOnMouseClicked(event -> {
                getChildren().add(menu1);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
                tt.setToX(menu0.getTranslateX() - offset);
                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
                tt1.setToX(menu0.getTranslateX());
                tt.play();
                tt1.play();

                tt.setOnFinished(evt -> {
                    getChildren().remove(menu0);
                });
            });
            
            // Game Exit Option Button
            MenuButton btnExit = new MenuButton("EXIT");
            btnExit.setOnMouseClicked(event -> {
                System.exit(0);
            });
            
            // Add Buttons to Menu
            menu0.getChildren().addAll(btnNewGame, btnOptions, btnExit);
            
            // Set Menu Background
            Rectangle bg = new Rectangle(800, 600);
            bg.setFill(Color.GREY);
            bg.setOpacity(0.4);
            
            // Add Background and Menu to GameMenu
            getChildren().addAll(bg, menu0);
        }
	}

    private class Enemy {
	    Polygon enemy;
	    
	    /* Enemy Bullet */
		Rectangle enemyBullet;
		
	
	    /* Default Constructor */
	    Enemy() {
	        
	    }
	    
	    private void setBullet() {
	    	this.enemyBullet = new Rectangle(enemy.getTranslateX(), enemy.getTranslateY(), 3, 6);
	    	this.enemyBullet.setFill(Color.RED);
	    	this.enemyBullet.setStroke(Color.RED);
	    }
	    
	    private Rectangle getBullet() {
	    	return enemyBullet;
	    }
	    
	    private void setSquid() {
	    	
	        enemy = new Polygon();
	        enemy.getPoints().addAll(new Double[] {
	                0.0, 0.0, 0.0, 8.0, 8.0, 8.0,
	                8.0, 12.0, 6.0, 12.0, 0.0, 18.0,
	                8.0, 12.0, 8.0, 18.0, 10.0, 12.0,
	                12.0, 18.0, 12.0, 12.0, 20.0, 18.0,
	                12.0, 12.0, 12.0, 8.0, 20.0, 8.0,
	                20.0, 0.0, 16.0, 0.0, 16.0, -4.0,
	                12.0, -4.0, 12.0, -8.0, 8.0, -8.0,
	                8.0, -4.0, 4.0, -4.0, 4.0, 0.0
	        });
	    }
	    
	    private void setBrute() {
	        enemy = new Polygon();
	        enemy.getPoints().addAll(new Double[] {
	                0.0, 0.0, 0.0, 10.0, 4.0, 10.0,
	                4.0, 14.0, 8.0, 14.0, 0.0, 20.0,
	                8.0, 14.0, 12.0, 14.0, 20.0, 20.0,
	                12.0, 14.0, 16.0, 14.0, 16.0, 10.0,
	                20.0, 10.0, 20.0, 0.0, 18.0, 0.0,
	                18.0, 8.0, 16.0, 8.0, 14.0, 8.0,
	                14.0, 4.0, 12.0, 4.0, 15.0, 0.0,
	                12.0, 4.0, 8.0, 4.0, 5.0, 0.0,
	                8.0, 4.0, 6.0, 4.0, 6.0, 8.0,
	                2.0, 8.0, 2.0, 0.0
	        });
	    }
	    
	    private void setPleb() {
	        enemy = new Polygon();
	        enemy.getPoints().addAll(new Double[] {
	                0.0, 0.0, 0.0, 10.0, 6.0, 10.0,
	                0.0, 20.0, 2.0, 20.0, 8.0, 10.0,
	                8.0, 15.0, 12.0, 15.0, 12.0, 10.0,
	                18.0, 20.0, 20.0, 20.0, 14.0, 10.0,
	                20.0, 10.0, 20.0, 0.0, 16.0, 0.0,
	                16.0, -2.0, 4.0, -2.0, 4.0, 0.0
	        });
	    }
	
	    private void setUFO() {
	        enemy = new Polygon();
	        enemy.getPoints().addAll(new Double[]{
	                0.0, 0.0, 0.0, 4.0, 8.0, 4.0,
	                8.0, 8.0, 12.0, 8.0, 12.0, 12.0,
	                16.0, 12.0, 16.0, 8.0, 20.0, 8.0,
	                20.0, 4.0, 28.0, 4.0, 28.0, 8.0,
	                32.0, 8.0, 32.0, 4.0, 40.0, 4.0,
	                40.0, 8.0, 44.0, 8.0, 44.0, 12.0,
	                46.0, 12.0, 46.0, 8.0, 50.0, 8.0,
	                50.0, 4.0, 60.0, 4.0, 60.0, 0.0,
	                56.0, 0.0, 56.0, -4.0, 52.0, -4.0,
	                52.0, -8.0, 48.0, -8.0, 48.0, -12.0,
	                40.0, -12.0, 40.0, -16.0, 20.0, -16.0,
	                20.0, -12.0, 12.0, -12.0, 12.0, -8.0,
	                8.0, -8.0, 8.0, -4.0, 4.0, -4.0,
	                4.0, 0.0, 0.0, 0.0
	        });
	    }
	    
	    private Polygon getEnemy() {
	        return enemy;
	    }
    }
    
    private void resetBulletPosition(Rectangle playerBullet, Polygon player) {
    	playerBullet.setLayoutX(player.getLayoutX() - player.getTranslateX() - 100);
    	playerBullet.setLayoutY(player.getLayoutY() - player.getTranslateY() - 600);
    }
    
    private void shootPlayerBullet(Rectangle playerBullet, Polygon player, Rectangle ufoHitbox, 
    		ArrayList<Rectangle> plebsHitboxesList, ArrayList<Rectangle> squidsHitboxesList, 
    		ArrayList<Rectangle> brutesHitboxesList, Group ufo, 
    		ArrayList<Enemy> plebsList, ArrayList<Enemy> squidsList, 
    		ArrayList<Enemy> brutesList, Text scoreText) {
    	seconds = 0;
    	TimerTask task = new TimerTask() {
            private final int MAX_SECONDS = 100;
            boolean firstRun = true;
            @Override
            public void run() { 
            	if(firstRun == true) {
            		playerBullet.setTranslateY(playerBullet.getLayoutY() - 20);
            		firstRun = false;
            	} else {
	                if (seconds < MAX_SECONDS) {
	                	playerBullet.setTranslateY(playerBullet.getTranslateY() - 10);
//	                    System.out.println("Seconds = " + seconds);
	                    
//	                	/* Bullet Location Value Testing */
//	                	System.out.println("X: " + playerBullet.getLayoutX() +
//	                						 " Y: " + playerBullet.getTranslateY());
	                	
	                    /* Collision Detection */
	                	if(ufoHitbox.getBoundsInParent().intersects(playerBullet.getBoundsInParent())){
                    		System.out.println("UFO collission detected");
                    		ufoHitbox.setLayoutX(5000);
                    		ufo.setVisible(false);
                    		playerBullet.setLayoutY(-5000);
                    		invaderKilledSound.play();
                    		score += 50;
                    		scoreText.setText("SCORE: " + score);
	                	}
	                	for(int i=0;i<plebsHitboxesList.size();i++) {
	                    	if(plebsHitboxesList.get(i).getBoundsInParent().intersects(playerBullet.getBoundsInParent())){
		                    		System.out.println("Pleb collission detected");
		                    		plebsHitboxesList.get(i).setLayoutX(5000);
		                    		plebsList.get(i).getEnemy().setVisible(false);
		                    		playerBullet.setLayoutY(-5000);
		                    		invaderKilledSound.play();
		                    		invaderCount--;
		                    		score += 10;
		                    		scoreText.setText("SCORE: " + score);
		                    		if(invaderCount == 0) {
		                    			scoreText.setText("YOU WIN");
			                    		enemyTimer.cancel();
			                			enemyBulletTimer.cancel();
			                			enemyBulletInterval.cancel();
			                			ufoTimer.cancel();
		                    		}
		                    }
		                }
	                	
	                	for(int i=0;i<squidsHitboxesList.size();i++) {
	                    	if(squidsHitboxesList.get(i).getBoundsInParent().intersects(playerBullet.getBoundsInParent())){
		                    		System.out.println("Squid collission detected");
		                    		squidsHitboxesList.get(i).setLayoutX(5000);
		                    		squidsList.get(i).getEnemy().setVisible(false);
		                    		playerBullet.setLayoutY(-5000);
		                    		invaderKilledSound.play();
		                    		invaderCount--;
		                    		score += 30;
		                    		scoreText.setText("SCORE: " + score);
		                    		if(invaderCount == 0) {
		                    			scoreText.setText("YOU WIN");
			                    		enemyTimer.cancel();
			                			enemyBulletTimer.cancel();
			                			enemyBulletInterval.cancel();
			                			ufoTimer.cancel();
		                    		}
		                    }
		                }
	                	
	                	for(int i=0;i<brutesHitboxesList.size();i++) {
	                    	if(brutesHitboxesList.get(i).getBoundsInParent().intersects(playerBullet.getBoundsInParent())){
		                    		System.out.println("Brute collission detected");
		                    		brutesHitboxesList.get(i).setLayoutX(5000);
		                    		brutesList.get(i).getEnemy().setVisible(false);
		                    		playerBullet.setLayoutY(-5000);
		                    		invaderKilledSound.play();
		                    		invaderCount--;
		                    		score += 20;
		                    		scoreText.setText("SCORE: " + score);
		                    		if(invaderCount == 0) {
		                    			scoreText.setText("YOU WIN");
			                    		enemyTimer.cancel();
			                			enemyBulletTimer.cancel();
			                			enemyBulletInterval.cancel();
			                			ufoTimer.cancel();
		                    		}
		                    }
		                }
	                    
	                    seconds++;
	                } else {
	                    // stop the timer
	                    cancel();
	                }
            	}
            }
        };
        timer.schedule(task, 0, 25);
    }
    
    
    
    public void moveEnemies(ArrayList<Rectangle> plebsHitboxesList, ArrayList<Rectangle> squidsHitboxesList, 
    							ArrayList<Rectangle> brutesHitboxesList, Group plebs, Group squids, Group brutes) {
    	final int dropDownAmount = 35;
    	final int moveAmount = 20;
    	final int MAX_ENEMY_SECONDS = 1500;
    	TimerTask task = new TimerTask() {
    		int enemySeconds = 0;
    		boolean moveLeft = true;
    		boolean dropDown = false;
    		int audioCount = 4;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(enemySeconds < MAX_ENEMY_SECONDS && invaderCount != 0) {
					switch(audioCount) {
						case 4:
							invaderSound4.play();
							audioCount--;
							break;
						case 3:
							invaderSound3.play();
							audioCount--;
							break;
						case 2:
							invaderSound2.play();
							audioCount--;
							break;
						case 1:
							invaderSound1.play();
							audioCount = 4;
							break;
					}
					if(squids.getTranslateX() >= -50 && moveLeft == true) {
						if(dropDown == true) {
							for(int i=0;i<squidsHitboxesList.size();i++) {
								squidsHitboxesList.get(i).setTranslateX(squidsHitboxesList.get(i).getTranslateX() - moveAmount);
								plebsHitboxesList.get(i).setTranslateX(plebsHitboxesList.get(i).getTranslateX() - moveAmount);
								brutesHitboxesList.get(i).setTranslateX(brutesHitboxesList.get(i).getTranslateX() - moveAmount);
								
								squidsHitboxesList.get(i).setTranslateY(squidsHitboxesList.get(i).getTranslateY() + dropDownAmount);
								plebsHitboxesList.get(i).setTranslateY(plebsHitboxesList.get(i).getTranslateY() + dropDownAmount);
								brutesHitboxesList.get(i).setTranslateY(brutesHitboxesList.get(i).getTranslateY()  + dropDownAmount);
							}
							squids.setTranslateX(squids.getTranslateX() - moveAmount);
							plebs.setTranslateX(plebs.getTranslateX() - moveAmount);
							brutes.setTranslateX(brutes.getTranslateX() - moveAmount);
							
							squids.setTranslateY(squids.getTranslateY() + dropDownAmount);
							plebs.setTranslateY(plebs.getTranslateY() + dropDownAmount);
							brutes.setTranslateY(brutes.getTranslateY() + dropDownAmount);
							
							dropDown = false;
						} else {
							for(int i=0;i<squidsHitboxesList.size();i++) {
								squidsHitboxesList.get(i).setTranslateX(squidsHitboxesList.get(i).getTranslateX() - moveAmount);
								plebsHitboxesList.get(i).setTranslateX(plebsHitboxesList.get(i).getTranslateX() - moveAmount);
								brutesHitboxesList.get(i).setTranslateX(brutesHitboxesList.get(i).getTranslateX() - moveAmount);
							}
							squids.setTranslateX(squids.getTranslateX() - moveAmount);
							plebs.setTranslateX(plebs.getTranslateX() - moveAmount);
							brutes.setTranslateX(brutes.getTranslateX() - moveAmount);
						}
					} else if(squids.getTranslateX() <= -50) {
						for(int i=0;i<squidsHitboxesList.size();i++) {
							squidsHitboxesList.get(i).setTranslateX(squidsHitboxesList.get(i).getTranslateX() + moveAmount);
							plebsHitboxesList.get(i).setTranslateX(plebsHitboxesList.get(i).getTranslateX() + moveAmount);
							brutesHitboxesList.get(i).setTranslateX(brutesHitboxesList.get(i).getTranslateX() + moveAmount);
							
							squidsHitboxesList.get(i).setTranslateY(squidsHitboxesList.get(i).getTranslateY() + dropDownAmount);
							plebsHitboxesList.get(i).setTranslateY(plebsHitboxesList.get(i).getTranslateY() + dropDownAmount);
							brutesHitboxesList.get(i).setTranslateY(brutesHitboxesList.get(i).getTranslateY()  + dropDownAmount);
						}
						squids.setTranslateX(squids.getTranslateX() + moveAmount);
						plebs.setTranslateX(plebs.getTranslateX() + moveAmount);
						brutes.setTranslateX(brutes.getTranslateX() + moveAmount);
						
						squids.setTranslateY(squids.getTranslateY() + dropDownAmount);
						plebs.setTranslateY(plebs.getTranslateY() + dropDownAmount);
						brutes.setTranslateY(brutes.getTranslateY() + dropDownAmount);
						
						moveLeft = false;
					}
					if(moveLeft == false && squids.getTranslateX() <= (NUM_INVADERS * 9)) {
						for(int i=0;i<squidsHitboxesList.size();i++) {
							squidsHitboxesList.get(i).setTranslateX(squidsHitboxesList.get(i).getTranslateX() + moveAmount);
							plebsHitboxesList.get(i).setTranslateX(plebsHitboxesList.get(i).getTranslateX() + moveAmount);
							brutesHitboxesList.get(i).setTranslateX(brutesHitboxesList.get(i).getTranslateX() + moveAmount);
						}
						squids.setTranslateX(squids.getTranslateX() + moveAmount);
						plebs.setTranslateX(plebs.getTranslateX() + moveAmount);
						brutes.setTranslateX(brutes.getTranslateX() + moveAmount);
					} else if(moveLeft == false && squids.getTranslateX() >= (NUM_INVADERS * 9)) {
						for(int i=0;i<squidsHitboxesList.size();i++) {
							squidsHitboxesList.get(i).setTranslateX(squidsHitboxesList.get(i).getTranslateX() + 20);
							plebsHitboxesList.get(i).setTranslateX(plebsHitboxesList.get(i).getTranslateX() + 20);
							brutesHitboxesList.get(i).setTranslateX(brutesHitboxesList.get(i).getTranslateX() + 20);
						}
						squids.setTranslateX(squids.getTranslateX() + moveAmount);
						plebs.setTranslateX(plebs.getTranslateX() + moveAmount);
						brutes.setTranslateX(brutes.getTranslateX() + moveAmount);
						moveLeft = true;
						dropDown = true;
					}
					enemySeconds++;
				} else {
					cancel();
				}
			}
    	};
    	enemyTimer.schedule(task,  0, 1000);
    }
    
    public void moveUFO(Group ufo, Rectangle ufoHitbox) {
    	ufoSound.play();
    	TimerTask task = new TimerTask() {
            @Override
            public void run() { 
            	if(ufo.getTranslateX() <= 400) {
            		ufo.setTranslateX(ufo.getTranslateX() + 5);
            		ufoHitbox.setTranslateX(ufo.getTranslateX() + 5);
            		
            	} else {
            		ufo.setTranslateX(-150);
            		ufoHitbox.setTranslateX(-150);
//            		ufoSound.play();
            	}
            }
        };
        ufoTimer.schedule(task,  0, 100);
    }
    
    public void resetEnemyBullets(ArrayList<Enemy> squidsList, ArrayList<Enemy> brutesList, 
    									ArrayList<Enemy> plebsList) {
    	for(int i=1;i<squidsList.size();i++) {
    		squidsList.get(i).getBullet().setTranslateX(Math.random() * 900 + 1);
    		squidsList.get(i).getBullet().setTranslateY(0);
    		
    		brutesList.get(i).getBullet().setTranslateX(Math.random() * 900 + 1);
    		brutesList.get(i).getBullet().setTranslateY(0);
    		
    		plebsList.get(i).getBullet().setTranslateX(Math.random() * 900 + 1);
    		brutesList.get(i).getBullet().setTranslateY(0);
    	}
    }
    
    public void shootEnemyBullets(ArrayList<Enemy> squidsList, ArrayList<Enemy> brutesList, 
    									ArrayList<Enemy> plebsList, Polygon player, Text scoreText) {
    	TimerTask task = new TimerTask() {
            @Override
            public void run() { 
            	for(int i=1;i<NUM_INVADERS;i++) {
            		squidsList.get(i).getBullet().setTranslateY(
            				squidsList.get(i).getBullet().getTranslateY() + 2);
            		if(player.getBoundsInParent().intersects(squidsList.get(i).getBullet().getBoundsInParent())) {
            			System.out.println("Player collision detected");
            			/* Game Over, Set the enter high score scene */
            			enemyTimer.cancel();
            			enemyBulletTimer.cancel();
            			enemyBulletInterval.cancel();
            			ufoTimer.cancel();
            			System.out.println("Game Over");
            			scoreText.setText("Game Over");
            		}
            		
            		brutesList.get(i).getBullet().setTranslateY(
            				brutesList.get(i).getBullet().getTranslateY() + 2);
            		if(player.getBoundsInParent().intersects(brutesList.get(i).getBullet().getBoundsInParent())) {
            			System.out.println("Player collision detected");
            			/* Game Over, Set the enter high score scene */
            			enemyTimer.cancel();
            			enemyBulletTimer.cancel();
            			enemyBulletInterval.cancel();
            			ufoTimer.cancel();
            			System.out.println("Game Over");
            			scoreText.setText("Game Over");
            		}
            		
            		plebsList.get(i).getBullet().setTranslateY(
            				plebsList.get(i).getBullet().getTranslateY() + 2);
            		if(player.getBoundsInParent().intersects(plebsList.get(i).getBullet().getBoundsInParent())) {
            			System.out.println("Player collision detected");
            			/* Game Over, Set the enter high score scene */
            			enemyTimer.cancel();
            			enemyBulletTimer.cancel();
            			enemyBulletInterval.cancel();
            			ufoTimer.cancel();
            			System.out.println("Game Over");
            			scoreText.setText("Game Over");
            		}
            	}
            }
        };
        enemyBulletTimer.schedule(task,  0, 25);
    }
    
    public void enemyBoolets(ArrayList<Enemy> squidsList, ArrayList<Enemy> brutesList, 
								ArrayList<Enemy> plebsList, Polygon player, Text scoreText) {
    	TimerTask task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				resetEnemyBullets(squidsList, brutesList, plebsList);
				shootEnemyBullets(squidsList, brutesList, plebsList, player, scoreText);
			}
    		
    	};
    	enemyBulletInterval.schedule(task,  0, 4500);
    }
}