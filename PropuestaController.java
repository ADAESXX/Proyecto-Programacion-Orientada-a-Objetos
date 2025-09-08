import java.util.*;
public class PropuestaController {

    //se registran todas las propuestas (no importa quien la hizo)
    private ArrayList<Propuesta> propuestas;
    //constructor
    public PropuestaController(){
        propuestas= new ArrayList<>();
    }

    ////metodos////
    //para crear la propuesta necesitamos obtener la propuesta
    public void crearPropuesta(Propuesta propuesta){
        //se agrega al arreglo las propuestas
        propuestas.add(propuesta);
        System.out.println("Propuesta creada: " + propuesta.getTitulo());
    }
    //se necesita la propuesta y su progreso para registrar los avances
    public void actualizarProgreso(Propuesta propuesta, float nuevoProgreso){
        //si se actualiza en el arreglo, ya que lo que el arreglo tiene solo es una referencia
        propuesta.setProgreso(nuevoProgreso);
        System.out.println("Progreso actualizado a " + nuevoProgreso + "% para propuesta: " + propuesta.getTitulo());
    }
    //se necesita saber que porpuesta y que inversionista para poder crear esa relacion de inversion
    public void registrarInversion(Propuesta propuesta, Inversionista inversionista){
        propuesta.setInversionistas(inversionista);
        inversionista.setInvertir(propuesta.getTitulo());
        System.out.println("Proyecto  " + propuesta.getTitulo() + " apoyado por " + inversionista.getNombre());
    }
    //se necesita saber que propuesta y que donante la apoya para poder crear esa relacion de donacion
    public void registrarDonacion(Propuesta propuesta, Donante donante){
        propuesta.setDonantes(donante);
        donante.setDonaciones(propuesta.getTitulo());
        System.out.println("Proyecto  " + propuesta.getTitulo() + " apoyado por " + donante.getNombre());
    }
    //se necesita saber que porpuesta y que voluntario es para poder crear esa relacion de 
    public void registrarApoyo(Propuesta propuesta, Voluntario voluntario){
        propuesta.serVoluntarios(voluntario);
        voluntario.setApoyos(propuesta.getTitulo());
        System.out.println("Proyecto " + propuesta.getTitulo() + "apoyado por" + voluntario.getNombre());
    }
    public ArrayList<Propuesta> getPropuestas(){
        return propuestas;
    }
}
