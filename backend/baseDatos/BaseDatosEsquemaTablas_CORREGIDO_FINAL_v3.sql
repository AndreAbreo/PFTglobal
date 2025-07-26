create table OPERACIONES
(
    ID_OPERACION     NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1),
    PRIMARY KEY (ID_OPERACION),
    NOMBRE_OPERACION VARCHAR2(30) not null
)
/

-- primary key already defined inline for OPERACIONES

alter table OPERACIONES
    add constraint UK_NOMBREOPERACIONES
        unique (NOMBRE_OPERACION)
/

create table INSTITUCIONES
(
    ID_INSTITUCION NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1),
    PRIMARY KEY (ID_INSTITUCION),
    NOMBRE         VARCHAR2(40) not null
)
/

-- primary key already defined inline for INSTITUCIONES

create table FUNCIONALIDADES_PERFILES
(
    ID_FUNCIONALIDAD NUMBER(38) not null,
    ID_PERFIL        NUMBER(38) not null
)
/

-- primary key already defined inline for FUNCIONALIDADES_PERFILES

create table PERFILES
(
    ID_PERFIL     NUMBER(38) generated as identity (start with 1),
    NOMBRE_PERFIL VARCHAR2(70) not null,
    ESTADO        VARCHAR2(20) not null
)
/

-- primary key already defined inline for PERFILES

create table FUNCIONALIDADES
(
    ID_FUNCIONALIDAD     NUMBER(38) generated as identity (start with 1),
    NOMBRE_FUNCIONALIDAD VARCHAR2(255),
    ESTADO               VARCHAR2(255),
    RUTA                 VARCHAR2(100)
)
/

-- primary key already defined inline for FUNCIONALIDADES

create table TIPOS_INTERVENCIONES
(
    ID_TIPO     NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1),
    NOMBRE_TIPO VARCHAR2(30)                  not null,
    ESTADO      VARCHAR2(20) DEFAULT 'ACTIVO' NOT NULL
)
/

alter table TIPOS_INTERVENCIONES
    add constraint PK_TIPOSINTERVENCIONES
        primary key (ID_TIPO)
/

alter table TIPOS_INTERVENCIONES
    add constraint UK_NOMBRETIPOINTERVENCIONES
        unique (NOMBRE_TIPO)
/

create table TIPOS_EQUIPOS
(
    ID_TIPO     NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1),
    NOMBRE_TIPO VARCHAR2(30)                  not null,
    ESTADO      VARCHAR2(20) DEFAULT 'ACTIVO' NOT NULL
)
/

alter table TIPOS_EQUIPOS
    add constraint PK_TIPO
        primary key (ID_TIPO)
/

alter table TIPOS_EQUIPOS
    add constraint UK_NOMBRETIPOSEQUIPOS
        unique (NOMBRE_TIPO)
/

create table MARCAS_MODELO
(
    ID_MARCA NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1),
    NOMBRE   VARCHAR2(30)                  not null,
    ESTADO   VARCHAR2(20) DEFAULT 'ACTIVO' NOT NULL
)
/

alter table MARCAS_MODELO
    add constraint PK_MARCASMODELOS
        primary key (ID_MARCA)
/

create table USUARIOS_TELEFONOS
(
    ID_TELEFONO NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1),
    ID_USUARIO  NUMBER,
    NUMERO      VARCHAR2(20) not null
)
/

alter table USUARIOS_TELEFONOS
    add constraint PK_TELEFONO
        primary key (ID_TELEFONO)
/

create table AUDITORIAS
(
    ID_AUDITORIA NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1),
    FECHA_HORA   DATE   not null,
    ID_USUARIO   NUMBER not null,
    ID_OPERACION NUMBER not null
)
/

-- primary key already defined inline for AUDITORIAS

alter table AUDITORIAS
    add constraint FK_AUDITORIAS_OPERACION
        foreign key (ID_OPERACION) references OPERACIONES
/

create table UBICACIONES
(
    ID_UBICACION   NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1),
    PRIMARY KEY (ID_UBICACION),
    NOMBRE         VARCHAR2(30)                  not null,
    SECTOR         VARCHAR2(30),
    PISO           NUMBER,
    NUMERO         NUMBER,
    CAMA           NUMBER,
    ID_INSTITUCION NUMBER,
    ESTADO         VARCHAR2(20) DEFAULT 'ACTIVO' NOT NULL
)
/

-- primary key already defined inline for UBICACIONES

alter table UBICACIONES
    add constraint FK_UBICACIONES_INSTITUCIONES
        foreign key (ID_INSTITUCION) references INSTITUCIONES
/

create table INTERVENCIONES
(
    ID_INTERVENCION NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1),
    ID_USUARIO      NUMBER        not null,
    ID_TIPO         NUMBER        not null,
    ID_EQUIPO       NUMBER        not null,
    MOTIVO          VARCHAR2(255) not null,
    FECHA_HORA      DATE          not null,
    COMENTARIOS     VARCHAR2(255)
)
/

-- primary key already defined inline for INTERVENCIONES

alter table INTERVENCIONES
    add constraint FK_INTERVENCIONES_TIPOSINTERVENCIONES
        foreign key (ID_TIPO) references TIPOS_INTERVENCIONES
/

create table BAJA_EQUIPOS
(
    ID_BAJA     NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1),
    ID_USUARIO  NUMBER                        not null,
    ID_EQUIPO   NUMBER                        not null,
    RAZON       VARCHAR2(30)                  not null,
    FECHA       DATE                          not null,
    ESTADO      VARCHAR2(20) DEFAULT 'ACTIVO' NOT NULL,
    COMENTARIOS VARCHAR2(100)
)
/

alter table BAJA_EQUIPOS
    add constraint PK_BAJAEQUIPOS
        primary key (ID_BAJA)
/

create table EQUIPOS_UBICACIONES
(
    ID_MOVIMIENTO NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1),
    ID_EQUIPO     NUMBER,
    ID_UBICACION  NUMBER,
    FECHA         DATE,
    USUARIO       NUMBER,
    OBSERVACIONES VARCHAR2(1000)
)
/

alter table EQUIPOS_UBICACIONES
    add constraint PK_EQUIPOSUBICACIONES
        primary key (ID_MOVIMIENTO)
/

alter table EQUIPOS_UBICACIONES
    add constraint FK_EQUB_UBICACIONES
        foreign key (ID_UBICACION) references UBICACIONES
/

create table BAJA_UBICACIONES
(
    ID_BAJA      NUMBER(38) generated as identity (start with 1),
    ID_USUARIO   NUMBER(38),
    ID_UBICACION NUMBER(38),
    RAZON        VARCHAR2(255),
    COMENTARIO   VARCHAR2(255),
    FECHA        DATE not null
)
/

-- primary key already defined inline for BAJA_UBICACIONES

alter table BAJA_UBICACIONES
    add constraint FK_BAJA_UBICACIONES_UBICACIONES
        foreign key (ID_UBICACION) references UBICACIONES
/

create table USUARIOS
(
    ID_USUARIO       NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1),
    CEDULA           VARCHAR2(8)                   not null,
    NOMBRE           VARCHAR2(50)                  not null,
    APELLIDO         VARCHAR2(50)                  not null,
    ID_PERFIL        NUMBER                        not null,
    ID_INSTITUCION   NUMBER                        not null,
    EMAIL            VARCHAR2(255)                 not null,
    NOMBRE_USUARIO   VARCHAR2(255)                 not null,
    CONTRASENIA      VARCHAR2(255)                 not null,
    ESTADO           VARCHAR2(20) DEFAULT 'ACTIVO' NOT NULL,
    FECHA_NACIMIENTO DATE
)
/

-- primary key already defined inline for USUARIOS

alter table USUARIOS
    add constraint CEDULA
        unique (CEDULA)
/

alter table USUARIOS
    add constraint EMAIL
        unique (EMAIL)
/

alter table USUARIOS
    add constraint FK_USUARIOS_INSTITUCIONES
        foreign key (ID_INSTITUCION) references INSTITUCIONES
/

create table PAISES
(
    ID_PAIS NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1),
    PRIMARY KEY (ID_PAIS),
    NOMBRE  VARCHAR2(50)                  not null,
    ESTADO  VARCHAR2(20) DEFAULT 'ACTIVO' NOT NULL
)
/

-- primary key already defined inline for PAISES

create table PROVEEDORES_EQUIPOS
(
    ID_PROVEEDOR NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1),
    NOMBRE       VARCHAR2(30)                  not null,
    ESTADO       VARCHAR2(20) DEFAULT 'ACTIVO' NOT NULL,
    ID_PAIS      NUMBER
)
/

alter table PROVEEDORES_EQUIPOS
    add constraint PK_PROVEEDORES
        primary key (ID_PROVEEDOR)
/

alter table PROVEEDORES_EQUIPOS
    add constraint FK_PROVEEDORES_PAISES
        foreign key (ID_PAIS) references PAISES
/

create table MODELOS_EQUIPOS
(
    ID_MODELO NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1),
    ID_MARCA  NUMBER                        not null,
    NOMBRE    VARCHAR2(50)                  not null,
    ESTADO    VARCHAR2(20) DEFAULT 'ACTIVO' NOT NULL
)
/

alter table MODELOS_EQUIPOS
    add constraint PK_MODELOSEQUIPOS
        primary key (ID_MODELO)
/

alter table MODELOS_EQUIPOS
    add constraint UK_MODELOSEQUIPOS
        unique (NOMBRE)
/

alter table MODELOS_EQUIPOS
    add constraint FK_MODELOSEQUIPOS_MARCASMODELOS
        foreign key (ID_MARCA) references MARCAS_MODELO
/

create table EQUIPOS
(
    ID_EQUIPO         NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1),
    ID_INTERNO        VARCHAR2(50)  not null,
    ID_UBICACION      NUMBER        not null,
    NRO_SERIE         VARCHAR2(100) not null,
    NOMBRE            VARCHAR2(100) not null,
    ID_TIPO           NUMBER        not null,
    GARANTIA          VARCHAR2(20),
    ESTADO            VARCHAR2(20) default 'ACTIVO',
    ID_PROVEEDOR      NUMBER,
    ID_PAIS           NUMBER,
    ID_MODELO         NUMBER,
    IMAGEN            VARCHAR2(255),
    FECHA_ADQUISICION DATE
)
/

-- primary key already defined inline for EQUIPOS

alter table EQUIPOS
    add constraint UK_IDINTERNOEQUIPOS
        unique (ID_INTERNO)
/

alter table EQUIPOS
    add constraint UK_NROSERIEEQUIPOS
        unique (NRO_SERIE)
/

alter table EQUIPOS
    add constraint FK_EQUIPOS_MODELOSEQUIPOS
        foreign key (ID_MODELO) references MODELOS_EQUIPOS
/

alter table EQUIPOS
    add constraint FK_EQUIPOS_PAISES
        foreign key (ID_PAIS) references PAISES
/

alter table EQUIPOS
    add constraint FK_EQUIPOS_PROVEEDORES
        foreign key (ID_PROVEEDOR) references PROVEEDORES_EQUIPOS
/

alter table EQUIPOS
    add constraint FK_EQUIPOS_TIPOSEQUIPOS
        foreign key (ID_TIPO) references TIPOS_EQUIPOS
/

alter table EQUIPOS
    add constraint FK_EQUIPOS_UBICACIONES
        foreign key (ID_UBICACION) references UBICACIONES
/

    -- agrego cosas faltantes al script --
    ALTER TABLE FUNCIONALIDADES_PERFILES
    ADD CONSTRAINT UNQ_FUNCIONALIDAD_PERFIL UNIQUE (ID_FUNCIONALIDAD, ID_PERFIL);

-- =====================================================
-- SCRIPT DE DATOS DE PRUEBA
-- Ordenado según dependencias de claves foráneas
-- =====================================================

-- =====================================================
-- NIVEL 1: TABLAS INDEPENDIENTES (Sin dependencias)
-- =====================================================

-- 1. OPERACIONES
INSERT INTO OPERACIONES (NOMBRE_OPERACION) VALUES ('Crear');
INSERT INTO OPERACIONES (NOMBRE_OPERACION) VALUES ('Modificar');
INSERT INTO OPERACIONES (NOMBRE_OPERACION) VALUES ('Eliminar');
INSERT INTO OPERACIONES (NOMBRE_OPERACION) VALUES ('Consultar');
INSERT INTO OPERACIONES (NOMBRE_OPERACION) VALUES ('Inactivar');

-- 2. INSTITUCIONES
INSERT INTO INSTITUCIONES (NOMBRE) VALUES ('Hospital Central');
INSERT INTO INSTITUCIONES (NOMBRE) VALUES ('Clínica San José');
INSERT INTO INSTITUCIONES (NOMBRE) VALUES ('Centro Médico Norte');
INSERT INTO INSTITUCIONES (NOMBRE) VALUES ('Hospital Pediátrico');

-- 3. PERFILES
INSERT INTO PERFILES (NOMBRE_PERFIL, ESTADO) VALUES ('Administrador', 'ACTIVO');
INSERT INTO PERFILES (NOMBRE_PERFIL, ESTADO) VALUES ('Aux Administrativo', 'ACTIVO');
INSERT INTO PERFILES (NOMBRE_PERFIL, ESTADO) VALUES ('Tecnico', 'ACTIVO');
INSERT INTO PERFILES (NOMBRE_PERFIL, ESTADO) VALUES ('Ingeniero Biomedico', 'ACTIVO');
INSERT INTO PERFILES (NOMBRE_PERFIL, ESTADO) VALUES ('Tecnologo', 'ACTIVO');
INSERT INTO PERFILES (NOMBRE_PERFIL, ESTADO) VALUES ('SuperAdmin', 'ACTIVO');

-- 4. FUNCIONALIDADES
-- Script para insertar todas las funcionalidades del sistema
-- Tabla: FUNCIONALIDADES
-- Campos: nombre_funcionalidad, estado, ruta

-- USUARIOS
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Crear Usuario', 'ACTIVO', '/usuarios/crear');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Modificar Usuario', 'ACTIVO', '/usuarios/modificar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Modificar Propio Usuario', 'ACTIVO', '/usuarios/modificar-propio-usuario');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Inactivar Usuario', 'ACTIVO', '/usuarios/inactivar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Filtrar Usuarios', 'ACTIVO', '/usuarios/filtrar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Seleccionar Usuario', 'ACTIVO', '/usuarios/seleccionar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Listar Usuarios', 'ACTIVO', '/usuarios/listar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Obtener Usuario por Email', 'ACTIVO', '/usuarios/obtenerUserEmail');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Login Usuario', 'ACTIVO', '/usuarios/login');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Login Google', 'ACTIVO', '/usuarios/google-login');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Renovar Token', 'ACTIVO', '/usuarios/renovar-token');

-- EQUIPOS
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Crear Equipo', 'ACTIVO', '/equipos/crear');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Modificar Equipo', 'ACTIVO', '/equipos/modificar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Inactivar Equipo', 'ACTIVO', '/equipos/inactivar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Listar Equipos', 'ACTIVO', '/equipos/listar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Seleccionar Equipo', 'ACTIVO', '/equipos/seleccionar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Filtrar Equipos', 'ACTIVO', '/equipos/filtrar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Listar Equipos Bajas', 'ACTIVO', '/equipos/listarBajas');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Ver Equipo Inactivo', 'ACTIVO', '/equipos/VerEquipoInactivo');

-- MARCAS
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Crear Marca', 'ACTIVO', '/marca/crear');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Modificar Marca', 'ACTIVO', '/marca/modificar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Inactivar Marca', 'ACTIVO', '/marca/inactivar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Listar Marcas', 'ACTIVO', '/marca/listar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Seleccionar Marca', 'ACTIVO', '/marca/seleccionar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Filtrar Marcas', 'ACTIVO', '/marca/filtrar');

-- MODELOS
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Crear Modelo', 'ACTIVO', '/modelo/crear');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Modificar Modelo', 'ACTIVO', '/modelo/modificar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Inactivar Modelo', 'ACTIVO', '/modelo/inactivar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Listar Modelos', 'ACTIVO', '/modelo/listar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Seleccionar Modelo', 'ACTIVO', '/modelo/seleccionar');

-- PROVEEDORES
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Crear Proveedor', 'ACTIVO', '/proveedores/crear');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Modificar Proveedor', 'ACTIVO', '/proveedores/modificar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Inactivar Proveedor', 'ACTIVO', '/proveedores/inactivar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Listar Proveedores', 'ACTIVO', '/proveedores/listar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Seleccionar Proveedor', 'ACTIVO', '/proveedores/seleccionar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Filtrar Proveedores', 'ACTIVO', '/proveedores/filtrar');

-- PAÍSES
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Crear País', 'ACTIVO', '/paises/crear');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Modificar País', 'ACTIVO', '/paises/modificar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Listar Países', 'ACTIVO', '/paises/listar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Seleccionar País', 'ACTIVO', '/paises/seleccionar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Filtrar Países', 'ACTIVO', '/paises/filtrar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Inactivar País', 'ACTIVO', '/paises/inactivar');

-- PERFILES
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Crear Perfil', 'ACTIVO', '/perfiles/crear');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Modificar Perfil', 'ACTIVO', '/perfiles/modificar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Inactivar Perfil', 'ACTIVO', '/perfiles/inactivar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Seleccionar Perfil', 'ACTIVO', '/perfiles/seleccionar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Listar Perfiles', 'ACTIVO', '/perfiles/listar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Buscar Perfil por Nombre', 'ACTIVO', '/perfiles/buscarPorNombre');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Buscar Perfil por Estado', 'ACTIVO', '/perfiles/buscarPorEstado');

-- FUNCIONALIDADES
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Crear Funcionalidad', 'ACTIVO', '/funcionalidades/crear');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Modificar Funcionalidad', 'ACTIVO', '/funcionalidades/modificar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Eliminar Funcionalidad', 'ACTIVO', '/funcionalidades/eliminar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Listar Funcionalidades', 'ACTIVO', '/funcionalidades/listar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Seleccionar Funcionalidad', 'ACTIVO', '/funcionalidades/seleccionar/{id}');

-- INTERVENCIONES
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Crear Intervención', 'ACTIVO', '/intervenciones/crear');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Modificar Intervención', 'ACTIVO', '/intervenciones/modificar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Listar Intervenciones', 'ACTIVO', '/intervenciones/listar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Buscar Intervención', 'ACTIVO', '/intervenciones/buscar/{id}');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Reporte por Fechas', 'ACTIVO', '/intervenciones/reportePorFechas');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Reporte por Tipo', 'ACTIVO', '/intervenciones/reportePorTipo');

-- TIPO INTERVENCIONES
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Crear Tipo Intervención', 'ACTIVO', '/tipoIntervenciones/crear');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Modificar Tipo Intervención', 'ACTIVO', '/tipoIntervenciones/modificar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Inactivar Tipo Intervención', 'ACTIVO', '/tipoIntervenciones/inactivar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Listar Tipos Intervención', 'ACTIVO', '/tipoIntervenciones/listar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Seleccionar Tipo Intervención', 'ACTIVO', '/tipoIntervenciones/seleccionar');

-- TIPO EQUIPOS
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Crear Tipo Equipo', 'ACTIVO', '/tipoEquipos/crear');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Modificar Tipo Equipo', 'ACTIVO', '/tipoEquipos/modificar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Inactivar Tipo Equipo', 'ACTIVO', '/tipoEquipos/inactivar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Listar Tipos Equipo', 'ACTIVO', '/tipoEquipos/listar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Seleccionar Tipo Equipo', 'ACTIVO', '/tipoEquipos/seleccionar');

-- UBICACIONES
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Listar Ubicaciones', 'ACTIVO', '/ubicaciones/listar');
INSERT INTO FUNCIONALIDADES (nombre_funcionalidad, estado, ruta) VALUES ('Seleccionar Ubicación', 'ACTIVO', '/ubicaciones/seleccionar');


-- 5. TIPOS_INTERVENCIONES
INSERT INTO TIPOS_INTERVENCIONES (NOMBRE_TIPO, ESTADO) VALUES ('Mantenimiento Preventivo', 'ACTIVO');
INSERT INTO TIPOS_INTERVENCIONES (NOMBRE_TIPO, ESTADO) VALUES ('Mantenimiento Correctivo', 'ACTIVO');
INSERT INTO TIPOS_INTERVENCIONES (NOMBRE_TIPO, ESTADO) VALUES ('Calibración', 'ACTIVO');
INSERT INTO TIPOS_INTERVENCIONES (NOMBRE_TIPO, ESTADO) VALUES ('Reparación', 'ACTIVO');
INSERT INTO TIPOS_INTERVENCIONES (NOMBRE_TIPO, ESTADO) VALUES ('Instalación', 'ACTIVO');

-- 6. TIPOS_EQUIPOS
INSERT INTO TIPOS_EQUIPOS (NOMBRE_TIPO, ESTADO) VALUES ('Monitor', 'ACTIVO');
INSERT INTO TIPOS_EQUIPOS (NOMBRE_TIPO, ESTADO) VALUES ('Ventilador', 'ACTIVO');
INSERT INTO TIPOS_EQUIPOS (NOMBRE_TIPO, ESTADO) VALUES ('Bomba de Infusión', 'ACTIVO');
INSERT INTO TIPOS_EQUIPOS (NOMBRE_TIPO, ESTADO) VALUES ('Desfibrilador', 'ACTIVO');
INSERT INTO TIPOS_EQUIPOS (NOMBRE_TIPO, ESTADO) VALUES ('Electrocardiógrafo', 'ACTIVO');
INSERT INTO TIPOS_EQUIPOS (NOMBRE_TIPO, ESTADO) VALUES ('Monitor de signos', 'ACTIVO');
INSERT INTO TIPOS_EQUIPOS (NOMBRE_TIPO, ESTADO) VALUES ('Electroquirurjico', 'ACTIVO');
INSERT INTO TIPOS_EQUIPOS (NOMBRE_TIPO, ESTADO) VALUES ('Anestesiometro', 'ACTIVO');
INSERT INTO TIPOS_EQUIPOS (NOMBRE_TIPO, ESTADO) VALUES ('Rayos x', 'ACTIVO');
INSERT INTO TIPOS_EQUIPOS (NOMBRE_TIPO, ESTADO) VALUES ('Nebulizador', 'ACTIVO');


-- 7. MARCAS_MODELO
INSERT INTO MARCAS_MODELO (NOMBRE, ESTADO) VALUES ('Philips', 'ACTIVO');
INSERT INTO MARCAS_MODELO (NOMBRE, ESTADO) VALUES ('GE Healthcare', 'ACTIVO');
INSERT INTO MARCAS_MODELO (NOMBRE, ESTADO) VALUES ('Siemens', 'ACTIVO');
INSERT INTO MARCAS_MODELO (NOMBRE, ESTADO) VALUES ('Drager', 'ACTIVO');
INSERT INTO MARCAS_MODELO (NOMBRE, ESTADO) VALUES ('Mindray', 'ACTIVO');
INSERT INTO MARCAS_MODELO (NOMBRE, ESTADO) VALUES ('Thomas', 'ACTIVO');
INSERT INTO MARCAS_MODELO (NOMBRE, ESTADO) VALUES ('Omron', 'ACTIVO');
INSERT INTO MARCAS_MODELO (NOMBRE, ESTADO) VALUES ('EDAN', 'ACTIVO');


-- 8. PAISES
INSERT INTO PAISES (NOMBRE, ESTADO) VALUES ('Uruguay', 'ACTIVO');
INSERT INTO PAISES (NOMBRE, ESTADO) VALUES ('Estados Unidos', 'ACTIVO');
INSERT INTO PAISES (NOMBRE, ESTADO) VALUES ('Alemania', 'ACTIVO');
INSERT INTO PAISES (NOMBRE, ESTADO) VALUES ('China', 'ACTIVO');
INSERT INTO PAISES (NOMBRE, ESTADO) VALUES ('Holanda', 'ACTIVO');

-- =====================================================
-- NIVEL 2: TABLAS CON 1 DEPENDENCIA
-- =====================================================

-- 9. PROVEEDORES_EQUIPOS
INSERT INTO PROVEEDORES_EQUIPOS (NOMBRE, ESTADO, ID_PAIS) VALUES ('MedSupply Uruguay', 'ACTIVO', 1);
INSERT INTO PROVEEDORES_EQUIPOS (NOMBRE, ESTADO, ID_PAIS) VALUES ('Philips Medical', 'ACTIVO', 2);
INSERT INTO PROVEEDORES_EQUIPOS (NOMBRE, ESTADO, ID_PAIS) VALUES ('Siemens Healthineers', 'ACTIVO', 3);
INSERT INTO PROVEEDORES_EQUIPOS (NOMBRE, ESTADO, ID_PAIS) VALUES ('Mindray International', 'ACTIVO', 4);
INSERT INTO PROVEEDORES_EQUIPOS (NOMBRE, ESTADO, ID_PAIS) VALUES ('Drager Medical', 'ACTIVO', 5);
INSERT INTO PROVEEDORES_EQUIPOS (NOMBRE, ESTADO, ID_PAIS) VALUES ('MedicTube', 'ACTIVO', 1);
INSERT INTO PROVEEDORES_EQUIPOS (NOMBRE, ESTADO, ID_PAIS) VALUES ('MoniVita', 'ACTIVO', 1);
INSERT INTO PROVEEDORES_EQUIPOS (NOMBRE, ESTADO, ID_PAIS) VALUES ('Desfribilados', 'ACTIVO', 1);
INSERT INTO PROVEEDORES_EQUIPOS (NOMBRE, ESTADO, ID_PAIS) VALUES ('Anestesic', 'ACTIVO', 1);


-- 10. MODELOS_EQUIPOS
INSERT INTO MODELOS_EQUIPOS (ID_MARCA, NOMBRE, ESTADO) VALUES (1, 'IntelliVue MX40', 'ACTIVO');
INSERT INTO MODELOS_EQUIPOS (ID_MARCA, NOMBRE, ESTADO) VALUES (1, 'IntelliVue MX800', 'ACTIVO');
INSERT INTO MODELOS_EQUIPOS (ID_MARCA, NOMBRE, ESTADO) VALUES (2, 'CARESCAPE B650', 'ACTIVO');
INSERT INTO MODELOS_EQUIPOS (ID_MARCA, NOMBRE, ESTADO) VALUES (3, 'MAGLIFE C+', 'ACTIVO');
INSERT INTO MODELOS_EQUIPOS (ID_MARCA, NOMBRE, ESTADO) VALUES (4, 'Fabius Plus', 'ACTIVO');
INSERT INTO MODELOS_EQUIPOS (ID_MARCA, NOMBRE, ESTADO) VALUES (5, 'BeneView T8', 'ACTIVO');

-- 11. UBICACIONES
INSERT INTO UBICACIONES (NOMBRE, SECTOR, PISO, NUMERO, CAMA, ID_INSTITUCION, ESTADO) VALUES ('UTI 1', 'Terapia Intensiva', 2, 1, 1, 1, 'ACTIVO');
INSERT INTO UBICACIONES (NOMBRE, SECTOR, PISO, NUMERO, CAMA, ID_INSTITUCION, ESTADO) VALUES ('UTI 2', 'Terapia Intensiva', 2, 1, 2, 1, 'ACTIVO');
INSERT INTO UBICACIONES (NOMBRE, SECTOR, PISO, NUMERO, CAMA, ID_INSTITUCION, ESTADO) VALUES ('Quirófano 1', 'Quirófanos', 1, 1, NULL, 1, 'ACTIVO');
INSERT INTO UBICACIONES (NOMBRE, SECTOR, PISO, NUMERO, CAMA, ID_INSTITUCION, ESTADO) VALUES ('Quirófano 2', 'Quirófanos', 1, 2, NULL, 1, 'ACTIVO');
INSERT INTO UBICACIONES (NOMBRE, SECTOR, PISO, NUMERO, CAMA, ID_INSTITUCION, ESTADO) VALUES ('Emergencias', 'Emergencias', 0, 1, 1, 1, 'ACTIVO');
INSERT INTO UBICACIONES (NOMBRE, SECTOR, PISO, NUMERO, CAMA, ID_INSTITUCION, ESTADO) VALUES ('Pediatría 1', 'Pediatría', 3, 1, 1, 4, 'ACTIVO');
INSERT INTO UBICACIONES (NOMBRE, SECTOR, PISO, NUMERO, CAMA, ID_INSTITUCION, ESTADO) VALUES ('Consultorio 1', 'Consultorios', 1, 1, NULL, 2, 'ACTIVO');

-- =====================================================
-- NIVEL 3: TABLAS CON MÚLTIPLES DEPENDENCIAS
-- =====================================================

-- 12. USUARIOS
INSERT INTO USUARIOS (CEDULA, NOMBRE, APELLIDO, ID_PERFIL, ID_INSTITUCION, EMAIL, NOMBRE_USUARIO, CONTRASENIA, ESTADO, FECHA_NACIMIENTO) 
VALUES ('12345678', 'Juan', 'Pérez', 1, 1, 'admin@hospital.com', 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ACTIVO', TO_DATE('1985-03-15', 'YYYY-MM-DD'));

INSERT INTO USUARIOS (CEDULA, NOMBRE, APELLIDO, ID_PERFIL, ID_INSTITUCION, EMAIL, NOMBRE_USUARIO, CONTRASENIA, ESTADO, FECHA_NACIMIENTO) 
VALUES ('23456789', 'María', 'González', 2, 1, 'aux@hospital.com', 'aux_admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ACTIVO', TO_DATE('1990-07-22', 'YYYY-MM-DD'));

INSERT INTO USUARIOS (CEDULA, NOMBRE, APELLIDO, ID_PERFIL, ID_INSTITUCION, EMAIL, NOMBRE_USUARIO, CONTRASENIA, ESTADO, FECHA_NACIMIENTO) 
VALUES ('34567890', 'Carlos', 'Rodríguez', 3, 1, 'tecnico@hospital.com', 'tecnico', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ACTIVO', TO_DATE('1988-11-10', 'YYYY-MM-DD'));

INSERT INTO USUARIOS (CEDULA, NOMBRE, APELLIDO, ID_PERFIL, ID_INSTITUCION, EMAIL, NOMBRE_USUARIO, CONTRASENIA, ESTADO, FECHA_NACIMIENTO) 
VALUES ('45678901', 'Ana', 'López', 4, 1, 'usuario@hospital.com', 'usuario', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ACTIVO', TO_DATE('1992-05-18', 'YYYY-MM-DD'));

INSERT INTO USUARIOS (CEDULA, NOMBRE, APELLIDO, ID_PERFIL, ID_INSTITUCION, EMAIL, NOMBRE_USUARIO, CONTRASENIA, ESTADO, FECHA_NACIMIENTO) 
VALUES ('56789012', 'Luis', 'Martínez', 1, 2, 'admin@clinica.com', 'admin_clinica', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ACTIVO', TO_DATE('1983-09-25', 'YYYY-MM-DD'));

-- agrego usuarios de Ad --
INSERT INTO USUARIOS (CEDULA, NOMBRE, APELLIDO, ID_PERFIL, ID_INSTITUCION, EMAIL, NOMBRE_USUARIO, CONTRASENIA, ESTADO, FECHA_NACIMIENTO)
VALUES ('3246053', 'Clark', 'Ken', 6, 1, 'superman@hospital.local', 'Superman', 'X2y287ow3Vu8ny+xKEn2IQ==:lQjLHVlqh5wOq4tnG1Z+Dm1tH8yzfS3Uge2cmaci/Wk=', 'ACTIVO', TO_DATE('1988-11-10', 'YYYY-MM-DD'));

INSERT INTO USUARIOS (CEDULA, NOMBRE, APELLIDO, ID_PERFIL, ID_INSTITUCION, EMAIL, NOMBRE_USUARIO, CONTRASENIA, ESTADO, FECHA_NACIMIENTO)
VALUES ('47777375', 'Anastasia', 'López', 1, 1, 'administrador@hospital.local', 'anastasia.lopez', 'X2y287ow3Vu8ny+xKEn2IQ==:lQjLHVlqh5wOq4tnG1Z+Dm1tH8yzfS3Uge2cmaci/Wk=', 'ACTIVO', TO_DATE('1992-05-18', 'YYYY-MM-DD'));

INSERT INTO USUARIOS (CEDULA, NOMBRE, APELLIDO, ID_PERFIL, ID_INSTITUCION, EMAIL, NOMBRE_USUARIO, CONTRASENIA, ESTADO, FECHA_NACIMIENTO)
VALUES ('23302285', 'Luisa', 'Martínez', 2, 2, 'auxiliar@hospital.local', 'luisa.martinez', 'X2y287ow3Vu8ny+xKEn2IQ==:lQjLHVlqh5wOq4tnG1Z+Dm1tH8yzfS3Uge2cmaci/Wk=', 'ACTIVO', TO_DATE('1983-09-25', 'YYYY-MM-DD'));

INSERT INTO USUARIOS (CEDULA, NOMBRE, APELLIDO, ID_PERFIL, ID_INSTITUCION, EMAIL, NOMBRE_USUARIO, CONTRASENIA, ESTADO, FECHA_NACIMIENTO)
VALUES ('21832561', 'Ana', 'De Armas', 4, 1, 'ingbiomedico1@hospital.local', 'ana.dearmas', 'X2y287ow3Vu8ny+xKEn2IQ==:lQjLHVlqh5wOq4tnG1Z+Dm1tH8yzfS3Uge2cmaci/Wk=', 'ACTIVO', TO_DATE('2007-07-13', 'YYYY-MM-DD'));

INSERT INTO USUARIOS (CEDULA, NOMBRE, APELLIDO, ID_PERFIL, ID_INSTITUCION, EMAIL, NOMBRE_USUARIO, CONTRASENIA, ESTADO, FECHA_NACIMIENTO)
VALUES ('03167807', 'Marck', 'Anthony', 5, 1, 'tegnologo1@hospital.local', 'marck.anthony', 'X2y287ow3Vu8ny+xKEn2IQ==:lQjLHVlqh5wOq4tnG1Z+Dm1tH8yzfS3Uge2cmaci/Wk=', 'ACTIVO', TO_DATE('2007-07-13', 'YYYY-MM-DD'));

INSERT INTO USUARIOS (CEDULA, NOMBRE, APELLIDO, ID_PERFIL, ID_INSTITUCION, EMAIL, NOMBRE_USUARIO, CONTRASENIA, ESTADO, FECHA_NACIMIENTO)
VALUES ('66028397', 'Mike', 'Tyson', 3, 1, 'tecnico2@hospital.local', 'mike.tyson', 'X2y287ow3Vu8ny+xKEn2IQ==:lQjLHVlqh5wOq4tnG1Z+Dm1tH8yzfS3Uge2cmaci/Wk=', 'ACTIVO', TO_DATE('2007-07-13', 'YYYY-MM-DD'));

INSERT INTO USUARIOS (CEDULA, NOMBRE, APELLIDO, ID_PERFIL, ID_INSTITUCION, EMAIL, NOMBRE_USUARIO, CONTRASENIA, ESTADO, FECHA_NACIMIENTO)
VALUES ('95890997', 'Bruce', 'Wayne', 6, 1, 'finanzas1@hospital.local', 'bruce.wayne', 'X2y287ow3Vu8ny+xKEn2IQ==:lQjLHVlqh5wOq4tnG1Z+Dm1tH8yzfS3Uge2cmaci/Wk=', 'ACTIVO', TO_DATE('2007-07-13', 'YYYY-MM-DD'));
-- =====================================================
-- NIVEL 4: TABLAS QUE DEPENDEN DE USUARIOS
-- =====================================================

-- 13. USUARIOS_TELEFONOS
INSERT INTO USUARIOS_TELEFONOS (ID_USUARIO, NUMERO) VALUES (1, '099123456');
INSERT INTO USUARIOS_TELEFONOS (ID_USUARIO, NUMERO) VALUES (1, '099654321');
INSERT INTO USUARIOS_TELEFONOS (ID_USUARIO, NUMERO) VALUES (2, '099234567');
INSERT INTO USUARIOS_TELEFONOS (ID_USUARIO, NUMERO) VALUES (3, '099345678');
INSERT INTO USUARIOS_TELEFONOS (ID_USUARIO, NUMERO) VALUES (4, '099456789');

-- =====================================================
-- NIVEL 5: TABLAS QUE DEPENDEN DE EQUIPOS Y UBICACIONES
-- =====================================================

-- 14. EQUIPOS
INSERT INTO EQUIPOS (ID_INTERNO, ID_UBICACION, NRO_SERIE, NOMBRE, ID_TIPO, GARANTIA, ESTADO, ID_PROVEEDOR, ID_PAIS, ID_MODELO, FECHA_ADQUISICION) 
VALUES ('MON001', 1, 'SN123456789', 'Monitor Multiparamétrico', 1, TO_DATE('2025-01-15', 'YYYY-MM-DD'), 'ACTIVO', 1, 1, 1, TO_DATE('2023-01-15', 'YYYY-MM-DD'));

INSERT INTO EQUIPOS (ID_INTERNO, ID_UBICACION, NRO_SERIE, NOMBRE, ID_TIPO, GARANTIA, ESTADO, ID_PROVEEDOR, ID_PAIS, ID_MODELO, FECHA_ADQUISICION) 
VALUES ('MON002', 2, 'SN123456790', 'Monitor Multiparamétrico', 1, TO_DATE('2025-02-20', 'YYYY-MM-DD'), 'ACTIVO', 1, 1, 1, TO_DATE('2023-02-20', 'YYYY-MM-DD'));

INSERT INTO EQUIPOS (ID_INTERNO, ID_UBICACION, NRO_SERIE, NOMBRE, ID_TIPO, GARANTIA, ESTADO, ID_PROVEEDOR, ID_PAIS, ID_MODELO, FECHA_ADQUISICION) 
VALUES ('VENT001', 1, 'SN987654321', 'Ventilador Mecánico', 2, TO_DATE('2025-11-10', 'YYYY-MM-DD'), 'ACTIVO', 2, 2, 5, TO_DATE('2022-11-10', 'YYYY-MM-DD'));

INSERT INTO EQUIPOS (ID_INTERNO, ID_UBICACION, NRO_SERIE, NOMBRE, ID_TIPO, GARANTIA, ESTADO, ID_PROVEEDOR, ID_PAIS, ID_MODELO, FECHA_ADQUISICION) 
VALUES ('BOMBA001', 3, 'SN555666777', 'Bomba de Infusión', 3, TO_DATE('2024-03-05', 'YYYY-MM-DD'), 'ACTIVO', 3, 3, 3, TO_DATE('2023-03-05', 'YYYY-MM-DD'));

INSERT INTO EQUIPOS (ID_INTERNO, ID_UBICACION, NRO_SERIE, NOMBRE, ID_TIPO, GARANTIA, ESTADO, ID_PROVEEDOR, ID_PAIS, ID_MODELO, FECHA_ADQUISICION) 
VALUES ('DESF001', 5, 'SN111222333', 'Desfibrilador', 4, TO_DATE('2027-08-15', 'YYYY-MM-DD'), 'ACTIVO', 4, 4, 4, TO_DATE('2022-08-15', 'YYYY-MM-DD'));

-- =====================================================
-- NIVEL 6: TABLAS QUE DEPENDEN DE USUARIOS Y EQUIPOS
-- =====================================================

-- 15. INTERVENCIONES
INSERT INTO INTERVENCIONES (ID_USUARIO, ID_TIPO, ID_EQUIPO, MOTIVO, FECHA_HORA, COMENTARIOS) 
VALUES (3, 1, 1, 'Mantenimiento preventivo mensual', TO_DATE('2024-01-15 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Equipo funcionando correctamente');

INSERT INTO INTERVENCIONES (ID_USUARIO, ID_TIPO, ID_EQUIPO, MOTIVO, FECHA_HORA, COMENTARIOS) 
VALUES (3, 2, 2, 'Reparación de pantalla', TO_DATE('2024-01-10 14:30:00', 'YYYY-MM-DD HH24:MI:SS'), 'Pantalla con fallas de visualización');

INSERT INTO INTERVENCIONES (ID_USUARIO, ID_TIPO, ID_EQUIPO, MOTIVO, FECHA_HORA, COMENTARIOS) 
VALUES (3, 3, 3, 'Calibración anual', TO_DATE('2024-01-05 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Calibración exitosa');

INSERT INTO INTERVENCIONES (ID_USUARIO, ID_TIPO, ID_EQUIPO, MOTIVO, FECHA_HORA, COMENTARIOS) 
VALUES (3, 4, 4, 'Reparación de motor', TO_DATE('2024-01-12 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Motor reemplazado');

-- =====================================================
-- NIVEL 7: TABLAS QUE DEPENDEN DE EQUIPOS Y UBICACIONES
-- =====================================================

-- 16. EQUIPOS_UBICACIONES
INSERT INTO EQUIPOS_UBICACIONES (ID_EQUIPO, ID_UBICACION, FECHA, USUARIO, OBSERVACIONES) 
VALUES (1, 1, TO_DATE('2023-01-15', 'YYYY-MM-DD'), 1, 'Instalación inicial');

INSERT INTO EQUIPOS_UBICACIONES (ID_EQUIPO, ID_UBICACION, FECHA, USUARIO, OBSERVACIONES) 
VALUES (2, 2, TO_DATE('2023-02-20', 'YYYY-MM-DD'), 1, 'Instalación inicial');

INSERT INTO EQUIPOS_UBICACIONES (ID_EQUIPO, ID_UBICACION, FECHA, USUARIO, OBSERVACIONES) 
VALUES (3, 1, TO_DATE('2022-11-10', 'YYYY-MM-DD'), 1, 'Instalación inicial');

INSERT INTO EQUIPOS_UBICACIONES (ID_EQUIPO, ID_UBICACION, FECHA, USUARIO, OBSERVACIONES) 
VALUES (4, 3, TO_DATE('2023-03-05', 'YYYY-MM-DD'), 1, 'Instalación inicial');

INSERT INTO EQUIPOS_UBICACIONES (ID_EQUIPO, ID_UBICACION, FECHA, USUARIO, OBSERVACIONES) 
VALUES (5, 5, TO_DATE('2022-08-15', 'YYYY-MM-DD'), 1, 'Instalación inicial');

-- =====================================================
-- NIVEL 8: TABLAS DE AUDITORÍA Y BAJAS
-- =====================================================

-- 17. AUDITORIAS
INSERT INTO AUDITORIAS (FECHA_HORA, ID_USUARIO, ID_OPERACION) 
VALUES (TO_DATE('2024-01-15 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1, 1);

INSERT INTO AUDITORIAS (FECHA_HORA, ID_USUARIO, ID_OPERACION) 
VALUES (TO_DATE('2024-01-15 10:30:00', 'YYYY-MM-DD HH24:MI:SS'), 2, 2);

INSERT INTO AUDITORIAS (FECHA_HORA, ID_USUARIO, ID_OPERACION) 
VALUES (TO_DATE('2024-01-15 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 3, 3);

INSERT INTO AUDITORIAS (FECHA_HORA, ID_USUARIO, ID_OPERACION) 
VALUES (TO_DATE('2024-01-15 16:45:00', 'YYYY-MM-DD HH24:MI:SS'), 1, 4);

-- 18. BAJA_EQUIPOS
INSERT INTO BAJA_EQUIPOS (ID_USUARIO, ID_EQUIPO, RAZON, FECHA, ESTADO, COMENTARIOS) 
VALUES (1, 1, 'Vida útil agotada', TO_DATE('2024-01-20', 'YYYY-MM-DD'), 'ACTIVO', 'Equipo reemplazado por modelo más nuevo');

-- 19. BAJA_UBICACIONES
INSERT INTO BAJA_UBICACIONES (ID_USUARIO, ID_UBICACION, RAZON, COMENTARIO, FECHA) 
VALUES (1, 7, 'Renovación de instalaciones', 'Ubicación en remodelación', TO_DATE('2024-01-25', 'YYYY-MM-DD'));

-- =====================================================
-- NIVEL 9: TABLAS DE RELACIÓN
-- =====================================================

-- 20. FUNCIONALIDADES_PERFILES
-- Asignar todas las funcionalidades (IDs del 1 al 72) a los perfiles 1, 2 y 6
BEGIN
FOR i IN 1..72 LOOP
    INSERT INTO FUNCIONALIDADES_PERFILES (ID_FUNCIONALIDAD, ID_PERFIL) VALUES (i, 1);
    INSERT INTO FUNCIONALIDADES_PERFILES (ID_FUNCIONALIDAD, ID_PERFIL) VALUES (i, 2);
    INSERT INTO FUNCIONALIDADES_PERFILES (ID_FUNCIONALIDAD, ID_PERFIL) VALUES (i, 6);
END LOOP;
END;
/
COMMIT;

BEGIN
FOR i IN (
    SELECT COLUMN_VALUE AS ID_FUNC FROM TABLE(SYS.ODCINUMBERLIST(
      3, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
      23, 29, 34, 39, 55, 56, 57, 58, 59, 60, 64, 69, 71
    ))
  ) LOOP
    INSERT INTO FUNCIONALIDADES_PERFILES (ID_FUNCIONALIDAD, ID_PERFIL) VALUES (i.ID_FUNC, 3);
INSERT INTO FUNCIONALIDADES_PERFILES (ID_FUNCIONALIDAD, ID_PERFIL) VALUES (i.ID_FUNC, 4);
INSERT INTO FUNCIONALIDADES_PERFILES (ID_FUNCIONALIDAD, ID_PERFIL) VALUES (i.ID_FUNC, 5);
END LOOP;
END;
/
COMMIT;


-- COMMIT PARA CONFIRMAR TODOS LOS DATOS
-- =====================================================
COMMIT;

-- =====================================================
-- VERIFICACIÓN DE DATOS INSERTADOS
-- =====================================================
SELECT 'OPERACIONES' as tabla, COUNT(*) as cantidad FROM OPERACIONES
UNION ALL
SELECT 'INSTITUCIONES', COUNT(*) FROM INSTITUCIONES
UNION ALL
SELECT 'PERFILES', COUNT(*) FROM PERFILES
UNION ALL
SELECT 'FUNCIONALIDADES', COUNT(*) FROM FUNCIONALIDADES
UNION ALL
SELECT 'TIPOS_INTERVENCIONES', COUNT(*) FROM TIPOS_INTERVENCIONES
UNION ALL
SELECT 'TIPOS_EQUIPOS', COUNT(*) FROM TIPOS_EQUIPOS
UNION ALL
SELECT 'MARCAS_MODELO', COUNT(*) FROM MARCAS_MODELO
UNION ALL
SELECT 'PAISES', COUNT(*) FROM PAISES
UNION ALL
SELECT 'PROVEEDORES_EQUIPOS', COUNT(*) FROM PROVEEDORES_EQUIPOS
UNION ALL
SELECT 'MODELOS_EQUIPOS', COUNT(*) FROM MODELOS_EQUIPOS
UNION ALL
SELECT 'UBICACIONES', COUNT(*) FROM UBICACIONES
UNION ALL
SELECT 'USUARIOS', COUNT(*) FROM USUARIOS
UNION ALL
SELECT 'USUARIOS_TELEFONOS', COUNT(*) FROM USUARIOS_TELEFONOS
UNION ALL
SELECT 'EQUIPOS', COUNT(*) FROM EQUIPOS
UNION ALL
SELECT 'INTERVENCIONES', COUNT(*) FROM INTERVENCIONES
UNION ALL
SELECT 'EQUIPOS_UBICACIONES', COUNT(*) FROM EQUIPOS_UBICACIONES
UNION ALL
SELECT 'AUDITORIAS', COUNT(*) FROM AUDITORIAS
UNION ALL
SELECT 'BAJA_EQUIPOS', COUNT(*) FROM BAJA_EQUIPOS
UNION ALL
SELECT 'BAJA_UBICACIONES', COUNT(*) FROM BAJA_UBICACIONES
UNION ALL
SELECT 'FUNCIONALIDADES_PERFILES', COUNT(*) FROM FUNCIONALIDADES_PERFILES; 