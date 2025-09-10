<<<<<<< HEAD
//falta clase

//el extends sirve para heredar atributos y métodos de otras clases
=======
>>>>>>> 65052aaf5f7d7bb2747f52d8aa15d127346595d4
import java.util.ArrayList;
import java.util.List;
//crea la clase de Voluntario
class Voluntario extends Usuario {
    private List<String> habilidades;
    private List<String> apoyos;
//crea el nombre, la contraseña, el correo y la ubicacion del voluntatio y lo agrega en una lista 
    public Voluntario(String nombre, String correo, String contrasena, String ubicacion) {
        super(nombre, correo, contrasena, ubicacion);
        this.habilidades = new ArrayList<>();
        this.apoyos = new ArrayList<>();
    }
//se agrego una lista para las habilidades de los voluntarios
    public List<String> getHabilidades() { return habilidades; }

<<<<<<< HEAD
    public void setApoyos(String apoyo){

=======
    public void agregarHabilidad(String habilidad) {
        if (!this.habilidades.contains(habilidad)) {
            this.habilidades.add(habilidad);
        }
>>>>>>> 65052aaf5f7d7bb2747f52d8aa15d127346595d4
    }
//la lista de las Habililidades
    public void setHabilidades(List<String> habilidades) {
        this.habilidades = habilidades;
    }
//
    public List<String> getApoyos() { return apoyos; }

    public void agregarApoyo(String apoyo) {
        if (!this.apoyos.contains(apoyo)) {
            this.apoyos.add(apoyo);
        }
    }
//aca se agrega las habilidades de los voluntarios 
    @Override
    public String toString() {
        return "Voluntario{" +
                "habilidades=" + habilidades +
                ", apoyos=" + apoyos +
                ;  }
}
//agrege 2 listas que son las de las habilidades y de los apoyos, asi se tiene el conocimiento de que tan habil es el voluntario 
//tambien agrege validaciones para poder evitar duplicados y tambien rescribi el metodo string para poder ver mejor la info 
