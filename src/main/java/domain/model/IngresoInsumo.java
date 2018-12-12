package domain.model;

import java.util.Calendar;

public class IngresoInsumo {
	private int idIngresoInsumo;
	private Deposito deposito;
	private Insumo insumo;
	private EnumTipoDeIngreso tipoIngreso;
	private Calendar fechaIngreso;
	private int cantidadIngreso;
	private OrdenDeTrabajo ordenDeTrabajo;
	private PedidoInsumo pedidoInsumo;
	private int estadoInsumoReservado;

	public IngresoInsumo() {
	}

	public IngresoInsumo(int idIngresoInsumo, Deposito deposito, Insumo insumo, EnumTipoDeIngreso tipoIngreso,
			Calendar fechaIngreso, int cantidadIngreso, OrdenDeTrabajo ordenDeTrabajo, PedidoInsumo pedidoInsumo,
			int estadoInsumoReservado) {
		this.idIngresoInsumo = idIngresoInsumo;
		this.deposito = deposito;
		this.insumo = insumo;
		this.tipoIngreso = tipoIngreso;
		this.fechaIngreso = fechaIngreso;
		this.cantidadIngreso = cantidadIngreso;
		this.ordenDeTrabajo = ordenDeTrabajo;
		this.pedidoInsumo = pedidoInsumo;
		this.estadoInsumoReservado = estadoInsumoReservado;
	}

	public int getIdIngresoInsumo() {
		return idIngresoInsumo;
	}

	public void setIdIngresoInsumo(int idIngresoInsumo) {
		this.idIngresoInsumo = idIngresoInsumo;
	}

	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}

	public Insumo getInsumo() {
		return insumo;
	}

	public void setInsumo(Insumo insumo) {
		this.insumo = insumo;
	}

	public EnumTipoDeIngreso getTipoIngreso() {
		return tipoIngreso;
	}

	public void setTipoIngreso(EnumTipoDeIngreso tipoIngreso) {
		this.tipoIngreso = tipoIngreso;
	}

	public Calendar getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Calendar fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public int getCantidadIngreso() {
		return cantidadIngreso;
	}

	public void setCantidadIngreso(int cantidadIngreso) {
		this.cantidadIngreso = cantidadIngreso;
	}

	public OrdenDeTrabajo getOrdenDeTrabajo() {
		return ordenDeTrabajo;
	}

	public void setOrdenDeTrabajo(OrdenDeTrabajo ordenDeTrabajo) {
		this.ordenDeTrabajo = ordenDeTrabajo;
	}

	public PedidoInsumo getPedidoInsumo() {
		return pedidoInsumo;
	}

	public void setPedidoInsumo(PedidoInsumo pedidoInsumo) {
		this.pedidoInsumo = pedidoInsumo;
	}

	public int getEstadoInsumoReservado() {
		return estadoInsumoReservado;
	}

	public void setEstadoInsumoReservado(int estadoInsumoReservado) {
		this.estadoInsumoReservado = estadoInsumoReservado;
	}

	@Override
	public String toString() {
		return "IngresoInsumo [idIngresoInsumo=" + idIngresoInsumo + ", deposito=" + deposito + ", insumo=" + insumo
				+ ", tipoIngreso=" + tipoIngreso + ", fechaIngreso=" + fechaIngreso + ", cantidadIngreso="
				+ cantidadIngreso + ", ordenDeTrabajo=" + ordenDeTrabajo + ", pedidoInsumo=" + pedidoInsumo
				+ ", estadoInsumoReservado=" + estadoInsumoReservado + "]";
	}

}
