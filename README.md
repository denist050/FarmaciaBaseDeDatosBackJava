# FarmaciaBaseDeDatosBackJava

Este proyecto es un **backend básico** desarrollado en **Java** que implementa un sistema **CRUD (Create, Read, Update, Delete)** conectado a una base de datos relacional. Está diseñado como un ejemplo de prueba para la gestión de información de una farmacia, utilizando cinco tablas principales: `Farmaco`, `Laboratorio`, `Nacionalidad`, `Provincia` y `Ciudad`.

## Tecnologías Utilizadas

- **Java**: Lenguaje principal
- **MySQL**: Base de datos relacional
- **Maven**: Gestión de dependencias
- **Dotenv**: Manejo de variables de entorno
- **JDBC**: Conexión a la base de datos

## Características

- **CRUD Completo**: Crear, leer, actualizar y eliminar registros.
- **Conexión a Base de Datos**: Usando **MySQL**.
- **Diseño Modular**: Separación por paquetes (`conexion`, `datos`, `dominio`, `presentacion`).
- **Interfaz en Consola**: Menú interactivo para operaciones CRUD.
- **Manejo de Relaciones**: Entre `Laboratorio` y `Nacionalidad`, `Provincia`, `Ciudad`.

## Estructura del Proyecto

```plaintext
src/
├── main/
│   ├── java/
│   │   └── farmacia/
│   │       ├── conexion/         # Conexión a la base de datos
│   │       │   └── Conexion.java
│   │       ├── datos/            # Acceso a datos (DAOs)
│   │       │   ├── FarmaciaDAO.java
│   │       │   └── IFarmaciaDAO.java
│   │       ├── dominio/          # Entidades del dominio
│   │       │   ├── Ciudad.java
│   │       │   ├── Farmaco.java
│   │       │   ├── Laboratorio.java
│   │       │   ├── MedicamentoLaboratorio.java
│   │       │   ├── Nacionalidad.java
│   │       │   └── Provincia.java
│   │       └── presentacion/     # Interfaz de usuario
│   │           └── farmaciaApp.java
```

## Requisitos Previos

- **Java**: JDK 8 o superior
- **MySQL**: Base de datos relacional
- **Maven**: Gestión de dependencias
- **Archivo `.env`**: Credenciales de base de datos

## Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/denist050/FarmaciaBaseDeDatosBackJava.git
cd FarmaciaBaseDeDatosBackJava
```

### 2. Configurar la base de datos

1. Instala y ejecuta **MySQL**.
2. Crea la base de datos:

```sql
CREATE DATABASE farmacia_dt;
```

3. Importa el archivo SQL:

```bash
mysql -u tu_usuario -p farmacia_dt < src/main/resources/scripts/farmacia_dt.sql
```

### 3. Configurar el archivo `.env`

Crea un archivo `.env` en la raíz con:

```env
DB_URL=jdbc:mysql://localhost:3306/farmacia_dt
DB_USER=tu_usuario
DB_PASSWORD=tu_contraseña
DB_DRIVER=com.mysql.cj.jdbc.Driver
```

### 4. Configurar el entorno

Asegúrate de tener:

- Java JDK 8 o superior
- Apache Maven

### 5. Compilar el proyecto

```bash
mvn clean install
```

### 6. Ejecutar el programa

```bash
java -cp target/FarmaciaBaseDeDatosBackJava.jar farmacia.presentacion.farmaciaApp
```

### 7. Probar el programa

El sistema abrirá un menú en consola para realizar operaciones CRUD sobre las tablas principales.

## Uso

El programa permite:

- Listar registros
- Buscar por ID o nombre
- Agregar registros
- Modificar registros
- Eliminar registros

### Ejemplo de Menú

#### Farmaco

- `farmaco_id`: ID único
- `nombre`: Nombre del fármaco
- `descripcion`: Descripción
- `tipo`: Venta libre o receta

#### Laboratorio

- `laboratorio_id`: ID único
- `nombre`: Nombre
- `direccion`: Dirección
- `telefono`: Teléfono
- `email`: Correo
- `nacionalidad_id`: FK a **Nacionalidad**
- `provincia_id`: FK a **Provincia**
- `ciudad_id`: FK a **Ciudad**

#### Nacionalidad, Provincia y Ciudad

Tablas relacionadas que permiten definir la ubicación y nacionalidad de los laboratorios.


