package dto;

import java.util.Calendar;

public class HerramientaReporteDTO {
	private String codigo;
	private String nombreCategoriaPadre;
	private String nombreHerramienta;
	private String nombreTecnico;
	private int cantidadPrestada;
	private String categoria;
	private Calendar fechaInicio;
	private Calendar fechaFin;
	private String fechaInicioHerramienta;
	private String fechaFinHerramienta;
	private String categoriaSeleccionada;

	public HerramientaReporteDTO() {
		categoria = "";
		categoriaSeleccionada = "";
		nombreCategoriaPadre = "";
	}

	public HerramientaReporteDTO(String nombreHerramienta, String nombreTecnico, int cantidadPrestada) {
		this.nombreHerramienta = nombreHerramienta;
		this.nombreTecnico = nombreTecnico;
		this.cantidadPrestada = cantidadPrestada;
		categoria = "";
		categoriaSeleccionada = "";
		nombreCategoriaPadre = "";
		fechaInicio = Calendar.getInstance();
		fechaFin = Calendar.getInstance();
		fechaInicioHerramienta = fechaInicio.toString();
		fechaFinHerramienta = fechaFin.toString();

	}

	public String getNombreHerramienta() {
		return nombreHerramienta;
	}

	public String getCategoriaSeleccionada() {
		return categoriaSeleccionada;
	}

	public void setCategoriaSeleccionada(String categoriaSeleccionada) {
		this.categoriaSeleccionada = categoriaSeleccionada;
	}

	public String getNombreCategoriaPadre() {
		return nombreCategoriaPadre;
	}

	public void setNombreCategoriaPadre(String nombreCategoriaPadre) {
		this.nombreCategoriaPadre = nombreCategoriaPadre;
	}

	public void setNombreHerramienta(String nombreHerramienta) {
		this.nombreHerramienta = nombreHerramienta;
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}
