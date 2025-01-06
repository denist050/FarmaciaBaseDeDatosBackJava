package farmacia.conexion;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Conexion {

    public static Connection getConexion(){

        Connection conexion = null;

        Dotenv dotenv = Dotenv.load();

        String url = dotenv.get("DB_URL");
        String usuario = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

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
