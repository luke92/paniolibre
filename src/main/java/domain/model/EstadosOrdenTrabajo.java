package domain.model;

public class EstadosOrdenTrabajo {
	private int idEstadosOrdenTrabajo;
	private int estado;

	public EstadosOrdenTrabajo(int idEstadosOrdenTrabajo, int estado) {
		this.idEstadosOrdenTrabajo = idEstadosOrdenTrabajo;
		this.estado = estado;
	}

	public int getIdEstadosOrdenTrabajo() {
		return idEstadosOrdenTrabajo;
	}

	public void setIdEstadosOrdenTrabajo(int idEstadosOrdenTrabajo) {
		this.idEstadosOrdenTrabajo = idEstadosOrdenTrabajo;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public EstadosOrdenTrabajo() {
	}
}