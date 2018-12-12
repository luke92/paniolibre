package presentacion.controladores;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import domain.model.Herramienta;
import domain.model.ReparacionHerramienta;
import dto.HerramientaDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import persistencia.dao.mysql.DAOSQLFactory;
import services.HerramientaService;
import services.ReparacionHerramientaService;
import util.Fechas;

public class ReparacionesABM implements Initializable {

	@FXML
	private ScrollPane panelABMReparaciones;

	@FXML
	private Label LabelTitulo;

	@FXML
	private VBox vboxFormulario;

	@FXML
	private Label LabelHerramientas;

	@FXML
	private TextField texCodigo;

	@FXML
	private TextField texNombre;

	@FXML
	private TextField texMarca;

	@FXML
	private Separator separatorHerramienta;

	@FXML
	private Label labelReparacionEnvio;

	@FXML
	private ComboBox<String> comboTipoReparacion;

	@FXML
	private DatePicker dateFechaEnvio;

	@FXML
	private TextArea texComentarioEnv;

	@FXML
	private Separator separatorReparacionEnvio;

	@FXML
	private Label labelReparacionDevolucion;

	@FXML
	private DatePicker dateFechaRecepcion;

	@FXML
	private DatePicker dateFechaGarantia;

	@FXML
	private TextArea texComentarioRecepcion;

	@FXML
	private HBox hboxBotones;

	@FXML
	private Button btnLimpiar;

	@FXML
	private Button btnGuardar;

	private HerramientaDTO hDto;

	@FXML
	private Button btnIngresar;

	public ScrollPane getPanelABMReparaciones() {
		return panelABMReparaciones;
	}

	@FXML
	void ingresar(MouseEvent event) {
		ReparacionHerramientaService service = new ReparacionHerramientaService(new DAOSQLFactory());
		Herramienta herramienta = new Herramienta();
		herramienta.setIdHerramienta(hDto.getIdHerramienta().get());
		int idReparacion = service.obtenerIdReparacionHerramienta(herramienta);
		ReparacionHerramienta reparacionHerramienta = new ReparacionHerramienta();
		reparacionHerramienta.setIdReparacionHerramienta(idReparacion);
		reparacionHerramienta.setComentarioRecepcion(texComentarioRecepcion.getText());
		reparacionHerramienta.setFechaRecibida(Fechas.localDateToCalendar(dateFechaRecepcion.getValue()));
		reparacionHerramienta.setFechaExpiracionGarantia(Fechas.localDateToCalendar(dateFechaGarantia.getValue()));
		service.editarReparacionHerramienta(reparacionHerramienta);
		texCodigo.setText("");
		texNombre.setText("");
		texMarca.setText("");
		dateFechaEnvio.setValue(null);
		texComentarioEnv.setText("");
		texComentarioRecepcion.setText("");
		dateFechaGarantia.setValue(null);
		dateFechaRecepcion.setValue(null);
		HerramientaService herramientaService = new HerramientaService(new DAOSQLFactory());
		herramientaService.cambiarEstadoDisponible(herramienta);
	}

	@FXML
	void guardar(MouseEvent event) {
		if (this.validacionesDeCampos()) {
			HerramientaService herramientaService = new HerramientaService(new DAOSQLFactory());
			ReparacionHerramientaService servicio = new ReparacionHerramientaService(new DAOSQLFactory());
			ReparacionHerramienta reparacionHerramienta = new ReparacionHerramienta();
			reparacionHerramienta.setIdReparacionHerramienta(0);
			Herramienta herramienta = new Herramienta();
			herramienta.setIdHerramienta(hDto.getIdHerramienta().get());
			reparacionHerramienta.setHerramienta(herramienta);
			comboTipoReparacion.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					reparacionHerramienta.setReparacionInterna(obtenerTipoReparacion(newValue));
				}
			});
			reparacionHerramienta.setFechaEnviada(Fechas.localDateToCalendar(dateFechaEnvio.getValue()));
			reparacionHerramienta.setComentarioEnvio(texComentarioEnv.getText());
			herramientaService.cambiarEstadoEnReparacion(herramienta);
			servicio.agregarReparacionHerramienta(reparacionHerramienta);
			texCodigo.setText("");
			texNombre.setText("");
			texMarca.setText("");
			dateFechaEnvio.setValue(null);
			texComentarioEnv.setText("");
			comboTipoReparacion.setVisible(false);
			this.labelReparacionEnvio.setDisable(true);
		}
	}

	@FXML
	void limpiar(MouseEvent event) {
		texCodigo.setText("");
		texNombre.setText("");
		texMarca.setText("");
		dateFechaEnvio.setValue(LocalDate.now());
		texComentarioEnv.setText("");
	}

	public Button getBtnGuardar() {
		return btnGuardar;
	}

	public void cargarHerramienta(HerramientaDTO h) {
		this.modoNuevaReparacion();
		texCodigo.setText(h.getCodigo().get());
		texNombre.setText(h.getNombre().get());
		texMarca.setText(h.getMarca().get());
		texComentarioEnv.setText(h.getComentarioAveriado());
		this.dateFechaEnvio.setValue(LocalDate.now());
		hDto = new HerramientaDTO();
		hDto.setIdHerramienta(h.getIdHerramienta().get());
	}

	public void cargarHerramientaRep(HerramientaDTO h) {
		this.modoFinalizarReparacion();
		texCodigo.setText(h.getCodigo().get());
		texNombre.setText(h.getNombre().get());
		texMarca.setText(h.getMarca().get());
		texComentarioEnv.setText(h.getComentarioAveriado());
		this.comboTipoReparacion.setVisible(false);
		hDto = new HerramientaDTO();
		hDto.setIdHerramienta(h.getIdHerramienta().get());
		this.dateFechaRecepcion.setValue(LocalDate.now());

	}

	public void modoNuevaReparacion() {
		labelReparacionDevolucion.setDisable(true);
		dateFechaRecepcion.setDisable(true);
		dateFechaGarantia.setDisable(true);
		texComentarioRecepcion.setDisable(true);
		this.btnIngresar.setVisible(false);
		texCodigo.setDisable(false);
		texComentarioEnv.setDisable(false);
		texMarca.setDisable(false);
		texNombre.setDisable(false);
		dateFechaEnvio.setDisable(false);
		this.btnGuardar.setVisible(true);

	}

	public void modoFinalizarReparacion() {
		labelReparacionDevolucion.setDisable(false);
		texCodigo.setDisable(true);
		texComentarioEnv.setDisable(true);
		texMarca.setDisable(true);
		texNombre.setDisable(true);
		dateFechaEnvio.setDisable(true);
		dateFechaRecepcion.setDisable(false);
		dateFechaGarantia.setDisable(false);
		texComentarioRecepcion.setDisable(false);
		this.btnGuardar.setVisible(false);
		this.btnIngresar.setVisible(true);

	}

	public int obtenerTipoReparacion(String tipo) {
		if (tipo.equals("Externa"))
			return 1;
		return 0;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		comboTipoReparacion.getItems().addAll("Interna", "Externa");

	}

	private boolean validacionesDeCampos() {
		ValidationSupport validationSupport = new ValidationSupport();
		boolean ret = true;
		if (comboTipoReparacion.getValue() == null) {
			validationSupport.registerValidator(comboTipoReparacion, Validator.createEmptyValidator("Campo requerido"));
			Notifications.create().title("Atencion").text("El campo 'Tipo de Reparacion' no puede estar vacio")
					.darkStyle().showWarning();
			ret = false;
		}
		if (dateFechaEnvio.getValue() == null) {
			validationSupport.registerValidator(dateFechaEnvio, Validator.createEmptyValidator("Campo requerido"));
			Notifications.create().title("Atencion").text("El campo 'Feha de Envio' no puede estar vacio").darkStyle()
					.showWarning();
			ret = false;
		}
		return ret;

	}

}
