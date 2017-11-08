package menu;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import game.GameController;
import game.Player;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameMenu extends Application {

	private GM gm;

	@Override
	public void start(Stage primaryStage) throws Exception {

		Pane root = new Pane();
		root.setPrefSize(800, 600);

		InputStream is = Files.newInputStream(Paths.get("res/images/battleships.jpg"));
		Image img = new Image(is);
		is.close();

		ImageView imgView = new ImageView(img);
		imgView.setFitWidth(800);
		imgView.setFitHeight(600);

		gm = new GM();

		root.getChildren().addAll(imgView, gm);

		Scene scene = new Scene(root);

		scene.setOnKeyPressed(event -> {

			if (event.getCode() == KeyCode.ESCAPE) {
				if (!gm.isVisible()) {
					FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gm);
					ft.setFromValue(0);
					ft.setToValue(1);
					gm.setVisible(true);
					ft.play();
				} else {
					FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gm);
					ft.setFromValue(1);
					ft.setToValue(0);
					ft.setOnFinished(evt -> gm.setVisible(false));
					ft.play();
				}
			}
		});

		primaryStage.setTitle("Battleships");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private class GM extends Parent {
		public GM() {
			
			VBox menu0 = new VBox(15);
			VBox menu1 = new VBox(15);

			menu0.setTranslateX(50);
			menu0.setTranslateY(220);
			menu1.setTranslateX(50);
			menu1.setTranslateY(220);

			final int offset = 400;

			menu1.setTranslateX(offset);
			
			MenuButton btnSinglePlayer = new MenuButton(" SINGLEPLAYER");
			btnSinglePlayer.setOnMouseClicked(event -> {
				Thread t1 = new Thread(new Runnable(){
					@Override
					public void run() {
						new GameController(new Player("Player 1"), new Player("Computer"));
					}
				});
				t1.start();
			});
			MenuButton btnMultiPlayer = new MenuButton(" MULTIPLAYER");

			MenuButton btnResume = new MenuButton(" RESUME");
			btnResume.setOnMouseClicked(event -> {
				FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
				ft.setFromValue(1);
				ft.setToValue(0);
				ft.setOnFinished(evt -> this.setVisible(false));
				ft.play();
			});

			MenuButton btnOptions = new MenuButton(" OPTIONS");
			btnOptions.setOnMouseClicked(event -> {
				this.getChildren().add(menu1);
				TranslateTransition tt0 = new TranslateTransition(Duration.seconds(0.25), menu0);
				tt0.setToX(menu0.getTranslateX() - offset);

				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
				tt1.setToX(menu0.getTranslateX());

				tt0.play();
				tt1.play();

				tt0.setOnFinished(evt -> {
					this.getChildren().remove(menu0);
				});
			});

			MenuButton btnExit = new MenuButton(" EXIT");
			btnExit.setOnMouseClicked(event -> {
				System.exit(0);
			});

			MenuButton btnBack = new MenuButton(" BACK");
			btnBack.setOnMouseClicked(event -> {
				this.getChildren().add(menu0);
				TranslateTransition tt0 = new TranslateTransition(Duration.seconds(0.25), menu1);
				tt0.setToX(menu1.getTranslateX() - offset);

				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
				tt1.setToX(menu1.getTranslateX());

				tt0.play();
				tt1.play();

				tt0.setOnFinished(evt -> {
					this.getChildren().remove(menu1);
				});
			});

			MenuButton btnSound = new MenuButton(" SOUND");
			MenuButton btnVideo = new MenuButton(" VIDEO");

			menu0.getChildren().addAll(btnSinglePlayer, btnMultiPlayer, btnResume, btnOptions, btnExit);
			menu1.getChildren().addAll(btnBack, btnSound, btnVideo);

			Rectangle bg = new Rectangle(800, 600);
			bg.setFill(Color.GRAY);
			bg.setOpacity(0.35);

			this.getChildren().addAll(bg, menu0);
		}

	}

	private static class MenuButton extends StackPane {
		private Text text;

		public MenuButton(String name) {
			text = new Text(name);
			text.setFont(Font.font(20));
			text.setFill(Color.WHITE);

			Rectangle bg = new Rectangle(250, 30);
			bg.setOpacity(0.6);
			bg.setFill(Color.BLACK);
			bg.setEffect(new GaussianBlur(3.5));

			this.setAlignment(Pos.CENTER_LEFT);
			this.setRotate(-0.5);
			this.getChildren().addAll(bg, text);

			this.setOnMouseEntered(event -> {
				bg.setTranslateX(10);
				text.setTranslateX(10);
				bg.setFill(Color.WHITE);
				text.setFill(Color.BLACK);
			});

			this.setOnMouseExited(event -> {
				bg.setTranslateX(0);
				text.setTranslateX(0);
				bg.setFill(Color.BLACK);
				text.setFill(Color.WHITE);
			});

			DropShadow drop = new DropShadow(50, Color.WHITE);
			drop.setInput(new Glow());

			this.setOnMousePressed(event -> setEffect(drop));
			this.setOnMouseReleased(event -> setEffect(null));
		}
	}

	public static void main(String args[]) {
		launch(args);
	}
}