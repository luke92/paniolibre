package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CategoriaHerramientaDTO {

	private IntegerProperty idCategoria;
	private StringProperty nombre;
	private CategoriaHerramientaDTO categoriaPadre;
	private EstadoDTO activo;

	public CategoriaHerramientaDTO() {

		this.idCategoria = new SimpleIntegerProperty();
		this.nombre = new SimpleStringProperty();
		this.categoriaPadre = null;
		this.activo = null;

	}

	public CategoriaHerramientaDTO(int idCategoria, String nombre, CategoriaHerramientaDTO categoriaPadre, int activo) {
		this.idCategoria = new SimpleIntegerProperty(idCategoria);
		this.nombre = new SimpleStringProperty(nombre);
		this.categoriaPadre = categoriaPadre;
		this.activo = (activo == 1) ? EstadoDTO.ALTA : EstadoDTO.BAJA;
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

	public CategoriaHerramientaDTO getCategoriaPadre() {
		return categoriaPadre;
	}

	public void setCategoriaPadre(CategoriaHerramientaDTO categoriaPadre) {
		this.categoriaPadre = categoriaPadre;
	}

	public EstadoDTO getActivo() {
		return activo;
	}

	public void setActivo(EstadoDTO activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return nombre.get();
	}

}
