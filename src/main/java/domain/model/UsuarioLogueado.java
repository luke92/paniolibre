package domain.model;

public class UsuarioLogueado {

	public static UsuarioLogueado instancia;
	private Usuario usuarioLogueado;

	private UsuarioLogueado(Usuario usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	public static UsuarioLogueado logearUsuario(Usuario usuarioLogueado) {
		if (instancia == null) {
			instancia = new UsuarioLogueado(usuarioLogueado);
		}
		return instancia;
	}

	public void desloguearUsuario() {
		this.instancia = null;
	}

	public Usuario getUsuarioLogueado() {
		return this.usuarioLogueado;
	}

	public static UsuarioLogueado getInstancia() {
		return instancia;

	}

}
