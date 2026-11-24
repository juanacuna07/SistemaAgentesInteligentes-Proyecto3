package com.unibague.agentes.model;

import java.time.LocalDateTime;

/**
 * Clase C (tarea de un agente inteligente). Este microservicio NO la guarda en su base de datos:
 * la guarda el microservicio de tareas (tareas_agentes_db). Aqui solo se usa para recibir y
 * enviar los datos de las tareas cuando se llama a ese microservicio.
 */
public class TareaAgente {

    private Integer idTarea;                // int           - llave primaria
    private String nombre;                  // String        - obligatorio, unico
    private Integer prioridad;              // int           - obligatorio (1 a 5)
    private Double porcentajeAvance;        // double        - obligatorio (0 a 100)
    private LocalDateTime fechaAsignacion;  // LocalDateTime - obligatorio
    private Boolean completada;             // boolean       - obligatorio
    private Double horasEstimadas;          // double        - OPCIONAL
    private String observaciones;           // String        - OPCIONAL
    private Integer idAgenteInteligente;    // referencia al agente inteligente (clase A) - obligatorio

    public TareaAgente() {
    }

    public Integer getIdTarea() { return idTarea; }
    public void setIdTarea(Integer idTarea) { this.idTarea = idTarea; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getPrioridad() { return prioridad; }
    public void setPrioridad(Integer prioridad) { this.prioridad = prioridad; }
    public Double getPorcentajeAvance() { return porcentajeAvance; }
    public void setPorcentajeAvance(Double porcentajeAvance) { this.porcentajeAvance = porcentajeAvance; }
    public LocalDateTime getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(LocalDateTime fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }
    public Boolean getCompletada() { return completada; }
    public void setCompletada(Boolean completada) { this.completada = completada; }
    public Double getHorasEstimadas() { return horasEstimadas; }
    public void setHorasEstimadas(Double horasEstimadas) { this.horasEstimadas = horasEstimadas; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public Integer getIdAgenteInteligente() { return idAgenteInteligente; }
    public void setIdAgenteInteligente(Integer idAgenteInteligente) { this.idAgenteInteligente = idAgenteInteligente; }
}
