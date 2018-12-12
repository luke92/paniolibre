package services;

import java.util.List;

import domain.model.Usuario;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.UsuarioDAO;

public class UsuarioService {
	private UsuarioDAO usuario;

	public UsuarioService(DAOAbstractFactory metodoPersistencia) {
		this.usuario = metodoPersistencia.createUsuarioDAO();
	}

	public void agregarUsuario(Usuario tecnico) {
		this.usuario.insert(tecnico);
	}

	public void eliminarUsuario(Usuario usuario) {
		this.usuario.delete(usuario);
	}

	public void editarUsuario(Usuario usuario) {
		this.usuario.edit(usuario);
	}

	public List<Usuario> obtenerUsuario() {
		return this.usuario.readAll();
	}

	public boolean existe(Usuario usuario) {
		return (obtenerIdUsuario(usuario) == 0);
	}

	public int obtenerIdUsuario(Usuario usuario) {
		return this.usuario.obtenerIdUsuario(usuario);
	}

	public boolean validarUserName(Usuario usuarioName) {
		List<Usuario> lista = this.obtenerUsuario();
		boolean usuarioEsValido = false;

		for (Usuario usuario : lista) {
			if (usuario.getUserName().equals(usuarioName.getUserName())) {
				usuarioEsValido = true;
			}
		}

		return usuarioEsValido;
	}

	public boolean validarUserClave(Usuario usuarioClave) {
		List<Usuario> lista = this.obtenerUsuario();
		boolean usuarioEsValido = false;

		for (Usuario user : lista) {
			if (usuarioClave.getClave().equals(user.getClave())
					&& user.getUserName().equals(usuarioClave.getUserName())) {
				usuarioEsValido = true;
			}
		}

		return usuarioEsValido;
	}

	public Usuario obtenerUsuarioxUserName(Usuario usuarioName) {
		List<Usuario> lista = this.obtenerUsuario();
		Usuario usuario2 = null;

		for (Usuario usuario : lista) {
			if (usuario.getUserName().equals(usuarioName.getUserName())) {
				usuario2 = usuario;
			}
		}

		return usuario2;
	}

}
