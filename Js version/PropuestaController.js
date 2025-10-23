class PropuestaController {
  constructor() {
    this.propuestas = [];
  }

  crearPropuesta(propuesta) {
    this.propuestas.push(propuesta);
    let cadena = "Propuesta creada:" + propuesta.getTitulo();
    return cadena;
  }

  actualizarProgreso(propuesta, nuevoProgreso) {
    propuesta.setProgreso(nuevoProgreso);
    let cadena = "Progreso actualizado a " + nuevoProgreso + "% para propuesta: " + propuesta.getTitulo();
    return cadena;
  }

  registrarInversion(propuesta, inversionista) {
    propuesta.setInversionistas(inversionista);
    inversionista.setInvertir(propuesta.getTitulo());
    let cadena = "Proyecto  " + propuesta.getTitulo() + " apoyado por " + inversionista.getNombre();
    return cadena;
  }

  registrarDonacion(propuesta, donante) {
    propuesta.setDonantes(donante);
    donante.setDonaciones(propuesta.getTitulo());
    let cadena = "Proyecto  " + propuesta.getTitulo() + " apoyado por " + donante.getNombre();
    return cadena;
  }

  registrarApoyo(propuesta, voluntario) {
    propuesta.setVoluntarios(voluntario);
    voluntario.setApoyos(propuesta.getTitulo());
    let cadena = "Proyecto " + propuesta.getTitulo() + "apoyado por" + voluntario.getNombre();
    return cadena;
  }

  getPropuestas() {
    return this.propuestas;
  }
}
