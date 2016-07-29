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

public class SpaceInvadersCollisionTest extends Application {
	private int livesLeft = 3;
	private int score = 0;
	private Timer timer = new Timer();
    private int seconds = 0;
    
	
	URL resource = getClass().getResource("assets/MenuMusic.wav");
	AudioClip mainMenuMusic = new AudioClip(resource.toString());
	
	private Game gameMenu;
	private Pane gamePane;
	private Scene gameScene;
    private Stage gameStage;

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
        		
        		Rectangle hitbox = new Rectangle(150, 150, 25, 25);
        		hitbox.setFill(Color.WHITE);
        		hitbox.setStroke(Color.WHITE);
        		
        		// Player Bullet
        		Rectangle playerBullet = new Rectangle(123, 600, 3, 6);
        		playerBullet.setFill(Color.WHITE);
        		playerBullet.setStroke(Color.WHITE);
        		
        		// Add Everything To Pane
        		gamePane.getChildren().addAll(HUD, bottom, player, playerBullet);
        		gamePane.getChildren().add(hitbox);
        		
        		// Set Scene
        		gameScene = new Scene(gamePane, 600, 700);
        		gameStage.setTitle("Space_Invaders");
        		gameStage.setScene(gameScene);
        		gameStage.setMinWidth(615);
        		gameStage.setMinHeight(750);
        		gameStage.setMaxWidth(615);
        		gameStage.setMaxHeight(750);
        		gameStage.show();
        		
        		// Handle Controls
        		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
        			@Override public void handle(KeyEvent event) {
        				switch (event.getCode()) {
        					case RIGHT: 
        						player.setLayoutX(player.getLayoutX() + 5);
        						if(player.getLayoutX() >= 550.0) player.setLayoutX(550.0);
        						break;
        					case LEFT:  
        						player.setLayoutX(player.getLayoutX() - 5);
        						if(player.getLayoutX() <= 0.0) player.setLayoutX(0.0);
        						break;
        					case DOWN:	
        						break;
        					case SPACE:	
        						audioShoot.play();
        						resetBulletPosition(playerBullet, player);
        						shootPlayerBullet(playerBullet, hitbox);
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
	
	    /* Default Constructor */
	    Enemy() {
	        
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
    
    private void shootPlayerBullet(Rectangle playerBullet, Rectangle hitbox) {
    	seconds = 0;
    	timer = new Timer();
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
//	                	System.out.println("X: " + (playerBullet.getLayoutX() - playerBullet.getTranslateX()) +
//	                						 " Y: " + playerBullet.getTranslateY());
	                	
	                    /* Collision Detection */
	                    if(hitbox.contains(playerBullet.getLayoutX() - playerBullet.getTranslateX(),
	                    					playerBullet.getLayoutY() - playerBullet.getTranslateY())) {
	                    	System.out.println("Collision detected you faggot");
	                    }
	                    
	                    seconds++;
	                } else {
	                    // stop the timer
	                    cancel();
	                }
            	}
            }
        };
        timer.schedule(task, 0, 10);
    }
}