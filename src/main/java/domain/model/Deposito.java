package domain.model;

import dto.DepositoDTO;

public class Deposito {
	private Integer idDeposito;
	private String nombre;
	private String comentario;
	private Estado activo;

	public Deposito() {

	}

	public Deposito(int idDeposito, String nombre, String comentario, Estado activo) {
		this.idDeposito = idDeposito;
		this.nombre = nombre;
		this.comentario = comentario;
		this.activo = activo;
	}

	public int getIdDeposito() {
		return idDeposito;
	}

	public void setIdDeposito(int idDeposito) {
		this.idDeposito = idDeposito;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Estado getActivo() {
		return activo;
	}

	public void setActivo(Estado activo) {
		this.activo = activo;
	}

	public DepositoDTO getDTO() {
		DepositoDTO deposito = new DepositoDTO();
		deposito.getIdDeposito().set(this.getIdDeposito());
		deposito.getNombre().set(this.getNombre());
		deposito.getComentario().set(this.getComentario());
		return deposito;
	}

}