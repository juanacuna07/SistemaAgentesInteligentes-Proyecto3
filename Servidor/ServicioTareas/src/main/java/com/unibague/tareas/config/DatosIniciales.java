package com.unibague.tareas.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.unibague.tareas.model.TareaAgente;
import com.unibague.tareas.repository.TareaAgenteRepository;

/**
 * Carga tareas de prueba solo si la coleccion esta vacia.
 * Usan los agentes inteligentes 1 (Atlas) y 2 (Boreas) que crea el microservicio A-B.
 * El agente 3 (Cronos) queda sin tareas para poder mostrar que si se deja eliminar.
 */
@Component
public class DatosIniciales implements CommandLineRunner {

    private final TareaAgenteRepository repositorio;

    public DatosIniciales(TareaAgenteRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void run(String... args) {
        if (repositorio.count() > 0) {
            return;
        }
        repositorio.insert(crear(1, "Mapear zona centro", 5, 100.0, LocalDateTime.of(2026, 9, 1, 8, 0),
                true, 12.0, "Mapa entregado al equipo", 1));
        repositorio.insert(crear(2, "Optimizar rutas de entrega", 4, 65.5, LocalDateTime.of(2026, 10, 5, 9, 30),
                false, 30.0, null, 1));
        repositorio.insert(crear(3, "Revisar sensores", 3, 20.0, LocalDateTime.of(2026, 10, 20, 14, 0),
                false, null, "Pendiente por repuestos", 2));
        repositorio.insert(crear(4, "Entrenar modelo de voz", 2, 0.0, LocalDateTime.of(2026, 11, 2, 10, 15),
                false, 45.5, null, 2));
    }

    private TareaAgente crear(int id, String nombre, int prioridad, double avance, LocalDateTime fecha,
                              boolean completada, Double horas, String observaciones, int idAgente) {
        TareaAgente tarea = new TareaAgente();
        tarea.setIdTarea(id);
        tarea.setNombre(nombre);
        tarea.setPrioridad(prioridad);
        tarea.setPorcentajeAvance(avance);
        tarea.setFechaAsignacion(fecha);
        tarea.setCompletada(completada);
        tarea.setHorasEstimadas(horas);
        tarea.setObservaciones(observaciones);
        tarea.setIdAgenteInteligente(idAgente);
        return tarea;
    }
}
