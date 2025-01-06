package farmacia.dominio;

public class Laboratorio {

    private int laboratorio_id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private Nacionalidad nacionalidad1;
    private int nacionalidad_id;
    private Provincia provincia1;
    private int provincia_id;
    private Ciudad ciudad1;
    private int ciudad_id;
    private String nacionalidad;
    private String provincia;
    private String ciudad;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Laboratorio{");
        sb.append("laboratorio_id=").append(laboratorio_id);
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append(", direccion='").append(direccion).append('\'');
        sb.append(", telefono='").append(telefono).append('\'');
        sb.append(", email='").append(email).append('\'');

        sb.append(", nacionalidad_id=").append(nacionalidad_id);

        sb.append(", provincia_id=").append(provincia_id);

        sb.append(", ciudad_id=").append(ciudad_id);

        sb.append('}');
        return sb.toString();
    }




    public Laboratorio(String nombre, String direccion, String telefono, String email, String nacionalidad, String provincia, String ciudad) {

        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.nacionalidad = nacionalidad;
        this.provincia = provincia;
        this.ciudad = ciudad;


    }

    public Laboratorio(int id, String nombre, String direccion, String telefono, String email, String nacionalidad, String provincia, String ciudad) {
        this.laboratorio_id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.nacionalidad = nacionalidad;
        this.provincia = provincia;
        this.ciudad = ciudad;


    }


    public Laboratorio(String direccion, String nombre, String telefono, String email, Provincia provincia, Nacionalidad nacionalidad, Ciudad ciudad) {


        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.provincia1 = provincia;
        this.nacionalidad1 = nacionalidad;
        this.ciudad1 = ciudad;
    }


    public Laboratorio() {
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public int getCiudad_id() {
        return ciudad_id;
    }

    public void setCiudad_id(int ciudad_id) {
        this.ciudad_id = ciudad_id;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Ciudad getCiudad1() {
        return ciudad1;
    }

    public void setCiudad1(Ciudad ciudad1) {
        this.ciudad1 = ciudad1;
    }

    public int getProvincia_id() {
        return provincia_id;
    }

    public void setProvincia_id(int provincia_id) {
        this.provincia_id = provincia_id;
    }

    public Provincia getProvincia1() {
        return provincia1;
    }

    public void setProvincia1(Provincia provincia1) {
        this.provincia1 = provincia1;
    }

    public int getNacionalidad_id() {
        return nacionalidad_id;
    }

    public void setNacionalidad_id(int nacionalidad_id) {
        this.nacionalidad_id = nacionalidad_id;
    }

    public Nacionalidad getNacionalidad1() {
        return nacionalidad1;
    }

    public void setNacionalidad1(Nacionalidad nacionalidad1) {
        this.nacionalidad1 = nacionalidad1;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getLaboratorio_id() {
        return laboratorio_id;
    }

    public void setLaboratorio_id(int laboratorio_id) {
        this.laboratorio_id = laboratorio_id;
    }
}