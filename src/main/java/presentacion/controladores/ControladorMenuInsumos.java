package presentacion.controladores;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import presentacion.controladores.categorias.ControladorCategoriasABM;
import util.TipoCategoria;

public class ControladorMenuInsumos {

	@FXML
	private BorderPane borderpanel;

	private Object controladorActual;

	@FXML
	void cambiarPanelCategorias(MouseEvent event) {
		borderpanel.setCenter(null);
		this.loadUI("/vistas/categorias/categoriasVista");
		ControladorCategoriasABM controlador = (ControladorCategoriasABM) this.controladorActual;
		controlador.cargarVista(TipoCategoria.INSUMOS);
	}

	@FXML
	void cambiarPanelInsumosMaestros(MouseEvent event) {
		borderpanel.setCenter(null);
		this.loadUI("/vistas/insumosmaestros");
		ControladorABMinsumos controlador = (ControladorABMinsumos) this.controladorActual;
		controlador.llenarTablaInsumos();
	}

	@FXML
	void cambiarPanelListaInsumos(MouseEvent event) {
		borderpanel.setCenter(null);
		this.loadUI("/vistas/tablainsumosmaestrosbusqueda");
		ControladorABMinsumos controlador = (ControladorABMinsumos) this.controladorActual;
		controlador.llenarTablaInsumos();
	}

	@FXML
	void cambiarPanelListaStock(MouseEvent event) {
		borderpanel.setCenter(null);
		this.loadUI("/vistas/tablastockbusqueda");
		ControladorStockInsumos controlador = (ControladorStockInsumos) this.controladorActual;
		controlador.llenarTabla();
	}

	@FXML
	void cambiarPanelPedidos(MouseEvent event) {
		borderpanel.setCenter(null);
		this.loadUI("/vistas/menupedidos");
		ControladorPedidos controlador = (ControladorPedidos) this.controladorActual;
		controlador.llenarTablaPedidos();
	}

	@FXML
	void cambiarPanelRetiros(MouseEvent event) {
		borderpanel.setCenter(null);
		this.loadUI("/vistas/retirosinsumos");
		ControladorStockInsumos controlador = (ControladorStockInsumos) this.controladorActual;
		controlador.llenarTabla();
	}

	@FXML
	void cambiarPanelStockMaestro(MouseEvent event) {
		borderpanel.setCenter(null);
	}

	@FXML
	void cerrar(MouseEvent event) {
		Stage stage = (Stage) borderpanel.getScene().getWindow();
		stage.close();
	}

	@FXML
	void MouseOnDragged(MouseEvent event) {
		// TODO Completar o quitar metodo
	}

	@FXML
	void MouseOnPressed(MouseEvent event) {
		// TODO Completar o quitar metodo
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