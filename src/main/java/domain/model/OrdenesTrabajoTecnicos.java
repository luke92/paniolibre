package domain.model;

public class OrdenesTrabajoTecnicos {
	public int OrdenTrabajo_id;
	public int Tecnico_id;

	public int getOrdenTrabajoId() {
		return OrdenTrabajo_id;
	}

	public void setOrdenTrabajoId(int ordenTrabajoId) {
		OrdenTrabajo_id = ordenTrabajoId;
	}

	public int getTecnicoId() {
		return Tecnico_id;
	}

	public void setTecnicoId(int tecnicoId) {
		Tecnico_id = tecnicoId;
	}

	public OrdenesTrabajoTecnicos(int ordenTrabajoId, int tecnicoId) {
		OrdenTrabajo_id = ordenTrabajoId;
		Tecnico_id = tecnicoId;
	}
}