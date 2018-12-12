package presentacion.controladores.insumos;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import domain.model.ArbolCategoria;
import domain.model.EnumUnidadMedida;
import domain.model.Estado;
import domain.model.Insumo;
import dto.ArbolCategoriaDTO;
import dto.EstadoDTO;
import dto.InsumoDTO;
import dto.UnidadMedidaDTO;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import persistencia.dao.mysql.DAOSQLFactory;
import services.ArbolCategoriaService;
import services.CategoriaInsumoService;
import services.InsumoService;
import util.Dialogos;
import util.ExpReg;
import util.Spinners;

public class VentanaIngresarInsumoControlador implements Initializable {

	@FXML
	private BorderPane ventanaAgregarInsumo;

	@FXML
	private BorderPane borderPrinipal;

	@FXML
	private TextField textId;

	@FXML
	private TextField textNombre;

	@FXML
	private TextField textMarca;

	@FXML
	private TextField textCodigo;

	@FXML
	private ChoiceBox<UnidadMedidaDTO> chbUnidadMedida;

	@FXML
	private TextArea textAreaComentario;

	@FXML
	private Button btnAgregar;

	@FXML
	private Button btnDescartar;

	@FXML
	private TreeView<ArbolCategoriaDTO> treeViewCategorias;

	@FXML
	private AnchorPane barraPrincipal;

	@FXML
	private Spinner<Integer> spnUmbralMinimo;

	ValidationSupport validationSupport = new ValidationSupport();

	private ArbolCategoriaService serviceCategoria = new ArbolCategoriaService(new DAOSQLFactory());
	private ObservableList<ArbolCategoriaDTO> per = FXCollections.observableArrayList();
	private TreeItem<ArbolCategoriaDTO> rootArbol;

	InsumoService insumoService = new InsumoService(new DAOSQLFactory());

	private ObservableList<InsumoDTO> masterDataAgregar = FXCollections.observableArrayList();

	CategoriaInsumoService categoria = new CategoriaInsumoService(new DAOSQLFactory());

	///// METODOS /////////////////////////////////////

	@FXML
	void agregarInsumoMaestro(MouseEvent event) {
		Insumo nuevoInsumo = new Insumo();
		nuevoInsumo.setIdInsumo(0);
		nuevoInsumo.setCodigoInsumo(textCodigo.getText().trim());
		nuevoInsumo.setMarca(textMarca.getText().trim());
		nuevoInsumo.setNombre(textNombre.getText().trim());
		nuevoInsumo.setComentario(textAreaComentario.getText().trim());
		nuevoInsumo.setUmbralMinimo(spnUmbralMinimo.getValue());
		ArbolCategoria categoriaArbol = new ArbolCategoria();
		ArbolCategoriaDTO categoriaSeleccionada = this.obtenerCategoriaSeleccionada();
		if (categoriaSeleccionada != null) {
			categoriaArbol.setId(categoriaSeleccionada.getId());
			categoriaArbol.setNombre(categoriaSeleccionada.getNombre());
		}
		EnumUnidadMedida unidadMedida = EnumUnidadMedida
				.getUnidadMedida(chbUnidadMedida.getValue().getIdUnidadMedida().get());
		nuevoInsumo.setUnidadMedida(unidadMedida);
		nuevoInsumo.setCategoriaInsumo(categoriaArbol);

		if (insumoService.existeInsumo(nuevoInsumo)) { // retorna true si existe el insumo
			Dialogos.error("Error al crear Insumo Maestro", "Revice los siguientes campos",
					"Ya existe ese codigo en el sistema");
		} else {
			insumoService.agregarInsumo(nuevoInsumo);
			this.masterDataAgregar.add(nuevoInsumo.getInsumoDTO());
			cerrarVentanaAgregarInsumo();
		}
	}

	@FXML
	void descartarInsumoMaestro(MouseEvent event) {
		cerrarVentanaAgregarInsumo();
	}

	private void cerrarVentanaAgregarInsumo() {
		Stage stage = (Stage) ventanaAgregarInsumo.getScene().getWindow();
		stage.close();
	}

	public void vincularArreglos(ObservableList<InsumoDTO> masterDataAgregar) {
		this.masterDataAgregar = masterDataAgregar;
	}

	public void cargarSpinnerUmbralMinimo() {
		new Spinners().setValoresSpinner(spnUmbralMinimo, 0, 99999999, 0);
	}

	@FXML
	void validarAgregarInsumo() {
		if (!textCodigo.getText().trim().isEmpty() && !textNombre.getText().trim().isEmpty()
				&& !textMarca.getText().trim().isEmpty() && (obtenerCategoriaSeleccionada() != null)) {
			Insumo insumo = new Insumo();
			insumo.setCodigoInsumo(textCodigo.getText().trim());
			if (insumoService.existeInsumo(insumo)) {
				validationSupport.registerValidator(textCodigo, Validator.createEmptyValidator("Ya existe ese codigo"));
			} else {
				btnAgregar.setDisable(false);
			}
		}

		String campoRequerido = "Campo requerido";
		validationSupport.registerValidator(textCodigo, Validator.createEmptyValidator(campoRequerido));
		validationSupport.registerValidator(textNombre, Validator.createEmptyValidator(campoRequerido));
		validationSupport.registerValidator(textMarca, Validator.createEmptyValidator(campoRequerido));
	}

	public void cargarChoiceBoxUnidadMedida() {
		List<UnidadMedidaDTO> lista = new ArrayList<>();
		lista.add(EnumUnidadMedida.CENTIMETROS.getUnidadMedidaDTO());
		lista.add(EnumUnidadMedida.GRAMOS.getUnidadMedidaDTO());
		lista.add(EnumUnidadMedida.KILOGRAMOS.getUnidadMedidaDTO());
		lista.add(EnumUnidadMedida.LITROS.getUnidadMedidaDTO());
		lista.add(EnumUnidadMedida.METROS.getUnidadMedidaDTO());
		lista.add(EnumUnidadMedida.MILILITROS.getUnidadMedidaDTO());
		lista.add(EnumUnidadMedida.PULGADAS.getUnidadMedidaDTO());
		lista.add(EnumUnidadMedida.PAQUETES.getUnidadMedidaDTO());
		lista.add(EnumUnidadMedida.UNIDADES.getUnidadMedidaDTO());
		lista.add(EnumUnidadMedida.VALIJA.getUnidadMedidaDTO());
		chbUnidadMedida.getItems().addAll(lista);
		chbUnidadMedida.getSelectionModel().selectFirst();
	}

	private ArbolCategoriaDTO obtenerCategoriaSeleccionada() {
		ReadOnlyObjectProperty<TreeItem<ArbolCategoriaDTO>> padre = treeViewCategorias.getSelectionModel()
				.selectedItemProperty();
		if (padre.get() != null)
			return padre.get().getValue();
		else
			return null;
	}

	public void llenarTreeView() {
		per.clear();
		ArbolCategoriaDTO arbol = null;
		arbol = serviceCategoria.obtenerArbolCategoriasInsumoDTO();

		per.add(arbol);

		rootArbol = new RecursiveTreeItem<>(per, RecursiveTreeObject::getChildren);
		treeViewCategorias.setRoot(rootArbol.getChildren().get(0));
		this.expandTreeView(rootArbol.getChildren().get(0));

	}

	private void expandTreeView(TreeItem<ArbolCategoriaDTO> item) {
		if (item != null && !item.isLeaf()) {
			item.setExpanded(true);
			for (TreeItem<ArbolCategoriaDTO> child : item.getChildren()) {
				expandTreeView(child);
			}
		}
	}

	@FXML
	public void processKeyEvent(KeyEvent ev) {
		if (!ExpReg.contieneSoloNumeros(spnUmbralMinimo.getEditor().getText())) {
			spnUmbralMinimo.getEditor().setText("0");
		}
	}

	// VENTANA MODIFICAR INSUMO MAESTRO //

	@FXML
	private Button btnConfirmarModificacionInsumo;

	@FXML
	private Button btnDescartarModificacionInsumo;

	private InsumoDTO insumoAmodificar;

	@FXML
	private Text lblCategoriaActual;

	private double initX;

	private double initY;

	// METODOS //

	@FXML
	void descartarModificacionInsumo(MouseEvent event) {
		cerrarVentanaModificionInsumo();
	}

	@FXML
	void modificarInsumoMaestro(MouseEvent event) {
		this.modificarInsumoVentana();
		cerrarVentanaModificionInsumo();
	}

	private void cerrarVentanaModificionInsumo() {
		Stage stage = (Stage) ventanaAgregarInsumo.getScene().getWindow();
		stage.close();
	}

	public void cargarInsumoAmodificar(InsumoDTO insumo) {
		this.insumoAmodificar = insumo;
		spnUmbralMinimo.valueProperty().addListener((obs, oldValue, newValue) -> validarModificarInsumo());
		this.llenarTreeView();
		this.cargarChoiceBoxUnidadMedida();
		textCodigo.setText(insumo.getCodigo().get().trim());
		textNombre.setText(insumo.getNombre().get().trim());
		textAreaComentario.setText(insumo.getComentario().get().trim());
		new Spinners().setValoresSpinner(spnUmbralMinimo, 0, 99999999, insumo.getUmbralMinimo().get());
		textMarca.setText(insumo.getMarca().get().trim());
		chbUnidadMedida.setValue(insumo.getUnidadMedida());
		lblCategoriaActual.setText(lblCategoriaActual.getText() + " " + insumo.getCategoria());

		chbUnidadMedida.getSelectionModel().selectedItemProperty()
				.addListener((obs, oldValue, newValue) -> validarModificarInsumo());
	}

	void modificarInsumoVentana() {
		Insumo insumo = new Insumo();
		insumo.setCodigoInsumo(textCodigo.getText().trim());
		insumo.setIdInsumo(insumoAmodificar.getIdInsumo().get());
		insumo.setNombre(textNombre.getText().trim());
		insumo.setMarca(textMarca.getText().trim());
		insumo.setComentario(textAreaComentario.getText().trim());
		insumo.setUmbralMinimo(Integer.parseInt(spnUmbralMinimo.getEditor().getText()));
		EnumUnidadMedida unidadMedida = EnumUnidadMedida
				.getUnidadMedida(chbUnidadMedida.getValue().getIdUnidadMedida().get());
		insumo.setUnidadMedida(unidadMedida);
		insumo.setEstado(Estado.ALTA);
		ArbolCategoria categoriaArbol = new ArbolCategoria();
		ArbolCategoriaDTO categoriaDTO = insumoAmodificar.getCategoria();

		if (this.obtenerCategoriaSeleccionada() != null) {
			categoriaDTO = this.obtenerCategoriaSeleccionada();
		}
		categoriaArbol.setId(categoriaDTO.getId());
		categoriaArbol.setNombre(categoriaDTO.getNombre());
		insumo.setCategoriaInsumo(categoriaArbol);
		insumoAmodificar = insumo.getDTO(insumoAmodificar);
		insumoService.editarInsumo(insumo);
	}

	@FXML
	void validarModificarInsumo() {
		btnConfirmarModificacionInsumo.setDisable(true);
		InsumoDTO insumoDTO = new InsumoDTO();
		insumoDTO.setActivo(EstadoDTO.ALTA);
		insumoDTO.setCodigo(textCodigo.getText().trim());
		insumoDTO.getComentario().set(textAreaComentario.getText().trim());
		insumoDTO.setCategoria(this.obtenerCategoriaSeleccionada());
		insumoDTO.setIdInsumo(insumoAmodificar.getIdInsumo().get());
		insumoDTO.getMarca().set(textMarca.getText().trim());
		insumoDTO.setNombre(textNombre.getText().trim());
		insumoDTO.getUmbralMinimo().set(spnUmbralMinimo.getValue());
		insumoDTO.setUnidadMedida(chbUnidadMedida.getValue());

		Insumo insumo = new Insumo();
		insumo.setCodigoInsumo(textCodigo.getText().trim());
		if (insumoService.existeInsumo(insumo)
				&& !textCodigo.getText().trim().equalsIgnoreCase(insumoAmodificar.getCodigo().get())) {
			validationSupport.registerValidator(textCodigo, Validator.createEmptyValidator("Ya existe ese codigo"));
		} else {
			if (!insumoDTO.equals(insumoAmodificar))
				btnConfirmarModificacionInsumo.setDisable(false);
			else
				btnConfirmarModificacionInsumo.setDisable(true);
		}

		validationSupport.registerValidator(textCodigo, Validator.createEmptyValidator("Campo requerido"));
		validationSupport.registerValidator(textNombre, Validator.createEmptyValidator("Campo requerido"));
		validationSupport.registerValidator(textMarca, Validator.createEmptyValidator("Campo requerido"));
	}

	public void arrastrarVenta2(boolean arrastrar) {

		if (arrastrar) {
			barraPrincipal.setOnMousePressed(event -> {
				Stage stage = (Stage) ventanaAgregarInsumo.getScene().getWindow();
				initX = event.getSceneX();
				initY = event.getSceneY();
				borderPrinipal.setOpacity(0.7);
			});
			barraPrincipal.setOnMouseDragged(event -> {
				Stage stage = (Stage) ventanaAgregarInsumo.getScene().getWindow();
				stage.setX(event.getScreenX() - initX);
				stage.setY(event.getScreenY() - initY);

			});
		} else {
			barraPrincipal.setOnMousePressed(event -> {
			});
			barraPrincipal.setOnMouseDragged(event -> {
			});
		}
		barraPrincipal.setOnMouseReleased(event -> borderPrinipal.setOpacity(1.0));

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.arrastrarVenta2(true);

	}

}
