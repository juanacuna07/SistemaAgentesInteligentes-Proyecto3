package com.unibague.tareas.servicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.unibague.tareas.dto.Pagina;
import com.unibague.tareas.excepciones.RecursoNoEncontradoException;
import com.unibague.tareas.excepciones.ReglaNegocioException;
import com.unibague.tareas.model.TareaAgente;
import com.unibague.tareas.repository.TareaAgenteRepository;

/** Logica de negocio de las tareas (clase C). */
@Service
public class ServicioTareaAgente {

    private final TareaAgenteRepository repositorio;

    public ServicioTareaAgente(TareaAgenteRepository repositorio) {
        this.repositorio = repositorio;
    }

    public TareaAgente crear(TareaAgente tarea) {
        if (repositorio.existsById(tarea.getIdTarea())) {
            throw new ReglaNegocioException("Ya existe una tarea con ID " + tarea.getIdTarea());
        }
        validarNombreUnico(tarea.getNombre(), null);
        validarAvance(tarea);
        limpiarTextos(tarea);
        return repositorio.insert(tarea);
    }

    public TareaAgente buscarPorId(int idTarea) {
        if (idTarea <= 0) {
            throw new ReglaNegocioException("El ID debe ser un entero positivo");
        }
        return repositorio.findById(idTarea)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe una tarea con ID " + idTarea));
    }

    public TareaAgente buscarPorNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new ReglaNegocioException("Debe escribir el nombre de la tarea a buscar");
        }
        return repositorio.findByNombreIgnoreCase(nombre.trim())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una tarea con el nombre '" + nombre.trim() + "'"));
    }

    /** Lista una pagina de tareas: todas, o filtradas por agente o por prioridad. */
    public Pagina<TareaAgente> listar(Integer idAgente, Integer prioridad, int pagina, int tamano) {
        if (pagina < 0 || tamano < 1 || tamano > 100) {
            throw new ReglaNegocioException("La pagina debe ser 0 o mayor y el tamano entre 1 y 100");
        }
        PageRequest solicitud = PageRequest.of(pagina, tamano, Sort.by("idTarea"));
        Page<TareaAgente> resultado;
        if (idAgente != null) {
            resultado = repositorio.findByIdAgenteInteligente(idAgente, solicitud);
        } else if (prioridad != null) {
            resultado = repositorio.findByPrioridad(prioridad, solicitud);
        } else {
            resultado = repositorio.findAll(solicitud);
        }
        return Pagina.de(resultado);
    }

    public TareaAgente actualizar(int idTarea, TareaAgente datos) {
        TareaAgente tarea = buscarPorId(idTarea);   // busqueda previa
        validarNombreUnico(datos.getNombre(), idTarea);
        validarAvance(datos);

        tarea.setNombre(datos.getNombre());
        tarea.setPrioridad(datos.getPrioridad());
        tarea.setPorcentajeAvance(datos.getPorcentajeAvance());
        tarea.setFechaAsignacion(datos.getFechaAsignacion());
        tarea.setCompletada(datos.getCompletada());
        tarea.setHorasEstimadas(datos.getHorasEstimadas());
        tarea.setObservaciones(datos.getObservaciones());
        tarea.setIdAgenteInteligente(datos.getIdAgenteInteligente());
        limpiarTextos(tarea);
        return repositorio.save(tarea);
    }

    public String eliminar(int idTarea) {
        TareaAgente tarea = buscarPorId(idTarea);   // busqueda previa
        repositorio.deleteById(idTarea);
        return "Tarea '" + tarea.getNombre() + "' eliminada correctamente.";
    }

    public long contarPorAgente(int idAgente) {
        return repositorio.countByIdAgenteInteligente(idAgente);
    }

    // ----------------------------------------------------------------- utilidades

    private void validarNombreUnico(String nombre, Integer idActual) {
        repositorio.findByNombreIgnoreCase(nombre.trim()).ifPresent(existente -> {
            if (idActual == null || !existente.getIdTarea().equals(idActual)) {
                throw new ReglaNegocioException("Ya existe una tarea con el nombre '" + nombre.trim() + "'");
            }
        });
    }

    /** Regla del negocio: una tarea completada debe tener 100% de avance. */
    private void validarAvance(TareaAgente tarea) {
        if (tarea.getCompletada() && tarea.getPorcentajeAvance() < 100) {
            throw new ReglaNegocioException("Si la tarea esta completada, el avance debe ser 100%");
        }
    }

    private void limpiarTextos(TareaAgente tarea) {
        tarea.setNombre(tarea.getNombre().trim());
        String observaciones = tarea.getObservaciones();
        tarea.setObservaciones(observaciones == null || observaciones.isBlank() ? null : observaciones.trim());
    }
}
