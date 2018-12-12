package presentacion.controladores.herramientas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import domain.model.CategoriaHerramienta;
import domain.model.DevolucionHerramienta;
import domain.model.EnumEstadoHerramienta;
import domain.model.Herramienta;
import domain.model.OrdenDeTrabajo;
import domain.model.Tecnico;
import domain.model.UbicacionDeposito;
import domain.model.Usuario;
import domain.model.UsuarioLogueado;
import dto.HerramientaDTO;
import dto.OrdenTrabajoDTO;
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
import persistencia.dao.mysql.DAOSQLFactory;
import services.DevolucionHerramientaService;
import services.HerramientaService;
import services.OrdenDeTrabajoService;
import services.RetiroHerramientaService;
import util.Dialogos;
import util.Fechas;

public class DevolucionesHerramientasAbmControlador {

	@FXML
	private ScrollPane panelABMDevoluciones;

	@FXML
	private VBox vboxFormulario;

	@FXML
	private Label LabelTitulo;

	@FXML
	private Label labelListaDevolucion;

	@FXML
	private ListView<HerramientaDTO> listDevolucion;

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

	private DevolucionesControlador controladorPadre;

	public void setControladorPadre(DevolucionesControlador controladorPadre) {
		this.controladorPadre = controladorPadre;
	}

	public ScrollPane getPanelABMDevoluciones() {
		return panelABMDevoluciones;
	}

	@FXML
	void guardar(MouseEvent event) {
		this.dateFechaDevolucion.setValue(LocalDate.now());
		// this.controladorPadre.getVboxPanelTablas().setDisable(false);
		this.controladorPadre.getBordelPanelRetiro().setLeft(null);
		HerramientaService herramientaService = new HerramientaService(new DAOSQLFactory());
		DevolucionHerramientaService devolucionHerramientaService = new DevolucionHerramientaService(
				new DAOSQLFactory());
		RetiroHerramientaService retiroHerramientaService = new RetiroHerramientaService(new DAOSQLFactory());
		DevolucionHerramienta devolucionHerramienta = new DevolucionHerramienta();
		devolucionHerramienta.setIdDevolucionHerramienta(0);
		UsuarioLogueado usuarioLogueado = UsuarioLogueado.getInstancia();
		Usuario usuario = usuarioLogueado.getUsuarioLogueado();
		devolucionHerramienta.setUsuario(usuario);

		devolucionHerramienta.setFechaDevolucion(Fechas.localDateToCalendar(dateFechaDevolucion.getValue()));
		OrdenDeTrabajo ordenDeTrabajo = new OrdenDeTrabajo();
		if (!this.texOrdenDeTrabajo.getText().equals("")) {
			OrdenTrabajoDTO orden = this.controladorPadre.obtenerorden();
			ordenDeTrabajo.setId(orden.getId().get());
			devolucionHerramienta.setOrdenDeTrabajo(ordenDeTrabajo);
		}
		List<Herramienta> herramientas = new ArrayList<Herramienta>();
		List<HerramientaDTO> herramientaDTOs = this.listDevolucion.getItems();
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
			if (herramientaDTO.getActivoDisponible() == false) {
				herramienta.setEstado(EnumEstadoHerramienta.DISPONIBLE);
				herramientaService.cambiarEstadoDisponible(herramienta);
				retiroHerramientaService.cambiarEstadoDevuelto(herramienta);
			} else {
				herramienta.setEstado(EnumEstadoHerramienta.AVERIADO);
				herramientaService.cambiarEstadoAvariada(herramienta);
				retiroHerramientaService.cambiarEstadoDevuelto(herramienta);
				devolucionHerramienta.setComentario(herramientaDTO.getComentarioAveriado());
			}
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
			devolucionHerramienta.setHerramienta(herramienta);
			Tecnico tecnico = new Tecnico();
			tecnico.setIdTecnico(controladorPadre.obtenerTecnico().getIdTecnico().get());
			devolucionHerramienta.setTecnico(tecnico);
			herramientas.add(herramienta);
			devolucionHerramienta.setHerramientas(herramientas);
			if (herramientaDTO.getActivoDisponible()) {
				devolucionHerramienta.setEstado(EnumEstadoHerramienta.DISPONIBLE);
			} else {
				devolucionHerramienta.setEstado(EnumEstadoHerramienta.AVERIADO);
			}
			devolucionHerramientaService.agregarDevolucionHerramienta(devolucionHerramienta);
		}
		
		if (!this.texOrdenDeTrabajo.getText().equals("")) {
			OrdenDeTrabajoService service = new OrdenDeTrabajoService(new DAOSQLFactory());
			OrdenTrabajoDTO orden = this.controladorPadre.obtenerorden();
			if (service.ordenTrabajoSinDevolucionesPendientes(orden.getIdOrdenTrabajo().get()) == true){
				if (Dialogos.confirmacion("Confirmar operacion", "La Orden de Trabajo no adeuda ninguna devolucion ",
						"Desea dar como Realizada la Orden de Trabajo ?", "Aceptar", "Cancelar")) {
					int idMantis = Integer.parseInt(orden.getIdOrdenTrabajo().get());
					service.cambiarEstadoOrdenResuelta(idMantis);
					service.cambiarEstadoCerrada(ordenDeTrabajo);
				}
			}
		}
	}

	@FXML
	void limpiar(MouseEvent event) {
		this.texOrdenDeTrabajo.setText("");
		this.texTecnico.setText("");
		this.texUsuario.setText("");
		this.dateFechaDevolucion.setValue(LocalDate.now());
	}

	public void setListaPrestamo(ObservableList<HerramientaDTO> masterData) {
		this.listDevolucion.setItems(masterData);
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