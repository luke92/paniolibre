package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UbicacionDepositoDTO {

	private IntegerProperty idUbicacionDeposito;
	private StringProperty nombre;
	private DepositoDTO deposito;
	private EstadoDTO estado;

	public UbicacionDepositoDTO() {
		this.idUbicacionDeposito = new SimpleIntegerProperty();
		this.nombre = new SimpleStringProperty();
		this.deposito = null;
		this.estado = null;
	}

	public UbicacionDepositoDTO(int idUbicacionDeposito, String nombre, DepositoDTO deposito, int activo) {
		this.idUbicacionDeposito = new SimpleIntegerProperty(idUbicacionDeposito);
		this.nombre = new SimpleStringProperty(nombre);
		this.deposito = deposito;
		this.estado = (activo == 1) ? EstadoDTO.ALTA : EstadoDTO.BAJA;
	}

	public IntegerProperty getIdUbicacionDeposito() {
		return idUbicacionDeposito;
	}

	public void setIdUbicacionDeposito(int idUbicacionDeposito) {
		this.idUbicacionDeposito.set(idUbicacionDeposito);
	}

	public StringProperty getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}

	public DepositoDTO getDeposito() {
		return deposito;
	}

	public void setDeposito(DepositoDTO deposito) {
		this.deposito = deposito;
	}

	public EstadoDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return nombre.get() + "-" + deposito.getNombre().get();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		UbicacionDepositoDTO other = (UbicacionDepositoDTO) obj;
		if (toString() == null) {
			if (other.toString() != null)
				return false;
		} else if (!toString().equals(other.toString()))
			return false;
		return true;
	}

}