package farmacia.datos;

import farmacia.dominio.Farmaco;
import farmacia.dominio.Laboratorio;

import java.util.List;



//Una interfaz es un contrato que define los métodos que una clase debe implementar.
// No contiene la lógica, sino solo la declaración de los métodos.
public interface IFarmaciaDAO{

    <Entidad> List<Entidad> listarEntidad(String entidad);
    Farmaco buscarFarmacoPorId(int farmacoId);
    public boolean insertar(Farmaco farmaco, Laboratorio laboratorio, String entidad);
    boolean modificarFarmaco(Farmaco farmaco, boolean buscarPorId,String input);
    boolean eliminarFarmaco(Farmaco farmaco_id);
    Farmaco validacionFarmaco();
    Laboratorio validacionLaboratorio();
    Farmaco validacionPorId(int id);


}
