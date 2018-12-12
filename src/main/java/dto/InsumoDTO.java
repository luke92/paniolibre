package dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Spinner;
import util.Spinners;

public class InsumoDTO {
	private IntegerProperty idInsumo;
	private StringProperty codigo;
	private StringProperty nombre;
	private StringProperty marca;
	private ArbolCategoriaDTO categoria;
	private UnidadMedidaDTO unidadMedida;
	private StringProperty comentario;
	private IntegerProperty umbralMinimo;
	private EstadoDTO activo;
	private Spinner<Integer> spnCantidad;
	private PedidoInsumoDTO pedidoInsumoDTO;
	private PedidoInsumoDetalleDTO pedidoInsumoDetalleDTO;
	private IntegerProperty cantidad;
	private OrdenTrabajoDTO ordenTrabajoDTO;

	public InsumoDTO() {
		this.idInsumo = new SimpleIntegerProperty();
		this.cantidad = new SimpleIntegerProperty();
		this.codigo = new SimpleStringProperty();
		this.nombre = new SimpleStringProperty();
		this.marca = new SimpleStringProperty();
		this.categoria = new ArbolCategoriaDTO();
		this.unidadMedida = new UnidadMedidaDTO();
		this.comentario = new SimpleStringProperty();
		this.umbralMinimo = new SimpleIntegerProperty();
		this.activo = null;
		this.spnCantidad = new Spinners().crearSpinner(0, 999999, 0);
		this.pedidoInsumoDTO = new PedidoInsumoDTO();
		this.pedidoInsumoDetalleDTO = new PedidoInsumoDetalleDTO();
		this.ordenTrabajoDTO = new OrdenTrabajoDTO();
	}

	public InsumoDTO(IntegerProperty idInsumo, StringProperty codigo, StringProperty nombre, StringProperty marca,
			ArbolCategoriaDTO categoria, UnidadMedidaDTO unidadMedida, StringProperty comentario,
			IntegerProperty umbralMinimo, EstadoDTO activo) {
		super();
		this.idInsumo = idInsumo;
		this.codigo = codigo;
		this.nombre = nombre;
		this.marca = marca;
		this.categoria = categoria;
		this.unidadMedida = unidadMedida;
		this.comentario = comentario;
		this.umbralMinimo = umbralMinimo;
		this.activo = activo;
		this.spnCantidad = new Spinners().crearSpinner(0, 999999, 0);
		this.cantidad = new SimpleIntegerProperty();
		this.ordenTrabajoDTO = new OrdenTrabajoDTO();
	}

	public IntegerProperty getIdInsumo() {
		return idInsumo;
	}

	public void setIdInsumo(int idInsumo) {
		this.idInsumo.set(idInsumo);
	}

	public StringProperty getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo.set(codigo);
	}

	public StringProperty getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}

	public StringProperty getMarca() {
		return marca;
	}

	public void setMarca(StringProperty marca) {
		this.marca = marca;
	}

	public ArbolCategoriaDTO getCategoria() {
		return categoria;
	}

	public void setCategoria(ArbolCategoriaDTO categoria) {
		this.categoria = categoria;
	}

	public UnidadMedidaDTO getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(UnidadMedidaDTO unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public StringProperty getComentario() {
		return comentario;
	}

	public void setComentario(StringProperty comentario) {
		this.comentario = comentario;
	}

	public IntegerProperty getUmbralMinimo() {
		return umbralMinimo;
	}

	public void setUmbralMinimo(IntegerProperty umbralMinimo) {
		this.umbralMinimo = umbralMinimo;
	}

	public EstadoDTO getActivo() {
		return activo;
	}

	public void setActivo(EstadoDTO activo) {
		this.activo = activo;
	}

	public Spinner<Integer> getSpnCantidad() {
		return spnCantidad;
	}

	public void setSpnCantidad(Spinner<Integer> spnCantidad) {
		this.spnCantidad = spnCantidad;
	}

	public void setMaxCantidad(Integer maxValue) {
		new Spinners().setValoresSpinner(spnCantidad, 0, maxValue, 0);
	}

	public Integer getValorSpinnerCantidad() {
		return Integer.parseInt(this.spnCantidad.getEditor().getText());
	}

	public PedidoInsumoDTO getPedidoInsumoDTO() {
		return pedidoInsumoDTO;
	}

	public void setPedidoInsumoDTO(PedidoInsumoDTO pedidoInsumoDTO) {
		this.pedidoInsumoDTO = pedidoInsumoDTO;
	}

	public PedidoInsumoDetalleDTO getPedidoInsumoDetalleDTO() {
		return pedidoInsumoDetalleDTO;
	}

	public void setPedidoInsumoDetalleDTO(PedidoInsumoDetalleDTO pedidoInsumoDetalleDTO) {
		this.pedidoInsumoDetalleDTO = pedidoInsumoDetalleDTO;
	}

	public IntegerProperty getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad.set(cantidad);
	}

	public OrdenTrabajoDTO getOrdenTrabajoDTO() {
		return ordenTrabajoDTO;
	}

	public void setOrdenTrabajoDTO(OrdenTrabajoDTO ordenTrabajoDTO) {
		this.ordenTrabajoDTO = ordenTrabajoDTO;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InsumoDTO other = (InsumoDTO) obj;
		if (activo != other.activo)
			return false;
		if (cantidad == null) {
			if (other.cantidad != null)
				return false;
		} else if (!cantidad.equals(other.cantidad))
			return false;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.get().equals(other.codigo.get()))
			return false;
		if (comentario == null) {
			if (other.comentario != null)
				return false;
		} else if (!comentario.get().equals(other.comentario.get()))
			return false;
		if (idInsumo == null) {
			if (other.idInsumo != null)
				return false;
		} else if (! (idInsumo.get() ==  other.idInsumo.get()))
			return false;
		if (marca == null) {
			if (other.marca != null)
				return false;
		} else if (!marca.get().equals(other.marca.get()))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.get().equals(other.nombre.get()))
			return false;
		if (ordenTrabajoDTO == null) {
			if (other.ordenTrabajoDTO != null)
				return false;
		} else if (!ordenTrabajoDTO.equals(other.ordenTrabajoDTO))
			return false;
		if (pedidoInsumoDTO == null) {
			if (other.pedidoInsumoDTO != null)
				return false;
		} else if (!pedidoInsumoDTO.equals(other.pedidoInsumoDTO))
			return false;
		if (pedidoInsumoDetalleDTO == null) {
			if (other.pedidoInsumoDetalleDTO != null)
				return false;
		} else if (!pedidoInsumoDetalleDTO.equals(other.pedidoInsumoDetalleDTO))
			return false;
		if (spnCantidad == null) {
			if (other.spnCantidad != null)
				return false;
		} else if (!spnCantidad.getEditor().getText().equals(other.spnCantidad.getEditor().getText()))
			return false;
		if (umbralMinimo == null) {
			if (other.umbralMinimo != null)
				return false;
		} else if (!( umbralMinimo.get() == other.umbralMinimo.get()))
			return false;
		if (unidadMedida == null) {
			if (other.unidadMedida != null)
				return false;
		} else if (unidadMedida.equals(other.unidadMedida))
			return false;
		return true;
	}
	
	

}
