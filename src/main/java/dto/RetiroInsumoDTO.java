package dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Spinner;
import util.Spinners;

public class RetiroInsumoDTO {
	private IntegerProperty idRetiroInsumo;
	private UsuarioDTO usuario;
	private TecnicoDTO tecnico;
	private IntegerProperty cantidadNueva;
	private IntegerProperty cantidadUsado;
	private IntegerProperty cantidadReservada;
	private Spinner<Integer> spnCantidadNuevo;
	private Spinner<Integer> spnCantidadUsado;
	private IntegerProperty devuelto;
	private InsumoDTO insumo;
	private List<InsumoDTO> insumos;
	private DepositoDTO deposito;
	private LocalDate fecha;
	private OrdenTrabajoDTO ordenDeTrabajo;

	public RetiroInsumoDTO() {
		this.idRetiroInsumo = new SimpleIntegerProperty();
		this.usuario = null;
		this.tecnico = null;
		this.cantidadNueva = new SimpleIntegerProperty();
		this.cantidadReservada = new SimpleIntegerProperty();
		this.cantidadUsado = new SimpleIntegerProperty();
		this.insumo = null;
		this.insumos = new ArrayList<InsumoDTO>();
		this.deposito = null;
		this.fecha = LocalDate.now();
		this.ordenDeTrabajo = null;
		this.devuelto = new SimpleIntegerProperty();
		this.spnCantidadNuevo = new Spinners().crearSpinner(0, 999999, 0);
		this.spnCantidadUsado = new Spinners().crearSpinner(0, 999999, 0);
	}

	public RetiroInsumoDTO(int idRetiroInsumo, UsuarioDTO usuario, TecnicoDTO tecnico, int cantidadNueva,
			int cantidadUsado, int cantidadReservada, int devuelto, InsumoDTO insumo, DepositoDTO deposito,
			LocalDate fecha, OrdenTrabajoDTO ordenDeTrabajo) {
		this.idRetiroInsumo = new SimpleIntegerProperty(idRetiroInsumo);
		this.usuario = usuario;
		this.tecnico = tecnico;
		this.cantidadNueva = new SimpleIntegerProperty(cantidadNueva);
		this.cantidadUsado = new SimpleIntegerProperty(cantidadUsado);
		this.cantidadReservada = new SimpleIntegerProperty(cantidadReservada);
		this.devuelto = new SimpleIntegerProperty(devuelto);
		this.insumo = insumo;
		this.insumos = new ArrayList<InsumoDTO>();
		this.deposito = deposito;
		this.fecha = fecha;
		this.ordenDeTrabajo = ordenDeTrabajo;
		this.spnCantidadNuevo = new Spinners().crearSpinner(0, cantidadNueva, 0);
		this.spnCantidadUsado = new Spinners().crearSpinner(0, cantidadUsado, 0);
		new Spinners().setValoresSpinner(spnCantidadNuevo, 0, cantidadNueva, 0);
		new Spinners().setValoresSpinner(spnCantidadUsado, 0, cantidadUsado, 0);
	}

	public void setSpnCantidadNuevo(Integer maxValue) {
		new Spinners().setValoresSpinner(spnCantidadNuevo, 0, maxValue, 0);
	}

	public void setSpnCantidadUsado(Integer maxValue) {
		new Spinners().setValoresSpinner(spnCantidadUsado, 0, maxValue, 0);
	}

	public Spinner<Integer> getSpnCantidadNuevo() {
		return spnCantidadNuevo;
	}

	public void setSpnCantidadNuevo(Spinner<Integer> spnCantidadNuevo) {
		this.spnCantidadNuevo = spnCantidadNuevo;
	}

	public Spinner<Integer> getSpnCantidadUsado() {
		return spnCantidadUsado;
	}

	public void setSpnCantidadUsado(Spinner<Integer> spnCantidadUsado) {
		this.spnCantidadUsado = spnCantidadUsado;
	}

	public Integer getValorSpinnerCantNuevo() {
		return Integer.parseInt(this.spnCantidadNuevo.getEditor().getText());
	}

	public Integer getValorSpinnerCantUsado() {
		return Integer.parseInt(this.spnCantidadUsado.getEditor().getText());
	}

	public IntegerProperty getIdRetiroInsumo() {
		return idRetiroInsumo;
	}

	public void setIdRetiroInsumo(int idRetiroInsumo) {
		this.idRetiroInsumo.set(idRetiroInsumo);
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

	public IntegerProperty getCantidadReservada() {
		return cantidadReservada;
	}

	public void setCantidadReservada(int cantidadReservada) {
		this.cantidadReservada.set(cantidadReservada);
	}

	public IntegerProperty getDevuelto() {
		return devuelto;
	}

	public void setDevuelto(int devuelto) {
		this.devuelto.set(devuelto);
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

	@Override
	public String toString() {
		return "RetiroInsumoDTO [idRetiroInsumo=" + idRetiroInsumo + ", usuario=" + usuario + ", tecnico=" + tecnico
				+ ", cantidadNueva=" + cantidadNueva + ", cantidadUsado=" + cantidadUsado + ", cantidadReservada="
				+ cantidadReservada + ", devuelto=" + devuelto + ", insumo=" + insumo + ", insumos=" + insumos
				+ ", deposito=" + deposito + ", fecha=" + fecha + ", ordenDeTrabajo=" + ordenDeTrabajo + "]";
	}

}
