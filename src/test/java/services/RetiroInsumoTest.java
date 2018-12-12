package services;

import org.junit.Test;

import persistencia.dao.mysql.DAOSQLFactory;

public class RetiroInsumoTest {
	RetiroInsumoService retiroInsumoService = new RetiroInsumoService(new DAOSQLFactory());

	@Test
	public void altaRetiroInsumo() {
		// Usuario usuario = new Usuario(1, "Lucas", "Vargas", "lvargas", "123456",
		// "lucasjv92@gmail.com", 1);
		// Tecnico tecnico = new Tecnico(1, "Walter Roma", 1);
		// CategoriaInsumo categoriaPadre = new CategoriaInsumo();
		// categoriaPadre.setNombre("Pintura");
		// categoriaPadre.setIdCategoriaInsumo(1);
		// categoriaPadre.setActivo(1);
		// CategoriaInsumo categoriaInsumo = new CategoriaInsumo(2, "Latex", 1,
		// categoriaPadre);
		// Insumo insumo = new Insumo(1, "Balde 20 lts", "Balde de pintura para todo
		// tipo de paredes", 5, 1,
		// categoriaInsumo);
		// Deposito deposito = new Deposito(1, "UNGS", "Sede Central", 1);
		// java.util.Date utilDate = new java.util.Date();
		// java.sql.Date fecha = new java.sql.Date(utilDate.getTime());
		// RetiroInsumo retiroInsumo = new RetiroInsumo(0, usuario, tecnico, 20, 5,
		// insumo, deposito, fecha);
		// retiroInsumoService.agregarRetiroInsumo(retiroInsumo);
	}

	@Test
	public void editarInsumoDeposito() {
		// Deposito deposito = new Deposito();
		// deposito.setIdDeposito(3);
		// Insumo insumo = new Insumo();
		// insumo.setIdInsumo(20);
		// UbicacionDeposito ubicacion = new UbicacionDeposito();
		// ubicacion.setIdUbicacionDeposito(4);
		// InsumoDeposito insumoDeposito = new InsumoDeposito(deposito, insumo,
		// ubicacion, 50, 15, 0);
		// insumoDeposito.setStockNuevo(20);
		// insumoDeposito.setStockUsado(5);
		// retiroInsumoService.editarInsumoDeposito(insumoDeposito);
	}
}