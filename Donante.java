//clae revisada - validaciones completas
//faltan revisar funciones extras y comunicaicon entre clases

import java.util.*;

//crea la clase de Donante
class Donante extends Usuario {
    private ArrayList<String> donaciones;
//crea la informacion para el donante 
    public Donante(String nombre, String correo, String contrasena, String ubicacion) {
        super(nombre, correo, contrasena, ubicacion);
        this.donaciones = new ArrayList<>();
    }
//crea la lista para las donaciones
    public ArrayList<String> getDonaciones() {
        return donaciones;
    }
//se agrega las donaciones en la lista
    public void setDonaciones(String donacion){ 
        if (donacion == null || donacion.isBlank()){ 
                throw new IllegalArgumentException("Donació inválida");
        }
        this.donaciones.add(donacion.trim());
    }
}
//quite getDonacioneRealizadas, ya que es lo mismo que hace getDonaciones, y seria tonto dejarlo
//se pusieron las listas para que sea mas facil encontrar las donaciones 
