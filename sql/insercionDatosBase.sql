use paniolibre;

SET NAMES 'utf8' COLLATE 'utf8_general_ci';

INSERT INTO Mantis (id) VALUES (1);

INSERT INTO Mail (mail, clave) values ('paniolibre@gmail.com', 'UGFuaW9MaWJyZTIwMTg=');

INSERT INTO Roles (id,nombre) values (1,'Administrador');
INSERT INTO Roles (id,nombre) values (2,'Encargado');
INSERT INTO Roles (id,nombre) values (3,'Pañolero');

INSERT INTO EstadosOrdenTrabajo (id,estado) values (1,'Nueva');
INSERT INTO EstadosOrdenTrabajo (id,estado) values (2,'Asignada');
INSERT INTO EstadosOrdenTrabajo (id,estado) values (3,'Realizada');
INSERT INTO EstadosOrdenTrabajo (id,estado) values (4,'Cerrada');
INSERT INTO EstadosOrdenTrabajo (id,estado) values (5,'Suspendida');

INSERT INTO EstadosPedidosInsumos (id,estado) values (1, 'Pendiente');
INSERT INTO EstadosPedidosInsumos (id,estado) values (2, 'Parcial');
INSERT INTO EstadosPedidosInsumos (id,estado) values (3, 'Completo');
INSERT INTO EstadosPedidosInsumos (id,estado) values (4, 'Incompleto');

INSERT INTO EstadosHerramientas (id, estado) values (1, 'Disponible');
INSERT INTO EstadosHerramientas (id, estado) values (2, 'Prestada');
INSERT INTO EstadosHerramientas (id, estado) values (3, 'Averiada');
INSERT INTO EstadosHerramientas (id, estado) values (4, 'En Reparacion');

INSERT INTO TiposIngresoInsumo (id, nombre) values (1,'Caja Chica');
INSERT INTO TiposIngresoInsumo (id, nombre) values (2,'Pedido');
INSERT INTO TiposIngresoInsumo (id, nombre) values (3, 'Proyecto');

INSERT INTO UnidadesMedida (id, nombre) values (1, 'Mililitros');
INSERT INTO UnidadesMedida (id, nombre) values (2, 'Litros');
INSERT INTO UnidadesMedida (id, nombre) values (3, 'Gramos');
INSERT INTO UnidadesMedida (id, nombre) values (4, 'Kilogramos');
INSERT INTO UnidadesMedida (id, nombre) values (5, 'Centimetros');
INSERT INTO UnidadesMedida (id, nombre) values (6, 'Metros');
INSERT INTO UnidadesMedida (id, nombre) values (7, 'Pulgadas');
INSERT INTO UnidadesMedida (id, nombre) values (8, 'Paquetes');
INSERT INTO UnidadesMedida (id, nombre) values (9, 'Pares');
INSERT INTO UnidadesMedida (id, nombre) values (10, 'Unidades');
INSERT INTO UnidadesMedida (id, nombre) values (11, 'Valija');


INSERT INTO Proyectos (nombre) values ('Centro Cultural');
INSERT INTO Proyectos (nombre) values ('Multiespacio');
INSERT INTO Proyectos (nombre) values ('Centro de servicios y accion con la comunidad');
INSERT INTO Proyectos (nombre) values ('Instituto de ciencias');
INSERT INTO Proyectos (nombre) values ('Laboratorio de fisica');
INSERT INTO Proyectos (nombre) values ('Laboratorio de quimica');
INSERT INTO Proyectos (nombre) values ('Instituto de desarrollo humano');
INSERT INTO Proyectos (nombre) values ('Estudio de medios');
INSERT INTO Proyectos (nombre) values ('Instituto de Industria');
INSERT INTO Proyectos (nombre) values ('Laboratorio de Ingenieria');
INSERT INTO Proyectos (nombre) values ('Instituto del conurbano');
INSERT INTO Proyectos (nombre) values ('Laboratorio de ecologia');
INSERT INTO Proyectos (nombre) values ('Mantenimiento y servicios generales');
INSERT INTO Proyectos (nombre) values ('Rectorado');
INSERT INTO Proyectos (nombre) values ('Secretaria Academica');
INSERT INTO Proyectos (nombre) values ('Bedelia');
INSERT INTO Proyectos (nombre) values ('Escuela Secundaria');

INSERT INTO TiposActividad (nombre) values ('AA / Calefaccion');
INSERT INTO TiposActividad (nombre) values ('Asistencia a terceros');
INSERT INTO TiposActividad (nombre) values ('Carpinteria');
INSERT INTO TiposActividad (nombre) values ('Cerrajeria');
INSERT INTO TiposActividad (nombre) values ('Electricidad');
INSERT INTO TiposActividad (nombre) values ('Gas');
INSERT INTO TiposActividad (nombre) values ('Herrería');
INSERT INTO TiposActividad (nombre) values ('Mampostería / Refacciones');
INSERT INTO TiposActividad (nombre) values ('Mantenimiento Preventivo');
INSERT INTO TiposActividad (nombre) values ('Pequeñas obras');
INSERT INTO TiposActividad (nombre) values ('Pintura');
INSERT INTO TiposActividad (nombre) values ('Redes / Cameras');
INSERT INTO TiposActividad (nombre) values ('Sanitarios');
INSERT INTO TiposActividad (nombre) values ('Telefonía');
INSERT INTO TiposActividad (nombre) values ('Traslados');
INSERT INTO TiposActividad (nombre) values ('Varios / Manuales');
INSERT INTO TiposActividad (nombre) values ('Otro');

INSERT INTO Especialidades (nombre) values ('Externo');
INSERT INTO Especialidades (nombre) values ('Electricista');
INSERT INTO Especialidades (nombre) values ('Mecanico');
INSERT INTO Especialidades (nombre) values ('Plomero');
INSERT INTO Especialidades (nombre) values ('Carpintero');
INSERT INTO Especialidades (nombre) values ('Refrigeracion');
INSERT INTO Especialidades (nombre) values ('Pintor');
INSERT INTO Especialidades (nombre) values ('Herrero');
INSERT INTO Especialidades (nombre) values ('Gasista');
INSERT INTO Especialidades (nombre) values ('Cerrajero');

CALL cargarCategoriaInsumoPadre('Insumos');
CALL cargarCategoriaHerramientaPadre('Herramientas');

CALL cargarUsuario ('Administrador','Pañol', 'admin', 'YWRtaW4=', 'paniolibre@gmail.com', true, 'root', 'YWRtaW4=');
CALL cargarRolUsuario(1,1);
CALL cargarRolUsuario(2,1);
CALL cargarRolUsuario(3,1);

CALL cargarTecnico ('Ext','99999999','Tecnico', 'Externo','9999999');
CALL cargarTecnico ('Miguel','37369589','Miguel', '','12106');
CALL cargarTecnico ('Sergio V','37369','Sergio V', '','12136');
CALL cargarTecnico ('Sergio','3743589','Sergio', '','12146');
CALL cargarTecnico ('Nelson','3657589','Nelson', '','121656');
INSERT INTO TecnicosxEspecialidades (tecnico_id,especialidad_id) VALUES (1,1);

-- Permitir que cualquier usuario root se conecte a nuestra base de datos desde cualquier ubicacion
grant all on db.* to 'root'@'%' identified by 'root';
GRANT ALL ON *.* to root@'%' IDENTIFIED BY 'root';