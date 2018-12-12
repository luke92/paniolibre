package dto;

public class ConexionDTO {
	private String ip;
	private String puerto;
	private String username;
	private String password;
	private String baseDatos;
	
	public ConexionDTO(String ip, String puerto, String username, String password, String baseDatos) {
		this.ip = ip;
		this.puerto = puerto;
		this.username = username;
		this.password = password;
		this.baseDatos = baseDatos;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPuerto() {
		return puerto;
	}
	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBaseDatos() {
		return baseDatos;
	}
	public void setBaseDatos(String baseDatos) {
		this.baseDatos = baseDatos;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseDatos == null) ? 0 : baseDatos.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((puerto == null) ? 0 : puerto.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConexionDTO other = (ConexionDTO) obj;
		if (baseDatos == null) {
			if (other.baseDatos != null)
				return false;
		} else if (!baseDatos.equals(other.baseDatos))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (puerto == null) {
			if (other.puerto != null)
				return false;
		} else if (!puerto.equals(other.puerto))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
}
