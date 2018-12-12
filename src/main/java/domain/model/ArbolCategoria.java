package domain.model;

import java.util.ArrayList;
import java.util.List;

import dto.ArbolCategoriaDTO;
import util.TipoCategoria;

public class ArbolCategoria {
	private int id;
	private String nombre;
	private Estado activo;
	private ArbolCategoria padre;
	private List<ArbolCategoria> hijas;
	private TipoCategoria tipoCategoria;

	public ArbolCategoria() {
		this.activo = Estado.ALTA;
		this.hijas = new ArrayList<>();
		this.tipoCategoria = TipoCategoria.INSUMOS;
	}

	public ArbolCategoria(TipoCategoria tipoCategoria) {
		this.activo = Estado.ALTA;
		this.hijas = new ArrayList<>();
		this.tipoCategoria = tipoCategoria;
	}

	public TipoCategoria getTipoCategoria() {
		return this.tipoCategoria;
	}

	public void setTipoCategoria(TipoCategoria tipoCategoria) {
		this.tipoCategoria = tipoCategoria;
	}

	public boolean agregarHija(ArbolCategoria categoriaHija) {
		categoriaHija.setPadre(this);
		return this.hijas.add(categoriaHija);
	}

	public ArbolCategoria borrarHija(ArbolCategoria categoriaHija) {
		for (int i = 0; i < this.hijas.size(); i++) {
			if (this.hijas.get(i).equals(categoriaHija)) {
				return this.hijas.remove(i);
			}
		}
		return null;
	}

	public ArbolCategoria(int id, String nombre, Estado activo, ArbolCategoria padre, List<ArbolCategoria> hijas,
			TipoCategoria tipoCategoria) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.activo = activo;
		this.padre = padre;
		this.hijas = hijas;
		this.tipoCategoria = tipoCategoria;
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
		ArbolCategoria other = (ArbolCategoria) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Estado getActivo() {
		return activo;
	}

	public void setActivo(Estado activo) {
		this.activo = activo;
	}

	public ArbolCategoria getPadre() {
		return padre;
	}

	public void setPadre(ArbolCategoria padre) {
		this.padre = padre;
	}

	public List<ArbolCategoria> getHijas() {
		return hijas;
	}

	public void setHijas(List<ArbolCategoria> hijas) {
		this.hijas = hijas;
	}

	public ArbolCategoriaDTO getDTO() {
		ArbolCategoriaDTO categoriaDTO = new ArbolCategoriaDTO();
		categoriaDTO.setId(this.getId());
		categoriaDTO.setActivo(this.getActivo().getDTO());
		categoriaDTO.setNombre(this.getNombre());
		categoriaDTO.setTipoCategoria(this.tipoCategoria);
		if (this.hijas != null) {
			for (int i = 0; i < this.hijas.size(); i++) {
				ArbolCategoriaDTO categoriaHija = this.getHijas().get(i).getDTO();
				categoriaDTO.agregarHija(categoriaHija);
			}
		}
		return categoriaDTO;
	}

	@Override
	public String toString() {
		return getNombre();
	}

}
