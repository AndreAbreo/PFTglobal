# Arquitectura del Backend

## 1. Stack Tecnológico
* **Lenguaje:** Java 17
* **Framework Principal:** Jakarta EE 10.0.0 (Enterprise Java Platform)
* **Gestión de Dependencias:** Maven 3.8.1
* **Servidor de Aplicaciones:** Servidor compatible con Jakarta EE (desplegado como WAR)
* **Framework de Persistencia:** Hibernate 6.2.4.Final + JPA 3.0
* **Base de Datos:** Oracle Database (con OracleDialect)
* **Documentación API:** OpenAPI 3.1.1 + Swagger UI 4.15.5

## 2. Arquitectura de la API REST
* **Descripción:** API RESTful basada en JAX-RS diseñada para gestionar el mantenimiento de equipos biomédicos en hospitales. Implementa un sistema completo de autenticación JWT, autorización basada en roles, y operaciones CRUD para todas las entidades del dominio.

* **Endpoints Principales:**

| Verbo HTTP | Ruta del Endpoint | Descripción de la Funcionalidad |
| :--- | :--- | :--- |
| `POST` | `/api/usuarios/login` | `Autenticación de usuario con email y contraseña` |
| `POST` | `/api/usuarios/google-login` | `Autenticación con Google OAuth2` |
| `POST` | `/api/usuarios/crear` | `Registra un nuevo usuario en el sistema` |
| `GET` | `/api/usuarios/listar` | `Obtiene listado completo de usuarios` |
| `GET` | `/api/usuarios/filtrar` | `Filtra usuarios por criterios específicos` |
| `PUT` | `/api/usuarios/modificar` | `Modifica usuario (solo administradores)` |
| `PUT` | `/api/usuarios/inactivar` | `Inactiva un usuario del sistema` |
| `POST` | `/api/equipos/crear` | `Registra un nuevo equipo biomédico` |
| `GET` | `/api/equipos/listar` | `Obtiene listado de todos los equipos` |
| `GET` | `/api/equipos/filtrar` | `Filtra equipos por múltiples criterios` |
| `PUT` | `/api/equipos/modificar` | `Modifica información de un equipo` |
| `PUT` | `/api/equipos/inactivar` | `Da de baja un equipo del sistema` |
| `GET` | `/api/equipos/listarBajas` | `Lista equipos dados de baja` |
| `POST` | `/api/intervenciones/crear` | `Registra una nueva intervención/mantenimiento` |
| `GET` | `/api/intervenciones/listar` | `Lista todas las intervenciones` |
| `GET` | `/api/intervenciones/reportePorFechas` | `Genera reportes por rango de fechas` |
| `GET` | `/api/ubicaciones/listar` | `Lista todas las ubicaciones disponibles` |
| `GET` | `/api/ubicaciones/seleccionar` | `Obtiene información de una ubicación específica` |
| `GET` | `/api/perfiles/listar` | `Lista todos los perfiles/roles del sistema` |
| `GET` | `/api/funcionalidades/listar` | `Lista funcionalidades disponibles por perfil` |
| `GET` | `/api/openapi.json` | `Documentación OpenAPI de la API` |

## 3. Persistencia de Datos
* **Framework de Persistencia:** JPA 3.0 con Hibernate 6.2.4.Final
* **Configuración de Creación de Esquema:**
    *Configuración encontrada en `src/main/resources/META-INF/persistence.xml`*
    ```properties
    hibernate.hbm2ddl.auto=validate
    hibernate.show_sql=false
    hibernate.format_sql=false
    hibernate.dialect=org.hibernate.dialect.OracleDialect
    ```
* **Datasource:** `java:/OracleDS` - Configurado como JTA datasource en el servidor de aplicaciones
* **Estrategia de Esquema:** `validate` - No crea ni modifica esquemas automáticamente, solo valida que coincidan con las entidades

## 4. Patrones de Diseño Implementados

* **Patrón 1: Enterprise JavaBeans (EJB)**
    * **Propósito:** Manejo de la lógica de negocio mediante componentes empresariales con gestión automática de transacciones, inyección de dependencias y ciclo de vida.
    * **Implementación:** Cada entidad tiene su Bean de servicio (`@Stateless`) con interfaz Remote correspondiente en `src/main/java/codigocreativo/uy/servidorapp/servicios/`.

* **Patrón 2: Data Transfer Object (DTO)**
    * **Propósito:** Transferir datos entre capas sin exponer entidades JPA directamente, optimizando el tráfico de red y proporcionando una capa de abstracción.
    * **Implementación:** DTOs definidos en `src/main/java/codigocreativo/uy/servidorapp/dtos/` con mappers automáticos usando MapStruct.

* **Patrón 3: Service Layer**
    * **Propósito:** Centralizar la lógica de negocio del dominio en una capa de servicios reutilizable, desacoplando los controladores REST de la lógica.
    * **Implementación:** `EquipoBean` y otros servicios EJB encapsulan reglas de negocio, y son consumidos desde `src/main/java/codigocreativo/uy/servidorapp/ws/`.

* **Patrón 4: DAO (Data Access Object)**
    * **Propósito:** Encapsular el acceso a la base de datos y aislarlo del resto del sistema.
    * **Implementación:** Las clases EJB utilizan `EntityManager` para acceder a la base de datos, siguiendo el patrón DAO aunque sin clases separadas explícitas.

* **Patrón 5: Inyección de Dependencias (Dependency Injection)**
    * **Propósito:** Desacoplar las dependencias entre clases y facilitar el testeo, mantenimiento y extensibilidad.
    * **Implementación:** Uso de `@Inject`, `@EJB` y `@PersistenceContext` para inyección automática de dependencias por parte del contenedor.

* **Patrón 6: Mapper**
    * **Propósito:** Convertir objetos entre diferentes modelos (por ejemplo, de entidad JPA a DTO) evitando código repetitivo.
    * **Implementación:** MapStruct se encarga de generar automáticamente las clases que convierten entidades a DTOs y viceversa.

* **Patrón 7: Interceptor / Filter**
    * **Propósito:** Aplicar lógica transversal como seguridad, auditoría o CORS sin acoplarla a la lógica de negocio.
    * **Implementación:** Filtros definidos en `src/main/java/codigocreativo/uy/servidorapp/filtros/`, como `JwtTokenFilter`, interceptan y validan los requests entrantes.

* **Patrón 8: RESTful Resource**
    * **Propósito:** Exponer operaciones sobre recursos del dominio a través de una interfaz uniforme basada en HTTP.
    * **Implementación:** Los recursos definidos en `src/main/java/codigocreativo/uy/servidorapp/ws/` siguen las convenciones REST usando JAX-RS (`@Path`, `@GET`, `@POST`, etc.).

* **Patrón 9: Data Mapper**
    * **Propósito:** Separar el modelo de dominio de la lógica de acceso a datos, mapeando objetos a tablas de base de datos sin que las entidades gestionen directamente la persistencia.
    * **Implementación:** Hibernate actúa como Data Mapper, traduciendo entre objetos Java y la base de datos relacional, manteniendo a las entidades independientes de SQL y operaciones de almacenamiento.


## 5. Seguridad

* **Mecanismo de Autenticación:** Sistema dual con JWT local y OAuth2 con Google. Los usuarios pueden autenticarse mediante credenciales locales o su cuenta Google. JWT generado con librería `io.jsonwebtoken` (jjwt 0.11.5).

* **Autorización:** Sistema basado en roles con control granular mediante la tabla `FUNCIONALIDADES_PERFILES`. Se utilizan filtros JAX-RS (`@Provider`) para validar permisos en cada endpoint según el perfil del usuario autenticado.

* **Configuración de JWT:** 
    * Clave secreta obtenida desde variable de entorno `SECRET_KEY`
    * Tiempo de expiración: 5 minutos
    * Algoritmo: HS256 (HMAC-SHA256)
    * Claims incluyen: email, perfil, fecha de emisión y expiración

* **Integración con Active Directory:** TODO: documentar.

## 6. Arquitectura de Componentes

### 6.1 Estructura de Paquetes
```
codigocreativo.uy.servidorapp/
├── config/          # Configuraciones (OpenAPI, Jackson)
├── dtos/            # Data Transfer Objects
├── entidades/       # Entidades JPA
├── enumerados/      # Enumeraciones
├── excepciones/     # Excepciones personalizadas
├── filtros/         # Filtros JAX-RS (seguridad, CORS, auditoría)
├── jwt/             # Servicios JWT
├── servicios/       # EJB Beans (lógica de negocio)
└── ws/              # Resources JAX-RS (endpoints REST)
```

### 6.2 Modelo de Datos
El sistema gestiona las siguientes entidades principales:
* **USUARIOS**: Gestión de usuarios con roles y perfiles
* **EQUIPOS**: Equipos biomédicos con información completa
* **INTERVENCIONES**: Mantenimientos y reparaciones
* **UBICACIONES**: Ubicaciones físicas de los equipos
* **INSTITUCIONES**: Instituciones médicas
* **AUDITORIAS**: Trazabilidad de operaciones

### 6.3 Características Técnicas
* **Transacciones:** Gestionadas automáticamente por el contenedor EJB
* **Inyección de Dependencias:** CDI (Context and Dependency Injection)
* **Validaciones:** Librerías especializadas (ej: CIUY para validación de cédulas uruguayas)
* **Logging:** Java Util Logging integrado
* **Testing:** JUnit 5 + Mockito para pruebas unitarias
* **Documentación:** OpenAPI 3.1.1 con Swagger UI integrado
* **Mapeo de Objetos:** MapStruct para conversiones automáticas DTO↔Entity