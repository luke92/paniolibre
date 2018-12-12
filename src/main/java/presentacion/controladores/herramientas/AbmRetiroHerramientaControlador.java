package presentacion.controladores.herramientas;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import domain.model.CategoriaHerramienta;
import domain.model.EnumEstadoHerramienta;
import domain.model.Herramienta;
import domain.model.OrdenDeTrabajo;
import domain.model.RetiroHerramienta;
import domain.model.Tecnico;
import domain.model.UbicacionDeposito;
import domain.model.Usuario;
import domain.model.UsuarioLogueado;
import dto.HerramientaDTO;
import dto.OrdenTrabajoDTO;
import dto.TecnicoDTO;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.controladores.insumos.ControladorListaOrdenesDeTrabajo;
import services.HerramientaService;
import services.OrdenDeTrabajoService;
import services.RetiroHerramientaService;
import services.TecnicoService;
import util.Fechas;

public class AbmRetiroHerramientaControlador implements Initializable {

	@FXML
	private ScrollPane panelABMRetiroHerramienta;

	@FXML
	private VBox vboxFormulario;

	@FXML
	private Label labelTitulo;

	@FXML
	private Label labelRetiro;

	@FXML
	private ComboBox<TecnicoDTO> comboTecnicos;

	@FXML
	private TextField texNumOrden;

	@FXML
	private Separator separatorRetiro;

	@FXML
	private Label labelListaPrestamo;

	@FXML
	private ListView<HerramientaDTO> listPrestamo;

	@FXML
	private Separator separatorPrestamo;

	@FXML
	private HBox hboxBotones;

	@FXML
	private Button btnLimpiar;

	@FXML
	private Button btnObtnerOrden;
	@FXML
	private Button btnRetirar;
	private OrdenTrabajoDTO orden;

	@FXML
	void obtenerOrdenes(MouseEvent event) {
		orden = new OrdenTrabajoDTO();
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource("/vistas/insumos/verListaOrdenes.fxml"));
			Scene scene = new Scene(root.load());
			scene.setFill(Color.TRANSPARENT);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.initModality(Modality.APPLICATION_MODAL);
			// Eventos del teclado
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if (event.getCode() == KeyCode.ESCAPE) {
						stage.close();
					}
				}
			});
			stage.showAndWait();
			ControladorListaOrdenesDeTrabajo controlador = root.getController();
			orden = controlador.obtenerOrden();
			this.texNumOrden.setText(orden.getIdOrdenTrabajo().get());
			this.comboTecnicos.getItems().clear();
			OrdenDeTrabajoService ordenDeTrabajoService = new OrdenDeTrabajoService(new DAOSQLFactory());
			OrdenDeTrabajo ordenDeTrabajo = new OrdenDeTrabajo();
			ordenDeTrabajo.setId(orden.getId().get());
			ordenDeTrabajo.setIdOrdenDeTrabajo(orden.getIdOrdenTrabajo().get());
			List<TecnicoDTO> tecnicos = ordenDeTrabajoService.obtenerTecnicos(ordenDeTrabajo);
			for (TecnicoDTO tecnicoDTO : tecnicos) {
				this.comboTecnicos.getItems().add(tecnicoDTO);
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	RetiroHerramientasControlador retiroHerramientasControlador;

	public ScrollPane getPanelABMRetiroHerramienta() {
		return panelABMRetiroHerramienta;
	}

	@FXML
	void limpiar(MouseEvent event) {
		this.texNumOrden.setText("");
	}

	@FXML
	void retirar(MouseEvent event) {
		// this.retiroHerramientasControlador.getVboxPanelTablas().setDisable(false);
		this.retiroHerramientasControlador.getBordelPanelRetiro().setLeft(null);
		RetiroHerramientaService servicio = new RetiroHerramientaService(new DAOSQLFactory());
		HerramientaService herramientaService = new HerramientaService(new DAOSQLFactory());
		RetiroHerramienta retiroHerramienta = new RetiroHerramienta();
		retiroHerramienta.setIdRetiroHerramienta(0);
		UsuarioLogueado usuarioLogueado = UsuarioLogueado.getInstancia();
		Usuario usuario = usuarioLogueado.getUsuarioLogueado();
		retiroHerramienta.setUsuario(usuario);
		Calendar fechaPrestamo = Calendar.getInstance();
		fechaPrestamo.setTime(Date.valueOf(LocalDate.now()));
		retiroHerramienta.setFechaPrestamo(fechaPrestamo);
		OrdenDeTrabajo ordenDeTrabajo = new OrdenDeTrabajo();
		if (!texNumOrden.getText().equals("")) {
			ordenDeTrabajo.setId(orden.getId().get());
			retiroHerramienta.setOrdenDeTrabajo(ordenDeTrabajo);
		}
		List<Herramienta> herramientas = new ArrayList<>();
		List<HerramientaDTO> herramientaDTOs = this.listPrestamo.getItems();
		for (HerramientaDTO herramientaDTO : herramientaDTOs) {
			Herramienta herramienta = new Herramienta();
			herramienta.setIdHerramienta(herramientaDTO.getIdHerramienta().get());
			herramienta.setNombre(herramientaDTO.getNombre().get());
			herramienta.setActivo(herramientaDTO.getActivo().getValor().get());
			CategoriaHerramienta categoriaHerramienta = new CategoriaHerramienta();
			categoriaHerramienta.setIdCategoria(herramientaDTO.getCategoria().getIdCategoria().get());
			herramienta.setCategoria(categoriaHerramienta);
			herramienta.setCodigo(herramientaDTO.getCodigo().get());
			herramienta.setComentario(herramientaDTO.getComentario().get());
			herramienta.setEstado(obtenerEstado(herramientaDTO.getEstadoHerramienta().getValor().get()));
			herramienta.setFactura(herramientaDTO.getFactura().get());
			herramienta.setFechaAdquisicion(Fechas.localDateToCalendar(herramientaDTO.getFechaAdquisicion()));
			herramienta.setFechaGarantiaExpiracion(Fechas.localDateToCalendar(herramientaDTO.getFechaGarantia()));
			herramienta.setMarca(herramientaDTO.getMarca().get());
			herramienta.setMecanismoAdquisicion(herramientaDTO.getMecanismoAdquisicion().get());
			herramienta.setNumeroActivo(herramientaDTO.getNumeroActivo().get());
			herramienta.setProveedor(herramientaDTO.getProveedor().get());
			UbicacionDeposito ubicacionDeposito = new UbicacionDeposito();
			ubicacionDeposito.setIdUbicacionDeposito(herramientaDTO.getUbicacion().getIdUbicacionDeposito().get());
			herramienta.setUbicacion(ubicacionDeposito);
			retiroHerramienta.setHerramienta(herramienta);
			Tecnico tecnico = new Tecnico();
			tecnico.setIdTecnico(comboTecnicos.getValue().getIdTecnico().get());
			retiroHerramienta.setTecnico(tecnico);
			herramientas.add(herramienta);
			herramientaService.cambiarEstadoPrestada(herramienta);
			servicio.agregarRetiroHerramienta(retiroHerramienta);
		}
		this.retiroHerramientasControlador.getMasterData1().clear();
	}

	public void setControladorPadre(RetiroHerramientasControlador retiroHerramientasControlador) {
		this.retiroHerramientasControlador = retiroHerramientasControlador;

	}

	public void setListaPrestamo(ObservableList<HerramientaDTO> masterData) {
		this.listPrestamo.setItems(masterData);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.texNumOrden.setEditable(false);
		TecnicoService tecnicoServicio = new TecnicoService(new DAOSQLFactory());
		List<Tecnico> tecnicos = tecnicoServicio.obtenerTecnicos();
		for (Tecnico tecnico : tecnicos) {
			TecnicoDTO tecnicoDTO = new TecnicoDTO();
			tecnicoDTO.setIdTecnico(tecnico.getIdTecnico());
			tecnicoDTO.setNombre(tecnico.getNombre());
			tecnicoDTO.setApellido(tecnico.getApellido());
			tecnicoDTO.setDni(tecnico.getDni());
			tecnicoDTO.setLegajo(tecnico.getLegajo());
			comboTecnicos.getItems().addAll(tecnicoDTO);
		}

		comboTecnicos.getSelectionModel().selectFirst();
	}

	public EnumEstadoHerramienta obtenerEstado(int estado) {
		if (estado == 1)
			return EnumEstadoHerramienta.DISPONIBLE;
		if (estado == 2)
			return EnumEstadoHerramienta.PRESTADO;
		if (estado == 3)
			return EnumEstadoHerramienta.AVERIADO;
		if (estado == 4)
			return EnumEstadoHerramienta.EN_REPARACION;
		return null;
	}

	public ListView<HerramientaDTO> getListPrestamo() {
		return listPrestamo;
	}

}
