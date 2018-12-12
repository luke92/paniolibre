package domain.model;

import dto.ArbolCategoriaDTO;
import dto.InsumoDTO;

public class Insumo { // ESTA CLASE YA ESTA

	private Integer idInsumo;
	private String codigoInsumo;
	private String nombreInsumo;
	private String marca;
	private ArbolCategoria categoriaInsumo;
	private EnumUnidadMedida unidadMedida;
	private String comentario;
	private Integer umbralMinimo;
	private Estado estado;

	public Insumo() {
	}

	public Insumo(Integer idInsumo, String codigoInsumo, String nombreInsumo, String marca,
			ArbolCategoria categoriaInsumo, EnumUnidadMedida unidadMedida, String comentario, Integer umbralMinimo,
			Estado estado) {
		this.idInsumo = idInsumo;
		this.codigoInsumo = codigoInsumo;
		this.nombreInsumo = nombreInsumo;
		this.marca = marca;
		this.categoriaInsumo = categoriaInsumo;
		this.unidadMedida = unidadMedida;
		this.comentario = comentario;
		this.umbralMinimo = umbralMinimo;
		this.estado = estado;
	}

	public Integer getIdInsumo() {
		return this.idInsumo;
	}

	public void setIdInsumo(int idInsumo) {
		this.idInsumo = idInsumo;
	}

	public String getNombre() {
		return this.nombreInsumo;
	}

	public void setNombre(String nombre) {
		this.nombreInsumo = nombre;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Integer getUmbralMinimo() {
		return this.umbralMinimo;
	}

	public void setUmbralMinimo(Integer umbralMinimo) {
		this.umbralMinimo = umbralMinimo;
	}

	public ArbolCategoria getCategoriaInsumo() {
		return this.categoriaInsumo;
	}

	public void setCategoriaInsumo(ArbolCategoria categoriaInsumo) {
		this.categoriaInsumo = categoriaInsumo;
	}

	public String nombreInsumo() {
		return this.nombreInsumo;
	}

	public String nombreComentario() {
		return this.comentario;
	}

	public Integer nombreUmbralMinimo() {
		return this.umbralMinimo;
	}

	public Integer nombreId() {
		return this.idInsumo;
	}

	public String getCodigoInsumo() {
		return codigoInsumo;
	}

	public void setCodigoInsumo(String codigoInsumo) {
		this.codigoInsumo = codigoInsumo;
	}

	public EnumUnidadMedida getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(EnumUnidadMedida unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public InsumoDTO getInsumoDTO() {
		InsumoDTO insumo = new InsumoDTO();
		insumo.getIdInsumo().set(this.getIdInsumo());
		insumo.getCodigo().set(this.getCodigoInsumo());
		insumo.getMarca().set(this.getMarca());
		insumo.getNombre().set(this.getNombre());
		insumo.getComentario().set(this.getComentario());
		insumo.getUmbralMinimo().set(this.getUmbralMinimo());
		insumo.setCategoria(this.getCategoriaInsumo().getDTO());
		insumo.setUnidadMedida(this.getUnidadMedida().getUnidadMedidaDTO());
		return insumo;
	}

	public InsumoDTO getDTO(InsumoDTO insumo) {
		insumo.getIdInsumo().set(this.getIdInsumo());
		insumo.getCodigo().set(this.getCodigoInsumo());
		insumo.getMarca().set(this.getMarca());
		insumo.getNombre().set(this.getNombre());
		insumo.getComentario().set(this.getComentario());
		insumo.getUmbralMinimo().set(this.getUmbralMinimo());
		ArbolCategoriaDTO categoria = this.categoriaInsumo.getDTO();
		insumo.getCategoria().setId(categoria.getId());
		;
		insumo.getCategoria().setNombre(categoria.getNombre());
		insumo.getUnidadMedida().getIdUnidadMedida().set(this.getUnidadMedida().getValor());
		insumo.getUnidadMedida().getNombre().set(this.getUnidadMedida().getNombre());
		return insumo;
	}
}