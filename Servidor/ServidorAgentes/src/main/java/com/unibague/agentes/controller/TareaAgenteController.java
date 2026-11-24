package com.unibague.agentes.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.unibague.agentes.dto.ActualizarTareaAgenteInput;
import com.unibague.agentes.dto.Pagina;
import com.unibague.agentes.dto.TareaAgenteInput;
import com.unibague.agentes.dto.TareaConAgente;
import com.unibague.agentes.model.TareaAgente;
import com.unibague.agentes.servicios.IServicioTareaAgente;

import jakarta.validation.Valid;

/**
 * Expone el CRUD de las tareas (clase C) como servicio web GraphQL.
 * Por dentro, el servicio llama al microservicio de tareas (REST).
 */
@Controller
public class TareaAgenteController {

    private final IServicioTareaAgente servicio;

    public TareaAgenteController(IServicioTareaAgente servicio) {
        this.servicio = servicio;
    }

    /** Listar PAGINADO con filtro opcional por agente o por prioridad: tarea + 2 datos del agente. */
    @QueryMapping
    public Pagina<TareaConAgente> tareasConAgente(@Argument Integer idAgente, @Argument Integer prioridad,
                                                  @Argument Integer pagina, @Argument Integer tamano) {
        return servicio.listarConAgente(idAgente, prioridad, pagina, tamano);
    }

    @QueryMapping
    public TareaAgente tareaAgentePorId(@Argument int idTarea) {
        return servicio.buscarPorId(idTarea);
    }

    @QueryMapping
    public TareaAgente tareaAgentePorNombre(@Argument String nombre) {
        return servicio.buscarPorNombre(nombre);
    }

    @MutationMapping
    public TareaAgente crearTareaAgente(@Argument @Valid TareaAgenteInput input) {
        return servicio.crear(input);
    }

    @MutationMapping
    public TareaAgente actualizarTareaAgente(@Argument int idTarea,
                                             @Argument @Valid ActualizarTareaAgenteInput input) {
        return servicio.actualizar(idTarea, input);
    }

    @MutationMapping
    public String eliminarTareaAgente(@Argument int idTarea) {
        return servicio.eliminar(idTarea);
    }
}
