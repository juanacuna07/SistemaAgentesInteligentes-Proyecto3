package com.unibague.tareas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.unibague.tareas.dto.Pagina;
import com.unibague.tareas.model.TareaAgente;
import com.unibague.tareas.servicios.ServicioTareaAgente;

import jakarta.validation.Valid;

/**
 * Servicio web REST del microservicio C. Lo consume el microservicio A-B
 * (los clientes graficos nunca llaman directamente a este microservicio).
 */
@RestController
@RequestMapping("/api/tareas")
public class TareaAgenteController {

    private final ServicioTareaAgente servicio;

    public TareaAgenteController(ServicioTareaAgente servicio) {
        this.servicio = servicio;
    }

    /** POST /api/tareas : insertar una tarea. */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TareaAgente crear(@Valid @RequestBody TareaAgente tarea) {
        return servicio.crear(tarea);
    }

    /** GET /api/tareas/5 : buscar una tarea por su ID. */
    @GetMapping("/{idTarea}")
    public TareaAgente buscarPorId(@PathVariable int idTarea) {
        return servicio.buscarPorId(idTarea);
    }

    /** GET /api/tareas/buscar?nombre=Mapear zona : buscar una tarea por su nombre. */
    @GetMapping("/buscar")
    public TareaAgente buscarPorNombre(@RequestParam String nombre) {
        return servicio.buscarPorNombre(nombre);
    }

    /** GET /api/tareas?idAgente=1&prioridad=3&pagina=0&tamano=25 : listar (los filtros son opcionales). */
    @GetMapping
    public Pagina<TareaAgente> listar(@RequestParam(required = false) Integer idAgente,
                                      @RequestParam(required = false) Integer prioridad,
                                      @RequestParam(defaultValue = "0") int pagina,
                                      @RequestParam(defaultValue = "25") int tamano) {
        return servicio.listar(idAgente, prioridad, pagina, tamano);
    }

    /** PUT /api/tareas/5 : actualizar una tarea (el ID no cambia). */
    @PutMapping("/{idTarea}")
    public TareaAgente actualizar(@PathVariable int idTarea, @Valid @RequestBody TareaAgente datos) {
        datos.setIdTarea(idTarea);
        return servicio.actualizar(idTarea, datos);
    }

    /** DELETE /api/tareas/5 : eliminar una tarea. */
    @DeleteMapping("/{idTarea}")
    public String eliminar(@PathVariable int idTarea) {
        return servicio.eliminar(idTarea);
    }

    /** GET /api/tareas/contar?idAgente=1 : cuantas tareas tiene un agente (integridad referencial). */
    @GetMapping("/contar")
    public long contarPorAgente(@RequestParam int idAgente) {
        return servicio.contarPorAgente(idAgente);
    }
}
