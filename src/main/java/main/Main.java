package main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Main extends Application implements Initializable{
	
	@FXML
    private ProgressBar progressBar;
	
	@FXML
	private Label labeltext;
	

	@Override
	public void start(Stage stage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/vistas/principal/Splashscreen.fxml"));
		Scene scene = new Scene(root);
		scene.setFill(Color.TRANSPARENT);
		stage.setScene(scene);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Timeline timer = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
			labeltext.setText(">Cargando Insumos");
			progressBar.setProgress(0.2);
			
		}));
		Timeline timer2 = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
			labeltext.setText(">Cargando Herramientas");
			progressBar.setProgress(0.5);
			
		}));
		Timeline timer3 = new Timeline(new KeyFrame(Duration.seconds(7), event -> {
			labeltext.setText(">Conectandose con Mantis Bug Tracker");
			progressBar.setProgress(0.7);
			
		}));
		Timeline timer4 = new Timeline(new KeyFrame(Duration.seconds(9), event -> {
			labeltext.setText(">Actualizando Ordenes De Trabajo");
			progressBar.setProgress(1.0);
			
		}));
		Timeline timer5 = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
			Parent root;
			try { 
				root = FXMLLoader.load(getClass().getResource("/vistas/principal/principal3.fxml"));
				progressBar.getScene().setRoot(root);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}));
		timer.play();
		timer2.play();
		timer3.play();
		timer4.play();
		timer5.play();
	}
}