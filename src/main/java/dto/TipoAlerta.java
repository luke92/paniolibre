package dto;

public enum TipoAlerta {
	STOCKCERO("Insumos con Stock Cero"),
	UMBRALMINIMO("Insumos con Umbral Minimo"),
	PEDIDOS("Pedidos de Insumos no recibidos"),
	REPARACIONES("Reparaciones de Herramientas no recibidas");
	
	private String estadoString;
	
	TipoAlerta(String string) {
		this.estadoString = string;
	}
	
	public String getNombre() {
		return this.estadoString;
	}
}
