package farmacia.dominio;

public class Nacionalidad {

    private int nacionalidad_id;
    private String nombre;

    public int getNacionalidad_id() {
        return nacionalidad_id;
    }

    public void setNacionalidad_id(int nacionalidad_id) {
        this.nacionalidad_id = nacionalidad_id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Nacionalidad{");
        sb.append("nacionalidad_id=").append(nacionalidad_id);
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
