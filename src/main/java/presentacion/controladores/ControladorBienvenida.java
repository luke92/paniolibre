package presentacion.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControladorBienvenida {

    @FXML
    private Label saludo;

	public Label getSaludo() {
		return saludo;
	}

	public void setSaludo(Label saludo) {
		this.saludo = saludo;
	}

}
