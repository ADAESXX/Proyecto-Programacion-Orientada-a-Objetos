//clase revisada- validaciones completas

//el extends sirve para heredar atributos y métodos de otras clases
import java.util.ArrayList;
//crea la clase de Voluntario
class Voluntario extends Usuario {
    private ArrayList<String> habilidades;
    private ArrayList<String> apoyos;
//crea el nombre, la contraseña, el correo y la ubicacion del voluntatio y lo agrega en una lista 
    public Voluntario(String nombre, String correo, String contrasena, String ubicacion) {
        super(nombre, correo, contrasena, ubicacion);
        this.habilidades = new ArrayList<>();
        this.apoyos = new ArrayList<>();
    }
//se agrego una lista para las habilidades de los voluntarios
    public ArrayList<String> getHabilidades() { return habilidades; }

//la lista de las Habililidades
    public void setHabilidades(String habilidades) {
        if (habilidades == null || habilidades.isBlank()){ 
                throw new IllegalArgumentException("No ingreso una habilidad válida");
        }
        this.habilidades.add(habilidades);
    }
//
    public ArrayList<String> getApoyos() { return apoyos; }

    public void setApoyos(String apoyo) {
        if (apoyo == null || apoyo.isBlank()){ 
                throw new IllegalArgumentException("No existe el emisor");
        }
        //valida que no se duplique el apoyo
        if (!this.apoyos.contains(apoyo)){
            this.apoyos.add(apoyo);
        }
    }
//aca se agrega las habilidades de los voluntarios 
    public String toString() {
        return "Voluntario{" +"habilidades=" + habilidades +", apoyos=" + apoyos;
    }
}
//agrege 2 listas que son las de las habilidades y de los apoyos, asi se tiene el conocimiento de que tan habil es el voluntario 
//tambien agrege validaciones para poder evitar duplicados y tambien rescribi el metodo string para poder ver mejor la info 
