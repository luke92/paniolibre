package dto;

import java.time.LocalDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import presentacion.controladores.HerramientaABM;
import util.TipoCategoria;

public class HerramientaDTO {

	private IntegerProperty idHerramienta;
	private StringProperty codigo;
	private StringProperty nombre;
	private UbicacionDepositoDTO ubicacion;
	private CategoriaHerramientaDTO categoria;
	private ArbolCategoriaDTO categoriaArbol;
	private StringProperty factura;
	private StringProperty numeroActivo;
	private StringProperty comentario;
	private StringProperty mecanismoAdquisicion;
	private LocalDate fechaAdquisicion;
	private LocalDate fechaGarantiaExpiracion;
	private LocalDate fechaUltimaModificacion;
	private EnumEstadoHerramientaDTO estadoHerramienta;
	private BooleanProperty activoDisponible;
	private EstadoDTO activo;
	private StringProperty proveedor;
	private StringProperty marca;
	private StringProperty fecha; ///// revisar ordenar
	private Button btnBorrar; ////
	HerramientaABM controlador;
	private Button abrirpanel;
	private StringProperty comentarioAveriado;

	private ObservableList<HerramientaDTO> masterData = FXCollections.observableArrayList();

	public HerramientaDTO() {

		this.idHerramienta = new SimpleIntegerProperty();
		this.codigo = new SimpleStringProperty();
		this.nombre = new SimpleStringProperty();
		this.ubicacion = null;
		this.categoria = null;
		this.factura = new SimpleStringProperty();
		this.numeroActivo = new SimpleStringProperty();
		this.comentario = new SimpleStringProperty();
		this.mecanismoAdquisicion = new SimpleStringProperty();
		this.fechaAdquisicion = null;
		this.fechaGarantiaExpiracion = null;
		this.estadoHerramienta = null;
		this.activo = null;
		this.fecha = new SimpleStringProperty();
		this.proveedor = new SimpleStringProperty();
		this.marca = new SimpleStringProperty();
		this.activoDisponible = new SimpleBooleanProperty();
		this.comentarioAveriado = new SimpleStringProperty();
		this.categoriaArbol = new ArbolCategoriaDTO(TipoCategoria.HERRAMIENTAS);
		this.fechaUltimaModificacion = null;
	}

	public HerramientaDTO(int idHerramienta, String codigo, String nombre, UbicacionDepositoDTO ubicacion,
			CategoriaHerramientaDTO categoria, String factura, String numeroActivo, String comentario,
			String mecanismoAdquisicion, LocalDate fechaAdquisicion, LocalDate fechaGarantiaExpiracion,
			LocalDate fechaUltimaModificacion, EnumEstadoHerramientaDTO estado, int activo, String proveedor,
			String marca, Button btnBorrar, ObservableList<HerramientaDTO> masterData, HerramientaABM controlador,
			Button abrirpanel) {

		this.idHerramienta = new SimpleIntegerProperty(idHerramienta);
		this.codigo = new SimpleStringProperty(codigo);
		this.nombre = new SimpleStringProperty(nombre);
		this.ubicacion = ubicacion;
		this.categoria = categoria;
		this.factura = new SimpleStringProperty(factura);
		this.numeroActivo = new SimpleStringProperty(numeroActivo);
		this.comentario = new SimpleStringProperty(comentario);
		this.mecanismoAdquisicion = new SimpleStringProperty(mecanismoAdquisicion);
		this.fechaAdquisicion = fechaAdquisicion;
		this.fechaGarantiaExpiracion = fechaGarantiaExpiracion;
		this.fechaUltimaModificacion = fechaUltimaModificacion;
		this.estadoHerramienta = estado;
		this.activo = (activo == 1) ? EstadoDTO.ALTA : EstadoDTO.BAJA;
		this.proveedor = new SimpleStringProperty(proveedor);
		this.marca = new SimpleStringProperty(marca);
		if (this.fechaGarantiaExpiracion != null)
			this.fecha = new SimpleStringProperty(this.fechaGarantiaExpiracion.toString());
		else
			this.fecha = new SimpleStringProperty("Sin Garantia");
		this.btnBorrar = btnBorrar;
		this.masterData = masterData;
		this.controlador = controlador;
		this.abrirpanel = abrirpanel;
		this.comentarioAveriado = new SimpleStringProperty();
		this.categoriaArbol = new ArbolCategoriaDTO(TipoCategoria.HERRAMIENTAS);
		btnBorrar.setOnAction(event -> {
			this.controlador.cargarDtoAmodificar(this);
			this.abrirpanel.fire();

		});
	}

	public HerramientaDTO(int idHerramienta, String codigo, String nombre, UbicacionDepositoDTO ubicacion,
			CategoriaHerramientaDTO categoria, String factura, String numeroActivo, String comentario,
			String mecanismoAdquisicion, LocalDate fechaAdquisicion, LocalDate fechaGarantiaExpiracion,
			LocalDate fechaUltimaModificacion, EnumEstadoHerramientaDTO estado, int activo, String proveedor,
			String marca, Boolean activoDisponible) {

		this.idHerramienta = new SimpleIntegerProperty(idHerramienta);
		this.codigo = new SimpleStringProperty(codigo);
		this.nombre = new SimpleStringProperty(nombre);
		this.ubicacion = ubicacion;
		this.categoria = categoria;
		this.factura = new SimpleStringProperty(factura);
		this.numeroActivo = new SimpleStringProperty(numeroActivo);
		this.comentario = new SimpleStringProperty(comentario);
		this.mecanismoAdquisicion = new SimpleStringProperty(mecanismoAdquisicion);
		this.fechaAdquisicion = fechaAdquisicion;
		this.fechaGarantiaExpiracion = fechaGarantiaExpiracion;
		this.estadoHerramienta = estado;
		this.activo = (activo == 1) ? EstadoDTO.ALTA : EstadoDTO.BAJA;
		this.proveedor = new SimpleStringProperty(proveedor);
		this.comentarioAveriado = new SimpleStringProperty();
		this.marca = new SimpleStringProperty(marca);
		if (this.fechaGarantiaExpiracion != null)
			this.fecha = new SimpleStringProperty(this.fechaGarantiaExpiracion.toString());
		else
			this.fecha = new SimpleStringProperty("Sin Garantia ");
		this.activoDisponible = new SimpleBooleanProperty(activoDisponible);
		this.categoriaArbol = new ArbolCategoriaDTO(TipoCategoria.HERRAMIENTAS);
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}

	public IntegerProperty getIdHerramienta() {
		return idHerramienta;
	}

	public void setIdHerramienta(int idHerramienta) {
		this.idHerramienta.set(idHerramienta);
	}

	public StringProperty getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo.set(codigo);
	}

	public StringProperty getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}

	public UbicacionDepositoDTO getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(UbicacionDepositoDTO ubicacion) {
		this.ubicacion = ubicacion;
	}

	public CategoriaHerramientaDTO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaHerramientaDTO categoria) {
		this.categoria = categoria;
	}

	public StringProperty getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura.set(factura);
	}

	public StringProperty getNumeroActivo() {
		return numeroActivo;
	}

	public void setNumeroActivo(String numeroActivo) {
		this.numeroActivo.set(numeroActivo);
	}

	public StringProperty getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario.set(comentario);
	}

	public StringProperty getMecanismoAdquisicion() {
		return mecanismoAdquisicion;
	}

	public void setMecanismoAdquisicion(String mecanismoAdquisicion) {
		this.mecanismoAdquisicion.set(mecanismoAdquisicion);
	}

	public LocalDate getFechaAdquisicion() {
		return fechaAdquisicion;
	}

	public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}

	public StringProperty getFechaGarantiaExpiracion() {
		return fecha;
	}

	public LocalDate getFechaGarantia() {
		return fechaGarantiaExpiracion;
	}

	public void setFechaGarantiaExpiracion(LocalDate fechaGarantiaExpiracion) {
		this.fechaGarantiaExpiracion = fechaGarantiaExpiracion;
	}

	public EnumEstadoHerramientaDTO getEstadoHerramienta() {
		return estadoHerramienta;
	}

	public void setEstadoHerramienta(EnumEstadoHerramientaDTO estadoHerramienta) {
		this.estadoHerramienta = estadoHerramienta;
	}

	public EstadoDTO getActivo() {
		return activo;
	}

	public void setActivo(EstadoDTO activo) {
		this.activo = activo;
	}

	public StringProperty getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor.set(proveedor);
	}

	public StringProperty getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca.set(marca);
	}

	public Button getBtnBorrar() {
		return btnBorrar;
	}

	public void setBtnBorrar(Button btnBorrar) {
		this.btnBorrar = btnBorrar;
	}

	public final StringProperty comentarioAveriadoProperty() {
		return this.comentarioAveriado;
	}

	public final BooleanProperty activoDisponibleProperty() {
		return this.activoDisponible;
	}

	public String getComentarioAveriado() {
		return comentarioAveriado.get();
	}

	public StringProperty getComentarioAveriadoProperti() {
		return comentarioAveriado;
	}

	public final Boolean getActivoDisponible() {
		return activoDisponible.get();
	}

	public void setComentarioAveriado(String comentario) {
		this.comentarioAveriado.set(comentario);
	}

	public final void setActivo(Boolean activo) {
		this.activoDisponible.set(activo);
	}

	@Override
	public String toString() {
		return "Herramienta -> Nombre: " + nombre.get() + ", Codigo: " + codigo.get();
	}

	public ArbolCategoriaDTO getCategoriaArbol() {
		return categoriaArbol;
	}

	public void setCategoriaArbol(ArbolCategoriaDTO categoriaArbol) {
		this.categoriaArbol = categoriaArbol;
	}

	public LocalDate getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}

	public void setFechaUltimaModificacion(LocalDate fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}
}