package persistencia.dao.interfaz;

import java.util.List;

import domain.model.Usuario;

public interface UsuarioDAO {

	public boolean insert(Usuario usuario);

	public boolean delete(Usuario usuario);

	public List<Usuario> readAll();

	public boolean edit(Usuario tecnico);

	public int obtenerIdUsuario(Usuario usuario);

}
