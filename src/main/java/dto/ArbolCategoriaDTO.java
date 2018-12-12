package dto;

import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import util.TipoCategoria;

public class ArbolCategoriaDTO extends RecursiveTreeObject<ArbolCategoriaDTO> {
	private Integer id;
	private StringProperty nombre;
	private EstadoDTO activo;
	private ArbolCategoriaDTO padre;
	private List<ArbolCategoriaDTO> hijas;
	private TipoCategoria tipoCategoria;

	public ArbolCategoriaDTO(TipoCategoria tipoCategoria) {
		this.nombre = new SimpleStringProperty();
		this.hijas = new ArrayList<>();
		this.setActivo(EstadoDTO.ALTA);
		this.setTipoCategoria(tipoCategoria);
	}

	public ArbolCategoriaDTO() {
		this.nombre = new SimpleStringProperty();
		this.hijas = new ArrayList<>();
		this.setActivo(EstadoDTO.ALTA);
		this.setTipoCategoria(TipoCategoria.INSUMOS);
	}

	public ArbolCategoriaDTO(int idCategoria, String nombre, EstadoDTO activo, List<ArbolCategoriaDTO> hijas,
			ArbolCategoriaDTO padre, TipoCategoria tipoCategoria) {
		this.setId(idCategoria);
		this.nombre = new SimpleStringProperty(nombre);
		this.setActivo(activo);
		this.hijas = hijas;
		this.padre = padre;
		this.setTipoCategoria(tipoCategoria);
	}

	public boolean agregarHija(ArbolCategoriaDTO categoriaHija) {
		categoriaHija.setPadre(this);
		this.getChildren().add(categoriaHija);
		return this.hijas.add(categoriaHija);
	}

	public void borrarHija(ArbolCategoriaDTO categoriaHija) {
		if (!this.hijas.isEmpty()) {
			this.getChildren().remove(categoriaHija);
			this.hijas.remove(categoriaHija);
		}
	}

	public boolean tieneHijas() {
		if (this.hijas != null)
			return !this.hijas.isEmpty();
		return false;
	}
	
	public List<ArbolCategoriaDTO> getHijas()
	{
		if(tieneHijas()) return this.hijas;
		return null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StringProperty getNombreProperty() {
		return this.nombre;
	}

	public String getNombre() {
		return this.nombre.get();
	}

	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}

	public EstadoDTO getActivo() {
		return activo;
	}

	public void setActivo(EstadoDTO activo) {
		this.activo = activo;
	}

	public ArbolCategoriaDTO getPadre() {
		return this.padre;
	}

	public void setPadre(ArbolCategoriaDTO padre) {
		this.padre = padre;
	}

	public TipoCategoria getTipoCategoria() {
		return tipoCategoria;
	}

	public void setTipoCategoria(TipoCategoria tipoCategoria) {
		this.tipoCategoria = tipoCategoria;
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
		ArbolCategoriaDTO other = (ArbolCategoriaDTO) obj;
		if (nombre.get() == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.get().equals(other.nombre.get()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getNombre();
	}

}
