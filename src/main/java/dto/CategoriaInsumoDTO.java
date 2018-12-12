package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CategoriaInsumoDTO {
	private IntegerProperty idCategoria;
	private StringProperty nombre;
	private CategoriaInsumoDTO categoriaPadre;
	private EstadoDTO activo;

	public CategoriaInsumoDTO() {
		this.idCategoria = new SimpleIntegerProperty();
		this.nombre = new SimpleStringProperty();
		this.categoriaPadre = null;
		this.activo = null;
	}

	public CategoriaInsumoDTO(IntegerProperty idCategoria, StringProperty nombre, CategoriaInsumoDTO categoriaPadre,
			EstadoDTO activo) {
		this.idCategoria = idCategoria;
		this.nombre = nombre;
		this.categoriaPadre = categoriaPadre;
		this.activo = activo;
	}

	public IntegerProperty getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria.set(idCategoria);
	}

	public StringProperty getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}

	public CategoriaInsumoDTO getCategoriaPadre() {
		return categoriaPadre;
	}

	public void setCategoriaPadre(CategoriaInsumoDTO categoriaPadre) {
		this.categoriaPadre = categoriaPadre;
	}

	public EstadoDTO getActivo() {
		return activo;
	}

	public void setActivo(EstadoDTO activo) {
		this.activo = activo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre.get() == null) ? 0 : nombre.get().hashCode());
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
		CategoriaInsumoDTO other = (CategoriaInsumoDTO) obj;
		if (nombre.get() == null) {
			if (other.nombre.get() != null)
				return false;
		} else if (!nombre.get().equals(other.nombre.get()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.getNombre().get();
	}

}
