package dto;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Spinner;
import util.Spinners;

public class PedidoInsumoDetalleDTO {
	private PedidoInsumoDTO pedidoInsumo;
	private InsumoDTO insumo;
	private List<InsumoDTO> listaInsumos;
	private IntegerProperty cantidad;
	private Spinner<Integer> spnCantidad;

	public PedidoInsumoDetalleDTO() {
		this.cantidad = new SimpleIntegerProperty();
		Spinners spinners = new Spinners();
		spnCantidad = spinners.crearSpinner(0, 99999999, 0);
		listaInsumos = new ArrayList<>();
	}

	public PedidoInsumoDetalleDTO(PedidoInsumoDTO pedidoInsumo, InsumoDTO insumo, int cantidad) {
		this.pedidoInsumo = pedidoInsumo;
		this.insumo = insumo;
		this.cantidad = new SimpleIntegerProperty(cantidad);
		Spinners spinners = new Spinners();
		spnCantidad = spinners.crearSpinner(0, 99999999, cantidad);
		listaInsumos = new ArrayList<>();
	}

	public void agregarInsumo(InsumoDTO insumo) {
		this.listaInsumos.add(insumo);
	}

	public void setCantidad(int cantidad) {
		this.cantidad.set(cantidad);
		Spinners spinners = new Spinners();
		spinners.setValoresSpinner(spnCantidad, 0, 99999999, cantidad);
	}

	public int getCantidad() {
		return this.cantidad.get();
	}

	public int getCantidadSpinner() {
		return Integer.parseInt(this.spnCantidad.getEditor().getText().trim());
	}

	public PedidoInsumoDTO getPedidoInsumo() {
		return pedidoInsumo;
	}

	public void setPedidoInsumo(PedidoInsumoDTO pedidoInsumo) {
		this.pedidoInsumo = pedidoInsumo;
	}

	public InsumoDTO getInsumo() {
		return insumo;
	}

	public void setInsumo(InsumoDTO insumo) {
		this.insumo = insumo;
	}

	public IntegerProperty getCantidadProperty() {
		return cantidad;
	}

	public void setCantidad(IntegerProperty cantidad) {
		this.cantidad = cantidad;
	}

	public Spinner<Integer> getSpnCantidad() {
		return spnCantidad;
	}

	public void setSpnCantidad(Spinner<Integer> spnCantidad) {
		this.spnCantidad = spnCantidad;
	}

	public List<InsumoDTO> getListaInsumos() {
		return listaInsumos;
	}

	public void setListaInsumos(List<InsumoDTO> listaInsumos) {
		this.listaInsumos = listaInsumos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cantidad == null) ? 0 : cantidad.hashCode());
		result = prime * result + ((insumo == null) ? 0 : insumo.hashCode());
		result = prime * result + ((listaInsumos == null) ? 0 : listaInsumos.hashCode());
		result = prime * result + ((pedidoInsumo == null) ? 0 : pedidoInsumo.hashCode());
		result = prime * result + ((spnCantidad == null) ? 0 : spnCantidad.hashCode());
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
		PedidoInsumoDetalleDTO other = (PedidoInsumoDetalleDTO) obj;
		if (cantidad == null) {
			if (other.cantidad != null)
				return false;
		} else if (!cantidad.equals(other.cantidad))
			return false;
		if (insumo == null) {
			if (other.insumo != null)
				return false;
		} else if (!insumo.equals(other.insumo))
			return false;
		if (listaInsumos == null) {
			if (other.listaInsumos != null)
				return false;
		} else if (!listaInsumos.equals(other.listaInsumos))
			return false;
		if (pedidoInsumo == null) {
			if (other.pedidoInsumo != null)
				return false;
		} else if (!pedidoInsumo.equals(other.pedidoInsumo))
			return false;
		if (spnCantidad == null) {
			if (other.spnCantidad != null)
				return false;
		} else if (!spnCantidad.equals(other.spnCantidad))
			return false;
		return true;
	}
	
	
	
}
