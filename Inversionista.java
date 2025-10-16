//clase revisada - validaciones completadas

import java.util.*;
public class Inversionista extends Usuario {
    
    // Lista que contiene las inversiones realizadas por el inversionista
    private ArrayList<String> inversiones;

    // Constructor que recibe los datos del usuario y crea una lista vacía de inversiones
    public Inversionista(String nombre, String correo, String contrasena, String ubicacion) {
        // Llamada al constructor de la clase padre (Usuario) para inicializar los atributos heredados
        super(nombre, correo, contrasena, ubicacion);
        
        // Inicializa la lista de inversiones como vacía
        this.inversiones = new ArrayList<>();
    }

    // Método para registrar una nueva inversión en la lista
    public void setInvertir(String inversion) {
        if (inversion == null || inversion.isEmpty()){ 
            throw new IllegalArgumentException("La inversión es inválida");
        }
        this.inversiones.add(inversion.trim());
    }

    // Otro getter que devuelve la lista de inversiones (funciona igual que getInversiones)
    public ArrayList<String> getInvertir() { 
        return inversiones; 
    }

}


