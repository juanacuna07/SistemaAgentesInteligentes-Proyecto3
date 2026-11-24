/** Clase estructural de la clase C: tarea de un agente inteligente. */
export default class TareaAgente {
  constructor({ idTarea, nombre, prioridad, porcentajeAvance, fechaAsignacion, completada,
    horasEstimadas = null, observaciones = null, idAgenteInteligente }) {
    this.idTarea = idTarea;
    this.nombre = nombre;
    this.prioridad = prioridad;
    this.porcentajeAvance = porcentajeAvance;
    this.fechaAsignacion = fechaAsignacion;
    this.completada = completada;
    this.horasEstimadas = horasEstimadas;
    this.observaciones = observaciones;
    this.idAgenteInteligente = idAgenteInteligente;
  }

  static desdeDatos(datos) {
    return new TareaAgente(datos);
  }
}
