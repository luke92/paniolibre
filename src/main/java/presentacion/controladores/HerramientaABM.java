package presentacion.controladores;

import java.time.LocalDate;
import java.util.Calendar;

import org.controlsfx.control.Notifications;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import domain.model.CategoriaHerramienta;
import domain.model.Deposito;
import domain.model.EnumEstadoHerramienta;
import domain.model.Herramienta;
import domain.model.UbicacionDeposito;
import dto.ArbolCategoriaDTO;
import dto.CategoriaHerramientaDTO;
import dto.DepositoDTO;
import dto.EnumEstadoHerramientaDTO;
import dto.EstadoDTO;
import dto.HerramientaDTO;
import dto.UbicacionDepositoDTO;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import persistencia.dao.mysql.DAOSQLFactory;
import services.AdministracionService;
import services.ArbolCategoriaService;
import services.HerramientaService;
import util.Fechas;

public class HerramientaABM {
	ValidationSupport validationSupport = new ValidationSupport();
	private boolean esVentanaAgregar = false;

	@FXML
	private DialogPane panelAgregarHerramienta;

	@FXML
	private Button btnAgregar;

	@FXML
	private Button btnBorrar;

	@FXML
	private TextField texCodigo;

	@FXML
	private TextField texNombre;

	@FXML
	private TextField textMarca;

	@FXML
	private ChoiceBox<UbicacionDepositoDTO> choUbicacion;

	@FXML
	private Text lblCategoria;

	@FXML
	private TreeView<ArbolCategoriaDTO> treeViewCategorias;

	private ArbolCategoriaService serviceCategoria = new ArbolCategoriaService(new DAOSQLFactory());
	private ObservableList<ArbolCategoriaDTO> per = FXCollections.observableArrayList();
	private TreeItem<ArbolCategoriaDTO> rootArbol;

	@FXML
	private TextField texFactura;

	@FXML
	private TextField texNumActivo;

	@FXML
	private TextArea texComentario;

	@FXML
	private TextField texAdiquisicion;

	@FXML
	private DatePicker DateAdquisicion;

	@FXML
	private DatePicker DateGarantia;

	@FXML
	private ChoiceBox<?> choHerramienta;

	@FXML
	private TextField texProveedor;

	@FXML
	private Text lblHerramienta;

	@FXML
	private Button btnCerrar;

	@FXML
	private Button btnModificar;

	@SuppressWarnings("unused")
	private HerramientaDTO herramientaAmodificar;

	private ObservableList<HerramientaDTO> masterData = FXCollections.observableArrayList();

	private Button btnVer;

	private Calendar fechaAdquisicion;

	private Calendar fechaGarantiaExpiracion;

	@FXML
	void agregar(MouseEvent event) {
		this.esVentanaAgregar = true;
		if (this.validacionesDeCampos()) {
			HerramientaDTO nuevaHerramienta = new HerramientaDTO(0, texCodigo.getText(), texNombre.getText(),
					getChoUbicacion().getValue(), null, texFactura.getText(), texNumActivo.getText(),
					texComentario.getText(), texAdiquisicion.getText(), DateAdquisicion.getValue(),
					DateGarantia.getValue(), LocalDate.now(), EnumEstadoHerramientaDTO.DISPONIBLE, 1,
					texProveedor.getText(), textMarca.getText(), new Button("ver"), masterData, this, this.btnVer);

			ArbolCategoriaDTO categoriaSeleccionda = this.obtenerCategoriaSeleccionada();
			CategoriaHerramientaDTO categoriaDTO = new CategoriaHerramientaDTO();
			categoriaDTO.setIdCategoria(categoriaSeleccionda.getId());
			categoriaDTO.setNombre(categoriaSeleccionda.getNombre());
			nuevaHerramienta.setCategoria(categoriaDTO);
			nuevaHerramienta.setCategoriaArbol(categoriaSeleccionda);
			// ACA SE CREA UN MODELO Y SE LLAMA EL SERVICE
			HerramientaService servicio = new HerramientaService(new DAOSQLFactory());

			CategoriaHerramienta categoria = new CategoriaHerramienta();
			categoria.setIdCategoria(categoriaSeleccionda.getId());
			categoria.setNombre(categoriaSeleccionda.getNombre());
			UbicacionDeposito ubicacion = new UbicacionDeposito();
			ubicacion.setIdUbicacionDeposito(getChoUbicacion().getValue().getIdUbicacionDeposito().get());
			fechaAdquisicion = Calendar.getInstance();
			int anioAdq = DateAdquisicion.getValue().getYear();
			int mesAdq = DateAdquisicion.getValue().getMonthValue();
			int diaAdq = DateAdquisicion.getValue().getDayOfMonth();
			fechaAdquisicion.set(anioAdq, mesAdq, diaAdq);
			if (DateGarantia.getValue() != null) {
				fechaGarantiaExpiracion = Calendar.getInstance();
				int anioGar = DateGarantia.getValue().getYear();
				int mesGar = DateGarantia.getValue().getMonthValue();
				int diaGar = DateGarantia.getValue().getDayOfMonth();
				fechaGarantiaExpiracion.set(anioGar, mesGar, diaGar);
			}
			Herramienta herramienta = new Herramienta(0, texCodigo.getText(), texNombre.getText(), ubicacion, categoria,
					texFactura.getText(), texNumActivo.getText(), texComentario.getText(), texAdiquisicion.getText(),
					fechaAdquisicion, texProveedor.getText(), fechaGarantiaExpiracion, EnumEstadoHerramienta.DISPONIBLE,
					1, textMarca.getText());
			servicio.agregarHerramienta(herramienta);
			this.masterData.add(nuevaHerramienta);
			// SE LLAMA A LA VENTANA PADRE PARA QUE CAMBIE EL PANEL A EL PANEL
			// HERRAMIENTATABLACONTROLADOR

			this.vaciarCampos();

		} else {
			/// ACA VAN LAS VENTANAS EMERGENTES CON LO QUE FALTO
			/// SE PUEDEN IR ACUMULADO EN UNA VARIBBLE LOS CAMPOS QUE VALLARON
			/// Y MOSTRARLOS EN UNA SOLA VENTANA
		}

	}

	@FXML
	void borrar(MouseEvent event) {
		// TODO Completar o quitar metodo
	}

	@FXML
	void modificar(MouseEvent event) {

		if (this.validacionesDeCampos()) {
			HerramientaService servicio = new HerramientaService(new DAOSQLFactory());
			CategoriaHerramienta categoria = new CategoriaHerramienta();
			ArbolCategoriaDTO categoriaSeleccionada = null;
			if (this.obtenerCategoriaSeleccionada() != null) {
				categoriaSeleccionada = obtenerCategoriaSeleccionada();
				categoria.setIdCategoria(categoriaSeleccionada.getId());
			} else {
				categoria.setIdCategoria(herramientaAmodificar.getCategoria().getIdCategoria().get());
			}
			UbicacionDeposito ubicacion = new UbicacionDeposito();
			ubicacion.setIdUbicacionDeposito(this.choUbicacion.getValue().getIdUbicacionDeposito().get());
			fechaAdquisicion = Calendar.getInstance();
			int anioAdq = DateAdquisicion.getValue().getYear();
			int mesAdq = DateAdquisicion.getValue().getMonthValue();
			int diaAdq = DateAdquisicion.getValue().getDayOfMonth();
			fechaAdquisicion.set(anioAdq, mesAdq, diaAdq);
			fechaGarantiaExpiracion = Calendar.getInstance();
			int anioGar = DateGarantia.getValue().getYear();
			int mesGar = DateGarantia.getValue().getMonthValue();
			int diaGar = DateGarantia.getValue().getDayOfMonth();
			fechaGarantiaExpiracion.set(anioGar, mesGar, diaGar);
			Herramienta herramienta = new Herramienta();
			herramienta.setCodigo(texCodigo.getText());
			herramientaAmodificar.setCodigo(texCodigo.getText());
			herramienta.setNombre(texNombre.getText());
			herramientaAmodificar.setNombre(texNombre.getText());
			herramienta.setIdHerramienta(herramientaAmodificar.getIdHerramienta().get());
			herramienta.setCategoria(categoria);
			CategoriaHerramientaDTO categoriaHerramientaDTO = new CategoriaHerramientaDTO();
			categoriaHerramientaDTO.setIdCategoria(herramientaAmodificar.getCategoria().getIdCategoria().get());
			herramientaAmodificar.setCategoria(categoriaHerramientaDTO);
			herramienta.setUbicacion(ubicacion);
			UbicacionDepositoDTO ubicacionDTO = new UbicacionDepositoDTO();
			ubicacionDTO.setIdUbicacionDeposito(herramientaAmodificar.getUbicacion().getIdUbicacionDeposito().get());
			herramientaAmodificar.setUbicacion(ubicacionDTO);
			herramienta.setFactura(texFactura.getText());
			herramientaAmodificar.setFactura(texFactura.getText());
			herramienta.setNumeroActivo(texNumActivo.getText());
			herramientaAmodificar.setNumeroActivo(texNumActivo.getText());
			herramienta.setComentario(texComentario.getText());
			herramientaAmodificar.setComentario(texComentario.getText());
			herramienta.setMecanismoAdquisicion(texAdiquisicion.getText());
			herramientaAmodificar.setMecanismoAdquisicion(texAdiquisicion.getText());
			herramienta.setFechaAdquisicion(fechaAdquisicion);
			herramientaAmodificar.setFechaAdquisicion(Fechas.CalendarTolocalDate(fechaAdquisicion));
			herramienta.setFechaGarantiaExpiracion(fechaGarantiaExpiracion);
			herramientaAmodificar.setFechaGarantiaExpiracion(Fechas.CalendarTolocalDate(fechaGarantiaExpiracion));
			herramienta.setProveedor(texProveedor.getText());
			herramientaAmodificar.setProveedor(texProveedor.getText());
			herramienta.setEstado(EnumEstadoHerramienta.DISPONIBLE);
			herramientaAmodificar.setEstadoHerramienta(EnumEstadoHerramientaDTO.DISPONIBLE);
			herramienta.setActivo(1);
			herramientaAmodificar.setActivo(EstadoDTO.ALTA);
			herramienta.setMarca(textMarca.getText());
			herramientaAmodificar.setMarca(textMarca.getText());
			herramientaAmodificar.setFechaUltimaModificacion(LocalDate.now());
			servicio.editarHerramienta(herramienta);
			this.masterData.remove(herramientaAmodificar);
			this.masterData.add(herramientaAmodificar);
			this.vaciarCampos();
		}
	}

	public void cargarDtoAmodificar(HerramientaDTO herramienta) {
		this.lblHerramienta.setText("Modificar Herramienta Maestra");

		AdministracionService administracionService = new AdministracionService(new DAOSQLFactory());
		this.btnAgregar.setDisable(true);
		this.btnBorrar.setDisable(false);
		this.btnModificar.setDisable(false);
		this.herramientaAmodificar = herramienta;
		this.texCodigo.setText(herramienta.getCodigo().get());
		texNombre.setText(herramienta.getNombre().get());
		texFactura.setText(herramienta.getFactura().get());
		texNumActivo.setText(herramienta.getActivo().getEstado().get());
		texComentario.setText(herramienta.getComentario().get());
		texAdiquisicion.setText(herramienta.getMecanismoAdquisicion().get());
		DateGarantia.setValue(herramienta.getFechaGarantia());
		DateAdquisicion.setValue(herramienta.getFechaAdquisicion());
		texProveedor.setText(herramienta.getProveedor().get());
		textMarca.setText(herramienta.getMarca().get());
		lblCategoria.setText("Categoria" + " actual: " + herramienta.getCategoria().getNombre().get());
		UbicacionDeposito uDeposito = new UbicacionDeposito();
		uDeposito.setIdUbicacionDeposito(herramienta.getUbicacion().getIdUbicacionDeposito().get());
		UbicacionDeposito ubicacionDeposito = administracionService.obtenerUbicacionDeposito(uDeposito);
		UbicacionDepositoDTO uDepositoDTO = new UbicacionDepositoDTO();
		uDepositoDTO.setNombre(ubicacionDeposito.getNombre());
		Deposito deposito = new Deposito();
		deposito.setIdDeposito(ubicacionDeposito.getDeposito().getIdDeposito());
		DepositoDTO depositoDTO = new DepositoDTO();
		depositoDTO.setNombre(administracionService.obtenerNombreDeposito(deposito));
		uDepositoDTO.setDeposito(depositoDTO);
		choUbicacion.setValue(uDepositoDTO);

	}

	void vaciarCampos() {
		this.herramientaAmodificar = null;
		this.texCodigo.setText("");
		texNombre.setText("");
		texFactura.setText("");
		texNumActivo.setText("");
		texComentario.setText("");
		texAdiquisicion.setText("");
		DateAdquisicion.setValue(LocalDate.now());
		texProveedor.setText("");
		textMarca.setText("");
		choUbicacion.getSelectionModel().clearSelection();
		DateAdquisicion.getEditor().clear();
		DateGarantia.getEditor().clear();
		this.lblCategoria.setText("Categoria");
	}

	public void cargarDTOnuevo(Button btnPanelABM, ObservableList<HerramientaDTO> masterData2) {
		this.btnAgregar.setDisable(false);
		this.btnBorrar.setDisable(true);
		this.btnModificar.setDisable(true);
		this.vaciarCampos();
		this.masterData = masterData2;
		this.btnVer = btnPanelABM;
		this.lblHerramienta.setText("Agregar Herramienta Maestra");
		this.choUbicacion.getSelectionModel().selectFirst();
	}

	private boolean validacionesDeCampos() {

		boolean ret = true;
		if (texCodigo.getText().isEmpty()) {
			validationSupport.registerValidator(texCodigo, Validator.createEmptyValidator("Campo requerido"));
			Notifications.create().title("Atencion").text("El campo 'Codigo' no puede estar vacio").darkStyle()
					.showWarning();
			ret = false;
		}
		if (texNombre.getText().isEmpty()) {
			validationSupport.registerValidator(texNombre, Validator.createEmptyValidator("Campo requerido"));
			Notifications.create().title("Atencion").text("El campo 'Nombre' no puede estar vacio").darkStyle()
					.showWarning();
			ret = false;
		}
		if (textMarca.getText().isEmpty()) {
			validationSupport.registerValidator(textMarca, Validator.createEmptyValidator("Campo requerido"));
			Notifications.create().title("Atencion").text("El campo 'Marca' no puede estar vacio").darkStyle()
					.showWarning();
			ret = false;
		}
		if (this.esVentanaAgregar && obtenerCategoriaSeleccionada() == null) {
			validationSupport.registerValidator(treeViewCategorias, Validator.createEmptyValidator("Campo requerido"));
			Notifications.create().title("Atencion").text("Debe seleccionarse una categoria").darkStyle().showWarning();
			ret = false;
		}
		if (getChoUbicacion().getValue() == null) {
			validationSupport.registerValidator(getChoUbicacion(), Validator.createEmptyValidator("Campo requerido"));
			Notifications.create().title("Atencion").text("El campo 'Ubicacion' no puede estar vacio").darkStyle()
					.showWarning();
			ret = false;
		}
		return ret;
	}

	public ChoiceBox<UbicacionDepositoDTO> getChoUbicacion() {
		return choUbicacion;
	}

	public void setChoUbicacion(ChoiceBox<UbicacionDepositoDTO> choUbicacion) {
		this.choUbicacion = choUbicacion;
	}

	public Button getBtnCerrar() {
		return btnCerrar;
	}

	public void llenarTreeView() {
		per.clear();
		ArbolCategoriaDTO arbol = null;
		arbol = serviceCategoria.obtenerArbolCategoriasHerramientaDTO();

		per.add(arbol);

		rootArbol = new RecursiveTreeItem<ArbolCategoriaDTO>(per, RecursiveTreeObject::getChildren);
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

	private ArbolCategoriaDTO obtenerCategoriaSeleccionada() {
		ReadOnlyObjectProperty<TreeItem<ArbolCategoriaDTO>> padre = treeViewCategorias.getSelectionModel()
				.selectedItemProperty();
		if (padre.get() != null)
			return padre.get().getValue();
		else
			return null;
	}
}
