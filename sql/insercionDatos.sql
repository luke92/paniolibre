use paniolibre;

SET NAMES 'utf8' COLLATE 'utf8_general_ci';

CALL cargarDeposito ('UNGS', 'Sede Central');
CALL cargarDeposito ('Centro Cultural', 'Anexo');

CALL cargarUbicacion('A1',1);
CALL cargarUbicacion('A1',2);
CALL cargarUbicacion('A2',1);
CALL cargarUbicacion('A2',2);

CALL cargarCategoriaInsumoHijo('Pintura',1);
CALL cargarCategoriaInsumoHijo('Latex',2);
CALL cargarCategoriaInsumoHijo('Electrico',1);
CALL cargarCategoriaInsumoHijo('Ferreteria',1);
CALL cargarCategoriaInsumoHijo('Herramientas',1);
CALL cargarCategoriaInsumoHijo('Otros',1);
CALL cargarCategoriaInsumoHijo('Sanitarios',1);
CALL cargarCategoriaInsumoHijo('Seguridad',1);

CALL cargarInsumo('AAA000','Balde', 'Alba', 2, 3, 'Balde de pintura para todo tipo de paredes', 5);
CALL cargarInsumo('AAA001','Bolsa de Tornillo', 'Black & Decker', 3, 5, 'Punta Plana', 5);

CALL cargarInsumoDeposito(1,1,1);
CAll cargarInsumoDeposito(2,1,2);
CALL cargarInsumoDeposito(1,2,3);
CALL cargarInsumoDeposito(2,2,4);

CALL cargarOrdenTrabajo ('0000001',1,'2018-05-23',null,null,'Pintar Paredes','Pintar paredes de la oficina color naranja',8,'Modulo 7',null,null,null);

CALL cargarAjusteStock(1,1,1,300,100,0);
CALL cargarAjusteStock(1,2,1,2,2,0);
CALL cargarAjusteStock(1,2,2,50,10,20);
CALL cargarAjusteStock(1,2,2,20,40,15);
CALL cargarIngresoInsumo(1,1,100,null);
CALL cargarRetiroInsumo(1,1,20,10,1,1);

CALL cargarCategoriaHerramientaHijo('Uso Diario',1);
CALL cargarCategoriaHerramientaHijo('Manual',2);
CALL cargarCategoriaHerramientaHijo('Electrico',1);
CALL cargarHerramienta('AAA000','Black & Decker','Destornillador',3,1,'000001','Compra','2017-04-10','EASY','345-0102932','2018-07-10','Punta Plana');

-- Permitir que cualquier usuario root se conecte a nuestra base de datos desde cualquier ubicacion
grant all on db.* to 'root'@'%' identified by 'root';
GRANT ALL ON *.* to root@'%' IDENTIFIED BY 'root';