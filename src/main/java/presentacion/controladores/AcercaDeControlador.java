package presentacion.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AcercaDeControlador implements Initializable {

	@FXML
	private Label labelContenido;
	
	@FXML
	private Button btnCerrar;

	@FXML
	void cerrar(MouseEvent event) {
		Stage stage = (Stage) btnCerrar.getScene().getWindow();
		stage.close();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.labelContenido.setText("Integrantes del equipo de Desarrollo :\n Rojas, Julio Cesar. E-mail: juliocesarrojas3@gmail.com \n Almiron, Carlos. E-mail: carlos.raul.almiron@ungs.edu.ar \n Vargas, Lucas. E-mail: lucasjv92@gmail.com \n Lucero Correo, Maximiliano. E-mail: mnlucerocorrea@gmail.com \n Tomas, Lautaro. E-mail: lauta159@gmail.com ");
	}

}
