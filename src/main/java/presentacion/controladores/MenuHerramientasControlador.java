package presentacion.controladores;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import presentacion.controladores.categorias.ControladorCategoriasABM;
import util.TipoCategoria;

public class MenuHerramientasControlador {

	private Object ControladorActual;

	@FXML
	private BorderPane borderpanelHerramientas;

	@FXML
	void cambiarPanelDevoluciones(MouseEvent event) throws IOException {
		this.borderpanelHerramientas.setCenter(null);
		this.loadUI("/vistas/herramientas/paneldevolucionesherramientas2");

	}

	@FXML
	void cambiarPanelHerramientasMaestros(MouseEvent event) {
		this.borderpanelHerramientas.setCenter(null);
		this.loadUI("/vistas/herramientas/paneltablaherramientas");

	}

	@FXML
	void cambiarPanelReparaciones(MouseEvent event) throws IOException {
		this.borderpanelHerramientas.setCenter(null);
		this.loadUI("/vistas/herramientas/penelreparacionherramientas");

	}

	@FXML
	void cambiarPanelRetiros(MouseEvent event) {

		this.borderpanelHerramientas.setCenter(null);
		this.loadUI("/vistas/herramientas/panelretirodeherramientas2");

	}

	@FXML
	void cambiarPanelCategoriasHerramientas(MouseEvent event) {
		this.borderpanelHerramientas.setCenter(null);
		this.loadUI("/vistas/categorias/categoriasVista");
		ControladorCategoriasABM controlador = (ControladorCategoriasABM) this.ControladorActual;
		controlador.cargarVista(TipoCategoria.HERRAMIENTAS);
	}

	private void loadUI(String ui) {
		FXMLLoader root = null;
		try {
			root = new FXMLLoader(getClass().getResource(ui + ".fxml"));
			Scene scene = new Scene(root.load()); // (root)
			this.ControladorActual = root.getController();
			borderpanelHerramientas.setCenter(scene.getRoot());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
