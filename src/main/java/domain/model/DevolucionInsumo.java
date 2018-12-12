package domain.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DevolucionInsumo {
	private RetiroInsumo retiroInsumo;
	private int idDevoluciones;
	private Usuario usuario;
	private Tecnico tecnico;
	private int cantidadNueva;
	private int cantidadUsado;
	private Insumo insumo;
	private List<Insumo> insumos;
	private Deposito deposito;
	private Calendar fecha;
	private OrdenDeTrabajo ordenDeTrabajo;

	public DevolucionInsumo() {
	}

	public DevolucionInsumo(int idDevoluciones, Usuario usuario, Tecnico tecnico, int cantidadNueva, int cantidadUsado,
			Insumo insumo, Deposito deposito, Calendar fecha, OrdenDeTrabajo ordenDeTrabajo) {
		this.idDevoluciones = idDevoluciones;
		this.usuario = usuario;
		this.tecnico = tecnico;
		this.cantidadNueva = cantidadNueva;
		this.cantidadUsado = cantidadUsado;
		this.insumo = insumo;
		this.deposito = deposito;
		this.fecha = fecha;
		this.ordenDeTrabajo = ordenDeTrabajo;
		this.insumos = new ArrayList<Insumo>();
	}

	public void setRetiroInsumo(RetiroInsumo retiroInsumo) {
		this.retiroInsumo = retiroInsumo;
	}

	public RetiroInsumo getRetiro() {
		return this.retiroInsumo;
	}

	public int getIdidDevoluciones() {
		return idDevoluciones;
	}

	public void agregarInsumo(Insumo insumo) {
		this.insumos.add(insumo);
	}

	public void setIdDevolucionInsumo(int idDevolucionInsumo) {
		this.idDevoluciones = idDevolucionInsumo;
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

}
