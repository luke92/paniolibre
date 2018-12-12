package domain.model;

import dto.CategoriaInsumoDTO;

public class CategoriaInsumo {
	private int idCategoriaInsumo;
	private String nombreCategoriaInsumo;
	private Estado activo;
	private CategoriaInsumo categoriaPadre;

	public CategoriaInsumo() {

	}

	public CategoriaInsumo(int idCategoriaInsumo, String nombre, Estado activo, CategoriaInsumo categoriaPadre) {
		this.idCategoriaInsumo = idCategoriaInsumo;
		this.nombreCategoriaInsumo = nombre;
		this.activo = activo;
		this.categoriaPadre = categoriaPadre;
	}

	public String getNombre() {
		return nombreCategoriaInsumo;
	}

	public void setNombre(String nombre) {
		this.nombreCategoriaInsumo = nombre;
	}

	public int getIdCategoriaInsumo() {
		return idCategoriaInsumo;
	}

	public void setIdCategoriaInsumo(int idCategoriaInsumo) {
		this.idCategoriaInsumo = idCategoriaInsumo;
	}

	public Estado getActivo() {
		return activo;
	}

	public void setActivo(Estado activo) {
		this.activo = activo;
	}

	public CategoriaInsumo getCategoriaPadre() {
		return categoriaPadre;
	}

	public void setCategoriaPadre(CategoriaInsumo categoriaPadre) {
		this.categoriaPadre = categoriaPadre;
	}

	@Override
	public String toString() {
		return this.getNombre();
	}

	public CategoriaInsumoDTO getCategoriaInsumoDTO() {
		CategoriaInsumoDTO categoria = new CategoriaInsumoDTO();
		categoria.getIdCategoria().set(this.getIdCategoriaInsumo());
		categoria.getNombre().set(this.getNombre());
		return categoria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idCategoriaInsumo;
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
		CategoriaInsumo other = (CategoriaInsumo) obj;

		return (idCategoriaInsumo != other.idCategoriaInsumo);
	}

	public CategoriaInsumoDTO getDTO() {
		CategoriaInsumoDTO categoria = new CategoriaInsumoDTO();
		categoria.getIdCategoria().set(this.getIdCategoriaInsumo());
		categoria.getNombre().set(this.getNombre());
		categoria.setActivo(this.getActivo().getDTO());
		if (this.categoriaPadre != null)
			categoria.setCategoriaPadre(this.getCategoriaPadre().getDTO());
		return categoria;
	}
}