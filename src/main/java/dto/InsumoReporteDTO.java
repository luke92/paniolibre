package dto;

import java.util.Calendar;

public class InsumoReporteDTO {
	private int idCategoriaHijo;
	private int idCategoriaPadre;
	private String codigo;
	private String categoriaSeleccionada;
	private String nombreCategoriaPadre;
	private String nombreInsumo;
	private String nombreTecnico;
	private int cantidadPrestada;
	private String categoria;
	private Calendar fechaInicio;
	private Calendar fechaFin;
	private String fechaInicioHerramienta;
	private String fechaFinHerramienta;
	private Integer cantidadRetirada;

	public InsumoReporteDTO() {
		categoriaSeleccionada = "";
		categoria = "";
		cantidadRetirada = new Integer(0);
		nombreCategoriaPadre = "";
		codigo = "";
	}

	public InsumoReporteDTO(String nombreInsumo, String nombreTecnico, int cantidadPrestada) {
		this.nombreInsumo = nombreInsumo;
		this.nombreTecnico = nombreTecnico;
		this.cantidadPrestada = cantidadPrestada;
		categoria = "";
		fechaInicio = Calendar.getInstance();
		fechaFin = Calendar.getInstance();
		fechaInicioHerramienta = fechaInicio.toString();
		fechaFinHerramienta = fechaFin.toString();
		cantidadRetirada = new Integer(0);
		nombreCategoriaPadre = "";
		categoriaSeleccionada = "";
		codigo = "";

	}

	public String getCategoriaSeleccionada() {
		return categoriaSeleccionada;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setCategoriaSeleccionada(String categoriaSeleccionada) {
		this.categoriaSeleccionada = categoriaSeleccionada;
	}

	public String getNombreInsumo() {
		return nombreInsumo;
	}

	public void setNombreInsumo(String nombreInsumo) {
		this.nombreInsumo = nombreInsumo;
	}

	public String getNombreTecnico() {
		return nombreTecnico;
	}

	public void setNombreTecnico(String nombreTecnico) {
		this.nombreTecnico = nombreTecnico;
	}

	public int getCantidadPrestada() {
		return cantidadPrestada;
	}

	public void setCantidadPrestada(int cantidadPrestada) {
		this.cantidadPrestada = cantidadPrestada;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Calendar getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Calendar fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Calendar getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Calendar fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getFechaInicioHerramienta() {
		return fechaInicioHerramienta;
	}

	public void setFechaInicioHerramienta(String fechaInicioHerramienta) {
		this.fechaInicioHerramienta = fechaInicioHerramienta;
	}

	public String getFechaFinHerramienta() {
		return fechaFinHerramienta;
	}

	public void setFechaFinHerramienta(String fechaFinHerramienta) {
		this.fechaFinHerramienta = fechaFinHerramienta;
	}

	public Integer getCantidadRetirada() {
		return cantidadRetirada;
	}

	public void setCantidadRetirada(Integer cantidadRetirada) {
		this.cantidadRetirada = cantidadRetirada;
	}

	public String getNombreCategoriaPadre() {
		return nombreCategoriaPadre;
	}

	public void setNombreCategoriaPadre(String nombreCategoriaPadre) {
		this.nombreCategoriaPadre = nombreCategoriaPadre;
	}

	public int getIdCategoriaHijo() {
		return idCategoriaHijo;
	}

	public void setIdCategoriaHijo(int idCategoriaHijo) {
		this.idCategoriaHijo = idCategoriaHijo;
	}

	public int getIdCategoriaPadre() {
		return idCategoriaPadre;
	}

	public void setIdCategoriaPadre(int idCategoriaPadre) {
		this.idCategoriaPadre = idCategoriaPadre;
	}

}
