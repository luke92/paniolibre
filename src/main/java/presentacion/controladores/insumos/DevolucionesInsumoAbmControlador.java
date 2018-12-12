package presentacion.controladores.insumos;

import java.time.LocalDate;

import dto.RetiroInsumoDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DevolucionesInsumoAbmControlador {

	@FXML
	private ScrollPane panelABMDevoluciones;

	@FXML
	private VBox vboxFormulario;

	@FXML
	private Label labelTitulo;

	@FXML
	private Label labelListaDevolucion;

	@FXML
	private ListView<RetiroInsumoDTO> listDevolucion;

	@FXML
	private Separator separatorListaDevolucion;

	@FXML
	private Label labelInfoDevolucion;

	@FXML
	private TextField texUsuario;

	@FXML
	private TextField texTecnico;

	@FXML
	private TextField texOrdenDeTrabajo;

	@FXML
	private DatePicker dateFechaDevolucion;

	@FXML
	private Separator separatorInfoDevolucion;

	@FXML
	private HBox hboxBotones;

	@FXML
	private Button btnLimpiar;

	@FXML
	private Button btnGuardar;

	private DevolucionesInsumoControlador devolucionesInsumoAbmControlador;

	public ScrollPane getPanelABMDevoluciones() {
		return panelABMDevoluciones;
	}

	@FXML
	void guardar(MouseEvent event) {
		// TODO Completar o quitar metodo
	}

	@FXML
	void limpiar(MouseEvent event) {
		this.texOrdenDeTrabajo.setText("");
		this.texTecnico.setText("");
		this.texUsuario.setText("");
		this.listDevolucion.getItems().clear();
	}

	public void setControladorPadre(DevolucionesInsumoControlador devolucionesInsumoControlador) {
		this.devolucionesInsumoAbmControlador = devolucionesInsumoControlador;

	}

	public void setListaPrestamo(ObservableList<RetiroInsumoDTO> masterData) {
		this.listDevolucion.setItems(masterData);
	}

	public TextField getTexUsuario() {
		return texUsuario;
	}

	public void setTexUsuario(String texto) {
		this.texUsuario.setText(texto);
	}

	public TextField getTexTecnico() {
		return texTecnico;
	}

	public void setTexTecnico(String tecnico) {
		this.texTecnico.setText(tecnico);
	}

	public TextField getTexOrdenDeTrabajo() {
		return texOrdenDeTrabajo;
	}

	public void setTexOrdenDeTrabajo(String texOrdenDeTrabajo) {
		this.texOrdenDeTrabajo.setText(texOrdenDeTrabajo);
	}

	public DatePicker getDateFechaDevolucion() {
		return dateFechaDevolucion;
	}

	public void setDateFechaDevolucion(LocalDate fecha) {
		this.dateFechaDevolucion.setValue(fecha);
	}

}