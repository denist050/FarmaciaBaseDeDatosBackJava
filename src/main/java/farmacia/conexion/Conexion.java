package farmacia.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Conexion {

    public static Connection getConexion(){

        Connection conexion = null;

        var baseDatos = "farmacia_dt";

        var url = "jdbc:mysql://localhost:3306/" + baseDatos;

        var usuario = "root";

        var password = "Kerosene472nafta";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); //clase de conexion a la base de datos
            conexion = DriverManager.getConnection(url, usuario, password);
        }catch (Exception e){
            System.out.println("Error al conectarse a BD: " + e.getMessage());
        }

        return conexion;

    }

    public static void main(String[] args) {
        Connection conexion = Conexion.getConexion();

        if(conexion != null){
            try {

                //objeto de tipo statement para hacer consultas a la base de datos
                Statement miStatement = conexion.createStatement();

                ResultSet miResultset = miStatement.executeQuery("SELECT * FROM personal");

                while (miResultset.next()){

                    System.out.println(miResultset.getString("nombre") + " " + miResultset.getString("apellido") +
                            " " + miResultset.getDate("fecha_nacimiento"));

                }


            }catch(Exception e){
                System.out.println("Error" + e.getMessage());
            }

        }

        else
            System.out.println("Error al conectarse");




    }

}
