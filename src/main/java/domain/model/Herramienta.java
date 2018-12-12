package domain.model;

import java.util.Calendar;

import util.TipoCategoria;

public class Herramienta {
	private int idHerramienta;
	private String codigo;
	private String nombre;
	private UbicacionDeposito ubicacion;
	private CategoriaHerramienta categoria;
	private ArbolCategoria categoriaArbol;
	private String factura;
	private String numeroActivo;
	private String comentario;
	private String mecanismoAdquisicion;
	private Calendar fechaAdquisicion;
	private Calendar fechaGarantiaExpiracion;
	private Calendar fechaUltimaModificacion;
	private EnumEstadoHerramienta estado;
	private int activo;
	private String proveedor;
	private String marca;

	public Herramienta() {

	}

	public Herramienta(int idHerramienta, String codigo, String nombre, UbicacionDeposito ubicacion,
			CategoriaHerramienta categoria, String factura, String numeroActivo, String comentario,
			String mecanismoAdquisicion, Calendar fechaAdquisicion, String proveedor, Calendar fechaGarantiaExpiracion,
			EnumEstadoHerramienta estado, int activo, String marca) {
		this.idHerramienta = idHerramienta;
		this.codigo = codigo;
		this.nombre = nombre;
		this.ubicacion = ubicacion;
		this.categoria = categoria;
		this.factura = factura;
		this.numeroActivo = numeroActivo;
		this.comentario = comentario;
		this.mecanismoAdquisicion = mecanismoAdquisicion;
		this.fechaAdquisicion = fechaAdquisicion;
		this.proveedor = proveedor;
		this.fechaGarantiaExpiracion = fechaGarantiaExpiracion;
		this.estado = estado;
		this.activo = activo;
		this.marca = marca;
		this.categoriaArbol = new ArbolCategoria(TipoCategoria.HERRAMIENTAS);
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public int getIdHerramienta() {
		return idHerramienta;
	}

	public void setIdHerramienta(int idHerramienta) {
		this.idHerramienta = idHerramienta;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public UbicacionDeposito getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(UbicacionDeposito ubicacion) {
		this.ubicacion = ubicacion;
	}

	public CategoriaHerramienta getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaHerramienta categoria) {
		this.categoria = categoria;
	}

	public String getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}

	public String getNumeroActivo() {
		return numeroActivo;
	}

	public void setNumeroActivo(String numeroActivo) {
		this.numeroActivo = numeroActivo;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getMecanismoAdquisicion() {
		return mecanismoAdquisicion;
	}

	public void setMecanismoAdquisicion(String mecanismoAdquisicion) {
		this.mecanismoAdquisicion = mecanismoAdquisicion;
	}

	public Calendar getFechaAdquisicion() {
		return fechaAdquisicion;
	}

	public void setFechaAdquisicion(Calendar fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}

	public Calendar getFechaGarantiaExpiracion() {
		return fechaGarantiaExpiracion;
	}

	public void setFechaGarantiaExpiracion(Calendar fechaGarantiaExpiracion) {
		this.fechaGarantiaExpiracion = fechaGarantiaExpiracion;
	}

	public EnumEstadoHerramienta getEstado() {
		return estado;
	}

	public void setEstado(EnumEstadoHerramienta estado) {
		this.estado = estado;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	@Override
	public String toString() {
		return "Herramienta [idHerramienta=" + idHerramienta + ", codigo=" + codigo + ", nombre=" + nombre
				+ ", ubicacion=" + ubicacion + ", categoria=" + categoria + ", factura=" + factura + ", numeroActivo="
				+ numeroActivo + ", comentario=" + comentario + ", mecanismoAdquisicion=" + mecanismoAdquisicion
				+ ", fechaAdquisicion=" + fechaAdquisicion + ", fechaGarantiaExpiracion=" + fechaGarantiaExpiracion
				+ ", estado=" + estado + ", activo=" + activo + ", proveedor=" + proveedor + ", marca=" + marca + "]";
	}

	public Calendar getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}

	public void setFechaUltimaModificacion(Calendar fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}

	public ArbolCategoria getCategoriaArbol() {
		return categoriaArbol;
	}

	public void setCategoriaArbol(ArbolCategoria categoriaArbol) {
		this.categoriaArbol = categoriaArbol;
	}

}
