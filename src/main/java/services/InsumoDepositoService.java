package services;

import java.util.ArrayList;
import java.util.List;

import domain.model.Deposito;
import domain.model.Insumo;
import domain.model.InsumoDeposito;
import domain.model.UbicacionDeposito;
import dto.AlertaInsumoDTO;
import dto.DepositoDTO;
import dto.InsumoDTO;
import dto.InsumoDepositoDTO;
import dto.InsumoDepositoSpinnerDTO;
import dto.UbicacionDepositoDTO;
import javafx.beans.property.SimpleIntegerProperty;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.InsumoDepositoDAO;

public class InsumoDepositoService {
	private static InsumoDepositoDAO insumoDeposito;

	public InsumoDepositoService(DAOAbstractFactory metodoPersistencia) {
		insumoDeposito = metodoPersistencia.createInsumoDepositoDAO();
	}

	public static List<InsumoDeposito> obtenerInsumos() {
		return insumoDeposito.readAll();
	}

	public boolean agregarInsumoDeposito(InsumoDepositoDTO insumo) {
		Deposito deposito = new Deposito();
		deposito.setIdDeposito(insumo.getUbicacion().getDeposito().getIdDeposito().get());

		UbicacionDeposito ubicacion = new UbicacionDeposito();
		ubicacion.setIdUbicacionDeposito(insumo.getUbicacion().getIdUbicacionDeposito().get());
		ubicacion.setDeposito(deposito);

		Insumo insumoModel = new Insumo();
		insumoModel.setIdInsumo(insumo.getInsumo().getIdInsumo().get());

		InsumoDeposito insumoDepositoModel = new InsumoDeposito();
		insumoDepositoModel.setUbicacion(ubicacion);
		insumoDepositoModel.setInsumo(insumoModel);
		
		return insumoDeposito.insert(insumoDepositoModel);
	}

	public boolean cambiarUbicacionInsumoDeposito(InsumoDepositoDTO insumo, UbicacionDepositoDTO ubicacionNuevaDTO) {
		Deposito deposito = new Deposito();
		deposito.setIdDeposito(insumo.getUbicacion().getDeposito().getIdDeposito().get());

		UbicacionDeposito ubicacionActual = new UbicacionDeposito();
		ubicacionActual.setIdUbicacionDeposito(insumo.getUbicacion().getIdUbicacionDeposito().get());
		ubicacionActual.setDeposito(deposito);

		Insumo insumoModel = new Insumo();
		insumoModel.setIdInsumo(insumo.getInsumo().getIdInsumo().get());

		InsumoDeposito insumoDepositoModel = new InsumoDeposito();
		insumoDepositoModel.setUbicacion(ubicacionActual);
		insumoDepositoModel.setInsumo(insumoModel);
		
		Deposito depositoNuevo = new Deposito();
		depositoNuevo.setIdDeposito(ubicacionNuevaDTO.getDeposito().getIdDeposito().get());
		
		UbicacionDeposito ubicacionNueva = new UbicacionDeposito();
		ubicacionNueva.setIdUbicacionDeposito(ubicacionNuevaDTO.getIdUbicacionDeposito().get());
		ubicacionNueva.setDeposito(depositoNuevo);
		return insumoDeposito.edit(insumoDepositoModel, ubicacionNueva);
	}

	public List<InsumoDepositoDTO> obtenerInsumosDTO() {
		List<InsumoDepositoDTO> insumos = new ArrayList<>();
		for (InsumoDeposito i : obtenerInsumos()) {
			InsumoDepositoDTO insumo = i.getDTO();
			insumos.add(insumo);
		}
		return insumos;
	}

	public InsumoDeposito obtenerInsumoDeposito(InsumoDeposito insumo) {
		return insumoDeposito.getById(insumo);
	}

	public void ajustarStock(InsumoDepositoDTO insumoDTO) {
		InsumoDeposito insumoDepositoAEditar = new InsumoDeposito();

		Insumo insumo = new Insumo();
		insumo.setIdInsumo(insumoDTO.getInsumo().getIdInsumo().get());
		insumoDepositoAEditar.setInsumo(insumo);

		UbicacionDeposito ubicacion = new UbicacionDeposito();
		Deposito deposito = new Deposito();
		deposito.setIdDeposito(insumoDTO.getUbicacion().getDeposito().getIdDeposito().get());
		ubicacion.setDeposito(deposito);
		insumoDepositoAEditar.setUbicacion(ubicacion);

		insumoDepositoAEditar.setStockNuevo(insumoDTO.getStockNuevo().get());
		insumoDepositoAEditar.setStockUsado(insumoDTO.getStockUsado().get());
		insumoDepositoAEditar.setStockReservado(insumoDTO.getStockReservado().get());

		insumoDeposito.ajustarStock(insumoDepositoAEditar);
	}

	public List<AlertaInsumoDTO> obtenerAlertaUmbralInsumo() {
		return insumoDeposito.obtenerAlertaUmbralMinimo();
	}

	public boolean existeInsumo(int idInsumo) {
		InsumoDeposito insumo = new InsumoDeposito();
		Insumo i = new Insumo();
		i.setIdInsumo(idInsumo);
		insumo.setInsumo(i);
		return insumoDeposito.existeInsumoEnDepositos(insumo);
	}

	public List<InsumoDepositoSpinnerDTO> obtenerInsumosDepositoSpinnerDTO() {
		List<InsumoDepositoSpinnerDTO> listaInsumosDepositoSpinner = new ArrayList<>();
		List<InsumoDeposito> modelo = insumoDeposito.readAll();

		for (InsumoDeposito i : modelo) {
			InsumoDTO insumo = new InsumoDTO();
			UbicacionDepositoDTO ubicacion = new UbicacionDepositoDTO();
			DepositoDTO deposito = new DepositoDTO();

			insumo.setIdInsumo(i.getInsumo().getIdInsumo());
			insumo.setNombre(i.getInsumo().getNombre());
			insumo.setUmbralMinimo(new SimpleIntegerProperty(i.getInsumo().getUmbralMinimo()));

			deposito.setIdDeposito(i.getUbicacion().getDeposito().getIdDeposito());
			deposito.setNombre(i.getUbicacion().getDeposito().getNombre());

			ubicacion.setIdUbicacionDeposito(i.getUbicacion().getIdUbicacionDeposito());
			ubicacion.setNombre(i.getUbicacion().getNombre());
			ubicacion.setDeposito(deposito);

			InsumoDepositoSpinnerDTO insumoDepositoSpinnerDTO = new InsumoDepositoSpinnerDTO(ubicacion,
					i.getStockNuevo(), i.getStockUsado(), i.getStockReservado(), insumo);
			listaInsumosDepositoSpinner.add(insumoDepositoSpinnerDTO);
		}

		return listaInsumosDepositoSpinner;
	}

	public List<InsumoDepositoSpinnerDTO> obtenerInsumosDepositoSpinnerDTOPorNumeroDeOrden() {
		List<InsumoDepositoSpinnerDTO> listaInsumosDepositoSpinner = new ArrayList<>();
		List<InsumoDeposito> modelo = obtenerInsumos();

		for (InsumoDeposito i : modelo) {
			InsumoDTO insumo = new InsumoDTO();
			UbicacionDepositoDTO ubicacion = new UbicacionDepositoDTO();
			DepositoDTO deposito = new DepositoDTO();

			insumo.setIdInsumo(i.getInsumo().getIdInsumo());
			insumo.setNombre(i.getInsumo().getNombre());
			insumo.setUmbralMinimo(new SimpleIntegerProperty(i.getInsumo().getUmbralMinimo()));

			deposito.setIdDeposito(i.getUbicacion().getDeposito().getIdDeposito());
			deposito.setNombre(i.getUbicacion().getDeposito().getNombre());

			ubicacion.setIdUbicacionDeposito(i.getUbicacion().getIdUbicacionDeposito());
			ubicacion.setNombre(i.getUbicacion().getNombre());
			ubicacion.setDeposito(deposito);

			InsumoDepositoSpinnerDTO insumoDepositoSpinnerDTO = new InsumoDepositoSpinnerDTO(ubicacion,
					i.getStockNuevo(), i.getStockUsado(), i.getStockReservado(), insumo);
			insumoDepositoSpinnerDTO.setSpnCantidadNuevo(i.getStockNuevo() + i.getStockReservado());
			listaInsumosDepositoSpinner.add(insumoDepositoSpinnerDTO);
		}

		return listaInsumosDepositoSpinner;
	}

	public List<InsumoDepositoSpinnerDTO> obtenerInsumosPorOrden() {
		List<InsumoDepositoSpinnerDTO> listaInsumosDepositoSpinner = new ArrayList<>();
		List<InsumoDeposito> modelo = obtenerInsumos();
		for (InsumoDeposito insumoDepositoModel : modelo) {
			InsumoDTO insumo = new InsumoDTO();
			UbicacionDepositoDTO ubicacion = new UbicacionDepositoDTO();
			DepositoDTO deposito = new DepositoDTO();

			insumo.setIdInsumo(insumoDepositoModel.getInsumo().getIdInsumo());
			insumo.setNombre(insumoDepositoModel.getInsumo().getNombre());
			insumo.setUmbralMinimo(new SimpleIntegerProperty(insumoDepositoModel.getInsumo().getUmbralMinimo()));

			deposito.setIdDeposito(insumoDepositoModel.getUbicacion().getDeposito().getIdDeposito());
			deposito.setNombre(insumoDepositoModel.getUbicacion().getDeposito().getNombre());

			ubicacion.setIdUbicacionDeposito(insumoDepositoModel.getUbicacion().getIdUbicacionDeposito());
			ubicacion.setNombre(insumoDepositoModel.getUbicacion().getNombre());
			ubicacion.setDeposito(deposito);

			InsumoDepositoSpinnerDTO insumoDepositoSpinnerDTO = new InsumoDepositoSpinnerDTO(ubicacion,
					insumoDepositoModel.getStockNuevo(), insumoDepositoModel.getStockUsado(),
					insumoDepositoModel.getStockReservado(), insumo);
			insumoDepositoSpinnerDTO
					.setSpnCantidadNuevo(insumoDepositoModel.getStockNuevo() + insumoDepositoModel.getStockReservado());
			listaInsumosDepositoSpinner.add(insumoDepositoSpinnerDTO);
		}
		return listaInsumosDepositoSpinner;

	}
}
