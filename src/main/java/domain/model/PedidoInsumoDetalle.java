package domain.model;

import dto.PedidoInsumoDetalleDTO;

public class PedidoInsumoDetalle {
	private PedidoInsumo pedidoInsumo;
	private Insumo insumo;
	private Integer cantidad;

	public PedidoInsumoDetalle() {

	}

	public PedidoInsumoDetalle(PedidoInsumo pedidoInsumo, Insumo insumo, int cantidad) {
		this.pedidoInsumo = pedidoInsumo;
		this.insumo = insumo;
		this.cantidad = cantidad;
	}

	public PedidoInsumo getPedidoInsumo() {
		return pedidoInsumo;
	}

	public void setPedidoInsumo(PedidoInsumo pedidoInsumo) {
		this.pedidoInsumo = pedidoInsumo;
	}

	public Insumo getInsumo() {
		return insumo;
	}

	public void setInsumo(Insumo insumo) {
		this.insumo = insumo;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "Pedido de Insumo " + pedidoInsumo.getIdPedidoInsumo() + ", insumo " + insumo + ", cantidad " + cantidad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((insumo == null) ? 0 : insumo.hashCode());
		result = prime * result + ((pedidoInsumo == null) ? 0 : pedidoInsumo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoInsumoDetalle other = (PedidoInsumoDetalle) obj;
		if (insumo == null) {
			if (other.insumo != null)
				return false;
		} else if (!insumo.equals(other.insumo))
			return false;
		if (pedidoInsumo == null) {
			if (other.pedidoInsumo != null)
				return false;
		} else if (!pedidoInsumo.equals(other.pedidoInsumo))
			return false;
		return true;
	}

	public PedidoInsumoDetalleDTO getDTO() {
		PedidoInsumoDetalleDTO pedido = new PedidoInsumoDetalleDTO();
		pedido.setCantidad(this.getCantidad());
		pedido.setInsumo(this.getInsumo().getInsumoDTO());
		pedido.setPedidoInsumo(this.getPedidoInsumo().getDTO());
		return pedido;
	}

}