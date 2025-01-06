package farmacia.datos;

import farmacia.dominio.Farmaco;
import farmacia.dominio.Laboratorio;

import java.util.List;



//Una interfaz es un contrato que define los métodos que una clase debe implementar.
// No contiene la lógica, sino solo la declaración de los métodos.
public interface IFarmaciaDAO{

    <Entidad> List<Entidad> listarEntidad(String entidad);
    public boolean insertar(Farmaco farmaco, Laboratorio laboratorio, String entidad);
    boolean modificarFarmaco(Farmaco farmaco, Laboratorio laboratorio, boolean buscarPorId,String input, String entidad);
    boolean eliminar(Farmaco farmaco, Laboratorio laboratorio, int id, String entidad);
    Farmaco validacionFarmaco();
    Laboratorio validacionLaboratorio(Integer id);
    Farmaco validacionPorId(int id);


}
