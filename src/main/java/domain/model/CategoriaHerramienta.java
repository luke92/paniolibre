package domain.model;

public class CategoriaHerramienta {

	private int idCategoria;
	private String nombre;
	private CategoriaHerramienta categoriaPadre;
	private int activo;

	public CategoriaHerramienta() {

	}

	public CategoriaHerramienta(int idCategoria, String nombre, CategoriaHerramienta categoriaPadre, int activo) {
		this.idCategoria = idCategoria;
		this.nombre = nombre;
		this.categoriaPadre = categoriaPadre;
		this.activo = activo;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public CategoriaHerramienta getCategoriaPadre() {
		return categoriaPadre;
	}

	public void setCategoriaPadre(CategoriaHerramienta categoriaPadre) {
		this.categoriaPadre = categoriaPadre;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return nombre;
	}

}
