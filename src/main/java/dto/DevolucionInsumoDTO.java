package dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class DevolucionInsumoDTO {
	private IntegerProperty idDevolucion;
	private UsuarioDTO usuario;
	private TecnicoDTO tecnico;
	private IntegerProperty cantidadNueva;
	private IntegerProperty cantidadUsado;
	private InsumoDTO insumo;
	private List<InsumoDTO> insumos;
	private DepositoDTO deposito;
	private LocalDate fecha;
	private OrdenTrabajoDTO ordenDeTrabajo;

	public DevolucionInsumoDTO() {
		this.idDevolucion = new SimpleIntegerProperty();
		this.usuario = null;
		this.tecnico = null;
		this.cantidadNueva = new SimpleIntegerProperty();
		this.cantidadUsado = new SimpleIntegerProperty();
		this.insumo = null;
		this.insumos = new ArrayList<InsumoDTO>();
		this.deposito = null;
		this.fecha = null;
		this.ordenDeTrabajo = null;
	}

	public DevolucionInsumoDTO(int idDevolucion, UsuarioDTO usuario, TecnicoDTO tecnico, int cantidadNueva,
			int cantidadUsado, int cantidadReservada, int devuelto, InsumoDTO insumo, DepositoDTO deposito,
			LocalDate fecha, OrdenTrabajoDTO ordenDeTrabajo) {
		this.idDevolucion = new SimpleIntegerProperty(idDevolucion);
		this.usuario = usuario;
		this.tecnico = tecnico;
		this.cantidadNueva = new SimpleIntegerProperty(cantidadNueva);
		this.cantidadUsado = new SimpleIntegerProperty(cantidadUsado);
		this.insumo = insumo;
		this.insumos = new ArrayList<InsumoDTO>();
		this.deposito = deposito;
		this.fecha = fecha;
		this.ordenDeTrabajo = ordenDeTrabajo;
	}

	public IntegerProperty getIdRetiroInsumo() {
		return idDevolucion;
	}

	public void setIdRetiroInsumo(int idRetiroInsumo) {
		this.idDevolucion.set(idRetiroInsumo);
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public TecnicoDTO getTecnico() {
		return tecnico;
	}

	public void setTecnico(TecnicoDTO tecnico) {
		this.tecnico = tecnico;
	}

	public IntegerProperty getCantidadNueva() {
		return cantidadNueva;
	}

	public void setCantidadNueva(int cantidadNueva) {
		this.cantidadNueva.set(cantidadNueva);
	}

	public IntegerProperty getCantidadUsado() {
		return cantidadUsado;
	}

	public void setCantidadUsado(int cantidadUsado) {
		this.cantidadUsado.set(cantidadUsado);
	}

	public InsumoDTO getInsumo() {
		return insumo;
	}

	public void setInsumo(InsumoDTO insumo) {
		this.insumo = insumo;
	}

	public List<InsumoDTO> getInsumos() {
		return insumos;
	}

	public void setInsumos(List<InsumoDTO> insumos) {
		this.insumos = insumos;
	}

	public DepositoDTO getDeposito() {
		return deposito;
	}

	public void setDeposito(DepositoDTO deposito) {
		this.deposito = deposito;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public OrdenTrabajoDTO getOrdenDeTrabajo() {
		return ordenDeTrabajo;
	}

	public void setOrdenDeTrabajo(OrdenTrabajoDTO ordenDeTrabajo) {
		this.ordenDeTrabajo = ordenDeTrabajo;
	}

}
