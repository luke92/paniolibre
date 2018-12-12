package dto;

public class MantisDTO {
	private String filtroNuevasId;
	private String filtroTodasId;
	private String filtroResueltasId;
	private String filtroCerradasId;
	private String filtroAsignadasId;
	private String idCustomFieldTecnicos;
	private String apiUrl;
	private String mantisPuerto;
	private String mantisIP;
	private String mantisNombreApp;
	
	public MantisDTO(String filtroNuevasId, String filtroTodasId, String filtroResueltasId, String filtroCerradasId,
			String filtroAsignadasId, String idCustomFieldTecnicos, String apiUrl, String mantisPuerto, String mantisIP,
			String mantisNombreApp) {
		super();
		this.filtroNuevasId = filtroNuevasId;
		this.filtroTodasId = filtroTodasId;
		this.filtroResueltasId = filtroResueltasId;
		this.filtroCerradasId = filtroCerradasId;
		this.filtroAsignadasId = filtroAsignadasId;
		this.idCustomFieldTecnicos = idCustomFieldTecnicos;
		this.apiUrl = apiUrl;
		this.mantisPuerto = mantisPuerto;
		this.mantisIP = mantisIP;
		this.mantisNombreApp = mantisNombreApp;
	}
	public String getFiltroNuevasId() {
		return filtroNuevasId;
	}
	public void setFiltroNuevasId(String filtroNuevasId) {
		this.filtroNuevasId = filtroNuevasId;
	}
	public String getFiltroTodasId() {
		return filtroTodasId;
	}
	public void setFiltroTodasId(String filtroTodasId) {
		this.filtroTodasId = filtroTodasId;
	}
	public String getFiltroResueltasId() {
		return filtroResueltasId;
	}
	public void setFiltroResueltasId(String filtroResueltasId) {
		this.filtroResueltasId = filtroResueltasId;
	}
	public String getFiltroCerradasId() {
		return filtroCerradasId;
	}
	public void setFiltroCerradasId(String filtroCerradasId) {
		this.filtroCerradasId = filtroCerradasId;
	}
	public String getFiltroAsignadasId() {
		return filtroAsignadasId;
	}
	public void setFiltroAsignadasId(String filtroAsignadasId) {
		this.filtroAsignadasId = filtroAsignadasId;
	}
	public String getIdCustomFieldTecnicos() {
		return idCustomFieldTecnicos;
	}
	public void setIdCustomFieldTecnicos(String idCustomFieldTecnicos) {
		this.idCustomFieldTecnicos = idCustomFieldTecnicos;
	}
	public String getApiUrl() {
		return apiUrl;
	}
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	public String getMantisPuerto() {
		return mantisPuerto;
	}
	public void setMantisPuerto(String mantisPuerto) {
		this.mantisPuerto = mantisPuerto;
	}
	public String getMantisIP() {
		return mantisIP;
	}
	public void setMantisIP(String mantisIP) {
		this.mantisIP = mantisIP;
	}
	public String getMantisNombreApp() {
		return mantisNombreApp;
	}
	public void setMantisNombreApp(String mantisNombreApp) {
		this.mantisNombreApp = mantisNombreApp;
	}
	
	
}
