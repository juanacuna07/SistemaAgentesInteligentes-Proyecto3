package com.unibague.agentes.dto;

import java.time.LocalDateTime;

import com.unibague.agentes.model.AgenteInteligente;
import com.unibague.agentes.model.EstadoOperativo;
import com.unibague.agentes.model.TareaAgente;

/**
 * Fila de la grilla de tareas: TODOS los atributos de la tarea (viene del microservicio C)
 * + DOS atributos del agente inteligente (viene de la coleccion agentes_inteligentes):
 * su nombre y su estado operativo.
 */
public class TareaConAgente {

    private final TareaAgente tarea;
    private final String nombreAgente;
    private final EstadoOperativo estadoAgente;

    public TareaConAgente(TareaAgente tarea, AgenteInteligente agente) {
        this.tarea = tarea;
        this.nombreAgente = agente == null ? null : agente.getNombre();
        this.estadoAgente = agente == null ? null : agente.getEstadoOperativo();
    }

    public Integer getIdTarea() { return tarea.getIdTarea(); }
    public String getNombre() { return tarea.getNombre(); }
    public Integer getPrioridad() { return tarea.getPrioridad(); }
    public Double getPorcentajeAvance() { return tarea.getPorcentajeAvance(); }
    public LocalDateTime getFechaAsignacion() { return tarea.getFechaAsignacion(); }
    public Boolean getCompletada() { return tarea.getCompletada(); }
    public Double getHorasEstimadas() { return tarea.getHorasEstimadas(); }
    public String getObservaciones() { return tarea.getObservaciones(); }
    public Integer getIdAgenteInteligente() { return tarea.getIdAgenteInteligente(); }
    // --- datos que vienen del agente inteligente (clase A) ---
    public String getNombreAgente() { return nombreAgente; }
    public EstadoOperativo getEstadoAgente() { return estadoAgente; }
}
