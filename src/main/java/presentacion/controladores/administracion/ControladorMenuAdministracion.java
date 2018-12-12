package presentacion.controladores.administracion;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import presentacion.controladores.ControladorDeposito;

public class ControladorMenuAdministracion {

	private Object controladorActual;
	@FXML
	private BorderPane borderpanel;
	@FXML
	private Button btnDepositos;
	@FXML
	private Button btnTecnicos;
	@FXML
	private Button btnbackup;
	@FXML
	private Button btnmail;
	@FXML
	private Button btnUsuario1;
	@FXML
	private Button btnUbicaciones;

	@FXML
	void cambiarPanelUsuario(MouseEvent event) {
		borderpanel.setCenter(null);
		this.loadUI("/vistas/administracion/abm_usuario");
		ControladorUsuarioABM controlador = (ControladorUsuarioABM) this.controladorActual;
	}

	@FXML
	void cambiarPanelUbicacion(MouseEvent event) {
		borderpanel.setCenter(null);
		this.loadUI("/vistas/administracion/abm_Ubicacion");
		ControladorABMUbicaciones controlador = (ControladorABMUbicaciones) this.controladorActual;
	}

	@FXML
	void abrirDeposito(MouseEvent event) {
		borderpanel.setCenter(null);
		this.loadUI("/vistas/menudeposito");
		ControladorDeposito controlador = (ControladorDeposito) this.controladorActual;
	}

	@FXML
	void abrirTecnicos(MouseEvent event) {
		this.loadUI("/vistas/tecnicos/tablaABMTecnicos");
	}

	@FXML
	void cambiarPanelBackup(MouseEvent event) {
		borderpanel.setCenter(null);
		this.loadUI("/vistas/administracion/backupVista");
	}

	@FXML
	void cambiarPanelMail(MouseEvent event) {
		borderpanel.setCenter(null);
		this.loadUI("/vistas/administracion/mailNew");
	}

	private void loadUI(String ui) {
		FXMLLoader root = null;
		try {
			root = new FXMLLoader(getClass().getResource(ui + ".fxml"));
			Scene scene = new Scene(root.load()); // (root)
			this.controladorActual = root.getController();
			borderpanel.setCenter(scene.getRoot());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}