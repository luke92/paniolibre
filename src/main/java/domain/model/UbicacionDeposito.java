package domain.model;

import dto.UbicacionDepositoDTO;

public class UbicacionDeposito {
	private Integer idUbicacionDeposito;
	private String nombre;
	private Deposito deposito;
	private Estado estado; // ESTA CLASE YA ESTA

	public UbicacionDeposito() {
	}

	public UbicacionDeposito(int idUbicacionDeposito, String nombre, Deposito deposito, Estado estado) {
		this.idUbicacionDeposito = idUbicacionDeposito;
		this.nombre = nombre;
		this.deposito = deposito;
		this.estado = estado;
	}

	public int getIdUbicacionDeposito() {
		return idUbicacionDeposito;
	}

	public void setIdUbicacionDeposito(int idUbicacionDeposito) {
		this.idUbicacionDeposito = idUbicacionDeposito;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public UbicacionDepositoDTO getDTO() {
		UbicacionDepositoDTO ubicacion = new UbicacionDepositoDTO();
		ubicacion.getIdUbicacionDeposito().set(this.getIdUbicacionDeposito());
		ubicacion.getNombre().set(this.getNombre());
		ubicacion.setDeposito(this.getDeposito().getDTO());
		return ubicacion;
	}
}