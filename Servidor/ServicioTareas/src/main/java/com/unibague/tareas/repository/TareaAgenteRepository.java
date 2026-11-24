package com.unibague.tareas.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.unibague.tareas.model.TareaAgente;

/** Acceso a la coleccion "tareas_agentes" (Spring Data MongoDB). */
public interface TareaAgenteRepository extends MongoRepository<TareaAgente, Integer> {

    /** Busqueda individual por nombre (sin distinguir mayusculas). El nombre es unico. */
    Optional<TareaAgente> findByNombreIgnoreCase(String nombre);

    /** Filtro: tareas de un agente inteligente (paginado). */
    Page<TareaAgente> findByIdAgenteInteligente(Integer idAgenteInteligente, Pageable pagina);

    /** Filtro: tareas con una prioridad (paginado). */
    Page<TareaAgente> findByPrioridad(Integer prioridad, Pageable pagina);

    /** Cuantas tareas tiene un agente. Lo usa el microservicio A-B antes de borrar un agente. */
    long countByIdAgenteInteligente(Integer idAgenteInteligente);
}
