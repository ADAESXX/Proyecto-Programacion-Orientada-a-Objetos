//crea la clase de Donante
class Donante extends Usuario {
    private List<String> donaciones;
//crea la informacion para el donante 
    public Donante(String nombre, String correo, String contrasena, String ubicacion) {
        super(nombre, correo, contrasena, ubicacion);
        this.donaciones = new ArrayList<>();
    }
//crea la lista para las donaciones
    public List<String> getDonaciones() {
        return donaciones;
    }
//se agrega las donaciones en la lista
    public void agregarDonacion(String donacion) {
        this.donaciones.add(donacion);
    }
}
//quite getDonacioneRealizadas, ya que es lo mismo que hace getDonaciones, y seria tonto dejarlo
//se pusieron las listas para que sea mas facil encontrar las donaciones 
