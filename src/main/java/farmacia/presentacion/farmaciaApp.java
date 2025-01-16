package farmacia.presentacion;

import farmacia.conexion.Conexion;
import farmacia.datos.FarmaciaDAO;
import farmacia.datos.IFarmaciaDAO;
import farmacia.dominio.Farmaco;
import farmacia.dominio.Laboratorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class farmaciaApp {



    public static void main(String[] args) {
        farmaciaApp();
    }


    private static void farmaciaApp(){

        Scanner entrada = new Scanner(System.in);
        IFarmaciaDAO farmaciaDAO = new FarmaciaDAO() {

        };

        boolean salir = false;
        do{
            try{
                int opcion;
                String entidad = seleccionarEntidad(entrada);
                opcion = mostrarMenu(entrada, entidad);
                salir = ejecutarOpciones(entrada, opcion, farmaciaDAO, entidad);
            }catch (Exception e){
                System.out.println("Error al ejecutar opciones: " + e.getMessage());
            }
            System.out.println();
        }while(salir != true);


    }

    private static String seleccionarEntidad(Scanner entrada){

        System.out.println("Ingrese el numero de la entidad con la que quiera trabajar: ");
        System.out.println("1. farmaco");
        System.out.println("2. laboratorio");
        System.out.println("3. medicamento/laboratorio");
        int opcion = entrada.nextInt();
        String entidad= "";
        switch (opcion) {
            case 1:
                entidad = "farmaco";
                break;
            case 2:
                entidad = "laboratorio";
                break;
            case 3:
                entidad = "medicamento/laboratorio";
                break;
        }
        return entidad;

    }

    private  static int mostrarMenu(Scanner entrada, String entidad){

        System.out.println();
        System.out.println("Tabla "+entidad+": ");
        System.out.println("Opciones: ");
        System.out.println("1. Listar "+entidad+".");
        System.out.println("2. Buscar "+entidad+".");
        System.out.println("3. Agregar "+entidad+".");
        System.out.println("4. Modificar "+entidad+".");
        System.out.println("5. Eliminar "+entidad+".");
        System.out.println("6. Salir");
        int opcion = entrada.nextInt();
        entrada.nextLine();
        return opcion;

    }

    private static boolean ejecutarOpciones(Scanner entrada, int opcion, IFarmaciaDAO farmaciaDAO, String entidad) {


        var listar = farmaciaDAO.listarEntidad(entidad);


        switch (opcion) {
            case 1:
                //Pruebas
                //listar clientes
                //Programación orientada a interfaces. La razón principal para hacerlo es abstraer y desacoplar el código,
                System.out.println("*** Listar "+entidad+" ***");
                listar.forEach(System.out::println);
                break;
            case 2:
                System.out.println("Elija el metodo de prueba para buscar "+entidad+": ");
                System.out.println("1. Id con busqueda por lista.");
                System.out.println("1. Nombre en busqueda por lista.");
                System.out.println("3. Id con busqueda en metodo de FarmaciaDAO");
                int opcionPrueba = entrada.nextInt();
                entrada.nextLine();
                switch (opcionPrueba) {
                    case 1:

                        System.out.println("Por favor ingrese el id del "+entidad+" a buscar: ");
                        int idBuscar = entrada.nextInt();
                        boolean encontrado = false;
                        for (Object obj : listar) { //version resumida -> for(Farmaco f : listar)
                            if(obj instanceof Farmaco) {
                                Farmaco f = (Farmaco) obj;
                                if (f.getFarmaco_id() == idBuscar) {
                                    encontrado = true;
                                    System.out.println("Farmaco encontrado: " + f.getNombre());
                                    break;

                                }
                            }
                            else if (obj instanceof Laboratorio) {
                                Laboratorio l = (Laboratorio) obj;
                                if (l.getLaboratorio_id() == idBuscar){
                                    encontrado = true;
                                    System.out.println("Laboratorio encontrado: " + l.getNombre());
                                    break;
                                }
                            }

                        }
                        if (!encontrado) {
                            System.out.println("No encontrado");
                        }
                        entrada.nextLine();
                        break;
                    case 2:

                        boolean encontrado1 = false;
                        System.out.println("Ingrese el nombre del "+entidad+" a buscar: ");
                        String idBuscarPorString = entrada.nextLine();
                        for (Object obj : listar) {
                            Farmaco f = (Farmaco) obj;
                            if (f.getNombre().equalsIgnoreCase(idBuscarPorString)) {
                                System.out.println("Farmaco encontrado: " + f.getNombre() + " Farmaco id: " + f.getFarmaco_id());
                                encontrado1 = true;
                                break;
                            }

                        }
                        if (!encontrado1) {
                            System.out.println("No encontrado");
                        }
                        break;
                    /*case 3:


                        System.out.println("Ingrese el id del farmaco a buscar: ");
                        farmacoId = entrada.nextInt();

                        Farmaco farmaco = farmaciaDAO.buscarFarmacoPorId(farmacoId);

                        if (farmaco != null)
                            System.out.println("Farmaco encontrado " + farmaco.getNombre());
                        else
                            System.out.println("No se encontro registro.");
                        break;*/
                }
                break;
            case 3:

                switch (entidad){


                    case "farmaco":

                        Farmaco nuevoFarmaco = farmaciaDAO.validacionFarmaco();
                        boolean agregado = farmaciaDAO.insertar(nuevoFarmaco, null,  entidad);

                        if (agregado)
                            System.out.println("Farmaco agregado: \n" + "Nombre: "+ nuevoFarmaco.getNombre() + "\nFarmaco ID: "+ nuevoFarmaco.getFarmaco_id());
                        else
                            System.out.println("No se agrego el farmaco: " + nuevoFarmaco.getNombre());
                        break;
                    case "laboratorio":
                        Laboratorio nuevoLaboratorio = farmaciaDAO.validacionLaboratorio(null);
                        agregado = farmaciaDAO.insertar(null, nuevoLaboratorio,  entidad);

                        if (agregado)
                            System.out.println("Laboratorio agregado: \n" + "Nombre: "+ nuevoLaboratorio.getNombre() + "\nLaboratorio ID: "+ nuevoLaboratorio.getLaboratorio_id());
                        else
                            System.out.println("No se agrego el laboratorio: " + nuevoLaboratorio);
                        break;
                }break;


            case 4:


                switch (entidad){

                    case "farmaco":

                        System.out.println("Ingrese el id o nombre del farmaco a modificar: ");
                        String input = entrada.nextLine();

                        try {
                            int id = Integer.parseInt(input);//busca por id
                            for (Object obj : listar) { //version resumida -> for(Farmaco f : listar)
                                if(obj instanceof Farmaco) {
                                    Farmaco f = (Farmaco) obj;
                                    if (f.getFarmaco_id() == id) {
                                        System.out.println("Farmaco encontrado: " + f.getNombre());
                                        Farmaco modificarFarmaco = farmaciaDAO.validacionPorId(id);
                                        if (farmaciaDAO.modificarFarmaco(modificarFarmaco, null, true, null, entidad)) {
                                            System.out.println("Farmaco Modificado exitosamente");
                                        } else {
                                            System.out.println("No se pudo modificar farmaco");
                                        }
                                        break;
                                    }
                                }
                            }
                        } catch (NumberFormatException e) {

                            for (Object obj : listar) { //version resumida -> for(Farmaco f : listar)
                                if(obj instanceof Farmaco) {
                                    Farmaco f = (Farmaco) obj;
                                    if (f.getNombre().equalsIgnoreCase(input)) {
                                        System.out.println("Farmaco encontrado: " + f.getNombre() + " Farmaco id: " + f.getFarmaco_id());
                                        Farmaco modificarFarmaco = farmaciaDAO.validacionFarmaco();
                                        if (farmaciaDAO.modificarFarmaco(modificarFarmaco, null, false, input, entidad)) {
                                            System.out.println("Farmaco Modificado exitosamente");
                                        } else {
                                            System.out.println("No se pudo modificar farmaco");
                                        }
                                        break;
                                    }
                                }
                            }
                        }break;


                    case "laboratorio":

                        System.out.println("Ingrese el id del laboratorio a modificar: ");
                        input = entrada.nextLine();

                        int id = Integer.parseInt(input);//busca por id
                            for (Object obj : listar) { //version resumida -> for(Farmaco f : listar)
                                if(obj instanceof Laboratorio) {
                                    Laboratorio l = (Laboratorio) obj;
                                if (l.getLaboratorio_id() == id) {
                                    Laboratorio modificarLaboratorio = farmaciaDAO.validacionLaboratorio(id);
                                    boolean agregado = farmaciaDAO.modificarFarmaco(null, modificarLaboratorio, false, null, entidad);

                                    if (agregado)
                                        System.out.println("Laboratorio agregado: \n" + "Nombre: " + modificarLaboratorio.getNombre() + "\nLaboratorio ID: " + modificarLaboratorio.getLaboratorio_id());
                                    else
                                        System.out.println("No se agrego el laboratorio: " + modificarLaboratorio);
                                    break;
                                }
                            }
                        }
                }break;


            case 5:

                switch (entidad){
                    case "farmaco":
                        try {

                            System.out.println("Ingrese el id del farmaco a eliminar: ");
                            Integer farmacoEliminarId = entrada.nextInt();
                            boolean encontrado = false;

                            for (Object obj : listar) {
                                if(obj instanceof Farmaco) {
                                    Farmaco f = (Farmaco) obj;
                                    if (f.getFarmaco_id() == farmacoEliminarId) {
                                        farmaciaDAO.eliminar(f, null, farmacoEliminarId, entidad);
                                        System.out.println("Farmaco eliminado con exito.");
                                        encontrado = true;
                                        break;
                                    }
                                }
                            }
                            if (!encontrado) {
                                System.out.println("El farmaco no se encuentra.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Entrada no valida. Por favor, ingrese un numero.");
                            entrada.nextLine();
                        } catch (Exception e) {
                            System.out.println("Ocurrio un error esperado: " + e.getMessage());
                        }
                        break;
                    case "laboratorio":
                        try {

                            System.out.println("Ingrese el id del laboratorio a eliminar: ");
                            int laboratorioEliminarId = entrada.nextInt();
                            boolean encontrado = false;

                            for (Object obj : listar) {
                                if(obj instanceof Laboratorio) {
                                    Laboratorio l = (Laboratorio) obj;
                                    if (l.getLaboratorio_id() == laboratorioEliminarId) {
                                        farmaciaDAO.eliminar(null, l, laboratorioEliminarId, entidad);
                                        System.out.println("Laboratorio eliminado con exito.");
                                        encontrado = true;
                                        break;
                                    }
                                }
                            }
                            if (!encontrado) {
                                System.out.println("El Laboratorio no se encuentra.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Entrada no valida. Por favor, ingrese un numero.");
                            entrada.nextLine();
                        } catch (Exception e) {
                            System.out.println("Ocurrio un error esperado: " + e.getMessage());
                        }
                        break;
                }break;


            case 6:
                System.out.println("Cerrando programa... ");
                return true;
            default:
                System.out.println("Opcion no valida");
        }

        return false;

    }
}
