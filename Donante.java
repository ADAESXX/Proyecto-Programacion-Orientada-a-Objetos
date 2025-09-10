//falta clase
import java.util.ArrayList;
//hereda métodos de la clase Uusuario
public class Donante extends Usuario{
    private ArrayList<String> donaciones;
    public Donante(String nombre, String correo, String contrasena, String ubicacion) {
        super(nombre, correo, contrasena, ubicacion);
        this.donaciones = new ArrayList<>();
    }

    public void setDonaciones(String newdonacion){

    }
    public ArrayList<String> getDonaciones(){
        return donaciones;
    }
}
