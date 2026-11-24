package com.unibague.agentes.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.unibague.agentes.dto.ActualizarTareaAgenteInput;
import com.unibague.agentes.dto.Pagina;
import com.unibague.agentes.dto.Paginacion;
import com.unibague.agentes.dto.TareaAgenteInput;
import com.unibague.agentes.dto.TareaConAgente;
import com.unibague.agentes.excepciones.RecursoNoEncontradoException;
import com.unibague.agentes.excepciones.ReglaNegocioException;
import com.unibague.agentes.model.AgenteInteligente;
import com.unibague.agentes.model.TareaAgente;
import com.unibague.agentes.repository.AgenteInteligenteRepository;

/**
 * Gestiona las tareas de forma transparente para los clientes: recibe la peticion GraphQL,
 * verifica la integridad referencial con los agentes inteligentes (que estan en ESTA base de datos)
 * y luego llama al microservicio de tareas, que es quien guarda la informacion.
 */
@Service
public class ServicioTareaAgente implements IServicioTareaAgente {

    private final ClienteMicroservicioTareas microservicioTareas;
    private final AgenteInteligenteRepository repositorioAgentes;

    public ServicioTareaAgente(ClienteMicroservicioTareas microservicioTareas,
                               AgenteInteligenteRepository repositorioAgentes) {
        this.microservicioTareas = microservicioTareas;
        this.repositorioAgentes = repositorioAgentes;
    }

    /** Listar con filtro opcional: todos los datos de la tarea + nombre y estado de su agente. */
    @Override
    public Pagina<TareaConAgente> listarConAgente(Integer idAgente, Integer prioridad,
                                                  Integer pagina, Integer tamano) {
        if (idAgente != null && !repositorioAgentes.existsById(idAgente)) {
            throw new RecursoNoEncontradoException("No existe un agente inteligente con ID " + idAgente);
        }
        if (prioridad != null && (prioridad < 1 || prioridad > 5)) {
            throw new ReglaNegocioException("La prioridad debe estar entre 1 y 5");
        }
        int p = Paginacion.pagina(pagina);
        int t = Paginacion.tamano(tamano);
        Pagina<TareaAgente> tareas = microservicioTareas.listar(idAgente, prioridad, p, t);

        // a cada tarea se le agregan dos datos de su agente inteligente
        List<TareaConAgente> filas = new ArrayList<>();
        for (TareaAgente tarea : tareas.elementos()) {
            AgenteInteligente agente = repositorioAgentes.findById(tarea.getIdAgenteInteligente()).orElse(null);
            filas.add(new TareaConAgente(tarea, agente));
        }
        return Pagina.de(filas, tareas.total(), tareas.pagina(), tareas.tamano());
    }

    @Override
    public TareaAgente buscarPorId(int idTarea) {
        if (idTarea <= 0) {
            throw new ReglaNegocioException("El ID debe ser un entero positivo");
        }
        return microservicioTareas.buscarPorId(idTarea);
    }

    @Override
    public TareaAgente buscarPorNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new ReglaNegocioException("Debe escribir el nombre de la tarea a buscar");
        }
        return microservicioTareas.buscarPorNombre(nombre.trim());
    }

    @Override
    public TareaAgente crear(TareaAgenteInput in) {
        validarAgenteExiste(in.idAgenteInteligente());   // integridad referencial

        TareaAgente tarea = new TareaAgente();
        tarea.setIdTarea(in.idTarea());
        tarea.setNombre(in.nombre());
        tarea.setPrioridad(in.prioridad());
        tarea.setPorcentajeAvance(in.porcentajeAvance());
        tarea.setFechaAsignacion(in.fechaAsignacion());
        tarea.setCompletada(in.completada());
        tarea.setHorasEstimadas(in.horasEstimadas());
        tarea.setObservaciones(in.observaciones());
        tarea.setIdAgenteInteligente(in.idAgenteInteligente());
        return microservicioTareas.crear(tarea);
    }

    @Override
    public TareaAgente actualizar(int idTarea, ActualizarTareaAgenteInput in) {
        TareaAgente tarea = buscarPorId(idTarea);          // busqueda previa
        validarAgenteExiste(in.idAgenteInteligente());     // integridad referencial

        tarea.setNombre(in.nombre());
        tarea.setPrioridad(in.prioridad());
        tarea.setPorcentajeAvance(in.porcentajeAvance());
        tarea.setFechaAsignacion(in.fechaAsignacion());
        tarea.setCompletada(in.completada());
        tarea.setHorasEstimadas(in.horasEstimadas());
        tarea.setObservaciones(in.observaciones());
        tarea.setIdAgenteInteligente(in.idAgenteInteligente());
        return microservicioTareas.actualizar(idTarea, tarea);
    }

    @Override
    public String eliminar(int idTarea) {
        buscarPorId(idTarea);                               // busqueda previa
        return microservicioTareas.eliminar(idTarea);
    }

    private void validarAgenteExiste(Integer idAgente) {
        if (idAgente == null || !repositorioAgentes.existsById(idAgente)) {
            throw new ReglaNegocioException("No existe el agente inteligente con ID " + idAgente
                    + ". Toda tarea debe pertenecer a un agente inteligente existente");
        }
    }
}
