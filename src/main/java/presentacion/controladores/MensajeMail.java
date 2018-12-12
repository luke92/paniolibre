package presentacion.controladores;

import java.util.ArrayList;
import java.util.List;

import domain.model.Herramienta;
import domain.model.Insumo;
import domain.model.PedidoInsumoDetalle;
import domain.model.Usuario;
import dto.AlertaDTO;
import dto.InsumoDepositoDTO;
import dto.TipoAlerta;
import persistencia.dao.mysql.DAOSQLFactory;
import services.AlertaService;
import services.HerramientaService;
import services.InsumoDepositoService;
import services.InsumoService;
import services.PedidoInsumoService;
import services.UsuarioService;
import util.Email;

public class MensajeMail {

	private static String firma = "\n\nSaludos,\nEquipo CommitAndPush.";
	private static boolean enviarMail = false;

	private MensajeMail() {
	}

	public static void enviarMail() {

		String mensaje = obtenerMensaje() + firma;
		if (enviarMail) {
			for (String destinatario : obtenerDestinatarios())
				Email.enviarConGMail(destinatario, "Pa\u00f1oLibre - Avisos", mensaje);
		}
	}

	private static List<String> obtenerDestinatarios() {
		List<Usuario> usuarios = new UsuarioService(new DAOSQLFactory()).obtenerUsuario();
		List<String> destinatarios = new ArrayList<>();
		for (Usuario usuario : usuarios) {
			if (usuario.isRecibeAlertasPorMail())
				destinatarios.add(usuario.getMail());
		}
		return destinatarios;
	}

	private static String obtenerMensaje() {
		StringBuilder mensaje = new StringBuilder();

		List<AlertaDTO> alertas = new AlertaService(new DAOSQLFactory()).obtenerAlertasNoEnviadasPorMail();
		if (!alertas.isEmpty()) {
			enviarMail = true;
			mensaje.append(obtenerMensajeStockCero(alertas));
			mensaje.append(obtenerMensajeUmbral(alertas));
			mensaje.append(obtenerPedidos(alertas));
			mensaje.append(obtenerReparaciones(alertas));
		}
		return mensaje.toString();

	}

	private static String obtenerMensajeStockCero(List<AlertaDTO> alertas) {
		StringBuilder mensaje = new StringBuilder();
		mensaje.append(TipoAlerta.STOCKCERO.getNombre() + ":\n");
		boolean hayAlertas = false;
		for (AlertaDTO alerta : alertas) {
			if (alerta.getTipoAlertaString() == TipoAlerta.STOCKCERO.getNombre()) {
				hayAlertas = true;
				mensaje.append(alerta.getDetalleAlertaString() + '\n');
			}
		}
		mensaje.append('\n');
		if (hayAlertas)
			return mensaje.toString();
		else
			return "\n";

	}

	private static String obtenerMensajeUmbral(List<AlertaDTO> alertas) {
		StringBuilder mensaje = new StringBuilder();
		mensaje.append(TipoAlerta.UMBRALMINIMO.getNombre() + ":\n");
		boolean hayAlertas = false;
		for (AlertaDTO alerta : alertas) {
			if (alerta.getTipoAlertaString() == TipoAlerta.UMBRALMINIMO.getNombre()) {
				hayAlertas = true;
				mensaje.append(alerta.getDetalleAlertaString() + '\n');
			}
		}
		mensaje.append('\n');
		if (hayAlertas)
			return mensaje.toString();
		else
			return "\n";
	}

	private static String obtenerReparaciones(List<AlertaDTO> alertas) {
		StringBuilder mensaje = new StringBuilder();
		mensaje.append(TipoAlerta.REPARACIONES.getNombre() + ":\n");
		boolean hayAlertas = false;
		for (AlertaDTO alerta : alertas) {
			if (alerta.getTipoAlertaString() == TipoAlerta.REPARACIONES.getNombre()) {
				hayAlertas = true;
				mensaje.append(alerta.getDetalleAlertaString() + '\n');
			}
		}
		mensaje.append('\n');
		if (hayAlertas)
			return mensaje.toString();
		else
			return "\n";
	}

	private static String obtenerPedidos(List<AlertaDTO> alertas) {
		StringBuilder mensaje = new StringBuilder();
		mensaje.append(TipoAlerta.PEDIDOS.getNombre() + ":\n");
		boolean hayAlertas = false;
		for (AlertaDTO alerta : alertas) {
			if (alerta.getTipoAlertaString() == TipoAlerta.PEDIDOS.getNombre()) {
				hayAlertas = true;
				mensaje.append(alerta.getDetalleAlertaString() + '\n');
			}
		}
		mensaje.append('\n');
		if (hayAlertas)
			return mensaje.toString();
		else
			return "\n";
	}

	private static String obtenerMensajeMail() {
		return mensajeReparaciones() + mensajeStock() + mensajePedidos();
	}

	private static String mensajeReparaciones() {
		String mensaje = "";
		HerramientaService serviceherramienta = new HerramientaService(new DAOSQLFactory());
		if (serviceherramienta.obtenerHerramientaAveriadas().size() > 0) {
			mensaje += "REPARACIONES DE HERRAMIENTAS:\n";
			for (Herramienta herramientaAveriada : serviceherramienta.obtenerHerramientaAveriadas()) {
				mensaje += "'" + herramientaAveriada.getNombre() + "' de marca '" + herramientaAveriada.getMarca()
						+ "'\n";
			}
			mensaje += "\n";
		}
		return mensaje;
	}

	private static String mensajeStock() {
		String mensaje = "";
		InsumoDepositoService serviceInsumoDeposito = new InsumoDepositoService(new DAOSQLFactory());
		if (serviceInsumoDeposito.obtenerInsumosDTO().size() > 0) {
			mensaje += "STOCK DE INSUMOS:\n";
			for (InsumoDepositoDTO insumo : serviceInsumoDeposito.obtenerInsumosDTO()) {
				if (insumo.getStockNuevo().get() == 0 || insumo.getStockUsado().get() == 0) {
					mensaje += "'" + insumo.getInsumo().getNombre().getValue() + "' en '" + insumo.getUbicacion()
							+ "'\n";
				}
			}
			mensaje += "\n";
		}
		return mensaje;
	}

	private static String mensajePedidos() {
		String mensaje = "";
		PedidoInsumoService servicePedidos = new PedidoInsumoService(new DAOSQLFactory());
		List<PedidoInsumoDetalle> pedidosInsumos = servicePedidos.obtenerPedidoInsumoDetalle();
		InsumoService serviceInsumo = new InsumoService(new DAOSQLFactory());
		if (pedidosInsumos.size() > 0) {
			mensaje += "PEDIDOS:\n";
			for (PedidoInsumoDetalle pedidoInsumoDetalle : pedidosInsumos) {
				Insumo i = new Insumo();
				i.setIdInsumo(pedidoInsumoDetalle.getInsumo().getIdInsumo());
				mensaje += serviceInsumo.obtenerInsumoMaestro(i).getNombre() + "' cantidad: "
						+ pedidoInsumoDetalle.getCantidad() + "'\n";
			}
		}
		return mensaje;
	}

}
