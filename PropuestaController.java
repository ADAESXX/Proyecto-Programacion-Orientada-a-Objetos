import java.util.*;
public class PropuestaController {

    //se registran todas las propuestas (no importa que perfil lo realizó)
    private ArrayList<Propuesta> propuestas;
    //constructor
    public PropuestaController(){
        propuestas= new ArrayList<>();
    }

    ////metodos////
    //para crear la propuesta necesitamos obtener la propuesta
    public String crearPropuesta(Propuesta propuesta){
        //se agrega al arreglo las propuestas
        propuestas.add(propuesta);
        String cadena="Propuesta creada:" + propuesta.getTitulo();
        return cadena;
       
    }
    //se necesita la propuesta y su progreso para registrar los avances
    public String actualizarProgreso(Propuesta propuesta, float nuevoProgreso){
        //si se actualiza en el arreglo, ya que lo que el arreglo tiene solo es una referencia
        propuesta.setProgreso(nuevoProgreso);
        String cadena="Progreso actualizado a " + nuevoProgreso + "% para propuesta: " + propuesta.getTitulo();
        return cadena;
    }
    //se necesita saber que porpuesta y que inversionista para poder crear esa relacion de inversion
    public String registrarInversion(Propuesta propuesta, Inversionista inversionista){
        propuesta.setInversionistas(inversionista);
        inversionista.setInvertir(propuesta.getTitulo());
        String cadena="Proyecto  " + propuesta.getTitulo() + " apoyado por " + inversionista.getNombre();
        return cadena;
    }
    //se necesita saber que propuesta y que donante la apoya para poder crear esa relacion de donacion
    public String registrarDonacion(Propuesta propuesta, Donante donante){
        propuesta.setDonantes(donante);
        donante.setDonaciones(propuesta.getTitulo());
        String cadena="Proyecto  " + propuesta.getTitulo() + " apoyado por " + donante.getNombre();
        return cadena;
    }
    //se necesita saber que porpuesta y que voluntario es para poder crear esa relacion de 
    public String registrarApoyo(Propuesta propuesta, Voluntario voluntario){
        propuesta.setVoluntarios(voluntario);
        voluntario.setApoyos(propuesta.getTitulo());
        String cadena="Proyecto " + propuesta.getTitulo() + "apoyado por" + voluntario.getNombre();
        return cadena;
    }
    public ArrayList<Propuesta> getPropuestas(){
        return propuestas;
    }
}
