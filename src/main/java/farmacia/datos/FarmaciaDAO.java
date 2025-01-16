package farmacia.datos;

import farmacia.conexion.Conexion;
import farmacia.dominio.Farmaco;
import farmacia.dominio.Laboratorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class FarmaciaDAO implements IFarmaciaDAO {

    Scanner entrada = new Scanner(System.in);


    //FarmaciaDAO es una clase que implementa la interfaz IFarmaciaDAO,
    // lo que significa que proporciona la implementación concreta de los métodos definidos en la interfaz.

    //(<Entidad>): Declara que el metodo es genérico y que Entidad es un tipo "temporal", Es un marcador temporal que permite
    // que el metodo sea reutilizable. Luego, cuando el metodo se llama, debes indicar qué clase concreta quieres usar.
    //(List<Entidad>): Indica que el metodo retorna una lista de objetos del tipo Entidad.
    @Override
    public <Entidad> List<Entidad> listarEntidad(String entidad) {

        List<Entidad> entidades = new ArrayList<>();
        PreparedStatement ps; //Objeto de la clase ps, nos permite preparar la sentencia sql que ejecutaremos desde la base
        ResultSet rs; //recibimos la informacion de la consulta
        Connection conexion = Conexion.getConexion();//llamamos a la clase Conexion y utilizamos el metodo getConexion que nos devolvera la conexion de sql y se la asignamos a una variable de tipo Connection
        String sql = "SELECT * FROM " + entidad + " ORDER BY " + entidad + "_id";
        try {
            ps = conexion.prepareStatement(sql); //con el objeto de conexion obtenemos la conexion, usamos el metodo prepareStament
            // , preparamos la sentencia y luego lo guardamos en el objeto ps para luego hacer uso del metodo executeQuery que ejecutara la sentencia.
            rs = ps.executeQuery();
            while (rs.next()) { //metodo next se posiciona en el registro a iterarar

                Object objeto = null;

                switch (entidad) {
                    case "farmaco":
                        Farmaco farmaco = new Farmaco();
                        farmaco.setFarmaco_id(rs.getInt("farmaco_id"));
                        farmaco.setNombre(rs.getString("nombre"));
                        farmaco.setDescripcion(rs.getString("descripcion"));
                        farmaco.setTipo(rs.getString("tipo"));
                        objeto = farmaco;
                        break;
                    case "laboratorio":
                        Laboratorio laboratorio = new Laboratorio();
                        laboratorio.setLaboratorio_id(rs.getInt("laboratorio_id"));
                        laboratorio.setNombre(rs.getString("nombre"));
                        laboratorio.setDireccion(rs.getString("direccion"));
                        laboratorio.setTelefono(rs.getString("telefono"));
                        laboratorio.setEmail(rs.getString("email"));
                        laboratorio.setNacionalidad_id(rs.getInt("nacionalidad_id"));
                        laboratorio.setProvincia_id(rs.getInt("provincia_id"));
                        laboratorio.setCiudad_id(rs.getInt("ciudad_id"));
                        objeto = laboratorio;
                        break;
                    default:
                        throw new IllegalArgumentException("Entidad no reconocida: " + entidad);
                }
                if (objeto != null) {
                    entidades.add((Entidad) objeto);
                }

            }
        } catch (Exception e) {
            System.out.println("Error al listar " + entidad + ": " + e.getMessage());
        } finally {
            try {
                conexion.close();

            } catch (Exception e) {
                System.out.println("Error");
            }
        }
        return entidades;

    }


    @Override
    public boolean insertar(Farmaco farmaco, Laboratorio laboratorio, String entidad) {
        PreparedStatement ps;
        Connection conexion = Conexion.getConexion();
        switch (entidad) {
            case "farmaco":
                String sql = "INSERT INTO farmaco(nombre, descripcion, tipo)" + " VALUES(?, ?, ?)";
                try {
                    ps = conexion.prepareStatement(sql);
                    ps.setString(1, farmaco.getNombre());
                    ps.setString(2, farmaco.getDescripcion());
                    ps.setString(3, farmaco.getTipo());

                    //ejecutamos la insercion
                    int filasAfectadas = ps.executeUpdate();

                    if (filasAfectadas > 0){
                        farmaco.setFarmaco_id(obtenerUltimoIdGenerado(conexion));
                        return true;
                    }else {
                        return false;
                    }

                } catch (Exception e) {
                    System.out.println("Error al agregar cliente: " + e.getMessage());
                } finally {
                    try {
                        conexion.close();
                    } catch (Exception e) {
                        System.out.println("Error al cerrar conexion: " + e.getMessage());
                    }
                }
            case "laboratorio":

                sql = "INSERT INTO laboratorio(nombre, direccion, telefono, email, nacionalidad_id, provincia_id, ciudad_id)" + " VALUES(?, ?, ?, ?, ?, ?, ?)";

                try {


                    ps = conexion.prepareStatement(sql);
                    ps.setString(1, laboratorio.getNombre());
                    ps.setString(2, laboratorio.getDireccion());
                    ps.setString(3, laboratorio.getTelefono());
                    ps.setString(4, laboratorio.getEmail());
                    ps.setInt(5, obtenerUbicacion("nacionalidad_id", "nacionalidad", laboratorio.getNacionalidad(), laboratorio));
                    ps.setInt(6, obtenerUbicacion("provincia_id", "provincia", laboratorio.getProvincia(), laboratorio));
                    ps.setInt(7, obtenerUbicacion("ciudad_id", "ciudad", laboratorio.getCiudad(), laboratorio));

                    int filasAfectadas = ps.executeUpdate();

                    if(filasAfectadas > 0){
                        laboratorio.setLaboratorio_id(obtenerUltimoIdGenerado(conexion));
                        return true;
                    }else {
                        return false;
                    }

                } catch (Exception e) {
                    System.out.println("Error al insertar: " + e.getMessage());
                } finally {
                    try {
                        conexion.close();
                    } catch (Exception e) {
                        System.out.println("Error al cerrar conexion: " + e.getMessage());
                    }
                }
        }


        return false;
    }

    @Override
    public boolean modificarFarmaco(Farmaco farmaco, Laboratorio laboratorio, boolean buscarPorId, String input, String entidad) {
        PreparedStatement ps = null;
        Connection conexion = Conexion.getConexion();
        String sql;

        switch (entidad) {
            case "farmaco":


                if (buscarPorId) {
                    sql = "UPDATE farmaco SET nombre=?, descripcion=?, tipo=? WHERE farmaco_id=?";
                } else {
                    sql = "UPDATE farmaco SET nombre=?, descripcion=?, tipo=? WHERE nombre=?";
                }

                try {
                    ps = conexion.prepareStatement(sql);
                    ps.setString(1, farmaco.getNombre());
                    ps.setString(2, farmaco.getDescripcion());
                    ps.setString(3, farmaco.getTipo());

                    if (buscarPorId) {
                        ps.setInt(4, farmaco.getFarmaco_id());
                    } else {
                        ps.setString(4, input);
                    }

                    System.out.println("SQL Query: " + sql);
                    System.out.println("Datos a modificar:");
                    System.out.println("Nombre: " + farmaco.getNombre());
                    System.out.println("Descripcion: " + farmaco.getDescripcion());
                    System.out.println("Tipo: " + farmaco.getTipo());
                    if (buscarPorId) {
                        System.out.println("ID: " + farmaco.getFarmaco_id());
                    } else {
                        System.out.println("Nombre para búsqueda: " + input);
                    }

                    int filasAfectadas = ps.executeUpdate();
                    if (filasAfectadas > 0) {
                        return true;
                    } else {
                        System.out.println("No se encontró ningún registro para actualizar.");
                    }

                } catch (Exception e) {
                    System.out.println("Error al modificar farmaco: " + e.getMessage());
                } finally {
                    try {
                        if (ps != null) ps.close();
                        if (conexion != null && !conexion.isClosed()) conexion.close();
                    } catch (Exception e) {
                        System.out.println("Error al cerrar recursos: " + e.getMessage());
                    }
                }

            case "laboratorio":

                sql = "UPDATE laboratorio SET nombre=?, direccion=?, telefono=?, email=?, nacionalidad_id=?, provincia_id=?, ciudad_id=? WHERE laboratorio_id=?";

                try {
                    ps = conexion.prepareStatement(sql);
                    ps.setString(1, laboratorio.getNombre());
                    ps.setString(2, laboratorio.getDireccion());
                    ps.setString(3, laboratorio.getTelefono());
                    ps.setString(4, laboratorio.getEmail());
                    ps.setInt(5, obtenerUbicacion("nacionalidad_id", "nacionalidad", laboratorio.getNacionalidad(), laboratorio));
                    ps.setInt(6, obtenerUbicacion("provincia_id", "provincia", laboratorio.getProvincia(), laboratorio));
                    ps.setInt(7, obtenerUbicacion("ciudad_id", "ciudad", laboratorio.getCiudad(), laboratorio));
                    ps.setInt(8, laboratorio.getLaboratorio_id());

                    System.out.println("SQL Query: " + sql);
                    System.out.println("Datos a modificar:");
                    System.out.println("Nombre: " + laboratorio.getNombre());
                    System.out.println("direccion: " + laboratorio.getDireccion());
                    System.out.println("telefono: " + laboratorio.getTelefono());
                    System.out.println("email: " + laboratorio.getEmail());
                    System.out.println("nacionalidad_id: " + obtenerUbicacion("nacionalidad_id", "nacionalidad", laboratorio.getNacionalidad(), laboratorio));
                    System.out.println("provincia_id: " + obtenerUbicacion("provincia_id", "provincia", laboratorio.getProvincia(), laboratorio));
                    System.out.println("ciudad_id: " + obtenerUbicacion("ciudad_id", "ciudad", laboratorio.getCiudad(), laboratorio));


                    int filasAfectadas = ps.executeUpdate();
                    if (filasAfectadas > 0) {
                        return true;
                    } else {
                        System.out.println("No se encontró ningún registro para actualizar.");
                    }

                } catch (Exception e) {
                    System.out.println("Error al modificar farmaco: " + e.getMessage());
                } finally {
                    try {
                        if (ps != null) ps.close();
                        if (conexion != null && !conexion.isClosed()) conexion.close();
                    } catch (Exception e) {
                        System.out.println("Error al cerrar recursos: " + e.getMessage());
                    }
                }
        }


        return false;
    }

    @Override
    public boolean eliminar(Farmaco farmaco, Laboratorio laboratorio, int id, String entidad) {

        switch (entidad) {
            case "farmaco":
                PreparedStatement ps = null;
                Connection conexion = Conexion.getConexion();
                String sql = "DELETE FROM farmaco WHERE farmaco_id = ?";
                System.out.println("SQL Query: " + sql);

                System.out.println("Datos a eliminar:");
                System.out.println("Nombre: " + farmaco.getNombre());
                System.out.println("Descripcion: " + farmaco.getDescripcion());
                System.out.println("Tipo: " + farmaco.getTipo());
                System.out.println("ID: " + farmaco.getFarmaco_id());
                System.out.println("Presiona 1 para confirmar la eliminacion: ");
                int confirmacion = entrada.nextInt();

                if (confirmacion == 1) {
                    try {
                        ps = conexion.prepareStatement(sql);
                        ps.setInt(1, id);
                        int filasAfectadas = ps.executeUpdate();
                        if (filasAfectadas > 0) {
                            return true; // Se eliminó correctamente
                        } else {
                            System.out.println("No se encontró el laboratorio con ID: " + id);
                        }

                    } catch (Exception e) {
                        System.out.println("Error al eliminar farmaco: " + e.getMessage());
                    } finally {
                        try {
                            if (ps != null) ps.close();
                            if (conexion != null && !conexion.isClosed()) conexion.close();
                        } catch (Exception e) {
                            System.out.println("Error al cerrar recursos: " + e.getMessage());
                        }
                    }
                } else {
                    System.out.println("Opcion incorrecta");

                }
                return false;
            case "laboratorio":

                ps = null;
                conexion = Conexion.getConexion();
                sql = "DELETE FROM laboratorio WHERE laboratorio_id = ?";
                System.out.println("SQL Query: " + sql);

                System.out.println("Datos a elimanar:");
                System.out.println("laboratorio_id: " + laboratorio.getLaboratorio_id());
                System.out.println("Nombre: " + laboratorio.getNombre());
                System.out.println("direccion: " + laboratorio.getDireccion());
                System.out.println("telefono: " + laboratorio.getTelefono());
                System.out.println("email: " + laboratorio.getEmail());

                System.out.println("Presiona 1 para confirmar la eliminacion: ");
                confirmacion = entrada.nextInt();

                if (confirmacion == 1) {
                    try {
                        ps = conexion.prepareStatement(sql);
                        ps.setInt(1, id);
                        ps.executeUpdate();
                        return true;
                    } catch (Exception e) {
                        System.out.println("Error al eliminar farmaco: " + e.getMessage());
                    } finally {
                        try {
                            if (ps != null) ps.close();
                            if (conexion != null && !conexion.isClosed()) conexion.close();
                        } catch (Exception e) {
                            System.out.println("Error al cerrar recursos: " + e.getMessage());
                        }
                    }
                } else {
                    System.out.println("Opcion incorrecta");

                }
                return false;


        }

        return false;
    }




    public Farmaco validacionFarmaco() {


        Scanner entrada = new Scanner(System.in);

        System.out.println("Ingrese el nombre del nuevo farmaco a agregar: ");
        String nombre = entrada.nextLine();
        System.out.println("Ingrese descripcion: ");
        String descripcion = entrada.nextLine();
        System.out.println("Ingrese si es venta libre/receta: ");
        String tipo = entrada.nextLine();

        Farmaco nuevoFarmaco = new Farmaco(nombre, descripcion, tipo);
        return nuevoFarmaco;

    }

    public Laboratorio validacionLaboratorio(Integer id) {


        Scanner entrada = new Scanner(System.in);

        System.out.println("Ingrese el nombre del laboratorio a agregar/modificar: ");
        String nombre = entrada.nextLine();
        System.out.println("Ingrese direccion: ");
        String direccion = entrada.nextLine();
        System.out.println("Ingrese telefono: ");
        String telefono = entrada.nextLine();
        System.out.println("Ingrese email");
        String email = entrada.nextLine();
        System.out.println("Ingrese nacionalidad");
        String nacionalidad = entrada.nextLine();
        System.out.println("Ingrese provincia:");
        String provincia = entrada.nextLine();
        System.out.println("Ingrese ciudad:");
        String ciudad = entrada.nextLine();
        if (id == null) {
            return new Laboratorio(nombre, direccion, telefono, email, nacionalidad, provincia, ciudad);
        } else {
            return new Laboratorio(id, nombre, direccion, telefono, email, nacionalidad, provincia, ciudad);
        }

    }

    public Farmaco validacionPorId(int id) {

        Scanner entrada = new Scanner(System.in);

        System.out.println("Ingrese el nombre del farmaco a agregar/modificar: ");
        String nombre = entrada.nextLine();
        System.out.println("Ingrese descripcion: ");
        String descripcion = entrada.nextLine();
        System.out.println("Ingrese si es venta libre/receta: ");
        String tipo = entrada.nextLine();

        var nuevoFarmaco = new Farmaco(id, nombre, descripcion, tipo);
        return nuevoFarmaco;

    }

    private String buscarNombrePorId(String tabla, int id, Connection conexion) {
        String sql = "SELECT nombre FROM " + tabla + " WHERE " + tabla + "_id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("nombre");
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar nombre por ID: " + e.getMessage());
        }
        return null;
    }

    public int obtenerUbicacion(String tablaId, String tabla, String nombre, Laboratorio laboratorio){

        try{
            Connection conexion = Conexion.getConexion();
            PreparedStatement ps;

            ResultSet rs;
            String sql = "SELECT +" + tablaId + " FROM "+ tabla + " WHERE nombre = ?";
            ps = conexion.prepareStatement(sql);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            int Id = 0;
            if(rs.next()){
                Id = rs.getInt(tablaId);
            } else {
                throw new IllegalArgumentException("Objeto no encontrado: " + laboratorio);
            }
            return Id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public int obtenerUltimoIdGenerado(Connection conexion) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT LAST_INSERT_ID()";
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);  // Devuelve el último ID generado
            } else {
                throw new IllegalArgumentException("No se encontró el último ID generado.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el último ID generado: " + e.getMessage(), e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
}
