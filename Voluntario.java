//falta clase

//el extends sirve para heredar atributos y métodos de otras clases
import java.util.ArrayList;

public class Voluntario extends Usuario {
    private ArrayList<String> habilidades= new ArrayList<>();
    private ArrayList<String> apoyos= new ArrayList<>();

    public Voluntario(String nombre, String correo, String contrasena, String ubicacion){
        super(nombre, correo, contrasena, ubicacion);
    }

    public void setApoyos(String apoyo){

    }
}
