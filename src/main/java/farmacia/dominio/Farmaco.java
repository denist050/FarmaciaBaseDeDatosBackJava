package farmacia.dominio;

import java.util.Objects;

// ORM OBJECT RELATIONAL MAPING
public class Farmaco {

    private int farmaco_id;
    private String nombre;
    private String descripcion;
    private String tipo;


    public Farmaco(){

    }

    public Farmaco(int farmaco_id){

        this.farmaco_id = farmaco_id;
    }

    public Farmaco(String nombre, String descripcion, String tipo){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public int getFarmaco_id() {
        return farmaco_id;
    }

    public void setFarmaco_id(int farmaco_id) {
        this.farmaco_id = farmaco_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Farmaco(int farmaco_id, String nombre, String descripcion, String tipo){

        this(nombre, descripcion, tipo); //llamamos al otro constructor para ahorrar lineas de codigo
        this.farmaco_id = farmaco_id;

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Farmaco{");
        sb.append("farmaco_id=").append(farmaco_id);
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append(", descripcion='").append(descripcion).append('\'');
        sb.append(", tipo='").append(tipo).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Farmaco farmaco = (Farmaco) o;
        return farmaco_id == farmaco.farmaco_id && Objects.equals(nombre, farmaco.nombre) && Objects.equals(descripcion, farmaco.descripcion) && Objects.equals(tipo, farmaco.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(farmaco_id, nombre, descripcion, tipo);
    }


}
