package domain.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RetiroInsumo {
	private int idRetiroInsumo;
	private Usuario usuario;
	private Tecnico tecnico;
	private int cantidadNueva;
	private int cantidadUsado;
	private int cantidadReservada;
	private int devuelto;
	private Insumo insumo;
	private List<Insumo> insumos;
	private Deposito deposito;
	private Calendar fecha;
	private OrdenDeTrabajo ordenDeTrabajo;

	public RetiroInsumo() {
	}

	public RetiroInsumo(int idRetiroInsumo, Usuario usuario, Tecnico tecnico, int cantidadNueva, int cantidadUsado,
			Insumo insumo, Deposito deposito, Calendar fecha, OrdenDeTrabajo ordenDeTrabajo, int cantidadReservada,
			int devuelto) {
		this.idRetiroInsumo = idRetiroInsumo;
		this.usuario = usuario;
		this.tecnico = tecnico;
		this.cantidadNueva = cantidadNueva;
		this.cantidadUsado = cantidadUsado;
		this.insumo = insumo;
		this.deposito = deposito;
		this.fecha = fecha;
		this.ordenDeTrabajo = ordenDeTrabajo;
		this.insumos = new ArrayList<Insumo>();
		this.cantidadReservada = cantidadReservada;
		this.devuelto = devuelto;
	}

	public int getIdRetiroInsumo() {
		return idRetiroInsumo;
	}

	public void agregarInsumo(Insumo insumo) {
		this.insumos.add(insumo);
	}

	public void setIdRetiroInsumo(int idRetiroInsumo) {
		this.idRetiroInsumo = idRetiroInsumo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
	}

	public int getCantidadNueva() {
		return cantidadNueva;
	}

	public void setCantidadNueva(int cantidadNueva) {
		this.cantidadNueva = cantidadNueva;
	}

	public int getCantidadUsado() {
		return cantidadUsado;
	}

	public void setCantidadUsado(int cantidadUsado) {
		this.cantidadUsado = cantidadUsado;
	}

	public Insumo getInsumo() {
		return insumo;
	}

	public void setInsumo(Insumo insumo) {
		this.insumo = insumo;
	}

	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}

	public Calendar getFecha() {
		return fecha;
	}

	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}

	public OrdenDeTrabajo getOrdenDeTrabajo() {
		return ordenDeTrabajo;
	}

	public void setOrdenDeTrabajo(OrdenDeTrabajo ordenDeTrabajo) {
		this.ordenDeTrabajo = ordenDeTrabajo;
	}

	public List<Insumo> getInsumos() {
		return insumos;
	}

	public void setInsumos(List<Insumo> insumos) {
		this.insumos = insumos;
	}

	public int getCantidadReservada() {
		return cantidadReservada;
	}

	public void setCantidadReservada(int cantidadReservada) {
		this.cantidadReservada = cantidadReservada;
	}

	public int getDevuelto() {
		return devuelto;
	}

	public void setDevuelto(int devuelto) {
		this.devuelto = devuelto;
	}

}