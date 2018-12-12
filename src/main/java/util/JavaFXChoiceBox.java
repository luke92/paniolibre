package util;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import persistencia.dao.mysql.DAOSQLFactory;
import services.TecnicoService;

public class JavaFXChoiceBox extends Application {
	TecnicoService tecnicoService = new TecnicoService(new DAOSQLFactory());

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void start(Stage primaryStage) {
		Label label1 = new Label();
		Label label2 = new Label();

		ChoiceBox choiceBox = new ChoiceBox();
		for (int i = 0; i < tecnicoService.obtenerTecnicos().size(); i++) {
			choiceBox.getItems().add(tecnicoService.obtenerTecnicos().get(i));
		}

		choiceBox.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
					label1.setText((String) newValue.toString());
				});

		Button btn = new Button();
		btn.setText("Say 'Hello World'");
		btn.setOnAction((ActionEvent event) -> {
			// TODO Completar o quitar este metodo
		});

		VBox root = new VBox();
		root.getChildren().addAll(choiceBox, btn, label1, label2);

		Scene scene = new Scene(root, 300, 250);
		btn.fire();
		btn.fire();
		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}